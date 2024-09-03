package com.spectra.result.transporter.context.strategy.hl7;

import java.util.List;
import java.util.Map;

import com.spectra.result.transporter.dto.hl7.state.NTERecord;
import com.spectra.result.transporter.dto.hl7.state.OBXRecord;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;

public abstract class AbstractHL7Conversion {
	protected abstract Map<String, List<PatientRecord>> toPatientRecordListMap(List<RepositoryResultDto> dtoList);
	
	protected PatientRecord repositoryResultDtoToPatientRecord(RepositoryResultDto dto){
		PatientRecord patientRecord = null;
		if(dto != null){
			patientRecord = new PatientRecord();
			patientRecord.setMhsOrderingFacId(dto.getFacilityId());
			patientRecord.setCid(dto.getCid());
			patientRecord.setAccessionNo(dto.getAccessionNo());
			//patientRecord.setAltPatientId(dto.getMrn());
			patientRecord.setAltPatientId(dto.getPatientId());
			patientRecord.setPatientName(dto.getPatientName());
			patientRecord.setDob(dto.getDateOfBirth());
			patientRecord.setGender(dto.getGender());
			patientRecord.setPatientSsn(dto.getPatientSsn());
			patientRecord.setProviderId(dto.getNpi());
			patientRecord.setOrderingPhyName(dto.getOrderingPhysicianName());
			patientRecord.setReportNteComment(dto.getReportNotes());
			patientRecord.setSpecimenRecDateformat(dto.getSpecimenReceiveDate());
			patientRecord.setCollectionDateformat(dto.getCollectionDate());
			patientRecord.setCollectionTimeformat(dto.getCollectionTime());
			patientRecord.setDrawFreq(dto.getDrawFreq());
			patientRecord.setResRptStatusChngDtTimeformat(dto.getResRprtStatusChngDtTime());
			patientRecord.setRequisitionStatus(dto.getOrderStatus());
			patientRecord.setCompoundTestCode(dto.getCompoundTestCode());
			patientRecord.setSubTestCode(dto.getTestCode());
			patientRecord.setSpecimenSource(dto.getSpecimenSource());
			patientRecord.setOrderNumber(dto.getOrderNumber());
			
			patientRecord.setFacilityName(dto.getFacilityName());
			patientRecord.setFacilityAddress1(dto.getFacilityAddress1());
			patientRecord.setFacilityAddress2(dto.getFacilityAddress2());
			patientRecord.setFacilityCity(dto.getFacilityCity());
			patientRecord.setFacilityState(dto.getFacilityState());
			patientRecord.setFacilityZip(dto.getFacilityZip());
			patientRecord.setFacilityPhone(dto.getFacilityPhone());
			
			patientRecord.setPatientAddress1(dto.getPatientAccountAddress1());
			patientRecord.setPatientAddress2(dto.getPatientAccountAddress2());
			patientRecord.setPatientCity(dto.getPatientAccountCity());
			patientRecord.setPatientState(dto.getPatientAccountState());
			patientRecord.setPatientZip(dto.getPatientAccountZip());
			patientRecord.setPatientPhone(dto.getPatientHomePhone());
			patientRecord.setMrnId(dto.getMrn());
			
			patientRecord.setFacilityId(dto.getFacilityId());
		}
		return patientRecord;
	}
	
	protected OBXRecord repositoryResultDtoToOBXRecord(RepositoryResultDto dto){
		OBXRecord obxRecord = null;
		if(dto != null){
			obxRecord = new OBXRecord();
			obxRecord.setAccessionNo(dto.getAccessionNo());
			obxRecord.setCompoundTestCode(dto.getCompoundTestCode());
			obxRecord.setSubTestCode(dto.getTestCode());
			obxRecord.setSeqResultName(dto.getResultName());
			obxRecord.setSeqTestName(dto.getTestName());
			//obxRecord.setOrderStatus(dto.getResultStatus());
			obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setTextualNumResult(dto.getTextualNumericResult());
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
			obxRecord.setAntibioticTextualNumResult(dto.getTextualNumericResult());
			//obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setLoincCode(dto.getLoincCode());
			obxRecord.setLoincName(dto.getLoincName());
		}
		return obxRecord;
	}
	
	protected NTERecord repositoryResultDtoToNTERecord(RepositoryResultDto dto){
		NTERecord nteRecord = null;
		if(dto != null){
			nteRecord = new NTERecord();
			nteRecord.setNteCompTestCode(dto.getCompoundTestCode());
			nteRecord.setNteResultName(dto.getResultName());
			nteRecord.setTestNteComment(dto.getResultComments());
			nteRecord.setNteTestCode(dto.getTestCode());
		}
		return nteRecord;
	}	
}
