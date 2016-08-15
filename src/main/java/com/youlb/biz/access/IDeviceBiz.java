package com.youlb.biz.access;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.access.DeviceInfo;
import com.youlb.entity.privilege.Operator;

/** 
 * @ClassName: IDeviceBiz.java 
 * @Description: 设备管理接口 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
public interface IDeviceBiz extends IBaseBiz<DeviceInfo> {

	/**
	 * @param device
	 * @param loginUser
	 * @return
	 */
	String saveOrUpdate(DeviceInfo device, Operator loginUser);


}
