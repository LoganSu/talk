package com.youlb.biz.houseInfo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.houseInfo.Neighborhoods;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;
import com.youlb.utils.exception.JsonException;

/** 
 * @ClassName: INeighborhoodsBiz.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2015年6月25日
 * 
 */
public interface INeighborhoodsBiz extends IBaseBiz<Neighborhoods>{

	/**添加或更新
	 * @param neighborhoods
	 */
	void saveOrUpdate(Neighborhoods neighborhoods,Operator loginUser)throws BizException, NumberFormatException, ClientProtocolException, UnsupportedEncodingException, IOException, JsonException;

	/**查询地区数据
	 * @return
	
	List<Area> initArea(); */

	/**获取社区基本信息
	 * @param loginUser 
	 * @return
	 */
	List<Neighborhoods> getNeighborList(Operator loginUser)throws BizException;

	/**通过neibId 获取areaId
	 * @param parentId
	 * @return
	 */
	String getAreaId(String parentId)throws BizException;

	/**通过areaId获取社区列表
	 * @param areaId
	 * @return
	 */
	List<Neighborhoods> getNeiborListByAreaId(String areaId)throws BizException;
	/**
	 * 判断是否有重复的编号
	 * @param parentId
	 * @return
	 */
	boolean checkNeighborNum(Neighborhoods neighborhoods)throws BizException;

	
}
