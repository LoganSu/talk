/**
 * Copyright 2015 Unionbon' Studio
 * All right Reserved.
 * Created on 2015年8月28日.
 * Created by garyxin.
 */

package com.youlb.utils.common;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.youlb.utils.exception.JsonException;


/**
 * @author garyxin on 2015年8月28日.
 *
 */
public class JsonUtils {
	/** 日志输出 */
	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
	
	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.setSerializationInclusion(Inclusion.NON_NULL); 
	}
	
	public static<T> T fromJson(String strJson, Class<T> className) throws JsonException {
		try {
			return mapper.readValue(strJson, className);
		} catch (JsonParseException e) {
			logger.error(e.getMessage(), e);
			throw new JsonException();
		} catch (JsonMappingException e) {
			logger.error(e.getMessage(), e);
			throw new JsonException();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new JsonException();
		}
	}
	
	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
