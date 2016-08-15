package com.youlb.biz.privilege;



import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.privilege.Role;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: TestServise 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年5月27日
 * 
 */
public interface IOperatorBiz extends IBaseBiz<Operator>{

	/**用户登录
	 * @param user
	 * @return
	 */
	Operator login(Operator user,String code);
	
	/**用户退出
	 * @param login
	 */
	void loginOut(Operator login);

	/**
	 * @param user
	 * @param loginUser
	 */
	void saveOrUpdate(Operator user);

	/**获取角色列表
	 * @param loginUser
	 * @param user
	 * @return
	 */
	List<Role> getRoleList(Operator loginUser, Operator user);

	/**显示运营商用户table数据
	 * @param user
	 * @param loginUser
	 * @return
	 */
	List<Operator> carrierShowList(Operator user, Operator loginUser);

	/**简称用户名是否存在
	 * @param user
	 * @param loginUser
	 * @return
	 */
	boolean chickLoginNameExist(Operator user, Operator loginUser);

	/**修改密码
	 * @param user
	 * @param loginUser
	 */
	int update(Operator user, String id);

	/**
	 * @param user
	 * @param loginUser
	 * @return
	 */
	String getUser(Operator user, Operator loginUser);
    /**
     * 发送手机验证码
     * @param user
     * @param expireTime
     */
	String getVerificationCode(Operator user,String expireTime)throws UnsupportedEncodingException,IOException,JsonException;

	boolean chickLoginNameExist(Operator user);




}
