package com.youlb.biz.common;

import java.io.Serializable;
import java.util.List;

import com.youlb.entity.privilege.Operator;
import com.youlb.utils.exception.BizException;

/**
 * 
 * @Title:业务父接口 
 * @Desription:主要定义了每个模块都需要完成的添加。修改。删除等常规操作
 * @ClassName:IBaseBiz.java
 * @Author:pengjy
 * @CreateDate:2015-6-7 下午5:31:12  
 * @Version:0.1
 */
public interface IBaseBiz<T extends Serializable> {
    /**
     * 
     * @ClassName: IBaseBiz.java
     * @Description: 添加记录
     * @Author:pengjy
     * @CreateDate:2015-6-7 下午5:32:31
     * @param target 要添加的目标对象
     * @return
     * @throws BizException
     */
	 public String save(T target)throws BizException;
	 /**
	  * 
	  * @ClassName: IBaseBiz.java
	  * @Description: 修改记录
	  * @Author:pengjy
	  * @CreateDate:2015-6-7 下午5:33:24
	  * @param target 要修改的目标对象
      * @throws BizException
	  */
	 public void update(T target)throws BizException;
	 
	 /**
	  * 
	  * @ClassName: IBaseBiz.java
	  * @Description: 根据标识删除对象
	  * @Author:pengjy
	  * @CreateDate:2015-6-7 下午5:33:47
	  * @param id 对象标识
      * @throws BizException
	  */
	 public void delete(Serializable id)throws BizException;
	 /**
	  * 
	  * @ClassName: IBaseBiz.java
	  * @Description: 批量删除对象
	  * @Author:pengjy
	  * @CreateDate:2015-6-7 下午5:34:11
	  * @param ids 对象标识集合
      * @throws BizException
	  */
	 public void delete(String[] ids)throws BizException;
	 
	 /**
	  * 
	  * @ClassName: IBaseBiz.java
	  * @Description: 根据标识获取对象，如不存在返回null
	  * @Author:pengjy
	  * @CreateDate:2015-6-7 下午5:34:45
	  * @param id 对象标识
      * @return 
      * @throws BizException
	  */
	 public T get(Serializable id)throws BizException;
	 
	 /**
	  * 
	  * @ClassName: IBaseBiz.java
	  * @Description: 按条件分页获取符合条件的记录
	  * @Author:pengjy
	  * @CreateDate:2015-6-7 下午5:35:02
	  * @param target 查询条件对象 包含分页 、排序
      * @return
      * @throws BizException
	  */
	 public List<T> showList(T target,Operator loginUser)throws BizException;
	 
	 
	 
}
