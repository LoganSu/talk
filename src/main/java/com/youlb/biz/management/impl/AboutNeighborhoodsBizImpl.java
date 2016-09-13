package com.youlb.biz.management.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.management.IAboutNeighborhoodsBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.management.AboutNeighborhoods;
import com.youlb.entity.management.AboutNeighborhoodsRemark;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
@Service("aboutNeighborBiz")
public class AboutNeighborhoodsBizImpl implements IAboutNeighborhoodsBiz {
	@Autowired
    private BaseDaoBySql<AboutNeighborhoods> aboutNeighborDao;
	public void setAboutNeighborDao(
			BaseDaoBySql<AboutNeighborhoods> aboutNeighborDao) {
		this.aboutNeighborDao = aboutNeighborDao;
	}
	@Autowired
    private BaseDaoBySql<AboutNeighborhoodsRemark> aboutNeighborRemarkDao;
	
	public void setAboutNeighborRemarkDao(
			BaseDaoBySql<AboutNeighborhoodsRemark> aboutNeighborRemarkDao) {
		this.aboutNeighborRemarkDao = aboutNeighborRemarkDao;
	}

	@Override
	public String save(AboutNeighborhoods target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(AboutNeighborhoods target) throws BizException {
		 

	}

	@Override
	public void delete(Serializable id) throws BizException {
		aboutNeighborDao.delete(id);

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
	public AboutNeighborhoods get(Serializable id) throws BizException {
		
		return aboutNeighborDao.get(id);
	}

	@Override
	public List<AboutNeighborhoods> showList(AboutNeighborhoods target,Operator loginUser) throws BizException {
		 StringBuilder sb = new StringBuilder();
		 sb.append("from AboutNeighborhoods where neighborDomainId=? order by forder");
		 List<AboutNeighborhoods> list = aboutNeighborDao.pageFind(sb.toString(), new Object[]{target.getNeighborDomainId()}, target.getPager());
		return list;
	}
    
	@Override
	public void saveOrUpdate(AboutNeighborhoods target,Operator loginUser) throws BizException {
		if(StringUtils.isBlank(target.getId())){
		    StringBuilder sb = new StringBuilder();
			sb.append("insert into t_about_neighborhoods (id,fheadline,ficon,fupdate_time,fstatus,forder,fhtml_url,fneighbor_domain_id,fcreatetime,fdeleteflag)values(?,?,?,now(),?,")
			.append("(select case when(max(o.forder) is null) then 0 else max(o.forder) end +1 from t_about_neighborhoods o  where o.fneighbor_domain_id=?),?,?,now(),?)");
			aboutNeighborDao.executeSql(sb.toString(), new Object[]{UUID.randomUUID().toString().replace("-", ""),
				target.getHeadline(),target.getIcon(),"2",target.getNeighborDomainId(),target.getHtmlUrl(),target.getNeighborDomainId(),1});
		}else{
			target.setUpdateTime(new Date());
			String update = "update t_about_neighborhoods set fheadline=?,ficon=?,fhtml_url=? where id=?";
			aboutNeighborDao.updateSQL(update, new Object[]{target.getHeadline(),target.getIcon(),target.getHtmlUrl(),target.getId()});
		}
		
	}

	@Override
	public void orderUp(AboutNeighborhoods aboutNeighborhoods) throws BizException {
		String updateUp ="update t_about_neighborhoods set forder=forder-1 where id=?";
		String updateDown ="update t_about_neighborhoods set forder=forder+1 where fneighbor_domain_id=? and forder=?";
		synchronized (aboutNeighborhoods) {
			int down =  aboutNeighborDao.updateSQL(updateDown, new Object[]{aboutNeighborhoods.getNeighborDomainId(),aboutNeighborhoods.getForder()-1});
			int up = aboutNeighborDao.updateSQL(updateUp, new Object[]{aboutNeighborhoods.getId()});
//			System.out.println(up);
//			System.out.println(down);
		}
	}

	@Override
	public int getMinOrder(AboutNeighborhoods aboutNeighborhoods) throws BizException {
		 String sql =" select min(forder) from t_about_neighborhoods where fneighbor_domain_id=?";
		 List<Integer> list = aboutNeighborDao.pageFindBySql(sql, new Object[]{aboutNeighborhoods.getNeighborDomainId()});
		 if(list!=null&&!list.isEmpty()){
			 return list.get(0);
		 }
		return 0;
	}

	@Override
	public int getMaxOrder(AboutNeighborhoods aboutNeighborhoods) throws BizException {
		 String sql =" select max(forder) from t_about_neighborhoods where fneighbor_domain_id=?";
		 List<Integer> list = aboutNeighborDao.pageFindBySql(sql, new Object[]{aboutNeighborhoods.getNeighborDomainId()});
		 if(list!=null&&!list.isEmpty()){
			 return list.get(0);
		 }
		return 0;
	}

	@Override
	public void orderDown(AboutNeighborhoods aboutNeighborhoods) throws BizException {
		String updateUp ="update t_about_neighborhoods set forder=forder+1 where id=?";
		String updateDown ="update t_about_neighborhoods set forder=forder-1 where fneighbor_domain_id=? and forder=?";
		synchronized (aboutNeighborhoods) {
			int down =  aboutNeighborDao.updateSQL(updateDown, new Object[]{aboutNeighborhoods.getNeighborDomainId(),aboutNeighborhoods.getForder()+1});
			int up = aboutNeighborDao.updateSQL(updateUp, new Object[]{aboutNeighborhoods.getId()});
//			System.out.println(up);
//			System.out.println(down);
		}
		
	}
    /**
     * 更新状态
     * @param aboutNeighborhoods
     * @return
     * @see com.youlb.biz.management.IAboutNeighborhoodsBiz#updateCheck(com.youlb.entity.management.AboutNeighborhoods)
     */
	@Override
	public int updateCheck(AboutNeighborhoods aboutNeighborhoods,Operator loginUser) throws BizException{
		 StringBuilder sb = new StringBuilder();
		 sb.append("update t_about_neighborhoods set fstatus=? ");
		 //取消发布
		 if("1".equals(aboutNeighborhoods.getStatus())){
			 sb.append("where id=? and fstatus= '3'");
			int i = aboutNeighborDao.updateSQL(sb.toString(), new Object[]{aboutNeighborhoods.getStatus(),aboutNeighborhoods.getId()});
			if(i<1){
				throw new BizException("已发布状态才能取消发布");
			}
			//待审核
		 }else if("2".equals(aboutNeighborhoods.getStatus())){
			 sb.append("where id=? and fstatus in('1','4')");
				int i = aboutNeighborDao.updateSQL(sb.toString(), new Object[]{aboutNeighborhoods.getStatus(),aboutNeighborhoods.getId()});
				if(i<1){
					throw new BizException("未发布或者撤回状态才能发布");
				}
			//审核通过	
		 }else if("3".equals(aboutNeighborhoods.getStatus())){
			 sb.append(",fversion=(select case when o.fversion is null then 0 else o.fversion end +1 from t_about_neighborhoods o  where o.id=?) where id=? and fstatus= '2'");
				int i = aboutNeighborDao.updateSQL(sb.toString(), new Object[]{aboutNeighborhoods.getStatus(),aboutNeighborhoods.getId(),aboutNeighborhoods.getId()});
				if(i<1){
					throw new BizException("未审核状态才能审核通过");
				}
			//撤回	
		 }else if("4".equals(aboutNeighborhoods.getStatus())){
			 sb.append("where id=? and fstatus= '2'");
				int i = aboutNeighborDao.updateSQL(sb.toString(), new Object[]{aboutNeighborhoods.getStatus(),aboutNeighborhoods.getId()});
				if(i<1){
					throw new BizException("带审核状态才能撤回");
				}
		 }
		 
		 
		 String insert ="insert into t_about_neighborhoods_remark(id,fstatus,fremark,fmain_id,foperator,fcreatetime,fdeleteflag) values(?,?,?,?,?,now(),1)";
		 //设置添加人
			String operator = "";
			//普通管理员 的真实姓名已经包含 admin
			if(SysStatic.NORMALADMIN.equals(loginUser.getIsAdmin())){
				operator= loginUser.getRealName();
			}else{
				operator = loginUser.getRealName()+"("+loginUser.getLoginName()+")";
			}
		 aboutNeighborDao.executeSql(insert, new Object[]{UUID.randomUUID().toString().replace("-", ""),
				          aboutNeighborhoods.getStatus(),aboutNeighborhoods.getRemark(),aboutNeighborhoods.getId(),operator});
		return 0;
	}
    /**
     * 查出备注列表
     * @param aboutNeighborhoods
     * @return
     * @throws BizException 
     * @see com.youlb.biz.management.IAboutNeighborhoodsBiz#showRemarkList(com.youlb.entity.management.AboutNeighborhoods)
     */
	@Override
	public List<AboutNeighborhoodsRemark> showRemarkList(AboutNeighborhoods aboutNeighborhoods) throws BizException {
		 String hql = "from AboutNeighborhoodsRemark where mainId=? order by createTime desc";
		return aboutNeighborRemarkDao.pageFind(hql, new Object[]{aboutNeighborhoods.getId()}, aboutNeighborhoods.getPager());
	}

}
