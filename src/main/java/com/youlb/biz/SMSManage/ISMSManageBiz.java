package com.youlb.biz.SMSManage;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.SMSManage.SMSManage;
import com.youlb.entity.SMSManage.SMSWhiteList;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
/**
 * 
* @ClassName: ISMSManageBiz.java 
* @Description: 短信网关配置业务接口 
* @author: Pengjy
* @date: 2016年9月9日
*
 */
public interface ISMSManageBiz extends IBaseBiz<SMSManage> {

	void saveOrUpdate(SMSManage sMSManage, Operator loginUser,List<SMSWhiteList> readExcelContent)throws BizException;

	List<SMSWhiteList> getWhiteListBySMSMangeId(String id)throws BizException;
	 
}
