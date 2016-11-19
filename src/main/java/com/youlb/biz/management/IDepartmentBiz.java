package com.youlb.biz.management;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.DepartmentTree;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: IDepartmentBiz.java 
* @Description: 部门业务接口 
* @author: Pengjy
* @date: 2016年6月14日
*
 */
public interface IDepartmentBiz extends IBaseBiz<Department> {

	void saveOrUpdate(Department department, Operator loginUser)throws BizException;
    /**
     * 获取部门树状结构数据
     * @param department
     * @param loginUser
     * @return
     */
	List<DepartmentTree> showListDepartmentTree(DepartmentTree department,Operator loginUser)throws BizException;
	/**
	 * 检查社区是否已经绑定物业公司
	 * @param domainId
	 * @return
	 */
	String checkDomain(String domainId)throws BizException;
	void delete(String[] ids, String parentId)throws BizException;
}
