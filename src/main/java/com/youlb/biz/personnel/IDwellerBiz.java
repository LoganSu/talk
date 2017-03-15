package com.youlb.biz.personnel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.personnel.Dweller;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: IDwellerBiz.java 
 * @Description: 住户信息管理业务接口 
 * @author: Pengjy
 * @date: 2015-10-26
 * 
 */
public interface IDwellerBiz extends IBaseBiz<Dweller> {

	/**
	 * @param dweller
	 */
	void saveOrUpdate(Dweller dweller,Operator loginUser)throws BizException, ClientProtocolException, UnsupportedEncodingException, IOException, JsonException ;

	/**判断房子是否已经被别人选择过
	 * @param dweller
	 * @return
	 */
	String checkRoomChoosed(Dweller dweller)throws BizException;
    /**
     * 检查手机号是否已经注册
     * @param phone
     * @return
     */
	String checkPhoneExistWebShow(Dweller dweller)throws BizException;
    /**
     * 通过域id获取运营商id
     * @param treecheckbox
     * @return
     */
	List<String> getCarrierByDomainId(List<String> treecheckbox)throws BizException;
	/**
	 * 查询所有的父节点
	 * @param string
	 * @return
	 */
	List<String> getParentIds(String string)throws BizException;
	/**
	 * 删除用户需要根据数据权限删除
	 * @param ids
	 * @param loginUser
	 */
	void delete(String[] ids, Operator loginUser)throws BizException;

}
