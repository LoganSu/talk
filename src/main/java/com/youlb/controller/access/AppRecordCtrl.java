package com.youlb.controller.access;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.access.IPermissionBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.CardInfo;
import com.youlb.entity.access.CardRecord;

/** 
 * @ClassName: CardrecordCtrl.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2016-3-6
 * 
 */
@Controller
@Scope("prototype")
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
	public CardInfo getImg(CardInfo cardInfo){
		if(cardInfo.getId()!=null){
			cardInfo = permissionBiz.getImg(cardInfo.getId());
		}
		return cardInfo;
	}
}
