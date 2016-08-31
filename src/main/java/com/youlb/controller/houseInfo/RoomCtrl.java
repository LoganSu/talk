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
import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.biz.houseInfo.IUnitBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Building;
import com.youlb.entity.houseInfo.Neighborhoods;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.utils.common.RegexpUtils;

/** 
 * @ClassName: RoomCtrl.java 
 * @Description: 房间业务控制类 
 * @author: Pengjy
 * @date: 2015年6月30日
 * 
 */
@RequestMapping("/mc/room")
@Scope("prototype")
@Controller
public class RoomCtrl extends BaseCtrl {
	@Autowired
    private IRoomBiz roomBiz;
	@Autowired
	private IBuildingBiz buildingBiz;
	@Autowired
	private INeighborhoodsBiz neighborBiz;
	@Autowired
	private IUnitBiz unitBiz;
	@Autowired
    private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	public void setUnitBiz(IUnitBiz unitBiz) {
		this.unitBiz = unitBiz;
	}
	public void setNeighborBiz(INeighborhoodsBiz neighborBiz) {
		this.neighborBiz = neighborBiz;
	}

	public void setRoomBiz(IRoomBiz roomBiz) {
		this.roomBiz = roomBiz;
	}
    
	
	public void setBuildingBiz(IBuildingBiz buildingBiz) {
		this.buildingBiz = buildingBiz;
	}
    /**初始化搜索数据
     * @param module
     * @param modulePath
     * @param parentId
     * @param model
     * @return
     * @see com.youlb.controller.common.BaseCtrl#showPage(java.lang.String, java.lang.String, java.lang.String, org.springframework.ui.Model)
     */
    @RequestMapping("/roomCardListshowPage.do")
    public String roomCardListshowPage(Model model) {
    	List<Neighborhoods> neighborList = neighborBiz.getNeighborList(getLoginUser());
    	model.addAttribute("neighborList", neighborList);
//    	if(neighborList!=null&&!neighborList.isEmpty()){
//    		List<Building> buildingList = getBuildingListByNeibId(neighborList.get(0).getId());
//    		model.addAttribute("buildingList", buildingList);
//    		if(buildingList!=null&&!buildingList.isEmpty()){
//    			List<Unit> unitList = getUnitListBybuildingId(buildingList.get(0).getId());
//    			model.addAttribute("unitList", unitList);
//    		}
//    	}
    	return super.showPage(model);
    }
    /**
     * 通过社区id获取楼栋集合
     * @param neighborId
     * @return
     */
    @RequestMapping("/getBuildingListByNeibId.do")
    @ResponseBody
    public List<Building> getBuildingListByNeibId(String neighborId){
    	return buildingBiz.getBuildingListByNeibId(neighborId);
    }
    
    /**
     * 通过楼栋id获取单元集合
     * @param neighborId
     * @return
     */
    @RequestMapping("/getUnitListBybuildingId.do")
    @ResponseBody
    public List<Unit> getUnitListBybuildingId(String buildingId){
    	return unitBiz.getUnitListBybuildingId(buildingId);
    }
    
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Room room){
		List<Room> list = new ArrayList<Room>();
		//处理发卡页面查询参数
		if(StringUtils.isNotBlank(room.getUnitId())){
			String domainId = domainBiz.getDomainIdByEntityId(room.getUnitId());
			room.setParentId(domainId);
		}
		try {
			list = roomBiz.showList(room,getLoginUser());
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
   	public String toSaveOrUpdate(String[] ids,Room room,Model model){
    	if(ids!=null&&ids.length>0){
    		room = roomBiz.get(ids[0]);
    		room.setParentId(domainBiz.getParentIdByEntityId(ids[0]));
    	}
    	
    	model.addAttribute("room",room);
   		return "/room/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Room room,Model model){
    	try {
    		if(!RegexpUtils.checkNumber(room.getRoomNum())){
    			super.message = "房号不能为空且不大于5个数字";
    			return  super.message;
    		}
    		if(room.getRoomNum().length()>5){
    			super.message = "房号不能超过5个数字";
    			return  super.message;
    		}
    		if(!RegexpUtils.checkNumber(room.getRoomFloor()+"")){
    			super.message = "楼层不能为空且为数字";
    			return  super.message;
    		}
    		//判断单元下面房间号是否已经存在
    		String roomNum = domainBiz.getDomainByParentId(room);
    		if(StringUtils.isNotBlank(roomNum)){
    			super.message = "房号"+roomNum+"已经存在";
    			return  super.message;
    		}
    		roomBiz.saveOrUpdate(room,getLoginUser());
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
				roomBiz.delete(ids);
			} catch (Exception e) {
				e.printStackTrace();
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	
 
	
	 /**
     * 绑定户主
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/bindingRoom.do")
	@ResponseBody
	public String bindingRoom(Room room,Model model){
		try {
			roomBiz.bindingRoom(room);
			super.message =  "绑定成功！";
		} catch (Exception e) {
			e.printStackTrace();
			super.message =  "绑定出错！";
		}
		return super.message;
	}
	
	
	/**
     * 解除户主绑定
     * @param ids
     * @param model
     * @return
     */
	@RequestMapping("/unbindingHomeHost.do")
	@ResponseBody
	public String unbindingHomeHost(Room room,Model model){
		try {
			roomBiz.unbindingRoom(room);
			super.message =  "解除绑定成功！";
		} catch (Exception e) {
			e.printStackTrace();
			super.message =  "接触绑定出错！";
		}
		return super.message;
	}
	
	@RequestMapping("/getAddressByDomainId.do")
	@ResponseBody
	public String getAddressByDomainId(String domainId){
		String address = roomBiz.getAddressByDomainId(domainId);
		return address;
	}
}
