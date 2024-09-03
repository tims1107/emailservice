package com.spectra.asr.hl7.creator;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
@Slf4j
public abstract class ORAbstractHL7Creator implements HL7Creator {
	//private Logger log = Logger.getLogger(ORAbstractHL7Creator.class);
	
	@Override
	public HL7Dto toHL7Dto(Map<String, List<PatientRecord>> listMap, GeneratorDto generatorDto) throws BusinessException {
		log.warn("toHL7Dto(): generatorDto.getEastWestFlag(): " + (generatorDto.getEastWestFlag() == null ? "NULL" : generatorDto.getEastWestFlag()));
		HL7Dto hl7Dto = null;

		if((listMap != null) && (generatorDto != null)){
			String eastWestFlag = generatorDto.getEastWestFlag();
			if((eastWestFlag == null) || (eastWestFlag.trim().length() == 0)){
				eastWestFlag = "East";
			}
			
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			
			AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());

			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				hl7Dto = new HL7Dto();

				DateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date datetime = new Date();

				DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
				Date date = new Date();
				
				DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
				Date date2 = new Date();
				
				//header builder
				String mshFileSendingFacility = null;
				String mshSendingApplication = null;
				String mshSendingFacilityId = null;
				
				String infoSpectraName = null;
				String infoSpectraAddress = null;
				String infoSpectraAddress2 = null;
				String infoSpectraCity = null;
				String infoSpectraState = null;
				String infoSpectraZip = null;
				String infoSpectraPhone = null;
				
				//sending from rockleigh set sending facility to east
				//eastWestFlag = "East";

				Map<String, Object> paramMap = new HashMap<String, Object>();
				if(eastWestFlag.equalsIgnoreCase("West")){
					paramMap.put("labPk", new Integer(6));
				}else if (eastWestFlag.equalsIgnoreCase("East")) {
					paramMap.put("labPk", new Integer(5));
				} else {
					paramMap.put("labPk", new Integer(7));
				}
				List<LabDto> labDtoList = asrDemographicService.getLab(paramMap);
				log.warn("toWorkbook(): labDtoList: " + (labDtoList == null ? "NULL" : labDtoList.toString()));
				LabDto labDto = labDtoList.get(0);
				String medicalDirector = labDto.getMedicalDirector();
				//if(eastWestFlag.equalsIgnoreCase("East")){
					mshFileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
					mshSendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
					mshSendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
				//}else{
//					mshFileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
//					mshSendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
//					mshSendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
				//}
				
				if(eastWestFlag.equalsIgnoreCase("East")){
					infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
					infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
					infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
					infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
					String[] addr2Array = infoSpectraAddress2.split("\\s");
					if(addr2Array.length == 3){
						infoSpectraCity = addr2Array[0].substring(0, addr2Array[0].indexOf(","));
						infoSpectraState = addr2Array[1];
						infoSpectraZip = addr2Array[2];
					}
				}else if(eastWestFlag.equalsIgnoreCase("West")){
					infoSpectraName = ApplicationProperties.getProperty("spectra.west.info.name");
					infoSpectraAddress = ApplicationProperties.getProperty("spectra.west.info.address");
					infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.west.info.address2");
					infoSpectraPhone = ApplicationProperties.getProperty("spectra.west.info.phone");
					String[] addr2Array = infoSpectraAddress2.split("\\s");
					if(addr2Array.length == 3){
						infoSpectraCity = addr2Array[0].substring(0, addr2Array[0].indexOf(","));
						infoSpectraState = addr2Array[1];
						infoSpectraZip = addr2Array[2];
					}
				} else if(eastWestFlag.equalsIgnoreCase("South")){
					infoSpectraName = ApplicationProperties.getProperty("spectra.south.info.name");
					infoSpectraAddress = ApplicationProperties.getProperty("spectra.south.info.address");
					infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.south.info.address2");
					infoSpectraPhone = ApplicationProperties.getProperty("spectra.south.info.phone");
					String[] addr2Array = infoSpectraAddress2.split("\\s");
					if(addr2Array.length == 3){
						infoSpectraCity = addr2Array[0].substring(0, addr2Array[0].indexOf(","));
						infoSpectraState = addr2Array[1];
						infoSpectraZip = addr2Array[2];
					}
				}
				
				String fileSendingFacility = null;
				String fileReceivingApplication = null;
				String fileReceivingFacility = null;
				String sendingApplication = null;
				String sendingFacilityId = null;
				String sendingFacilityIdType = null;
				String msgProfileId = null;

/*				
				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				generatorFieldDto.setGeneratorFieldType("HL7");
				generatorFieldDto.setStatus("active");

				generatorFieldDto.setGeneratorField("fhs.5");
				List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				if((fieldDtoList != null) && (fieldDtoList.size() > 0)){
					fileReceivingApplication = fieldDtoList.get(0).getGeneratorFieldValue();
					fileReceivingApplication = (fileReceivingApplication == null ? "" : fileReceivingApplication);
				}else{
					fileReceivingApplication = "";
				}

				generatorFieldDto.setGeneratorField("fhs.6");
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				if((fieldDtoList != null) && (fieldDtoList.size() > 0)){
					fileReceivingFacility = fieldDtoList.get(0).getGeneratorFieldValue();
					fileReceivingFacility = (fileReceivingFacility == null ? "" : fileReceivingFacility);
				}else{
					fileReceivingFacility = "";
				}
				generatorFieldDto.setGeneratorField("msh.4.3");
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				sendingFacilityIdType = fieldDtoList.get(0).getGeneratorFieldValue();				
				
*/
				
				//state master
				StateMasterDto stateMasterDto = new StateMasterDto();
				stateMasterDto.setStateMasterPk(generatorDto.getStateFk());
				List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
				if(stdList.size() >= 1){
					stateMasterDto = stdList.get(0);
				}
				fileReceivingApplication = stateMasterDto.getReceivingApplication();
				fileReceivingApplication = (fileReceivingApplication == null ? "" : fileReceivingApplication);
				fileReceivingFacility = stateMasterDto.getReceivingFacility();
				fileReceivingFacility = (fileReceivingFacility == null ? "" : fileReceivingFacility);
				msgProfileId = stateMasterDto.getMsgProfileId();
				log.warn("toHL7Dto(): msgProfileId: " + (msgProfileId == null ? "NULL" : msgProfileId));
				msgProfileId = (msgProfileId == null ? "" : msgProfileId);
				
				
				//generatorFieldDto.setGeneratorField("msh.4.3");
				//fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				//sendingFacilityIdType = fieldDtoList.get(0).getGeneratorFieldValue();
				
//				if(eastWestFlag.equalsIgnoreCase("West")) {
//					sendingFacilityIdType = ApplicationProperties.getProperty("spectra.west.msh.4.3");
//				} else if (eastWestFlag.equalsIgnoreCase("South")) {
//
//				}else{
					sendingFacilityIdType = ApplicationProperties.getProperty("spectra.east.msh.4.3");
//				}
				
				StringBuilder fhsBuilder = new StringBuilder();
				fhsBuilder.append("FHS|^~\\&|ASR|").append(mshFileSendingFacility).append("^").append(mshSendingFacilityId).append("^").append(sendingFacilityIdType).append("|");
				fhsBuilder.append(fileReceivingApplication).append("|");
				fhsBuilder.append(fileReceivingFacility).append("\n");

				StringBuilder bhsBuilder = new StringBuilder();
				bhsBuilder.append("BHS|^~\\&|ASR|").append(mshFileSendingFacility).append("^").append(mshSendingFacilityId).append("^").append(sendingFacilityIdType).append("|");
				bhsBuilder.append(fileReceivingApplication).append("|");
				bhsBuilder.append(fileReceivingFacility).append("\n");		
				//end header builder
				
				String hapiCustomPackage = generatorDto.getHl7Package();
				
				Set<String> keySet = listMap.keySet();
				StringBuilder hl7Builder = new StringBuilder();

				int patientRecordListSize = 0;

				DateFormat df = new SimpleDateFormat("yyMMdd");
				String dtStr = df.format(new Date());

				log.warn("toHL7Dto(): keySet: size: " + (keySet == null ? "NULL" : String.valueOf(keySet.size())));
				log.warn("toHL7Dto(): keySet: size: " + (keySet == null ? "NULL" : keySet.toString()));
				
				StringBuilder oruBuilder = new StringBuilder();
				
				Map<String, Object> pMap = new HashMap<String, Object>();
				pMap.put("mshSendingApplication", mshSendingApplication);
				pMap.put("mshFileSendingFacility", mshFileSendingFacility);
				pMap.put("mshSendingFacilityId", mshSendingFacilityId);
				pMap.put("sendingFacilityIdType", sendingFacilityIdType);
				pMap.put("dateFormat", dateFormat);
				pMap.put("dateFormat2", dateFormat2);
				pMap.put("infoSpectraAddress", infoSpectraAddress);
				pMap.put("infoSpectraAddress2", infoSpectraAddress2);
				pMap.put("infoSpectraCity", infoSpectraCity);
				pMap.put("infoSpectraState", infoSpectraState);
				pMap.put("infoSpectraZip", infoSpectraZip);
				pMap.put("medicalDirector", medicalDirector);
				pMap.put("hapiCustomPackage", hapiCustomPackage);
				
				pMap.put("fileReceivingApplication", fileReceivingApplication);
				pMap.put("fileReceivingFacility", fileReceivingFacility);
				pMap.put("msgProfileId", msgProfileId);
				
				
				for(String key : keySet){
					List<PatientRecord> patientRecordList = listMap.get(key);
					log.warn("toHL7Dto(): patientRecordList: size: " + (patientRecordList == null ? "NULL" : String.valueOf(patientRecordList.size())));
					if(patientRecordList != null){
						patientRecordListSize += patientRecordList.size();

						for(PatientRecord patientRecord : patientRecordList){
							AbstractMessage hl7Message = null;
							try{
								hl7Message = this.getMessage(patientRecord, pMap);
								String pipedMessage = this.getPipedHL7Message(hl7Message, pMap);
								oruBuilder.append(pipedMessage);
							}catch(HL7Exception hl7e){
								log.error(hl7e.getMessage());
								hl7e.printStackTrace();
							}
						}//for
					}//if
				}//for
				
				StringBuilder btsBuilder = new StringBuilder();
				btsBuilder.append("BTS|").append(String.valueOf(patientRecordListSize)).append("|").append("\n");

				StringBuilder ftsBuilder = new StringBuilder();
				ftsBuilder.append("FTS|1|").append("\n");

				if((oruBuilder != null) && (oruBuilder.length() > 0)){
					hl7Builder.append(fhsBuilder.toString());
					hl7Builder.append(bhsBuilder.toString());
					hl7Builder.append(oruBuilder.toString());
					hl7Builder.append(btsBuilder.toString());
					hl7Builder.append(ftsBuilder.toString());
				}

				log.warn("toHL7Dto(): hl7Builder: " + (hl7Builder == null ? "NULL" : hl7Builder.toString()));

				hl7Dto.setHl7String(hl7Builder.toString());
				hl7Dto.setState(generatorDto.getStateTarget());
				hl7Dto.setCounty(generatorDto.getCountyTarget());
				hl7Dto.setCreatedBy(generatorDto.getCreatedBy());
				hl7Dto.setLastUpdatedBy(generatorDto.getLastUpdatedBy());
				Timestamp ts = new Timestamp(System.currentTimeMillis());
				hl7Dto.setCreatedDate(ts);
				hl7Dto.setLastUpdatedDate(ts);
			}//if
		}//if
		return hl7Dto;
	}
/*
	@Override
	public AbstractMessage getMessage(PatientRecord patientRecord) throws HL7Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPipedHL7Message(AbstractMessage hl7Message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getXmlHL7Message(AbstractMessage hl7Message) {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
