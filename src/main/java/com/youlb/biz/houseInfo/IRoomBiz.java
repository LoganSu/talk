package com.youlb.biz.houseInfo;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.privilege.Operator;

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
	void saveOrUpdate(Room room,Operator loginUser);

	/**绑定户主
	 * @param room
	 */
	void bindingRoom(Room room);

	/**接触绑定户主
	 * @param room
	 */
	void unbindingRoom(Room room);
	/**
	 * 通过域id获取房间地址
	 * @param domainId
	 * @return
	 */
	String getAddressByDomainId(String domainId);

}
