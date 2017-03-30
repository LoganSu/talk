package com.youlb.controller.access;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.access.IPermissionBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.CardInfo;
import com.youlb.entity.access.CardRecord;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: CardrecordCtrl.java 
 * @Description: app开锁记录
 * @author: Pengjy
 * @date: 2016-3-6
 * 
 */
@Controller
@RequestMapping("/mc/appRecord")
public class AppRecordCtrl extends BaseCtrl {

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
	public Map<String, Object> showList(CardRecord cardRecord){
		List<CardRecord> list = new ArrayList<CardRecord>();
		try {
			list = permissionBiz.appRecordList(cardRecord,getLoginUser());
		} catch (Exception e) {
			//TODO log
		}
		return setRows(list);
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
}
