package com.youlb.biz.access;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.access.CardInfo;
import com.youlb.entity.access.CardRecord;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IPermissionBiz.java 
 * @Description: 授权管理业务接口 
 * @author: Pengjy
 * @date: 2015-11-5
 * 
 */
public interface IPermissionBiz extends IBaseBiz<CardInfo> {

	/**
	 * @param cardInfo
	 * @param loginUser
	 * @return
	 */
	String saveOrUpdate(CardInfo cardInfo, Operator loginUser);


	/**根据人员信息查询地址信息
	 * @param cardInfo
	 * @return
	 */
	List<CardInfo> findAddress(CardInfo cardInfo,Map<String, String> domainMap);

	/**写卡入库
	 * @param cardInfo
	 * @throws NativeException 
	 * @throws IllegalAccessException 
	 */
	int writeCard(CardInfo cardInfo) throws IllegalAccessException, ParseException, JsonException, IOException;

	/**判断卡片是否已经存在
	 * @param cardSn
	 * @return
	 */
	boolean checkCardExist(String cardSn);

	/**根据相应条件 获取card map
	 * @param cardInfo
	 * @return
	 */
	Map<String, CardInfo> cardMap(CardInfo cardInfo);

	/** 挂失 解挂 注销统一方法
	 * @param cardInfo
	 */
	void lossUnlossDestroy(CardInfo cardInfo) throws BizException, ClientProtocolException, IOException, ParseException, JsonException;


	/**通过roomId获取地址信息
	 * @param cardInfo
	 * @return
	 */
	String findAddressByRoomId(String roomId);


	/**通过cardSn获取地址信息
	 * @param cardInfo
	 * @return
	 */
	List<CardInfo> findAddressByCardSn(CardInfo cardInfo);


	/**
	 * @param cardInfo
	 * @param loginUser
	 * @return
	 */
	List<CardRecord> appRecordList(CardRecord cardRecord, Operator loginUser);


	/**
	 * @param cardInfo
	 * @param loginUser
	 * @return
	 */
	List<CardRecord> cardRecord(CardRecord cardRecord, Operator loginUser);


	/**
	 * @param cardSn
	 * @return
	 */
	CardInfo getImg(Integer id);
   
}
