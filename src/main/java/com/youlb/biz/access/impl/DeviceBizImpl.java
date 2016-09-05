package com.youlb.biz.access.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.access.IDeviceBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.access.DeviceInfo;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;

/** 
 * @ClassName: DeviceBizImpl.java 
 * @Description: 设备管理业务实现类 
 * @author: Pengjy
 * @date: 2015-11-23
 * 
 */
@Service("deviceBiz")
public class DeviceBizImpl implements IDeviceBiz {
	@Autowired
    private BaseDaoBySql<DeviceInfo> deviceSqlDao;
	public void setDeviceSqlDao(BaseDaoBySql<DeviceInfo> deviceSqlDao) {
		this.deviceSqlDao = deviceSqlDao;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(DeviceInfo target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**修改密码
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(DeviceInfo device) throws BizException {
		String update = "update DeviceInfo t set t.devicePws=? where t.id=?";
		deviceSqlDao.update(update,new Object[]{SHAEncrypt.digestPassword(device.getDevicePws()),device.getId()});
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		deviceSqlDao.delete(id);

	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null){
			for(String id:ids){
				delete(id);
			}
		}

	}

	/**
	 * @param id
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#get(java.io.Serializable)
	 */
	@Override
	public DeviceInfo get(Serializable id) throws BizException {
		return deviceSqlDao.get(id);
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<DeviceInfo> showList(DeviceInfo target, Operator loginUser)
			throws BizException {
		 StringBuilder sb =new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("from DeviceInfo t where 1=1");
		 if(StringUtils.isNotBlank(target.getId())){
			 sb.append(" and t.id like ?");
			 values.add("%"+target.getId()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDeviceNum())){
			 sb.append(" and t.deviceNum like ?");
			 values.add("%"+target.getDeviceNum()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDeviceModel())){
			 sb.append(" and t.deviceModel like ?");
			 values.add("%"+target.getDeviceModel()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getDeviceBorn())){
			 sb.append(" and t.deviceBorn =?");
			 values.add(target.getDeviceBorn());
		 }
		 if(StringUtils.isNotBlank(target.getDeviceStatus())){
			 if("1".equals(target.getDeviceStatus())){
				 sb.append(" and t.deviceStatus =?");
				 values.add(target.getDeviceStatus());
			 }else{
				 sb.append(" and t.deviceStatus is null or t.deviceStatus ='' ");
			 }
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.deviceNum");
		return deviceSqlDao.pageFind(sb.toString(),values.toArray(), target.getPager());
	}

	/**
	 * @param device
	 * @param loginUser
	 * @return
	 * @see com.youlb.biz.access.IDeviceBiz#saveOrUpdate(com.youlb.entity.access.DeviceInfo, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public String saveOrUpdate(DeviceInfo device, Operator loginUser) {
		 String update = "update DeviceInfo set deviceStatus=?,deviceFactory=?,deviceBorn=?,remark=? where id=?";
		deviceSqlDao.update(update,new Object[]{device.getDeviceStatus(),device.getDeviceFactory(),device.getDeviceBorn(),device.getRemark(),device.getId()});
		return null;
	}


}
