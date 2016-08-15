package com.youlb.biz.houseInfo;

import java.io.Serializable;
import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.Domain;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: IDomain.java 
 * @Description: 域对象接口 
 * @author: Pengjy
 * @date: 2015年8月31日
 * 
 */
public interface IDomainBiz extends IBaseBiz<Domain> {

	/**获取子节点
	 * @param id
	 * @return
	 */
	List<Domain> getDomainByParentId(String id,Operator loginUser,String dwellerId);

	/**通过实体id删除domain对象
	 * @param id
	 */
	void deleteByEntityId(Serializable id);

	/**获取与对象集合
	 * @param carrier
	 * @param loginUser
	 * @return
	 */
	List<Domain> getDomainList(Carrier carrier, Operator loginUser);

	/**更新与对象名称 公共方法
	 * @param entityId
	 * @throws BizException
	 */
	void update(String remark,String entityId) throws BizException;
	/**
	 * 通过entityid获取domainid
	 * @param buildingBiz
	 * @return
	 */
	String getDomainIdByEntityId(String unitId);
	/**
	 * 获取单元下面的房间号，判断房号是否已经存在
	 * @param room
	 * @return
	 */
	String getDomainByParentId(Room room);
	/**
	 * 通过entityid 获取有子节点的域名称
	 * @param ids
	 * @return
	 */
	String hasChild(String[] ids);

}
