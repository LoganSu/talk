package com.youlb.controller.SMSManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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

import com.youlb.biz.SMSManage.ISMSManageBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.SMSManage.SMSManage;
import com.youlb.entity.SMSManage.SMSWhiteList;
import com.youlb.utils.common.ExcelUtils;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: IPManageCtrl.java 
* @Description: 短信网关配置控制器  
* @author: Pengjy
* @date: 2016年9月1日
*
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/SMSManage")
public class SMSManageCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(SMSManageCtrl.class);

	@Autowired
	private ISMSManageBiz SMSManageBiz;
	public void setSMSManageBiz(ISMSManageBiz sMSManageBiz) {
		SMSManageBiz = sMSManageBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(SMSManage SMSManage){
		List<SMSManage> list = new ArrayList<SMSManage>();
		try {
			list = SMSManageBiz.showList(SMSManage,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,Model model){
    	if(ids!=null&&ids.length>0){
			try {
				SMSManage SMSManage = SMSManageBiz.get(ids[0]);
				model.addAttribute("SMSManage", SMSManage);
			} catch (BizException e) {
				log.error("获取单个数据失败");
				e.printStackTrace();
			}
    	}
   		return "/SMSManage/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    public String save(HttpServletRequest request,SMSManage SMSManage,Model model){
    	if(StringUtils.isBlank(SMSManage.getIp())||!RegexpUtils.checkIpAddress(SMSManage.getIp())){
    		super.message = "请填写正确的ip地址！";
    		model.addAttribute("message", super.message);
			return INPUT;
    	}
    	if(SMSManage.getPort()!=null){
    		if(SMSManage.getPort()>10000||!RegexpUtils.checkNumber(SMSManage.getPort()+"")){
    			super.message = "请填写正确的端口！";
    			model.addAttribute("message", super.message);
    			return INPUT;
    		}
    	}else{
    		super.message = "端口不能为空！";
			model.addAttribute("message", super.message);
			return INPUT;
    	}
    	
    	if(StringUtils.isBlank(SMSManage.getUsername())){
    		super.message = "用户名不能为空！";
    		model.addAttribute("message", super.message);
			return INPUT;

    	}
        if(StringUtils.isBlank(SMSManage.getPwd())){
        	super.message = "密码不能为空！";
        	model.addAttribute("message", super.message);
			return INPUT;

    	}
        if(StringUtils.isBlank(SMSManage.getSign())){
        	super.message = "公司签名不能为空！";
        	model.addAttribute("message", super.message);
			return INPUT;

    	}
        
      //服务器地址
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile multipartFile = multipartRequest.getFile("SMSManageWhiteFile");
		List<SMSWhiteList> readExcelContent = null;
		if(multipartFile!=null&&!multipartFile.isEmpty()){
			String realName = multipartFile.getOriginalFilename();
			String suffix = realName.substring(realName.lastIndexOf("."));
			if(!".xlsx".equalsIgnoreCase(suffix)&&!".xls".equalsIgnoreCase(suffix)){
				super.message = "请选择正确的文件类型！";
    			model.addAttribute("message", super.message);
    			return INPUT;
			}
			try {
				 readExcelContent = ExcelUtils.readExcelContent(multipartFile.getInputStream(), SMSWhiteList.class);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | InstantiationException
					| NoSuchMethodException | SecurityException
					| ParseException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
		     SMSManageBiz.saveOrUpdate(SMSManage,getLoginUser(),readExcelContent);
		} catch (BizException e) {
			super.message =  e.getMessage();
			if(RegexpUtils.checkChinese(super.message)){
				model.addAttribute("message", super.message);
			}else{
				model.addAttribute("message", "操作失败");
			}
			return INPUT;
		}

    	return INPUT;
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
				SMSManageBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
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
    @RequestMapping("/singleDownPhone.do")    
    public ModelAndView download(HttpServletRequest request,HttpServletResponse response) {
    		String path = SMSManageCtrl.class.getClassLoader().getResource("").getPath(); 
    		File file = new File(path+"resource/smsWhiteList.xls");
    		long fileLength = file.length();  
    		BufferedInputStream bis = null;
    		BufferedOutputStream out = null;
    		try {
    			response = setFileDownloadHeader(request,response, "smsWhiteList.xls",fileLength);
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
	 * 设置让浏览器弹出下载对话框的Header.
	 * 根据浏览器的不同设置不同的编码格式  防止中文乱码
	 * @param fileName 下载后的文件名.
	 */
	public HttpServletResponse setFileDownloadHeader(HttpServletRequest request,HttpServletResponse response, String fileName,long fileLength) {
		try {
		    //中文文件名支持
		    String encodedfileName = null;
		    String agent = request.getHeader("USER-AGENT");
		    if(null != agent && -1 != agent.indexOf("MSIE")){//IE
		        encodedfileName = java.net.URLEncoder.encode(fileName,"UTF-8");
		    }else if(null != agent && -1 != agent.indexOf("Mozilla")){
		        encodedfileName = new String (fileName.getBytes("UTF-8"),"iso-8859-1");
		    }else{
		        encodedfileName = java.net.URLEncoder.encode(fileName,"UTF-8");
		    }
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");
		    response.setContentType("application/octet-stream");
		    response.setHeader("Content-Length", String.valueOf(fileLength));
		    response.setContentType("text/x-plain");
		    return response;
		} catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		}
		return response;
	}
	
	 /**
     * 
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/singleDownfile.do")    
    public ModelAndView singleDownfile(HttpServletRequest request,HttpServletResponse response,String id,Model model) {
    	 ModelAndView m= new ModelAndView();
    	try {
    		String path = SMSManageCtrl.class.getClassLoader().getResource("").getPath().substring(1); 
			List<SMSWhiteList> list = SMSManageBiz.getWhiteListBySMSMangeId(id);
			String randomUUID = UUID.randomUUID().toString();
			randomUUID = randomUUID.replace("-", "");
			String temPath = ExcelUtils.exportExcel("smsWhiteList.xls", new String[]{"电话号码"}, new String[]{"phone"}, list, path+"tem/"+randomUUID+".xls");
			File file = new File(temPath);
	    	//获取项目根目录
//	    	String rootPath = request.getSession().getServletContext().getRealPath("/");
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
		            setFileDownloadHeader(request,response, "smsWhiteList.xls",fileLength);
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
}
