package com.youlb.biz.management;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.management.AboutNeighborhoods;
import com.youlb.entity.management.AboutNeighborhoodsRemark;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

public interface IAboutNeighborhoodsBiz extends IBaseBiz<AboutNeighborhoods> {

	void saveOrUpdate(AboutNeighborhoods aboutNeighborhoods, Operator loginUser);
    /**
     * 向上箭头排序
     * @param aboutNeighborhoods
     */
	void orderUp(AboutNeighborhoods aboutNeighborhoods);
    /**
     * 获取最小值
     * @param aboutNeighborhoods
     * @return
     */
	int getMinOrder(AboutNeighborhoods aboutNeighborhoods);
	 /**
     * 获取最大值
     * @param aboutNeighborhoods
     * @return
     */
	int getMaxOrder(AboutNeighborhoods aboutNeighborhoods);
	/**
     * 向下箭头排序
     * @param aboutNeighborhoods
     */
	void orderDown(AboutNeighborhoods aboutNeighborhoods);
	/**
	 * 更新状态
	 * @param aboutNeighborhoods
	 * @return
	 */
	int updateCheck(AboutNeighborhoods aboutNeighborhoods,Operator loginUser)throws BizException;
	List<AboutNeighborhoodsRemark> showRemarkList(
			AboutNeighborhoods aboutNeighborhoods);
   
}
