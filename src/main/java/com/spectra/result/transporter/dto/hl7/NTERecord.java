package com.spectra.result.transporter.dto.hl7;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class NTERecord implements Serializable {
	/*
	private String test_NTE_comment = "";
	private String nte_test_code;
	private String nte_comp_test_code;
	private String nte_result_name;
	*/
	private String testNteComment;
	private String nteTestCode;
	private String nteCompTestCode;
	private String nteResultName;
	
	/*
	public String getTest_NTE_comment() {
	return test_NTE_comment;
	}
	
	public void setTest_NTE_comment(String test_NTE_comment) {
		this.test_NTE_comment = test_NTE_comment;
	}
	public String getNte_test_code() {
		return nte_test_code;
	}
	public void setNte_test_code(String nte_test_code) {
		this.nte_test_code = nte_test_code;
	}

	public String getNte_comp_test_code() {
		return nte_comp_test_code;
	}

	public void setNte_comp_test_code(String nte_comp_test_code) {
		this.nte_comp_test_code = nte_comp_test_code;
	}

	public String getNte_result_name() {
		return nte_result_name;
	}

	public void setNte_result_name(String nte_result_name) {
		this.nte_result_name = nte_result_name;
	}
	*/
	
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
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}	
}
