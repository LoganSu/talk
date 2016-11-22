package com.youlb.entity.doorMachine;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.youlb.entity.common.BaseModel;

@Table(name="t_door_machine")
@Entity
public class DoorMachine extends BaseModel{
	private static final long serialVersionUID = -1716963340887365557L;
	/**软件型号*/
	@Column(name="fsoftware_type")
	private String softwareType;
	/**硬件型号*/
    @Column(name="fhardware_model")
	private String  hardwareModel;
    /**备注*/
    @Column(name="fremark")
	private String  remark;
    
    
	 
	public String getSoftwareType() {
		return softwareType;
	}
	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}
	public String getHardwareModel() {
		return hardwareModel;
	}
	public void setHardwareModel(String hardwareModel) {
		this.hardwareModel = hardwareModel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
