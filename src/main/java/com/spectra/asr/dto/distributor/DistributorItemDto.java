package com.spectra.asr.dto.distributor;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class DistributorItemDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer distributorItemPk;
	private Integer distributorFk;
	private String distributorItem;
	private String distributorItemType;
	private Integer distributorItemsGroup;
	private String distributorItemValue;
	
	public Integer getDistributorItemsGroup() {
		return distributorItemsGroup;
	}

	public void setDistributorItemsGroup(Integer distributorItemsGroup) {
		this.distributorItemsGroup = distributorItemsGroup;
	}

	public String getDistributorItemValue() {
		return distributorItemValue;
	}

	public void setDistributorItemValue(String distributorItemValue) {
		this.distributorItemValue = distributorItemValue;
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

	public Integer getDistributorItemPk() {
		return distributorItemPk;
	}

	public void setDistributorItemPk(Integer distributorItemPk) {
		this.distributorItemPk = distributorItemPk;
	}

	public Integer getDistributorFk() {
		return distributorFk;
	}

	public void setDistributorFk(Integer distributorFk) {
		this.distributorFk = distributorFk;
	}

	public String getDistributorItem() {
		return distributorItem;
	}

	public void setDistributorItem(String distributorItem) {
		this.distributorItem = distributorItem;
	}

	public String getDistributorItemType() {
		return distributorItemType;
	}

	public void setDistributorItemType(String distributorItemType) {
		this.distributorItemType = distributorItemType;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
