package com.spectra.asr.businessobject.hl7.state.nj;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.hl7.creator.HL7Creator;
import com.spectra.asr.hl7.creator.factory.HL7CreatorFactory;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
//import com.spectra.asr.dao.ora.hub.AsrDao;

@Slf4j
public class NJHL7BoImpl implements NJHL7Bo {
	//private Logger log = Logger.getLogger(NJHL7BoImpl.class);
	
	@Override
	public HL7Dto toHL7Dto(Map<String, List<PatientRecord>> listMap, GeneratorDto generatorDto) throws BusinessException {
		log.warn("toHL7Dto(): generatorDto.getEastWestFlag(): " + (generatorDto.getEastWestFlag() == null ? "NULL" : generatorDto.getEastWestFlag()));
		//log.warn("toHL7Dto(): listMap: " + (listMap == null ? "NULL" : listMap.toString()));
		HL7Dto hl7Dto = null;
		
		if((listMap != null) && (generatorDto != null)){
			HL7Creator hl7Creator = (HL7Creator)HL7CreatorFactory.getCreatorImpl("NJHL7Creator");
			if(hl7Creator != null){
				hl7Dto = hl7Creator.toHL7Dto(listMap, generatorDto);
			}
		}

/*		
		if((listMap != null) && (generatorDto != null)){
			String eastWestFlag = generatorDto.getEastWestFlag();
			if((eastWestFlag == null) || (eastWestFlag.trim().length() == 0)){
				eastWestFlag = "East";
			}
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
				
				//sending from rockleigh set sending facility to east
				//eastWestFlag = "East";
				
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if(eastWestFlag.equalsIgnoreCase("West")){
					paramMap.put("labPk", new Integer(6));
				}else{
					paramMap.put("labPk", new Integer(5));
				}
				List<LabDto> labDtoList = asrDemographicService.getLab(paramMap);
				log.warn("toWorkbook(): labDtoList: " + (labDtoList == null ? "NULL" : labDtoList.toString()));
				LabDto labDto = labDtoList.get(0);
				String medicalDirector = labDto.getMedicalDirector();
				if(eastWestFlag.equalsIgnoreCase("East")){
					mshFileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
					mshSendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
					mshSendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
				}else{
					mshFileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
					mshSendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
					mshSendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
				}
				//String mshFileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
				//String mshSendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
				//String mshSendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
				
				String fileSendingFacility = null;
				String fileReceivingApplication = null;
				String fileReceivingFacility = null;
				String sendingApplication = null;
				String sendingFacilityId = null;
				String sendingFacilityIdType = null;

				GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
				generatorFieldDto.setGeneratorFk(generatorDto.getGeneratorPk());
				generatorFieldDto.setGeneratorFieldType("HL7");
				generatorFieldDto.setStatus("active");
				
				generatorFieldDto.setGeneratorField("fhs.5");
				List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				fileReceivingApplication = fieldDtoList.get(0).getGeneratorFieldValue();
				
				generatorFieldDto.setGeneratorField("fhs.6");
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				fileReceivingFacility = fieldDtoList.get(0).getGeneratorFieldValue();
				
				generatorFieldDto.setGeneratorField("msh.4.3");
				fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
				sendingFacilityIdType = fieldDtoList.get(0).getGeneratorFieldValue();				
			
				log.warn("toHL7Dto(): fileSendingFacility: " + (fileSendingFacility == null ? "NULL" : fileSendingFacility));
				log.warn("toHL7Dto(): fileReceivingApplication: " + (fileReceivingApplication == null ? "NULL" : fileReceivingApplication));
				log.warn("toHL7Dto(): fileReceivingFacility: " + (fileReceivingFacility == null ? "NULL" : fileReceivingFacility));
				log.warn("toHL7Dto(): sendingApplication: " + (sendingApplication == null ? "NULL" : sendingApplication));
				log.warn("toHL7Dto(): sendingFacilityId: " + (sendingFacilityId == null ? "NULL" : sendingFacilityId));
				log.warn("toHL7Dto(): sendingFacilityIdType: " + (sendingFacilityIdType == null ? "NULL" : sendingFacilityIdType));
				
				StringBuilder fhsBuilder = new StringBuilder();
				//fhsBuilder.append("FHS|^~\\&|HL7|").append(fileSendingFacility).append("^").append(sendingFacilityId).append("^").append(sendingFacilityIdType).append("|");
				fhsBuilder.append("FHS|^~\\&|HL7|").append(mshFileSendingFacility).append("^").append(mshSendingFacilityId).append("^").append(sendingFacilityIdType).append("|");
				fhsBuilder.append(fileReceivingApplication).append("|");
				fhsBuilder.append(fileReceivingFacility).append("\n");
				//hl7Builder.append(fhsBuilder.toString());

				
				StringBuilder bhsBuilder = new StringBuilder();
				//bhsBuilder.append("BHS|^~\\&||").append(fileSendingFacility).append("^").append(sendingFacilityId).append("^").append(sendingFacilityIdType).append("|");
				bhsBuilder.append("BHS|^~\\&||").append(mshFileSendingFacility).append("^").append(mshSendingFacilityId).append("^").append(sendingFacilityIdType).append("|");
				bhsBuilder.append(fileReceivingApplication).append("|");
				bhsBuilder.append(fileReceivingFacility).append("\n");				
				
				//end header builder				
				
				String hapiCustomPackage = generatorDto.getHl7Package();
				
				PrintWriter pw = null;
				StringWriter sw = null;
				
				Set<String> keySet = listMap.keySet();
				StringBuilder hl7Builder = new StringBuilder();
				
				int patientRecordListSize = 0;
				
				DateFormat df = new SimpleDateFormat("yyMMdd");
				String dtStr = df.format(new Date());
				
				log.warn("toHL7Dto(): keySet: size: " + (keySet == null ? "NULL" : String.valueOf(keySet.size())));
				log.warn("toHL7Dto(): keySet: size: " + (keySet == null ? "NULL" : keySet.toString()));
				
				StringBuilder zruBuilder = new StringBuilder();
				
				for(String key : keySet){
					
					List<PatientRecord> patientRecordList = listMap.get(key);
					log.warn("toHL7Dto(): patientRecordList: size: " + (patientRecordList == null ? "NULL" : String.valueOf(patientRecordList.size())));
					if(patientRecordList != null){
						patientRecordListSize += patientRecordList.size();
						
						for(PatientRecord patientRecord : patientRecordList){
							String performingLabId = patientRecord.getPerformingLabId();
							Integer labFk = patientRecord.getLabFk();
							if((labFk.intValue() == 5) && (performingLabId.indexOf("SW") != -1)){
								//sendout west address
								fileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
								sendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
								sendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
							}else if((labFk.intValue() == 5) && (performingLabId.indexOf("SE") != -1)){
								//east address
								fileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
								sendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
								sendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
							}else if((labFk.intValue() == 5) && (performingLabId.indexOf("OUTSEND") != -1)){
								//east address
								fileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
								sendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
								sendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
							}
							
							if((labFk.intValue() == 6) && (performingLabId.indexOf("SE") != -1)){
								//sendout east address
								fileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
								sendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
								sendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
							}else if((labFk.intValue() == 6) && (performingLabId.indexOf("SW") != -1)){
								//west address
								fileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
								sendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
								sendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
							}else if((labFk.intValue() == 6) && (performingLabId.indexOf("OUTSEND") != -1)){
								//west address
								fileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
								sendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
								sendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
							}
							
							
							ZRU_R01 zru = new ZRU_R01();
							try{
								zru.initQuickstart("ORU", "R01", "P");
								
								MSH msh = zru.getMSH();
								//msh.getSendingApplication().getNamespaceID().setValue("EAST");
								//msh.getSendingFacility().getNamespaceID().setValue("Spectra East");
								//msh.getSendingFacility().getUniversalID().setValue("31D0961672");
								//msh.getSendingFacility().getUniversalIDType().setValue("CLIA");
								
								//msh.getSendingApplication().getNamespaceID().setValue(sendingApplication); //EAST
								//msh.getSendingFacility().getNamespaceID().setValue(fileSendingFacility); //Spectra East
								//msh.getSendingFacility().getUniversalID().setValue(sendingFacilityId); //31D0961672
								
								msh.getSendingApplication().getNamespaceID().setValue(mshSendingApplication); //EAST
								msh.getSendingFacility().getNamespaceID().setValue(mshFileSendingFacility); //Spectra East
								msh.getSendingFacility().getUniversalID().setValue(mshSendingFacilityId); //31D0961672
								
								msh.getSendingFacility().getUniversalIDType().setValue(sendingFacilityIdType); //CLIA							
								msh.getReceivingFacility().getNamespaceID().setValue(patientRecord.getCid()); //cid
								msh.getReceivingFacility().getUniversalID().setValue(patientRecord.getMhsOrderingFacId()); //Mhs_ordering_fac_id
								//DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
								String timeOfAnEvent = dateFormat.format(new Date());
								msh.getDateTimeOfMessage().getTimeOfAnEvent().setValue(timeOfAnEvent);
								//msh.getMessageControlID().setValue("1");
								
								msh.getMessageControlID().setValue(patientRecord.getAccessionNo());
								
								//StringBuilder dtNanoBuilder = new StringBuilder();
								//dtNanoBuilder.append(dtStr).append(String.valueOf(System.nanoTime()));
								//msh.getMessageControlID().setValue(dtNanoBuilder.toString());
								
								
								ZRU_R01_RESPONSE response = zru.getRESPONSE();
								//log.warn("toHL7Dto(): response: " + (response == null ? "NULL" : response.getName()));
								ORU_R01_PATIENT patient = response.getPATIENT();
								//log.warn("toHL7Dto(): patient: " + (patient == null ? "NULL" : patient.toString()));
								//log.warn("toHL7Dto(): patient: " + (patient == null ? "NULL" : patient.getName()));
								PID pid = patient.getPID();
								//log.warn("toHL7Dto(): pid: " + (pid == null ? "NULL" : pid.getName()));

								pid.getSetIDPatientID().setValue("1");
								pid.getPatientIDExternalID().getID().setValue(patientRecord.getMrnId()); //Mrn_id
								pid.getPatientIDInternalID(0).getID().setValue(patientRecord.getAltPatientId()); //patientrecord.getAlt_patient_id
								//pid.getAlternatePatientID().getID().setValue("8000075633"); //patientrecord.getAlt_patient_id
								String patientName = patientRecord.getPatientName();
								log.warn("toHL7Dto(): patientName: " + (patientName == null ? "NULL" : patientName));
								//if(patientName.indexOf("^") != -1){
								if(StringUtils.contains(patientName, "^")){
									//String[] nameArray = patientName.split("^");
									String[] nameArray = StringUtils.split(patientName, "\\^");
									if((nameArray != null) && (nameArray.length >= 2)){
										log.warn("toHL7Dto(): nameArray[0]: " + (nameArray[0] == null ? "NULL" : nameArray[0]));
										log.warn("toHL7Dto(): nameArray[1]: " + (nameArray[1] == null ? "NULL" : nameArray[1]));
										pid.getPatientName(0).getFamilyName().setValue(nameArray[0]); //last name
										pid.getPatientName(0).getGivenName().setValue(nameArray[1]); // first name
									}
								}else{
									pid.getPatientName(0).getFamilyName().setValue(patientName); //last name
								}
								pid.getMotherSMaidenName().getFamilyName().setValue(patientRecord.getCid()); //cid
								pid.getDateOfBirth().getTimeOfAnEvent().setValue(patientRecord.getDateOfBirth()); //patientrecord.getDate_of_birth
								pid.getSex().setValue(patientRecord.getGender()); //patientrecord.getGender
								pid.getPatientAlias(0).getFamilyName().setValue("T");
								
								String race = patientRecord.getPatientRace();
								if((race == null) || (race.trim().length() == 0)){
									race = "U";
								}
								pid.getRace().setValue(race); //patientrecord.getPatientRace
								//pid.getRace().setValue(patientRecord.getPatientRace()); //patientrecord.getPatientRace
								
								pid.getPatientAddress(0).getStreetAddress().setValue(patientRecord.getPatientAddress1()); //patientrecord.getPatient_address1
								String patientAddress2 = patientRecord.getPatientAddress2();
								if((patientAddress2 != null) && (patientAddress2.length() > 0)){
									pid.getPatientAddress(1).getStreetAddress().setValue(patientAddress2);
								}
								//pid.getPatientAddress(0).getXad1_StreetAddress().setValue("");
								pid.getPatientAddress(0).getCity().setValue(patientRecord.getPatientCity()); //patientrecord.getPatient_city
								pid.getPatientAddress(0).getStateOrProvince().setValue(patientRecord.getPatientState()); //patientrecord.getPatient_state
								pid.getPatientAddress(0).getZipOrPostalCode().setValue(patientRecord.getPatientZip()); //patientrecord.getPatient_zip
								//pid.getPhoneNumberHome(0).getPhoneNumber().setValue("201-767-7070"); //patientrecord.getPatient_phone
								pid.getPhoneNumberHome(0).getAreaCityCode().setValue(patientRecord.getPatientAreaCode());
								pid.getPhoneNumberHome(0).getPhoneNumber().setValue(patientRecord.getPatientPhone());
								//pid.getPhoneNumberHome(0).getAnyText().setValue("201-767-7070");
								
								//if(patientRecord.getPatientState() != null){
								//	if(!HL7WriterConstants.STATE_SSN_EXCLUDE_MAP.containsKey(patientRecord.getPatientState().trim())){
								//		pid.getSSNNumberPatient().setValue(patientRecord.getPatientSsn()); //patientrecord.getPatient_ssn
								//	}
								//}
								
								
								String ethnicGroup = patientRecord.getEthnicGroup();
								if((ethnicGroup == null) || (ethnicGroup.trim().length() == 0)){
									ethnicGroup = "U";
								}
								pid.getEthnicGroup().setValue(ethnicGroup); //patientrecord.getEthnicGroup
								//pid.getEthnicGroup().setValue(patientRecord.getEthnicGroup()); //patientrecord.getEthnicGroup
								
								OBXRecord obxRec = patientRecord.getObxList().get(0);
								
								ZRU_R01_ORDER_OBSERVATION orderObservation = response.getORDER_OBSERVATION();
								log.warn("toHL7Dto(): orderObservation: " + (orderObservation == null ? "NULL" : orderObservation.getName()));
								OBR obr = orderObservation.getOBR();
								log.warn("toHL7Dto(): obr: " + (obr == null ? "NULL" : obr.getName()));
								obr.getSetIDObservationRequest().setValue("1");
								obr.getPlacerOrderNumber(0).getEntityIdentifier().setValue(patientRecord.getOrderNumber()); //patientrecord.getOrder_number
								//obr.getFillerOrderNumber().getNamespaceID().setValue("2376HBW"); //patientrecord.getOrder_number
								obr.getFillerOrderNumber().getEntityIdentifier().setValue(patientRecord.getOrderNumber()); //patientrecord.getOrder_number
								obr.getUniversalServiceIdentifier().getIdentifier().setValue(obxRec.getCompoundTestCode()); //obxrecord.getCompound_test_code
								obr.getUniversalServiceIdentifier().getText().setValue(obxRec.getSeqTestName()); //obxrecord.getSeq_test_name
								obr.getUniversalServiceIdentifier().getNameOfCodingSystem().setValue("L");
								obr.getRequestedDateTime().getTimeOfAnEvent().setValue(patientRecord.getSpecimenReceiveDate()); //patientrecord.getSpecimen_receive_date
								obr.getObservationDateTime().getTimeOfAnEvent().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
								obr.getSpecimenActionCode().setValue("G");
								obr.getSpecimenReceivedDateTime().getTimeOfAnEvent().setValue(patientRecord.getSpecimenReceiveDate()); //patientrecord.getSpecimen_receive_date
								obr.getSpecimenSource().getSpecimenSourceNameOrCode().getIdentifier().setValue(patientRecord.getSpecimenSource()); //patientrecord.getSpecimen_source
								obr.getOrderingProvider(0).getIDNumber().setValue(patientRecord.getProviderId()); //patientrecord.getProvider_id
								String orderingPhyName = patientRecord.getOrderingPhyName();
								log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
								if(StringUtils.contains(orderingPhyName, "^")){
									//String[] nameArray = orderingPhyName.split("^");
									String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
									if((phyNameArray != null) && (phyNameArray.length >= 2)){
										log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
										log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
										obr.getOrderingProvider(0).getFamilyName().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
										obr.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name									
									}
								}else{
									obr.getOrderingProvider(0).getFamilyName().setValue(orderingPhyName); //last name
								}							
								obr.getOrderCallbackPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code
								obr.getOrderCallbackPhoneNumber(0).getPhoneNumber().setValue(patientRecord.getFacilityPhoneNumber()); //patientrecord.getFacility_phone_number
								obr.getResultsRptStatusChngDateTime().getTimeOfAnEvent().setValue(patientRecord.getResRprtStatusChngDtTime()); //patientrecord.getRes_rprt_status_chng_dt_time
								obr.getResultStatus().setValue(patientRecord.getRequisitionStatus()); //patientrecord.getRequisition_status							
								
								
								ZLR zlr = orderObservation.getZLR();
								log.warn("toHL7Dto(): zlr: " + (zlr == null ? "NULL" : zlr.getName()));
								zlr.getOrderingProviderAddress(0).getStreetAddress().setValue(patientRecord.getFacilityAddress1()); //patientrecord.getFacility_address1
								zlr.getOrderingProviderAddress(0).getOtherDesignation().setValue(patientRecord.getFacilityAddress2());
								zlr.getOrderingProviderAddress(0).getCity().setValue(patientRecord.getFacilityCity());
								zlr.getOrderingProviderAddress(0).getStateOrProvince().setValue(patientRecord.getFacilityState());
								zlr.getOrderingProviderAddress(0).getZipOrPostalCode().setValue(patientRecord.getFacilityZip());
								zlr.getOrderingFacility(0).getOrganizationName().setValue(patientRecord.getFacilityName());
								zlr.getOrderingFacilityAddress(0).getStreetAddress().setValue(patientRecord.getFacilityAddress1());
								zlr.getOrderingFacilityAddress(0).getOtherDesignation().setValue(patientRecord.getFacilityAddress2());
								zlr.getOrderingFacilityAddress(0).getCity().setValue(patientRecord.getFacilityCity());
								zlr.getOrderingFacilityAddress(0).getStateOrProvince().setValue(patientRecord.getFacilityState());
								zlr.getOrderingFacilityAddress(0).getZipOrPostalCode().setValue(patientRecord.getFacilityZip());
								zlr.getOrderingFacilityPhone(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode());
								zlr.getOrderingFacilityPhone(0).getPhoneNumber().setValue(patientRecord.getFacilityPhoneNumber());
								zlr.getPatientAge().getNum1().setValue(patientRecord.getPatientAge());							
								
								
								List<OBXRecord> obxList = patientRecord.getObxList();
								if(obxList != null){
									String isolateName = null;
									int ctr = 0;

									//order obx and nte
									//List<OBXRecord> obxList = patRec.getObxList();
									//log.warn("toHL7Dto(): OBXLIST SIZE: " + (obxList == null ? "NULL" : String.valueOf(obxList.size())));
									//log.warn("toHL7Dto(): OBXLIST: " + (obxList == null ? "NULL" : obxList.toString()));
									
									
									//group by order test code
									TreeMap<String, List<OBXRecord>> obxOtcMap = new TreeMap<String,List<OBXRecord>>();
									if(obxList != null){
										for(OBXRecord obx : obxList){
											String otc = obx.getCompoundTestCode();
											if(obxOtcMap.containsKey(otc)){
												List<OBXRecord> obxrList = obxOtcMap.get(otc);
												obxrList.add(obx);
											}else{
												List<OBXRecord> obxrList = new ArrayList<OBXRecord>();
												obxrList.add(obx);
												obxOtcMap.put(otc, obxrList);
											}
										}
										Collection<List<OBXRecord>> valueList = obxOtcMap.values();
										obxList = new ArrayList<OBXRecord>();
										for(List<OBXRecord> value : valueList){
											for(OBXRecord obxr : value){
												obxList.add(obxr);
											}
										}
									}
									
									TreeMap<Integer, OBXRecord> seqObxMap = new TreeMap<Integer, OBXRecord>();
									if(obxList != null){
										for(OBXRecord obx : obxList){
											Integer seqNo = obx.getSequenceNo();
											if(seqNo != null){
												if(seqObxMap.containsKey(seqNo)){
													Map.Entry<Integer, OBXRecord> lastEntry = seqObxMap.lastEntry();
													Integer lastKey = lastEntry.getKey();
													//seqNo = new Integer(seqNo.intValue() + 1);
													seqNo = new Integer(lastKey.intValue() + 1);
												}
											}else{
												seqNo = new Integer(obxList.size() + 1);
											}
											//seqObxMap.put(obx.getSequenceNo(), obx);
											seqObxMap.put(seqNo, obx);
										}
										obxList = new ArrayList<OBXRecord>(seqObxMap.values());
									}
									
									List<NTERecord> nteList = patientRecord.getNteList();
									TreeMap<Integer, NTERecord> seqNteMap = new TreeMap<Integer, NTERecord>();
									if(nteList != null){
										for(NTERecord nte : nteList){
											Integer seqNo = nte.getSequenceNo();
											if(seqNo != null){
												if(seqNteMap.containsKey(seqNo)){
													Map.Entry<Integer, NTERecord> lastEntry = seqNteMap.lastEntry();
													Integer lastKey = lastEntry.getKey();
													//seqNo = new Integer(seqNo.intValue() + 1);
													seqNo = new Integer(lastKey.intValue() + 1);
												}
											}else{
												seqNo = new Integer(nteList.size() + 1);
											}
											//seqNteMap.put(nte.getSequenceNo(), nte);
											seqNteMap.put(seqNo, nte);
										}
										nteList = new ArrayList<NTERecord>(seqNteMap.values());
									}
									//end order obx and nte										
									
									
									
									for(int i = 0; i < obxList.size(); i++){
									//for(int i = 0; i < seqObxList.size(); i++){
										ctr += 1;
										OBXRecord obxRecord = obxList.get(i);
										NTERecord nteRecord = nteList.get(i);
										//OBXRecord obxRecord = seqObxList.get(i);

										if(obxRecord.getSourceOfComment().equalsIgnoreCase("PT-ANALYT") ) {
											isolateName = obxRecord.getSeqResultName().trim();
										}
										
										ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(i);
										
										String tnr = obxRecord.getTextualNumResult();
										
										if((obxRecord.getSubTestCode() != null) && 
												(obxRecord.getSeqResultName() != null) && 
												(obxRecord.getTextualNumResult() != null) && 
												(!obxRecord.getTextualNumResult().contains("\n"))){
											OBX obx = observation.getOBX();
											log.warn("toHL7Dto(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));
											obx.getSetIDOBX().setValue(String.valueOf(ctr));
											//obx.getValueType().setValue(obxRecord.getTestResultType()); //obxrecord.getTestresult_type
											obx.getValueType().setValue("SN");
											if((obxRecord.getLoincCode() != null) && (obxRecord.getLoincCode().length() > 0)){
												obx.getObservationIdentifier().getIdentifier().setValue(obxRecord.getLoincCode());
												obx.getObservationIdentifier().getText().setValue(obxRecord.getLoincName());
												obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
											}
											if((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)){
												StringBuilder builder = new StringBuilder();
												builder.append(obxRecord.getSubTestCode()).append("-1-001");
												obx.getObservationIdentifier().getAlternateIdentifier().setValue(builder.toString());
												obx.getObservationIdentifier().getAlternateText().setValue(obxRecord.getSeqTestName());
												obx.getObservationIdentifier().getNameOfAlternateCodingSystem().setValue("L");										
											}
											String testResultType = obxRecord.getTestResultType();
											//if("ST".equals(testResultType)){
											//	ST st = new ST(zru); //obx.testResultType could be ST or NM
											//	st.setValue(obxRecord.getTextualNumResult());
											//	Varies value = obx.getObservationValue(0);
											//	value.setData(st);											
											//}else if("NM".equals(testResultType)){
											//	NM nm = new NM(zru);
											//	nm.setValue(obxRecord.getTextualNumResult());
											//	Varies value = obx.getObservationValue(0);
											//	value.setData(nm);
											//}else{
											//	ST st = new ST(zru); //obx.testResultType could be ST or NM
											//	st.setValue(obxRecord.getTextualNumResult());
											//	Varies value = obx.getObservationValue(0);
											//	value.setData(st);
											//}
											String op = null;
											String val = null;
											String result = obxRecord.getTextualNumResult();

											if(result.indexOf("<") != -1){
												op = result.substring(0, (result.indexOf("<") + 1));
												val = result.substring((result.indexOf("<") + 1));
											}else if(result.indexOf(">") != -1){
												op = result.substring(0, (result.indexOf(">") + 1));
												val = result.substring((result.indexOf(">") + 1));
											}else if(result.indexOf("<=") != -1){
												op = result.substring(0, (result.indexOf("<=") + 2));
												val = result.substring((result.indexOf("<=") + 2));
											}else if(result.indexOf(">=") != -1){
												op = result.substring(0, (result.indexOf(">=") + 2));
												val = result.substring((result.indexOf(">=") + 2));
											}else if(result.indexOf("=") != -1){
												op = result.substring(0, (result.indexOf("=") + 1));
												val = result.substring((result.indexOf("=") + 1));											
											}else{
												val = result;
											}

											if("ST".equals(testResultType)){
												if(op == null){
													String txtNumRes = obxRecord.getTextualNumResult().trim();
													obx.getValueType().setValue("ST");
													ST st = new ST(zru); //obx.testResultType could be ST or NM
													st.setValue(txtNumRes);
													Varies value = obx.getObservationValue(0);
													value.setData(st);


													// add organism name to observation val
													String microOrgName = obxRecord.getMicroOrganismName();
													if(microOrgName != null){
														if((txtNumRes != null) && (txtNumRes.indexOf(microOrgName) == -1)){
															st = new ST(zru); //obx.testResultType could be ST or NM
															st.setValue(microOrgName);
															value = obx.getObservationValue(1);
															value.setData(st);
														}
													}

												}else{
													SN sn = new SN(zru);
													if(op != null){
														sn.getComparator().setValue(op);
													}
													sn.getNum1().setValue("1");
													sn.getSeparatorOrSuffix().setValue(":");
													if(val != null){
														sn.getNum2().setValue(val);
													}
													//sn.getNum2().setValue(obxRecord.getTextualNumResult());
													Varies value = obx.getObservationValue(0);
													value.setData(sn);
												}
											}else if("NM".equals(testResultType)){
												//NM nm = new NM(zru);
												//nm.setValue(obxRecord.getTextualNumResult());
												SN sn = new SN(zru);
												if(op != null){
													sn.getComparator().setValue(op);
												}
												sn.getNum1().setValue("1");
												sn.getSeparatorOrSuffix().setValue(":");
												if(val != null){
													sn.getNum2().setValue(val);
												}											
												//sn.getNum1().setValue(obxRecord.getTextualNumResult());											
												Varies value = obx.getObservationValue(0);
												//value.setData(nm);
												value.setData(sn);
											}else{
												//ST st = new ST(zru); //obx.testResultType could be ST or NM
												//st.setValue(obxRecord.getTextualNumResult());
												SN sn = new SN(zru);
												if(op != null){
													sn.getComparator().setValue(op);
												}
												sn.getNum1().setValue("1");
												sn.getSeparatorOrSuffix().setValue(":");
												if(val != null){
													sn.getNum2().setValue(val);
												}											
												//sn.getNum1().setValue(obxRecord.getTextualNumResult());											
												Varies value = obx.getObservationValue(0);
												//value.setData(st);
												value.setData(sn);
											}										
											obx.getUnits().getText().setValue(obxRecord.getUnits());
											obx.getReferencesRange().setValue(obxRecord.getRefRange());
											obx.getAbnormalFlags(0).setValue(obxRecord.getAbnormalFlag());
											obx.getObservResultStatus().setValue(obxRecord.getResultStatus());
											StringBuilder releaseDateTimeBuilder = new StringBuilder();
											releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
											obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(releaseDateTimeBuilder.toString());
											log.warn("toHL7Dto(): requisition id: " + (patientRecord.getOrderNumber() == null ? "NULL" : patientRecord.getOrderNumber()));
											log.warn("toHL7Dto(): sendingFacilityId: " + (sendingFacilityId == null ? "NULL" : sendingFacilityId));
											log.warn("toHL7Dto(): fileSendingFacility: " + (fileSendingFacility == null ? "NULL" : fileSendingFacility));
											obx.getProducerSID().getIdentifier().setValue(sendingFacilityId); //31D0961672
											obx.getProducerSID().getText().setValue(fileSendingFacility); //Spectra East
											obx.getProducerSID().getNameOfCodingSystem().setValue(sendingFacilityIdType); //CLIA
											obx.getObservationMethod(0).getIdentifier().setValue("FMC");
											
										}else if(obxRecord.getTextualNumResult().contains("\n")){
											String[] obxTextNumResultArray = obxRecord.getTextualNumResult().split("\n");
											if(obxTextNumResultArray != null) {
												for(int j = 0; j < obxTextNumResultArray.length; j++) {
													if(!(obxTextNumResultArray[j].trim().equalsIgnoreCase(""))) {
														OBX obx = observation.getOBX();
														log.warn("toHL7Dto(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));
														obx.getSetIDOBX().setValue(String.valueOf(ctr));
														obx.getValueType().setValue(obxRecord.getTestResultType()); //obxrecord.getTestresult_type
														if((obxRecord.getLoincCode() != null) && (obxRecord.getLoincCode().length() > 0)){
															obx.getObservationIdentifier().getIdentifier().setValue(obxRecord.getLoincCode());
															obx.getObservationIdentifier().getText().setValue(obxRecord.getLoincName());
															obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
														}
														if((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)){
															StringBuilder builder = new StringBuilder();
															builder.append(obxRecord.getSubTestCode()).append("-1-001");
															obx.getObservationIdentifier().getAlternateIdentifier().setValue(builder.toString());
															obx.getObservationIdentifier().getAlternateText().setValue(obxRecord.getSeqTestName());
															obx.getObservationIdentifier().getNameOfAlternateCodingSystem().setValue("L");										
														}
														String testResultType = obxRecord.getTestResultType();
														//if("ST".equals(testResultType)){
														//	ST st = new ST(zru); //obx.testResultType could be ST or NM
														//	//st.setValue(obxRecord.getTextualNumResult());
														//	st.setValue(obxTextNumResultArray[j].trim());
														//	Varies value = obx.getObservationValue(0);
														//	value.setData(st);											
														//}else if("NM".equals(testResultType)){
														//	NM nm = new NM(zru);
														//	//nm.setValue(obxRecord.getTextualNumResult());
														//	nm.setValue(obxTextNumResultArray[j].trim());
														//	Varies value = obx.getObservationValue(0);
														//	value.setData(nm);
														//}else{
														//	ST st = new ST(zru); //obx.testResultType could be ST or NM
														//	//st.setValue(obxRecord.getTextualNumResult());
														//	st.setValue(obxTextNumResultArray[j].trim());
														//	Varies value = obx.getObservationValue(0);
														//	value.setData(st);
														//}
														
														//result
														String op = null;
														String val = null;
														//String result = obxRecord.getTextualNumResult();
														String result = obxTextNumResultArray[j];

														if(result.indexOf("<") != -1){
															op = result.substring(0, (result.indexOf("<") + 1));
															val = result.substring((result.indexOf("<") + 1));
														}else if(result.indexOf(">") != -1){
															op = result.substring(0, (result.indexOf(">") + 1));
															val = result.substring((result.indexOf(">") + 1));
														}else if(result.indexOf("<=") != -1){
															op = result.substring(0, (result.indexOf("<=") + 2));
															val = result.substring((result.indexOf("<=") + 2));
														}else if(result.indexOf(">=") != -1){
															op = result.substring(0, (result.indexOf(">=") + 2));
															val = result.substring((result.indexOf(">=") + 2));
														}else if(result.indexOf("=") != -1){
															op = result.substring(0, (result.indexOf("=") + 1));
															val = result.substring((result.indexOf("=") + 1));											
														}else{
															val = result;
														}

														if("ST".equals(testResultType)){
															if(op == null){
																//String txtNumRes = obxRecord.getTextualNumResult().trim();
																String txtNumRes = obxTextNumResultArray[j].trim();
																obx.getValueType().setValue("ST");
																ST st = new ST(zru); //obx.testResultType could be ST or NM
																st.setValue(txtNumRes);
																Varies value = obx.getObservationValue(0);
																value.setData(st);


																// add organism name to observation val
																String microOrgName = obxRecord.getMicroOrganismName();
																if(microOrgName != null){
																	if((txtNumRes != null) && (txtNumRes.indexOf(microOrgName) == -1)){
																		st = new ST(zru); //obx.testResultType could be ST or NM
																		st.setValue(microOrgName);
																		value = obx.getObservationValue(1);
																		value.setData(st);
																	}
																}

															}else{
																SN sn = new SN(zru);
																if(op != null){
																	sn.getComparator().setValue(op);
																}
																sn.getNum1().setValue("1");
																sn.getSeparatorOrSuffix().setValue(":");
																if(val != null){
																	sn.getNum2().setValue(val);
																}
																//sn.getNum2().setValue(obxRecord.getTextualNumResult());
																Varies value = obx.getObservationValue(0);
																value.setData(sn);
															}
														}else if("NM".equals(testResultType)){
															//NM nm = new NM(zru);
															//nm.setValue(obxRecord.getTextualNumResult());
															SN sn = new SN(zru);
															if(op != null){
																sn.getComparator().setValue(op);
															}
															sn.getNum1().setValue("1");
															sn.getSeparatorOrSuffix().setValue(":");
															if(val != null){
																sn.getNum2().setValue(val);
															}											
															//sn.getNum1().setValue(obxRecord.getTextualNumResult());											
															Varies value = obx.getObservationValue(0);
															//value.setData(nm);
															value.setData(sn);
														}else{
															//ST st = new ST(zru); //obx.testResultType could be ST or NM
															//st.setValue(obxRecord.getTextualNumResult());
															SN sn = new SN(zru);
															if(op != null){
																sn.getComparator().setValue(op);
															}
															sn.getNum1().setValue("1");
															sn.getSeparatorOrSuffix().setValue(":");
															if(val != null){
																sn.getNum2().setValue(val);
															}											
															//sn.getNum1().setValue(obxRecord.getTextualNumResult());											
															Varies value = obx.getObservationValue(0);
															//value.setData(st);
															value.setData(sn);
														}														
														//end result
														
														obx.getUnits().getText().setValue(obxRecord.getUnits());
														obx.getReferencesRange().setValue(obxRecord.getRefRange());
														obx.getAbnormalFlags(0).setValue(obxRecord.getAbnormalFlag());
														obx.getObservResultStatus().setValue(obxRecord.getResultStatus());
														StringBuilder releaseDateTimeBuilder = new StringBuilder();
														releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
														obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(releaseDateTimeBuilder.toString());
														log.warn("toHL7Dto(): requisition id: " + (patientRecord.getOrderNumber() == null ? "NULL" : patientRecord.getOrderNumber()));
														log.warn("toHL7Dto(): sendingFacilityId: " + (sendingFacilityId == null ? "NULL" : sendingFacilityId));
														log.warn("toHL7Dto(): fileSendingFacility: " + (fileSendingFacility == null ? "NULL" : fileSendingFacility));
														obx.getProducerSID().getIdentifier().setValue(sendingFacilityId); //31D0961672
														obx.getProducerSID().getText().setValue(fileSendingFacility); //Spectra East
														obx.getProducerSID().getNameOfCodingSystem().setValue(sendingFacilityIdType); //CLIA
														obx.getObservationMethod(0).getIdentifier().setValue("FMC");
														
														ctr++;
													}
												}
											} 
											ctr--;
										}else{ 
											ctr -= 1; 
										}//if
										
									}//for
								}//if
								
								
								//String hapiCustomPackage = this.configService.getString("hapi.custom.package");
								//ModelClassFactory cmf = new CustomModelClassFactory(ip.getHapiCustomPackage());
								ModelClassFactory cmf = new CustomModelClassFactory(hapiCustomPackage);
								HapiContext context = new DefaultHapiContext();
								context.setModelClassFactory(cmf);
								
								//HapiContext context = new DefaultHapiContext();
								//HapiContext context = this.createHapiContext();
								Parser parser = context.getPipeParser();
								//hl7String = parser.encode(zru);
								
								//hl7Builder.append(parser.encode(zru));
								zruBuilder.append(parser.encode(zru));
								
							}catch(IOException ioe){
								log.error(ioe);
								ioe.printStackTrace();
							}catch(HL7Exception hl7e){
								log.error(hl7e);
								hl7e.printStackTrace();
							}//catch(ConfigException ce){
							//	log.error(ce);
							//	ce.printStackTrace();
							//}
						}//for
						

						
					}
				}//outer for
				
				
				
				StringBuilder btsBuilder = new StringBuilder();
				btsBuilder.append("BTS|").append(String.valueOf(patientRecordListSize)).append("|").append("\n");
				//hl7Builder.append(btsBuilder.toString());
				
				StringBuilder ftsBuilder = new StringBuilder();
				ftsBuilder.append("FTS|1|").append("\n");
				//hl7Builder.append(ftsBuilder.toString());
				
				//StringBuffer footer = new StringBuffer();
				//footer.append("BTS|").append(c).append("|");
				//pw.println(footer.toString());
				//pw.println("FTS|1|");			
				
				
				//hl7String = hl7Builder.toString();
				
				if((zruBuilder != null) && (zruBuilder.length() > 0)){
					hl7Builder.append(fhsBuilder.toString());
					hl7Builder.append(bhsBuilder.toString());
					hl7Builder.append(zruBuilder.toString());
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
				
			}
		}
*/		
		log.warn("toHL7Dto(): hl7Dto: " + (hl7Dto == null ? "NULL" : hl7Dto.toString()));
		return hl7Dto;
	}

}
