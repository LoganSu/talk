package com.youlb.biz.countManage;

import java.io.IOException;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.countManage.SipCount;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

public interface ISipCountBiz extends IBaseBiz<SipCount> {
    /**
     * 所有sip账号
     * @param sipCount
     * @param loginUser
     * @return
     */
	List<SipCount> showAllList(SipCount sipCount, Operator loginUser)throws BizException;
    
	String getAddressByDomainId(String domainId)throws BizException;
    /**
     * 门口机sip账号查询
     * @param sipCount
     * @param loginUser
     * @return
     */
	List<SipCount> deviceCountSipShowList(SipCount sipCount, Operator loginUser)throws BizException;

	void restDevice(String username, String reset_time, String reset_type)throws BizException, ClientProtocolException, IOException, ParseException, JsonException;

}
