package com.youlb.controller.appManage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
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

import com.youlb.biz.appManage.IAppManageBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.appManage.AppManage;
import com.youlb.utils.common.ApkUtil;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: AppManage.java 
 * @Description: app管理 
 * @author: Pengjy
 * @date: 2015-11-26
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/appManage")
public class AppManageCtrl extends BaseCtrl {
	
	private static Logger log = LoggerFactory.getLogger(AppManageCtrl.class);
	@Autowired
    private IAppManageBiz appManageBiz;
	public void setAppManageBiz(IAppManageBiz appManageBiz) {
		this.appManageBiz = appManageBiz;
	}
	
//	@Override
//	public String search(String modulePath, String appType, Model model) {
//		model.addAttribute("appType", appType);
//		return modulePath+"/search";
//	}
	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(AppManage appManage){
		List<AppManage> list = new ArrayList<AppManage>();
		try {
			list = appManageBiz.showList(appManage,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,AppManage appManage,Model model){
    	String opraterType = appManage.getOpraterType();
    	if(ids!=null&&ids.length>0){
    		appManage = appManageBiz.get(ids[0]);
    		appManage.setOpraterType(opraterType);
    	}
    	if(SysStatic.two.equals(appManage.getAppType())){
    		List<AppManage> appList = appManageBiz.getOldVersion();
    		model.addAttribute("appList",appList);
    	}
    	//获取父节点
//    	if(StringUtils.isNotBlank(domain.getParentId())){
//    		Domain parentDomain = domainBiz.get(domain.getParentId());
//    		domain.setParentName(parentDomain.getName());//父节点名称
//    		domain.setLevel(parentDomain.getLevel());//父节点等级
//    	}else{
//    		domain.setParentId("1");
//    	}
    	model.addAttribute("appManage",appManage);
   		return "/appManage/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    public String save(HttpServletRequest request,HttpSession session,AppManage appManage,Model model){
    	try {
    		if(StringUtils.isBlank(appManage.getVersionDes())){
    			super.message = "版本说明不能为空！";
    			model.addAttribute("message", super.message);
    			return INPUT;
    		}else{
    			//过滤特殊字符
        		for(String s:SysStatic.SPECIALSTRING){
        			if(appManage.getVersionDes().contains(s)){
        				super.message="您提交的相关表单数据字符含有非法字符!";
        				request.setAttribute("message", message);
        				return INPUT;
        			}
        		}
    		}
    		List<String> treecheckbox = appManage.getTreecheckbox();
    		if(treecheckbox!=null&&treecheckbox.size()!=1){
    			super.message = "请选择一个域发布信息！";
    			model.addAttribute("message", super.message);
    			return INPUT;
    		}
//    		if(SysStatic.one.equals(appManage.getAppType())){
//    			if(appManage.getTreecheckbox()==null||appManage.getTreecheckbox().isEmpty()){
//					super.message = "请选择指定区域升级";
//					model.addAttribute("message", super.message);
//					return INPUT;
//				}
//    		}
    		
    		// Create a factory for disk-based file items  
//            FileItemFactory factory = new DiskFileItemFactory();  
//    		ServletFileUpload upload = new ServletFileUpload(factory);
//    		FileUploadProgressListener listener = new FileUploadProgressListener(session);
//    		upload.setProgressListener(listener);
    		
    		
    		//http://localhost:8080
//    		System.out.println(strBackUrl);
//    		String requestURI = request.getRemoteAddr();
//    		System.out.println(requestURI);
//    		InetAddress localHost = InetAddress.getLocalHost();
//    		String hostAddress = localHost.getHostAddress();
//    		System.out.println(hostAddress);
//    		String localAddr = SysStatic.FILEUPLOADPATH;
//    		System.out.println(localAddr);
//    		String servletPath = request.getServletPath();
//    		System.out.println(servletPath);
    		//服务器地址
			String strBackUrl = "http://" + SysStatic.FILEUPLOADIP+ request.getContextPath();      //项目名称  
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
    		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
    		MultipartFile appIcon = multipartRequest.getFile("appIcon");
    		//app存储名称
    		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
    		String appSaveName =sd.format(new Date());
    		String name = multipartFile.getOriginalFilename();
			String suffix = name.substring(name.indexOf("."));
			if(!".apk".equalsIgnoreCase(suffix)){
				super.message = "请选择正确的App类型！";
    			model.addAttribute("message", super.message);
    			return INPUT;
			}
			appSaveName+=suffix;
    		appManage.setAppSaveName(appSaveName);
    		long fileSize = 0;
//    		if(StringUtils.isBlank(appManage.getVersionNum())){
//    			super.message = "软件版本不能为空！";
//    			model.addAttribute("message", super.message);
//    			return INPUT; 
//    		}
    		//MD5
    		String md5 = SHAEncrypt.fileMd5(multipartFile);
    		appManage.setMd5Value(md5);
    		//添加的时检查版本是否已经存在
    		if(StringUtils.isBlank(appManage.getId())){
    			boolean b = appManageBiz.checkVersion(md5);
    			if(b){
    				super.message = "软件版本已经存在！";
    				model.addAttribute("message", super.message);
    				return INPUT;
    			}
    		}
    		if(multipartFile!=null&&!multipartFile.isEmpty()) {
//    			session.setAttribute("file", multipartFile);
    			fileSize = multipartFile.getSize();
    			//相对路径
    			String relativePath ="";
    			//手机app路径
    			if(SysStatic.two.equals(appManage.getAppType())){
    				relativePath=SysStatic.APPDIR+SysStatic.PHONE+appSaveName;
    			//门口机app路径
    			}else if(SysStatic.one.equals(appManage.getAppType())){
    				relativePath=SysStatic.APPDIR+SysStatic.DEVICE+appSaveName;
    			}else if(SysStatic.three.equals(appManage.getAppType())){
    				relativePath=SysStatic.APPDIR+SysStatic.MANAGE_DEVICE+appSaveName;
    			}else if(SysStatic.five.equals(appManage.getAppType())){
    				relativePath=SysStatic.APPDIR+SysStatic.MANAGEMENT+appSaveName;
    			}else if(SysStatic.six.equals(appManage.getAppType())){
    				relativePath=SysStatic.APPDIR+SysStatic.OTHERAPP+appSaveName;
    			}
    			if(appIcon!=null&&!appIcon.isEmpty()){
    				String iconRealName = appIcon.getOriginalFilename();
        			String iconSuffix = iconRealName.substring(iconRealName.indexOf("."));
        			if(!".jpg".equalsIgnoreCase(iconSuffix)&&!".jpeg".equalsIgnoreCase(iconSuffix)&&!".png".equalsIgnoreCase(iconSuffix)){
        				super.message = "图标请选择.jpg、.jpeg、.png文件类型！";
            			model.addAttribute("message", super.message);
            			return INPUT;
        			}
        			String  iconName = sd.format(new Date())+iconSuffix;
        			String iconPath = SysStatic.APPDIR+SysStatic.OTHERAPP;
        			appManage.setIconUrl(strBackUrl+iconPath.substring(iconPath.indexOf("appDir")-1)+iconName);
        			File file = new File(iconPath+iconName);
    				if(!file.exists()){
    					file.mkdirs();
    				}
    				appIcon.transferTo(file);//上传图标
        		}
    			
    			log.info("服务器地址 ： http://" + SysStatic.FILEUPLOADIP );
    			appManage.setServerAddr(strBackUrl);
    			appManage.setRelativePath(relativePath.substring(relativePath.indexOf("appDir")-1));//文件相对路径
				File file = new File(relativePath);
				if(!file.exists()){
					file.mkdirs();
				}
//				session.setAttribute("file", file);
				multipartFile.transferTo(file);//上传文件
				
				String[] unZip = ApkUtil.unZip(file);
    			appManage.setVersionName(unZip[0]);//apk文件版本名称 1.1.1
    			appManage.setPackageName(unZip[1]);//包名
    			appManage.setVersionCode(unZip[2]);//apk文件版本号
				if(fileSize!=0){
					fileSize = fileSize/1024;
				}
				appManage.setAppSize(fileSize);
				appManageBiz.saveOrUpdate(appManage,getLoginUser());
    		}else{
    			super.message = "请选择上传app！";
    			model.addAttribute("message", super.message);
    			return INPUT;
    		}
//    		super.message = "app上传成功！";
		} catch (BizException e) {
			super.message = "操作失败！";
			e.printStackTrace();
		} catch (IllegalStateException e) {
			super.message = "app上传失败！";
			e.printStackTrace();
		} catch (IOException e) {
			super.message = "app上传失败！";
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 model.addAttribute("message", super.message);
    	 return INPUT;
    }
    
    @RequestMapping("/progress.do")
    @ResponseBody
    public Progress initCreateInfo(HttpSession session) {
    	System.out.println(session.getId());
        Progress status = (Progress) session.getAttribute("upload_ps");
        return status;
    }
    /**
     * 
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/singleDown.do")    
    public ModelAndView download(HttpServletRequest request,HttpServletResponse response,String id,Model model) {
    	//获取项目根目录
//    	String rootPath = request.getSession().getServletContext().getRealPath("/");
    	AppManage appManage = appManageBiz.get(id);
    	String fileName=appManage.getVersionName()+".apk";
        File file = new File(SysStatic.APPDIR.substring(0,SysStatic.APPDIR.indexOf("appDir")-1)+appManage.getRelativePath());
        if(!file.exists()){
        	super.message = "该文件不存在！";
			model.addAttribute("message", super.message);
        	return new ModelAndView("/common/input");
        }
        long fileLength = file.length();  
        BufferedInputStream bis = null;
        BufferedOutputStream out = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            setFileDownloadHeader(request,response, fileName,fileLength);
            byte[] buff = new byte[1024];
            while (true) {
              int bytesRead;
              if (-1 == (bytesRead = bis.read(buff, 0, buff.length))){
                  break;
              }
              out.write(buff, 0, bytesRead);
            }
//            file.deleteOnExit();
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
		return null;
     
    }   
    
    /**
     * 设置让浏览器弹出下载对话框的Header.
     * 根据浏览器的不同设置不同的编码格式  防止中文乱码
     * @param fileName 下载后的文件名.
     */
    public void setFileDownloadHeader(HttpServletRequest request,HttpServletResponse response, String fileName,long fileLength) {
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
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
				appManageBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
				e.printStackTrace();
			}
		}
		return super.message;
	}
	
	  /** 
     * process 获取进度 
     * @param request 
     * @param response 
     * @return 
     * @throws Exception 
      
    @RequestMapping(value = "/process.json", method = RequestMethod.GET)  
    @ResponseBody  
    public Object process(HttpServletRequest request,HttpServletResponse response) throws Exception {  
        return ( ProcessInfo)request.getSession().getAttribute("proInfo");  
    }  	*/ 

}
