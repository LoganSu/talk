package com.youlb.biz.IPManage;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.IPManage.IPManage;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

public interface IIPManageBiz extends IBaseBiz<IPManage> {

	void saveOrUpdate(IPManage iPManage, Operator loginUser)throws BizException;
    /**
     * 判断名称是否存在
     * @param iPManage
     * @return
     */
	boolean checkNeibName(IPManage iPManage)throws BizException;

 
}
