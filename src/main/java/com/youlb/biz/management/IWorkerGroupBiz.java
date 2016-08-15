package com.youlb.biz.management;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.Worker;
import com.youlb.entity.management.WorkerGroup;
import com.youlb.entity.privilege.Operator;

public interface IWorkerGroupBiz extends IBaseBiz<WorkerGroup> {

	void saveOrUpdate(WorkerGroup workerGroup, Operator loginUser);
    /**
     * 获取登录用户的物业公司列表
     * @param loginUser
     * @return
     */
	List<Department> getCompanyList(Operator loginUser);
	/**
	 * 通过组id获取员工列表
	 * @param groupId
	 * @param loginUser
	 * @return
	 */
	List<Worker> showgroupWorkerList(WorkerGroup workerGroup);
	/**
	 * 添加分组员工
	 * @param workerGroup
	 * @param loginUser
	 */
	void addWorker(WorkerGroup workerGroup, Operator loginUser);

}
