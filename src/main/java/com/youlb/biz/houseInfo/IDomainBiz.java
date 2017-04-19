package com.youlb.biz.houseInfo;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.controller.access.HouseTree;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.Domain;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

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
	List<Domain> getDomainByParentId(String id,Operator loginUser,String dwellerId)throws BizException;

	/**通过实体id删除domain对象
	 * @param id
	 */
	void deleteByEntityId(Serializable id)throws BizException;

	/**获取与对象集合
	 * @param carrier
	 * @param loginUser
	 * @return
	 */
	List<Domain> getDomainList(Carrier carrier, Operator loginUser)throws BizException;

	/**更新与对象名称 公共方法
	 * @param entityId
	 * @param createSipNum 
	 * @throws BizException
	 */
	void update(String remark,String entityId, String createSipNum) throws BizException;
	/**
	 * 通过entityid获取domainid
	 * @param buildingBiz
	 * @return
	 */
	String getDomainIdByEntityId(String unitId)throws BizException;
	/**
	 * 获取单元下面的房间号，判断房号是否已经存在
	 * @param room
	 * @return
	 */
	String getDomainByParentId(Room room)throws BizException;
	/**
	 * 通过entityid 获取有子节点的域名称
	 * @param ids
	 * @return
	 */
	String hasChild(String[] ids)throws BizException;

	String getParentIdByEntityId(String string)throws BizException;
	/**
	 * 通过房间id获取社区的秘钥
	 * @param roomId
	 * @return
	 */
	String getNeiborKey(String domainId)throws BizException;

	List<Domain> getDomainByParentId(String id,Operator loginUser,Boolean isAll)throws BizException;
    /**
     * 获取树结构数据
     * @return
     */
	 List<Map<String,String>> domainTree()throws BizException ;
	 /**
	  * 创建sip
	  * @param domainId
	  * @param neiborName
	  * @throws UnsupportedEncodingException
	  * @throws IOException
	  * @throws ClientProtocolException
	  * @throws JsonException
	  * @throws BizException
	  */
	 public void createSip(String domainId, String neiborName)
				throws UnsupportedEncodingException, IOException,
				ClientProtocolException, JsonException, BizException;
	 /**
	  * 向上获取社区名称
	  * @param entityId
	  * @return
	  * @throws BizException
	  */
	 public String getNeiborName(String entityId) throws BizException;
	 /**
	  * 向下获取字级列表
	  * @param areaId
	  * @return
	  * @throws BizException
	  */
	List<String> getNeiborNames(String areaId)throws BizException;
    /**
     * 获取域id和sip账号
     * @param id
     * @return
     */
	List<Object[]> getDomainIdAndSipByEntityId(String id)throws BizException;
	/**
	 * 
	 * @param houseTree
	 */
	void insertHouseInfo(HouseTree houseTree);
}
