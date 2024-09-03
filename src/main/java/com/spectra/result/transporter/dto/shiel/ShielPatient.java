package com.spectra.result.transporter.dto.shiel;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ShielPatient implements Serializable {
	protected String patientIDExternal;
	protected String facilityIDExternal;
	protected String patientIDInternal;
	protected String facilityIDInternal;
	protected String patientIDAlt;
	protected String lastName;
	protected String firstName;
	protected String middleName;
	protected String nameSuffix;
	protected String dateOfBirth;
	//protected Timestamp dateOfBirth;
	protected String patientAge;
	protected String placeOfBirth;
	protected String countryOfOrigin;
	protected String lengthResidency;
	protected String raceCode;
	protected String ethnicGroupCode;
	protected String sexCode;
	protected String addressLine1;
	protected String city;
	protected String state;
	protected String country;
	protected String zipCode;
	protected String countyCode;
	protected String homePhone;
	protected String ssn;
	protected String parentLastName;
	protected String parentFirstName;
	protected String parentMiddleInitial;
	protected String parentSuffix;
	protected String parentAddressLine1;
	protected String parentCity;
	protected String parentState;
	protected String parentCountry;
	protected String parentZipCode;
	protected String parentPhone;
	protected String pregnancyFlag;
	protected String deptCorrectionID;
	protected String insuranceBillingNumber;
	protected String typeOfInsurance;
	protected String vaccineTrialParticipation;
	protected String providerLastName;
	protected String providerFirstName;
	protected String providerMiddleName;
	protected String providernameSuffix;
	protected String providerAddressLine1;
	protected String providerCity;
	protected String providerState;
	protected String providerZipCode;
	protected String providerPhone;
	protected String facilityName;
	protected String facilityAddressLine1;
	protected String facilityCity;
	protected String facilityState;
	protected String facilityZip;
	protected String facilityPhone;
	protected String pathologistLastName;
	protected String pathologistLicenseNumber;
	protected String pathologistStateOfLicense;
	protected String accessionNum;
	protected String snomedCode;
	protected String collectionDate;
	//protected Timestamp collectionDate;
	protected String specimenSourceCode;
	protected String specimenSourceName;
	protected String resultReportDate;
	//protected Timestamp resultReportDate;
	protected String resultStatusCode;
	protected String dataType;
	protected String loincCode;
	protected String loincDesc;
	protected String localCode;
	protected String localDesc;
	protected String unitOfMeasure;
	protected String referenceRange;
	protected String observationResultStatus;
	protected String observationMethod;
	protected String testResult;
	protected String pathReport;
	protected String obxSnomedCode;
	protected String specimenDescription;
	protected String icdCode;
	protected String icdRevNo;
	protected String clinicalHistory;
	protected String natureOfSpecimen;
	protected String grossPathology;
	protected String microscopicPathology;
	protected String finalDx;
	protected String comment;
	protected String supplementalReports;
	protected String stagingParameters;
	protected String sendingFacilityName;
	protected String sendingFacilityClia;
	protected String messageDate;
	//protected Timestamp messageDate;
	protected String receivingApplication;
	protected String recordTerminationIndicator;
	
	protected Timestamp lastUpdateTime;	
	protected String delimitedString;
	protected String resultType;
	protected String processed;
	protected Timestamp processedTime;
	protected Integer hoursLoaded;
	protected Timestamp fileLastUpdateTime;
	
	public Timestamp getFileLastUpdateTime() {
		return fileLastUpdateTime;
	}
	public void setFileLastUpdateTime(Timestamp fileLastUpdateTime) {
		this.fileLastUpdateTime = fileLastUpdateTime;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
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
	public String getDelimitedString() {
		return delimitedString;
	}
	public void setDelimitedString(String delimitedString) {
		this.delimitedString = delimitedString;
	}
	public String getPatientIDExternal() {
		return patientIDExternal;
	}
	public void setPatientIDExternal(String patientIDExternal) {
		this.patientIDExternal = patientIDExternal;
	}
	public String getFacilityIDExternal() {
		return facilityIDExternal;
	}
	public void setFacilityIDExternal(String facilityIDExternal) {
		this.facilityIDExternal = facilityIDExternal;
	}
	public String getPatientIDInternal() {
		return patientIDInternal;
	}
	public void setPatientIDInternal(String patientIDInternal) {
		this.patientIDInternal = patientIDInternal;
	}
	public String getFacilityIDInternal() {
		return facilityIDInternal;
	}
	public void setFacilityIDInternal(String facilityIDInternal) {
		this.facilityIDInternal = facilityIDInternal;
	}
	public String getPatientIDAlt() {
		return patientIDAlt;
	}
	public void setPatientIDAlt(String patientIDAlt) {
		this.patientIDAlt = patientIDAlt;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getNameSuffix() {
		return nameSuffix;
	}
	public void setNameSuffix(String nameSuffix) {
		this.nameSuffix = nameSuffix;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPatientAge() {
		return patientAge;
	}
	public void setPatientAge(String patientAge) {
		this.patientAge = patientAge;
	}
	public String getPlaceOfBirth() {
		return placeOfBirth;
	}
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}
	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}
	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}
	public String getLengthResidency() {
		return lengthResidency;
	}
	public void setLengthResidency(String lengthResidency) {
		this.lengthResidency = lengthResidency;
	}
	public String getRaceCode() {
		return raceCode;
	}
	public void setRaceCode(String raceCode) {
		this.raceCode = raceCode;
	}
	public String getEthnicGroupCode() {
		return ethnicGroupCode;
	}
	public void setEthnicGroupCode(String ethnicGroupCode) {
		this.ethnicGroupCode = ethnicGroupCode;
	}
	public String getSexCode() {
		return sexCode;
	}
	public void setSexCode(String sexCode) {
		this.sexCode = sexCode;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getParentLastName() {
		return parentLastName;
	}
	public void setParentLastName(String parentLastName) {
		this.parentLastName = parentLastName;
	}
	public String getParentFirstName() {
		return parentFirstName;
	}
	public void setParentFirstName(String parentFirstName) {
		this.parentFirstName = parentFirstName;
	}
	public String getParentMiddleInitial() {
		return parentMiddleInitial;
	}
	public void setParentMiddleInitial(String parentMiddleInitial) {
		this.parentMiddleInitial = parentMiddleInitial;
	}
	public String getParentSuffix() {
		return parentSuffix;
	}
	public void setParentSuffix(String parentSuffix) {
		this.parentSuffix = parentSuffix;
	}
	public String getParentAddressLine1() {
		return parentAddressLine1;
	}
	public void setParentAddressLine1(String parentAddressLine1) {
		this.parentAddressLine1 = parentAddressLine1;
	}
	public String getParentCity() {
		return parentCity;
	}
	public void setParentCity(String parentCity) {
		this.parentCity = parentCity;
	}
	public String getParentState() {
		return parentState;
	}
	public void setParentState(String parentState) {
		this.parentState = parentState;
	}
	public String getParentCountry() {
		return parentCountry;
	}
	public void setParentCountry(String parentCountry) {
		this.parentCountry = parentCountry;
	}
	public String getParentZipCode() {
		return parentZipCode;
	}
	public void setParentZipCode(String parentZipCode) {
		this.parentZipCode = parentZipCode;
	}
	public String getParentPhone() {
		return parentPhone;
	}
	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}
	public String getPregnancyFlag() {
		return pregnancyFlag;
	}
	public void setPregnancyFlag(String pregnancyFlag) {
		this.pregnancyFlag = pregnancyFlag;
	}
	public String getDeptCorrectionID() {
		return deptCorrectionID;
	}
	public void setDeptCorrectionID(String deptCorrectionID) {
		this.deptCorrectionID = deptCorrectionID;
	}
	public String getInsuranceBillingNumber() {
		return insuranceBillingNumber;
	}
	public void setInsuranceBillingNumber(String insuranceBillingNumber) {
		this.insuranceBillingNumber = insuranceBillingNumber;
	}
	public String getTypeOfInsurance() {
		return typeOfInsurance;
	}
	public void setTypeOfInsurance(String typeOfInsurance) {
		this.typeOfInsurance = typeOfInsurance;
	}
	public String getVaccineTrialParticipation() {
		return vaccineTrialParticipation;
	}
	public void setVaccineTrialParticipation(String vaccineTrialParticipation) {
		this.vaccineTrialParticipation = vaccineTrialParticipation;
	}
	public String getProviderLastName() {
		return providerLastName;
	}
	public void setProviderLastName(String providerLastName) {
		this.providerLastName = providerLastName;
	}
	public String getProviderFirstName() {
		return providerFirstName;
	}
	public void setProviderFirstName(String providerFirstName) {
		this.providerFirstName = providerFirstName;
	}
	public String getProviderMiddleName() {
		return providerMiddleName;
	}
	public void setProviderMiddleName(String providerMiddleName) {
		this.providerMiddleName = providerMiddleName;
	}
	public String getProvidernameSuffix() {
		return providernameSuffix;
	}
	public void setProvidernameSuffix(String providernameSuffix) {
		this.providernameSuffix = providernameSuffix;
	}
	public String getProviderAddressLine1() {
		return providerAddressLine1;
	}
	public void setProviderAddressLine1(String providerAddressLine1) {
		this.providerAddressLine1 = providerAddressLine1;
	}
	public String getProviderCity() {
		return providerCity;
	}
	public void setProviderCity(String providerCity) {
		this.providerCity = providerCity;
	}
	public String getProviderState() {
		return providerState;
	}
	public void setProviderState(String providerState) {
		this.providerState = providerState;
	}
	public String getProviderZipCode() {
		return providerZipCode;
	}
	public void setProviderZipCode(String providerZipCode) {
		this.providerZipCode = providerZipCode;
	}
	public String getProviderPhone() {
		return providerPhone;
	}
	public void setProviderPhone(String providerPhone) {
		this.providerPhone = providerPhone;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	public String getFacilityAddressLine1() {
		return facilityAddressLine1;
	}
	public void setFacilityAddressLine1(String facilityAddressLine1) {
		this.facilityAddressLine1 = facilityAddressLine1;
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
	public String getPathologistLastName() {
		return pathologistLastName;
	}
	public void setPathologistLastName(String pathologistLastName) {
		this.pathologistLastName = pathologistLastName;
	}
	public String getPathologistLicenseNumber() {
		return pathologistLicenseNumber;
	}
	public void setPathologistLicenseNumber(String pathologistLicenseNumber) {
		this.pathologistLicenseNumber = pathologistLicenseNumber;
	}
	public String getPathologistStateOfLicense() {
		return pathologistStateOfLicense;
	}
	public void setPathologistStateOfLicense(String pathologistStateOfLicense) {
		this.pathologistStateOfLicense = pathologistStateOfLicense;
	}
	public String getAccessionNum() {
		return accessionNum;
	}
	public void setAccessionNum(String accessionNum) {
		this.accessionNum = accessionNum;
	}
	public String getSnomedCode() {
		return snomedCode;
	}
	public void setSnomedCode(String snomedCode) {
		this.snomedCode = snomedCode;
	}
	public String getCollectionDate() {
		return collectionDate;
	}
	public void setCollectionDate(String collectionDate) {
		this.collectionDate = collectionDate;
	}
	public String getSpecimenSourceCode() {
		return specimenSourceCode;
	}
	public void setSpecimenSourceCode(String specimenSourceCode) {
		this.specimenSourceCode = specimenSourceCode;
	}
	public String getSpecimenSourceName() {
		return specimenSourceName;
	}
	public void setSpecimenSourceName(String specimenSourceName) {
		this.specimenSourceName = specimenSourceName;
	}
	public String getResultReportDate() {
		return resultReportDate;
	}
	public void setResultReportDate(String resultReportDate) {
		this.resultReportDate = resultReportDate;
	}
	public String getResultStatusCode() {
		return resultStatusCode;
	}
	public void setResultStatusCode(String resultStatusCode) {
		this.resultStatusCode = resultStatusCode;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getLoincCode() {
		return loincCode;
	}
	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
	}
	public String getLoincDesc() {
		return loincDesc;
	}
	public void setLoincDesc(String loincDesc) {
		this.loincDesc = loincDesc;
	}
	public String getLocalCode() {
		return localCode;
	}
	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}
	public String getLocalDesc() {
		return localDesc;
	}
	public void setLocalDesc(String localDesc) {
		this.localDesc = localDesc;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getReferenceRange() {
		return referenceRange;
	}
	public void setReferenceRange(String referenceRange) {
		this.referenceRange = referenceRange;
	}
	public String getObservationResultStatus() {
		return observationResultStatus;
	}
	public void setObservationResultStatus(String observationResultStatus) {
		this.observationResultStatus = observationResultStatus;
	}
	public String getObservationMethod() {
		return observationMethod;
	}
	public void setObservationMethod(String observationMethod) {
		this.observationMethod = observationMethod;
	}
	public String getTestResult() {
		return testResult;
	}
	public void setTestResult(String testResult) {
		this.testResult = testResult;
	}
	public String getPathReport() {
		return pathReport;
	}
	public void setPathReport(String pathReport) {
		this.pathReport = pathReport;
	}
	public String getObxSnomedCode() {
		return obxSnomedCode;
	}
	public void setObxSnomedCode(String obxSnomedCode) {
		this.obxSnomedCode = obxSnomedCode;
	}
	public String getSpecimenDescription() {
		return specimenDescription;
	}
	public void setSpecimenDescription(String specimenDescription) {
		this.specimenDescription = specimenDescription;
	}
	public String getIcdCode() {
		return icdCode;
	}
	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}
	public String getIcdRevNo() {
		return icdRevNo;
	}
	public void setIcdRevNo(String icdRevNo) {
		this.icdRevNo = icdRevNo;
	}
	public String getClinicalHistory() {
		return clinicalHistory;
	}
	public void setClinicalHistory(String clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}
	public String getNatureOfSpecimen() {
		return natureOfSpecimen;
	}
	public void setNatureOfSpecimen(String natureOfSpecimen) {
		this.natureOfSpecimen = natureOfSpecimen;
	}
	public String getGrossPathology() {
		return grossPathology;
	}
	public void setGrossPathology(String grossPathology) {
		this.grossPathology = grossPathology;
	}
	public String getMicroscopicPathology() {
		return microscopicPathology;
	}
	public void setMicroscopicPathology(String microscopicPathology) {
		this.microscopicPathology = microscopicPathology;
	}
	public String getFinalDx() {
		return finalDx;
	}
	public void setFinalDx(String finalDx) {
		this.finalDx = finalDx;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getSupplementalReports() {
		return supplementalReports;
	}
	public void setSupplementalReports(String supplementalReports) {
		this.supplementalReports = supplementalReports;
	}
	public String getStagingParameters() {
		return stagingParameters;
	}
	public void setStagingParameters(String stagingParameters) {
		this.stagingParameters = stagingParameters;
	}
	public String getSendingFacilityName() {
		return sendingFacilityName;
	}
	public void setSendingFacilityName(String sendingFacilityName) {
		this.sendingFacilityName = sendingFacilityName;
	}
	public String getSendingFacilityClia() {
		return sendingFacilityClia;
	}
	public void setSendingFacilityClia(String sendingFacilityClia) {
		this.sendingFacilityClia = sendingFacilityClia;
	}
	public String getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(String messageDate) {
		this.messageDate = messageDate;
	}
	public String getReceivingApplication() {
		return receivingApplication;
	}
	public void setReceivingApplication(String receivingApplication) {
		this.receivingApplication = receivingApplication;
	}
	public String getRecordTerminationIndicator() {
		return recordTerminationIndicator;
	}
	public void setRecordTerminationIndicator(String recordTerminationIndicator) {
		this.recordTerminationIndicator = recordTerminationIndicator;
	}

	public String toString(){
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append(ReflectionToStringBuilder.toString(this));
		return builder.toString();
	}
}
