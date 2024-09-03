package com.spectra.result.transporter.context.strategy.hl7;

import com.spectra.result.transporter.businessobject.spring.hl7.HL7WriterBo;
import com.spectra.result.transporter.context.strategy.ConversionStrategy;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.utils.convert.ConversionUtil;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class HL7ConversionStrategy implements ConversionStrategy<String> {
	//private Logger log = Logger.getLogger(HL7ConversionStrategy.class);
	
	protected HL7WriterBo hl7WriterBo;
	
	public void setWriterBo(HL7WriterBo hl7WriterBo){
		this.hl7WriterBo = hl7WriterBo;
	}
	
	@Override
	public String convert(List<RepositoryResultDto> dtoList) {
		String hl7Str = null;
		if(dtoList != null){
			if(this.hl7WriterBo != null){
				Map<String, List<PatientRecord>> listMap = ConversionUtil.toPatientRecordListMapByReqNo(dtoList);
				log.debug("convert(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
				try{
					hl7Str = this.hl7WriterBo.toHL7String(listMap);
				}catch(BusinessException be){
					log.error(String.valueOf(be));
					be.printStackTrace();
				}
			}
		}
		return hl7Str;
	}
/*
	public Map<String, List<PatientRecord>> toPatientRecordListMap(List<RepositoryResultDto> dtoList){
		Map<String, List<PatientRecord>> listMap = null;
		if(dtoList != null){
			Map<String, List<RepositoryResultDto>> dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
			for(RepositoryResultDto dto : dtoList){
				String reqNo = dto.getOrderNumber();
				if(reqNo != null){
					if(dtoListMap.containsKey(reqNo)){
						List<RepositoryResultDto> reqListDto = dtoListMap.get(reqNo);
						reqListDto.add(dto);
					}else{
						List<RepositoryResultDto> reqDtoList = new ArrayList<RepositoryResultDto>();
						reqDtoList.add(dto);
						dtoListMap.put(reqNo, reqDtoList);
					}
				}
			}//for
			
			listMap = new HashMap<String, List<PatientRecord>>();
			
			Set<Map.Entry<String, List<RepositoryResultDto>>> entrySet = dtoListMap.entrySet();
			for(Map.Entry<String, List<RepositoryResultDto>> entry : entrySet){
				List<RepositoryResultDto> resultDtoList = entry.getValue();
				//List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
				if(resultDtoList != null){
					RepositoryResultDto dto = resultDtoList.get(0);
					PatientRecord patientRecord = ConversionUtil.repositoryResultDtoToPatientRecord(dto);
					if(patientRecord != null){
						List<OBXRecord> obxList = new ArrayList<OBXRecord>();
						List<NTERecord> nteList = new ArrayList<NTERecord>();
						for(RepositoryResultDto resultDto : resultDtoList){
							OBXRecord obxRecord = ConversionUtil.repositoryResultDtoToOBXRecord(resultDto);
							obxList.add(obxRecord);
							
							NTERecord nteRecord = ConversionUtil.repositoryResultDtoToNTERecord(resultDto);
							nteList.add(nteRecord);
						}
						
						patientRecord.setObxList(obxList);
						patientRecord.setNteList(nteList);
						
						String facilityId = dto.getFacilityId();
						if(facilityId != null){
							if(listMap.containsKey(facilityId)){
								List<PatientRecord> patientRecList = listMap.get(facilityId);
								if(patientRecList != null){
									patientRecList.add(patientRecord);
								}
							}else{
								List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
								patientRecList.add(patientRecord);
								listMap.put(facilityId, patientRecList);
							}
						}
					}
				}
			}//for
		}
		return listMap;
	}
*/	
/*	
	PatientRecord repositoryResultDtoToPatientRecord(RepositoryResultDto dto){
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
	
	OBXRecord repositoryResultDtoToOBXRecord(RepositoryResultDto dto){
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
	
	NTERecord repositoryResultDtoToNTERecord(RepositoryResultDto dto){
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
*/	
}
