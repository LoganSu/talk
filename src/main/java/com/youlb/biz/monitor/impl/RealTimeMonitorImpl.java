package com.youlb.biz.monitor.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.monitor.IRealTimeMonitorBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.monitor.PointInfo;
import com.youlb.entity.monitor.RealTimeMonitor;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
@Service("realTimeMonitorBiz")
public class RealTimeMonitorImpl implements IRealTimeMonitorBiz {
	@Autowired
	private BaseDaoBySql<RealTimeMonitor>  realTimeMonitorDao;
	public void setRealTimeMonitorDao(
			BaseDaoBySql<RealTimeMonitor> realTimeMonitorDao) {
		this.realTimeMonitorDao = realTimeMonitorDao;
	}

	@Override
	public String save(RealTimeMonitor target)
			throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(RealTimeMonitor target)throws BizException {
		String update = "update RealTimeMonitor set remark=?,status=? where id=?";
		realTimeMonitorDao.update(update,new Object[]{target.getRemark(),"1",target.getId()});
	}

	@Override
	public void delete(Serializable id) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(String[] ids) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public RealTimeMonitor get(Serializable id)throws BizException {
		
		return realTimeMonitorDao.get(id);
	}

	@Override
	public List<RealTimeMonitor> showList(RealTimeMonitor target, Operator loginUser)throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("select * from(select d.fdomainid,r.id,s.fvalue warnType,r.fstatus status,r.fcreatetime createTime,d.fwarn_phone warnPhone from t_real_time_monitor r")
		.append(" left join t_devicecount d on d.fdevicecount=r.fdevicecount left join t_staticparam s on s.fkey=r.fwarn_type ");
		
		//普通用户过滤域
		 List<String> domainIds = loginUser.getDomainIds();
		 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
			 sb.append(SearchHelper.jointInSqlOrHql(domainIds, " d.fdomainid "));
			 values.add(domainIds);
		 }
		 sb.append(")t");
		 OrderHelperUtils.getOrder(sb, target, "t.", " t.status,t.creaTetime desc ");
		List<Object[]> listObj = realTimeMonitorDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		List<RealTimeMonitor> list = new ArrayList<RealTimeMonitor>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				RealTimeMonitor r = new RealTimeMonitor();
				r.setPager(target.getPager());
				r.setAddress(obj[0]==null?"":findAddressByRoomId((String)obj[0]));
				r.setId(obj[1]==null?"":(String)obj[1]);
				r.setWarnType(obj[2]==null?"":(String)obj[2]);
				r.setStatus(obj[3]==null?"":(String)obj[3]);
				r.setCreateTime(obj[4]==null?null:(Date)obj[4]);
				r.setWarnPhone(obj[5]==null?"":(String)obj[5]);
				list.add(r);
			}
		}
		return list;
	}

	/**获取地址信息
	 * @param cardInfo
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.access.IPermissionBiz#findAddress(com.youlb.entity.access.CardInfo)
	 */
	private String findAddressByRoomId(String domainId) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer is not null ORDER BY flayer),'')");
		 List<String> listObj = realTimeMonitorDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}
    /**
     * 获取告警地址信息
     * @param ids
     * @return
     * @throws BizException 
     * @see com.youlb.biz.monitor.IRealTimeMonitorBiz#getData(java.lang.String[])
     */
	@Override
	public List<PointInfo> getData(String[] ids) throws BizException {
		if(ids!=null&&ids.length>0){
			List<PointInfo> list = new ArrayList<PointInfo>();
			StringBuffer sb = new StringBuffer();
             sb.append("SELECT dc.flongitude,dc.flatitude,d.fremark,d.id from t_real_time_monitor r ")
             .append("INNER JOIN t_devicecount dc on dc.fdevicecount=r.fdevicecount INNER JOIN t_domain d on d.id=dc.fdomainid where 1=1 ");
             sb.append(SearchHelper.jointInSqlOrHql(Arrays.asList(ids), "r.id"));
             List<Object[]> listObj = realTimeMonitorDao.pageFindBySql(sb.toString(), new Object[]{Arrays.asList(ids)});
             if(listObj!=null&&!listObj.isEmpty()){
            	 for(Object[] obj:listObj){
            		 PointInfo point = new PointInfo();
            		 point.setX(obj[0]==null?null:(Double)obj[0]);
            		 point.setY(obj[1]==null?null:(Double)obj[1]);
            		 point.setName(obj[2]==null?"":(String)obj[2]);
            		 point.setAddress(obj[3]==null?"":findAddressByRoomId((String)obj[3]));
            		 list.add(point);
            	 }
            	 return list;
             }
             
		}
		return null;
	}


	@Override
	public RealTimeMonitor getWarnInfoById(String id) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("select d.fdomainid,r.id,s.fvalue,r.fstatus,r.fcreatetime,d.fwarn_phone from t_real_time_monitor r")
		.append(" left join t_devicecount d on d.fdevicecount=r.fdevicecount left join t_staticparam s on s.fkey=r.fwarn_type where r.id=?");
		List<Object[]> listObj = realTimeMonitorDao.pageFindBySql(sb.toString(), new Object[]{id});
		List<RealTimeMonitor> list = new ArrayList<RealTimeMonitor>();
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				RealTimeMonitor r = new RealTimeMonitor();
				r.setAddress(obj[0]==null?"":findAddressByRoomId((String)obj[0]));
				r.setId(obj[1]==null?"":(String)obj[1]);
				r.setWarnType(obj[2]==null?"":(String)obj[2]);
				r.setStatus(obj[3]==null?"":(String)obj[3]);
				r.setCreateTime(obj[4]==null?null:(Date)obj[4]);
				r.setWarnPhone(obj[5]==null?"":(String)obj[5]);
				list.add(r);
				return r;
			}
		}
		return null;
	}

}
