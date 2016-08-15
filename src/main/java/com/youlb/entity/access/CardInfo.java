package com.youlb.entity.access;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.StringUtils;
import com.youlb.entity.common.Model;
import com.youlb.utils.common.SysStatic;

/** 
 * @ClassName: CardInfo.java 
 * @Description: 门禁卡信息 
 * @author: Pengjy
 * @date: 2015-11-4
 * 
 */
@Entity
@Table(name="t_cardinfo")
public class CardInfo extends Model {
	/**卡片序列号*/
   @Id
   @Column(name="fcardsn")
   private String cardSn;
   /**卡号*/
   @Column(name="fcardno")
   private Integer cardNo;
   /**类型*/
   @Column(name="fcardtype")
   private String cardType;
   /**操作类型*/
   @Column(name="foprtype")
   private String oprType;
   /**密码*/
   @Column(name="fpwd")
   private String  pwd;
   /**开始时间*/
   @Column(name="ffrdate")
   private String  frDate;
   /**结束时间*/
   @Column(name="ftodate")
   private String  toDate;
   /**卡片状态*/
   @Column(name="fcardstatus")
   private String  cardStatus;
   /**持卡状态*/
   @Column(name="fholdstatus")
   private String  holdStatus;
   /**创建时间*/
   @Column(name="fcreatetime")
   private Date  createTime=new Date();
   /**房间id*/
   @Column(name="froomid")
   private String  roomId;
   
   @Transient
   private String username;
   
   /**性别*/
   @Transient
   private String sex;
   /**名字*/
   @Transient
   private String fname;
   /**身份证号码*/
   @Transient
   private String idNum;
   /**联系电话*/
   @Transient
   private String phone;
   /**邮箱地址*/
   @Transient
   private String email;
   /**办卡数量*/
   @Transient
   private Integer cardcount;
   /**租客所属房间id*/
   @Transient
   private Integer domainSn;
   /**域对象序号 关联卡*/
   @Transient
   private List<Integer> domainSns;
   /**快到期卡片标识*/
   @Transient
   private String nearEndCard;
   /**完整地址*/
   @Transient
   private String address;
   
   @Transient
   private Date ftime;
   @Transient
   private String path;
   @Transient
   private Integer id;
   
 

public Integer getId() {
	return id;
}

public void setId(Integer id) {
	this.id = id;
}

public String getPath() {
	return path;
}

public void setPath(String path) {
	this.path = path;
}

public Date getFtime() {
	return ftime;
}

public void setFtime(Date ftime) {
	this.ftime = ftime;
}

//卡片管理授权房间详情
//   public String getDetail() {
//	   return "<a class='cardInfoPermission' rel='"+getCardSn()+"' href='javascript:void(0)'>授权地址</a>";
//   }
    
    public String getCreateTimeStr() {
		String createTimeStr = "";
		if(getCreateTime()!=null){
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			createTimeStr = sd.format(getCreateTime());
			
		}
		return createTimeStr;
	}
    public String getSexStr() {
		 String sexStr = "";
		 if(StringUtils.isNotBlank(this.sex)){
			 if(SysStatic.one.equals(this.sex)){
				 sexStr="男";
			 }else if(SysStatic.two.equals(this.sex)){
				 sexStr="女";
			 }
		 }
		 
		 return sexStr;
	 }
	 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNearEndCard() {
		return nearEndCard;
	}
	public void setNearEndCard(String nearEndCard) {
		this.nearEndCard = nearEndCard;
	}
	public Integer getDomainSn() {
		return domainSn;
	}
	public void setDomainSn(Integer domainSn) {
		this.domainSn = domainSn;
	}
	public List<Integer> getDomainSns() {
		return domainSns;
	}
	public void setDomainSns(List<Integer> domainSns) {
		this.domainSns = domainSns;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	 
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getCardcount() {
		return cardcount;
	}
	public void setCardcount(Integer cardcount) {
		this.cardcount = cardcount;
	}
	public Date getCreateTime() {
			return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public String getCardStatusStr() {
		String cardStatusStr="";
		if(SysStatic.LIVING.equals(cardStatus)){
			cardStatusStr="激活";
		}else if(SysStatic.LOSS.equals(cardStatus)){
			cardStatusStr="挂失";
		}else if(SysStatic.CANCEL.equals(cardStatus)){
			cardStatusStr="注销";
		}
		return cardStatusStr;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getHoldStatus() {
		return holdStatus;
	}
	public void setHoldStatus(String holdStatus) {
		this.holdStatus = holdStatus;
	}
	public String getCardSn() {
			return cardSn;
	}
	public void setCardSn(String cardSn) {
		this.cardSn = cardSn;
	}
	public Integer getCardNo() {
		return cardNo;
	}
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getOprType() {
		return oprType;
	}
	public String getCardTypeStr() {
		String cardTypeStr = "";
		if(SysStatic.ICCARD.equals(cardType)){
			cardTypeStr="IC卡";
		}else if(SysStatic.IDCARD.equals(cardType)){
			cardTypeStr="身份证";
		}else if(SysStatic.BANKCARD.equals(cardType)){
			cardTypeStr="银行卡";
		}
		return cardTypeStr;
	}
	public void setOprType(String oprType) {
		this.oprType = oprType;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFrDate() {
//		if(StringUtils.isNotBlank(frDate)){
//			frDate = frDate.substring(2);
//		}
		return frDate;
	}
	public void setFrDate(String frDate) {
		this.frDate = frDate;
	}
	public String getToDate() {
//		if(StringUtils.isNotBlank(toDate)){
//			toDate = toDate.substring(2);
//		}
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
