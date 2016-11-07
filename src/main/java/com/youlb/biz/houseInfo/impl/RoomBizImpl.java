package com.youlb.biz.houseInfo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.youlb.biz.houseInfo.IDomainBiz;
import com.youlb.biz.houseInfo.IRoomBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.common.Domain;
import com.youlb.entity.common.Pager;
import com.youlb.entity.houseInfo.Room;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.common.SysStatic;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.helper.OrderHelperUtils;
import com.youlb.utils.helper.SearchHelper;

/** 
 * @ClassName: RoomBizImpl.java 
 * @Description: 房间信息管理业务实现类 
 * @author: Pengjy
 * @date: 2015年6月30日
 * 
 */
@Service
@Component("roomBiz")
public class RoomBizImpl implements IRoomBiz {
	
	@Autowired
	private BaseDaoBySql<Room> roomSqlDao;
	public void setRoomSqlDao(BaseDaoBySql<Room> roomSqlDao) {
		this.roomSqlDao = roomSqlDao;
	}
	@Autowired
	private BaseDaoBySql<Domain> domainSqlDao;
	public void setDomainSqlDao(BaseDaoBySql<Domain> domainSqlDao) {
		this.domainSqlDao = domainSqlDao;
	}
	@Autowired
    private IDomainBiz domainBiz;
	public void setDomainBiz(IDomainBiz domainBiz) {
		this.domainBiz = domainBiz;
	}

