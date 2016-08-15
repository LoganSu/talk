package com.youlb.entity.houseInfo;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.youlb.entity.common.BaseModel;

@Entity
@Table(name="t_address")
public class Address extends BaseModel{
   private String fname;
   private String fparentid;
   private String fshortname;
   private String flevel;
   private String fposition;
   private String fgrouporder;
   
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getFparentid() {
		return fparentid;
	}
	public void setFparentid(String fparentid) {
		this.fparentid = fparentid;
	}
	public String getFshortname() {
		return fshortname;
	}
	public void setFshortname(String fshortname) {
		this.fshortname = fshortname;
	}
	public String getFlevel() {
		return flevel;
	}
	public void setFlevel(String flevel) {
		this.flevel = flevel;
	}
	public String getFposition() {
		return fposition;
	}
	public void setFposition(String fposition) {
		this.fposition = fposition;
	}
	public String getFgrouporder() {
		return fgrouporder;
	}
	public void setFgrouporder(String fgrouporder) {
		this.fgrouporder = fgrouporder;
	}
	   

}
