package com.youlb.biz.infoPublish;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.infoPublish.AdPublish;
import com.youlb.entity.infoPublish.AdPublishPicture;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IAdPublishBiz.java 
 * @Description: 信息发布业务接口 
 * @author: Pengjy
 * @date: 2015-12-2
 * 
 */
public interface IAdPublishBiz extends IBaseBiz<AdPublish> {

	/**
	 * @param infoPublish
	 * @param loginUser
	 */
	void saveOrUpdate(AdPublish adPublish, Operator loginUser)throws BizException,IllegalAccessException, InvocationTargetException,UnsupportedEncodingException, ParseException, JsonException, IOException;

	/**添加图片记录
	 * @param adPic
	 * @return
	 */
	String addPicture(AdPublishPicture adPic)throws BizException;

	/**删除图片
	 * @param picId
	 */
	void deletePicture(String picId)throws BizException;
	/**
	 * 发布
	 * @param ids
	 * @param loginUser
	 */
	public void publish(String[] ids, Operator loginUser)throws BizException, IllegalAccessException, InvocationTargetException,UnsupportedEncodingException,ClientProtocolException, IOException, ParseException, JsonException ;
    /**
     * 通过关联id获取图片列表
     * @param id
     * @return
     */
	List<AdPublishPicture> getPicByAdpublishId(String id)throws BizException;

}
