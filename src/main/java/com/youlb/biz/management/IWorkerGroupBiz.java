package com.youlb.biz.management;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.Worker;
import com.youlb.entity.management.WorkerGroup;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

public interface IWorkerGroupBiz extends IBaseBiz<WorkerGroup> {

	void saveOrUpdate(WorkerGroup workerGroup, Operator loginUser)throws BizException;
    /**
     * 获取登录用户的物业公司列表
     * @param loginUser
     * @return
     */
	List<Department> getCompanyList(Operator loginUser)throws BizException;
	/**
	 * 通过组id获取员工列表
	 * @param groupId
	 * @param loginUser
	 * @return
	 */
	List<Worker> showgroupWorkerList(WorkerGroup workerGroup)throws BizException;
	/**
	 * 添加分组员工
	 * @param workerGroup
	 * @param loginUser
	 */
	void addWorker(WorkerGroup workerGroup, Operator loginUser)throws BizException;

}
