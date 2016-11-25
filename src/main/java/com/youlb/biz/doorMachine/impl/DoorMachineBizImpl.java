package com.youlb.biz.doorMachine.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.doorMachine.IDoorMachineBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.doorMachine.DoorMachine;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
@Service("doorMachineBiz")
public class DoorMachineBizImpl implements IDoorMachineBiz {
	@Autowired
	private BaseDaoBySql<DoorMachine> doorMachineSqlDao;
	public void setDoorMachineSqlDao(BaseDaoBySql<DoorMachine> doorMachineSqlDao) {
		this.doorMachineSqlDao = doorMachineSqlDao;
	}

	@Override
	public String save(DoorMachine target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(DoorMachine target) throws BizException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Serializable id) throws BizException {
		doorMachineSqlDao.delete(id);

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
	public DoorMachine get(Serializable id) throws BizException {
		
		return doorMachineSqlDao.get(id);
	}

	@Override
	public List<DoorMachine> showList(DoorMachine target, Operator loginUser)throws BizException {
		 StringBuilder sb = new StringBuilder("from DoorMachine where 1=1");
		 List<Object> values = new ArrayList<Object>();
		 if(StringUtils.isNotBlank(target.getSoftwareType())){
			 sb.append("and softwareType like ?");
			 values.add("%"+target.getSoftwareType()+"%");
		 }
		 if(StringUtils.isNotBlank(target.getHardwareModel())){
			 sb.append("and hardwareModel like ?");
			 values.add("%"+target.getHardwareModel()+"%");
		 }
		 
		return doorMachineSqlDao.pageFind(sb.toString(), values.toArray(), target.getPager());
	}

	@Override
	public void saveOrUpdate(DoorMachine doorMachine, Operator loginUser) throws BizException {
		if(StringUtils.isBlank(doorMachine.getId())){
			doorMachine.setId(null);
			doorMachineSqlDao.add(doorMachine);
		}else{
			doorMachineSqlDao.update(doorMachine);
		}
		
	}
    /**
     * 检查组合是否已经存在
     * @param doorMachine
     * @return
     * @throws BizException 
     * @see com.youlb.biz.doorMachine.IDoorMachineBiz#checkExist(com.youlb.entity.doorMachine.DoorMachine)
     */
	@Override
	public boolean checkExist(DoorMachine doorMachine) throws BizException {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new ArrayList<Object>();
		sb.append("from DoorMachine where softwareType=? and hardwareModel=?");
		values.add(doorMachine.getSoftwareType());
		values.add(doorMachine.getHardwareModel());
		if(StringUtils.isNotBlank(doorMachine.getId())){
			sb.append(" and id != ?");
			values.add(doorMachine.getId());
		}
		 List<DoorMachine> list = doorMachineSqlDao.find(sb.toString(), values.toArray());
		 if(list!=null&&!list.isEmpty()){
			 return true;
		 }
		return false;
	}
    /**
     * 获取软件型号列表
     * @return
     * @throws BizException 
     * @see com.youlb.biz.doorMachine.IDoorMachineBiz#getSoftwareTypeList()
     */
	@Override
	public List<String> getSoftwareTypeList() throws BizException {
		 String hql = "select  softwareType from DoorMachine group by softwareType order by softwareType";
		return doorMachineSqlDao.find(hql, new Object[]{});
	}

}
