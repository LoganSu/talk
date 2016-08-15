package com.youlb.biz.countManage;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.countManage.Users;

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

}
