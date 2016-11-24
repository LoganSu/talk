package com.youlb.biz.domainName;

import java.util.List;

import com.youlb.biz.common.IBaseBiz;
import com.youlb.entity.domainName.DomainName;
import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

public interface IDomainNameBiz extends IBaseBiz<DomainName> {

	void saveOrUpdate(DomainName domainName, Operator loginUser) throws BizException;

	List<DomainName> showListByParentId(String id, Operator loginUser);

}
