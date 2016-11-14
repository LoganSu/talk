package com.youlb.biz.management.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.management.IDepartmentBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.management.Department;
import com.youlb.entity.management.DepartmentTree;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
/**
 * 
* @ClassName: DepartmentBizImpl.java 
* @Description: 部门业务实现类 
* @author: Pengjy
* @date: 2016年6月14日
*
 */
@Service("departmentBiz")
public class DepartmentBizImpl implements IDepartmentBiz {
	@Autowired
    private BaseDaoBySql<Department> departmentDao;
	public void setDepartmentDao(BaseDaoBySql<Department> departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Override
	public String save(Department target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Department target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		departmentDao.delete(id);
		//删除关联
		String del = "delete from t_department_domain where fdepartmentid=?";
		departmentDao.executeSql(del, new Object[]{id});
		//删除员工
		 del = "delete from t_work where fdepartmentid=?";
		departmentDao.executeSql(del, new Object[]{id});
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
	public Department get(Serializable id) throws BizException {
		Department department = departmentDao.get(id);               
		String sql="select fdomainid from t_department_domain where fdepartmentid=?";
		List<String> domainIds = departmentDao.pageFindBySql(sql, new Object[]{id});
		department.setDomainIds(domainIds);
		return department;
	}

	@Override
	public List<Department> showList(Department target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select d.id id,d.fdepartmentname departmentName,d.fdescription description,")
		 .append("pd.fdepartmentname parentName, d.fcreatetime createTime,d.fparentid,d.ftel tel,d.faddress address ")
		 .append("from t_department d left JOIN t_department pd on pd.id=d.fparentid ");
		 if(StringUtils.isNotBlank(target.getParentId())){
			 sb.append(" where d.fparentid=? ");
			 values.add(target.getParentId());
		 }else{
			 sb.append("INNER JOIN t_department_domain tdd on tdd.fdepartmentid=d.id where d.fparentid is null ");
			 if(StringUtils.isNotBlank(target.getId())){
				 sb.append(" and d.id=? ");
				 values.add(target.getId());
			 }
			 //普通用户过滤域
			 List<String> domainIds = loginUser.getDomainIds();
			 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
				 sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tdd.fdomainid "));
				 values.add(domainIds);
			 }
 		 }
		 sb.append(" GROUP BY d.id ,d.fdepartmentname,d.fdescription,pd.fdepartmentname, d.fcreatetime,d.fparentid,d.ftel,d.faddress ");
		 sb.append(")t");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		List<Object[]> listObj = departmentDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 List<Department> list = new ArrayList<Department>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				Department dep = new Department();
				dep.setPager(target.getPager());
				dep.setId(obj[0]==null?"":(String)obj[0]);
				dep.setDepartmentName(obj[1]==null?"":(String)obj[1]);
				dep.setDescription(obj[2]==null?"":(String)obj[2]);
				dep.setParentName(obj[3]==null?"":(String)obj[3]);
				dep.setCreateTime(obj[4]==null?null:(Date)obj[4]);
				dep.setParentId(obj[5]==null?"":(String)obj[5]);
				dep.setTel(obj[6]==null?"":(String)obj[6]);
                dep.setAddress(obj[7]==null?"":(String)obj[7]);
				list.add(dep);
			}
		}
		return list;
	}

	@Override
	public void saveOrUpdate(Department department, Operator loginUser) throws BizException {
		String department_domain = "insert into t_department_domain(fdepartmentid,fdomainid) values(?,?)";
		 //添加
		 if(StringUtils.isBlank(department.getId())){
			 //设置层级
			 Integer layer = department.getLayer();
			 if(layer==null){
				 department.setLayer(0);
			 }else{
				 department.setLayer(++layer);
			 }
			 String departmentId = (String) departmentDao.add(department);
			 departmentDao.getCurrSession().flush();
			 List<String> domainIds = department.getDomainIds();
			 if(domainIds!=null){
				 for(String domainId:domainIds){
					 departmentDao.executeSql(department_domain, new Object[]{departmentId,domainId});
				 }
			 }
		 }else{
			 String update = "update t_department set fdepartmentname=?,fdescription=?,ftel=?,faddress=? where id=?";
			 departmentDao.updateSQL(update, new Object[]{department.getDepartmentName(),department.getDescription(),department.getTel(),department.getAddress(),department.getId()});
			 //删除历史
			 String delet = "delete from t_department_domain where fdepartmentid=?";
			 departmentDao.executeSql(delet,new Object[]{department.getId()});
			 //重新插入
			 List<String> domainIds = department.getDomainIds();
			 if(domainIds!=null){
				 for(String domainId:domainIds){
					 departmentDao.executeSql(department_domain, new Object[]{department.getId(),domainId});
				 }
			 }
		 }
		
	}
    /**
     * 
     * @param department
     * @param loginUser
     * @return
     * @throws BizException 
     */
	@Override
	public List<DepartmentTree> showListDepartmentTree(DepartmentTree department,Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select d.id,d.fdepartmentname,d.fparentid,d.flayer,d.fcreatetime ")
		 .append("from t_department d left JOIN t_department pd on pd.id=d.fparentid ");
		 if(StringUtils.isNotBlank(department.getParentId())){
			 sb.append(" where d.fparentid=? ");
			 values.add(department.getParentId());
		 }else{
			 sb.append("INNER JOIN t_department_domain tdd on tdd.fdepartmentid=d.id where d.fparentid is null ");
			 //普通用户过滤域
			 List<String> domainIds = loginUser.getDomainIds();
			 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
				 sb.append(SearchHelper.jointInSqlOrHql(domainIds, " tdd.fdomainid "));
				 values.add(domainIds);
			 }
 		 }
		 sb.append(" GROUP BY d.id ,d.fdepartmentname,d.fcreatetime,d.fparentid,d.flayer ");
		 sb.append(" order by d.fcreatetime");
		List<Object[]> listObj = departmentDao.pageFindBySql(sb.toString(), values.toArray());
		 List<DepartmentTree> list = new ArrayList<DepartmentTree>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				DepartmentTree dep = new DepartmentTree();
				dep.setId(obj[0]==null?"":(String)obj[0]);
				dep.setDepartmentName(obj[1]==null?"":(String)obj[1]);
				dep.setParentId(obj[2]==null?"":(String)obj[2]);
				dep.setLayer(obj[3]==null?null:(Integer)obj[3]);
				list.add(dep);
			}
		}
		return list;
	}
    /**
     * 检查社区是否已经绑定物业公司
     * @param domainId
     * @return
     * @throws BizException 
     * @see com.youlb.biz.management.IDepartmentBiz#checkDomain(java.lang.String)
     */
	@Override
	public String checkDomain(String domainId) throws BizException {
		String sql ="select fdepartmentid from t_department_domain where fdomainid=?";
		List<String> departmentId = departmentDao.pageFindBySql(sql, new Object[]{domainId});
		if(departmentId!=null&&!departmentId.isEmpty()){
			sql = "select fremark from t_domain where id=?";
			List<String> neiborName = departmentDao.pageFindBySql(sql, new Object[]{domainId});
			if(neiborName!=null&&!neiborName.isEmpty()){
				return neiborName.get(0);
			}
		}
		return null;
	}
    
}
