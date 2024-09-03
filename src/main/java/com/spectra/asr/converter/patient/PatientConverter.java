package com.spectra.asr.converter.patient;

import com.spectra.asr.dto.patient.NTERecord;
import com.spectra.asr.dto.patient.OBXRecord;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateResultDto;
import lombok.extern.slf4j.Slf4j;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public final class PatientConverter {
	//static Logger log = Logger.getLogger(PatientConverter.class);
	
	public static PatientRecord stateResultDtoToPatientRecord(StateResultDto dto){
		PatientRecord patientRecord = null;
		if(dto != null){
			String sourceState = dto.getSourceState();
			
			patientRecord = new PatientRecord();
			patientRecord.setMhsOrderingFacId(dto.getFacilityId());
			patientRecord.setCid(dto.getCid());
			patientRecord.setAccessionNo(dto.getAccessionNo());
			//patientRecord.setAltPatientId(dto.getMrn());
			patientRecord.setAltPatientId(dto.getPatientId());
			
			StringBuilder patNameBuilder = new StringBuilder();
			if(dto.getPatientLastName() != null){
				patNameBuilder.append(dto.getPatientLastName()).append(",");
			}else{
				patNameBuilder.append("");
			}
			if(dto.getPatientFirstName() != null){
				patNameBuilder.append(dto.getPatientFirstName()).append(",");
			}else{
				patNameBuilder.append("");
			}
			if(dto.getPatientMiddleName() != null){
				String midName = dto.getPatientMiddleName();
				if(midName.equalsIgnoreCase("null")){
					midName = null;
				}
				patNameBuilder.append(midName).append(",");
				//patNameBuilder.append(dto.getPatientMiddleName()).append(",");
			}else{
				patNameBuilder.append("");
			}
			//String patName = patNameBuilder.substring(0, patNameBuilder.lastIndexOf(","));
			String patName = null;
			if(patNameBuilder.lastIndexOf(",") != -1){
				patName = patNameBuilder.substring(0, patNameBuilder.lastIndexOf(","));
			}else{
				patName = patNameBuilder.toString();
			}
			patientRecord.setPatientName(patName);
			
			patientRecord.setDob(dto.getDateOfBirth());
			patientRecord.setGender(dto.getGender());
			patientRecord.setPatientSsn(dto.getPatientSsn());
			patientRecord.setProviderId(dto.getNpi());
			patientRecord.setOrderingPhyName(dto.getOrderingPhysicianName());
			patientRecord.setReportNteComment(dto.getReportNotes());
			patientRecord.setSpecimenRecDateformat(dto.getSpecimenReceiveDate());
			patientRecord.setCollectionDateformat(dto.getCollectionDate());
			patientRecord.setCollectionTimeformat(dto.getCollectionDateTime());
			patientRecord.setDrawFreq(dto.getDrawFreq());
			patientRecord.setResRptStatusChngDtTimeformat(dto.getResRprtStatusChngDtTime());
			patientRecord.setRequisitionStatus(dto.getOrderDetailStatus());
			patientRecord.setCompoundTestCode(dto.getOrderTestCode());
			patientRecord.setSubTestCode(dto.getResultTestCode());
			patientRecord.setSpecimenSource(dto.getSpecimenSource());
			patientRecord.setOrderNumber(dto.getOrderNumber());
			
			patientRecord.setFacilityName(dto.getFacilityName());
			patientRecord.setFacilityAddress1(dto.getFacilityAddress1());
			patientRecord.setFacilityAddress2(dto.getFacilityAddress2());
			patientRecord.setFacilityCity(dto.getFacilityCity());
			patientRecord.setFacilityState(dto.getFacilityState());
			patientRecord.setFacilityZip(dto.getFacilityZip());
			patientRecord.setFacilityPhone(dto.getFacilityPhone());
			
			if(sourceState.equalsIgnoreCase("patient")){
				String patAddress1 = dto.getPatientAccountAddress1();
				patAddress1 = ((patAddress1 == null) || (patAddress1.indexOf("null") != -1)) ? "" : patAddress1;
				patientRecord.setPatientAddress1(patAddress1);
				
				String patAddress2 = dto.getPatientAccountAddress2();
				patAddress2 = ((patAddress2 == null) || (patAddress2.indexOf("null") != -1)) ? "" : patAddress2;
				patientRecord.setPatientAddress2(patAddress2);
				
				String patCity = dto.getPatientAccountCity();
				patCity = ((patCity == null) || (patCity.indexOf("null") != -1)) ? "" : patCity;
				patientRecord.setPatientCity(patCity);

				String patState = dto.getPatientAccountState();
				patState = ((patState == null) || (patState.indexOf("null") != -1)) ? "" : patState;
				patientRecord.setPatientState(patState);
				
				String patZip = dto.getPatientAccountZip();
				patZip = ((patZip == null) || (patZip.indexOf("null") != -1)) ? "" : patZip;
				patientRecord.setPatientZip(patZip);
				
				String patPhone = dto.getPatientHomePhone();
				patPhone = ((patPhone == null) || (patPhone.indexOf("null") != -1)) ? "" : patPhone;
				patientRecord.setPatientPhone(patPhone);
				
				patientRecord.setPatientOrigPhone(patPhone);
				
				String patCounty = dto.getPatientAccountCounty();
				patCounty = ((patCounty == null) || (patCounty.indexOf("null") != -1)) ? "" : patCounty;
				patientRecord.setPatientCounty(patCounty);
			}
			
			patientRecord.setMrnId(dto.getMrn());
			
			patientRecord.setFacilityId(dto.getFacilityId());
			
			patientRecord.setReleaseDate(dto.getReleaseDateTime());
			patientRecord.setReleaseTime(dto.getReleaseTime());
			
			patientRecord.setActiFacilityId(dto.getActiFacilityId());
			patientRecord.setFmcNumber(dto.getFmcNumber());
			
			patientRecord.setClinicalManager(dto.getClinicalManager());
			patientRecord.setMedicalDirector(dto.getMedicalDirector());
			
			patientRecord.setPerformingLabId(dto.getPerformingLabId());
			
			patientRecord.setLabFk(dto.getLabFk());
			
			patientRecord.setPatientRace(dto.getPatientRace());
			patientRecord.setEthnicGroup(dto.getEthnicGroup());
		}
		return patientRecord;
	}
	
	public static OBXRecord stateResultDtoToOBXRecord(StateResultDto dto){
		OBXRecord obxRecord = null;
		if(dto != null){
			obxRecord = new OBXRecord();
			obxRecord.setAccessionNo(dto.getAccessionNo());
			obxRecord.setCompoundTestCode(dto.getOrderTestCode());
			obxRecord.setSubTestCode(dto.getResultTestCode());
			obxRecord.setSeqResultName(dto.getResultTestName());
			obxRecord.setSeqTestName(dto.getOrderTestName());
			//obxRecord.setOrderStatus(dto.getResultStatus());
			obxRecord.setResultStatus(dto.getResultStatus());
			//obxRecord.setTextualNumResult(dto.getTextualResultFull());
			obxRecord.setTextualNumResult(dto.getTextualResult());
			obxRecord.setUnits(dto.getUnits());
			obxRecord.setRefRange(dto.getReferenceRange());
			obxRecord.setAbnormalFlag(dto.getAbnormalFlag());
			obxRecord.setRelDateFormat(dto.getReleaseDate());
			obxRecord.setRelTimeFormat(dto.getReleaseTime());
			obxRecord.setPerformingLabId(dto.getPerformingLabId());
			obxRecord.setOrderMethod(dto.getOrderMethod());
			obxRecord.setOrderNumber(dto.getOrderNumber());
			//obxRecord.setRrTestCode(dto.getNewHorizonCode());
			//obxRecord.setOldTestCode(dto.getTestCode());
			obxRecord.setSourceOfComment(dto.getSourceOfComment());
			obxRecord.setSensitivityFlag(dto.getAbnormalFlag());
			obxRecord.setAntibioticTextualNumResult(dto.getTextualResultFull());
			//obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setLoincCode(dto.getLoincCode());
			obxRecord.setLoincName(dto.getLoincName());
			obxRecord.setSequenceNo(dto.getSequenceNo());
			obxRecord.setMicroOrganismName(dto.getMicroOrganismName());
			obxRecord.setDeviceName(dto.getDeviceName());
			
			obxRecord.setCollectionDateTime(dto.getCollectionDateTime());
			obxRecord.setReleaseDateTime(dto.getReleaseDateTime());
		}
		return obxRecord;
	}
	
	public static NTERecord stateResultDtoToNTERecord(StateResultDto dto){
		NTERecord nteRecord = null;
		if(dto != null){
			nteRecord = new NTERecord();
			nteRecord.setNteCompTestCode(dto.getOrderTestCode());
			nteRecord.setNteResultName(dto.getResultTestName());
			nteRecord.setTestNteComment(dto.getResultComments());
			nteRecord.setNteTestCode(dto.getResultTestCode());
			nteRecord.setSequenceNo(dto.getSequenceNo());
		}
		return nteRecord;
	}
}
