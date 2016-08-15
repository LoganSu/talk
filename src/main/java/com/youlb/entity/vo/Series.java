package com.youlb.entity.vo;

import java.util.List;

/** 
 * @ClassName: Series.java 
 * @Description: TODO 
 * @author: Pengjy
 * @date: 2016-1-31
 * 
 */
public class Series {

    private String name;
    private String type;
    private List<Number> data;
     
    public Series(){}
    public Series(String name,String type){
    	this.name=name;
    	this.type=type;
    }
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<Number> getData() {
		return data;
	}
	public void setData(List<Number> data) {
		this.data = data;
	}
}
