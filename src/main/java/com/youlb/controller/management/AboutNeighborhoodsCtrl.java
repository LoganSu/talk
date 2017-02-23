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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
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

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.infoPublish.IAdPublishBiz;
import com.youlb.biz.management.IAboutNeighborhoodsBiz;
import com.youlb.biz.staticParam.IStaticParamBiz;
import com.youlb.controller.SMSManage.SMSManageCtrl;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.common.Domain;
import com.youlb.entity.infoPublish.AdPublishPicture;
import com.youlb.entity.management.AboutNeighborhoods;
import com.youlb.entity.management.AboutNeighborhoodsRemark;
import com.youlb.entity.management.Department;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.QiniuUtils;
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
	
	@Autowired
	private IDomainBiz domainBiz;
	
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
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
	 * @throws BizException 
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(HttpServletRequest request,String[] ids,String neighborDomainId,Model model) throws BizException{
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
    			if(aboutNeighborhoods.getHeadline().length()>4){
    				super.message="栏目标题不能超过4个字符";
    				request.setAttribute("message", message);
    				return INPUT;
        		}
        	}
        	if(StringUtils.isBlank(aboutNeighborhoods.getAboutNeighborhoodsDetail())){
        		super.message="内容不能为空";
        		request.setAttribute("message", message);
        		return INPUT;
        	}
//        	else{
//        		//过滤特殊字符
//        		for(String s:SysStatic.SPECIALSTRING){
//        			if(aboutNeighborhoods.getAboutNeighborhoodsDetail().contains(s)){
//        				super.message="您提交的相关表单数据字符含有非法字符!";
//        				request.setAttribute("message", super.message);
//                		return INPUT;
//        			}
//        		}
//        	}
        	FileReader in = null;
        	BufferedReader br=null;
        	FileWriter out=null;
        	BufferedWriter bw=null;
        	File file =null;

        	try {
    	    		String detail = aboutNeighborhoods.getAboutNeighborhoodsDetail();
    	    		String rootPath = request.getSession().getServletContext().getRealPath("/");
    	    		//拷贝原始的html文件
					in = new FileReader(new File(rootPath+"origin.html"));
    	    		br = new BufferedReader(in);
    	    		String path = AboutNeighborhoodsCtrl.class.getClassLoader().getResource("").getPath();
    	    		path=path.substring(0,path.indexOf("WEB-INF"));
    	    		//创建文件
    	    		String htmlFileName = Utils.dateToString(new Date(), "yyyyMMddHHmmss")+".html";
    	    		String htmlPath = path+"/tems/"+htmlFileName;
    	    		 file = new File(htmlPath);
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
    	    		 int code = QiniuUtils.upload(htmlPath, "web/aboutNeigh/html/"+htmlFileName);
    		            if(code==200){
    		            	aboutNeighborhoods.setHtmlUrl(QiniuUtils.URL+"web/aboutNeigh/html/"+htmlFileName);
    		            }
    		        aboutNeighborBiz.saveOrUpdate(aboutNeighborhoods,getLoginUser());
        	} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (BizException e) {
				// TODO Auto-generated catch block
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
        	//删除临时文件
			if(file!=null){
//				System.out.println(file.delete());
				log.info("删除临时文件：："+file.delete());
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
		try {
			int minOrder = aboutNeighborBiz.getMinOrder(aboutNeighborhoods);
			if(aboutNeighborhoods.getForder()==minOrder){
				 super.message="亲，已经到顶了";
				return super.message;
			}
				aboutNeighborBiz.orderUp(aboutNeighborhoods);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			//检查是否已经是最大值
			int minOrder = aboutNeighborBiz.getMaxOrder(aboutNeighborhoods);
			if(aboutNeighborhoods.getForder()==minOrder){
				 super.message="亲，已经到底了";
				return super.message;
			}
			aboutNeighborBiz.orderDown(aboutNeighborhoods);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	 * @throws BizException 
     */
	@RequestMapping("/showHtml.do")
	public String showHtml(HttpServletRequest request,String id,Model model) throws BizException{
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
			
			try {
				List<AdPublishPicture> picList = adPublishBiz.getPicByAdpublishId(aboutNeighborhoods.getNeighborDomainId());
				model.addAttribute("picList", picList);
			} catch (BizException e) {
				log.error("获取图片列表失败");
				e.printStackTrace();
			}
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
    @ResponseBody
    public AdPublishPicture upoadFile(HttpServletRequest request,HttpServletResponse resp,AdPublishPicture adPic,Model model) throws IOException{
    	try {
				adPic.setId(adPublishBiz.addPicture(adPic));
    	} catch (IllegalStateException e) {
			super.message = "媒体文件上传失败！";
			e.printStackTrace();
		} catch (BizException e) {
			super.message = "媒体文件上传失败！";
			e.printStackTrace();
		}
    	return adPic;
    }
    
    @RequestMapping("/getNodes.do")
	@ResponseBody
	public List<HashMap<String,Object>> getNodes(String id,String name,Integer level,String nocheckLevel){
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		try {
			Domain sd = new Domain();
			sd.setParentId(id);
			List<Domain> dList = domainBiz.showList(sd,getLoginUser());
			if(dList!=null&&!dList.isEmpty()){
				for(Domain d:dList){
					HashMap<String,Object> hm = new HashMap<String,Object>();   
					hm.put("id",d.getId());//id属性  ，数据传递
					hm.put("name", d.getRemark()); //name属性，显示节点名称 
					hm.put("level", level==null?0:level+1);//设置层级
					if(StringUtils.isNotBlank(nocheckLevel)){
						if(nocheckLevel.contains(level+"")){
						   hm.put("nocheck", true);
						}
					}
					 sd = new Domain();
					sd.setParentId(d.getId());
//					List<Domain> child = domainBiz.showList(sd,getLoginUser());
					if(level==null){
						hm.put("isParent", true);
					}else{
						hm.put("isParent", false);
					}
					hm.put("pId", id);  
					
					list.add(hm);  
				}  
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
