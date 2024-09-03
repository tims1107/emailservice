package com.spectra.asr.dto.hl7;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class HL7Dto extends ResultDto {
	private String state;
	private String county;
	private String hl7String;
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getHl7String() {
		return hl7String;
	}

	public void setHl7String(String hl7String) {
		this.hl7String = hl7String;
	}

	public String toString(){
		//StringBuilder builder = new StringBuilder(super.toString());
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
