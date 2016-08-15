package com.youlb.entity.management;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;
@Entity
@Table(name="t_about_neighborhoods")
public class AboutNeighborhoods extends BaseModel {

	private static final long serialVersionUID = 6610357633481555193L;
	/**栏目标题*/
	@Column(name="fheadline")
	private String headline;
	/**图标*/
	@Column(name="ficon")
    private String icon;
	/**更新时间*/
	@Column(name="fupdate_time")
    private Date updateTime;
	/**状态 1未发布 2审核中 3已发布*/
	@Column(name="fstatus")
    private String status;
	/**排序*/
	@Column(name="forder")
    private Integer forder;
	/**版本*/
	@Column(name="fversion")
    private String version;
	/**html  url*/
	@Column(name="fhtml_url")
    private String htmlUrl;
	/**社区id*/
	@Column(name="fneighbor_domain_id")
    private String neighborDomainId;
	/**编辑框内容*/
	@Transient
	private String aboutNeighborhoodsDetail;
	/**审核流程备注*/
	@Transient
	private String remark;
	
	 public String getOperate() {
		   	StringBuilder sb = new StringBuilder();
		   	sb.append("<a class='aboutNeighborhoodsDetail' rel='"+getId()+"'  href='javascript:void(0)'>预览</a>&nbsp;");
		   	sb.append("<a class='aboutNeighborhoodsRemark' rel='"+getId()+"' cardStatus='1' href='javascript:void(0)'>记录</a>&nbsp;");
//		   	.append("<a class='infoPublishDelete' rel='"+getId()+"' cardStatus='3' href='javascript:void(0)'>删除</a>&nbsp;");
		   
				return sb.toString();
			}
	 public String getStatusStr() {
		 String statusStr="";
		 if(StringUtils.isNotBlank(status)){
			 if("1".equals(status)){
				 statusStr="未发布";
			 }else if("2".equals(status)){
				 statusStr="审核中";
			 }else if("3".equals(status)){
				 statusStr="已发布";
			 }else if("4".equals(status)){
				 statusStr="撤回";
			 }
		 }
			return statusStr;
	}
	 
	 public String getOrder() {
			StringBuilder sb = new StringBuilder();
		   	sb.append("<a class='aboutNeighborhoodsUp' rel='"+getId()+"' forder='"+getForder()+"' href='javascript:void(0)'><span class='glyphicon glyphicon-arrow-up' aria-hidden='true'></span></a>&nbsp;")
		   	.append("&nbsp;&nbsp;<a class='aboutNeighborhoodsDown' rel='"+getId()+"' forder='"+getForder()+"' href='javascript:void(0)'><span class='glyphicon glyphicon-arrow-down' aria-hidden='true'></span></a>");
		   	return sb.toString();
	 }
	 public String getUpdateTimeStr() {
		 if(updateTime!=null){
			String updateTimeStr = DateHelper.dateFormat(updateTime, "yyyy-MM-dd HH:mm:ss");
			return updateTimeStr;
		 }
		 return "";
	}
	 public String getCreateTimeStr() {
		 if(getCreateTime()!=null){
			String createTimeStr = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd HH:mm:ss");
			return createTimeStr;
		 }
		 return "";
	}
	  
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getForder() {
		return forder;
	}
	public void setForder(Integer forder) {
		this.forder = forder;
	}
	public String getHtmlUrl() {
		return htmlUrl;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.htmlUrl = htmlUrl;
	}
	public String getAboutNeighborhoodsDetail() {
		return aboutNeighborhoodsDetail;
	}
	public void setAboutNeighborhoodsDetail(String aboutNeighborhoodsDetail) {
		this.aboutNeighborhoodsDetail = aboutNeighborhoodsDetail;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNeighborDomainId() {
		return neighborDomainId;
	}
	public void setNeighborDomainId(String neighborDomainId) {
		this.neighborDomainId = neighborDomainId;
	}
	 
	
	
	
}
