package com.youlb.entity.monitor;

public class PointInfo {
  private String x;
  private String y;
  private String name;
  private String address;
  private String tel;
  
  
  
public PointInfo() {
	super();
	// TODO Auto-generated constructor stub
}
public PointInfo(String x, String y, String name, String address, String tel) {
	super();
	this.x = x;
	this.y = y;
	this.name = name;
	this.address = address;
	this.tel = tel;
}
public String getX() {
	return x;
}
public void setX(String x) {
	this.x = x;
}
public String getY() {
	return y;
}
public void setY(String y) {
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
