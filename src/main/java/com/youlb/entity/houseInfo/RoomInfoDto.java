package com.youlb.entity.houseInfo;


public class RoomInfoDto {
	/**房号 */
	private String roomNum;
	/**楼层*/
	private Integer roomFloor;
	/**房产证号*/
	private String certificateNum ;
   /**户主id*/
//   private String dwellerId;
   /**房间归属(业主所有/开发商/物业)*/
   private String roomType;
   /**用途*/
   private String purpose;
   /**朝向 (选择)*/
   private String orientation;
   /**装修情况*/
   private String decorationStatus;
   /**建筑面积*/
   private String roomArea;
   /**使用面*/
   private String useArea;
   /**花园面积*/
   private String gardenArea;
   /**是否空置  未空置，空置*/
   private String useStatus;
   /**备注*/
   private String remark;
   /**房间sip账号*/
//   private String sipNum;
   /**房间密码*/
//   private String password;
   /**开卡次数*/
//	private Integer cardCount;
	/**sip分组*/
//	private Integer sipGroup;
public String getRoomNum() {
	return roomNum;
}
public void setRoomNum(String roomNum) {
	this.roomNum = roomNum;
}
public Integer getRoomFloor() {
	return roomFloor;
}
public void setRoomFloor(Integer roomFloor) {
	this.roomFloor = roomFloor;
}
public String getCertificateNum() {
	return certificateNum;
}
public void setCertificateNum(String certificateNum) {
	this.certificateNum = certificateNum;
}
public String getRoomType() {
	return roomType;
}
public void setRoomType(String roomType) {
	this.roomType = roomType;
}
public String getPurpose() {
	return purpose;
}
public void setPurpose(String purpose) {
	this.purpose = purpose;
}
public String getOrientation() {
	return orientation;
}
public void setOrientation(String orientation) {
	this.orientation = orientation;
}
public String getDecorationStatus() {
	return decorationStatus;
}
public void setDecorationStatus(String decorationStatus) {
	this.decorationStatus = decorationStatus;
}
public String getRoomArea() {
	return roomArea;
}
public void setRoomArea(String roomArea) {
	this.roomArea = roomArea;
}
public String getUseArea() {
	return useArea;
}
public void setUseArea(String useArea) {
	this.useArea = useArea;
}
public String getGardenArea() {
	return gardenArea;
}
public void setGardenArea(String gardenArea) {
	this.gardenArea = gardenArea;
}
public String getUseStatus() {
	return useStatus;
}
public void setUseStatus(String useStatus) {
	this.useStatus = useStatus;
}
public String getRemark() {
	return remark;
}
public void setRemark(String remark) {
	this.remark = remark;
}
    
   
}
