package com.youlb.controller.access;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.youlb.biz.access.IDeviceBiz;
import com.youlb.controller.SMSManage.SMSManageCtrl;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.DeviceInfo;
import com.youlb.entity.access.DeviceInfoDto;
import com.youlb.utils.common.ExcelUtils;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: DeviceCtrl.java 
 * @Description: 设备管理 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/device")
public class DeviceCtrl extends BaseCtrl{
	private static Logger log = LoggerFactory.getLogger(DeviceCtrl.class);
	@Autowired
	private ServletContext context;
	public void setContext(ServletContext context) {
		this.context = context;
	}

	@Autowired
    private IDeviceBiz deviceBiz;
	public void setDeviceBiz(IDeviceBiz deviceBiz) {
		this.deviceBiz = deviceBiz;
	}
	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(DeviceInfo device){
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		try {
			list = deviceBiz.showList(device,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}

	 /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,DeviceInfo device,Model model){
    	if(ids!=null&&ids.length>0){
    		 try {
				device = deviceBiz.get(ids[0]);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	//获取父节点
//    	if(StringUtils.isNotBlank(domain.getParentId())){
//    		Domain parentDomain = domainBiz.get(domain.getParentId());
//    		domain.setParentName(parentDomain.getName());//父节点名称
//    		domain.setLevel(parentDomain.getLevel());//父节点等级
//    	}else{
//    		domain.setParentId("1");
//    	}
    	model.addAttribute("device",device);
   		return "/device/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(DeviceInfo device,Model model){
    	try {
    		deviceBiz.saveOrUpdate(device,getLoginUser());
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }
    /**
     * 删除
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/delete.do")
	@ResponseBody
	public String delete(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				deviceBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	/**
     * 设置密码
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/toDeviceInfoSetPws.do")
	public String toDeviceInfoSetPws(DeviceInfo device,Model model){
		
		model.addAttribute("device",device);
		return "/device/deviceInfoSetPws";
	}
	/**
     * 设置密码
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/deviceInfoSetPws.do")
	@ResponseBody
	public String deviceInfoSetPws(DeviceInfo device,Model model){
			try {
				deviceBiz.update(device);
			} catch (Exception e) {
				super.message =  "设置密码出错";
			}
		return super.message;
	}
	
		/**
	     * 下载模板
	     * @param fileName
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping("/singleDownModel.do")    
	    public ModelAndView download(HttpServletRequest request,HttpServletResponse response) {
	    		String path = SMSManageCtrl.class.getClassLoader().getResource("").getPath(); 
	    		File file = new File(path+"resource/deviceInfo.xls");
	    		long fileLength = file.length();  
	    		BufferedInputStream bis = null;
	    		BufferedOutputStream out = null;
	    		try {
	    			response = setFileDownloadHeader(request,response, "deviceInfo.xls",fileLength);
	    			bis = new BufferedInputStream(new FileInputStream(file));
	    			out = new BufferedOutputStream(response.getOutputStream());
	    			byte[] buff = new byte[3072];
	    			int bytesRead;
	    			while ((bytesRead = bis.read(buff, 0, buff.length))!=-1) {
	    				out.write(buff, 0, bytesRead);
	    			}
	    		} catch (FileNotFoundException e1) {
	    			e1.printStackTrace();
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}finally{
	    			try {
	    				if(bis != null){
	    					bis.close();
	    				}
	    				if(out != null){
	    					out.flush();
	    					out.close();
	    				}
	    			}
	    			catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    		}
			return null;
	     
	    }   

	    /**
	     * 导入设备信息
	     * @param user
	     * @param model
	     * @return
	     */
	    @RequestMapping("/importDeviceInfo.do")
	    public String importDeviceInfo(HttpServletRequest request,Model model){
	    	//服务器地址
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
			MultipartFile multipartFile = multipartRequest.getFile("deviceInfo");
			List<DeviceInfoDto> readExcelContent = null;
			if(multipartFile!=null&&!multipartFile.isEmpty()){
				String realName = multipartFile.getOriginalFilename();
				String suffix = realName.substring(realName.lastIndexOf("."));
				if(!".xlsx".equalsIgnoreCase(suffix)&&!".xls".equalsIgnoreCase(suffix)){
					super.message = "请选择正确的文件类型！";
	    			model.addAttribute("message", super.message);
	    			return INPUT;
				}
				try {
					 readExcelContent = ExcelUtils.readExcelContent(multipartFile.getInputStream(), DeviceInfoDto.class);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | InstantiationException
						| NoSuchMethodException | SecurityException
						| ParseException | IOException e) {
					e.printStackTrace();
					super.message = "解析excel文件出错！";
					model.addAttribute("message", super.message);
					return INPUT;
				}
				if(readExcelContent!=null){
					try {
						deviceBiz.saveBatch(readExcelContent);
					} catch (BizException e) {
						log.error(e.getMessage());
						e.printStackTrace();
						super.message = e.getMessage();
						model.addAttribute("message", super.message);
				    	return INPUT;

					}
				}
				
			}else{
				log.error("请选择文件！");
				model.addAttribute("message","请选择文件！");
		    	return INPUT;
			}
	    	return INPUT;
	    }
	    
		
		 /**
	     * 导出白名单
	     * @param fileName
	     * @param request
	     * @param response
	     * @return
	     */
	    @RequestMapping("/singleDownfile.do")    
	    public ModelAndView singleDownfile(HttpServletRequest request,HttpServletResponse response,String[] ids,Model model) {
	    	 ModelAndView m= new ModelAndView();
	    	try {
	    		String path = SMSManageCtrl.class.getClassLoader().getResource("").getPath();
	    		path=path.substring(0,path.indexOf("WEB-INF"));
	    		//查询所有的设备数据
				List<DeviceInfoDto> list = deviceBiz.getDeviceInfoDto(ids);
				String randomUUID = UUID.randomUUID().toString();
				randomUUID = randomUUID.replace("-", "");
				log.info("path="+path);
				String[] title = new String[]{"设备SN码","设备编号","设备型号","设备厂家","设备状态","应用版本","内存大小","存储容量","系统版本","处理器","固件版本","内核版本","备注","设备出厂日期","版本号","软件型号"};
				String[] filds =new String[]{"id","deviceNum","deviceModel","deviceFactory","deviceStatus","app_version","memory_size","storage_capacity","system_version","processor_type","firmware_version","kernal_version","remark","deviceBorn","versionNum","softwareType"};
				String temPath = ExcelUtils.exportExcel("设备信息", title, filds, list, path+"tems/"+randomUUID+".xls");
				log.info("temPath="+temPath);
				File file = new File(temPath);
		    	//获取项目根目录
//		    	String rootPath = request.getSession().getServletContext().getRealPath("/");
		    	BufferedInputStream bis = null;
		    	BufferedOutputStream out = null;
		    	try {
			        if(!file.exists()){
			        	super.message = "该文件不存在！";
						model.addAttribute("message", super.message);
			        	return new ModelAndView("/common/input");
			        }
			        long fileLength = file.length();  
			            bis = new BufferedInputStream(new FileInputStream(file));
			            out = new BufferedOutputStream(response.getOutputStream());
			            setFileDownloadHeader(request,response, "deviceInfo.xls",fileLength);
			            byte[] buff = new byte[1024];
			            while (true) {
			              int bytesRead;
			              if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
			                  break;
			              }
			              out.write(buff, 0, bytesRead);
			            }
			//            file.deleteOnExit();
			            m.addObject("file", file);
			        }
		        catch (IOException e) {
					e.printStackTrace();
		        }
		        finally{
		            try {
		                if(bis != null){
		                    bis.close();
		                }
		                if(out != null){
		                    out.flush();
		                    out.close();
		                }
		            }
		            catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
		            }
		        }
				
			} catch (BizException e) {
				e.printStackTrace();
			}
	    	
	    	return m;
	    }
	    
	    /**
	     * 激活设备
	     * @param ids
	     * @param model
	     * @return
	     */
		@RequestMapping("/setLive.do")
		@ResponseBody
		public String setLive(String[] ids,Model model){
			if(ids!=null&&ids.length>0){
				try {
					deviceBiz.setLive(ids);
				} catch (Exception e) {
					super.message =  "激活设备出错";
				}
			}
			return super.message;
		}
}
