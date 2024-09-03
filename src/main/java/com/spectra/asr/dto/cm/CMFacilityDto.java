package com.spectra.asr.dto.cm;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class CMFacilityDto extends ResultDto {
	private String hlabNum;
	private String facilityNum;
	private String fmcNumber;
	private String facilityPref;
	private String facilityName;
	private String address1;
	private String address2;
	private String city;
	private String facilityState;
	private String zip;
	private String phone;
	private String accountCategory;
	private String accountStatus;
	private String typeOfService;
	private String sourceLab;
	
	public String getHlabNum() {
		return hlabNum;
	}
	public void setHlabNum(String hlabNum) {
		this.hlabNum = hlabNum;
	}
	public String getFacilityNum() {
		return facilityNum;
	}
	public void setFacilityNum(String facilityNum) {
		this.facilityNum = facilityNum;
	}
	public String getFmcNumber() {
		return fmcNumber;
	}
	public void setFmcNumber(String fmcNumber) {
		this.fmcNumber = fmcNumber;
	}
	public String getFacilityPref() {
		return facilityPref;
	}
	public void setFacilityPref(String facilityPref) {
		this.facilityPref = facilityPref;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
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
	public String getFacilityState() {
		return facilityState;
	}
	public void setFacilityState(String facilityState) {
		this.facilityState = facilityState;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAccountCategory() {
		return accountCategory;
	}
	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public String getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}
	public String getSourceLab() {
		return sourceLab;
	}
	public void setSourceLab(String sourceLab) {
		this.sourceLab = sourceLab;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
