package com.spectra.result.transporter.dto.rr.test;

import java.sql.Timestamp;
import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class TestPropertiesDto implements Serializable {
	private Integer testPropId;
	private String state;
	private String compoundTestCode;
	private String testCode;
	private String positivity;
	private String valueType;
	private String typeValue;
	private Timestamp lastUpdateTime;
	private String lastUpdateBy;
	
	public Integer getTestPropId() {
		return testPropId;
	}
	public void setTestPropId(Integer testPropId) {
		this.testPropId = testPropId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCompoundTestCode() {
		return compoundTestCode;
	}
	public void setCompoundTestCode(String compoundTestCode) {
		this.compoundTestCode = compoundTestCode;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getPositivity() {
		return positivity;
	}
	public void setPositivity(String positivity) {
		this.positivity = positivity;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}	
}
