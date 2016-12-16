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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.youlb.biz.access.IPermissionBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.CardInfo;
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
@Scope("prototype")
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
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
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
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
	@RequestMapping("/getKey.do")
	@ResponseBody
	public String getKey(String roomId){
		try {
			String neiborKey = domainBiz.getNeiborKey(roomId);
//			if(StringUtils.isBlank(neiborKey)){
//				return (String) servletContext.getAttribute("ic_cardKey");
//			}else{
				return neiborKey;
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
    	if(StringUtils.isNotBlank(cardInfo.getRoomId())){
			try {
				String address = permissionBiz.findAddressByRoomId(cardInfo.getRoomId());
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
			if(StringUtils.isNotBlank(cardInfo.getRoomId())){
				String address = permissionBiz.findAddressByRoomId(cardInfo.getRoomId());
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
    			super.message="无操作纪录";
    			return super.message;
    		}
    		permissionBiz.lossUnlossDestroy(cardInfo);
    	}catch(BizException e){
    		e.printStackTrace();
    		super.message="操作失败！";
    	} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
    	return super.message;
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
			boolean b = permissionBiz.checkCardExist(cardInf);
			if(b){
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
    	boolean b = permissionBiz.checkCardExist(cardInfo);
    	if(b){
    		super.message="1";
    		return super.message;
    	}
    		int i = permissionBiz.writeCard(cardInfo);
			super.message=i+"";
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
			super.message="2";
			return super.message;
		} 
    	return super.message;
    }
    
    
}
