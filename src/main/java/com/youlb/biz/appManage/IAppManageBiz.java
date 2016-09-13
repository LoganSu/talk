package com.youlb.biz.appManage;

import java.io.IOException;
import java.util.List;

import org.apache.http.ParseException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.appManage.AppManage;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IAppManageBiz.java 
 * @Description: app管理业务接口 
 * @author: Pengjy
 * @date: 2015-11-26
 * 
 */
public interface IAppManageBiz extends IBaseBiz<AppManage> {

	/**
	 * @param appManage
	 * @param loginUser
	 */
	void saveOrUpdate(AppManage appManage, Operator loginUser)throws ParseException, JsonException, IOException,BizException ;

	/**
	 * @return
	 */
	List<AppManage> getOldVersion()throws BizException;

	/**检查添加的版本是否已经存在
	 * @param appManage
	 * @return
	 */
	boolean checkVersion(String md5Value)throws BizException;

	/**获取最新的app包
	 * @return
	 */
	AppManage lastVersion(String type)throws BizException;

}
