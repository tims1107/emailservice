package com.spectra.result.transporter.dto.rr;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import java.sql.Timestamp;
import java.io.Serializable;

public class RepositoryResultDto implements Serializable {
	private String facilityId;
	private String cid;
	private String accessionNo;
	private String mrn;
	private String patientName;
	private Timestamp dateOfBirth;
	private String gender;
	private String patientSsn;
	private String npi;
	private String orderingPhysicianName;
	private String reportNotes;
	private Timestamp specimenReceiveDate;
	private Timestamp collectionDate;
	private Timestamp collectionTime;
	private String drawFreq;
	private Timestamp resRprtStatusChngDtTime;
	private String orderStatus;
	private String compoundTestCode;
	private String testCode;
	private String resultName;
	private String testName;
	private String resultStatus;
	private String textualNumericResult;
	private String units;
	private String referenceRange;
	private String abnormalFlag;
	private Timestamp releaseDate;
	private Timestamp releaseTime;
	private String resultComments;
	private String performingLabId;
	private String orderMethod;
	private String specimenSource;
	private String orderNumber;
	
	private String loggingSite;
	private Integer age;
	private String facilityName;
	private String condCode;
	private String patientType;
	private String sourceOfComment;
	private String patientId;
	private String altPatientId;
	private Timestamp currentRunTime;
	private String requisitionStatus;
	private String facilityAddress1;
	private String facilityAddress2;
	private String facilityCity;
	private String facilityState;
	private String facilityZip;
	private String facilityPhone;
	private String patientAccountAddress1;
	private String patientAccountAddress2;
	private String patientAccountCity;
	private String patientAccountState;
	private String patientAccountZip;
	private String patientHomePhone;
	private String loincCode;
	private String loincName;
	private String valueType;
	private String actiFacilityId;
	private Timestamp lastUpdateTime;
	
	private String newHorizonCode;
	private String oldTestCode;
	private String ethnicGroup;
	private String patientRace;
	
	private String processed;
	private Timestamp processedTime;
	private Integer hoursLoaded;

	private String fmcNumber;
	private String clinicalManager;
	private String medicalDirector;
	
	private String resultType;
	
	private String patientAccountCounty;
	
	private Integer sequenceNo;
	
	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getPatientAccountCounty() {
		return patientAccountCounty;
	}

