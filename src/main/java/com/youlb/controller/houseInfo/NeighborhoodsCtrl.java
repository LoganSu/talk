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

import com.youlb.biz.houseInfo.IAreaBiz;
import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.INeighborhoodsBiz;
import com.youlb.controller.common.BaseCtrl;
import com.youlb.entity.houseInfo.Neighborhoods;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.RegexpUtils;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: NeighborhoodsCtrl.java 
 * @Description: 社区信息控制类
 * @author: Pengjy
 * @date: 2015年6月25日
 * 
 */
@Controller
@RequestMapping("/mc/neighborhoods")
@Scope("prototype")
public class NeighborhoodsCtrl extends BaseCtrl {
	@Autowired
	private INeighborhoodsBiz neighborBiz;
	@Autowired
	private IDomainBiz domainBiz;
	@Autowired
	private IAreaBiz areaBiz;
	
	public void setAreaBiz(IAreaBiz areaBiz) {
		this.areaBiz = areaBiz;
	}
	public void setNeighborBiz(INeighborhoodsBiz neighborBiz) {
		this.neighborBiz = neighborBiz;
	}
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}
	/**
	 * 显示table数据
	 * @return
	 */
	@RequestMapping("/showList.do")
	@ResponseBody
	public  Map<String, Object> showList(Neighborhoods neighborhoods){
		List<Neighborhoods> list = new ArrayList<Neighborhoods>();
		try {
			Operator loginUser = getLoginUser();
			list = neighborBiz.showList(neighborhoods,loginUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return setRows(list);
	}
	
	 /**
     * 跳转到添加、更新页面
     * @return
     */
    @RequestMapping("/toSaveOrUpdate.do")
   	public String toSaveOrUpdate(String[] ids,Neighborhoods neighborhoods,Model model){
    	if(ids!=null&&ids.length>0){
    		 try {
				neighborhoods = neighborBiz.get(ids[0]);
				model.addAttribute("neighborhoods", neighborhoods);
			} catch (BizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
   		return "/neighborhoods/addOrEdit";
   	}
    /**
     * 保存或修改
     * @param user
     * @param model
     * @return
     */
    @RequestMapping("/saveOrUpdate.do")
    @ResponseBody
    public String save(Neighborhoods neighborhoods,Model model){
    	try {
    		if(StringUtils.isBlank(neighborhoods.getNeibName())){
    			super.message = "社区名称不能为空！";
    			return  super.message;
    		}
    		if(!RegexpUtils.checkNumAndLetter(neighborhoods.getNeibNum(), 5, 5)){
    			super.message = "社区编号不能为空且为5个数字！";
    			return  super.message;
    		}
    		if(StringUtils.isBlank(neighborhoods.getAddress())){
    			super.message = "社区地址不能为空！";
    			return  super.message;
    		}
    		if(StringUtils.isNotBlank(neighborhoods.getTotalArea())&&!RegexpUtils.checkDecimals(neighborhoods.getTotalArea())){
    			super.message = "总占地面积输入为数字类型！";
    			return  super.message;
    		}
    		if(StringUtils.isNotBlank(neighborhoods.getTotalBuildArea())&&!RegexpUtils.checkDecimals(neighborhoods.getTotalBuildArea())){
    			super.message = "总建筑面积输入为数字类型！";
    			return  super.message;
    		}
    		if(StringUtils.isNotBlank(neighborhoods.getTotalBussnisArea())&&!RegexpUtils.checkDecimals(neighborhoods.getTotalBussnisArea())){
    			super.message = "总商业面积输入为数字类型！";
    			return  super.message;
    		}
    		
    		neighborBiz.saveOrUpdate(neighborhoods,getLoginUser());
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
    public String update(Neighborhoods Neighborhoods,Model model){
    	try {
    		neighborBiz.upate(Neighborhoods);
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
				neighborBiz.delete(ids);
			} catch (Exception e) {
				super.message =  "删除出错";
			}
		}
		return super.message;
	}
	 /**
     * 通过areaId获取社区列表
     * @param areaId
     * @param model
     * @return
     
	@RequestMapping("/getNeiborListByAreaId.do")
	@ResponseBody
	public List<Neighborhoods> getNeiborListByAreaId(String areaId,Model model){
		 List<Neighborhoods> neiborList = neighborBiz.getNeiborListByAreaId(areaId);
		return neiborList;
	}
	*/
}
