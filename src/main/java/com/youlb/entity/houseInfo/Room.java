package com.youlb.entity.houseInfo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.youlb.entity.common.BaseModel;

/** 
 * @ClassName: Room.java 
 * @Description: 房间（具体的户）实体类
 * @author: Pengjy
 * @date: 2015年6月29日
 * 
 */
@Entity
@Table(name="t_room")
public class Room extends BaseModel {
	/**房号 */
	@Column(name="FROOMNUM")
	private String roomNum;
	/**楼层*/
	@Column(name="FROOMFLOOR")
	private Integer roomFloor;
	/**房产证号*/
	@Column(name="FCERTIFICATENUM")
	private String certificateNum ;
   /**户主id*/
   @Column(name="fdwellerid")
   private String dwellerId;
   /**房间归属(业主所有/开发商/物业)*/
   @Column(name="FROOMTYPE")
   private String roomType;
   /**用途*/
   @Column(name="FPURPOSE")
   private String purpose;
   /**朝向 (选择)*/
   @Column(name="FORIENTATION")
   private String orientation;
   /**装修情况*/
   @Column(name="FDECORATIONSTATUS")
   private String decorationStatus;
   /**建筑面积*/
   @Column(name="FROOMAREA")
   private String roomArea;
   /**使用面*/
   @Column(name="FUSEAREA")
   private String useArea;
   /**花园面积*/
   @Column(name="FGARDENAREA")
   private String gardenArea;
   /**是否空置  未空置，空置*/
   @Column(name="FUSESTATUS")
   private String useStatus;
   /**备注*/
   @Column(name="FREMARK")
   private String remark;
   /**房间sip账号*/
   @Column(name="fsipnum")
   private String sipNum;
   /**房间密码*/
   @Column(name="fpassword")
   private String password;
   /**开卡次数*/
	@Column(name="fcardcount")
	private Integer cardCount;
	/**sip分组*/
	@Column(name="fsip_group")
	private Integer sipGroup;
   
   @Transient
   private String parentId;
   @Transient
   private String unitId;
   
    //绑定户主信息
//    public String getHomeHost(){
//    	if(StringUtils.isNotBlank(this.hostInfoId)){
//    		return "<a href='javascript:void(0)' id='"+this.hostInfoId+"' class='showHomeHostInfo'>查看户主信息</a>";

//    	}else{
//    		return "<a href='javascript:void(0)' style='color: green;' id='"+super.getId()+"' neibId='"+this.neibId+"' class='bindingHomeHostInfo'>绑定户主信息</a>";
//    	}
//    }
   

   //门禁授权操作
   public String getOperate() {
  	StringBuilder sb = new StringBuilder();
  	sb.append("<a class='cardInfoOpenCard' rel='"+getId()+"' href='javascript:void(0)'>发卡</a>&nbsp;")
  	.append("<a class='cardInfoLoss' rel='"+getId()+"' cardStatus='2' href='javascript:void(0)'>挂失</a>&nbsp;")
  	.append("<a class='cardInfoUnloss' rel='"+getId()+"' cardStatus='1' href='javascript:void(0)'>解挂</a>&nbsp;")
  	.append("<a class='cardInfoDestroy' rel='"+getId()+"' cardStatus='3' href='javascript:void(0)'>注销</a>&nbsp;");

  
		return sb.toString();
	}
	
	
    
	public Integer getSipGroup() {
		return sipGroup;
	}
	public void setSipGroup(Integer sipGroup) {
		this.sipGroup = sipGroup;
	}
	public Integer getCardCount() {
		return cardCount;
	}
	public void setCardCount(Integer cardCount) {
		this.cardCount = cardCount;
	}
	public String getDwellerId() {
		return dwellerId;
	}
	public void setDwellerId(String dwellerId) {
		this.dwellerId = dwellerId;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getParentId() {
		return parentId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSipNum() {
		return sipNum;
	}
	public void setSipNum(String sipNum) {
		this.sipNum = sipNum;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Integer getRoomFloor() {
		return roomFloor;
	}
	public void setRoomFloor(Integer roomFloor) {
		this.roomFloor = roomFloor;
	}
	public String getRoomNum() {
		return roomNum;
	}
	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
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
