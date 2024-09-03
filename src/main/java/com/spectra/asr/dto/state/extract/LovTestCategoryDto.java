package com.spectra.asr.dto.state.extract;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class LovTestCategoryDto extends ResultDto {
	private Integer testCategoryPk;
	private String testCategory;
	private Integer mci;
	private Integer sessionId;
	private Integer bodyId;
	private Integer messageDateTime;
	private String testCategoryDescription;
	private String customDepartment;
	
	public Integer getTestCategoryPk() {
		return testCategoryPk;
	}

	public void setTestCategoryPk(Integer testCategoryPk) {
		this.testCategoryPk = testCategoryPk;
	}

	public String getTestCategory() {
		return testCategory;
	}

	public void setTestCategory(String testCategory) {
		this.testCategory = testCategory;
	}

	public Integer getMci() {
		return mci;
	}

	public void setMci(Integer mci) {
		this.mci = mci;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getBodyId() {
		return bodyId;
	}

	public void setBodyId(Integer bodyId) {
		this.bodyId = bodyId;
	}

	public Integer getMessageDateTime() {
		return messageDateTime;
	}

	public void setMessageDateTime(Integer messageDateTime) {
		this.messageDateTime = messageDateTime;
	}

	public String getTestCategoryDescription() {
		return testCategoryDescription;
	}

	public void setTestCategoryDescription(String testCategoryDescription) {
		this.testCategoryDescription = testCategoryDescription;
	}

	public String getCustomDepartment() {
		return customDepartment;
	}

	public void setCustomDepartment(String customDepartment) {
		this.customDepartment = customDepartment;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
