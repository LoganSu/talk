package com.youlb.entity.monitor;

public class PointInfo {
  private Double x;
  private Double y;
  private String name;
  private String address;
  private String tel;
  
  
  
public PointInfo() {
	super();
	// TODO Auto-generated constructor stub
}
public PointInfo(Double x, Double y, String name, String address, String tel) {
	super();
	this.x = x;
	this.y = y;
	this.name = name;
	this.address = address;
	this.tel = tel;
}
public Double getX() {
	return x;
}
public void setX(Double x) {
	this.x = x;
}
public Double getY() {
	return y;
}
public void setY(Double y) {
	this.y = y;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
  
  
}
