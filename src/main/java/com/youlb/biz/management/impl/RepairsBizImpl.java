package com.youlb.biz.management.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.management.IRepairsBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.management.Repairs;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
/**
* @ClassName: CustomerServiceBizImpl.java 
* @Description: 工单服务业务实现类 
* @author: Pengjy
* @date: 2016年6月13日
*
 */
@Service("repairsBiz")
public class RepairsBizImpl implements IRepairsBiz {
	@Autowired
    private BaseDaoBySql<Repairs> repairsDao;
	public void setRepairsDao(BaseDaoBySql<Repairs> repairsDao) {
		this.repairsDao = repairsDao;
	}


	@Override
	public String save(Repairs target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void update(Repairs target) throws BizException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void delete(Serializable id) throws BizException {
		repairsDao.delete(id);
		
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
	public Repairs get(Serializable id) throws BizException {
		Repairs repairs = repairsDao.get(id);
		String sql = "select id from t_domain where fentityid =?";
		List<String> list = repairsDao.pageFindBySql(sql, new Object[]{repairs.getRoomId()});
		if(list!=null&&!list.isEmpty()){
			repairs.setDomainId(list.get(0));
		}
		repairs.setAddress(getAddressByDomainId(repairs.getRoomId()));
		return repairs;
	}
    /**
     * 列表显示
     * @param target
     * @param loginUser
     * @return
     * @throws BizException
     * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
     */
	@Override
	public List<Repairs> showList(Repairs target,Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from(SELECT c.id,c.fordernum orderNum,c.flinkman linkman,c.froom_id roomId,c.fphone phone,c.fordertype orderType,c.fcomefrom comeFrom,")
		 .append("c.fstatus status,d.fworkername workerName,c.fdealtime dealTime,c.fdealresult dealResult,c.fcreatetime createTime,c.fservicecontent serviceContent ")
		 .append("from t_repairs c inner join t_domain td on td.fentityid=c.froom_id left join t_users u on u.id=c.fuserid  LEFT JOIN t_worker d on c.fworkerid=d.id where c.forder_nature=? ");
		 values.add(target.getOrderNature());//过滤工单和服务单
		 //普通用户过滤域
		 List<String> domainIds = loginUser.getDomainIds();
		 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			 sb.append(SearchHelper.jointInSqlOrHql(domainIds, " td.id "));
			 values.add(domainIds);
		 }
		 
		 sb.append(") t where 1=1");
		 //过滤已处理和未处理
		 if(StringUtils.isNotBlank(target.getStatus())){
			 if("unfinish".equals(target.getStatus())){
				 sb.append(" and t.status < ?");
				 values.add("3");
			 }else if("repairsFinishing".equals(target.getStatus())){
				 sb.append(" and t.status = ?");
				 values.add("3");
			 }else if("finish".equals(target.getStatus())){
				 sb.append(" and t.status > ?");
				 values.add("3");
			 }
		 }
		 if(StringUtils.isNotBlank(target.getOrderNum())){
			 sb.append(" and t.orderNum like ?");
			 values.add("%"+target.getOrderNum()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getLinkman())){
			 sb.append(" and t.linkman like ?");
			 values.add("%"+target.getLinkman()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getPhone())){
			 sb.append(" and t.phone like ?");
			 values.add("%"+target.getPhone()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getOrderType())){
			 sb.append(" and t.orderType = ?");
			 values.add(target.getOrderType());
		 }
		 if(StringUtils.isNotBlank(target.getComeFrom())){
			 sb.append(" and t.comeFrom = ?");
			 values.add(target.getComeFrom());
		 }
		 if(StringUtils.isNotBlank(target.getStartTime())){
			sb.append(" and to_char(t.createTime,'yyyy-MM-dd') >= ?");
			values.add(target.getStartTime());
		}
		if(StringUtils.isNotBlank(target.getEndTime())){
			sb.append(" and to_char(t.createTime,'yyyy-MM-dd') <= ?");
			values.add(target.getEndTime());
		}
		OrderHelperUtils.getOrder(sb, target, "t.", " t.createTime desc ");
		List<Object[]> listObj = repairsDao.pageFindBySql(sb.toString(), values.toArray(),target.getPager());
		List<Repairs> list = new ArrayList<Repairs>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj :listObj){
				Repairs custSer = new Repairs();
				custSer.setPager(target.getPager());
				custSer.setId(obj[0]==null?"":(String)obj[0]);
				custSer.setOrderNum(obj[1]==null?"":(String)obj[1]);
				custSer.setLinkman(obj[2]==null?"":(String)obj[2]);
				if(obj[3]!=null){
					custSer.setAddress(getAddressByDomainId((String)obj[3]));
				}
				custSer.setPhone(obj[4]==null?"":(String)obj[4]);
				custSer.setOrderType(obj[5]==null?"":(String)obj[5]);
				custSer.setComeFrom(obj[6]==null?"":(String)obj[6]);
				custSer.setStatus(obj[7]==null?"":(String)obj[7]);
				custSer.setWorkerName(obj[8]==null?"":(String)obj[8]);
				custSer.setDealTime(obj[9]==null?null:(Date)obj[9]);
				custSer.setDealResult(obj[10]==null?"":(String)obj[10]);
				custSer.setCreateTime(obj[11]==null?null:(Date)obj[11]);
				custSer.setServiceContent(obj[12]==null?"":(String)obj[12]);
				list.add(custSer);
			}
		}
		 return list;
	}

