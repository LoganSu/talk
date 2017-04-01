package com.youlb.controller.access;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.access.IPermissionBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.CardInfo;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: PermissionCtrl.java 
 * @Description: 门禁授权管理 
 * @author: Pengjy
 * @date: 2015-11-5
 * 
 */
@Controller
@RequestMapping("/mc/permission")
public class PermissionCtrl extends BaseCtrl {
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
	 * 卡片管理table数据
	 * @return
	 */
	@RequestMapping("/showList.do") 
	@ResponseBody
	public  Map<String, Object> showList(CardInfo cardInfo){
		List<CardInfo> list = new ArrayList<CardInfo>();
		try {
			list = permissionBiz.showList(cardInfo,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,CardInfo cardInfo,Model model){
    	if(ids!=null&&ids.length>0){
    		try {
				cardInfo = permissionBiz.get(ids[0]);
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
    	model.addAttribute("cardInfo",cardInfo);
   		return "/permission/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(CardInfo cardInfo,Model model){
    	try {
    		String id = permissionBiz.saveOrUpdate(cardInfo,getLoginUser());
    		//更新map的值
//    		if(id!=null){
//    			Map<String,String> domainMap = (Map<String, String>) servletContext.getAttribute("domainMap");
//    			domainMap.put(id, cardInfo.getName());
//    		}
		} catch (Exception e) {
			e.printStackTrace();
			//TODO log
			return "操作失败！";
		}
    	 return  null;
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
				permissionBiz.delete(ids);
				//删除map的值
//				Map<String,String> domainMap = (Map<String, String>) servletContext.getAttribute("domainMap");
//				for(String id:ids){
//	    			domainMap.remove(id);
//				}
			} catch (Exception e) {
				return  "删除出错";
			}
		}
		return null;
	}
	
	@RequestMapping("/getKey.do")
	@ResponseBody
	public String getKey(String domainId){
		try {
			if(StringUtils.isNotBlank(domainId)){
				String neiborKey = domainBiz.getNeiborKey(domainId);
				return neiborKey;
			}
//			if(StringUtils.isBlank(neiborKey)){
//				return (String) servletContext.getAttribute("ic_cardKey");
//			}else{
//			}
		} catch (BizException e) {
			e.printStackTrace();
		}
		return "";
	}
	 /**
     * 跳转到开卡页面
     * @return
     */
    @RequestMapping("/toOpenCard.do")
   	public String toOpenCard(CardInfo cardInfo,Model model){
    	//获取人的地址列表
    	if(StringUtils.isNotBlank(cardInfo.getDomainId())){
			try {
				String address = permissionBiz.findAddressByRoomId(cardInfo.getDomainId());
				model.addAttribute("address",address);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
//    	String ic_cardKey = (String) servletContext.getAttribute("ic_cardKey");
//    	System.out.println(ic_cardKey);
   		return "/permission/openCard";
   	}
    /**
     * 跳转挂失注销页面
     * @return
     */
    @RequestMapping("/cardInfoLossUnlossDestroy.do")
   	public String cardInfoLossUnlossDestroy(CardInfo cardInfo,Model model){
    	//获取card map  key=cardSn value=CardInfo
		try {
			Map<String, CardInfo> cardMap = permissionBiz.cardMap(cardInfo);
			//获取人的地址列表
			if(StringUtils.isNotBlank(cardInfo.getDomainId())){
				String address = permissionBiz.findAddressByRoomId(cardInfo.getDomainId());
				model.addAttribute("address",address);
			}
			model.addAttribute("cardInfo",cardInfo);
			model.addAttribute("cardMap",cardMap);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		return "/permission/cardInfoLossUnlossDestroy";
   	}
    
    /**
     * 挂失 解挂 注销统一方法
     * @param cardInfo
     * @param model
     * @return
     */
     
    @RequestMapping("/lossUnlossDestroy.do")
    @ResponseBody
    public String lossUnlossDestroy(CardInfo cardInfo,Model model){
//    	System.out.println(cardInfo);
    	try{
    		if(StringUtils.isBlank(cardInfo.getCardSn())){
    			return "无操作纪录";
    		}
    		permissionBiz.lossUnlossDestroy(cardInfo);
    	}catch(BizException e){
    		e.printStackTrace();
    		return "操作失败！";
    	} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "操作失败！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "操作失败！";
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "操作失败！";
		} catch (JsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "操作失败！";
		}
			
    	return null;
    }
    
    
    /**
     * 跳转挂失注销页面
     * @return
     */
    @RequestMapping("/cardInfoPermission.do")
   	public String cardInfoPermission(CardInfo cardInfo,Model model){
    	try {
    	List<CardInfo> addressList = permissionBiz.findAddressByCardSn(cardInfo);
    	model.addAttribute("addressList",addressList);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   		return "/permission/cardInfoPermission";
   	}
    /**
     * 连接读卡器
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/connectCardMachine.do")
    @ResponseBody
    public CardInfo connectCardMachine(CardInfo cardInf,Model model){
		try {
			CardInfo c = permissionBiz.checkCardExist(cardInf);
			if(c!=null&&!SysStatic.CANCEL.equals(c.getCardStatus())){
				return null;
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return cardInf;
    }
    
    /**
     * 写卡片信息 和信息入库
     * @param cardInfo
     * @param model
     * @return
     */
     
    @RequestMapping("/writeCard.do")
    @ResponseBody
    public String writeCard(CardInfo cardInfo,Model model){
    	//检查卡片是否已经使用
    	try {
    		CardInfo c = permissionBiz.checkCardExist(cardInfo);
    	if(c!=null){
    		//注销的卡可以重新发卡
    		if(SysStatic.CANCEL.equals(c.getCardStatus())){
    			permissionBiz.updateCardInfo(c);
    			return "0";
    		}else{
    			return "1";
    		}
    	}else{
    		cardInfo.setCardBelongs("1");//所属住户
    		int i = permissionBiz.writeCard(cardInfo);
    		return i+"";
    	}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BizException e) {
			e.printStackTrace();
			return "2";
		} 
    	return null;
    }
    
		/**
	     * 判断卡片是否已经初始化秘钥
	     * @param cardInfo
	     * @param model
	     * @return
	     */
	     
	    @RequestMapping("/isInitKey.do")
	    @ResponseBody
	    public String isInitKey(CardInfo cardInfo,Model model){
	    	if(StringUtils.isNotBlank(cardInfo.getCardSn())&&StringUtils.isNotBlank(cardInfo.getDomainId())){
	    		try {
					return	permissionBiz.isInitKey(cardInfo.getCardSn(),cardInfo.getDomainId());
				} catch (BizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}else{
	    		return "3";
	    	}
	    	return null;
    }
	    
	    
	    /**
	     * 判断是不是最后注销的卡
	     * @param cardInfo
	     * @param model
	     * @return
	     */
	     
	    @RequestMapping("/isLastCard.do")
	    @ResponseBody
	    public String isLastCard(CardInfo cardInfo,Model model){
	    	if(StringUtils.isNotBlank(cardInfo.getCardSn())){
	    		try {
					return	permissionBiz.isLastCard(cardInfo.getCardSn());
				} catch (BizException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}else{
	    		return "卡片id不能为空！";
	    	}
	    	return null;
    }
	    
}
