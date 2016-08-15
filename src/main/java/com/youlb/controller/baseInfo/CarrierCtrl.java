package com.youlb.controller.baseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.baseInfo.ICarrierBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.Domain;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.vo.QJson;
import com.youlb.entity.vo.QTree;
import com.youlb.utils.common.RegexpUtils;

/** 
 * @ClassName: CarrierCtrl.java 
 * @Description: 运营商信息管理 
 * @author: Pengjy
 * @date: 2015年8月28日
 * 
 */
@Controller
@RequestMapping("/mc/carrier")
@Scope("prototype")
public class CarrierCtrl extends BaseCtrl {
	@Autowired
	private ICarrierBiz carrierBiz;
	@Autowired
	private IDomainBiz domainBiz;
	
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	public void setCarrierBiz(ICarrierBiz carrierBiz) {
		this.carrierBiz = carrierBiz;
	}


	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Carrier carrier){
		List<Carrier> list = new ArrayList<Carrier>();
		Operator loginUser = getLoginUser();
		list = carrierBiz.showList(carrier, loginUser);
		
		return setRows(list);
	}
	

	/**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Carrier carrier,Model model){
    	
    	if(ids!=null&&ids.length>0){
    		carrier = carrierBiz.get(ids[0]);
		}
//    	Operator loginUser = getLoginUser();
    	//获取权限列表
//    	List<Privilege> privilegeList = carrierBiz.getPrivilegeList(loginUser,role);
//    	model.addAttribute("privilegeList", privilegeList);
    	model.addAttribute("carrier", carrier);
   		return "/carrier/addOrEdit";
   	}
    
    /**
     * 保存
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String saveOrUpdate(Carrier carrier,Model model){
    	try {
	    		
    		if(StringUtils.isBlank(carrier.getCarrierName())){
    			super.message = "名称不能为空！";
   			      return  super.message;
    		}
    		
    		 if(StringUtils.isBlank(carrier.getCarrierNum())||!RegexpUtils.checkLetter(carrier.getCarrierNum(), 1, 10)){
    			 super.message = "运营商简称不能为空且最多为10个字母！";
    			 return  super.message;
    		 }
    		 //检查简称是否已经存在
    		 boolean  b = carrierBiz.checkCarrierNumExist(carrier);
    		 if(b){
    			 super.message = "该运营商简称已经存在！";
    			 return  super.message;
    		 }
    		 if(StringUtils.isBlank(carrier.getTel())){
	    			super.message = "手机号码不能为空";
	    			return  super.message;
	    		}else{
	    			if(!RegexpUtils.checkMobile(carrier.getTel())){
	    				super.message = "请填写正确的手机号码";
	    				return  super.message;
	    			}
	    		}
    		 
    		   if(StringUtils.isNotBlank(carrier.getFax())&&!RegexpUtils.checkPhone(carrier.getFax())){
	    			super.message = "请填写正确的电话号码！";
	   			      return  super.message;
	    		}
    		   if(StringUtils.isBlank(carrier.getAddress())){
    			   super.message = "地址不能为空！";
    			   return  super.message;
    		   }
//	    		if(StringUtils.isNotBlank(carrier.getPostcode())&&!RegexpUtils.checkNumber(carrier.getPostcode())||carrier.getPostcode().length()>10){
//	    			super.message = "请填写正确的邮编！";
//	   			      return  super.message;
//	    		}
	    		//创建运营商时必须绑定域对象 也是就是绑定所属的社区信息
	    		if(carrier.getTreecheckbox()==null||carrier.getTreecheckbox().isEmpty()){
	    			super.message = "请选择运营商管辖的区域，如果没有区域信息请先创建！";
	    			 return  super.message;
	    		}
    		 
	    		if(StringUtils.isBlank(carrier.getId())){
	    			//判断社区是否已经有绑定运营商
	    			String neiborName = carrierBiz.checkHasChecked(carrier.getTreecheckbox());
	    			if(StringUtils.isNotBlank(neiborName)){
	    				super.message = neiborName+"已经绑定运营商";
	    				return  super.message;
	    			}
	    		}else{
	    			//更新时查看选择的区域是否已经被别的运营商绑定
	    			Carrier c = carrierBiz.get(carrier.getId());
	    			List<String> oldDomain = c.getTreecheckbox();
	    			List<String> newDomain = new ArrayList<String>();
	    			for(String domain:carrier.getTreecheckbox()){
	    				if(!oldDomain.contains(domain)){
	    					newDomain.add(domain);
	    				}
	    			}
	    			//说明有更新
	    			if(!newDomain.isEmpty()){
	    				//判断社区是否已经有绑定运营商
	    				String neiborName1 = carrierBiz.checkHasChecked(newDomain);
	    				if(StringUtils.isNotBlank(neiborName1)){
	    					super.message = neiborName1+"已经绑定运营商";
	    					return  super.message;
	    				}
	    			}
	    		}
    		
    		
    		carrierBiz.saveOrUpdate(carrier);
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
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
	public String deleteUser(String[] ids,Model model){
		if(ids!=null&&ids.length>0){
			try {
				carrierBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	/**
     * 显示域对象
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
	public QJson domainList(Carrier carrier){
		QJson json = new QJson();
		if(StringUtils.isNotBlank(carrier.getId())){
			carrier = carrierBiz.get(carrier.getId());
		}
		//获取权限列表
		List<Domain> domainList = domainBiz.getDomainList(carrier, getLoginUser());
//    	List<Privilege> privilegeList = roleBiz.getPrivilegeList(loginUser,role);
    	QTree t = new QTree();
		t.setText("选择区域");
		t.setUrl("checkfalse");
		List<QTree> children = objToTree(domainList);
		t.setChildren(children);;
		json.setMsg("OK");
		json.setObject(t);
		json.setSuccess(true);
		json.setType("1");
   		return json;
	}
	
	 /**
     * 把集合转换成树状图数据
     * @param privilegeList
     * @return 
     */
    private List<QTree> objToTree(List<Domain> domainList){
    	 List<QTree> listTree = new ArrayList<QTree>();
    	if(domainList!=null){
    		for(Domain domain:domainList){
    			QTree tree = new QTree();
    			tree.setId(domain.getId());//id
    			tree.setText(domain.getRemark());//名称
    			tree.setChecked(domain.getChecked()==null?false:true);//是否选中
    			tree.setChildren(objToTree(domain.getChildren()));
    			tree.setLayer(domain.getLayer());
    			listTree.add(tree);
    		}
    	}
    	return listTree;
    }
	
}
