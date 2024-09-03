package com.spectra.result.transporter.dto.rr.test;

import java.sql.Timestamp;
import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TestPropertiesHistDto implements Serializable {
	private Integer testPropHistoryId;
	private Integer testPropId;
	private String testPropXml;
	private String createdBy;
	private Timestamp createdDate;
	private String modifiedBy;
	private Timestamp modifiedDate;
	
	public Integer getTestPropHistoryId() {
		return testPropHistoryId;
	}
	public void setTestPropHistoryId(Integer testPropHistoryId) {
		this.testPropHistoryId = testPropHistoryId;
	}
	public Integer getTestPropId() {
		return testPropId;
	}
	public void setTestPropId(Integer testPropId) {
		this.testPropId = testPropId;
	}
	public String getTestPropXml() {
		return testPropXml;
	}
	public void setTestPropXml(String testPropXml) {
		this.testPropXml = testPropXml;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
