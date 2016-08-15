package com.youlb.biz.staticParam;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.privilege.Operator;
import com.youlb.entity.staticParam.StaticParam;

public interface IStaticParamBiz extends IBaseBiz<StaticParam> {

	void saveOrUpdate(StaticParam staticParam, Operator loginUser);
    /**
     * 通过key获取参数
     * @param fkey
     * @return
     */
	StaticParam getParamByKey(String fkey);
	/**
	 * 通过匹配key获取参数
	 * @param fkey 
	 * @param lenght 匹配字符串的长度 0~lenght
	 */
	List<StaticParam> getParamByLikeKey(String fkey,Integer lenght);

}
