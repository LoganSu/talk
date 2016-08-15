package com.youlb.biz.infoPublish;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.infoPublish.TodayNews;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.JsonException;


/** 
 * @ClassName: ITodayNewsBiz.java 
 * @Description: 今天头条业务接口 
 * @author: Pengjy
 * @date: 2016-5-11
 * 
 */
public interface ITodayNewsBiz extends IBaseBiz<TodayNews> {

	public void saveOrUpdate(TodayNews todayNews, Operator loginUser) throws IllegalAccessException, InvocationTargetException, ClientProtocolException, IOException, ParseException, JsonException ;

	/**
	 * 发布
	 * @param ids
	 * @param loginUser
	 */
	public void publish(String[] ids, Operator loginUser)throws IllegalAccessException, InvocationTargetException,UnsupportedEncodingException,ClientProtocolException, IOException, ParseException, JsonException ;


 
}
