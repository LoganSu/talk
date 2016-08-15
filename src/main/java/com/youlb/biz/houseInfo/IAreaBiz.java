package com.youlb.biz.houseInfo;


import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Address;
import com.youlb.entity.houseInfo.Area;
import com.youlb.entity.privilege.Operator;

/** 
 * @ClassName: IAreaBiz.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年6月23日
 * 
 */
public interface IAreaBiz extends IBaseBiz<Area>{

	/**
	 * @param area
	 */
	void saveOrUpdate(Area area,Operator loginUser);
	
	/**通过省份获取地区
	 * @param province
	 * @return
	 */
	List<Area> getAreaList(String province);

	/**获取地区数据 按省份分组
	 * @return
	 */
	List<Area> getProvinceList(String areaId);

	/**	获取地区列表
	 * @return
	 */
	List<Area> getAreaList();

	/**获取地区信息
	 * @param neibId
	 * @return
	 */
	Area getAreaByNeibId(String neibId);
	/**
	 * 通过父id获取地址
	 * @param parentId
	 * @return
	 */
	List<Address> getAddressByParentId(String parentId);
	/**
	 * 获取社区编号
	 * @param city
	 * @return
	 */
	String getAreaCode(String city);
    /**
     * 检查城市是否已经存在
     * @param area
     * @return
     */
	List<Area> getAreaList(Area area);

}
