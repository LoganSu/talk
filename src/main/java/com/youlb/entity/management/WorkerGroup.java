package com.youlb.entity.management;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.youlb.entity.common.BaseModel;
import com.youlb.utils.helper.DateHelper;
@Entity
@Table(name="t_worker_group")
public class WorkerGroup extends BaseModel {
	private static final long serialVersionUID = 4364867504699872075L;
	/**组名称*/
	@Column(name="fgroup_name")
    private String groupName;
	/**所属公司*/
	@Column(name="fdepartment_id")
    private String departmentId;
	/**是否具有派单权限 1具备派单权限 2普通组*/
	@Column(name="fpower")
    private Integer power;
	/**备注*/
	@Column(name="fremark")
    private String remark;
	@Transient
	private String departmentName;
	/**员工id集合*/
	@Transient
	private List<String> treecheckbox;
	
	
	public String getPowerStr(){
		String powerStr = "";
		if(power!=null){
			if(new Integer(1).equals(power)){
				powerStr="派单组";
			}else if(new Integer(2).equals(power)){
				powerStr="普通组";
			}
		}
		return powerStr;
	}
	
	public String getCreateTimeStr(){
		 if(getCreateTime()!=null){
	            String createTimeStr = DateHelper.dateFormat(getCreateTime(), "yyyy-MM-dd HH:mm:ss");		
	            return createTimeStr;
	    	}
	    	 return "";
	}
	
	public String getOperator(){
		
		return "<a class='workerGroupList' rel='"+getId()+"' href='javascript:void(0)'>人员列表</a>";
	}
	
	
	public List<String> getTreecheckbox() {
		return treecheckbox;
	}

	public void setTreecheckbox(List<String> treecheckbox) {
		this.treecheckbox = treecheckbox;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getPower() {
		return power;
	}
	public void setPower(Integer power) {
		this.power = power;
	}
	 
	
}
