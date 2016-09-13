package com.youlb.biz.management;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.Worker;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

public interface IWorkerBiz extends IBaseBiz<Worker> {

	void saveOrUpdate(Worker worker, Operator loginUser)throws BizException;
    /**
     * 通过部门获取员工列表
     * @param departmentId
     * @return
     */
	List<Worker> getWorkerList(String departmentId)throws BizException;
	/**
	 * 检查手机号码是否已经被注册
	 * @param phone
	 * @return
	 */
	boolean checkPhoneExist(String phone,String id)throws BizException;

}
