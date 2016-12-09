package com.youlb.biz.baseInfo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.baseInfo.ICarrierBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.baseInfo.Carrier;
import com.youlb.entity.common.Pager;
import com.youlb.entity.domainName.DomainName;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Privilege;
import com.youlb.entity.privilege.Role;
import com.youlb.utils.common.SHAEncrypt;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;
import com.youlb.utils.sms.SmsUtil;

/** 
 * @ClassName: CarrierBizImpl.java 
 * @Description: 运营商信息实现类 
 * @author: Pengjy
 * @date: 2015年8月28日
 * 
 */
@Service("carrierBiz")
public class CarrierBizImpl implements ICarrierBiz {
	@Autowired
	private BaseDaoBySql<Carrier> carrierSqlDao;
	public void setCarrierSqlDao(BaseDaoBySql<Carrier> carrierSqlDao) {
		this.carrierSqlDao = carrierSqlDao;
	}
	@Autowired
	private BaseDaoBySql<Operator> operatorSqlDao;
	@Autowired
	private BaseDaoBySql<Role> roleSqlDao;
	@Autowired
    private BaseDaoBySql<Privilege> privilegeSqlDao;
	public void setPrivilegeSqlDao(BaseDaoBySql<Privilege> privilegeSqlDao) {
		this.privilegeSqlDao = privilegeSqlDao;
	}
	public void setOperatorSqlDao(BaseDaoBySql<Operator> operatorSqlDao) {
		this.operatorSqlDao = operatorSqlDao;
	}

	public void setRoleSqlDao(BaseDaoBySql<Role> roleSqlDao) {
		this.roleSqlDao = roleSqlDao;
	}
	
