package com.youlb.biz.countManage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.countManage.DeviceCount;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IDeviceCountBiz.java 
 * @Description: 设备账号业务接口 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
public interface IDeviceCountBiz extends IBaseBiz<DeviceCount> {

	/**
	 * @param deviceCount
	 */
	void saveOrUpdate(DeviceCount deviceCount)throws BizException,ClientProtocolException,IOException, ParseException, JsonException;
	/**
	 * 
	 * @param endTime
	 */
	void updateById(String endTime,String ramdonCode,String orderNum,String qrPath,String id)throws BizException;
	/**
	 * 获取派单序列
	 * @return
	 */
	String getOrderNum()throws BizException;
	/**
	 * 通过账号获取对象
	 * @param deviceCount
	 * @return
	 */
	DeviceCount getByCount(String deviceCount)throws BizException;

}
