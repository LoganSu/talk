package com.youlb.biz.management;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.Repairs;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: ICustomerServiceBiz.java 
* @Description: 工单服务业务接口 
* @author: Pengjy
* @date: 2016年6月12日
*
 */
public interface IRepairsBiz extends IBaseBiz<Repairs> {


	String[] countArr(Integer orderNatue,Operator loginUser)throws BizException;
   
	void saveOrUpdate(Repairs repairs, Operator loginUser)throws BizException;

}
