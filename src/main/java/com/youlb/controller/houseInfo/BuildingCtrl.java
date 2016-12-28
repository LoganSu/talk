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
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Building;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: AreaCtrller.java 
 * @Description: 楼栋逻辑控制类 
 * @author: Pengjy
 * @date: 2015年6月23日
 * 
 */
@Controller
@RequestMapping("/mc/building")
@Scope("prototype")
public class BuildingCtrl extends BaseCtrl {
	
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

	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Building building){
		List<Building> list = new ArrayList<Building>();
		try {
			list = buildingBiz.showList(building,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,Building building,Model model){
    	if(ids!=null&&ids.length>0){
    		 try {
				building = buildingBiz.get(ids[0]);
				model.addAttribute("building",building);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
   		return "/building/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Building building,Model model){
    	try {
    		if(StringUtils.isBlank(building.getBuildingName())){
    			super.message = "楼栋名称不能为空！";
    			return  super.message;
    		}else{
    			boolean b = buildingBiz.checkBuildingName(building);
	    		if(b){
	    			super.message = "楼栋名已经存在！";
	    			return  super.message;
	    		}
    		}
    		if(!RegexpUtils.checkNumAndLetter(building.getBuildingNum(), 3, 3)){
    			super.message = "楼栋编号不能为空且为3个字符！";
    			return  super.message;
    		}
    		//同一个社区 楼栋编号不能相同
    		boolean b = buildingBiz.checkBuildingNum(building);
    		if(b){
    			super.message = "楼栋编号已经存在！";
    			return  super.message;
    		}
    		if(!RegexpUtils.checkNumber(building.getTotalFloor()+"")){
    			super.message = "层数不能为空且为数字！";
    			return  super.message;
    		}
    		if(StringUtils.isNotBlank(building.getBuildHeight())&&!RegexpUtils.checkDecimals(building.getBuildHeight())){
    			super.message = "楼高为数字类型！";
    			return  super.message;
    		}
    		buildingBiz.saveOrUpdate(building,getLoginUser());
		} catch (Exception e) {
			super.message = "操作失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }
    /**
     * 修改
     * @param user
     * @param model
     * @return
     
    @RequestMapping("/update.do")
    @ResponseBody
    public String update(Area area,Model model){
    	try {
    		areaBiz.upate(area);
		} catch (Exception e) {
			super.message = "修改失败！";
			e.printStackTrace();
			//TODO log
		}
    	 return  super.message;
    }*/
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
				buildingBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	 /**
     * 通过neibId获取楼栋列表
     * @param areaId
     * @param model
     * @return
     */
	@RequestMapping("/getBuildingListByNeibId.do")
	@ResponseBody
	public List<Building> getBuildingListByNeibId(String neibId,Model model){
		try {
			 List<Building> buildingList = buildingBiz.getBuildingListByNeibId(neibId);
			 return buildingList;
		} catch (BizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;

	}
}
