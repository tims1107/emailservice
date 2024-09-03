package com.spectra.asr.dto.generator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class GeneratorFieldsMapDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer generatorFieldsMapPk;
	private Integer generatorFk;
	private Integer generatorFieldsGroup;
	
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

	public Integer getGeneratorFieldsMapPk() {
		return generatorFieldsMapPk;
	}

	public void setGeneratorFieldsMapPk(Integer generatorFieldsMapPk) {
		this.generatorFieldsMapPk = generatorFieldsMapPk;
	}

	public Integer getGeneratorFk() {
		return generatorFk;
	}

	public void setGeneratorFk(Integer generatorFk) {
		this.generatorFk = generatorFk;
	}

	public Integer getGeneratorFieldsGroup() {
		return generatorFieldsGroup;
	}

	public void setGeneratorFieldsGroup(Integer generatorFieldsGroup) {
		this.generatorFieldsGroup = generatorFieldsGroup;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
