package com.youlb.utils.helper;
import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;


/**
 * 
 * @Title:工具类
 * @Desription:排序对象 OrderHelper
 * @ClassName:OrderHelper.java
 * @CreateDate:2015-6-7 下午5:49:43  
 * @Version:0.1
 */
@SuppressWarnings("serial")
public class OrderHelper implements Serializable {
	/** 排序属性名称 */
	private String propertyName;
	/** 排序规则 */
	private Type type;
	/**属性名称规则,true为自动添加前缀， false 为原样输出*/
	private boolean auto = true;
	/**是否为联合查询。如设置为true 按指定的属性真实情况进行排序。如为false，则去当前属性对应的列*/
	private boolean isfetch;
	/**排序类型*/
	public enum Type{
		ASC,DESC
	}
	public OrderHelper(){}
	
	public OrderHelper(String propertyName,Type type){
		setPropertyName(propertyName);
		this.type = type;
	}


	public String getPropertyName() {
		if(auto&&StringUtils.isNotBlank(this.propertyName)){
			return "o."+propertyName;
		}
		return propertyName;
	}
	
	public void setPropertyName(String propertyName) {
		if(propertyName.lastIndexOf("Str")!=-1&&propertyName.lastIndexOf("Str")==propertyName.length()-3)
			this.propertyName = propertyName.substring(0,propertyName.lastIndexOf("Str"));
		else if(propertyName.indexOf(".")!=-1)
			if(this.isfetch){
				this.propertyName = propertyName;
			}else{
				this.propertyName = propertyName.substring(0,propertyName.indexOf("."));
			}
		else
			this.propertyName = propertyName;

	}
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	
	public boolean isAuto() {
		return auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}
	
	public boolean getIsfetch() {
		return isfetch;
	}

	public void setIsfetch(boolean isfetch) {
		this.isfetch = isfetch;
	}

	@Override
	public String toString() {
		return this.getPropertyName()+"  "+this.type;
	}
	
	public void setSortName(String sortName) {
		if(StringUtils.isNotBlank(sortName)) {
			setPropertyName(sortName);
		}
	}
	
	public void setSortOrder(String sortOrder) {
		if(StringUtils.isNotBlank(sortOrder)) {
			if("asc".equals(sortOrder)) {
				this.type = Type.ASC;
			} else {
				this.type = Type.DESC;
			}
			
		}
	}
}
