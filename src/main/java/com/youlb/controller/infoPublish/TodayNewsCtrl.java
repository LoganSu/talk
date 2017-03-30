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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.infoPublish.ITodayNewsBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.infoPublish.TodayNews;
import com.youlb.utils.common.QiniuUtils;
import com.youlb.utils.common.Utils;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: TodayNewsCtrl.java 
 * @Description: 今日头条 
 * @author: Pengjy
 * @date: 2016-5-11
 * 
 */
@Controller
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
    		try {
				todayNews = todayNewsBiz.get(ids[0]);
	    		//如果是全部推送类型不需要返回标签
	    		if("1".equals(todayNews.getSendType())){
	    			todayNews.setTreecheckbox(null);
	    		}else if("2".equals(todayNews.getSendType())){
	    			//选择区域发送处理数据回显
					List<String> parentIds = todayNewsBiz.getParentIds(ids[0]);
					model.addAttribute("parentIds",parentIds);
	    		}
    		} catch (BizException e1) {
				log.error("获取数据失败");
				e1.printStackTrace();
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
//					infoDetail=infoDetail.replaceAll("&nbsp;", " ");
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
    @ResponseBody
    public String save(HttpServletRequest request,TodayNews todayNews,Model model){
    	//标题不能为空
    	if(StringUtils.isBlank(todayNews.getTitle())){
    		super.message="标题不能为空";
//    		model.addAttribute("message", super.message);
        	return super.message;
    	}
    	//内容不能为空
    	if(StringUtils.isBlank(todayNews.getTodayNewsDetailEditor())){
    		super.message="内容不能为空";
    		return super.message;
    	}
    	if("2".equals(todayNews.getSendType())){
    		List<String> treecheckbox = todayNews.getTreecheckbox();
    		if(treecheckbox==null||treecheckbox.size()!=1){
    			super.message = "请选择一个域发布信息！";
    			return  super.message;
    		}
    	}
    	FileReader in = null;
    	BufferedReader br=null;
    	FileWriter out=null;
    	BufferedWriter bw=null;
    	File file =null;
			try {
	    		String detail = todayNews.getTodayNewsDetailEditor();
//	    		detail = detail.replaceAll(" ", "&nbsp;");
	    		String rootPath = request.getSession().getServletContext().getRealPath("/");
	    		//拷贝原始的html文件
	    		in = new FileReader(new File(rootPath+"origin.html"));
	    		br = new BufferedReader(in);
	    		String path = TodayNewsCtrl.class.getClassLoader().getResource("").getPath();
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
	    				log.info(detail);
	    				bw.write("\r\n");
	    			}
	    		}
	    		bw.flush();
//	            m.addObject("file", file);
	            int code = QiniuUtils.upload(htmlPath, "web/todayNews/html/"+htmlFileName);
	            if(code==200){
	    		   todayNews.setNewsUrl(QiniuUtils.URL+"web/todayNews/html/"+htmlFileName);
	    		   todayNewsBiz.saveOrUpdate(todayNews,getLoginUser());
	            }else{
	            	super.message="上传文件到七牛失败";
	            }
			} catch (ParseException e) {
				super.message="发布头条出错";
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				super.message="发布头条出错";
				e.printStackTrace();
			} catch (IOException e) {
				super.message="发布头条出错";
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				super.message="发布头条出错";
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				super.message="发布头条出错";
				e.printStackTrace();
			} catch (JsonException e) {
				super.message="发布头条出错";
				e.printStackTrace();
			} catch (BizException e) {
				super.message="发布头条出错";
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
			return super.message;
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
