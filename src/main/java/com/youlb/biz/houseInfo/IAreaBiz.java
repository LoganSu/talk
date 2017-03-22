package com.youlb.biz.houseInfo;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Address;
import com.youlb.entity.houseInfo.Area;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

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
	void saveOrUpdate(Area area,Operator loginUser)throws BizException, UnsupportedEncodingException, ClientProtocolException, IOException, JsonException;
	
	/**通过省份获取地区
	 * @param province
	 * @return
	 */
	List<Area> getAreaList(String province)throws BizException;

	/**获取地区数据 按省份分组
	 * @return
	 */
	List<Area> getProvinceList(String areaId)throws BizException;

	/**	获取地区列表
	 * @return
	 */
	List<Area> getAreaList()throws BizException;

	/**获取地区信息
	 * @param neibId
	 * @return
	 */
	Area getAreaByNeibId(String neibId)throws BizException;
	/**
	 * 通过父id获取地址
	 * @param parentId
	 * @return
	 */
	List<Address> getAddressByParentId(String parentId)throws BizException;
	/**
	 * 获取社区编号
	 * @param city
	 * @return
	 */
	String getAreaCode(String city)throws BizException;
    /**
     * 检查城市是否已经存在
     * @param area
     * @return
     */
	List<Area> getAreaList(Area area)throws BizException;
	/**
	 * 查询编号是否存在
	 * @param areaNum
	 * @return
	 */
	String checkAreaNum(String areaNum)throws BizException;
	/**
	 * 查询编号是否存在查询area表
	 * @param areaNum
	 * @return
	 */
	boolean checkAreaNumFromArea(Area area)throws BizException;

}