	@Autowired
	private BaseDaoBySql<DomainName> domainNameSqlDao;
	public void setDomainNameSqlDao(BaseDaoBySql<DomainName> domainNameSqlDao) {
		this.domainNameSqlDao = domainNameSqlDao;
	}
	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Carrier target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Carrier target) throws BizException {
		// TODO Auto-generated method stub

	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		//删除用户表纪录
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM t_operator t where t.id in (SELECT tor.foperatorid from t_carrier c INNER JOIN t_role r on r.fcarrierid=c.id ")
		.append(" INNER JOIN t_operator_role tor on tor.froleid=r.id where c.id=?)");
		carrierSqlDao.executeSql(sb.toString(), new Object[]{id});
		carrierSqlDao.delete(id);
	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		 if(ids!=null){
			 for(String id:ids){
				 delete(id);
			 }
		 }

	}

	/**
	 * @param id
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#get(java.io.Serializable)
	 */
	@Override
	public Carrier get(Serializable id) throws BizException {
		Carrier carrier = carrierSqlDao.get(id);
		//获取域集合
		String sql ="select m.fdomainid from t_carrier c INNER JOIN t_carrier_domain m on m.fcarrierId=c.id where c.id=?";
		List<String> domainids = carrierSqlDao.pageFindBySql(sql,new Object[]{id});
		carrier.setTreecheckbox(domainids);
		return carrier;
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Carrier> showList(Carrier target, Operator loginUser)throws BizException {
		List<Carrier> list = new ArrayList<Carrier>();
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select c.id id,c.fcarriername carrierName,c.ftel tel,c.fpostcode postcode,c.ffax fax,c.faddress address,c.fremark remark,")
		 .append("c.fisnormal isNormal,c.FCREATETIME createTime,c.fcarrierNum carrierNum from t_carrier c inner join t_carrier_domain tcd on tcd.fcarrierid=c.id where 1=1");
		 //普通运营商只能看到自己所属运营商 看不到admin运营商
		 if(SysStatic.NORMALCARRIER.equals(loginUser.getCarrier().getIsNormal())){
			 sb.append(" and c.id =?");
			 values.add(loginUser.getCarrier().getId());
		 //特殊运营商不看到自己所属运营商
		 }else{
		    sb.append(" and c.fisnormal !=?");
		    values.add(SysStatic.SPECIALCARRIER);
		 }
		 List<String> domainIds = loginUser.getDomainIds();
			if(domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"tcd.fdomainid"));
				values.add(domainIds);
			}else{
				return list;
			}
		 sb.append(" GROUP BY c.id,c.fcarriername,c.ftel,c.fpostcode,c.ffax,c.faddress,c.fremark,c.FCREATETIME,c.fisnormal,c.fcarrierNum) t where 1=1");
		 if(StringUtils.isNotBlank(target.getCarrierName())){
			 sb.append(" and t.carrierName like ?");
			 values.add("%"+target.getCarrierName()+"%");	 
		 }
        if(StringUtils.isNotBlank(target.getAddress())){
        	sb.append(" and t.address like ?");
			values.add("%"+target.getAddress()+"%");	 
		 }
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.createTime");
		 List<Object[]> listObj = carrierSqlDao.pageFindBySql(sb.toString(),values.toArray(), target.getPager());
		 if(listObj!=null&&!listObj.isEmpty()){
			 for(Object[] obj:listObj){
				 Carrier carrier = new Carrier();
				 carrier.setId(obj[0]==null?null:(String)obj[0]);
				 carrier.setCarrierName(obj[1]==null?null:(String)obj[1]);
				 carrier.setTel(obj[2]==null?null:(String)obj[2]);
                 carrier.setPostcode(obj[3]==null?null:(String)obj[3]);
                 carrier.setFax(obj[4]==null?null:(String)obj[4]);
                 carrier.setAddress(obj[5]==null?null:(String)obj[5]);
                 carrier.setRemark(obj[6]==null?null:(String)obj[6]);
                 carrier.setIsNormal(obj[7]==null?null:(String)obj[7]);
                 carrier.setCreateTime(obj[8]==null?null:(Date)obj[8]);
                 carrier.setCarrierNum(obj[9]==null?null:(String)obj[9]);
                 carrier.setPager(target.getPager());
                 list.add(carrier);
			 }
		 }
		 
		return list;
	}

	/**
	 * @param carrier
	 * @throws BizException 
	 * @see com.youlb.biz.baseInfo.ICarrierBiz#saveOrUpdate(com.youlb.entity.baseInfo.Carrier)
	 */
	@Override
	public void saveOrUpdate(Carrier carrier) throws BizException {
		String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
		//add
		if(StringUtils.isBlank(carrier.getId())){
			carrier.setIsNormal(SysStatic.NORMALCARRIER);//普通运营商
			String carrierId = (String) carrierSqlDao.add(carrier);
			carrierSqlDao.getCurrSession().flush();
			List<String> domainIds = carrier.getTreecheckbox();
			if(domainIds!=null&&!domainIds.isEmpty()){
				//插入中间表
				for(String domainId:domainIds){
					carrierSqlDao.executeSql(sql, new Object[]{domainId,carrierId});
				}
				//创建admin用户
				Operator operator = new Operator();
				operator.setLoginName(SysStatic.ADMIN);//设置admin用户
				//获取随机密码
				String random = SmsUtil.random();
				operator.setPassword(SHAEncrypt.digestPassword(random));//设置获取随机密码
				operator.setIsAdmin(SysStatic.NORMALADMIN);//管理员
				operator.setPhone(carrier.getTel());
				operator.setRealName(carrier.getCarrierName()+"("+SysStatic.ADMIN+")");
				String operatorId = (String) operatorSqlDao.add(operator);
				//创建一个默认角色
				Role role = new Role();
				role.setRoleName(carrier.getCarrierName()+"("+SysStatic.ADMIN+")");//角色名称为运营商名加上admin
				role.setDescription(SysStatic.COMMON_ROLE_DESCRAPTION);//描述
				role.setCarrierId(carrierId);//设置运营商
				role.setIsAdmin(SysStatic.NORMALADMIN);//管理员角色
				String roleId = (String) roleSqlDao.add(role);
				roleSqlDao.getCurrSession().flush();
				String addRoleOperator ="insert into t_operator_role (foperatorid,froleid) values(?,?)";
				roleSqlDao.executeSql(addRoleOperator, new Object[]{operatorId,roleId});
				//发送密码到手机上
				Boolean b = SmsUtil.sendSMS(carrier.getTel(), "【赛翼智能】欢迎使用友邻邦产品，为您创建的"+carrier.getCarrierName()+"运营商密码为："+random+"，请妥善保管");
				 //TODO LOG
				 //添加运营商域名
				DomainName domainName = domainNameSqlDao.get(carrier.getDomainNameId());
				DomainName sub = new DomainName();
				sub.setDomain(domainName.getDomain()+".sayee.cn/"+carrier.getCarrierNum()+"/login.do");
				sub.setPlatform(domainName.getPlatform());
				sub.setFname(carrier.getCarrierName()+"运营商");
				sub.setLayer(domainName.getLayer()+1);
				sub.setParentid(domainName.getId());
				domainNameSqlDao.add(sub);
				//设置角色的权限
//			List<Privilege> pList = privilegeSqlDao.find("from Privilege p where p.type=?",new Object[]{SysStatic.NORMALPRIVILEGE});
//			if(pList!=null&&!pList.isEmpty()){
//				String addRolePrivilege ="insert into t_role_privilege (froleid,fprivilegeid) values(?,?)";
//				for(Privilege privilege:pList){
//					privilegeSqlDao.executeSql(addRolePrivilege,  new Object[]{roleId,privilege.getId()});
//				}
//			}
			}
		//update
		}else{
			carrierSqlDao.update(carrier);
			//修改admin用户手机号
			StringBuilder sb = new StringBuilder();
			sb.append("update t_operator set fphone=?,frealname=? where id=(SELECT o.id from t_operator o INNER JOIN t_operator_role tor on tor.foperatorid=o.id ")
			.append(" INNER JOIN t_role r on r.id =tor.froleid where r.fcarrierid=? and o.fisadmin='1')");
			carrierSqlDao.updateSQL(sb.toString(), new Object[]{carrier.getTel(),carrier.getCarrierName()+"("+SysStatic.ADMIN+")",carrier.getId()});
			//如果有就先根据用户删掉中间表 
			 String dele = "delete from t_carrier_domain where fcarrierId=?";
			 carrierSqlDao.executeSql(dele, new Object[]{carrier.getId()});
			 //更新用户角色中间表
			 List<String> domainIds = carrier.getTreecheckbox();
				if(domainIds!=null&&!domainIds.isEmpty()){
					//插入新关系
					for(String domainId:domainIds){
						carrierSqlDao.executeSql(sql, new Object[]{domainId,carrier.getId()});
					}
				}
		}
	}
	/**判断运营商简称是否存在 
	 * @param carrier
	 * @return
	 * @throws BizException 
	 * @see com.youlb.biz.baseInfo.ICarrierBiz#checkCarrierNumExist(com.youlb.entity.baseInfo.Carrier)
	 */
	@Override
	public boolean checkCarrierNumExist(Carrier carrier) throws BizException {
		StringBuilder sb = new StringBuilder("from Carrier t where t.carrierNum=? ");
		List<Object> values = new ArrayList<Object>();
		values.add(carrier.getCarrierNum());
		//更新判断排除自己
		if(StringUtils.isNotBlank(carrier.getId())){
			sb.append(" and id != ?");
			values.add(carrier.getId());
		}
		List<Carrier> cList = carrierSqlDao.find(sb.toString(), values.toArray());
		if(cList!=null&&!cList.isEmpty()){
			return true;
		}
		return false;
	}
	 /**
     * 检查社区是否已经绑定运营商  如果绑定返回社区名称 没绑定返回空
     * @param treecheckbox
     * @return
	 * @throws BizException 
     */
	@Override
	public String checkHasChecked(List<String> treecheckbox) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT array_to_string(ARRAY( ")
		.append("SELECT d.fremark from t_carrier_domain tcd INNER JOIN t_domain d on d.id=tcd.fdomainid ")
		.append("where 1=1 " );
		sb.append(SearchHelper.jointInSqlOrHql(treecheckbox, " d.id "));
		sb.append(" and tcd.fcarrierid!='1' and d.flayer='1'),'，')");
		List<String> list = carrierSqlDao.pageFindBySql(sb.toString(), new Object[]{treecheckbox});
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

}
