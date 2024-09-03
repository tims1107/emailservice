package com.spectra.asr.dto.generator;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class GeneratorFieldDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer generatorFieldPk;
	private Integer generatorFk;
	private String generatorField;
	private String generatorFieldType;
	private Integer generatorFieldSequence;
	private String generatorFieldValue;
	private Integer generatorFieldsGroup;
	
	private List<String> excludeGeneratorFieldList;
	
	public List<String> getExcludeGeneratorFieldList() {
		return excludeGeneratorFieldList;
	}

	public void setExcludeGeneratorFieldList(List<String> excludeGeneratorFieldList) {
		this.excludeGeneratorFieldList = excludeGeneratorFieldList;
	}

	public Integer getGeneratorFieldsGroup() {
		return generatorFieldsGroup;
	}

	public void setGeneratorFieldsGroup(Integer generatorFieldsGroup) {
		this.generatorFieldsGroup = generatorFieldsGroup;
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

	public Integer getGeneratorFieldPk() {
		return generatorFieldPk;
	}

	public void setGeneratorFieldPk(Integer generatorFieldPk) {
		this.generatorFieldPk = generatorFieldPk;
	}

	public Integer getGeneratorFk() {
		return generatorFk;
	}

	public void setGeneratorFk(Integer generatorFk) {
		this.generatorFk = generatorFk;
	}

	public String getGeneratorField() {
		return generatorField;
	}

	public void setGeneratorField(String generatorField) {
		this.generatorField = generatorField;
	}

	public String getGeneratorFieldType() {
		return generatorFieldType;
	}

	public void setGeneratorFieldType(String generatorFieldType) {
		this.generatorFieldType = generatorFieldType;
	}

	public Integer getGeneratorFieldSequence() {
		return generatorFieldSequence;
	}

	public void setGeneratorFieldSequence(Integer generatorFieldSequence) {
		this.generatorFieldSequence = generatorFieldSequence;
	}

	public String getGeneratorFieldValue() {
		return generatorFieldValue;
	}

	public void setGeneratorFieldValue(String generatorFieldValue) {
		this.generatorFieldValue = generatorFieldValue;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
