package com.youlb.biz.management;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.BillManage;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: IBillManageBiz.java 
* @Description: 费用管理接口类 
* @author: Pengjy
* @date: 2016年10月17日
*
 */
public interface IBillManageBiz extends IBaseBiz<BillManage> {

	void saveOrUpdate(BillManage billManage, Operator loginUser)throws BizException;

}
