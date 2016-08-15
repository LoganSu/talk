package com.youlb.entity.common;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;



/**
 * 
 * @Title:父类model  
 * @Desription:所有模型类的父类，包含了如标识，创建时间等信息
 * @ClassName:BaseModel.java
 * @Author:pengjy
 * @CreateDate:2013-6-7 下午5:27:24  
 * @Version:0.1
 */
@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
/*@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)*/
public  class BaseModel extends Model implements Serializable{
	private static final long serialVersionUID = -3830177359420683215L;
	/**模型编号*/
	@Id
	@GenericGenerator(name="uuid",strategy="uuid")
	@GeneratedValue(generator="uuid")
	@Column(length=32,name="id")
	private String id;
	/**创建时间*/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false,updatable=false,name="FCREATETIME")
	private Date createTime = new Date();
	@Column(nullable=false,updatable=false,name="FDELETEFLAG")
	private Integer delFlag = 1; 
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String showMessage(){
		return this.getClass().getName();
	}
	public Integer getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
