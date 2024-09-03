package com.spectra.result.transporter.businessobject.spring.hl7.state;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v23.datatype.NM;
import ca.uhn.hl7v2.model.v23.datatype.ST;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v23.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.OBR;
import ca.uhn.hl7v2.model.v23.segment.OBX;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.parser.CustomModelClassFactory;
import ca.uhn.hl7v2.parser.ModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;
import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.dto.hl7.state.OBXRecord;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.hl7v2.model.v23.group.ZRU_R01_ORDER_OBSERVATION;
import com.spectra.result.transporter.hl7v2.model.v23.group.ZRU_R01_RESPONSE;
import com.spectra.result.transporter.hl7v2.model.v23.message.ZRU_R01;
import com.spectra.result.transporter.hl7v2.model.v23.segment.ZLR;
import com.spectra.result.transporter.properties.state.StateInterfaceProperties;
import com.spectra.result.transporter.utils.props.PropertiesUtil;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
public class StateHL7WriterBoImpl implements StateHL7WriterBo {
	//private Logger log = Logger.getLogger(StateHL7WriterBoImpl.class);
	
	protected PropertiesUtil propertiesUtil;
	protected StateInterfaceProperties ip;
	protected ConfigService configService;

	@Override
	public void setPropertiesUtil(PropertiesUtil propertiesUtil) {
		this.propertiesUtil = propertiesUtil;
		ip = (StateInterfaceProperties)this.propertiesUtil.loadBeanProperties();
	}
	
	@Override
	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	@Override
	public boolean writeHL7(Map<String, List<PatientRecord>> listMap) throws BusinessException {
		boolean writeHl7 = false;
		if(this.configService != null){
			try{
				String localFolder = this.configService.getString("folder.local");
				String archiveFolder = this.configService.getString("folder.drop");
				log.debug("writeHL7(): localFolder: " + (localFolder == null ? "NULL" : localFolder));
				log.debug("writeHL7(): archiveFolder: " + (archiveFolder == null ? "NULL" : archiveFolder));
				if(listMap != null && localFolder != null && archiveFolder != null){
					String state = listMap.values().iterator().next().get(0).getPatientState();
					String hl7String = this.toHL7String(listMap);
					log.debug("writeHL7(): hl7String: " + (hl7String == null ? "NULL" : hl7String));
					if(hl7String != null){
						//DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
						DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
						Date date = new Date();
						String year  = dateFormat.format(date).substring(0, 4);
						String month = dateFormat.format(date).substring(4, 6);
						String dt = dateFormat.format(date);
						
						StringBuilder builder = new StringBuilder();
						//builder.append(parms.myArchPath).append(parms.myArchFile);
						//builder.append(parms.myArchPath).append(year).append("_").append(month).append("/");
						builder.append(localFolder);
						File localFile = new File(builder.toString());
						boolean created = false;
						if(!localFile.exists()){
							created = localFile.mkdirs();
							log.debug("writeHL7(): created: " + String.valueOf(created) + " " + localFile.toString());
						}
	
						String archFile = this.configService.getString("arch.file");
						//archFile = archFile.substring(0, (archFile.lastIndexOf(".") + 1));
						archFile = MessageFormat.format(archFile, new String[]{ state, dt, });

						//builder.append(archFile).append(dt).append(".txt");
						builder.append(archFile);

						//log.debug("writeHL7(): builder: " + (builder == null ? "NULL" : builder.toString()));
/*
						localFile = new File(builder.toString());
						FileWriter fw = null;
						BufferedWriter bw = null;
						try{
							if(!localFile.exists()){
								localFile.createNewFile();
							}			
							fw = new FileWriter(localFile, true);
							bw = new BufferedWriter(fw);
							bw.write(hl7String);
						}catch(IOException ioe){
							ioe.printStackTrace();
						}finally{
							if(bw != null){
								try{
									bw.close();
								}catch(IOException ioe){
									ioe.printStackTrace();
								}
							}
							if(fw != null){
								try{
									fw.close();
								}catch(IOException ioe){
									ioe.printStackTrace();
								}
							}
						}
*/						
						writeHl7 = true;
						log.debug("writeHL7(): builder: " + (builder == null ? "NULL" : builder.toString()));
	
					}
				}
			}catch(ConfigException ce){
				log.error(String.valueOf(ce));
				ce.printStackTrace();
			}
		}//if
		
		/*if(listMap != null){
			String hl7String = this.toHL7String(listMap);
			log.debug("writeHL7(): hl7String: " + (hl7String == null ? "NULL" : hl7String));
		}*/
		return writeHl7;
	}

