package com.youlb.biz.privilege;


import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.entity.privilege.Role;

/** 
 * @ClassName: IRoleBiz.java 
 * @Description: 角色业务接口 
 * @author: Pengjy
 * @date: 2015年7月7日
 * 
 */
public interface IRoleBiz extends IBaseBiz<Role> {


	/**
	 * @param role
	 */
	void saveOrUpdate(Role role);

	/**获取权限列表
	 * @param loginUser
	 * @return
	 */
	List<Privilege> getPrivilegeList(Operator loginUser,Role role);

	/**运营商角色table数据
	 * @param role
	 * @param loginUser
	 * @return
	 */
	List<Role> carrierShowList(Role role, Operator loginUser);

}
