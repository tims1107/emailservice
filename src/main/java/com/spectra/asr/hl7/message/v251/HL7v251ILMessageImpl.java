package com.spectra.asr.hl7.message.v251;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Varies;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v251.datatype.SN;
import ca.uhn.hl7v2.model.v251.datatype.ST;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.*;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.ParserConfiguration;
import ca.uhn.hl7v2.util.Terser;
import com.spectra.asr.dto.filter.LOINC_CODE;
import com.spectra.asr.dto.patient.NTERecord;
import com.spectra.asr.dto.patient.OBXRecord;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.snomed.SNOMED_MASTER;
import com.spectra.asr.hl7.message.HL7Message;
import com.spectra.controller.MicroController;
import com.spectra.repo;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import com.spectra.utils.HL7_Hapi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.spectra.asr.utils.file.writer.FileUtil.getMSHControlNum;
@Slf4j
public class HL7v251ILMessageImpl implements HL7Message {

	private static final String MSH3_2 = "2.16.840.1.114222.4.3.22.29.7.1";
	private static final String MSH3_3 = "ISO";
	private static final String MSH4_2 = "31D0961672";
	private static final String MSH4_3 = "CLIA";
	private static final String MSH5_1 = "NCDPH NCEDSS";
	private static final String MSH5_2 = "2.16.840.1.113883.3.591.3.1";

	private static final String MSH6_1 = "NCDPH EDS";
	private static final String MSH6_2 = "2.16.840.1.113883.3.591.1.1";

	private static final String MSH21_1 = "PHLabReport-NoAck";
	private static final String MSH21_2 = "ELR_Receiver";
	private static final String MSH21_3 = "2.16.840.1.114222.4.10.3";
	private static final String MSH21_4 = "ISO";

	private static final String ORC12_9_2 = "2.16.840.1.114222.4.3.22.29.7.1";
	private static final String MSH1_1 = "^~" + "\\" + "&";

	private MicroController microController;

	private ORU_R01 oru;
	private ORU_R01_ORDER_OBSERVATION orderObservation;

	private void _makeSPM(PatientRecord patientRecord,OBXRecord obxRecord,int obxrep){
		oru = HL7_Hapi.spmChildSegment(patientRecord, oru, obxrep,1,orderObservation,obxRecord);
	}

