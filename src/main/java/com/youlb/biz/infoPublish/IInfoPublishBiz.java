package com.youlb.biz.infoPublish;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.infoPublish.InfoPublish;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IInfoPublish.java 
 * @Description: 信息发布业务接口 
 * @author: Pengjy
 * @date: 2015-12-1
 * 
 */
public interface IInfoPublishBiz extends IBaseBiz<InfoPublish> {

	/**
	 * @param infoPublish
	 * @param loginUser
	 */
	public void saveOrUpdate(InfoPublish infoPublish, Operator loginUser) throws ClientProtocolException, IOException, IllegalAccessException, InvocationTargetException, ParseException, JsonException ;
	/**
	 * 只能删除本运营商的公告
	 * @param ids
	 * @param loginUser
	 */
	public void delete(String[] ids, Operator loginUser);
	/**
	 * 发布
	 * @param ids
	 * @param loginUser
	 */
	public void publish(String[] ids, Operator loginUser)throws IllegalAccessException, InvocationTargetException,UnsupportedEncodingException,ClientProtocolException, IOException, ParseException, JsonException ;

}
