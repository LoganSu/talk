package com.youlb.controller.management;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.youlb.biz.infoPublish.IAdPublishBiz;
import com.youlb.biz.management.IAboutNeighborhoodsBiz;
import com.youlb.biz.staticParam.IStaticParamBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.controller.infoPublish.AdPublishCtrl;
import com.youlb.entity.infoPublish.AdPublishPicture;
import com.youlb.entity.management.AboutNeighborhoods;
import com.youlb.entity.management.AboutNeighborhoodsRemark;
import com.youlb.entity.staticParam.StaticParam;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.common.Utils;
import com.youlb.utils.exception.BizException;
@Controller
@Scope("prototype")
@RequestMapping("/mc/aboutNeighborhoods")
public class AboutNeighborhoodsCtrl extends BaseCtrl {
	
	private static Logger log = LoggerFactory.getLogger(AboutNeighborhoodsCtrl.class);

	@Autowired
	private IAboutNeighborhoodsBiz aboutNeighborBiz;
	public void setAboutNeighborBiz(IAboutNeighborhoodsBiz aboutNeighborBiz) {
		this.aboutNeighborBiz = aboutNeighborBiz;
	}
	@Autowired
	private IStaticParamBiz staticParamBiz;
	public void setStaticParamBiz(IStaticParamBiz staticParamBiz) {
		this.staticParamBiz = staticParamBiz;
	}
	@Autowired
    private IAdPublishBiz adPublishBiz;
	public void setAdPublishBiz(IAdPublishBiz adPublishBiz) {
		this.adPublishBiz = adPublishBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(AboutNeighborhoods aboutNeighborhoods){
		List<AboutNeighborhoods> list = new ArrayList<AboutNeighborhoods>();
		try {
			list = aboutNeighborBiz.showList(aboutNeighborhoods,getLoginUser());
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
   	public String toSaveOrUpdate(HttpServletRequest request,String[] ids,String neighborDomainId,Model model){
    	model.addAttribute("neighborDomainId", neighborDomainId);
//    	List<StaticParam> iconList = staticParamBiz.getParamByLikeKey("neighborhoods_icon",19);
//    	if(iconList!=null&&!iconList.isEmpty()){
//    		//获取服务器ip拼接url
//			String http = "http://"+ SysStatic.FILEUPLOADPATH+ request.getContextPath(); //项目名称  ;
//    		for(StaticParam staticParam :iconList){
//    			staticParam.setFvalue(http+"/icon/"+staticParam.getFvalue());
//    		}
//    		model.addAttribute("iconList", iconList);
//    	}
    	if(ids!=null&&ids.length>0){
    		AboutNeighborhoods aboutNeighborhoods = aboutNeighborBiz.get(ids[0]);
    		String urlStr = aboutNeighborhoods.getHtmlUrl();
    		if(StringUtils.isNotBlank(urlStr)){
    			try {
					URL url = new URL(urlStr);
					URLConnection openConnection = url.openConnection();
					InputStream in = openConnection.getInputStream();
					//字节流转字符流
					InputStreamReader isr = new InputStreamReader(in);
					BufferedReader bf = new BufferedReader(isr);
					String str = null;
					StringBuilder sb = new StringBuilder();
					while((str=bf.readLine())!=null){
						sb.append(str);
					}
					//截取body里面的内容
					int start = sb.indexOf("<body>");
					int end = sb.indexOf("</body>");
					//获取内容
					String infoDetail = sb.substring(start+6, end);
					request.setAttribute("infoDetail", infoDetail);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		model.addAttribute("aboutNeighborhoods",aboutNeighborhoods);
    	}
   		return "/aboutNeighborhoods/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    public String save(HttpServletRequest request,AboutNeighborhoods aboutNeighborhoods,Model model){
        	//标题不能为空
        	if(StringUtils.isBlank(aboutNeighborhoods.getHeadline())){
        		super.message="栏目标题不能为空";
        		request.setAttribute("message", message);
        		return INPUT;
        	}else{
        		//过滤特殊字符
        		for(String s:SysStatic.SPECIALSTRING){
        			if(aboutNeighborhoods.getHeadline().length()>4){
        				super.message="栏目标题不能超过4个字符";
        				request.setAttribute("message", message);
        				return INPUT;
        			}
        			if(aboutNeighborhoods.getHeadline().contains(s)){
        				super.message="您提交的相关表单数据字符含有非法字符!";
        				request.setAttribute("message", message);
        				return INPUT;
        			}
        		}
        	}
        	if(StringUtils.isBlank(aboutNeighborhoods.getAboutNeighborhoodsDetail())){
        		super.message="内容不能为空";
        		request.setAttribute("message", message);
        		return INPUT;
        	}
        	FileReader in = null;
        	BufferedReader br=null;
        	FileWriter out=null;
        	BufferedWriter bw=null;
        	try {
    				//把映射目录全部加上访问地址
    				String http = "http://"+ SysStatic.FILEUPLOADIP+ request.getContextPath(); //项目名称  ;
//    				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
//    	    		MultipartFile multipartFile = multipartRequest.getFile("icon");
//    	    		if(multipartFile!=null&&!multipartFile.isEmpty()) {
//    	    			String fileName = Utils.dateToString(new Date(), "yyyyMMddHHmmss");
//    	    			String dir = fileName.substring(0,6);
//    	    			//获取上传文件的后缀
//    	    			String riginalFilename = multipartFile.getOriginalFilename();
//    	    			String suffix = riginalFilename.substring(riginalFilename.lastIndexOf("."), riginalFilename.length());
//    	    			//文件名按时间命名
//    	    			String relativePath= SysStatic.TODAYNEWS+dir+"/"+fileName+suffix;
//    	    			File file = new File(relativePath);
//    	    			if(!file.exists()){
//    	    				file.mkdirs();
//    	    			}
//    	    			multipartFile.transferTo(file);
//    	    			aboutNeighborhoods.setIcon(http+relativePath.substring(relativePath.indexOf("aboutNeighborhoods")-1));
//    	    		}
    	    		String detail = aboutNeighborhoods.getAboutNeighborhoodsDetail();
    	    		String ediorFile = http + "/ediorFile/";
    	    		//kindeditor 默认把url前面的  http://192.168.1.231:8080去掉了 所以要加上
    	    		detail = detail.replaceAll("/ediorFile/", ediorFile);
    	    		String rootPath = request.getSession().getServletContext().getRealPath("/");
    	    		//拷贝原始的html文件
    	    		
						in = new FileReader(new File(rootPath+"origin.html"));
					
    	    		br = new BufferedReader(in);
    	    		String htmlTargetPath = SysStatic.ABOUTNEIGHBORHOODS+Utils.dateToString(new Date(), "yyyyMM");
    	    		//创建目录
    	    		File dir = new File(htmlTargetPath);
    	    		if(!dir.exists()){
    	    			dir.mkdirs();
    	    		}
    	    		//创建文件
    	    		String htmlPath = htmlTargetPath+"/"+Utils.dateToString(new Date(), "yyyyMMddHHmmss")+".html";
    	    		File file = new File(htmlPath);
    	    		if(!file.exists()){
    	    			file.createNewFile();
    	    		}
    	    		out = new FileWriter(file);
    	    		bw= new BufferedWriter(out);
    	    		String line = null;
    	    		while((line=br.readLine())!=null){
    	    			bw.write(line);
    	    			//添加html内容
    	    			if("<body>".equals(line)){
    	    				bw.write("\r\n");
    	    				bw.write(detail);
    	    				bw.write("\r\n");
    	    			}
    	    		}
    	    		bw.flush();
    	    		aboutNeighborhoods.setHtmlUrl(http+htmlPath.substring(htmlPath.indexOf("aboutNeighborhoods")-1));
    		        aboutNeighborBiz.saveOrUpdate(aboutNeighborhoods,getLoginUser());
        	} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(br!=null){
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(bw!=null){
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out!=null){
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
    	 return  INPUT;
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
				aboutNeighborBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	@Override
	public String showPage(Model model) {
		 
		return super.showPage(model);
	}
	
	
	/**
     * 向上箭头排序
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/orderUp.do")
	@ResponseBody
	public String orderUp(AboutNeighborhoods aboutNeighborhoods,Model model){
		//检查是否已经是最小值
		int minOrder = aboutNeighborBiz.getMinOrder(aboutNeighborhoods);
		if(aboutNeighborhoods.getForder()==minOrder){
			 super.message="亲，已经到顶了";
			return super.message;
		}
		aboutNeighborBiz.orderUp(aboutNeighborhoods);
		return super.message;
	}
	/**
     * 向下箭头排序
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/orderDown.do")
	@ResponseBody
	public String orderDown(AboutNeighborhoods aboutNeighborhoods,Model model){
		//检查是否已经是最大值
		int minOrder = aboutNeighborBiz.getMaxOrder(aboutNeighborhoods);
		if(aboutNeighborhoods.getForder()==minOrder){
			 super.message="亲，已经到底了";
			return super.message;
		}
		aboutNeighborBiz.orderDown(aboutNeighborhoods);
		return super.message;
	}
	
	
	/**
     * 跳转到备注页面
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/toRemarkPage.do")
	public String toRemarkPage(AboutNeighborhoods aboutNeighborhoods,Model model){
		model.addAttribute("aboutNeighborhoods", aboutNeighborhoods);
		return "/aboutNeighborhoods/toRemarkPage";
	}
	
	/**
     * 状态更新
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/updateCheck.do")
	@ResponseBody
	public String updateCheck(AboutNeighborhoods aboutNeighborhoods,Model model){
		//检查是否已经是最大值
		try{
			int i = aboutNeighborBiz.updateCheck(aboutNeighborhoods,getLoginUser());
		}catch(BizException e){
			if(RegexpUtils.checkChinese(e.getMessage())){
				super.message = e.getMessage();
			}else{
				e.printStackTrace();
				super.message ="操作失败";
			}
		}
//		System.out.println(i);
		return super.message;
	}
	/**
     * 预览显示html页面
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/showHtml.do")
	public String showHtml(HttpServletRequest request,String id,Model model){
		if(StringUtils.isNotBlank(id) ){
			AboutNeighborhoods aboutNeighborhoods = aboutNeighborBiz.get(id);
			String urlStr = aboutNeighborhoods.getHtmlUrl();
    		if(StringUtils.isNotBlank(urlStr)){
    			try {
					URL url = new URL(urlStr);
					URLConnection openConnection = url.openConnection();
					InputStream in = openConnection.getInputStream();
					//字节流转字符流
					InputStreamReader isr = new InputStreamReader(in);
					BufferedReader bf = new BufferedReader(isr);
					String str = null;
					StringBuilder sb = new StringBuilder();
					while((str=bf.readLine())!=null){
						sb.append(str);
					}
					//截取body里面的内容
					int start = sb.indexOf("<body>");
					int end = sb.indexOf("</body>");
					//获取内容
					String infoDetail = sb.substring(start+6, end);
					request.setAttribute("infoDetail", infoDetail);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
		}
		return "/aboutNeighborhoods/showHtml";
	}
	
	
	/**
     * 审核记录列表
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/showRemark.do")
	public String showRemark(AboutNeighborhoods aboutNeighborhoods,Model model){
		model.addAttribute("aboutNeighborhoods", aboutNeighborhoods);
		return "/aboutNeighborhoods/showRemark";
	}
	

	/**
	 * 显示备注table数据
	 * @return
	 */
	@RequestMapping("/showRemarkList.do")
	@ResponseBody
	public  Map<String, Object> showRemarkList(AboutNeighborhoods aboutNeighborhoods){
		List<AboutNeighborhoodsRemark> list = new ArrayList<AboutNeighborhoodsRemark>();
		try {
			list = aboutNeighborBiz.showRemarkList(aboutNeighborhoods);
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	/**
     * 跳转到添加首页图片页面
     * @param id
     * @param model
     * @return
     */
	@RequestMapping("/toAddFirstPage.do")
	public String toAddFirstPage(AboutNeighborhoods aboutNeighborhoods,Model model){
		if(StringUtils.isNotBlank(aboutNeighborhoods.getNeighborDomainId())&&!"undefined".equals(aboutNeighborhoods.getNeighborDomainId())){
			List<AdPublishPicture> picList = adPublishBiz.getPicByAdpublishId(aboutNeighborhoods.getNeighborDomainId());
			model.addAttribute("picList", picList);
		}
		return "/aboutNeighborhoods/toAddFirstPage";
	}
    /**
     * 上传文件
     * @param request
     * @param model
     * @return
     * @throws IOException 
     */
    @RequestMapping("/uploadFile.do")
    public void upoadFile(HttpServletRequest request,HttpServletResponse resp,AdPublishPicture adPic,Model model) throws IOException{
//    	AdPublishPicture adPic = new AdPublishPicture();
//    	System.out.println(adPic);
    	//获取项目根目录
//    	String rootPath = request.getSession().getServletContext().getRealPath("/");
    	try {
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
    		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");
    		if(multipartFile!=null&&!multipartFile.isEmpty()) {
    			SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmss");
        		String fileName =sd.format(new Date());
        		//按月份分文件夹
        		String subDir =fileName.substring(0, 6);
    			String riginalFilename = multipartFile.getOriginalFilename();
    			BufferedImage bufferedImage = ImageIO.read(multipartFile.getInputStream());   
    			int width = bufferedImage.getWidth();   
    			int height = bufferedImage.getHeight();
    			//获取上传文件的后缀
    			String suffix = riginalFilename.substring(riginalFilename.lastIndexOf("."), riginalFilename.length());
    			//文件名按时间命名
    			String relativePath= SysStatic.ADDIR+SysStatic.PICTURE+subDir+"/"+fileName+suffix;
    			log.info("服务器地址 ： http://" + SysStatic.FILEUPLOADIP );
    			//服务器地址 
    			String strBackUrl = "http://" + SysStatic.FILEUPLOADIP         //端口号  
                        + request.getContextPath();      //项目名称  
				File file = new File(relativePath);
				if(!file.exists()){
					file.mkdirs();
				}
				multipartFile.transferTo(file);
				adPic.setServerAddr(strBackUrl);
				adPic.setRelativePath(relativePath.substring(relativePath.indexOf("adDir")-1));//文件相对路径
				adPic.setId(adPublishBiz.addPicture(adPic));
    		}
    		resp.setContentType("text/html");
    		resp.getWriter().write(JsonUtils.toJson(adPic)); 
    	} catch (BizException e) {
			super.message = "操作失败！";
			e.printStackTrace();
		} catch (IllegalStateException e) {
			super.message = "媒体文件上传失败！";
			e.printStackTrace();
		} catch (IOException e) {
			super.message = "媒体文件上传失败！";
			e.printStackTrace();
		}
//    	resp.getWriter().write("Ok");
//		return adPic;
    }
}
