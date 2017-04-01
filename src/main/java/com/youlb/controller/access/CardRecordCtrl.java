package com.youlb.controller.access;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youlb.biz.access.IPermissionBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.SMSManage.SMSManageCtrl;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.CardInfo;
import com.youlb.entity.access.CardRecord;
import com.youlb.utils.common.ExcelUtils;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: CardrecordCtrl.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2016-3-6
 * 
 */
@Controller
@RequestMapping("/mc/cardRecord")
public class CardRecordCtrl extends BaseCtrl {
	private static Logger log = LoggerFactory.getLogger(CardRecordCtrl.class);

	@Autowired
	private IPermissionBiz permissionBiz;
    @Autowired
	private IDomainBiz domainBiz;
    @Autowired
	private ServletContext servletContext;
	public void setPermissionBiz(IPermissionBiz permissionBiz) {
		this.permissionBiz = permissionBiz;
	}
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/**
	 * 显示门禁授权table数据
	 * @return
	 */
	@RequestMapping("/showList.do") 
	@ResponseBody
	public  Map<String, Object> showList(CardRecord cardRecord){
		List<CardRecord> list = new ArrayList<CardRecord>();
		try {
			list = permissionBiz.cardRecord(cardRecord,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	
	/**
	 * 考勤管理显示table数据
	 * @return
	 */
	@RequestMapping("/checkingshowList.do") 
	@ResponseBody
	public  Map<String, Object> checkingshowList(CardRecord cardRecord){
		List<CardRecord> list = new ArrayList<CardRecord>();
		try {
			list = permissionBiz.checkingshowList(cardRecord,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
	}
	
	@Override
	public String showPage(Model model) {
		//初始化查询时间
		model.addAttribute("now", new Date());
		return super.showPage(model);
	}
	
	@RequestMapping("/getImg.do")
	@ResponseBody
	public CardInfo getImg(CardInfo cardInfo,HttpServletRequest request){
		if(cardInfo.getId()!=null){
			try {
				cardInfo = permissionBiz.getImg(cardInfo.getId());
				Date date = cardInfo.getFtime();
				Calendar c = Calendar.getInstance();
				c.setTime(date);
				c.add(Calendar.MONTH, 1);
				long l = c.getTimeInMillis();//文件生成时间 + 一个月
				long now = new Date().getTime();//当前时间
				//文件超过一个月显示本地图片 
				if(l-now<0){
					String strBackUrl = SysStatic.FILEUPLOADIP+ request.getContextPath();      //项目名称  
					cardInfo.setServeraddr(strBackUrl +"/qiniubackup");
				} 
			} catch (BizException e) {
				e.printStackTrace();
			}
		}
		return cardInfo;
	}
	
	
	 /**
     * 导出
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/singleDownfile.do")    
    public ModelAndView singleDownfile(HttpServletRequest request,HttpServletResponse response,CardRecord cardRecord,Model model) {
    	 ModelAndView m= new ModelAndView();
    	try {
    		//查询统计数据
			List<CardRecord> list = permissionBiz.checkingshowList(cardRecord, getLoginUser());
//			if(list!=null&&!list.isEmpty()){
			    String fileName=cardRecord.getStartTime().replace("-", "")+"-"+cardRecord.getEndTime().replace("-", "")+".xls";
				log.info(fileName+"数据条数"+list.size());
				String path = CardRecordCtrl.class.getClassLoader().getResource("").getPath();
	    		path=path.substring(0,path.indexOf("WEB-INF"));
	    		String[] title = new String[]{"日期","时间","姓名","工号"};
	    		String[] filds =new String[]{"fdate","ftime","fname","workerNum"};
	    		String temPath = ExcelUtils.exportExcel("考勤统计", title, filds, list, path+"tems/"+fileName);
	    		log.info("temPath="+temPath);
	    		File file = new File(temPath);
	    		//获取项目根目录
//	    	String rootPath = request.getSession().getServletContext().getRealPath("/");
	    		BufferedInputStream bis = null;
	    		BufferedOutputStream out = null;
	    		try {
//	    			if(!file.exists()){
//	    				model.addAttribute("message", "该文件不存在！");
//	    				return INPUT;
//	    			}
	    			long fileLength = file.length();  
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
	    			m.addObject("file", file);
	    		}
	    		catch (IOException e) {
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
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		}
//			}
		} catch (BizException e) {
			e.printStackTrace();
		}
    	
    	return m;
    }
}
