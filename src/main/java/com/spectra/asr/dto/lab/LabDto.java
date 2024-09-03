package com.spectra.asr.dto.lab;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class LabDto extends ResultDto {
	private Integer labPk;
	private String labId;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zip;
	private String logo;
	private String phoneNumber;
	private String faxNumber;
	private String medicalDirector;
	private String leadTechnicalDirector;
	private String sourceLabSystem;
	private String activeFlag;
	
	public Integer getLabPk() {
		return labPk;
	}

	public void setLabPk(Integer labPk) {
		this.labPk = labPk;
	}

	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getMedicalDirector() {
		return medicalDirector;
	}

	public void setMedicalDirector(String medicalDirector) {
		this.medicalDirector = medicalDirector;
	}

	public String getLeadTechnicalDirector() {
		return leadTechnicalDirector;
	}

	public void setLeadTechnicalDirector(String leadTechnicalDirector) {
		this.leadTechnicalDirector = leadTechnicalDirector;
	}

	public String getSourceLabSystem() {
		return sourceLabSystem;
	}

	public void setSourceLabSystem(String sourceLabSystem) {
		this.sourceLabSystem = sourceLabSystem;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