	private  void _makeOBX(PatientRecord patientRecord,OBXRecord obxRecord,int obxrep){

		oru = HL7_Hapi.obxChildSegment(patientRecord, oru, obxrep,1,orderObservation,obxRecord);
	}
	@Override
	public AbstractMessage getMessage(PatientRecord patientRecord, Map<String, Object> paramMap) {
//		try {
//			microController = new MicroController(new String[]{"db.json", null, null, null, "PROD"});
//		} catch (IOException e) {
//			log.error("MicroController error: {}", e.getMessage());
//		}
		patientRecord.getObxList().stream()
				.forEach(System.out::println);

		//System.exit(0);

		//AbstractMessage msg = null;
		//ORU_R01 oru = null;

		if (patientRecord != null) {
			String performingLabId = patientRecord.getPerformingLabId();
			Integer labFk = patientRecord.getLabFk();

			String fileSendingFacility = null;
			String sendingApplication = null;
			String sendingFacilityId = null;

			String infoSpectraName = null;
			String infoSpectraAddress = null;
			String infoSpectraAddress2 = null;
			String infoSpectraPhone = null;
			String infoSpectraCity = null;
			String infoSpectraZip = null;
			String infoSpectraState = null;
			String medicalDirector = null;

			if (patientRecord.getPerformingLabId().indexOf("SE") != -1) {
				infoSpectraName = ApplicationProperties.getProperty("spectra.east.info.name");
				infoSpectraAddress = ApplicationProperties.getProperty("spectra.east.info.address");
				infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.east.info.address2");
				infoSpectraPhone = ApplicationProperties.getProperty("spectra.east.info.phone");
				medicalDirector = patientRecord.getMedicalDirector();


			} else if (patientRecord.getPerformingLabId().indexOf("SH") != -1) {
				infoSpectraName = ApplicationProperties.getProperty("spectra.south.info.name");
				infoSpectraAddress = ApplicationProperties.getProperty("spectra.south.info.address");
				infoSpectraAddress2 = ApplicationProperties.getProperty("spectra.south.info.address2");
				infoSpectraPhone = ApplicationProperties.getProperty("spectra.south.info.phone");
				medicalDirector = patientRecord.getMedicalDirector();

			}

			String[] addr2Array = infoSpectraAddress2.split("\\s");
			if (addr2Array.length == 3) {
				infoSpectraCity = addr2Array[0].substring(0, addr2Array[0].indexOf(","));
				infoSpectraState = addr2Array[1];
				infoSpectraZip = addr2Array[2];
			}

			String mshSendingApplication = (String) paramMap.get("mshSendingApplication");
			String mshFileSendingFacility = (String) paramMap.get("mshFileSendingFacility");
			String mshSendingFacilityId = (String) paramMap.get("mshSendingFacilityId");
			String sendingFacilityIdType = (String) paramMap.get("sendingFacilityIdType");
			DateFormat dateFormat = (DateFormat) paramMap.get("dateFormat");
			DateFormat dateFormat2 = (DateFormat) paramMap.get("dateFormat2");


			String fileReceivingApplication = (String) paramMap.get("fileReceivingApplication");
			String fileReceivingFacility = (String) paramMap.get("fileReceivingFacility");

			String msgProfileId = (String) paramMap.get("msgProfileId");
			log.warn("getMessage(): msgProfileId: " + (msgProfileId == null ? "NULL" : msgProfileId));

			log.warn("getMessage(): medicalDirector: " + (medicalDirector == null ? "NULL" : medicalDirector));
			String mdFirstName = null;
			String mdLastName = null;
			if (medicalDirector.indexOf("M.D.") != -1) {
				medicalDirector = medicalDirector.substring("M.D.".length());
				if (medicalDirector.indexOf(".") != -1) {
					mdFirstName = "Alex"; //medicalDirector.substring(0, (medicalDirector.lastIndexOf(".") + 1));
					mdLastName = "Ryder";//medicalDirector.substring((medicalDirector.lastIndexOf(".") + 1));
				}
			}
			if (medicalDirector.indexOf(" MD") != -1) {
				medicalDirector = medicalDirector.substring(0, (medicalDirector.indexOf(" MD") + 1));
				mdFirstName = medicalDirector.substring(0, (medicalDirector.indexOf(" ") + 1));
				mdLastName = medicalDirector.substring(medicalDirector.indexOf(" "));
				if (mdLastName.indexOf(",") != -1) {
					mdLastName = mdLastName.replaceAll(",", "");
				}
			}

			if (medicalDirector.indexOf("Dr") != -1) {

				mdFirstName = "C.O.";
				mdLastName = "Burdick";

			}
			log.warn("getMessage(): mdFirstName: " + (mdFirstName == null ? "NULL" : mdFirstName));
			log.warn("getMessage(): mdLastName: " + (mdLastName == null ? "NULL" : mdLastName));


			if (patientRecord.getPerformingLabId().indexOf("SW") != -1) {
				mdFirstName = "C.O.";
				mdLastName = "Burdick";
			} else {
				mdFirstName = mdFirstName.trim();
				mdLastName = mdLastName.trim();
			}


			if (performingLabId.indexOf("SE") != -1) {
				//east address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
			} else if (performingLabId.indexOf("OUTSEND") != -1) {
				//east address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.east.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.east.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.east.msh.4.2");
			} else if (performingLabId.indexOf("SH") != -1) {
				//east address
				fileSendingFacility = ApplicationProperties.getProperty("spectra.south.fhs.4");
				sendingApplication = ApplicationProperties.getProperty("spectra.south.msh.3");
				sendingFacilityId = ApplicationProperties.getProperty("spectra.south.msh.4.2");
			}


			// Init ORU
			oru = new ORU_R01();

			// new Terser
			HapiContext context = new DefaultHapiContext();
			ParserConfiguration pc = context.getParserConfiguration();
			Message hapiMsg = null;

			Parser p = context.getPipeParser();

			Terser terser = new Terser(oru);


			// **** Begin MSH ****

			oru = HL7_Hapi.mshSegment(patientRecord, oru);


			// Begin SFT

			// SFT Segment
			oru = HL7_Hapi.sftSegment(patientRecord, oru);

			//Begin PID
			patientRecord.setPatientName("LastName, FirstName");
			oru = HL7_Hapi.pidSegment(patientRecord, oru);


			// Start ORC
			OBXRecord obxRec = patientRecord.getObxList().get(0);

			orderObservation = oru.getPATIENT_RESULT(0).getORDER_OBSERVATION();

			oru = HL7_Hapi.orcSegment(patientRecord, oru);

			oru = HL7_Hapi.obrSegment(patientRecord, oru, 0,orderObservation);

			oru = HL7_Hapi.obxSegment(patientRecord, oru, 0,0,orderObservation);

			orderObservation = oru.getPATIENT_RESULT(1).getORDER_OBSERVATION();

			oru = HL7_Hapi.obrSegment(patientRecord,  oru,1,orderObservation);
			AtomicInteger obxrep = new AtomicInteger(0);

			patientRecord.getObxList().stream()
					.filter(t -> t.getSequenceNo() > 0)
					//.limit(1)
					.forEach(t -> {_makeOBX(patientRecord,t,obxrep.getAndIncrement());});

			_makeSPM(patientRecord,obxRec,1);


			//oru = HL7_Hapi.obxChildSegment(patientRecord, oru, 0,1,orderObservation);
			//oru = HL7_Hapi.obxChildSegment(patientRecord, oru, 1,1,orderObservation);

//			orderObservation = oru.getPATIENT_RESULT(2).getORDER_OBSERVATION();
//			oru = HL7_Hapi.obxChildSegment(patientRecord, oru, 0,2,orderObservation);

//			oru = HL7_Hapi.obrSegment(patientRecord,  oru,2);
//			oru = HL7_Hapi.obxChildSegment(patientRecord, oru, 0,2);


//				String orderingPhyName = patientRecord.getOrderingPhyName();
//				log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
//				if (StringUtils.contains(orderingPhyName, "^")) {
//					String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
//					if ((phyNameArray != null) && (phyNameArray.length >= 2)) {
//						log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
//						log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
//						//orc.getOrderingProvider(0).getFamilyName().getSurname().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
//						//orc.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name
//					}
//				} else {
//					//orc.getOrderingProvider(0).getFamilyName().getSurname().setValue(orderingPhyName); //last name
//				}

//				List<OBXRecord> obxList = patientRecord.getObxList().stream()
//						.filter(t -> t.getSeqResultName().matches("Isolate(.*)"))
//						.collect(Collectors.toList());
//
//				List<OBXRecord> obxList = patientRecord.getObxList();
////				OBXRecord or = obxList.stream()
////						.filter(t -> t.getSeqResultName().matches("Isolate(.*)"))
////						.findFirst().get();
//
////				obxList.stream()
////						.map(t -> _getDrugs(t))
////						.forEach(System.out::println);
//
//				// Get Loinccode
//				LOINC_CODE lc = getLoincCodes(obxList.get(0).getMicroOrganismName(),"ORG");
//
//
//
////				LOINC_CODE lc = loinclist.stream()
////						.findFirst().get();
//
//				//log.info("Loinc Code: {}",lc);

			// *** OBR ***

			//OBR obr = orderObservation.getOBR();
			//log.warn("toHL7Dto(): obr: " + (obr == null ? "NULL" : obr.getName()));
//				obr.getSetIDOBR().setValue("1");
//
//				// OBR 2
//				obr.getPlacerOrderNumber().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
////				obr.getPlacerOrderNumber().getNamespaceID().setValue(fileSendingFacility);
////				obr.getPlacerOrderNumber().getUniversalID().setValue(sendingFacilityId);
////				obr.getPlacerOrderNumber().getUniversalIDType().setValue(sendingFacilityIdType);
//
//				// OBR 3
//				obr.getFillerOrderNumber().getEntityIdentifier().setValue(patientRecord.getOrderNumber()); //patientrecord.getOrder_number
////				obr.getFillerOrderNumber().getNamespaceID().setValue(fileSendingFacility);
////				obr.getFillerOrderNumber().getUniversalID().setValue(sendingFacilityId);
////				obr.getFillerOrderNumber().getUniversalIDType().setValue(sendingFacilityIdType);
//
//
//
//				// OBR 4
//
//				if(obxRec.getMicroOrganismName() == null) {
//					obr.getObr4_UniversalServiceIdentifier().getCe1_Identifier().setValue(obxRec.getLoincCode());
//					obr.getObr4_UniversalServiceIdentifier().getCe2_Text().setValue(obxRec.getLoincName());
//				} else {
//					obr.getObr4_UniversalServiceIdentifier().getCe1_Identifier().setValue(lc.getLoinccode());
//					obr.getObr4_UniversalServiceIdentifier().getCe2_Text().setValue(lc.getLoincname());
//				}
//
//
//
//				obr.getObr4_UniversalServiceIdentifier().getCe3_NameOfCodingSystem().setValue("LN");
//
//				obr.getObr4_UniversalServiceIdentifier().getCe4_AlternateIdentifier().setValue(obxRec.getCompoundTestCode()); //obxrecord.getCompound_test_code
//				obr.getObr4_UniversalServiceIdentifier().getCe5_AlternateText().setValue(obxRec.getSeqTestName()); //obxrecord.getSeq_test_name
//				obr.getObr4_UniversalServiceIdentifier().getCe6_NameOfAlternateCodingSystem().setValue("L");
//
//				// OBR 7
//				//obr.getObservationDateTime().getTime().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
//				obr.getRelevantClinicalInformation().setValue("Recurring patient"); // NEED REQUIREMENT FROM QA
//				obr.getSpecimenActionCode().setValue("G");
//				obr.getSpecimenSource().getSpecimenSourceNameOrCode().getIdentifier().setValue(patientRecord.getSpecimenSource()); //patientrecord.getSpecimen_source
//				obr.getOrderingProvider(0).getIDNumber().setValue(patientRecord.getProviderId()); //patientrecord.getProvider_id
//				//String orderingPhyName = patientRecord.getOrderingPhyName();
//				//log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
//				if(StringUtils.contains(orderingPhyName, "^")){
//					String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
//					if((phyNameArray != null) && (phyNameArray.length >= 2)){
//						//log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
//						//log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
//						obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
//						obr.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name
//					}
//				}else{
//					obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(orderingPhyName); //last name
//				}
//				obr.getOrderCallbackPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code
//				//obr.getOrderCallbackPhoneNumber(0).getTelephoneNumber().setValue(patientRecord.getFacilityPhoneNumber()); //patientrecord.getFacility_phone_number
//
//				obr.getOrderCallbackPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
//				obr.getOrderCallbackPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
//				obr.getOrderCallbackPhoneNumber(0).getTelecommunicationUseCode().setValue("WPN");
//				obr.getOrderCallbackPhoneNumber(0).getCountryCode().setValue("1");
//
//				obr.getResultsRptStatusChngDateTime().getTime().setValue(patientRecord.getResRprtStatusChngDtTime()); //patientrecord.getRes_rprt_status_chng_dt_time
//				obr.getDiagnosticServSectID().setValue("LAB"); // NEED REQUIREMENT FROM QA
//				obr.getResultStatus().setValue(patientRecord.getRequisitionStatus()); //patientrecord.getRequisition_status
//
//
////				if(obxRec.getSubTestCode().equals("329G") ||
////				 obxRec.getSubTestCode().equals("329M")) {
////					String loinc_code [] = coding.get(obxRec.getSubTestCode() + "_Loinc").split("\\|");
////					obr.getParentResult().getParentObservationIdentifier().getIdentifier().setValue(loinc_code[0]);
////					; // NEED REQUIREMENT FROM QA
////					obr.getParentResult().getParentObservationIdentifier().getText().setValue(loinc_code[1]);
////					obr.getParentResult().getParentObservationIdentifier().getNameOfCodingSystem().setValue("LN");
////				} else {
////					obr.getParentResult().getParentObservationIdentifier().getIdentifier().setValue(obxRec.getLoincCode());
////					; // NEED REQUIREMENT FROM QA
////					obr.getParentResult().getParentObservationIdentifier().getText().setValue(obxRec.getLoincName());
////					obr.getParentResult().getParentObservationIdentifier().getNameOfCodingSystem().setValue("LN");
////				}
//				obr.getParent(); // NEED REQUIREMENT FROM QA
//				obr.getReasonForStudy(); // NEED REQUIREMENT FROM QA
//
//				obr.getObr16_OrderingProvider(0).getAssigningAuthority().getNamespaceID().setValue("NPI");
//				obr.getObr16_OrderingProvider(0).getAssigningAuthority().getUniversalID().setValue(sendingFacilityId);
//				obr.getObr16_OrderingProvider(0).getAssigningAuthority().getUniversalIDType().setValue("CLIA");
//
//				obr.getObr16_OrderingProvider(0).getIdentifierTypeCode().setValue("NPI");
//
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-7",repo.convertDate(patientRecord.getCollectionDateformat()));
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-16-10","L");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-14",repo.convertDate(patientRecord.getCollectionDateformat()));
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-22",repo.convertDate(patientRecord.getReleaseDate()));
//
//
//
//				//terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBX-1","1");
//				//terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBX-2","SN");


			// *** OBX Segment ****


//				SNOMED_MASTER sm = null;
//
//
//				if(obxList != null){
//					String isolateName = null;
//					int ctr = 0;
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
//					TreeMap<String, List<OBXRecord>> obxOtcMap = new TreeMap<String,List<OBXRecord>>();
//					if(obxList != null){
//						for(OBXRecord obx : obxList){
//							String otc = obx.getCompoundTestCode();
//							if(obxOtcMap.containsKey(otc)){
//								List<OBXRecord> obxrList = obxOtcMap.get(otc);
//								obxrList.add(obx);
//							}else{
//								List<OBXRecord> obxrList = new ArrayList<OBXRecord>();
//								obxrList.add(obx);
//								obxOtcMap.put(otc, obxrList);
//							}
//						}
//						Collection<List<OBXRecord>> valueList = obxOtcMap.values();
//						obxList = new ArrayList<OBXRecord>();
//						for(List<OBXRecord> value : valueList){
//							for(OBXRecord obxr : value){
//								obxList.add(obxr);
//							}
//						}
//					}

			//
//					obxOtcMap.entrySet().stream()
//							.map(t -> t.getValue())
//							.flatMap(Collection::stream)
//							.forEach(System.out::println);


			TreeMap<Integer, OBXRecord> seqObxMap = new TreeMap<Integer, OBXRecord>();
//				if (obxList != null) {
//					for (OBXRecord obx : obxList) {
//						Integer seqNo = obx.getSequenceNo();
//						if (seqNo != null) {
//							if (seqObxMap.containsKey(seqNo)) {
//								Map.Entry<Integer, OBXRecord> lastEntry = seqObxMap.lastEntry();
//								Integer lastKey = lastEntry.getKey();
//								//seqNo = new Integer(seqNo.intValue() + 1);
//								seqNo = new Integer(lastKey.intValue() + 1);
//							}
//						} else {
//							seqNo = new Integer(obxList.size() + 1);
//						}
//						//seqObxMap.put(obx.getSequenceNo(), obx);
//						seqObxMap.put(seqNo, obx);
//					}
//					obxList = new ArrayList<OBXRecord>(seqObxMap.values());
//				}


			seqObxMap.entrySet().stream()
					.map(t -> t.getValue())
					.sorted(Comparator.comparing(OBXRecord::getSequenceNo))
					.forEach(System.out::println);

			//System.exit(0);

			List<NTERecord> nteList = patientRecord.getNteList();
			TreeMap<Integer, NTERecord> seqNteMap = new TreeMap<Integer, NTERecord>();
			if (nteList != null) {
				for (NTERecord nte : nteList) {
					Integer seqNo = nte.getSequenceNo();
					if (seqNo != null) {
						if (seqNteMap.containsKey(seqNo)) {
							Map.Entry<Integer, NTERecord> lastEntry = seqNteMap.lastEntry();
							Integer lastKey = lastEntry.getKey();
							//seqNo = new Integer(seqNo.intValue() + 1);
							seqNo = new Integer(lastKey.intValue() + 1);
						}
					} else {
						seqNo = new Integer(nteList.size() + 1);
					}
					//seqNteMap.put(nte.getSequenceNo(), nte);
					seqNteMap.put(seqNo, nte);
				}
				nteList = new ArrayList<NTERecord>(seqNteMap.values());
			}
			//end order obx and nte

			//for(int i = 0; i < seqObxList.size(); i++){
//					for(int i = 0; i < 1; i++){
//						ctr += 1;
//						//OBXRecord obxRecord = seqObxList.get(i);
//						OBXRecord obxRecord = obxList.get(i);
//						NTERecord nteRecord = nteList.get(i);
//
//						if(i > 0) {
//							lc = getLoincCodes(obxRecord.getSeqResultName(), "DRUG");
//						} else {
//							sm = getSnomedMaster(obxRecord.getMicroOrganismName(),null);
//						}


			// **************************************

//						if(obxRecord.getSourceOfComment().equalsIgnoreCase("PT-ANALYT") ) {
//							isolateName = obxRecord.getSeqResultName().trim();
//						}

			//ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(i);


//						String tnr = obxRecord.getTextualNumResult();

//						if((obxRecord.getSubTestCode() != null) &&
//								(obxRecord.getSeqResultName() != null) &&
//								(obxRecord.getTextualNumResult() != null) &&
//								(!obxRecord.getTextualNumResult().contains("\n"))) {
			//OBX obx = observation.getOBX();
			//log.warn("toHL7Dto(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));

//							String testResultType = null;

			//


//							// OBX 1
//							//obx.getSetIDOBX().setValue(String.valueOf(ctr));
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-1", Integer.toString(ctr));
//							//obx.getValueType().setValue(obxRecord.getTestResultType()); //obxrecord.getTestresult_type
//							// OBX 2
//
//
//							if(obxRecord.getMicroOrganismName() != null &&
//								i < 1){
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-2", "CE");
//								//obx.getValueType().setValue("CE");
//								testResultType = "CE";
//							} else {
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-2", "SN");
//								//obx.getValueType().setValue("SN");
//								testResultType = "SN";
//							}
//
//
//							// OBX 3
//
//							if(obxRecord.getMicroOrganismName() == null) {
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-1", obxRecord.getLoincCode());
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-2", obxRecord.getLoincName());
//
////								obx.getObservationIdentifier().getIdentifier().setValue(obxRecord.getLoincCode());
////								obx.getObservationIdentifier().getText().setValue(obxRecord.getLoincName());
////								obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
//							} else {
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-1", lc.getLoinccode());
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-2", lc.getLoincname());
////								obx.getObservationIdentifier().getIdentifier().setValue(lc.getLoinccode());
////								obx.getObservationIdentifier().getText().setValue(lc.getLoincname());
////								obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
//							}
//
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-3", "LN");
//
//							if ((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)) {
//								StringBuilder builder = new StringBuilder();
//								builder.append(obxRecord.getSubTestCode()).append("-1-001");
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-4", builder.toString());
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-5", obxRecord.getSeqTestName());
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-6", "L");
//
////								obx.getObservationIdentifier().getAlternateIdentifier().setValue(builder.toString());
////								obx.getObservationIdentifier().getAlternateText().setValue(obxRecord.getSeqTestName());
////								obx.getObservationIdentifier().getNameOfAlternateCodingSystem().setValue("L");
//							}
//
//
//
//							// OBX 4
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-4", "1");
//
//							// OBX 5
//							String op = null;
//							String val = null;
//							String result = obxRecord.getTextualNumResult();
//
//							if(result.indexOf("<") != -1){
//								op = result.substring(0, (result.indexOf("<") + 1));
//								val = result.substring((result.indexOf("<") + 1));
//							}else if(result.indexOf(">") != -1){
//								op = result.substring(0, (result.indexOf(">") + 1));
//								val = result.substring((result.indexOf(">") + 1));
//							}else if(result.indexOf("<=") != -1){
//								op = result.substring(0, (result.indexOf("<=") + 2));
//								val = result.substring((result.indexOf("<=") + 2));
//							}else if(result.indexOf(">=") != -1){
//								op = result.substring(0, (result.indexOf(">=") + 2));
//								val = result.substring((result.indexOf(">=") + 2));
//							}else if(result.indexOf("=") != -1){
//								op = result.substring(0, (result.indexOf("=") + 1));
//								val = result.substring((result.indexOf("=") + 1));
//							}else{
//								op = "=";
//								val = result.substring(2);
//							}
//
//
//							if(testResultType.equalsIgnoreCase("SN")) {
//
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", op);
//
//
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", val);
//							} else {
//								if(obxRecord.getMicroOrganismName() != null) {
//									terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", sm.getSnomedcode());
//									terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", sm.getPreferredname());
//									terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-3", "SCT");
//								} else {
////									terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", snomed.get(0));
////									terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", snomed.get(1));
////									terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-3", snomed.get(2));
//								}
//
//							}
//
//							// OBX 6 Units
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-1", obxRecord.getUnits());
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-2", "");
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-3", "UCUM");
//
//							// OBX 7 Reference Range
//							//obx.getReferencesRange().setValue(obxRecord.getRefRange());
//							//terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-7", obxRecord.getRefRange());
//
//							// OBX 8 Abnormal Flag
//
//
//							// OBX 11 Result Status
//							//obx.getObservationResultStatus().setValue(obxRecord.getResultStatus());
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-11", obxRecord.getResultStatus());
//
//							// OBX 14 Observation Date
////							StringBuilder releaseDateTimeBuilder = new StringBuilder();
////							releaseDateTimeBuilder.append(obxRecord.getReleaseDateTimeStr()).append(obxRecord.getReleaseTime().substring(8, 12));
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-14-1", repo.convertDate(obxRecord.getCollectionDateTime()));
//
//
//							// OBX 15 CLIA Number^Performing Lab^CLIA
////							obx.getObx15_ProducerSReference().getCe1_Identifier().setValue(sendingFacilityId);
////							obx.getObx15_ProducerSReference().getCe2_Text().setValue(fileSendingFacility);
////							obx.getObx15_ProducerSReference().getCe3_NameOfCodingSystem().setValue("CLIA");
//
//
//							// OBX-17 OrderMethod
//							//obx.getObservationMethod(i).getIdentifier().setValue("SPECTRA-LIS");
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-17-1", "SPECTRA-LIS");
//
//							//obx.getObx17_ObservationMethod(i).getText().setValue(obxRecord.getDeviceName());
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-17-2", obxRecord.getDeviceName());
//
//
//							// OBX 18
//
//
//
//							// OBX 19 DateTime of Analysis
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-19-1", repo.convertDate(obxRecord.getCollectionDateTime()));
//
//							//OBX 23
//							//obx.getPerformingOrganizationName().getOrganizationIdentifier().setValue(sendingFacilityId);
////							obx.getPerformingOrganizationName().getOrganizationName().setValue(fileSendingFacility);
////							obx.getPerformingOrganizationName().getOrganizationNameTypeCode().setValue("D");
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-1", fileSendingFacility);
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-2", "D");
//							//obx.getPerformingOrganizationName().getIdentifierTypeCode().setValue("XXX");
//
//							// OBX 23.6.1
////							obx.getPerformingOrganizationName().getAssigningAuthority().getNamespaceID().setValue("CLIA");
////							obx.getPerformingOrganizationName().getAssigningAuthority().getUniversalID().setValue("2.16.840.1.114222.4.3.22.29.7.1");
////							obx.getPerformingOrganizationName().getAssigningAuthority().getUniversalIDType().setValue("ISO");
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-6-1", "CLIA");
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-6-2", "2.16.840.1.114222.4.3.22.29.7.1");
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-6-3", "ISO");
//
//							// OBX 23.7
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-7", "XX");
//
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-10", sendingFacilityId);
////
//							//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
//							//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
//							if((infoSpectraAddress2 != null) && (infoSpectraAddress2.length() > 0)){
//								StringBuilder performOrgAddBuilder = new StringBuilder();
//								performOrgAddBuilder.append(infoSpectraAddress).append(" ").append(infoSpectraAddress2);
//								//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(performOrgAddBuilder.toString());
//								//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(performOrgAddBuilder.toString());
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-1", performOrgAddBuilder.toString());
//							}else{
//								//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
//								//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
//								terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-1", infoSpectraAddress);
//							}
////							obx.getPerformingOrganizationAddress().getCity().setValue(infoSpectraCity);
////							obx.getPerformingOrganizationAddress().getStateOrProvince().setValue(infoSpectraState);
////							obx.getPerformingOrganizationAddress().getZipOrPostalCode().setValue(infoSpectraZip);
//
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-3", infoSpectraCity);
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-4", infoSpectraState);
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-5", infoSpectraZip);
//
//
//
//
////							obx.getPerformingOrganizationMedicalDirector().getFamilyName().getSurname().setValue(mdLastName);
////							obx.getPerformingOrganizationMedicalDirector().getGivenName().setValue(mdFirstName);
//
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-25-2", mdLastName);
//							terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-25-3", mdFirstName);
//
//						}else if(obxRecord.getTextualNumResult().contains("\n")){
////							String[] obxTextNumResultArray = obxRecord.getTextualNumResult().split("\n");
////							if(obxTextNumResultArray != null) {
////								for(int j = 0; j < obxTextNumResultArray.length; j++) {
////									if(!(obxTextNumResultArray[j].trim().equalsIgnoreCase(""))) {
////										OBX obx = observation.getOBX();
////										log.warn("toHL7Dto(): obx " + "(" + String.valueOf(i) + "): " + (obx == null ? "NULL" : obx.getName()));
////										obx.getSetIDOBX().setValue(String.valueOf(ctr));
////										//obx.getValueType().setValue(obxRecord.getTestResultType()); //obxrecord.getTestresult_type
////										obx.getValueType().setValue("SN");
////										if((obxRecord.getLoincCode() != null) && (obxRecord.getLoincCode().length() > 0)){
////											obx.getObservationIdentifier().getIdentifier().setValue(obxRecord.getLoincCode());
////											obx.getObservationIdentifier().getText().setValue(obxRecord.getLoincName());
////											obx.getObservationIdentifier().getNameOfCodingSystem().setValue("LN");
////										}
////										if((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)){
////											StringBuilder builder = new StringBuilder();
////											builder.append(obxRecord.getSubTestCode()).append("-1-001");
////											obx.getObservationIdentifier().getAlternateIdentifier().setValue(builder.toString());
////											obx.getObservationIdentifier().getAlternateText().setValue(obxRecord.getSeqTestName());
////											obx.getObservationIdentifier().getNameOfAlternateCodingSystem().setValue("L");
////										}
////										String testResultType = obxRecord.getTestResultType();
////										//if("ST".equals(testResultType)){
////										//	ST st = new ST(oru); //obx.testResultType could be ST or NM
////										//	//st.setValue(obxRecord.getTextualNumResult());
////										//	st.setValue(obxTextNumResultArray[j].trim());
////										//	Varies value = obx.getObservationValue(0);
////										//	value.setData(st);
////										//}else if("NM".equals(testResultType)){
////										//	NM nm = new NM(oru);
////										//	//nm.setValue(obxRecord.getTextualNumResult());
////										//	nm.setValue(obxTextNumResultArray[j].trim());
////										//	Varies value = obx.getObservationValue(0);
////										//	value.setData(nm);
////										//}else{
////										//	ST st = new ST(oru); //obx.testResultType could be ST or NM
////										//	//st.setValue(obxRecord.getTextualNumResult());
////										//	st.setValue(obxTextNumResultArray[j].trim());
////										//	Varies value = obx.getObservationValue(0);
////										//	value.setData(st);
////										//}
////
////										//result
////										String op = null;
////										String val = null;
////										//String result = obxRecord.getTextualNumResult();
////										String result = obxTextNumResultArray[j];
////
////										if(result.indexOf("<") != -1){
////											op = result.substring(0, (result.indexOf("<") + 1));
////											val = result.substring((result.indexOf("<") + 1));
////										}else if(result.indexOf(">") != -1){
////											op = result.substring(0, (result.indexOf(">") + 1));
////											val = result.substring((result.indexOf(">") + 1));
////										}else if(result.indexOf("<=") != -1){
////											op = result.substring(0, (result.indexOf("<=") + 2));
////											val = result.substring((result.indexOf("<=") + 2));
////										}else if(result.indexOf(">=") != -1){
////											op = result.substring(0, (result.indexOf(">=") + 2));
////											val = result.substring((result.indexOf(">=") + 2));
////										}else if(result.indexOf("=") != -1){
////											op = result.substring(0, (result.indexOf("=") + 1));
////											val = result.substring((result.indexOf("=") + 1));
////										}else{
////											val = result;
////										}
////
////										if("ST".equals(testResultType)){
////											if(op == null){
////												//String txtNumRes = obxRecord.getTextualNumResult().trim();
////												String txtNumRes = obxTextNumResultArray[j].trim();
////												obx.getValueType().setValue("ST");
////												ST st = new ST(oru); //obx.testResultType could be ST or NM
////												st.setValue(txtNumRes);
////												Varies value = obx.getObservationValue(0);
////												value.setData(st);
////
////
////												// add organism name to observation val
////												String microOrgName = obxRecord.getMicroOrganismName();
////												if(microOrgName != null){
////													if((txtNumRes != null) && (txtNumRes.indexOf(microOrgName) == -1)){
////														st = new ST(oru); //obx.testResultType could be ST or NM
////														st.setValue(microOrgName);
////														value = obx.getObservationValue(1);
////														value.setData(st);
////													}
////												}
////
////											}else{
////												SN sn = new SN(oru);
////												if(op != null){
////													sn.getComparator().setValue(op);
////												}
////												sn.getNum1().setValue("1");
////												//sn.getSeparatorOrSuffix().setValue(":");
////												sn.getSeparatorSuffix().setValue(":");
////												if(val != null){
////													sn.getNum2().setValue(val);
////												}
////												//sn.getNum2().setValue(obxRecord.getTextualNumResult());
////												Varies value = obx.getObservationValue(0);
////												value.setData(sn);
////											}
////										}else if("NM".equals(testResultType)){
////											//NM nm = new NM(oru);
////											//nm.setValue(obxRecord.getTextualNumResult());
////											SN sn = new SN(oru);
////											if(op != null){
////												sn.getComparator().setValue(op);
////											}
////											sn.getNum1().setValue("1");
////											//sn.getSeparatorOrSuffix().setValue(":");
////											sn.getSeparatorSuffix().setValue(":");
////											if(val != null){
////												sn.getNum2().setValue(val);
////											}
////											//sn.getNum1().setValue(obxRecord.getTextualNumResult());
////											Varies value = obx.getObservationValue(0);
////											//value.setData(nm);
////											value.setData(sn);
////										}else{
////											//ST st = new ST(oru); //obx.testResultType could be ST or NM
////											//st.setValue(obxRecord.getTextualNumResult());
////											SN sn = new SN(oru);
////											if(op != null){
////												sn.getComparator().setValue(op);
////											}
////											sn.getNum1().setValue("1");
////											//sn.getSeparatorOrSuffix().setValue(":");
////											sn.getSeparatorSuffix().setValue(":");
////											if(val != null){
////												sn.getNum2().setValue(val);
////											}
////											//sn.getNum1().setValue(obxRecord.getTextualNumResult());
////											Varies value = obx.getObservationValue(0);
////											//value.setData(st);
////											value.setData(sn);
////										}
////										//end result
////
////										//obx.getUnits().getText().setValue(obxRecord.getUnits());
////										obx.getReferencesRange().setValue(obxRecord.getRefRange());
////
////										obx.getAbnormalFlags(0).setValue("Test");
////
////										//ST obx8_1  = new ST (oru);
////										ST obx8_2  = new ST (oru);
////										ST obx8_3  = new ST (oru);
////										ST obx8_4  = new ST (oru);
////										ST obx8_5  = new ST (oru);
////										ST obx8_6  = new ST (oru);
////										ST obx8_7  = new ST (oru);
////										ST obx8_8  = new ST (oru);
////
////
////										//obx8_1.setValue("N");
////										obx8_2.setValue("Normal");
////										obx8_3.setValue("HL70078");
////										obx8_4.setValue("N");
////										obx8_5.setValue("Normal");
////										obx8_6.setValue("NHL70078");
////										obx8_7.setValue("2.5.1");
////										obx8_8.setValue("V1");
////
////
////										//obx.getAbnormalFlags(0).getExtraComponents().getComponent(0).setData(obx8_1 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(0).setData(obx8_2 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(1).setData(obx8_3 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(2).setData(obx8_4 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(3).setData(obx8_5 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(4).setData(obx8_6 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(5).setData(obx8_7 );
////										obx.getAbnormalFlags(0).getExtraComponents().getComponent(6).setData(obx8_8 );
////
////
////
////										obx.getObservationResultStatus().setValue(obxRecord.getResultStatus());
////										StringBuilder releaseDateTimeBuilder = new StringBuilder();
////										releaseDateTimeBuilder.append(obxRecord.getReleaseDate()).append(obxRecord.getReleaseTime().substring(8, 12));
////										obx.getDateTimeOfTheObservation().getTime().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
////
////										obx.getDateTimeOfTheAnalysis().getTime().setValue(releaseDateTimeBuilder.toString());
////										log.warn("getMessage(): requisition id: " + (patientRecord.getOrderNumber() == null ? "NULL" : patientRecord.getOrderNumber()));
////										log.warn("getMessage(): sendingFacilityId: " + (sendingFacilityId == null ? "NULL" : sendingFacilityId));
////										log.warn("getMessage(): fileSendingFacility: " + (fileSendingFacility == null ? "NULL" : fileSendingFacility));
////										obx.getPerformingOrganizationName().getOrganizationIdentifier().setValue(sendingFacilityId);
////										obx.getPerformingOrganizationName().getOrganizationName().setValue(fileSendingFacility);
////										obx.getPerformingOrganizationName().getOrganizationNameTypeCode().setValue(sendingFacilityIdType);
////
////										//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
////										//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
////										if((infoSpectraAddress2 != null) && (infoSpectraAddress2.length() > 0)){
////											StringBuilder performOrgAddBuilder = new StringBuilder();
////											performOrgAddBuilder.append(infoSpectraAddress).append(" ").append(infoSpectraAddress2);
////											//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(performOrgAddBuilder.toString());
////											obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(performOrgAddBuilder.toString());
////										}else{
////											//obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
////											obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
////										}
////										obx.getPerformingOrganizationAddress().getCity().setValue(infoSpectraCity);
////										obx.getPerformingOrganizationAddress().getStateOrProvince().setValue(infoSpectraState);
////										obx.getPerformingOrganizationAddress().getZipOrPostalCode().setValue(infoSpectraZip);
/////*
////										String[] arr = medicalDirector.split("\\s");
////										String mdFirstName = null;
////										String mdLastName = null;
////										for(int a = 0; a < arr.length; a++){
////											if(arr[a].indexOf(",") != -1){
////												arr[a] = arr[a].substring(0, arr[a].indexOf(","));
////											}
////										}
////										if(medicalDirector.indexOf(" MD") != -1){
////											mdFirstName = arr[0];
////											mdLastName = arr[1];
////										}else if(medicalDirector.indexOf("Dr.") != -1){
////											mdFirstName = arr[1];
////											mdLastName = arr[2];
////										}
////*/
////										obx.getPerformingOrganizationMedicalDirector().getFamilyName().getSurname().setValue(mdLastName);
////										obx.getPerformingOrganizationMedicalDirector().getGivenName().setValue(mdFirstName);
////										obx.getObservationMethod(0).getIdentifier().setValue("FMC");
////
////										//NTE nte = observation.getNTE();
////										//nte.getSetIDNTE().setValue(String.valueOf(ctr));
////										//nte.getSourceOfComment().setValue(obxRecord.getSourceOfComment());
////										//nte.getComment(0).setValue(nteRecord.getTestNteComment());
////
////										ctr++;
////									}
////								}
////							}
////							ctr--;
//						}else{
//							ctr -= 1;
//						}//if
//
//					}//for
//				}//if

			// Micro OBR(1) Drugs
//				int obrcnt = 1;
//
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrcnt + ")/OBR-1","2");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrcnt + ")/OBR-16-10","L");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrcnt + ")/OBR-14",repo.convertDate(patientRecord.getCollectionDateformat()));
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrcnt + ")/OBR-22",repo.convertDate(patientRecord.getReleaseDate()));

