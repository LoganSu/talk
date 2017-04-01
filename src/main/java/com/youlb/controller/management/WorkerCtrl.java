package com.youlb.controller.management;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.access.IPermissionBiz;
import com.youlb.biz.management.IDepartmentBiz;
import com.youlb.biz.management.IWorkerBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.access.CardInfo;
import com.youlb.entity.management.DepartmentTree;
import com.youlb.entity.management.Worker;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.JsonUtils;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

@Controller
@RequestMapping("/mc/worker")
public class WorkerCtrl extends BaseCtrl{
	private static Logger log = LoggerFactory.getLogger(WorkerCtrl.class);

	@Autowired
    private IWorkerBiz workerBiz;
	public void setWorkerBiz(IWorkerBiz workerBiz) {
		this.workerBiz = workerBiz;
    }
	@Autowired
	private IDepartmentBiz departmentBiz;
	public void setDepartmentBiz(IDepartmentBiz departmentBiz) {
		this.departmentBiz = departmentBiz;
	}
	
	@Autowired
	private IPermissionBiz permissionBiz;
	public void setPermissionBiz(IPermissionBiz permissionBiz) {
		this.permissionBiz = permissionBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Worker worker){
		List<Worker> list = new ArrayList<Worker>();
		try {
			list = workerBiz.showList(worker,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,Model model){
    	if(ids!=null&&ids.length>0){
			try {
				Worker worker = workerBiz.get(ids[0]);
				model.addAttribute("worker", worker);
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    	}
		try {
			List<DepartmentTree> topList = departmentBiz.showListDepartmentTree(new DepartmentTree(),getLoginUser());
			List<DepartmentTree> children = getDepartmentList(topList,getLoginUser());
			model.addAttribute("departmentTree", JsonUtils.toJson(children));
		} catch (BizException e) {
			log.error("获取部门数据失败");
			e.printStackTrace();
		}
   		return "/worker/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Worker worker,Model model){
    	try {
	    		if(StringUtils.isBlank(worker.getDepartmentId())||worker.getDepartmentId().contains(",")){
	    			return "请选择一个部门！";
	    		}
	    		if(StringUtils.isBlank(worker.getWorkerName())){
	    			return "姓名不能为空！";
	    		}
	    		if(StringUtils.isBlank(worker.getPhone())){
	    			return "手机号码不能为空！";
	    		}else{
	    			if(!RegexpUtils.checkMobile(worker.getPhone())){
	    				return "请填写正确的手机号码！";
	    			}
	    		}
	    		//判断手机号是否已经注册
	    		boolean b = workerBiz.checkPhoneExist(worker.getPhone(),worker.getId());
	    		if(b){
	    			return "该手机号码已经被注册！";
	    		}
	    		worker.setUsername(worker.getPhone());
	    		worker.setPassword(SHAEncrypt.digestPassword(worker.getPhone()));
				workerBiz.saveOrUpdate(worker,getLoginUser());
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BizException e) {
				log.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				workerBiz.delete(ids);
			} catch (Exception e) {
				return  "删除出错";
			}
		}
		return null;
	}
	
	
	@Override
	public String showPage(Model model) {
		try {
			List<DepartmentTree> topList = departmentBiz.showListDepartmentTree(new DepartmentTree(),getLoginUser());
			List<DepartmentTree> children = getDepartmentList(topList,getLoginUser());
			model.addAttribute("departmentTree", JsonUtils.toJson(children));
		} catch (BizException e) {
			log.error("获取部门数据失败");
 			e.printStackTrace();
		}
		return super.showPage(model);
	}
	/**
	 * 递归获取权限树数据
	 * @param list
	 * @param path
	 * @return
	 */
    private List<DepartmentTree> getDepartmentList(List<DepartmentTree> list,Operator loginUser){
    	List<DepartmentTree> treeList = new ArrayList<DepartmentTree>();
    	if(list!=null&&!list.isEmpty()){
			for(DepartmentTree a:list){
				a.setParentId(a.getId());
				try {
					List<DepartmentTree> departLis = departmentBiz.showListDepartmentTree(a,loginUser);
					if(departLis!=null){
						DepartmentTree atree = new DepartmentTree();
						List<DepartmentTree> authorityList = getDepartmentList(departLis, loginUser);
						atree.setId(a.getId());
						atree.setDepartmentName(a.getDepartmentName());
						atree.setLayer(a.getLayer());
						atree.setDepartmentTree(authorityList);
						treeList.add(atree);
					}
				} catch (BizException e) {
					log.error("获取部门数据失败");
 					e.printStackTrace();
				}
			}
		}
    	return treeList;
	}
    
    /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toOpenCard.do")
   	public String toOpenCard(String workerId,Model model){
    	if(StringUtils.isNotBlank(workerId)){
			try {
				Worker worker = workerBiz.get(workerId);
				model.addAttribute("worker", worker);
			} catch (BizException e) {
				log.error("获取单条数据失败");
				e.printStackTrace();
			}
    	}
   		return "/worker/openCard";
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
        		    //检查人和卡片是否已经存在
        		    Boolean b = permissionBiz.checkWorkerCardExist(cardInfo);
        		    if(!b){
        		    	//检查卡片是否已经存在 cardInfo（一张物业卡只能发一次）
        		    	CardInfo c = permissionBiz.checkCardExist(cardInfo.getCardSn());
        		    	if(c!=null){
        		    		//注销的卡可以重新发卡
        		    		if(SysStatic.CANCEL.equals(c.getCardStatus())){
        		    			permissionBiz.workerUpdateCardInfo(cardInfo);
        		    			return "0";
        		    		}else{
        		    			return "1";
        		    		}
        		    	}else{
        		    		cardInfo.setCardBelongs("2");//所属物业员工
        		    		int i = permissionBiz.writeCard(cardInfo);
        		    		return i+"";
        		    	}
        		    }else{
        		    	return "3";
        		    }
				} catch (IllegalAccessException e) {
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
					return "2";
				}
        		return null;
        }
        
        /**
         * 跳转挂失注销页面
         * @return
         */
        @RequestMapping("/workerLossUnlossDestroy.do")
       	public String cardInfoLossUnlossDestroy(CardInfo cardInfo,Model model){
        	//获取card map  key=cardSn value=CardInfo
    		try {
    			Map<String, CardInfo> cardMap = permissionBiz.cardMap(cardInfo);
    			//获取人的地址列表
    			if(cardMap!=null&&!cardMap.isEmpty()){
    				for(String key:cardMap.keySet()){
    					CardInfo c = cardMap.get(key);
    				    String address = permissionBiz.findAddressByWorkerId(key,cardInfo.getWorkerId());
    					c.setAddress(address);
    				}
    			}
    			model.addAttribute("cardInfo",cardInfo);
    			model.addAttribute("cardMap",cardMap);
    		} catch (BizException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
       		return "/worker/cardInfoLossUnlossDestroy";
       	}
        
        
        
}
