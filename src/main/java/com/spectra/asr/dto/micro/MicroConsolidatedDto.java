package com.spectra.asr.dto.micro;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class MicroConsolidatedDto extends ResultDto {
	private Integer listPk;
	private String code;
	private String description;
	private String reportableConditions;
	private String tests;
	
	public Integer getListPk() {
		return listPk;
	}

	public void setListPk(Integer listPk) {
		this.listPk = listPk;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getReportableConditions() {
		return reportableConditions;
	}

	public void setReportableConditions(String reportableConditions) {
		this.reportableConditions = reportableConditions;
	}

	public String getTests() {
		return tests;
	}

	public void setTests(String tests) {
		this.tests = tests;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
