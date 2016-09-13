package com.youlb.biz.privilege;


import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.utils.exception.BizException;

/** 
 * @ClassName: IPrivilegeBiz.java 
 * @Description: 权限接口
 * @author: Pengjy
 * @date: 2015年8月25日
 * 
 */
public interface IPrivilegeBiz extends IBaseBiz<Privilege> {



	/**
	 * @param privilege
	 */
	void saveOrUpdate(Privilege privilege)throws BizException;

	/**
	 * @param id
	 * @param loginUser
	 * @return
	 */
	List<Privilege> showListByParentId(String id, Operator loginUser)throws BizException;

   
}
