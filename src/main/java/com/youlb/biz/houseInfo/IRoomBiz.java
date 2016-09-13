package com.youlb.biz.houseInfo;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: IRoomBiz.java 
 * @Description: 房间信息业务接口
 * @author: Pengjy
 * @date: 2015年6月30日
 * 
 */
public interface IRoomBiz extends IBaseBiz<Room> {

	/**保存或更新
	 * @param room
	 */
	void saveOrUpdate(Room room,Operator loginUser)throws BizException;

	/**绑定户主
	 * @param room
	 */
	void bindingRoom(Room room)throws BizException;

	/**接触绑定户主
	 * @param room
	 */
	void unbindingRoom(Room room)throws BizException;
	/**
	 * 通过域id获取房间地址
	 * @param domainId
	 * @return
	 */
	String getAddressByDomainId(String domainId)throws BizException;

}
