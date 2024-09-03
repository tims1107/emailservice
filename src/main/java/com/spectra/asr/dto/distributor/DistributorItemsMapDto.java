package com.spectra.asr.dto.distributor;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class DistributorItemsMapDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer distributorItemsMapPk;
	private Integer distributorFk;
	private Integer distributorItemsGroup;
	
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

	public Integer getDistributorItemsMapPk() {
		return distributorItemsMapPk;
	}

	public void setDistributorItemsMapPk(Integer distributorItemsMapPk) {
		this.distributorItemsMapPk = distributorItemsMapPk;
	}

	public Integer getDistributorFk() {
		return distributorFk;
	}

	public void setDistributorFk(Integer distributorFk) {
		this.distributorFk = distributorFk;
	}

	public Integer getDistributorItemsGroup() {
		return distributorItemsGroup;
	}

	public void setDistributorItemsGroup(Integer distributorItemsGroup) {
		this.distributorItemsGroup = distributorItemsGroup;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
