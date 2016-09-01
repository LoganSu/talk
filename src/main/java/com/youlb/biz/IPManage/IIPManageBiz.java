package com.youlb.biz.IPManage;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.IPManage.IPManage;
import com.youlb.entity.privilege.Operator;

public interface IIPManageBiz extends IBaseBiz<IPManage> {

	void saveOrUpdate(IPManage iPManage, Operator loginUser);

 
}
