package com.youlb.biz.management.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.management.IBillManageBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.management.BillManage;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.DateHelper;
/**
 * 
* @ClassName: BillManageBizImpl.java 
* @Description: 费用管理业务实现类 
* @author: Pengjy
* @date: 2016年10月17日
*
 */
@Service("billManageBiz")
public class BillManageBizImpl implements IBillManageBiz {
	@Autowired
    private BaseDaoBySql<BillManage> billManageDao;
	public BaseDaoBySql<BillManage> getBillManageDao() {
		return billManageDao;
	}

	@Override
	public String save(BillManage target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(BillManage target) throws BizException {
		// TODO Auto-generated method stub

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
	public BillManage get(Serializable id) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BillManage> showList(BillManage target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder();
		 List<BillManage> list = new ArrayList<BillManage>();
		 sb.append("select b.id,b.fbill_num billNum,b.fname fname,b.fdomain_id address,b.fphone phone,b.ftype ftype,b.fmoney money,")
		 .append("to_char(b.fstart_time,'yyyy-MM-dd HH24:mi:ss') startTimeStr,to_char(b.fend_time,'yyyy-MM-dd HH24:mi:ss') endTimeStr,")
		 .append("b.fstatus statusStr,b.fpay_platform payPlatform,to_char(b.fpay_time,'yyyy-MM-dd HH24:mi:ss') payTime,b.fdesc fdesc from t_billmanage b");
		 List<Object[]> listObj = billManageDao.pageFindBySql(sb.toString(), new Object[]{}, target.getPager());
		if(listObj!=null&&!listObj.isEmpty()){
			for(Object[] obj:listObj){
				BillManage bill = new BillManage();
				 bill.setPager(target.getPager());
				 bill.setId(obj[0]==null?"":(String)obj[0]);
				 bill.setBillNum(obj[1]==null?"":(String)obj[1]);
				 bill.setFname(obj[2]==null?"":(String)obj[2]);
				 bill.setAddress(getAddressByDomainId(obj[3]==null?"":(String)obj[3]));
				 bill.setPhone(obj[4]==null?"":(String)obj[4]);
				 bill.setFtype(obj[5]==null?null:(Integer)obj[5]);
				 bill.setMoney(obj[6]==null?null:((Double)obj[6]));
				 bill.setStartTimeStr(obj[7]==null?"":(String)obj[7]);
				 bill.setEndTimeStr(obj[8]==null?"":(String)obj[8]);
				 bill.setStatus(obj[9]==null?null:(Integer)obj[9]);
				 bill.setPayPlatform(obj[10]==null?null:(Integer)obj[10]);
				 bill.setPayTimeStr(obj[11]==null?"":(String)obj[11]);
				 bill.setDesc(obj[12]==null?"":(String)obj[12]);
				 list.add(bill);
			}
		}
		return list;
	}

	@Override
	public void saveOrUpdate(BillManage billManage, Operator loginUser) throws BizException {
		  //转换时间
		billManage.setStartTime(DateHelper.strParseDate(billManage.getStartTimeStr()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		billManage.setEndTime(DateHelper.strParseDate(billManage.getEndTimeStr()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));

		
		 if(StringUtils.isBlank(billManage.getId())){
			 billManage.setId(null);
			//生成序号8位数
		    Session session = billManageDao.getCurrSession();
			SQLQuery query = session.createSQLQuery("SELECT '1'||substring('000000'||nextval('t_billnum_seq'),length(currval('t_billnum_seq')||'')) ");
			List<String> list =  query.list();
			billManage.setBillNum(list.get(0));
			billManageDao.add(billManage);
		 }
		
	}
	
	 /**
     * 通过域id获取地址
     * @param roomId
     * @return
     * @throws BizException
     */
	private String getAddressByDomainId(String domainId) throws BizException {
		if(StringUtils.isBlank(domainId)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer >0 ORDER BY flayer),'')");
		 List<String> listObj = billManageDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}

}
