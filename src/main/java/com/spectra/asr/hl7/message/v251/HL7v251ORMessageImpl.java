package com.spectra.asr.hl7.message.v251;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v23.datatype.CE;
import ca.uhn.hl7v2.model.v251.datatype.SN;
import ca.uhn.hl7v2.model.v251.datatype.ST;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.*;
import com.spectra.asr.dto.patient.NTERecord;
import com.spectra.asr.dto.patient.OBXRecord;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.hl7.message.HL7Message;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import static com.spectra.asr.utils.file.writer.FileUtil.getMSHControlNum;

@Slf4j
public class HL7v251ORMessageImpl implements HL7Message {
	//private Logger log = Logger.getLogger(HL7v251ORMessageImpl.class);
	
	@Override
	public AbstractMessage getMessage(PatientRecord patientRecord, Map<String, Object> paramMap) {

		Map<String,String> coding = new HashMap<>();


		coding.put("329M_Loinc","94564-2|SARS coronavirus 2 Ab.IgM:PrThr:Pt:Ser:Ord:IA");
		coding.put("329G_Loinc","94563-4|SARS coronavirus 2 Ab.IgG:PrThr:Pt:Ser:Ord:IA");
		coding.put("Reactive","11214006|Reactive|SCT|Reactive|Reactive|L");
		coding.put("Nonreactive","131194007|Non-Reactive|SCT|Nonreactive|Nonreactive|L");

		//AbstractMessage msg = null;
		ORU_R01 oru = null;
		
		if(patientRecord != null){
			String performingLabId = patientRecord.getPerformingLabId();
			Integer labFk = patientRecord.getLabFk();
			
			String fileSendingFacility = null;
			String sendingApplication = null;
			String sendingFacilityId = null;
			
			String mshSendingApplication = (String)paramMap.get("mshSendingApplication");
			String mshFileSendingFacility = (String)paramMap.get("mshFileSendingFacility");
			String mshSendingFacilityId = (String)paramMap.get("mshSendingFacilityId");
			String sendingFacilityIdType = (String)paramMap.get("sendingFacilityIdType");
			DateFormat dateFormat = (DateFormat)paramMap.get("dateFormat");
			DateFormat dateFormat2 = (DateFormat)paramMap.get("dateFormat2");
			String infoSpectraAddress = (String)paramMap.get("infoSpectraAddress");
			String infoSpectraAddress2 = (String)paramMap.get("infoSpectraAddress2");
			String infoSpectraCity = (String)paramMap.get("infoSpectraCity");
			String infoSpectraState = (String)paramMap.get("infoSpectraState");
			String infoSpectraZip = (String)paramMap.get("infoSpectraZip");
			String medicalDirector = (String)paramMap.get("medicalDirector");
			
			String fileReceivingApplication = (String)paramMap.get("fileReceivingApplication");
			String fileReceivingFacility = (String)paramMap.get("fileReceivingFacility");
			String msgProfileId = (String)paramMap.get("msgProfileId");
			log.warn("getMessage(): msgProfileId: " + (msgProfileId == null ? "NULL" : msgProfileId));
			
			log.warn("getMessage(): medicalDirector: " + (medicalDirector == null ? "NULL" : medicalDirector));
			String mdFirstName = null;
			String mdLastName = null;
			String mdDoctor = null;

			if(medicalDirector.indexOf("Dr.") != -1){
				medicalDirector = medicalDirector.substring("Dr.".length());
				if(medicalDirector.indexOf(".") != -1){
					mdFirstName = medicalDirector.substring(0, (medicalDirector.lastIndexOf(".") + 1));
					mdLastName = medicalDirector.substring((medicalDirector.lastIndexOf(".") + 1));
				}
			}
			if(medicalDirector.indexOf("M.D.") != -1){
				//medicalDirector = medicalDirector.substring("M.D.".length());
				//if(medicalDirector.indexOf(".") != -1){
					mdFirstName = "Alex";
					mdLastName = "Ryder";
					mdDoctor = "MD";
				//}
			}
			if(medicalDirector.indexOf(" MD") != -1){
				medicalDirector = medicalDirector.substring(0, (medicalDirector.indexOf(" MD") + 1));
				mdFirstName = medicalDirector.substring(0, (medicalDirector.indexOf(" ") + 1));
				mdLastName = medicalDirector.substring(medicalDirector.indexOf(" "));
				if(mdLastName.indexOf(",") != -1){
					mdLastName = mdLastName.replaceAll(",", "");
				}
			}
			log.warn("getMessage(): mdFirstName: " + (mdFirstName == null ? "NULL" : mdFirstName));
			log.warn("getMessage(): mdLastName: " + (mdLastName == null ? "NULL" : mdLastName));
			
			
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

			if((labFk.intValue() == 7) && (performingLabId.indexOf("SE") != -1)){
				//sendout east address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
			}else if((labFk.intValue() == 7) && (performingLabId.indexOf("SW") != -1)) {
				//west address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
			}	else if((labFk.intValue() == 7) && (performingLabId.indexOf("SH") != -1)){
				//west address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.south.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.south.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.south.msh.4.2");

			}else if((labFk.intValue() == 7) && (performingLabId.indexOf("OUTSEND") != -1)){
				//west address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.west.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.west.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.west.msh.4.2");
			}

			oru = new ORU_R01();
			try{
				oru.initQuickstart("ORU", "R01", "P");

				MSH msh = oru.getMSH();
				//msh.getSendingApplication().getNamespaceID().setValue(mshSendingApplication); //EAST

				msh.getSendingApplication().getHd1_NamespaceID().setValue("ASR");
				msh.getSendingApplication().getHd3_UniversalIDType().setValue("ISO");


				msh.getSendingFacility().getNamespaceID().setValue(mshFileSendingFacility); //Spectra East
				msh.getSendingFacility().getUniversalID().setValue(mshSendingFacilityId); //31D0961672

				msh.getSendingFacility().getUniversalIDType().setValue(sendingFacilityIdType); //CLIA
				
				//msh.getReceivingFacility().getNamespaceID().setValue(patientRecord.getCid()); //cid
				//msh.getReceivingFacility().getUniversalID().setValue(patientRecord.getMhsOrderingFacId()); //Mhs_ordering_fac_id
//				log.warn("Shutting down PA");
//				System.exit(0);
				msh.getReceivingApplication().getNamespaceID().setValue(fileReceivingApplication);
				msh.getReceivingFacility().getNamespaceID().setValue(fileReceivingFacility);
				
				if((msgProfileId != null) || (msgProfileId.length() > 0)){
					String[] mpi = msgProfileId.split("\\^");
					String entityId = (mpi[0] == null ? "" : mpi[0]);
					String namespaceId = (mpi[1] == null ? "" : mpi[1]);
					String universalId = (mpi[2] == null ? "" : mpi[2]);
					String universalIdType = (mpi[3] == null ? "" : mpi[3]);
					//log.warn("toHL7Dto(): entityId: " + (entityId == null ? "NULL" : entityId));
					//log.warn("toHL7Dto(): namespaceId: " + (namespaceId == null ? "NULL" : namespaceId));
					//log.warn("toHL7Dto(): universalId: " + (universalId == null ? "NULL" : universalId));
					//log.warn("toHL7Dto(): universalIdType: " + (universalIdType == null ? "NULL" : universalIdType));
					//msh.getMessageProfileIdentifier(0).getNamespaceID().setValue(msgProfileId);

//					msh.getMessageProfileIdentifier(0).getEntityIdentifier().setValue(entityId);
//					msh.getMessageProfileIdentifier(0).getNamespaceID().setValue(namespaceId);
//					msh.getMessageProfileIdentifier(0).getUniversalID().setValue(universalId);
//					msh.getMessageProfileIdentifier(0).getUniversalIDType().setValue(universalIdType);
				}
				
				String timeOfAnEvent = dateFormat.format(new Date());
				msh.getDateTimeOfMessage().getTime().setValue(timeOfAnEvent);
				//msh.getMessageControlID().setValue(patientRecord.getAccessionNo());

				msh.getMessageControlID().setValue(getMSHControlNum());

				SFT sft = oru.getSFT();
				sft.getSoftwareVendorOrganization().getOrganizationName().setValue(mshFileSendingFacility);
				sft.getSoftwareCertifiedVersionOrReleaseNumber().setValue("1.0");
				sft.getSoftwareProductName().setValue("ASR");
				sft.getSoftwareBinaryID().setValue("1");
				String installDate = dateFormat2.format(new Date());
				sft.getSoftwareInstallDate().getTime().setValue(installDate);
				
				ORU_R01_PATIENT_RESULT patientResult = oru.getPATIENT_RESULT();
				ORU_R01_PATIENT patient = patientResult.getPATIENT();
				
				PID pid = patient.getPID();
				//log.warn("getMessage(): pid: " + (pid == null ? "NULL" : pid.getName()));

				pid.getSetIDPID().setValue("1");
				pid.getPatientID().getIDNumber().setValue(patientRecord.getMrnId());
				//pid.getAlternatePatientIDPID(0).getIDNumber().setValue(patientRecord.getAltPatientId());
				pid.getPid3_PatientIdentifierList(0).getIDNumber().setValue(patientRecord.getAltPatientId());
				
				String patientName = patientRecord.getPatientName();
				log.warn("toHL7Dto(): patientName: " + (patientName == null ? "NULL" : patientName));
				if(StringUtils.contains(patientName, "^")){
					String[] nameArray = StringUtils.split(patientName, "\\^");
					if((nameArray != null) && (nameArray.length >= 2)){
						log.warn("toHL7Dto(): nameArray[0]: " + (nameArray[0] == null ? "NULL" : nameArray[0]));
						log.warn("toHL7Dto(): nameArray[1]: " + (nameArray[1] == null ? "NULL" : nameArray[1]));
						pid.getPatientName(0).getFamilyName().getSurname().setValue(nameArray[0]);
						pid.getPatientName(0).getGivenName().setValue(nameArray[1]); // first name
					}
				}else{
					pid.getPatientName(0).getFamilyName().getSurname().setValue(patientName);
				}
				
				pid.getMotherSMaidenName(0).getFamilyName().getSurname().setValue(patientRecord.getCid());
				pid.getDateTimeOfBirth().getTime().setValue(patientRecord.getDateOfBirth());
				pid.getAdministrativeSex().setValue(patientRecord.getGender());
				pid.getPatientAlias(0).getFamilyName().getSurname().setValue("T");

				String race = patientRecord.getPatientRace();
				if((race == null) || (race.trim().length() == 0)){
					race = "U";
				}

				pid.getRace(0).getText().setValue(patientRecord.getPatientRace());
				pid.getRace(0).getIdentifier().setValue(patientRecord.getRaceCode());
				pid.getRace(0).getNameOfCodingSystem().setValue("HL7");

				String patAddr1 = patientRecord.getPatientAddress1();
				//log.warn("toHL7Dto(): patAddr1: " + (patAddr1 == null ? "NULL" : patAddr1));
				pid.getPatientAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(patientRecord.getPatientAddress1());
				//pid.getPatientAddress(0).getStreetAddress().getStreetName().setValue(patientRecord.getPatientAddress1());
				String patientAddress2 = patientRecord.getPatientAddress2();
				if((patientAddress2 != null) && (patientAddress2.length() > 0)){
					//pid.getPatientAddress(1).getStreetAddress().getStreetName().setValue(patientAddress2);
					pid.getPatientAddress(0).getOtherDesignation().setValue(patientAddress2);
				}
				pid.getPatientAddress(0).getCity().setValue(patientRecord.getPatientCity()); //patientrecord.getPatient_city
				pid.getPatientAddress(0).getStateOrProvince().setValue(patientRecord.getPatientState()); //patientrecord.getPatient_state
				pid.getPatientAddress(0).getZipOrPostalCode().setValue(patientRecord.getPatientZip()); //patientrecord.getPatient_zip
				pid.getPatientAddress(0).getCountry().setValue(patientRecord.getPatientCounty());
				pid.getPhoneNumberHome(0).getAreaCityCode().setValue(patientRecord.getPatientAreaCode());
				//pid.getPhoneNumberHome(0).getTelephoneNumber().setValue(patientRecord.getPatientPhone());
				//log.warn("toHL7Dto(): patientRecord.getPatientAreaCode(): " + (patientRecord.getPatientAreaCode() == null ? "NULL" : patientRecord.getPatientAreaCode()));
				//log.warn("toHL7Dto(): patientRecord.getPatientPhone(): " + (patientRecord.getPatientPhone() == null ? "NULL" : patientRecord.getPatientPhone()));
				pid.getPhoneNumberHome(0).getTelecommunicationEquipmentType().setValue("PH");
				pid.getPhoneNumberHome(0).getLocalNumber().setValue(patientRecord.getPatientPhone());
				pid.getPhoneNumberHome(0).getAreaCityCode().setValue(patientRecord.getPatientAreaCode());
				pid.getPid13_PhoneNumberHome(0).getCountryCode().setValue("1");

				pid.getPid13_PhoneNumberHome(0).getTelecommunicationUseCode().setValue("PRN");


				String ethnicGroup = patientRecord.getEthnicGroup();
				if((ethnicGroup == null) || (ethnicGroup.trim().length() == 0)){
					ethnicGroup = "U";
				}

				pid.getEthnicGroup(0).getText().setValue(patientRecord.getEthnicGroup());
				pid.getEthnicGroup(0).getIdentifier().setValue(patientRecord.getEthnicGroupCode());
				pid.getEthnicGroup(0).getNameOfCodingSystem().setValue("HL7");
				
				//NK1 nk1 = patient.getNK1();
				////nk1.getSetIDNK1().setValue("NK1");
				//nk1.getSetIDNK1().setValue("1");
				//nk1.getContactPersonSName(0).getFamilyName().getSurname().setValue("");
				//nk1.getContactPersonSName(0).getGivenName().setValue("");
				
				OBXRecord obxRec = patientRecord.getObxList().get(0);
				
				ORU_R01_ORDER_OBSERVATION orderObservation = patientResult.getORDER_OBSERVATION();
				
				ORC orc = orderObservation.getORC();
				orc.getOrderControl().setValue("RE");
				orc.getPlacerOrderNumber().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
				orc.getFillerOrderNumber().getEntityIdentifier().setValue(patientRecord.getOrderNumber());
				orc.getPlacerGroupNumber().getEntityIdentifier().setValue(patientRecord.getOrderNumber());
				orc.getOrderingProvider(0).getIDNumber().setValue(patientRecord.getProviderId()); //patientrecord.getProvider_id
				String orderingPhyName = patientRecord.getOrderingPhyName();
				log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
				if(StringUtils.contains(orderingPhyName, "^")){
					String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
					if((phyNameArray != null) && (phyNameArray.length >= 2)){
						log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
						log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
						orc.getOrderingProvider(0).getFamilyName().getSurname().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
						orc.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name									
					}
				}else{
					orc.getOrderingProvider(0).getFamilyName().getSurname().setValue(orderingPhyName); //last name
				}
				orc.getCallBackPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code
				//orc.getCallBackPhoneNumber(0).getTelephoneNumber().setValue(patientRecord.getFacilityPhoneNumber()); //patientrecord.getFacility_phone_number
				
				orc.getCallBackPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
				orc.getCallBackPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
				orc.getCallBackPhoneNumber(0).getCountryCode().setValue("1");
				orc.getCallBackPhoneNumber(0).getTelecommunicationUseCode().setValue("PRN");
				
				orc.getOrderingFacilityName(0).getOrganizationName().setValue(patientRecord.getFacilityName());
				//orc.getOrderingFacilityAddress(0).getStreetAddress().getStreetName().setValue(patientRecord.getFacilityAddress1());
				orc.getOrderingFacilityAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(patientRecord.getFacilityAddress1());
				orc.getOrderingFacilityAddress(0).getOtherDesignation().setValue(patientRecord.getFacilityAddress2());
				orc.getOrderingFacilityAddress(0).getCity().setValue(patientRecord.getFacilityCity());
				orc.getOrderingFacilityAddress(0).getStateOrProvince().setValue(patientRecord.getFacilityState());
				orc.getOrderingFacilityAddress(0).getZipOrPostalCode().setValue(patientRecord.getFacilityZip());
				orc.getOrderingFacilityPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code
				//orc.getOrderingFacilityPhoneNumber(0).getTelephoneNumber().setValue(patientRecord.getFacilityPhoneNumber()); //patientrecord.getFacility_phone_number
				
				orc.getOrderingFacilityPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
				orc.getOrderingFacilityPhoneNumber(0).getTelecommunicationUseCode().setValue("PRN");
				orc.getOrderingFacilityPhoneNumber(0).getCountryCode().setValue("1");
				orc.getOrderingFacilityPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
				
				//orc.getOrderingProviderAddress(0).getStreetAddress().getStreetName().setValue(infoSpectraAddress);
				orc.getOrderingProviderAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
				orc.getOrderingProviderAddress(0).getOtherDesignation().setValue(infoSpectraAddress2);
				orc.getOrderingProviderAddress(0).getCity().setValue(infoSpectraCity);
				orc.getOrderingProviderAddress(0).getStateOrProvince().setValue(infoSpectraState);
				orc.getOrderingProviderAddress(0).getZipOrPostalCode().setValue(infoSpectraZip);
				
				OBR obr = orderObservation.getOBR();
				log.warn("toHL7Dto(): obr: " + (obr == null ? "NULL" : obr.getName()));
				obr.getSetIDOBR().setValue("1");
				obr.getPlacerOrderNumber().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
				obr.getFillerOrderNumber().getEntityIdentifier().setValue(patientRecord.getOrderNumber()); //patientrecord.getOrder_number
				obr.getUniversalServiceIdentifier().getIdentifier().setValue(obxRec.getCompoundTestCode()); //obxrecord.getCompound_test_code
				obr.getUniversalServiceIdentifier().getText().setValue(obxRec.getSeqTestName()); //obxrecord.getSeq_test_name
				obr.getUniversalServiceIdentifier().getNameOfCodingSystem().setValue("L");
				obr.getObservationDateTime().getTime().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
				obr.getRelevantClinicalInformation().setValue("Recurring patient"); // NEED REQUIREMENT FROM QA
				obr.getSpecimenActionCode().setValue("G");
				obr.getSpecimenSource().getSpecimenSourceNameOrCode().getIdentifier().setValue(patientRecord.getSpecimenSource()); //patientrecord.getSpecimen_source
				obr.getOrderingProvider(0).getIDNumber().setValue(patientRecord.getProviderId()); //patientrecord.getProvider_id
				//String orderingPhyName = patientRecord.getOrderingPhyName();
				//log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
				if(StringUtils.contains(orderingPhyName, "^")){
					String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
					if((phyNameArray != null) && (phyNameArray.length >= 2)){
						//log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
						//log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
						obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
						obr.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name									
					}
				}else{
					obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(orderingPhyName); //last name
				}							
				obr.getOrderCallbackPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code
				//obr.getOrderCallbackPhoneNumber(0).getTelephoneNumber().setValue(patientRecord.getFacilityPhoneNumber()); //patientrecord.getFacility_phone_number
				
				obr.getOrderCallbackPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
				obr.getOrderCallbackPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
				obr.getOrderCallbackPhoneNumber(0).getTelecommunicationUseCode().setValue("PRN");
				obr.getOrderCallbackPhoneNumber(0).getCountryCode().setValue("1");
				obr.getResultsRptStatusChngDateTime().getTime().setValue(patientRecord.getResRprtStatusChngDtTime()); //patientrecord.getRes_rprt_status_chng_dt_time
				obr.getDiagnosticServSectID().setValue("LAB"); // NEED REQUIREMENT FROM QA
				obr.getResultStatus().setValue(patientRecord.getRequisitionStatus()); //patientrecord.getRequisition_status


				if(obxRec.getSubTestCode().equals("329G") ||
				 obxRec.getSubTestCode().equals("329M")) {
					String loinc_code [] = coding.get(obxRec.getSubTestCode() + "_Loinc").split("\\|");
					obr.getParentResult().getParentObservationIdentifier().getIdentifier().setValue(loinc_code[0]);
					; // NEED REQUIREMENT FROM QA
					obr.getParentResult().getParentObservationIdentifier().getText().setValue(loinc_code[1]);
					obr.getParentResult().getParentObservationIdentifier().getNameOfCodingSystem().setValue("LN");
				} else {
					obr.getParentResult().getParentObservationIdentifier().getIdentifier().setValue(obxRec.getLoincCode());
					; // NEED REQUIREMENT FROM QA
					obr.getParentResult().getParentObservationIdentifier().getText().setValue(obxRec.getLoincName());
					obr.getParentResult().getParentObservationIdentifier().getNameOfCodingSystem().setValue("LN");
				}
				obr.getParent(); // NEED REQUIREMENT FROM QA
				obr.getReasonForStudy(); // NEED REQUIREMENT FROM QA
				


				List<OBXRecord> obxList = patientRecord.getObxList();
				if(obxList != null){
					String isolateName = null;
					int ctr = 0;
/*
					List<OBXRecord> seqObxList = null;
				
					TreeMap<String, OBXRecord> seqObxMap = new TreeMap<String, OBXRecord>();
					for(OBXRecord obxr : obxList){
						seqObxMap.put((obxr.getSequenceNo() + obxr.getCompoundTestCode() + obxr.getSubTestCode()), obxr);
					}					

					if(seqObxMap.size() > 0){
						seqObxList = new ArrayList<OBXRecord>(seqObxMap.values());
					}
*/					
					
					
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

					//for(int i = 0; i < seqObxList.size(); i++){
					for(int i = 0; i < obxList.size(); i++){
						ctr += 1;
						//OBXRecord obxRecord = seqObxList.get(i);
						OBXRecord obxRecord = obxList.get(i);
						NTERecord nteRecord = nteList.get(i);

						if(obxRecord.getSourceOfComment().equalsIgnoreCase("PT-ANALYT") ) {
							isolateName = obxRecord.getSeqResultName().trim();
						}

						ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(i);

						String tnr = obxRecord.getTextualNumResult();

						if((obxRecord.getSubTestCode() != null) &&
								(obxRecord.getSeqResultName() != null) &&
								(obxRecord.getTextualNumResult() != null) &&
								(!obxRecord.getTextualNumResult().contains("\n"))) {
							OBX obx = observation.getOBX();
							log.warn("toHL7Dto(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));
							obx.getSetIDOBX().setValue(String.valueOf(ctr));
							//obx.getValueType().setValue(obxRecord.getTestResultType()); //obxrecord.getTestresult_type
							obx.getValueType().setValue("SN");

							if ((obxRecord.getLoincCode() != null) && (obxRecord.getLoincCode().length() > 0)) {
								obx.getObservationIdentifier().getIdentifier().setValue(obxRecord.getLoincCode());
								obx.getObservationIdentifier().getText().setValue(obxRecord.getLoincName());
								obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
							}

							if (obxRecord.getSubTestCode().equals("329G") ||
									obxRecord.getSubTestCode().equals("329M")) {
								obx.getValueType().setValue("CE");

								String loinc_code[] = coding.get(obxRecord.getSubTestCode() + "_Loinc").split("\\|");

								obx.getObservationIdentifier().getIdentifier().setValue(loinc_code[0]);
								obx.getObservationIdentifier().getText().setValue(loinc_code[1]);
								obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
							}
							if ((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)) {
								StringBuilder builder = new StringBuilder();
								builder.append(obxRecord.getSubTestCode()).append("-1-001");
								obx.getObservationIdentifier().getAlternateIdentifier().setValue(builder.toString());
								obx.getObservationIdentifier().getAlternateText().setValue(obxRecord.getSeqTestName());
								obx.getObservationIdentifier().getNameOfAlternateCodingSystem().setValue("L");
							}

							String testResultType = null;

							if (obxRecord.getSubTestCode().equals("329G") ||
									obxRecord.getSubTestCode().equals("329M")) {
								testResultType = "CE";
							} else {
								testResultType = obxRecord.getTestResultType();
							}
							//if("ST".equals(testResultType)){
							//	ST st = new ST(oru); //obx.testResultType could be ST or NM
							//	st.setValue(obxRecord.getTextualNumResult());
							//	Varies value = obx.getObservationValue(0);
							//	value.setData(st);
							//}else if("NM".equals(testResultType)){
							//	NM nm = new NM(oru);
							//	nm.setValue(obxRecord.getTextualNumResult());
							//	Varies value = obx.getObservationValue(0);
							//	value.setData(nm);
							//}else{
							//	ST st = new ST(oru); //obx.testResultType could be ST or NM
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
									ST st = new ST(oru); //obx.testResultType could be ST or NM
									st.setValue(txtNumRes);
									Varies value = obx.getObservationValue(0);
									value.setData(st);


									// add organism name to observation val
									String microOrgName = obxRecord.getMicroOrganismName();
									if(microOrgName != null){
										if((txtNumRes != null) && (txtNumRes.indexOf(microOrgName) == -1)){
											st = new ST(oru); //obx.testResultType could be ST or NM
											st.setValue(microOrgName);
											value = obx.getObservationValue(1);
											value.setData(st);
										}
									}

								}else{
									SN sn = new SN(oru);
									if(op != null){
										sn.getComparator().setValue(op);
									}
									sn.getNum1().setValue("1");
									//sn.getSeparatorOrSuffix().setValue(":");
									sn.getSeparatorSuffix().setValue(":");
									if(val != null){
										sn.getNum2().setValue(val);
									}
									//sn.getNum2().setValue(obxRecord.getTextualNumResult());
									Varies value = obx.getObservationValue(0);
									value.setData(sn);
								}
							}else if("NM".equals(testResultType)) {
								//NM nm = new NM(oru);
								//nm.setValue(obxRecord.getTextualNumResult());
								SN sn = new SN(oru);
								if (op != null) {
									sn.getComparator().setValue(op);
								}
								sn.getNum1().setValue("1");
								//sn.getSeparatorOrSuffix().setValue(":");
								sn.getSeparatorSuffix().setValue(":");
								if (val != null) {
									sn.getNum2().setValue(val);
								}
								//sn.getNum1().setValue(obxRecord.getTextualNumResult());
								Varies value = obx.getObservationValue(0);
								//value.setData(nm);
								value.setData(sn);
							} else if ("CE".equals(testResultType)){
								String values [] = coding.get(obxRecord.getTextualNumResult()).split("\\|");
								CE ce = new CE(oru);
								ce.getCe1_Identifier().setValue(values[0]);
								ce.getCe2_Text().setValue(values[1]);
								ce.getCe3_NameOfCodingSystem().setValue(values[2]);
								ce.getCe4_AlternateIdentifier().setValue(values[3]);
								ce.getCe5_AlternateText().setValue(values[4]);
								ce.getCe6_NameOfAlternateCodingSystem().setValue(values[5]);

								Varies value = obx.getObservationValue(0);
								value.setData(ce);
							}else{
								//ST st = new ST(oru); //obx.testResultType could be ST or NM
								//st.setValue(obxRecord.getTextualNumResult());
								SN sn = new SN(oru);
								if(op != null){
									sn.getComparator().setValue(op);
								}
								sn.getNum1().setValue("1");
								//sn.getSeparatorOrSuffix().setValue(":");
								sn.getSeparatorSuffix().setValue(":");
								if(val != null){
									sn.getNum2().setValue(val);
								}
								//sn.getNum1().setValue(obxRecord.getTextualNumResult());
								Varies value = obx.getObservationValue(0);
								//value.setData(st);
								value.setData(sn);
							}
							obx.getUnits().getIdentifier().setValue(obxRecord.getUnits());
							obx.getReferencesRange().setValue(obxRecord.getRefRange());
							obx.getAbnormalFlags(0).setValue(obxRecord.getAbnormalFlag());
							obx.getObservationResultStatus().setValue(obxRecord.getResultStatus());
							StringBuilder releaseDateTimeBuilder = new StringBuilder();
							releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
							obx.getDateTimeOfTheObservation().getTime().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
							obx.getDateTimeOfTheAnalysis().getTime().setValue(releaseDateTimeBuilder.toString());
							log.warn("getMessage(): requisition id: " + (patientRecord.getOrderNumber() == null ? "NULL" : patientRecord.getOrderNumber()));
							log.warn("getMessage(): sendingFacilityId: " + (sendingFacilityId == null ? "NULL" : sendingFacilityId));
							log.warn("getMessage(): fileSendingFacility: " + (fileSendingFacility == null ? "NULL" : fileSendingFacility));
							obx.getPerformingOrganizationName().getOrganizationIdentifier().setValue(sendingFacilityId);
							obx.getPerformingOrganizationName().getOrganizationName().setValue(fileSendingFacility);
							obx.getPerformingOrganizationName().getOrganizationNameTypeCode().setValue(sendingFacilityIdType);

							//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
							//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
							if((infoSpectraAddress2 != null) && (infoSpectraAddress2.length() > 0)){
								StringBuilder performOrgAddBuilder = new StringBuilder();
								performOrgAddBuilder.append(infoSpectraAddress).append(" ").append(infoSpectraAddress2);
								//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(performOrgAddBuilder.toString());
								obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(performOrgAddBuilder.toString());
							}else{
								//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
								obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
							}
							obx.getPerformingOrganizationAddress().getCity().setValue(infoSpectraCity);
							obx.getPerformingOrganizationAddress().getStateOrProvince().setValue(infoSpectraState);
							obx.getPerformingOrganizationAddress().getZipOrPostalCode().setValue(infoSpectraZip);
/*							
							String[] arr = medicalDirector.split("\\s");
							String mdFirstName = null;
							String mdLastName = null;
							for(int a = 0; a < arr.length; a++){
								if(arr[a].indexOf(",") != -1){
									arr[a] = arr[a].substring(0, arr[a].indexOf(","));
								}
								log.warn("getMessage(): a[" + String.valueOf(a) + "]: " + (arr[a] == null ? "NULL" : arr[a]));
							}
							if(medicalDirector.indexOf(" MD") != -1){
								mdFirstName = arr[0];
								mdLastName = arr[1];
							}else if(medicalDirector.indexOf("Dr.") != -1){
								mdFirstName = arr[1];
								mdLastName = arr[2];
							}
*/							
							obx.getPerformingOrganizationMedicalDirector().getFamilyName().getSurname().setValue(mdLastName);
							obx.getPerformingOrganizationMedicalDirector().getGivenName().setValue(mdFirstName);
							obx.getPerformingOrganizationMedicalDirector().getDegreeEgMD().setValue(mdDoctor);
							obx.getObservationMethod(0).getIdentifier().setValue("FMC");

							//NTE nte = observation.getNTE();
							//nte.getSetIDNTE().setValue(String.valueOf(ctr));
							//nte.getSourceOfComment().setValue(obxRecord.getSourceOfComment());
							//nte.getComment(0).setValue(nteRecord.getTestNteComment());							
							
						}else if(obxRecord.getTextualNumResult().contains("\n")){
							String[] obxTextNumResultArray = obxRecord.getTextualNumResult().split("\n");
							if(obxTextNumResultArray != null) {
								for(int j = 0; j < obxTextNumResultArray.length; j++) {
									if(!(obxTextNumResultArray[j].trim().equalsIgnoreCase(""))) {
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
										//	ST st = new ST(oru); //obx.testResultType could be ST or NM
										//	//st.setValue(obxRecord.getTextualNumResult());
										//	st.setValue(obxTextNumResultArray[j].trim());
										//	Varies value = obx.getObservationValue(0);
										//	value.setData(st);
										//}else if("NM".equals(testResultType)){
										//	NM nm = new NM(oru);
										//	//nm.setValue(obxRecord.getTextualNumResult());
										//	nm.setValue(obxTextNumResultArray[j].trim());
										//	Varies value = obx.getObservationValue(0);
										//	value.setData(nm);
										//}else{
										//	ST st = new ST(oru); //obx.testResultType could be ST or NM
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
												ST st = new ST(oru); //obx.testResultType could be ST or NM
												st.setValue(txtNumRes);
												Varies value = obx.getObservationValue(0);
												value.setData(st);


												// add organism name to observation val
												String microOrgName = obxRecord.getMicroOrganismName();
												if(microOrgName != null){
													if((txtNumRes != null) && (txtNumRes.indexOf(microOrgName) == -1)){
														st = new ST(oru); //obx.testResultType could be ST or NM
														st.setValue(microOrgName);
														value = obx.getObservationValue(1);
														value.setData(st);
													}
												}

											}else{
												SN sn = new SN(oru);
												if(op != null){
													sn.getComparator().setValue(op);
												}
												sn.getNum1().setValue("1");
												//sn.getSeparatorOrSuffix().setValue(":");
												sn.getSeparatorSuffix().setValue(":");
												if(val != null){
													sn.getNum2().setValue(val);
												}
												//sn.getNum2().setValue(obxRecord.getTextualNumResult());
												Varies value = obx.getObservationValue(0);
												value.setData(sn);
											}
										}else if("NM".equals(testResultType)){
											//NM nm = new NM(oru);
											//nm.setValue(obxRecord.getTextualNumResult());
											SN sn = new SN(oru);
											if(op != null){
												sn.getComparator().setValue(op);
											}
											sn.getNum1().setValue("1");
											//sn.getSeparatorOrSuffix().setValue(":");
											sn.getSeparatorSuffix().setValue(":");
											if(val != null){
												sn.getNum2().setValue(val);
											}
											//sn.getNum1().setValue(obxRecord.getTextualNumResult());
											Varies value = obx.getObservationValue(0);
											//value.setData(nm);
											value.setData(sn);
										}else{
											//ST st = new ST(oru); //obx.testResultType could be ST or NM
											//st.setValue(obxRecord.getTextualNumResult());
											SN sn = new SN(oru);
											if(op != null){
												sn.getComparator().setValue(op);
											}
											sn.getNum1().setValue("1");
											//sn.getSeparatorOrSuffix().setValue(":");
											sn.getSeparatorSuffix().setValue(":");
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
										obx.getObservationResultStatus().setValue(obxRecord.getResultStatus());
										StringBuilder releaseDateTimeBuilder = new StringBuilder();
										releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
										obx.getDateTimeOfTheObservation().getTime().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
										obx.getDateTimeOfTheAnalysis().getTime().setValue(releaseDateTimeBuilder.toString());
										log.warn("getMessage(): requisition id: " + (patientRecord.getOrderNumber() == null ? "NULL" : patientRecord.getOrderNumber()));
										log.warn("getMessage(): sendingFacilityId: " + (sendingFacilityId == null ? "NULL" : sendingFacilityId));
										log.warn("getMessage(): fileSendingFacility: " + (fileSendingFacility == null ? "NULL" : fileSendingFacility));
										obx.getPerformingOrganizationName().getOrganizationIdentifier().setValue(sendingFacilityId);
										obx.getPerformingOrganizationName().getOrganizationName().setValue(fileSendingFacility);
										obx.getPerformingOrganizationName().getOrganizationNameTypeCode().setValue(sendingFacilityIdType);

										//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
										//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
										if((infoSpectraAddress2 != null) && (infoSpectraAddress2.length() > 0)){
											StringBuilder performOrgAddBuilder = new StringBuilder();
											performOrgAddBuilder.append(infoSpectraAddress).append(" ").append(infoSpectraAddress2);
											//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(performOrgAddBuilder.toString());
											obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(performOrgAddBuilder.toString());
										}else{
											//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
											obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
										}
										obx.getPerformingOrganizationAddress().getCity().setValue(infoSpectraCity);
										obx.getPerformingOrganizationAddress().getStateOrProvince().setValue(infoSpectraState);
										obx.getPerformingOrganizationAddress().getZipOrPostalCode().setValue(infoSpectraZip);
/*										
										String[] arr = medicalDirector.split("\\s");
										String mdFirstName = null;
										String mdLastName = null;
										for(int a = 0; a < arr.length; a++){
											if(arr[a].indexOf(",") != -1){
												arr[a] = arr[a].substring(0, arr[a].indexOf(","));
											}
										}
										if(medicalDirector.indexOf(" MD") != -1){
											mdFirstName = arr[0];
											mdLastName = arr[1];
										}else if(medicalDirector.indexOf("Dr.") != -1){
											mdFirstName = arr[1];
											mdLastName = arr[2];
										}
*/										
										obx.getPerformingOrganizationMedicalDirector().getFamilyName().getSurname().setValue(mdLastName);
										obx.getPerformingOrganizationMedicalDirector().getGivenName().setValue(mdFirstName);														
										obx.getObservationMethod(0).getIdentifier().setValue("FMC");

										//NTE nte = observation.getNTE();
										//nte.getSetIDNTE().setValue(String.valueOf(ctr));
										//nte.getSourceOfComment().setValue(obxRecord.getSourceOfComment());
										//nte.getComment(0).setValue(nteRecord.getTestNteComment());
										
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
				
				SPM spm = orderObservation.getSPECIMEN().getSPM();
				spm.getSetIDSPM().setValue("1");
				spm.getSpecimenID().getPlacerAssignedIdentifier().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
				spm.getSpecimenType().getIdentifier().setValue(patientRecord.getSpecimenSource());
				spm.getSpecimenType().getText().setValue(patientRecord.getSpecimenSource());
				spm.getSpecimenType().getNameOfCodingSystem().setValue(""); // NEED REQUIREMENT FROM QA
				spm.getSpecimenCollectionDateTime().getRangeStartDateTime().getTime().setValue(patientRecord.getCollectionDate());
				spm.getSpecimenCollectionDateTime().getRangeEndDateTime().getTime().setValue(patientRecord.getCollectionDate());
				spm.getSpecimenReceivedDateTime().getTime().setValue(patientRecord.getSpecimenReceiveDate());

/*
				//String hapiCustomPackage = this.configService.getString("hapi.custom.package");
				//ModelClassFactory cmf = new CustomModelClassFactory(ip.getHapiCustomPackage());
				ModelClassFactory cmf = new CustomModelClassFactory(hapiCustomPackage);
				HapiContext context = new DefaultHapiContext();
				context.setModelClassFactory(cmf);

				//HapiContext context = new DefaultHapiContext();
				//HapiContext context = this.createHapiContext();
				Parser parser = context.getPipeParser();
				//hl7String = parser.encode(oru);

				//hl7Builder.append(parser.encode(oru));
				zruBuilder.append(parser.encode(oru));
*/
			}catch(IOException ioe){
				log.error(ioe.getMessage());
				ioe.printStackTrace();
			}catch(HL7Exception hl7e){
				log.error(hl7e.getMessage());
				hl7e.printStackTrace();
			}
		}
		
		return oru;
	}

}
