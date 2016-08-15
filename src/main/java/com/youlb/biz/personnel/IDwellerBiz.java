package com.youlb.biz.personnel;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.personnel.Dweller;
import com.youlb.entity.privilege.Operator;

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
	void saveOrUpdate(Dweller dweller,Operator loginUser);

	/**判断房子是否已经被别人选择过
	 * @param dweller
	 * @return
	 */
	String checkRoomChoosed(Dweller dweller);
    /**
     * 检查手机号是否已经注册
     * @param phone
     * @return
     */
	boolean checkPhoneExistWebShow(String phone,String carrierId);
    /**
     * 通过域id获取运营商id
     * @param treecheckbox
     * @return
     */
	List<String> getCarrierByDomainId(List<String> treecheckbox);

}
