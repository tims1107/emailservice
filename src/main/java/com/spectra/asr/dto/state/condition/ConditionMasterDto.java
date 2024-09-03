package com.spectra.asr.dto.state.condition;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class ConditionMasterDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer conditionMasterPk;
	private Integer stateFk;
	private Integer conditionFilterFk;
	private String orderTestCode;
	private String resultTestCode;
	private String condition;
	private String valueType;
	private String conditionValue;
	
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
	public Integer getConditionMasterPk() {
		return conditionMasterPk;
	}
	public void setConditionMasterPk(Integer conditionMasterPk) {
		this.conditionMasterPk = conditionMasterPk;
	}
	public Integer getStateFk() {
		return stateFk;
	}
	public void setStateFk(Integer stateFk) {
		this.stateFk = stateFk;
	}
	public Integer getConditionFilterFk() {
		return conditionFilterFk;
	}
	public void setConditionFilterFk(Integer conditionFilterFk) {
		this.conditionFilterFk = conditionFilterFk;
	}
	public String getOrderTestCode() {
		return orderTestCode;
	}
	public void setOrderTestCode(String orderTestCode) {
		this.orderTestCode = orderTestCode;
	}
	public String getResultTestCode() {
		return resultTestCode;
	}
	public void setResultTestCode(String resultTestCode) {
		this.resultTestCode = resultTestCode;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getConditionValue() {
		return conditionValue;
	}
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
