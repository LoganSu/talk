package com.youlb.biz.SMSManage.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youlb.biz.SMSManage.ISMSManageBiz;
import com.youlb.dao.common.BaseDaoBySql;
import com.youlb.entity.SMSManage.SMSManage;
import com.youlb.entity.SMSManage.SMSWhiteList;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: SMSManageBizImpl.java 
* @Description: 短信网关配置业务实现类 
* @author: Pengjy
* @date: 2016年9月9日
*
 */
@Service("SMSManageBiz")
public class SMSManageBizImpl implements ISMSManageBiz {
	@Autowired
    private BaseDaoBySql<SMSManage> SMSManageDao;
	public void setSMSManageDao(BaseDaoBySql<SMSManage> sMSManageDao) {
		SMSManageDao = sMSManageDao;
	}

	@Override
	public String save(SMSManage target) throws BizException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(SMSManage target) throws BizException {
		

	}

	@Override
	public void delete(Serializable id) throws BizException {
		SMSManageDao.delete(id);
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
	public SMSManage get(Serializable id) throws BizException {
		return SMSManageDao.get(id);
	}

	@Override
	public List<SMSManage> showList(SMSManage target, Operator loginUser)throws BizException {
		String hql="from SMSManage order by createTime desc";
		return SMSManageDao.pageFind(hql,new Object[]{}, target.getPager());
	}

	@Override
	public void saveOrUpdate(SMSManage sMSManage, Operator loginUser,List<SMSWhiteList> readExcelContent) throws BizException{
		String insert="insert into t_sms_manage_whitelist (fsms_manage_id,fphone) values (?,?)";
		if(StringUtils.isBlank(sMSManage.getId())){
			String id = (String) SMSManageDao.add(sMSManage);
			if(readExcelContent!=null){
				for(SMSWhiteList s:readExcelContent){
//					System.out.println(s.getPhone());
					if(s.getPhone()!=null&&s.getPhone().length()!=11){
						throw new BizException("导入的手机号码格式不正确");
					}
					if(StringUtils.isNotBlank(s.getPhone())){
						SMSManageDao.executeSql(insert, new Object[]{id,s.getPhone()});
					}
				}
			}
		}else{
			SMSManageDao.update(sMSManage);
			if(readExcelContent!=null){
				//先删除历史记录
				String delete = "delete from t_sms_manage_whitelist where fsms_manage_id=?";
				SMSManageDao.executeSql(delete, new Object[]{sMSManage.getId()});
				SMSManageDao.getCurrSession().flush();
				for(SMSWhiteList s:readExcelContent){
					if(StringUtils.isNotBlank(s.getPhone())){
					    SMSManageDao.executeSql(insert, new Object[]{sMSManage.getId(),s.getPhone()});
					}
				}
			}
		}
		
		
	}
    /**
     * 获取白名单列表
     * @param id
     * @return
     * @throws BizException 
     * @see com.youlb.biz.SMSManage.ISMSManageBiz#getWhiteListBySMSMangeId(java.lang.String)
     */
	@Override
	public List<SMSWhiteList> getWhiteListBySMSMangeId(String id) throws BizException {
		String sql = "select fphone from t_sms_manage_whitelist where fsms_manage_id=?";
		List<String> list = SMSManageDao.pageFindBySql(sql, new Object[]{id});
		List<SMSWhiteList> phoneList = new ArrayList<SMSWhiteList>();
		if(list!=null){
			for(String phone:list){
				SMSWhiteList s = new SMSWhiteList();
				s.setPhone(phone);
				phoneList.add(s);
			}
		}
		return phoneList;
	}
	

}
