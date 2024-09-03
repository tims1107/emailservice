package com.spectra.asr.dto.generator;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class GeneratorDto extends ResultDto {
	private String state;
	private String stateAbbreviation;
	private Integer generatorPk;
	private Integer stateFk;
	private String fileMask;
	private String hl7Package;
	private String conversionContext;
	private String writerContext;
	private String writeBy;
	private String stateTarget;
	private String countyTarget;
	private String conversionStrategy;
	private String writerStrategy;
	private String eastWestFlag;
	private String entityType;
	
	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEastWestFlag() {
		return eastWestFlag;
	}

	public void setEastWestFlag(String eastWestFlag) {
		this.eastWestFlag = eastWestFlag;
	}

	public String getWriterStrategy() {
		return writerStrategy;
	}

	public void setWriterStrategy(String writerStrategy) {
		this.writerStrategy = writerStrategy;
	}

	public String getConversionStrategy() {
		return conversionStrategy;
	}

	public void setConversionStrategy(String conversionStrategy) {
		this.conversionStrategy = conversionStrategy;
	}

	public String getCountyTarget() {
		return countyTarget;
	}

	public void setCountyTarget(String countyTarget) {
		this.countyTarget = countyTarget;
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

	public String getStateTarget() {
		return stateTarget;
	}

	public void setStateTarget(String stateTarget) {
		this.stateTarget = stateTarget;
	}

	public Integer getGeneratorPk() {
		return generatorPk;
	}

	public void setGeneratorPk(Integer generatorPk) {
		this.generatorPk = generatorPk;
	}

	public Integer getStateFk() {
		return stateFk;
	}

	public void setStateFk(Integer stateFk) {
		this.stateFk = stateFk;
	}

	public String getFileMask() {
		return fileMask;
	}

	public void setFileMask(String fileMask) {
		this.fileMask = fileMask;
	}

	public String getHl7Package() {
		return hl7Package;
	}

	public void setHl7Package(String hl7Package) {
		this.hl7Package = hl7Package;
	}

	public String getConversionContext() {
		return conversionContext;
	}

	public void setConversionContext(String conversionContext) {
		this.conversionContext = conversionContext;
	}

	public String getWriterContext() {
		return writerContext;
	}

	public void setWriterContext(String writerContext) {
		this.writerContext = writerContext;
	}

	public String getWriteBy() {
		return writeBy;
	}

	public void setWriteBy(String writeBy) {
		this.writeBy = writeBy;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
