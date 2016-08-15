package com.youlb.entity.common;

import java.io.Serializable;

/**
 * 
 * @Title:基本model父类  
 * @Desription:基本model父类  
 * @ClassName:Model.java
 * @Author:pengjy
 * @CreateDate:2013-6-7 下午5:29:02  
 * @Version:0.1
 */
public abstract class Model implements Serializable{
	/**分页对象*/
	private Pager pager;
	/**bootstrap table的序号*/
	private int index;
	/**排序字段 table js field的值*/
	private String sort;
	/**排序方式 ：desc、asc*/
	private String order;
	
	public Pager getPager() {
		return pager;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	private static final long serialVersionUID = 2768965816312694161L;
	
}
