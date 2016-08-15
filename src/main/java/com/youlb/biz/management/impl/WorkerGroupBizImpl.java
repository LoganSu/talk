package com.youlb.biz.management.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.management.IWorkerGroupBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.Worker;
import com.youlb.entity.management.WorkerGroup;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
@Service("workerGroupBiz")
public class WorkerGroupBizImpl implements IWorkerGroupBiz {
	@Autowired
	private BaseDaoBySql<WorkerGroup> workerGroupDao;
	public void setWorkerGroupDao(BaseDaoBySql<WorkerGroup> workerGroupDao) {
		this.workerGroupDao = workerGroupDao;
	}

	@Override
	public String save(WorkerGroup target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(WorkerGroup target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		workerGroupDao.delete(id);

	}

	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null){
			for(String id:ids){
				delete(id);
			}
		}
		

	}

	@Override
	public WorkerGroup get(Serializable id) throws BizException {
		WorkerGroup workerGroup= workerGroupDao.get(id);
		String sql ="select fworker_id from t_worker_to_group where fgroup_id=?";
		List<String> workerIds = workerGroupDao.pageFindBySql(sql, new Object[]{id});
		workerGroup.setWorkerIds(workerIds);
		return workerGroup;
	}

	@Override
	public List<WorkerGroup> showList(WorkerGroup target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from(select g.id id,g.fgroup_name groupName,g.fpower power,g.fremark remark,d.fdepartmentname departmentName,g.fcreatetime createTime ")
		 .append(" from t_worker_group g inner join t_department d on d.id=g.fdepartment_id  INNER JOIN t_department_domain tdd on tdd.fdepartmentid=d.id where 1=1 ");
		//普通用户过滤s域
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tdd.fdomainid "));
			values.add(domainIds);
		}
		 if(StringUtils.isNotBlank(target.getGroupName())){
			 sb.append(" and g.fgroup_name like ?");
			 values.add("%"+target.getGroupName()+"%");
		 }
		 if(target.getPower()!=null){
			 sb.append(" and g.fpower = ?");
			 values.add(target.getPower());
		 }
		 sb.append(" GROUP BY g.id,g.fgroup_name,g.fpower,g.fremark,d.fdepartmentname,g.fcreatetime)t");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime desc ");
		List<Object[]> listObj = workerGroupDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		List<WorkerGroup> list = new ArrayList<WorkerGroup>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				WorkerGroup wg = new WorkerGroup();
				wg.setPager(target.getPager());
				wg.setId(obj[0]==null?"":(String)obj[0]);
				wg.setGroupName(obj[1]==null?"":(String)obj[1]);
				wg.setPower(obj[2]==null?null:(Integer)obj[2]);
				wg.setRemark(obj[3]==null?"":(String)obj[3]);
				wg.setDepartmentName(obj[4]==null?"":(String)obj[4]);
				wg.setCreateTime(obj[5]==null?null:(Date)obj[5]);
				list.add(wg);
			}
		}
		return list;
	}

	@Override
	public void saveOrUpdate(WorkerGroup workerGroup, Operator loginUser) {
		String insert ="insert into t_worker_to_group(fgroup_id,fworker_id) values(?,?)";
		 //add
		 if(StringUtils.isBlank(workerGroup.getId())){
			 String id = (String)workerGroupDao.add(workerGroup);
			 if(workerGroup.getWorkerIds()!=null&&!workerGroup.getWorkerIds().isEmpty()){
				 for(String workerId :workerGroup.getWorkerIds()){
					 workerGroupDao.executeSql(insert, new Object[]{id,workerId});
				 }
			 }
			 
		 }else{
			 String update = "update WorkerGroup set departmentId=?,groupName=?,remark=?,power=? where id=?";
			 workerGroupDao.update(update, new Object[]{workerGroup.getDepartmentId(),workerGroup.getGroupName(),workerGroup.getRemark(),workerGroup.getPower(),workerGroup.getId()});
			 //删掉历史关系
			 String delete = "delete from t_worker_to_group where fgroup_id=?";
			 workerGroupDao.executeSql(delete, new Object[]{workerGroup.getId()});
			 //插入新关系
			 if(workerGroup.getWorkerIds()!=null&&!workerGroup.getWorkerIds().isEmpty()){
				 for(String workerId :workerGroup.getWorkerIds()){
					 workerGroupDao.executeSql(insert, new Object[]{workerGroup.getId(),workerId});
				 }
			 }
		 }
	}
    /**
     * 获取登录用户的物业公司
     * @param loginUser
     * @return
     * @see com.youlb.biz.management.IWorkerGroupBiz#getCompanyList(com.youlb.entity.privilege.Operator)
     */
	@Override
	public List<Department> getCompanyList(Operator loginUser) {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("SELECT d.id,d.fdepartmentname from  t_department_domain tdd INNER JOIN t_department d on d.id=tdd.fdepartmentid ");
		//普通用户过滤s域
		List<String> domainIds = loginUser.getDomainIds();
		if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tdd.fdomainid "));
			values.add(domainIds);
		}
		 sb.append(" group by d.id");
		 List<Object[]> listObj = workerGroupDao.pageFindBySql(sb.toString(), values.toArray());
		 List<Department> list = new ArrayList<Department>();
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj :listObj){
				 Department department = new Department();
				 department.setId(obj[0]==null?"":(String)obj[0]);
				 department.setDepartmentName(obj[1]==null?"":(String)obj[1]);
				 list.add(department);
			 }
		 }
		return list;
	}
    /**
     * 获取员工列表
     * @param groupId
     * @param loginUser
     * @return
     * @see com.youlb.biz.management.IWorkerGroupBiz#showgroupWorkerList(java.lang.String, com.youlb.entity.privilege.Operator)
     */
	@Override
	public List<Worker> showgroupWorkerList(WorkerGroup workerGroup) {
		List<Worker> list = new ArrayList<Worker>();
		if(StringUtils.isNotBlank(workerGroup.getId())){
			StringBuilder sb = new StringBuilder();
			sb.append("select * from (SELECT g.fworker_id id,w.fworkername workerName,w.fphone phone,d.fdepartmentname departmentName from t_worker_group wg INNER JOIN t_worker_to_group g ")
			.append(" on g.fgroup_id=wg.id INNER JOIN t_worker w on w.id=g.fworker_id INNER JOIN t_department d on d.id=w.fdepartmentid")
			.append(" where wg.id= ?) t ");
			OrderHelperUtils.getOrder(sb, workerGroup, "t.", "t.workerName");
			List<Object[]> listObj = workerGroupDao.pageFindBySql(sb.toString(), new Object[]{workerGroup.getId()}, workerGroup.getPager());
			if(listObj!=null&&!listObj.isEmpty()){
				for(Object[] obj:listObj){
					Worker worker = new Worker();
					worker.setPager(workerGroup.getPager());
					worker.setId(obj[0]==null?"":(String)obj[0]);
					worker.setWorkerName(obj[1]==null?"":(String)obj[1]);
					worker.setPhone(obj[2]==null?"":(String)obj[2]);
					worker.setDepartmentName(obj[3]==null?"":(String)obj[3]);
					list.add(worker);
				}
			}
		}
		return list;
	}
     /**
      * 添加分组员工
      * @param workerGroup
      * @param loginUser
      * @see com.youlb.biz.management.IWorkerGroupBiz#addWorker(com.youlb.entity.management.WorkerGroup, com.youlb.entity.privilege.Operator)
      */
	@Override
	public void addWorker(WorkerGroup workerGroup, Operator loginUser) {
		String insert ="insert into t_worker_to_group(fgroup_id,fworker_id) values(?,?)";
		 //删掉历史关系
		 String delete = "delete from t_worker_to_group where fgroup_id=?";
		 workerGroupDao.executeSql(delete, new Object[]{workerGroup.getId()});
		 //插入新关系
		 if(workerGroup.getWorkerIds()!=null&&!workerGroup.getWorkerIds().isEmpty()){
			 for(String workerId :workerGroup.getWorkerIds()){
				 workerGroupDao.executeSql(insert, new Object[]{workerGroup.getId(),workerId});
			 }
		 }
	}

}
