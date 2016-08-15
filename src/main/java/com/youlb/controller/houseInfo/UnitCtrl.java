package com.youlb.controller.houseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IBuildingBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.INeighborhoodsBiz;
import com.youlb.biz.houseInfo.IUnitBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.entity.privilege.Operator;

/** 
 * @ClassName: NeighborhoodsCtrl.java 
 * @Description: 社区信息控制类
 * @author: Pengjy
 * @date: 2015年7月25日
 * 
 */
@Controller
@RequestMapping("/mc/unit")
@Scope("prototype")
public class UnitCtrl extends BaseCtrl {
	@Autowired
	private IUnitBiz unitBiz;
	@Autowired
	private IBuildingBiz buildingBiz;
	@Autowired
	private INeighborhoodsBiz neighborBiz;
	@Autowired
	private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	
	public void setBuildingBiz(IBuildingBiz buildingBiz) {
		this.buildingBiz = buildingBiz;
	}

	public void setNeighborBiz(INeighborhoodsBiz neighborBiz) {
		this.neighborBiz = neighborBiz;
	}

	public void setUnitBiz(IUnitBiz unitBiz) {
		this.unitBiz = unitBiz;
	}

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Unit unit){
		List<Unit> list = new ArrayList<Unit>();
		try {
			Operator loginUser = getLoginUser();
			list = unitBiz.showList(unit,loginUser);
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
   	public String toSaveOrUpdate(String[] ids,Unit unit,Model model){
    	if(ids!=null&&ids.length>0){
    		 unit = unitBiz.get(ids[0]);
    	}
    	model.addAttribute("unit", unit);
   		return "/unit/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Unit unit,Model model){
    	try {
    		unitBiz.saveOrUpdate(unit,getLoginUser());
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
				//检查是否有子节点
				String remark= domainBiz.hasChild(ids);
				if(StringUtils.isNotBlank(remark)){
					super.message = "请先删除"+remark+"的子域";
					return super.message;
				}
				unitBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
}
