package com.spectra.asr.dto.state.condition;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class ConditionFilterDto extends ResultDto {
	private Integer conditionFilterPk;
	private String condition;
	private String filter;
	private String valueType;
	
	public Integer getConditionFilterPk() {
		return conditionFilterPk;
	}

	public void setConditionFilterPk(Integer conditionFilterPk) {
		this.conditionFilterPk = conditionFilterPk;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
