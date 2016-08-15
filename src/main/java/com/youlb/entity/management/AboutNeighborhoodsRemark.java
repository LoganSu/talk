package com.youlb.entity.management;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;
@Entity
@Table(name="t_about_neighborhoods_remark")
public class AboutNeighborhoodsRemark extends BaseModel {
	private static final long serialVersionUID = -970383832255576856L;
	/**状态*/
	@Column(name="fstatus")
    private String status;
	/**备注*/
    @Column(name="fremark")
    private String remark;
    /**关联id*/
    @Column(name="fmain_id")
    private String mainId;
    /**操作人*/
    @Column(name="foperator")
    private String operator;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMainId() {
		return mainId;
	}
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	 public String getCreateTimeStr() {
		 if(getCreateTime()!=null){
			String createTimeStr = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd HH:mm:ss");
			return createTimeStr;
		 }
		 return "";
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
}
