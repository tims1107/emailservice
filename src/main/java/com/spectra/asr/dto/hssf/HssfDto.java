package com.spectra.asr.dto.hssf;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.ss.usermodel.Workbook;

import com.spectra.asr.dto.ResultDto;

public class HssfDto extends ResultDto {
	private String state;
	private String county;
	private Workbook workbook;
	
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
	public Workbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}
	
	public String toString(){
		//StringBuilder builder = new StringBuilder(super.toString());
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
