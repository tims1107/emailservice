package com.spectra.asr.dto.distributor;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class DistributorDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer distributorPk;
	private Integer stateFk;
	private Integer generatorFk;
	private String cronExpression;
	private String runFrequency;
	private String distributionType;
	private String distributionContext;
	private String stateTarget;
	
	private String generatorFieldType;

	private String entityType;
	
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getGeneratorFieldType() {
		return generatorFieldType;
	}

	public void setGeneratorFieldType(String generatorFieldType) {
		this.generatorFieldType = generatorFieldType;
	}

	public String getStateTarget() {
		return stateTarget;
	}

	public void setStateTarget(String stateTarget) {
		this.stateTarget = stateTarget;
	}

	public String getDistributionContext() {
		return distributionContext;
	}

	public void setDistributionContext(String distributionContext) {
		this.distributionContext = distributionContext;
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

	public Integer getDistributorPk() {
		return distributorPk;
	}

	public void setDistributorPk(Integer distributorPk) {
		this.distributorPk = distributorPk;
	}

	public Integer getStateFk() {
		return stateFk;
	}

	public void setStateFk(Integer stateFk) {
		this.stateFk = stateFk;
	}

	public Integer getGeneratorFk() {
		return generatorFk;
	}

	public void setGeneratorFk(Integer generatorFk) {
		this.generatorFk = generatorFk;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getRunFrequency() {
		return runFrequency;
	}

	public void setRunFrequency(String runFrequency) {
		this.runFrequency = runFrequency;
	}

	public String getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(String distributionType) {
		this.distributionType = distributionType;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
