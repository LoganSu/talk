package com.youlb.biz.countManage;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.countManage.SipCount;
import com.youlb.entity.privilege.Operator;

public interface ISipCountBiz extends IBaseBiz<SipCount> {
    /**
     * 所有sip账号
     * @param sipCount
     * @param loginUser
     * @return
     */
	List<SipCount> showAllList(SipCount sipCount, Operator loginUser);
    
	String getAddressByDomainId(String domainId);
    /**
     * 门口机sip账号查询
     * @param sipCount
     * @param loginUser
     * @return
     */
	List<SipCount> deviceCountSipShowList(SipCount sipCount, Operator loginUser);

}
