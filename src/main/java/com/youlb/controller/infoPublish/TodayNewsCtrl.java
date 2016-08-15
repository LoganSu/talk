package com.youlb.controller.infoPublish;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
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
import com.youlb.biz.infoPublish.ITodayNewsBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.infoPublish.TodayNews;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.common.Utils;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: TodayNewsCtrl.java 
 * @Description: 今日头条 
 * @author: Pengjy
 * @date: 2016-5-11
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/mc/todayNews")
public class TodayNewsCtrl extends BaseCtrl {
	
	private static Logger log = LoggerFactory.getLogger(TodayNewsCtrl.class);
	@Autowired
    private ITodayNewsBiz todayNewsBiz;
	public void setTodayNewsBiz(ITodayNewsBiz todayNewsBiz) {
		this.todayNewsBiz = todayNewsBiz;
	}

	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(TodayNews todayNews){
		List<TodayNews> list = new ArrayList<TodayNews>();
		try {
			list = todayNewsBiz.showList(todayNews,getLoginUser());
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
   	public String toSaveOrUpdate(HttpServletRequest request,String[] ids,TodayNews todayNews,Model model){
    	String opraterType = todayNews.getOpraterType();
    	if(ids!=null&&ids.length>0){
    		todayNews = todayNewsBiz.get(ids[0]);
    		//如果是全部推送类型不需要返回标签
    		if("1".equals(todayNews.getSendType())){
    			todayNews.setTreecheckbox(null);
    		}
    		todayNews.setOpraterType(opraterType);
    		String urlStr = todayNews.getNewsUrl();
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
    	model.addAttribute("todayNews",todayNews);
   		return "/todayNews/addOrEdit";
   	}
    
     
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    public String save(HttpServletRequest request,TodayNews todayNews,Model model){
    	//标题不能为空
    	if(StringUtils.isBlank(todayNews.getTitle())){
    		super.message="标题不能为空";
    		request.setAttribute("message", message);
    		return INPUT;
    	}else{
    		//过滤特殊字符
    		for(String s:SysStatic.SPECIALSTRING){
    			if(todayNews.getTitle().contains(s)){
    				super.message="您提交的相关表单数据字符含有非法字符!";
    				request.setAttribute("message", message);
    				return INPUT;
    			}
    		}
    	}
    	//内容不能为空
    	if(StringUtils.isBlank(todayNews.getTodayNewsDetail())){
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
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
	    		MultipartFile multipartFile = multipartRequest.getFile("picture");
	    		if(multipartFile!=null&&!multipartFile.isEmpty()) {
	    			String fileName = Utils.dateToString(new Date(), "yyyyMMddHHmmss");
	    			String dir = fileName.substring(0,6);
	    			//获取上传文件的后缀
	    			String riginalFilename = multipartFile.getOriginalFilename();
	    			String suffix = riginalFilename.substring(riginalFilename.lastIndexOf("."), riginalFilename.length());
	    			//文件名按时间命名
	    			String relativePath= SysStatic.TODAYNEWS+dir+"/"+fileName+suffix;
	    			File file = new File(relativePath);
	    			if(!file.exists()){
	    				file.mkdirs();
	    			}
	    			multipartFile.transferTo(file);
	    			todayNews.setPictureUrl(http+relativePath.substring(relativePath.indexOf("todayNews")-1));
	    		}
	    		String detail = todayNews.getTodayNewsDetail();
	    		String ediorFile = http + "/ediorFile/";
	    		//kindeditor 默认把url前面的  http://192.168.1.231:8080去掉了 所以要加上
	    		detail = detail.replaceAll("/ediorFile/", ediorFile);
	    		String rootPath = request.getSession().getServletContext().getRealPath("/");
	    		//拷贝原始的html文件
	    		in = new FileReader(new File(rootPath+"origin.html"));
	    		br = new BufferedReader(in);
	    		String htmlTargetPath = SysStatic.HTMLFILE+Utils.dateToString(new Date(), "yyyyMM");
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
	    		todayNews.setNewsUrl(http+htmlPath.substring(htmlPath.indexOf("htmlFile")-1));
	    		todayNewsBiz.saveOrUpdate(todayNews,getLoginUser());
			} catch (ParseException e) {
				super.message="发布头条出错";
				request.setAttribute("message", message);
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				super.message="发布头条出错";
				request.setAttribute("message", message);
				e.printStackTrace();
			} catch (IOException e) {
				super.message="发布头条出错";
				request.setAttribute("message", message);
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				super.message="发布头条出错";
				request.setAttribute("message", message);
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				super.message="发布头条出错";
				request.setAttribute("message", message);
				e.printStackTrace();
			} catch (JsonException e) {
				super.message="发布头条出错";
				request.setAttribute("message", message);
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
				todayNewsBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
	 /**
     * 发布
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/publish.do")
	@ResponseBody
	public String publish(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				todayNewsBiz.publish(ids,getLoginUser());
			} catch (Exception e) {
				super.message =  "发布出错";
			}
		}
		return super.message;
	}
}
