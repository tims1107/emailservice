package com.spectra.asr.dto.state;

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.spectra.asr.dto.ResultDto;

public class StateResultDto extends ResultDto {
	private String facilityId;
	private String cid;
	private String accessionNo;
	private String ethnicGroup;
	private String patientRace;
	private String mrn;
	private String patientFullName;
	private String patientLastName;
	private String patientFirstName;
	private String patientMiddleName;
	private Timestamp dateOfBirth;
	private String gender;
	private String patientSsn;
	private String npi;
	private String orderingPhysicianName;
	private String reportNotes;
	private Timestamp specimenReceiveDate;
	private Timestamp collectionDate;
	private String collectionTime;
	private Timestamp collectionDateTime;
	private String drawFreq;
	private Timestamp resRprtStatusChngDtTime;
	private String orderDetailStatus;
	private String orderTestCode;
	private String orderTestName;
	private String resultTestCode;
	private String resultTestName;
	//private String resultName;
	//private String testName;
	private String resultStatus;
	private String textualResult;
	private String textualResultFull;
	private BigDecimal numericResult;
	private String units;
	private String referenceRange;
	private String abnormalFlag;
	private Timestamp releaseDate;
	private Timestamp releaseTime;
	private Timestamp releaseDateTime;
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
	private String eastWestFlag;
	private String internalExternalFlag;
	private String actiFacilityId;
	private Timestamp lastUpdateTime;
	
	private String newHorizonCode;
	private String oldTestCode;
	//private String ethnicGroup;
	//private String patientRace;
	
	private String processed;
	private Timestamp processedTime;
	private Integer hoursLoaded;

	private String fmcNumber;
	private String clinicalManager;
	private String medicalDirector;
	
	private String resultType;
	
	private String patientAccountCounty;
	
	private Integer sequenceNo;
	
	private String facilityAccountStatus;
	private String facilityActiveFlag;
	private String microIsolate;
	private String microOrganismName;
	private Integer labFk;
	
	private String reportableState;
	private String sourceState;
	
	private String notifiedFlag;
	private Timestamp notifiedTime;
	
	private String eastWest;
	
	private Double convertedResult;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	private String deviceName;
	
	public Double getConvertedResult() {
		return convertedResult;
	}
	public void setConvertedResult(Double convertedResult) {
		this.convertedResult = convertedResult;
	}
	public String getEastWest() {
		return eastWest;
	}
	public void setEastWest(String eastWest) {
		this.eastWest = eastWest;
	}
	public String getNotifiedFlag() {
		return notifiedFlag;
	}
	public void setNotifiedFlag(String notifiedFlag) {
		this.notifiedFlag = notifiedFlag;
	}
	public Timestamp getNotifiedTime() {
		return notifiedTime;
	}
	public void setNotifiedTime(Timestamp notifiedTime) {
		this.notifiedTime = notifiedTime;
	}
	public String getReportableState() {
		return reportableState;
	}
	public void setReportableState(String reportableState) {
		this.reportableState = reportableState;
	}
	public String getSourceState() {
		return sourceState;
	}
	public void setSourceState(String sourceState) {
		this.sourceState = sourceState;
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
	public String getEthnicGroup() {
		return ethnicGroup;
	}
	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}
	public String getPatientRace() {
		return patientRace;
	}
//	public void setPatientRace(String patientRace) {
//		this.patientRace = patientRace;
//	}

	public void setPatientRace(String patientRace) {
		this.patientRace = patientRace;
	}

	public String getMrn() {
		return mrn;
	}
	public void setMrn(String mrn) {
		this.mrn = mrn;
	}
	public String getPatientFullName() {
		return patientFullName;
	}
	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}
	public String getPatientLastName() {
		return patientLastName;
	}
	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientMiddleName() {
		return patientMiddleName;
	}
	public void setPatientMiddleName(String patientMiddleName) {
		this.patientMiddleName = patientMiddleName;
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
	public String getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	public Timestamp getCollectionDateTime() {
		return collectionDateTime;
	}
	public void setCollectionDateTime(Timestamp collectionDateTime) {
		this.collectionDateTime = collectionDateTime;
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
	public String getOrderDetailStatus() {
		return orderDetailStatus;
	}
	public void setOrderDetailStatus(String orderDetailStatus) {
		this.orderDetailStatus = orderDetailStatus;
	}
	public String getOrderTestCode() {
		return orderTestCode;
	}
	public void setOrderTestCode(String orderTestCode) {
		this.orderTestCode = orderTestCode;
	}
	public String getOrderTestName() {
		return orderTestName;
	}
	public void setOrderTestName(String orderTestName) {
		this.orderTestName = orderTestName;
	}
	public String getResultTestCode() {
		return resultTestCode;
	}
	public void setResultTestCode(String resultTestCode) {
		this.resultTestCode = resultTestCode;
	}
	public String getResultTestName() {
		return resultTestName;
	}
	public void setResultTestName(String resultTestName) {
		this.resultTestName = resultTestName;
	}
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getTextualResult() {
		return textualResult;
	}
	public void setTextualResult(String textualResult) {
		this.textualResult = textualResult;
	}
	public String getTextualResultFull() {
		return textualResultFull;
	}
	public void setTextualResultFull(String textualResultFull) {
		this.textualResultFull = textualResultFull;
	}
	public BigDecimal getNumericResult() {
		return numericResult;
	}
	public void setNumericResult(BigDecimal numericResult) {
		this.numericResult = numericResult;
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
	public Timestamp getReleaseDateTime() {
		return releaseDateTime;
	}
	public void setReleaseDateTime(Timestamp releaseDateTime) {
		this.releaseDateTime = releaseDateTime;
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
	public String getSourceOfComment() {
		return sourceOfComment;
	}
	public void setSourceOfComment(String sourceOfComment) {
		this.sourceOfComment = sourceOfComment;
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
	public String getValueType() {
		return valueType;
	}
	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
	public String getEastWestFlag() {
		return eastWestFlag;
	}
	public void setEastWestFlag(String eastWestFlag) {
		this.eastWestFlag = eastWestFlag;
	}
	public String getInternalExternalFlag() {
		return internalExternalFlag;
	}
	public void setInternalExternalFlag(String internalExternalFlag) {
		this.internalExternalFlag = internalExternalFlag;
	}
	public String getActiFacilityId() {
		return actiFacilityId;
	}
	public void setActiFacilityId(String actiFacilityId) {
		this.actiFacilityId = actiFacilityId;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
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
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getPatientAccountCounty() {
		return patientAccountCounty;
	}
	public void setPatientAccountCounty(String patientAccountCounty) {
		this.patientAccountCounty = patientAccountCounty;
	}
	public Integer getSequenceNo() {
		return sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	public String getFacilityAccountStatus() {
		return facilityAccountStatus;
	}
	public void setFacilityAccountStatus(String facilityAccountStatus) {
		this.facilityAccountStatus = facilityAccountStatus;
	}
	public String getFacilityActiveFlag() {
		return facilityActiveFlag;
	}
	public void setFacilityActiveFlag(String facilityActiveFlag) {
		this.facilityActiveFlag = facilityActiveFlag;
	}
	public String getMicroIsolate() {
		return microIsolate;
	}
	public void setMicroIsolate(String microIsolate) {
		this.microIsolate = microIsolate;
	}
	public String getMicroOrganismName() {
		return microOrganismName;
	}
	public void setMicroOrganismName(String microOrganismName) {
		this.microOrganismName = microOrganismName;
	}
	public Integer getLabFk() {
		return labFk;
	}
	public void setLabFk(Integer labFk) {
		this.labFk = labFk;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
