package com.youlb.biz.houseInfo;


import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Unit;
import com.youlb.entity.privilege.Operator;

/** 
 * @ClassName: IUnitBiz.java 
 * @Description: 单元业务接口
 * @author: Pengjy
 * @date: 2015年7月25日
 * 
 */
public interface IUnitBiz extends IBaseBiz<Unit>{

	/**添加或更新
	 * @param unit
	 */
	void saveOrUpdate(Unit unit,Operator loginUser);

	/**通过building id获取unit集合
	 * @param id
	 * @return
	 */
	List<Unit> getUnitListBybuildingId(String id);



}
