package com.youlb.biz.baseInfo;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.baseInfo.Carrier;

/** 
 * @ClassName: ICarrierBiz.java 
 * @Description: 运营商信息接口
 * @author: Pengjy
 * @date: 2015年8月28日
 * 
 */
public interface ICarrierBiz extends IBaseBiz<Carrier> {

	/**
	 * @param carrier
	 */
	void saveOrUpdate(Carrier carrier);


	/**检查运营商简称是否已经存在
	 * @param carrier
	 * @return
	 */
	boolean checkCarrierNumExist(Carrier carrier);

    /**
     * 检查社区是否已经绑定运营商  如果绑定返回社区名称 没绑定返回空
     * @param treecheckbox
     * @return
     */
	String checkHasChecked(List<String> treecheckbox);
    
}
