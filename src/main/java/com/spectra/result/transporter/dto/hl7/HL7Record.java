package com.spectra.result.transporter.dto.hl7;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class HL7Record implements Serializable {
/*	
	private String averityfacility_id;
	private String initialfacility_id;	


	private String facility_id;
	private String accession_number;
	//private String order_number;

	private List patients = new ArrayList();

	private Date test_rel_dateformat;
	private String test_release_date;
*/

	private String averityFacilityId;
	private String initialFacilityId;
	private String facilityId;
	private String accessionNumber;
	private List<PatientRecord> patientRecordList = new ArrayList<PatientRecord>();
	private Date testRelDateformat;
	private String testReleaseDate;
	
	public String getTestReleaseDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(this.getTestRelDateformat() != null){
			this.testReleaseDate = dateFormat.format(this.getTestRelDateformat());
		}
		return testReleaseDate;
	}
	public void setTestReleaseDate(String testReleaseDate) {
		this.testReleaseDate = testReleaseDate;
	}
	
	public Date getTestRelDateformat() {
		return testRelDateformat;
	}
	public void setTestRelDateformat(Date testRelDateformat) {
		this.testRelDateformat = testRelDateformat;
	}
	
	public List<PatientRecord> getPatientRecordList() {
		return patientRecordList;
	}
	public void setPatientRecordList(List<PatientRecord> patientRecordList) {
		this.patientRecordList = patientRecordList;
	}
	
	public String getAccessionNumber() {
		return accessionNumber;
	}
	public void setAccessionNumber(String accessionNumber) {
		this.accessionNumber = accessionNumber;
	}
	
	public String getFacilityId() {
		StringBuilder facidBuilder = new StringBuilder();
		if(this.facilityId != null){
			facidBuilder.append(this.facilityId);
			if(this.facilityId.startsWith("7")){
				facidBuilder.append("E");
			}
			this.facilityId = facidBuilder.toString();
		}
		return this.facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	
	public String getInitialFacilityId() {
		return initialFacilityId;
	}
	public void setInitialFacilityId(String initialFacilityId) {
		this.initialFacilityId = initialFacilityId;
	}
	
	public String getAverityFacilityId() {
		return averityFacilityId;
	}
	public void setAverityFacilityId(String averityFacilityId) {
		this.averityFacilityId = averityFacilityId;
	}


	/*
	public String getAverityfacility_id() {
		return averityfacility_id;
	}
	public void setAverityfacility_id(String averityfacility_id) {
		this.averityfacility_id = averityfacility_id;
	}

	public List getPatients() {
		return patients;
	}
	public void setPatients(List patients) {
		this.patients = patients;
	}

	public Date getTest_rel_dateformat() {
		return test_rel_dateformat;
	}
	public void setTest_rel_dateformat(Date test_rel_dateformat) {
		this.test_rel_dateformat = test_rel_dateformat;
	}
	public String getTest_release_date() {
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		
		if(getTest_rel_dateformat() != null)
			this.test_release_date = dateFormat.format(getTest_rel_dateformat());
		
		return test_release_date;
	}
	public void setTest_release_date(String test_release_date) {
		this.test_release_date = test_release_date;
	}
	public String getFacility_id(){ 
		String facid = this.facility_id;
		if(this.facility_id != null)
		{
		if(facid.startsWith("7")){
			facid = this.facility_id + "E";
		}else{
			facid = this.facility_id;
		}
		}
		return facid;
	}
	public void setFacility_id(String facility_id) {
		this.facility_id = facility_id;
	}
	public String getAccession_number() {
		return accession_number;
	}
	public void setAccession_number(String accession_number) {
		this.accession_number = accession_number;
	}
	public String getInitialfacility_id() {
		return initialfacility_id;
	}
	public void setInitialfacility_id(String initialfacility_id) {
		this.initialfacility_id = initialfacility_id;
	}
*/	
	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
