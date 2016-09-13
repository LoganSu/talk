package com.youlb.biz.countManage;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.countManage.Users;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: IUsersBiz.java 
 * @Description: 注册用户业务接口 
 * @author: Pengjy
 * @date: 2015-11-24
 * 
 */
public interface IUsersBiz extends IBaseBiz<Users> {

	/**
	 * @param users
	 */
	void saveOrUpdate(Users users);
	/**
	 * 暂停用户使用
	 * @param id
	 */
	void update(Integer id,String status)throws BizException;

}
