package com.youlb.controller.houseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youlb.biz.houseInfo.IBuildingBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.INeighborhoodsBiz;
import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.biz.houseInfo.IUnitBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: NeighborhoodsCtrl.java 
 * @Description: 社区信息控制类
 * @author: Pengjy
 * @date: 2015年7月25日
 * 
 */
@Controller
@RequestMapping("/mc/unit")
public class UnitCtrl extends BaseCtrl {
	@Autowired
	private IUnitBiz unitBiz;
	@Autowired
	private IBuildingBiz buildingBiz;
	@Autowired
	private INeighborhoodsBiz neighborBiz;
	@Autowired
	private IDomainBiz domainBiz;
	@Autowired
    private IRoomBiz roomBiz;
	
	
	public void setRoomBiz(IRoomBiz roomBiz) {
		this.roomBiz = roomBiz;
	}
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
    		 try {
				unit = unitBiz.get(ids[0]);
				model.addAttribute("unit", unit);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
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
	    	if(StringUtils.isBlank(unit.getUnitName())){
	    		super.message = "单元名称不能为空!";
	    		 return  super.message;
	    	}else{
				boolean b = unitBiz.checkUnitName(unit);
	    		if(b){
	    			super.message = "单元名称已经存在！";
	    			return  super.message;
	    		}
			}
	    	if(StringUtils.isBlank(unit.getUnitNum())||!RegexpUtils.checkNumber(unit.getUnitNum())||unit.getUnitNum().length()>5){
	    		super.message = "呼叫号码不能为空且小于5位数字!";
	    		 return  super.message;
	    	}
	    	//同一个楼栋 单元编号不能相同
			boolean b = unitBiz.checkUnitNum(unit);
			if(b){
				super.message = "呼叫号码已经存在！";
				return  super.message;
			}
			 //更新操作
			if(StringUtils.isNotBlank(unit.getId())){
				Unit a = unitBiz.get(unit.getId());
				//编号有更新操作
				if(!a.getUnitNum().equals(unit.getUnitNum())){
					//查询父级编号
					String startNum = roomBiz.getStartNum(unit.getId(),4);
					roomBiz.updateSipNum(startNum+"-"+a.getUnitNum(),unit.getUnitNum(),4);
				}
			}
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
