package com.youlb.biz.access;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.access.DeviceInfo;
import com.youlb.entity.access.DeviceInfoDto;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

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
	String saveOrUpdate(DeviceInfo device, Operator loginUser)throws BizException ;
    /**
     * 批量导入excel数据
     * @param readExcelContent
     */
	void saveBatch(List<DeviceInfoDto> readExcelContent)throws BizException;
	 /**
	  * 获取设备信息
	  * @return
	  */
	List<DeviceInfoDto> getDeviceInfoDto(String[] ids)throws BizException;
	 /**
	  *  激活设备
	  * @param ids
	  */
	void setLive(String[] ids)throws BizException;


}
