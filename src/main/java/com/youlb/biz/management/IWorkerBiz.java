package com.youlb.biz.management;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.Worker;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

public interface IWorkerBiz extends IBaseBiz<Worker> {

	void saveOrUpdate(Worker worker, Operator loginUser)throws BizException, ClientProtocolException, UnsupportedEncodingException, IOException, JsonException;
    /**
     * 通过部门获取员工列表
     * @param departmentId
     * @return
     */
	List<Worker> getWorkerList(String departmentId)throws BizException;
	/**
	 * 检查手机号码是否已经被注册
	 * @param phone
	 * @return
	 */
	boolean checkPhoneExist(String phone,String id)throws BizException;

}
