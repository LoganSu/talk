package com.youlb.entity.common;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;




/**
 * 
 * @Title:显示次序的模型父类  
 * @Desription:显示次序的模型父类
 * @ClassName:OrderModel.java
 * @Author:pengjy
 * @CreateDate:2013-6-7 下午5:29:28  
 * @Version:0.1
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class OrderModel extends BaseModel{
	/** 门户展现次序 */
	@Column(nullable=false,updatable=false,name="FGRADATION")
	private Integer gradation = 99;
	public Integer getGradation() {
		return gradation;
	}

	public void setGradation(Integer gradation) {
		this.gradation = gradation;
	}
	
	public String getGradationStr(){
		return "<input type='text' name='nums' value='"+this.getGradation()+"'/>";
	}
	
}