	public String toHL7String(Map<String, List<PatientRecord>> listMap) throws BusinessException {
		String hl7String = null;
		//if((listMap != null) && (this.propertiesUtil != null)){
		if((listMap != null) && (this.configService != null)){
			DateFormat datetimeFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date datetime = new Date();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
			Date date = new Date();
			DateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
			Date date2 = new Date();
			
			PrintWriter pw = null;
			StringWriter sw = null;
			
			Set<String> keySet = listMap.keySet();
			StringBuilder hl7Builder = new StringBuilder();
			for(String key : keySet){
				String facilityId = null;
				if(key != null){
					if(key.startsWith("1") || key.startsWith("7")){
						facilityId = key + "E";
					}else if(key.startsWith("8")){
						facilityId = key + "T";
					}else{
						facilityId = key;
					}
				}
				if(facilityId == null){
					throw new BusinessException("NULL facilityId");
				}
				List<PatientRecord> patientRecordList = listMap.get(key);
				if(patientRecordList != null){
					for(PatientRecord patientRecord : patientRecordList){
						ZRU_R01 zru = new ZRU_R01();
						try{
							zru.initQuickstart("ORU", "R01", "P");
							
							MSH msh = zru.getMSH();
							msh.getSendingApplication().getNamespaceID().setValue("EAST");
							msh.getSendingFacility().getNamespaceID().setValue("Spectra East");
							msh.getSendingFacility().getUniversalID().setValue("31D0961672");
							msh.getSendingFacility().getUniversalIDType().setValue("CLIA");
							msh.getReceivingFacility().getNamespaceID().setValue(patientRecord.getCid()); //cid
							msh.getReceivingFacility().getUniversalID().setValue(patientRecord.getMhsOrderingFacId()); //Mhs_ordering_fac_id
							//DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
							String timeOfAnEvent = dateFormat.format(new Date());
							msh.getDateTimeOfMessage().getTimeOfAnEvent().setValue(timeOfAnEvent);
							msh.getMessageControlID().setValue("1");			
							
							ZRU_R01_RESPONSE response = zru.getRESPONSE();
							//log.debug("toHL7String(): response: " + (response == null ? "NULL" : response.getName()));
							ORU_R01_PATIENT patient = response.getPATIENT();
							//log.debug("toHL7String(): patient: " + (patient == null ? "NULL" : patient.toString()));
							//log.debug("toHL7String(): patient: " + (patient == null ? "NULL" : patient.getName()));
							PID pid = patient.getPID();
							//log.debug("toHL7String(): pid: " + (pid == null ? "NULL" : pid.getName()));

							pid.getSetIDPatientID().setValue("1");
							pid.getPatientIDExternalID().getID().setValue(patientRecord.getMrnId()); //Mrn_id
							pid.getPatientIDInternalID(0).getID().setValue(patientRecord.getAltPatientId()); //patientrecord.getAlt_patient_id
							//pid.getAlternatePatientID().getID().setValue("8000075633"); //patientrecord.getAlt_patient_id
							String patientName = patientRecord.getPatientName();
							log.debug("toHL7String(): patientName: " + (patientName == null ? "NULL" : patientName));
							//if(patientName.indexOf("^") != -1){
							if(StringUtils.contains(patientName, "^")){
								//String[] nameArray = patientName.split("^");
								String[] nameArray = StringUtils.split(patientName, "\\^");
								if((nameArray != null) && (nameArray.length >= 2)){
									log.debug("toHL7String(): nameArray[0]: " + (nameArray[0] == null ? "NULL" : nameArray[0]));
									log.debug("toHL7String(): nameArray[1]: " + (nameArray[1] == null ? "NULL" : nameArray[1]));
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
							pid.getRace().setValue(patientRecord.getPatientRace()); //patientrecord.getPatientRace
							pid.getPatientAddress(0).getStreetAddress().setValue(patientRecord.getPatientAddress1()); //patientrecord.getPatient_address1
							//pid.getPatientAddress(0).getXad1_StreetAddress().setValue("");
							pid.getPatientAddress(0).getCity().setValue(patientRecord.getPatientCity()); //patientrecord.getPatient_city
							pid.getPatientAddress(0).getStateOrProvince().setValue(patientRecord.getPatientState()); //patientrecord.getPatient_state
							pid.getPatientAddress(0).getZipOrPostalCode().setValue(patientRecord.getPatientZip()); //patientrecord.getPatient_zip
							//pid.getPhoneNumberHome(0).getPhoneNumber().setValue("201-767-7070"); //patientrecord.getPatient_phone
							pid.getPhoneNumberHome(0).getAreaCityCode().setValue(patientRecord.getPatientAreaCode());
							pid.getPhoneNumberHome(0).getPhoneNumber().setValue(patientRecord.getPatientPhone());
							//pid.getPhoneNumberHome(0).getAnyText().setValue("201-767-7070");
							pid.getSSNNumberPatient().setValue(patientRecord.getPatientSsn()); //patientrecord.getPatient_ssn
							pid.getEthnicGroup().setValue(patientRecord.getEthnicGroup()); //patientrecord.getEthnicGroup
							
							OBXRecord obxRec = patientRecord.getObxList().get(0);
							
							ZRU_R01_ORDER_OBSERVATION orderObservation = response.getORDER_OBSERVATION();
							log.debug("toHL7String(): orderObservation: " + (orderObservation == null ? "NULL" : orderObservation.getName()));
							OBR obr = orderObservation.getOBR();
							log.debug("toHL7String(): obr: " + (obr == null ? "NULL" : obr.getName()));
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
							log.debug("toHL7String(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
							if(StringUtils.contains(orderingPhyName, "^")){
								//String[] nameArray = orderingPhyName.split("^");
								String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
								if((phyNameArray != null) && (phyNameArray.length >= 2)){
									log.debug("toHL7String(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
									log.debug("toHL7String(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
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
							log.debug("toHL7String(): zlr: " + (zlr == null ? "NULL" : zlr.getName()));
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
								for(int i = 0; i < obxList.size(); i++){
									ctr += 1;
									OBXRecord obxRecord = obxList.get(i);
									
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
										log.debug("toHL7String(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));				
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
										if("ST".equals(testResultType)){
											ST st = new ST(zru); //obx.testResultType could be ST or NM
											st.setValue(obxRecord.getTextualNumResult());
											Varies value = obx.getObservationValue(0);
											value.setData(st);											
										}else if("NM".equals(testResultType)){
											NM nm = new NM(zru);
											nm.setValue(obxRecord.getTextualNumResult());
											Varies value = obx.getObservationValue(0);
											value.setData(nm);
										}else{
											ST st = new ST(zru); //obx.testResultType could be ST or NM
											st.setValue(obxRecord.getTextualNumResult());
											Varies value = obx.getObservationValue(0);
											value.setData(st);
										}
										obx.getUnits().getText().setValue(obxRecord.getUnits());
										obx.getReferencesRange().setValue(obxRecord.getRefRange());
										obx.getAbnormalFlags(0).setValue(obxRecord.getAbnormalFlag());
										obx.getObservResultStatus().setValue(obxRecord.getResultStatus());
										StringBuilder releaseDateTimeBuilder = new StringBuilder();
										releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
										obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(releaseDateTimeBuilder.toString());
										obx.getProducerSID().getIdentifier().setValue("31D0961672");
										obx.getProducerSID().getText().setValue("Spectra East");
										obx.getProducerSID().getNameOfCodingSystem().setValue("CLIA");
										obx.getObservationMethod(0).getIdentifier().setValue("FMC");
										
									}else if(obxRecord.getTextualNumResult().contains("\n")){
										String[] obxTextNumResultArray = obxRecord.getTextualNumResult().split("\n");
										if(obxTextNumResultArray != null) {
											for(int j = 0; j < obxTextNumResultArray.length; j++) {
												if(!(obxTextNumResultArray[j].trim().equalsIgnoreCase(""))) {
													OBX obx = observation.getOBX();
													log.debug("toHL7String(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));				
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
													if("ST".equals(testResultType)){
														ST st = new ST(zru); //obx.testResultType could be ST or NM
														//st.setValue(obxRecord.getTextualNumResult());
														st.setValue(obxTextNumResultArray[j].trim());
														Varies value = obx.getObservationValue(0);
														value.setData(st);											
													}else if("NM".equals(testResultType)){
														NM nm = new NM(zru);
														//nm.setValue(obxRecord.getTextualNumResult());
														nm.setValue(obxTextNumResultArray[j].trim());
														Varies value = obx.getObservationValue(0);
														value.setData(nm);
													}else{
														ST st = new ST(zru); //obx.testResultType could be ST or NM
														//st.setValue(obxRecord.getTextualNumResult());
														st.setValue(obxTextNumResultArray[j].trim());
														Varies value = obx.getObservationValue(0);
														value.setData(st);
													}
													obx.getUnits().getText().setValue(obxRecord.getUnits());
													obx.getReferencesRange().setValue(obxRecord.getRefRange());
													obx.getAbnormalFlags(0).setValue(obxRecord.getAbnormalFlag());
													obx.getObservResultStatus().setValue(obxRecord.getResultStatus());
													StringBuilder releaseDateTimeBuilder = new StringBuilder();
													releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
													obx.getDateTimeOfTheObservation().getTimeOfAnEvent().setValue(releaseDateTimeBuilder.toString());
													obx.getProducerSID().getIdentifier().setValue("31D0961672");
													obx.getProducerSID().getText().setValue("Spectra East");
													obx.getProducerSID().getNameOfCodingSystem().setValue("CLIA");
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
							
							
							String hapiCustomPackage = this.configService.getString("hapi.custom.package");
							//ModelClassFactory cmf = new CustomModelClassFactory(ip.getHapiCustomPackage());
							ModelClassFactory cmf = new CustomModelClassFactory(hapiCustomPackage);
							HapiContext context = new DefaultHapiContext();
							context.setModelClassFactory(cmf);
							
							//HapiContext context = new DefaultHapiContext();
							//HapiContext context = this.createHapiContext();
							Parser parser = context.getPipeParser();
							//hl7String = parser.encode(zru);
							hl7Builder.append(parser.encode(zru));
						}catch(IOException ioe){
							log.error(ioe.getMessage());
							ioe.printStackTrace();
						}catch(HL7Exception hl7e){
							log.error(hl7e.getMessage());
							hl7e.printStackTrace();
						}catch(ConfigException ce){
							log.error(ce.getMessage());
							ce.printStackTrace();
						}
					}//for
				}
			}//for
			hl7String = hl7Builder.toString();
		}
		return hl7String;
	}
}
