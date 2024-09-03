package com.spectra.asr.dto.demographic;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class AsrActivityNoDemoDto extends ResultDto {
	private Integer activityNoDemoPk;
	private String initiateId;
	private Integer labOrderFk;
	private String requisitionId;
	private String patientName;
	private Timestamp patientDob;
	private String ssn;
	private String hasDemo;
	public Integer getActivityNoDemoPk() {
		return activityNoDemoPk;
	}
	public void setActivityNoDemoPk(Integer activityNoDemoPk) {
		this.activityNoDemoPk = activityNoDemoPk;
	}
	public String getInitiateId() {
		return initiateId;
	}
	public void setInitiateId(String initiateId) {
		this.initiateId = initiateId;
	}
	public Integer getLabOrderFk() {
		return labOrderFk;
	}
	public void setLabOrderFk(Integer labOrderFk) {
		this.labOrderFk = labOrderFk;
	}
	public String getRequisitionId() {
		return requisitionId;
	}
	public void setRequisitionId(String requisitionId) {
		this.requisitionId = requisitionId;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public Timestamp getPatientDob() {
		return patientDob;
	}
	public void setPatientDob(Timestamp patientDob) {
		this.patientDob = patientDob;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getHasDemo() {
		return hasDemo;
	}
	public void setHasDemo(String hasDemo) {
		this.hasDemo = hasDemo;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
