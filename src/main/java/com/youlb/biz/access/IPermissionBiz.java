package com.youlb.biz.access;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
	String saveOrUpdate(CardInfo cardInfo, Operator loginUser)throws BizException ;


	/**根据人员信息查询地址信息
	 * @param cardInfo
	 * @return
	 */
	List<CardInfo> findAddress(CardInfo cardInfo,Map<String, String> domainMap) throws BizException;

	/**写卡入库
	 * @param cardInfo
	 * @throws NativeException 
	 * @throws IllegalAccessException 
	 */
	int writeCard(CardInfo cardInfo) throws IllegalAccessException, ParseException, JsonException, IOException,BizException;

	/**判断卡片是否已经存在
	 * @param cardSn
	 * @return
	 */
	CardInfo checkCardExist(CardInfo cardInf) throws BizException;

	/**根据相应条件 获取card map
	 * @param cardInfo
	 * @return
	 */
	Map<String, CardInfo> cardMap(CardInfo cardInfo) throws BizException;

	/** 挂失 解挂 注销统一方法
	 * @param cardInfo
	 */
	void lossUnlossDestroy(CardInfo cardInfo) throws BizException, ClientProtocolException, IOException, ParseException, JsonException;


	/**通过roomId获取地址信息
	 * @param cardInfo
	 * @return
	 */
	String findAddressByRoomId(String roomId) throws BizException;


	/**通过cardSn获取地址信息
	 * @param cardInfo
	 * @return
	 */
	List<CardInfo> findAddressByCardSn(CardInfo cardInfo) throws BizException;


	/**
	 * @param cardInfo
	 * @param loginUser
	 * @return
	 */
	List<CardRecord> appRecordList(CardRecord cardRecord, Operator loginUser) throws BizException;


	/**
	 * @param cardInfo
	 * @param loginUser
	 * @return
	 */
	List<CardRecord> cardRecord(CardRecord cardRecord, Operator loginUser) throws BizException;


	/**
	 * @param cardSn
	 * @return
	 */
	CardInfo getImg(Integer id) throws BizException;

   
	void updateCardInfo(CardInfo cardInfo)throws BizException,UnsupportedEncodingException,IOException, ParseException, JsonException;

	/**
	 * 判断卡片是否已经初始化秘钥
	 * @param cardSn
	 * @return
	 * @throws BizException
	 */
	String isInitKey(String cardSn,String roomId)throws BizException;

	/**
	 * 判断是不是最后注销的卡
	 * @param cardSn
	 * @return
	 */
	String isLastCard(String cardSn)throws BizException;
   
}