	/**
	 * @param target
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#save(java.io.Serializable)
	 */
	@Override
	public String save(Room target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param target
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#update(java.io.Serializable)
	 */
	@Override
	public void update(Room target) throws BizException {
		StringBuilder sb = new StringBuilder();
		sb.append("update Room set roomNum=?,roomFloor=?,certificateNum=?,roomType=?,purpose=?,orientation=?,decorationStatus=?,roomArea=?, ")
		.append("useArea=?,gardenArea=?,useStatus=?,remark=? where id=?");
		roomSqlDao.update(sb.toString(), new Object[]{target.getRoomNum(),target.getRoomFloor(),target.getCertificateNum(),target.getRoomType(),
			             target.getPurpose(),target.getOrientation(),target.getDecorationStatus(),target.getRoomArea(),target.getUseArea(),
			             target.getGardenArea(),target.getUseStatus(),target.getRemark(),target.getId()});
	}

	/**
	 * @param id
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.io.Serializable)
	 */
	@Override
	public void delete(Serializable id) throws BizException {
		roomSqlDao.delete(id);
		//删除domain里面的数据
		domainBiz.deleteByEntityId(id);
		//删除sip账号
		String sql = "delete from users t where t.local_sip=(select fsipnum from t_room where id=?) and t.sip_type='1'";
		roomSqlDao.executeSql(sql, new Object[]{id});

	}

	/**
	 * @param ids
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) throws BizException {
		if(ids!=null&&ids.length>0){
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
	public Room get(Serializable id) throws BizException {
		
		return roomSqlDao.get(id);
	}


	/**
	 * @param room
	 * @throws BizException 
	 * @throws NumberFormatException 
	 * @see com.youlb.biz.room.IRoomBiz#saveOrUpdate(com.youlb.entity.room.Room)
	 */
	@Override
	public void saveOrUpdate(Room room,Operator loginUser) throws NumberFormatException, BizException {
		//add
		if(StringUtils.isBlank(room.getId())){
			Session session = domainSqlDao.getCurrSession();
			String sipNum = getSipNum(room.getParentId());
			room.setSipNum(sipNum+room.getRoomNum());//设置sip账号
			room.setPassword(SysStatic.ROOMDEFULTPASSWORD);//设置默认密码
			//获取分组号
			SQLQuery group = session.createSQLQuery("SELECT '8'||substring('0000000'||nextval('tbl_room_sip_group'),length(currval('tbl_room_sip_group')||'')) ");
			List<String> groupList = group.list();
			room.setSipGroup(Integer.parseInt(groupList.get(0)));
			//插入sip账号到数据库注册
			String roomId = (String) roomSqlDao.add(room);
			//添加真正的sip账号fs拨号使用
			SQLQuery query = session.createSQLQuery("SELECT '1'||substring('00000000'||nextval('tbl_sipcount_seq'),length(currval('tbl_sipcount_seq')||'')) ");
		    List<String> list =  query.list();
		    String addSip ="insert into users (user_sip,user_password,local_sip,sip_type) values(?,?,?,?)";
		    //使用uuid为sip密码
		    String password = UUID.randomUUID().toString().replace("-", "");
			domainSqlDao.executeSql(addSip, new Object[]{Integer.parseInt(list.get(0)),password,sipNum+room.getRoomNum(),"1"});//房间sip账号类型为1
//			String insertSip = "insert into users (user_sip,user_password) values(?,?)";
//			//时间戳密码
//			Calendar c = Calendar.getInstance();
//			long timeInMillis = c.getTimeInMillis();
//			roomSqlDao.executeSql(insertSip, new Object[]{sipNum, timeInMillis+""});
			
			Domain domain = new Domain();
			domain.setEntityId(roomId);
			domain.setLayer(SysStatic.ROOM);//房间层
			domain.setRemark(room.getRoomNum());
			domain.setParentId(room.getParentId());//domain的parentId
			String domainId = (String) domainBiz.save(domain);
			loginUser.getDomainIds().add(domainId);
			domainSqlDao.getCurrSession().flush();
			//域跟运营商绑定
			String sql ="insert into t_carrier_domain (fdomainid,fcarrierid) values(?,?)";
			domainSqlDao.executeSql(sql, new Object[]{domainId,loginUser.getCarrier().getId()});
			
		}else{
			update(room);
			//更新与对象
			domainBiz.update(room.getRoomNum(),room.getId());
		}
		
	}
	public static void main(String[] args) {
		String s = UUID.randomUUID().toString().replace("-", "");
	}
	/**
	 * @param domainId
	 * @return
	 * @throws BizException 
	 */
	private String getSipNum(String domainId) throws BizException {
		String sql ="select d.id,d.flayer from t_domain d where d.id=?";
		List<Object[]> list = domainSqlDao.pageFindBySql(sql, new Object[]{domainId});
		StringBuilder sb = new StringBuilder();
		getSipNumDetail(list.get(0),sb);
		return sb.toString();
	}

	/**
	 * @param obj
	 * @return
	 * @throws BizException 
	 */
	private Object[] getSipNumDetail(Object[] obj,StringBuilder sb) throws BizException {
		if(obj!=null){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT td.id,td.flayer,");
			if(obj[1].equals(SysStatic.AREA)){
				sql.append("t.fareanum ");
			}else if(obj[1].equals(SysStatic.NEIGHBORHOODS)){
				sql.append("t.fneibnum ");
			}else if(obj[1].equals(SysStatic.BUILDING)){
				sql.append("t.fbuildingnum ");
			}else if(obj[1].equals(SysStatic.UNIT)){
				sql.append("t.funitnum ");
			}
			sql.append("from  t_domain d INNER JOIN t_domain td on td.id=d.fparentid INNER JOIN ");
			if(obj[1].equals(SysStatic.AREA)){
				sql.append(" t_area ");
			}else if(obj[1].equals(SysStatic.NEIGHBORHOODS)){
				sql.append(" t_neighborhoods ");
			}else if(obj[1].equals(SysStatic.BUILDING)){
				sql.append("t_building ");
			}else if(obj[1].equals(SysStatic.UNIT)){
				sql.append("t_unit ");
			}
			
			sql.append(" t ON t.id=d.fentityid  where d.id = ? ");
			String id = (String)obj[0];
			List<Object[]> list = domainSqlDao.pageFindBySql(sql.toString(), new Object[]{id});
			if(!list.isEmpty()){
				Object[] subObj = list.get(0);
				sb.insert(0, subObj[2]);
				if(subObj[1]!=null){
					getSipNumDetail(subObj, sb);
				}
				return subObj;
			}
		}
		return null;
	}

	/**绑定户主
	 * @param room
	 * @see com.youlb.biz.room.IRoomBiz#bindingRoom(com.youlb.entity.room.Room)
	 */
	@Override
	public void bindingRoom(Room room) {
//		//更新room的registUserId
//		String hql ="update Room t set t.hostInfoId = ? where t.id = ?"; 
//		roomSqlDao.update(hql, new Object[]{room.getDwellerId(),room.getId()});
//		//生成户主的sip账号 TODO
	}

	/**解除绑定户主
	 * @param room
	 * @throws BizException 
	 * @see com.youlb.biz.room.IRoomBiz#unbindingRoom(com.youlb.entity.room.Room)
	 */
	@Override
	public void unbindingRoom(Room room) throws BizException {
		String hql ="update Room t set t.hostInfoId = null where t.id = ?"; 
		roomSqlDao.update(hql, new Object[]{room.getId()});
	}

	/**
	 * @param target
	 * @param loginUser
	 * @return
	 * @throws BizException
	 * @see com.youlb.biz.common.IBaseBiz#showList(java.io.Serializable, com.youlb.entity.privilege.Operator)
	 */
	@Override
	public List<Room> showList(Room target, Operator loginUser)throws BizException {
		 List<Room> list = new ArrayList<Room>();
		 StringBuilder sb = new StringBuilder();
		 List<Object> values = new ArrayList<Object>();
		 sb.append("select * from (select r.id id,r.FROOMNUM roomNum,r.FROOMFLOOR roomFloor,r.FCERTIFICATENUM certificateNum," )
		 .append("r.FROOMTYPE roomType,r.FPURPOSE purpose,r.FORIENTATION orientation,r.FDECORATIONSTATUS decorationStatus,r.FROOMAREA roomArea,")
		 .append("r.FUSEAREA useArea,r.FGARDENAREA gardenArea,r.FUSESTATUS useStatus,r.FREMARK remark,r.fcardcount cardCount, r.FCREATETIME createTime")
		 .append(" from t_room r inner join t_domain d on d.fentityid = r.id where 1=1 ");
		 if(StringUtils.isNotBlank(target.getParentId())){
			 sb.append(" and d.fparentid=? ");
			 values.add(target.getParentId());
		 }else{
			 return list;
		 }
		 //处理发卡页面权限(发卡页面管理员也需要看到)
		 List<String> domainIds = loginUser.getDomainIds();
		 if(!SysStatic.SPECIALADMIN.equals(loginUser.getIsAdmin())&&domainIds!=null&&!domainIds.isEmpty()){
				sb.append(SearchHelper.jointInSqlOrHql(domainIds,"d.id"));
				values.add(domainIds);
			}
		 
//		 if(domainIds!=null&&!domainIds.isEmpty()){
//				sb.append(SearchHelper.filterDomain(domainIds,"d.id"));
//				values.add(domainIds);
//			}
			if(StringUtils.isNotBlank(target.getRoomNum())){
				sb.append("and r.FROOMNUM like ?");
				values.add("%"+target.getRoomNum()+"%");
			}
			if(target.getRoomFloor()!=null){
				sb.append("and r.FROOMFLOOR = ?");
				values.add(target.getRoomFloor());
			}
			if(StringUtils.isNotBlank(target.getCertificateNum())){
				sb.append("and r.FCERTIFICATENUM like ?");
				values.add("%"+target.getCertificateNum()+"%");
			}
		 sb.append(") t");
		 OrderHelperUtils.getOrder(sb, target, "t.", "t.roomNum");
		 List<Object[]> listObj = roomSqlDao.pageFindBySql(sb.toString(), values.toArray(), target.getPager());
		 if(listObj!=null&&!listObj.isEmpty()){
			//设置分页
//			Pager pager = target.getPager();
//			pager.setTotalRows(listObj.size());
			 for(Object[] obj:listObj){
				 Room room = new Room();
				    room.setPager(target.getPager());
				    room.setId(obj[0]==null?"":(String)obj[0]);
				    room.setRoomNum(obj[1]==null?"":(String)obj[1]);
					room.setRoomFloor(obj[2]==null?null:((Integer)obj[2]));
					room.setCertificateNum(obj[3]==null?"":(String)obj[3]);
					room.setDwellerId(obj[4]==null?"":(String)obj[4]);
					room.setRoomType(obj[4]==null?"":(String)obj[4]);
					room.setPurpose(obj[5]==null?"":(String)obj[5]);
					room.setOrientation(obj[6]==null?"":(String)obj[6]);
					room.setDecorationStatus(obj[7]==null?"":(String)obj[7]);
					room.setRoomArea(obj[8]==null?"":(String)obj[8]);
					room.setUseArea(obj[9]==null?"":(String)obj[9]);
					room.setGardenArea(obj[10]==null?"":(String)obj[10]);
					room.setUseStatus(obj[11]==null?"":(String)obj[11]);
					room.setRemark(obj[12]==null?"":(String)obj[12]);
					room.setCardCount(obj[13]==null?0:((Integer)obj[13]));
					list.add(room);
			 }
		 }
		 return list;
	}

	@Override 
	public String getAddressByDomainId(String domainId) throws BizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select array_to_string (ARRAY(WITH RECURSIVE r AS (SELECT * FROM t_domain WHERE id = ?")
		.append(" union ALL SELECT t_domain.* FROM t_domain, r WHERE t_domain.id = r.fparentid) SELECT  fremark FROM r where flayer >0 ORDER BY flayer),'')");
		 List<String> listObj = roomSqlDao.pageFindBySql(sb.toString(), new Object[]{domainId});
		 if(listObj!=null&&!listObj.isEmpty()){
			 return listObj.get(0);
		 }
		return null;
	}

}
