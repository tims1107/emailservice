package com.spectra.asr.dto.audit;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class AsrAuditDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer asrAuditPk;
	private Integer stateFk;
	private Integer conditionMasterFk;
	private Integer conditionFilterFk;
	private Integer generatorFk;
	private Integer generatorFieldFk;
	private Integer generatorFieldsMapFk;
	private Integer distributorFk;
	private Integer distributorItemFk;
	private Integer distributorItemsMapFk;
	private Integer microConditionMasterFk;
	
	public Integer getMicroConditionMasterFk() {
		return microConditionMasterFk;
	}

	public void setMicroConditionMasterFk(Integer microConditionMasterFk) {
		this.microConditionMasterFk = microConditionMasterFk;
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

	public Integer getAsrAuditPk() {
		return asrAuditPk;
	}

	public void setAsrAuditPk(Integer asrAuditPk) {
		this.asrAuditPk = asrAuditPk;
	}

	public Integer getStateFk() {
		return stateFk;
	}

	public void setStateFk(Integer stateFk) {
		this.stateFk = stateFk;
	}

	public Integer getConditionMasterFk() {
		return conditionMasterFk;
	}

	public void setConditionMasterFk(Integer conditionMasterFk) {
		this.conditionMasterFk = conditionMasterFk;
	}

	public Integer getConditionFilterFk() {
		return conditionFilterFk;
	}

	public void setConditionFilterFk(Integer conditionFilterFk) {
		this.conditionFilterFk = conditionFilterFk;
	}

	public Integer getGeneratorFk() {
		return generatorFk;
	}

	public void setGeneratorFk(Integer generatorFk) {
		this.generatorFk = generatorFk;
	}

	public Integer getGeneratorFieldFk() {
		return generatorFieldFk;
	}

	public void setGeneratorFieldFk(Integer generatorFieldFk) {
		this.generatorFieldFk = generatorFieldFk;
	}

	public Integer getGeneratorFieldsMapFk() {
		return generatorFieldsMapFk;
	}

	public void setGeneratorFieldsMapFk(Integer generatorFieldsMapFk) {
		this.generatorFieldsMapFk = generatorFieldsMapFk;
	}

	public Integer getDistributorFk() {
		return distributorFk;
	}

	public void setDistributorFk(Integer distributorFk) {
		this.distributorFk = distributorFk;
	}

	public Integer getDistributorItemFk() {
		return distributorItemFk;
	}

	public void setDistributorItemFk(Integer distributorItemFk) {
		this.distributorItemFk = distributorItemFk;
	}

	public Integer getDistributorItemsMapFk() {
		return distributorItemsMapFk;
	}

	public void setDistributorItemsMapFk(Integer distributorItemsMapFk) {
		this.distributorItemsMapFk = distributorItemsMapFk;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
