package com.spectra.asr.dto.patient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class NTERecord {
	
	private String testNteComment;
	private String nteTestCode;
	private String nteCompTestCode;
	private String nteResultName;

	private Integer sequenceNo;
	
	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getTestNteComment() {
		return testNteComment;
	}
	
	public void setTestNteComment(String testNteComment) {
		this.testNteComment = testNteComment;
	}
	public String getNteTestCode() {
		return nteTestCode;
	}
	public void setNteTestCode(String nteTestCode) {
		this.nteTestCode = nteTestCode;
	}

	public String getNteCompTestCode() {
		return nteCompTestCode;
	}

	public void setNteCompTestCode(String nteCompTestCode) {
		this.nteCompTestCode = nteCompTestCode;
	}

	public String getNteResultName() {
		return nteResultName;
	}

	public void setNteResultName(String nteResultName) {
		this.nteResultName = nteResultName;
	}
	
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}	

}
