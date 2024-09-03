package com.spectra.asr.dto.portal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class EntityResultCountDto extends ResultDto {
	private Integer resultCount;
	private String resultTestCode;
	private String entity;
	private String orderTestName;
	
	public String getOrderTestName() {
		return orderTestName;
	}

	public void setOrderTestName(String orderTestName) {
		this.orderTestName = orderTestName;
	}

	public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	public String getResultTestCode() {
		return resultTestCode;
	}

	public void setResultTestCode(String resultTestCode) {
		this.resultTestCode = resultTestCode;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
