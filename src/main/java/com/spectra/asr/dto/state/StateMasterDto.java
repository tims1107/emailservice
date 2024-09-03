package com.spectra.asr.dto.state;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class StateMasterDto extends ResultDto {
	private Integer stateMasterPk;
	private String state;
	private String stateAbbreviation;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String zipCode;
	private String phoneNumber;
	private String faxNumber;
	private String elr;
	private String contact;
	private String email;
	private String entityType;
	private String filterStateBy;
	
	private String receivingApplication;
	private String receivingFacility;
	private String msgProfileId;
	
	public String getReceivingApplication() {
		return receivingApplication;
	}

	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}

	public String getReceivingFacility() {
		return receivingFacility;
	}

	public void setReceivingFacility(String receivingFacility) {
		this.receivingFacility = receivingFacility;
	}

	public String getMsgProfileId() {
		return msgProfileId;
	}

	public void setMsgProfileId(String msgProfileId) {
		this.msgProfileId = msgProfileId;
	}

	public String getFilterStateBy() {
		return filterStateBy;
	}

	public void setFilterStateBy(String filterStateBy) {
		this.filterStateBy = filterStateBy;
	}

	public Integer getStateMasterPk() {
		return stateMasterPk;
	}

	public void setStateMasterPk(Integer stateMasterPk) {
		this.stateMasterPk = stateMasterPk;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}

	public void setStateAbbreviation(String stateAbbreviation) {
		this.stateAbbreviation = stateAbbreviation;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public String getElr() {
		return elr;
	}

	public void setElr(String elr) {
		this.elr = elr;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