    /**
     * 获取展现数字数据
     * @return
     * @see com.youlb.biz.management.ICustomerServiceBiz#countArr()
     */
	@Override
	public String[] countArr(Integer orderNature,Operator loginUser) {
		String[] arr = new String[4];
		 //总数
		String sql = "select count(*) from t_repairs r inner join t_domain d on d.fentityid=r.froom_id where r.forder_nature=? ";
		StringBuilder sb = new StringBuilder(sql);
		List<Object> values = new ArrayList<Object>();
		values.add(orderNature);
		//普通用户过滤域
		 List<String> domainIds = loginUser.getDomainIds();
		 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			 sb.append(SearchHelper.jointInSqlOrHql(domainIds, " d.id "));
			 values.add(domainIds);
		 }
		 String common=sb.toString();
		 sb = new StringBuilder(common);
		int all = repairsDao.getCountBySql(sb.toString(),values.toArray());
		arr[0]=all+"";
		//已完成数
		sb.append(" and  r.fstatus >'3'");
		int finish = repairsDao.getCountBySql(sb.toString(),values.toArray());
		arr[1]=finish+"";
		//未完成数
		sb = new StringBuilder(common); 
		sb.append(" and r.fstatus in('1','2')");
		int unfinish = repairsDao.getCountBySql(sb.toString(),values.toArray());
		arr[2]=unfinish+"";
		//正在处理
		sb = new StringBuilder(common); 
		sb.append(" and  r.fstatus ='3'");
		int finishing = repairsDao.getCountBySql(sb.toString(),values.toArray());
		arr[3]=finishing+"";
		return arr;
	}


	@Override
	public void saveOrUpdate(Repairs repairs, Operator loginUser) {
		//add
		if(StringUtils.isBlank(repairs.getId())){
			//封装对象
			repairs = setRepairs(repairs);
			repairs.setReminderCount(0);
			repairsDao.add(repairs);
		}else{
			String sql = "select fentityid from t_domain where id =?";
			List<String> listObj = repairsDao.pageFindBySql(sql, new Object[]{repairs.getDomainId()});
			if(listObj!=null&&!listObj.isEmpty()){
				repairs.setRoomId(listObj.get(0));//绑定房间
			}
			String update ="update Repairs set linkman=?,phone=?,serviceContent=?,roomId=? where id=?";
			repairsDao.update(update,new Object[]{repairs.getLinkman(),repairs.getPhone(),repairs.getServiceContent(),repairs.getRoomId(),repairs.getId()});
		}
	}


	private Repairs setRepairs(Repairs repairs) {
		repairs.setStatus(SysStatic.one);//未处理
		repairs.setOrderNum(new Date().getTime()+"");//工单编号
		String sql = "select fentityid from t_domain where id =?";
		List<String> listObj = repairsDao.pageFindBySql(sql, new Object[]{repairs.getDomainId()});
		if(listObj!=null&&!listObj.isEmpty()){
			repairs.setRoomId(listObj.get(0));//绑定房间
		}
		sql = "select id from t_users where fmobile_phone=?";
		List<Integer> userId = repairsDao.pageFindBySql(sql, new Object[]{repairs.getPhone()});
		if(userId!=null&&!userId.isEmpty()){
			repairs.setUserId(userId.get(0));//绑定注册用户
		}
		sql = "SELECT t.fdepartmentid from t_department_domain t where t.fdomainid=(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ? "+
		       "union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid)SELECT r.id FROM r where r.flayer='1')";
		List<String> departmentId = repairsDao.pageFindBySql(sql, new Object[]{repairs.getDomainId()});
		if(departmentId!=null&&!departmentId.isEmpty()){
			repairs.setDepartmentId(departmentId.get(0));//物业公司id(顶级部门)
		}
		return repairs;
	}
    

	private String getAddressByDomainId(String roomId) {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE fentityid = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer >0 ORDER BY flayer),'')");
		 List<String> listObj = repairsDao.pageFindBySql(sb.toString(), new Object[]{roomId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}


}