	public void setPatientAccountCounty(String patientAccountCounty) {
		this.patientAccountCounty = patientAccountCounty;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getFmcNumber() {
		return fmcNumber;
	}

	public void setFmcNumber(String fmcNumber) {
		this.fmcNumber = fmcNumber;
	}

	public String getClinicalManager() {
		return clinicalManager;
	}

	public void setClinicalManager(String clinicalManager) {
		this.clinicalManager = clinicalManager;
	}

	public String getMedicalDirector() {
		return medicalDirector;
	}

	public void setMedicalDirector(String medicalDirector) {
		this.medicalDirector = medicalDirector;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getActiFacilityId() {
		return actiFacilityId;
	}

	public void setActiFacilityId(String actiFacilityId) {
		this.actiFacilityId = actiFacilityId;
	}

	public String getProcessed() {
		return processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}

	public Timestamp getProcessedTime() {
		return processedTime;
	}

	public void setProcessedTime(Timestamp processedTime) {
		this.processedTime = processedTime;
	}

	public Integer getHoursLoaded() {
		return hoursLoaded;
	}

	public void setHoursLoaded(Integer hoursLoaded) {
		this.hoursLoaded = hoursLoaded;
	}

	public String getLoggingSite() {
		return loggingSite;
	}

	public void setLoggingSite(String loggingSite) {
		this.loggingSite = loggingSite;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getCondCode() {
		return condCode;
	}

	public void setCondCode(String condCode) {
		this.condCode = condCode;
	}

	public String getPatientType() {
		return patientType;
	}

	public void setPatientType(String patientType) {
		this.patientType = patientType;
	}

	public String getPatientId() {
		return patientId;
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getAltPatientId() {
		return altPatientId;
	}

	public void setAltPatientId(String altPatientId) {
		this.altPatientId = altPatientId;
	}

	public Timestamp getCurrentRunTime() {
		return currentRunTime;
	}

	public void setCurrentRunTime(Timestamp currentRunTime) {
		this.currentRunTime = currentRunTime;
	}

	public String getRequisitionStatus() {
		return requisitionStatus;
	}

	public void setRequisitionStatus(String requisitionStatus) {
		this.requisitionStatus = requisitionStatus;
	}

	public String getFacilityAddress1() {
		return facilityAddress1;
	}

	public void setFacilityAddress1(String facilityAddress1) {
		this.facilityAddress1 = facilityAddress1;
	}

	public String getFacilityAddress2() {
		return facilityAddress2;
	}

	public void setFacilityAddress2(String facilityAddress2) {
		this.facilityAddress2 = facilityAddress2;
	}

	public String getFacilityCity() {
		return facilityCity;
	}

	public void setFacilityCity(String facilityCity) {
		this.facilityCity = facilityCity;
	}

	public String getFacilityState() {
		return facilityState;
	}

	public void setFacilityState(String facilityState) {
		this.facilityState = facilityState;
	}

	public String getFacilityZip() {
		return facilityZip;
	}

	public void setFacilityZip(String facilityZip) {
		this.facilityZip = facilityZip;
	}

	public String getFacilityPhone() {
		return facilityPhone;
	}

	public void setFacilityPhone(String facilityPhone) {
		this.facilityPhone = facilityPhone;
	}

	public String getPatientAccountAddress1() {
		return patientAccountAddress1;
	}

	public void setPatientAccountAddress1(String patientAccountAddress1) {
		this.patientAccountAddress1 = patientAccountAddress1;
	}

	public String getPatientAccountAddress2() {
		return patientAccountAddress2;
	}

	public void setPatientAccountAddress2(String patientAccountAddress2) {
		this.patientAccountAddress2 = patientAccountAddress2;
	}

	public String getPatientAccountCity() {
		return patientAccountCity;
	}

	public void setPatientAccountCity(String patientAccountCity) {
		this.patientAccountCity = patientAccountCity;
	}

	public String getPatientAccountState() {
		return patientAccountState;
	}

	public void setPatientAccountState(String patientAccountState) {
		this.patientAccountState = patientAccountState;
	}

	public String getPatientAccountZip() {
		return patientAccountZip;
	}

	public void setPatientAccountZip(String patientAccountZip) {
		this.patientAccountZip = patientAccountZip;
	}

	public String getPatientHomePhone() {
		return patientHomePhone;
	}

	public void setPatientHomePhone(String patientHomePhone) {
		this.patientHomePhone = patientHomePhone;
	}

	public String getLoincCode() {
		return loincCode;
	}

	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
	}

	public String getLoincName() {
		return loincName;
	}

	public void setLoincName(String loincName) {
		this.loincName = loincName;
	}

	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getAccessionNo() {
		return accessionNo;
	}

	public void setAccessionNo(String accessionNo) {
		this.accessionNo = accessionNo;
	}

	public String getMrn() {
		return mrn;
	}

	public void setMrn(String mrn) {
		this.mrn = mrn;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPatientSsn() {
		return patientSsn;
	}

	public void setPatientSsn(String patientSsn) {
		this.patientSsn = patientSsn;
	}

	public String getNpi() {
		return npi;
	}

	public void setNpi(String npi) {
		this.npi = npi;
	}

	public String getOrderingPhysicianName() {
		return orderingPhysicianName;
	}

	public void setOrderingPhysicianName(String orderingPhysicianName) {
		this.orderingPhysicianName = orderingPhysicianName;
	}

	public String getReportNotes() {
		return reportNotes;
	}

	public void setReportNotes(String reportNotes) {
		this.reportNotes = reportNotes;
	}

	public Timestamp getSpecimenReceiveDate() {
		return specimenReceiveDate;
	}

	public void setSpecimenReceiveDate(Timestamp specimenReceiveDate) {
		this.specimenReceiveDate = specimenReceiveDate;
	}

	public Timestamp getCollectionDate() {
		return collectionDate;
	}

	public void setCollectionDate(Timestamp collectionDate) {
		this.collectionDate = collectionDate;
	}

	public Timestamp getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(Timestamp collectionTime) {
		this.collectionTime = collectionTime;
	}

	public String getDrawFreq() {
		return drawFreq;
	}

	public void setDrawFreq(String drawFreq) {
		this.drawFreq = drawFreq;
	}

	public Timestamp getResRprtStatusChngDtTime() {
		return resRprtStatusChngDtTime;
	}

	public void setResRprtStatusChngDtTime(Timestamp resRprtStatusChngDtTime) {
		this.resRprtStatusChngDtTime = resRprtStatusChngDtTime;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCompoundTestCode() {
		return compoundTestCode;
	}

	public void setCompoundTestCode(String compoundTestCode) {
		this.compoundTestCode = compoundTestCode;
	}

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getTextualNumericResult() {
		return textualNumericResult;
	}

	public void setTextualNumericResult(String textualNumericResult) {
		this.textualNumericResult = textualNumericResult;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public String getReferenceRange() {
		return referenceRange;
	}

	public void setReferenceRange(String referenceRange) {
		this.referenceRange = referenceRange;
	}

	public String getAbnormalFlag() {
		return abnormalFlag;
	}

	public void setAbnormalFlag(String abnormalFlag) {
		this.abnormalFlag = abnormalFlag;
	}

	public Timestamp getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Timestamp releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Timestamp getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(Timestamp releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getResultComments() {
		return resultComments;
	}

	public void setResultComments(String resultComments) {
		this.resultComments = resultComments;
	}

	public String getPerformingLabId() {
		return performingLabId;
	}

	public void setPerformingLabId(String performingLabId) {
		this.performingLabId = performingLabId;
	}

	public String getOrderMethod() {
		return orderMethod;
	}

	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}

	public String getSpecimenSource() {
		return specimenSource;
	}

	public void setSpecimenSource(String specimenSource) {
		this.specimenSource = specimenSource;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getNewHorizonCode() {
		return newHorizonCode;
	}

	public void setNewHorizonCode(String newHorizonCode) {
		this.newHorizonCode = newHorizonCode;
	}

	public String getOldTestCode() {
		return oldTestCode;
	}

	public void setOldTestCode(String oldTestCode) {
		this.oldTestCode = oldTestCode;
	}

	public String getSourceOfComment() {
		return sourceOfComment;
	}

	public void setSourceOfComment(String sourceOfComment) {
		this.sourceOfComment = sourceOfComment;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public String getPatientRace() {
		return patientRace;
	}

	public void setPatientRace(String patientRace) {
		this.patientRace = patientRace;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
