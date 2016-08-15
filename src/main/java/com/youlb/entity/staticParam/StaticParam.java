package com.youlb.entity.staticParam;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.youlb.entity.common.BaseModel;

@Entity
@Table(name="t_staticparam")
public class StaticParam extends BaseModel {
	private static final long serialVersionUID = -8990622538956918008L;
	@Column(name="fkey")
    private String fkey;
	@Column(name="fvalue")
    private String fvalue;
	@Column(name="fdescr")
    private String fdescr;
	public String getFkey() {
		return fkey;
	}
	public void setFkey(String fkey) {
		this.fkey = fkey;
	}
	public String getFvalue() {
		return fvalue;
	}
	public void setFvalue(String fvalue) {
		this.fvalue = fvalue;
	}
	public String getFdescr() {
		return fdescr;
	}
	public void setFdescr(String fdescr) {
		this.fdescr = fdescr;
	}
	
	
}
