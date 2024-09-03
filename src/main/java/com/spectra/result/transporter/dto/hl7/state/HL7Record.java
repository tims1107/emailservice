package com.spectra.result.transporter.dto.hl7.state;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.result.transporter.dto.hl7.state.PatientRecord;

public class HL7Record {
	private String averityFacilityId;
	private String initialFacilityId;	
	private String facilityId;
	private String accessionNumber;
	private List<PatientRecord> patientRecordList = new ArrayList<PatientRecord>();
	private Date testRelDateformat;
	private String testReleaseDate;

	public String getAverityFacilityId() {
		return averityFacilityId;
	}
	public void setAverityFacilityId(String averityFacilityId) {
		this.averityFacilityId = averityFacilityId;
	}
	
	public List<PatientRecord> getPatientRecordList() {
		return patientRecordList;
	}
	public void setPatientRecordList(List<PatientRecord> patientRecordList) {
		this.patientRecordList = patientRecordList;
	}
	
	public Date getTestRelDateformat() {
		return testRelDateformat;
	}
	public void setTestRelDateformat(Date testRelDateformat) {
		this.testRelDateformat = testRelDateformat;
	}
	public String getTestReleaseDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getTestRelDateformat() != null)
			this.testReleaseDate = dateFormat.format(getTestRelDateformat());
		
		return testReleaseDate;
	}
	public void setTestReleaseDate(String testReleaseDate) {
		this.testReleaseDate = testReleaseDate;
	}
	public String getFacilityId(){ 
		StringBuilder facidBuilder = new StringBuilder();
		if(this.facilityId != null){
			facidBuilder.append(this.facilityId);
			if(this.facilityId.startsWith("8")){
				facidBuilder.append("T");
			}else if(this.facilityId.startsWith("7")){
				facidBuilder.append("E");
			}
			this.facilityId = facidBuilder.toString();
		}
		return this.facilityId;	
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getAccessionNumber() {
		return accessionNumber;
	}
	public void setAccessionNumber(String accession_number) {
		this.accessionNumber = accession_number;
	}
	public String getInitialFacilityId() {
		return initialFacilityId;
	}
	public void setInitialFacilityId(String initialFacilityId) {
		this.initialFacilityId = initialFacilityId;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