//				terser.set("/OBX-1","1");
//				terser.set("/OBX-2","SN");

//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/.OBX(0)-1","1");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/.OBX(0)-2","SN");
			//terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/.OBX-1","1");
			//terser.set("PATIENT_RESULT/ORDER_OBSERVATION(1)/.OBX-1","2");

			//Segment obx = terser.getFinder().findSegment("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBX",0);

			//terser.set(obx,1,0,1,1,"X");

//				int spmline = 1;
//
//				for(int i = 1; i < 5; i++) {
//					terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + i + ")/.OBX-1", Integer.toString(i));
//					terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + i + ")/.OBX-2", "SN");
//					spmline = i;
//				}

			//terser.set("PATIENT_RESULT/ORDER_OBSERVATION(2)/.OBX-1","2");
			//terser.set("PATIENT_RESULT/ORDER_OBSERVATION(2)/.OBX-2","SN");

			log.error("*** Error ***");



//				SPM spm = orderObservation.getSPECIMEN().getSPM();
//				spm.getSetIDSPM().setValue("1");
//				spm.getSpecimenID().getPlacerAssignedIdentifier().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
//				spm.getSpecimenID().getPlacerAssignedIdentifier().getNamespaceID().setValue("Spectra East");
//				spm.getSpecimenID().getPlacerAssignedIdentifier().getUniversalID().setValue("2.16.840.1.114222.4.3.22.29.7.1");
//				spm.getSpecimenID().getPlacerAssignedIdentifier().getUniversalIDType().setValue("ISO");

