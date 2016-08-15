package com.youlb.biz.houseInfo;


import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Building;
import com.youlb.entity.privilege.Operator;

/** 
 * @ClassName: IBuildingBiz.java 
 * @Description: 楼栋业务接口
 * @author: Pengjy
 * @date: 2015年6月29日
 * 
 */
public interface IBuildingBiz extends IBaseBiz<Building>{

	/**
	 * @param area
	 */
	void saveOrUpdate(Building building,Operator loginUser);

	/**通过neibId获取buildId
	 * @param parentId
	 * @return
	 */
	String getNeibId(String parentId);

	/**通过neibId获取楼栋列表
	 * @param neibId
	 * @return
	 */
	List<Building> getBuildingListByNeibId(String neibId);
	


}