//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-1","1");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-1-1",patientRecord.getAccessionNo());
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-1-2","Spectra East");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-1-3","2.16.840.1.114222.4.3.22.29.7.1");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-1-4","ISO");
//
////				spm.getSpecimenID().getFillerAssignedIdentifier().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
////				spm.getSpecimenID().getFillerAssignedIdentifier().getNamespaceID().setValue("Spectra East");
////				spm.getSpecimenID().getFillerAssignedIdentifier().getUniversalID().setValue("2.16.840.1.114222.4.3.22.29.7.1");
////				spm.getSpecimenID().getFillerAssignedIdentifier().getUniversalIDType().setValue("ISO");
//
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-2-1",patientRecord.getAccessionNo());
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-2-2","Spectra East");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-2-3","2.16.840.1.114222.4.3.22.29.7.1");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-2-2-4","ISO");

			// SPM 4
//				spm.getSpecimenType().getIdentifier().setValue(obxRec.getSpecimenSourceIdentifier());
//				spm.getSpecimenType().getText().setValue(obxRec.getSpecimenSourceText());
//				spm.getSpecimenType().getNameOfCodingSystem().setValue("SCT");
//				spm.getSpecimenType().getCwe7_CodingSystemVersionID().setValue("1.0");

//				sm = getSnomedMaster(patientRecord.getAccessionNo().substring(0,2),"SPECIMEN");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-4-1",sm.getSnomedcode());
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-4-2",sm.getPreferredname());
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-4-3","SCT");
//
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-4-7","1.0");
//
//				// SPM 8
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-8-1","NONE");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-8-2","NONE");
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-8-3","HL70371");
//
//
//
//				//spm.getSpecimenType().getText().setValue(patientRecord.getSpecimenSource());
//				//spm.getSpecimenType().getNameOfCodingSystem().setValue(""); // NEED REQUIREMENT FROM QA
////				spm.getSpecimenCollectionDateTime().getRangeStartDateTime().getTime().setValue(patientRecord.getCollectionDate());
////				//spm.getSpecimenCollectionDateTime().getRangeEndDateTime().getTime().setValue(patientRecord.getCollectionDate());
////				spm.getSpecimenReceivedDateTime().getTime().setValue(patientRecord.getSpecimenReceiveDate());
//
//				// SPM 17
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-17-1",repo.convertDate(patientRecord.getCollectionTimeformat()));
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-17-2",repo.convertDate(patientRecord.getSpecimenRecDateformat()));
//
//				// SPM 18
//				terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + spmline + ")/.SPM-18",repo.convertDate(patientRecord.getSpecimenRecDateformat()));
//
//
//				//String hapiCustomPackage = this.configService.getString("hapi.custom.package");
//				/*
//				//ModelClassFactory cmf = new CustomModelClassFactory(ip.getHapiCustomPackage());
//				ModelClassFactory cmf = new CustomModelClassFactory(hapiCustomPackage);
//				HapiContext context = new DefaultHapiContext();
//				context.setModelClassFactory(cmf);
//
//				//HapiContext context = new DefaultHapiContext();
//				//HapiContext context = this.createHapiContext();
//				Parser parser = context.getPipeParser();
//				//hl7String = parser.encode(oru);
//
//				//hl7Builder.append(parser.encode(oru));
//				zruBuilder.append(parser.encode(oru));
//*/
//			//System.out.println(oru.printStructure());
//
//			//System.exit(0);


//			} catch (HL7Exception hl7e) {
//				log.error(hl7e.getMessage());
//				hl7e.printStackTrace();
//			}


		}

		return oru;
	}
	}


//	private OBXRecord _getDrugs(OBXRecord obxRecord){
//
//		return obxRecord;
//	}
//
//	private LOINC_CODE getLoincCodes(String organismname,String valuetype){
//		return microController.getMicroOrganismFilter("select * from micro_organism_filter where organismname = '" +
//				organismname + "'");
//	}
//
//	private SNOMED_MASTER getSnomedMaster(String localname, String valuetype){
//		return microController.getSnomedCode("select * from snomed_master where localname = '" +
//				localname + "'");
//	}

	//}
