package com.spectra.utils;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v25.datatype.CE;
import ca.uhn.hl7v2.model.v251.datatype.ST;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.*;
import ca.uhn.hl7v2.util.Terser;
import com.spectra.asr.dto.filter.LOINC_CODE;
import com.spectra.asr.dto.patient.OBXRecord;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.snomed.SNOMED_MASTER;
import com.spectra.controller.MicroController;
import com.spectra.repo;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.spectra.asr.utils.file.writer.FileUtil.getMSHControlNum;

@Slf4j
public class HL7_Hapi {

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
    private static final String MSH21_3 = "2.16.840.1.113883.9.11";
    private static final String MSH21_4 = "ISO";

    private static final String ORC12_9_2 = "2.16.840.1.114222.4.3.22.29.7.1";
    private static final String MSH1_1 = "^~" + "\\" + "&";

    @FunctionalInterface
    interface createSegment {

        void createSegment();


    }

    public static ORU_R01 mshSegment(PatientRecord rec, ORU_R01 oru){
        Terser terser = null;
        StringBuffer sb = new StringBuffer();

        try {
            oru.initQuickstart("ORU", "R01", "T");

            MSH msh = oru.getMSH();
            // MSH 2
            msh.getMsh2_EncodingCharacters().setValue(MSH1_1);


           // MSH 3_1
				msh.getSendingApplication().getHd1_NamespaceID().setValue("Spectra East");
				//Spectra OID
				// MSH 3_2
				msh.getSendingApplication().getHd2_UniversalID().setValue(MSH3_2);
				// MSH 3_3
				msh.getSendingApplication().getHd3_UniversalIDType().setValue(MSH3_3);

               // MSH 4
				msh.getSendingFacility().getNamespaceID().setValue("Spectra East"); //Spectra East
				msh.getSendingFacility().getUniversalID().setValue("31D0961672"); //31D0961672
				msh.getSendingFacility().getUniversalIDType().setValue(MSH4_3); //CLIA

            // MSH 5
				msh.getReceivingApplication().getNamespaceID().setValue("IL-ELR");
				//msh.getReceivingApplication().getUniversalID().setValue(MSH5_2);
				//msh.getReceivingApplication().getUniversalIDType().setValue(MSH3_3);


             //MSH 6
				msh.getReceivingFacility().getNamespaceID().setValue("ILDOH");
//				msh.getReceivingFacility().getUniversalID().setValue(MSH6_2);
//				msh.getReceivingFacility().getUniversalIDType().setValue(MSH3_3);

            //MSH 7
            sb.append(new SimpleDateFormat("yyyyMMddHHmmZ").format(new Date()));

            msh.getDateTimeOfMessage().getTime().setValue(sb.toString());
            sb.setLength(0);

            // MSH 10
            terser.set(msh,10,0,1,1,msh.getMessage().getParser().getParserConfiguration().getIdGenerator().getID());
            //msh.getMessageControlID().setValue(getMSHControlNum());
            // MSH 15
            msh.getMsh15_AcceptAcknowledgmentType().setValue("NE");

            // MSH 16
            msh.getMsh16_ApplicationAcknowledgmentType().setValue("NE");

            // MSH 17
            msh.getMsh17_CountryCode().setValue("USA");

            // MSH 21
            msh.getMessageProfileIdentifier(0).getEntityIdentifier().setValue(MSH21_1);
            msh.getMessageProfileIdentifier(0).getNamespaceID().setValue(MSH21_2);
            msh.getMessageProfileIdentifier(0).getUniversalID().setValue(MSH21_3);
            msh.getMessageProfileIdentifier(0).getUniversalIDType().setValue(MSH21_4);



        } catch (HL7Exception e) {
            log.error("HL7 Exception: {}",e.getMessage());
        } catch (IOException e) {
            log.error("IO Exception: {}", e.getMessage());
        }


        return oru;
    }

    public static ORU_R01 sftSegment(PatientRecord rec, ORU_R01 oru){
        Terser terser = new Terser(oru);

        SFT sft = oru.getSFT();

        try {
            terser.set(sft,1,0,1,1,"Spectra East");
            terser.set(sft,1,0,2,1,"L");
            terser.set(sft,1,0,6,1,"Spectra East");
            terser.set(sft,1,0,6,2,"2.16.840.1.114222.4.3.22.29.7.1");
            terser.set(sft,1,0,6,3,"ISO");
            terser.set(sft,1,0,7,1,"XX");
            terser.set(sft,1,0,10,1,"Spectra East");
            terser.set(sft,2,0,1,1,"1.0");
            terser.set(sft,3,0,1,1,"ASR");
            terser.set(sft,4,0,1,1,"1");
            terser.set(sft,6,0,1,1,"20240419");
        } catch (HL7Exception e) {
            log.error("HL7 Error: {}",e.getMessage());
        }

        return oru;
    }

    public static ORU_R01 pidSegment(PatientRecord patientRecord, ORU_R01 oru) {
        Terser terser = new Terser(oru);

        PID pid = oru.getPATIENT_RESULT().getPATIENT().getPID();

        try
        {
        // PID 1
        pid.getSetIDPID().setValue("1");

        // PID 2

        // PID 3_1
        pid.getPid3_PatientIdentifierList(0).getCx1_IDNumber().setValue(patientRecord.getAltPatientId());

        // PID 3_2
        // PID 3_3

            List<String> sendingLab = new LinkedList<>();

            if(patientRecord.getPerformingLabId().indexOf("SE") != -1){
                //east address
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.fhs.4"));
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.msh.3"));
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.msh.4.2"));
            }else if(patientRecord.getPerformingLabId().indexOf("OUTSEND") != -1){
                //east address
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.fhs.4"));
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.msh.3"));
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.msh.4.2"));
            }else if(patientRecord.getPerformingLabId().indexOf("SH") != -1){
                //east address
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.fhs.4"));
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.msh.3"));
                sendingLab.add(com.spectra.scorpion.framework.util.ApplicationProperties.getProperty("spectra.east.msh.4.2"));
            }

        // PID 3_4_1
//        pid.getPid3_PatientIdentifierList(0).getCx4_AssigningAuthority().getNamespaceID().setValue(sendingLab.get(0));
            terser.set(pid,3,0,4,1,"Spectra East");

        // PID 3_4_2
//        pid.getPid3_PatientIdentifierList(0).getCx4_AssigningAuthority().getUniversalID().setValue(sendingLab.get(1));
            terser.set(pid,3,0,4,2,"31D0961672");

        // PID 3_4_3
//        pid.getPid3_PatientIdentifierList(0).getCx4_AssigningAuthority().getUniversalIDType().setValue(sendingLab.get(2));
            terser.set(pid,3,0,4,3,"CLIA");

        // PID 3_4_4
        pid.getPid3_PatientIdentifierList(0).getCx5_IdentifierTypeCode().setValue("PI");


        // PID 4

        // PIS 5_1


        String patientName = patientRecord.getPatientName();
        log.warn("toHL7Dto(): patientName: " + (patientName == null ? "NULL" : patientName));
        if (StringUtils.contains(patientName, "^")) {
            String[] nameArray = StringUtils.split(patientName, "\\^");
            if ((nameArray != null) && (nameArray.length >= 2)) {
                log.warn("toHL7Dto(): nameArray[0]: " + (nameArray[0] == null ? "NULL" : nameArray[0]));
                log.warn("toHL7Dto(): nameArray[1]: " + (nameArray[1] == null ? "NULL" : nameArray[1]));
                pid.getPatientName(0).getFamilyName().getSurname().setValue(nameArray[0]);
                pid.getPatientName(0).getGivenName().setValue(nameArray[1]); // first name
            }
        } else {
            pid.getPatientName(0).getFamilyName().getSurname().setValue(patientName);
        }


        //pid.getMotherSMaidenName(0).getFamilyName().getSurname().setValue(patientRecord.getCid());
        pid.getDateTimeOfBirth().getTime().setValue(patientRecord.getDateOfBirth());
        pid.getAdministrativeSex().setValue(patientRecord.getGender());


        terser.set("PATIENT_RESULT/.PID-5-7","L");

        String race = patientRecord.getPatientRace();
        if ((race == null) || (race.trim().length() == 0)) {
            race = "U";
        }

        CE pidce = new CE(oru);

        pid.getRace(0).getIdentifier().setValue(patientRecord.getRaceCode());
        pid.getRace(0).getText().setValue(patientRecord.getPatientRace());
        pid.getRace(0).getNameOfCodingSystem().setValue("HL70005");

//				pid.getRace(0).getIdentifier().setValue(patientRecord.getPatientRace());
//				pid.getRace(0).getText().setValue("Other Race");
//				pid.getRace(0).getNameOfCodingSystem().setValue("CDCREC");
//				pid.getRace(0).getAlternateIdentifier().setValue("U");
//				pid.getRace(0).getAlternateText().setValue("Unknown");
//				pid.getRace(0).getNameOfAlternateCodingSystem().setValue("L");

//				ST extraSubcomponent3_6 = new ST(oru);
//				ST extraSubcomponent3_7 = new ST(oru);
//				extraSubcomponent3_6.setValue("2.5.1");
//				extraSubcomponent3_7.setValue("1.0");
//				pid.getRace(0).getExtraComponents().getComponent(0).setData(extraSubcomponent3_6);
//				pid.getRace(0).getExtraComponents().getComponent(1).setData(extraSubcomponent3_7);

        terser.set("PATIENT_RESULT/.PID-11-6","USA");


        //pid.getRace(0).getText().setValue(race);
        //pid.getRace(0).getIdentifier().setValue(race);

        String patAddr1 = patientRecord.getPatientAddress1();
        //log.warn("toHL7Dto(): patAddr1: " + (patAddr1 == null ? "NULL" : patAddr1));
        pid.getPatientAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(patientRecord.getPatientAddress1());
        //pid.getPatientAddress(0).getStreetAddress().getStreetName().setValue(patientRecord.getPatientAddress1());
        String patientAddress2 = patientRecord.getPatientAddress2();
        if ((patientAddress2 != null) && (patientAddress2.length() > 0)) {
            //pid.getPatientAddress(1).getStreetAddress().getStreetName().setValue(patientAddress2);
            pid.getPatientAddress(0).getOtherDesignation().setValue(patientAddress2);
        }
        pid.getPatientAddress(0).getCity().setValue(patientRecord.getPatientCity()); //patientrecord.getPatient_city
        pid.getPatientAddress(0).getStateOrProvince().setValue(patientRecord.getPatientState()); //patientrecord.getPatient_state
        pid.getPatientAddress(0).getZipOrPostalCode().setValue(patientRecord.getPatientZip()); //patientrecord.getPatient_zip
        pid.getPatientAddress(0).getCountry().setValue("USA");


        // PID 13 Patient phone
        if (patientRecord.getPatientPhone().trim().length() > 0) {
            pid.getPid13_PhoneNumberHome(0).getCountryCode().setValue("1");

            pid.getPid13_PhoneNumberHome(0).getTelecommunicationUseCode().setValue("PRN");

            pid.getPhoneNumberHome(0).getAreaCityCode().setValue(patientRecord.getPatientAreaCode());
            pid.getPhoneNumberHome(0).getTelecommunicationEquipmentType().setValue("PH");

            pid.getPhoneNumberHome(0).getLocalNumber().setValue(patientRecord.getPatientPhone());
            pid.getPhoneNumberHome(0).getAreaCityCode().setValue(patientRecord.getPatientAreaCode());

        }


        String ethnicGroup = patientRecord.getEthnicGroup();
        if((ethnicGroup == null) || (ethnicGroup.trim().length() == 0)){
            ethnicGroup = "U";
        }




        pid.getEthnicGroup(0).getIdentifier().setValue(patientRecord.getEthnicGroupCode());
        pid.getEthnicGroup(0).getText().setValue(patientRecord.getEthnicGroup());
        // Ethnic group source table HL70189
        pid.getEthnicGroup(0).getNameOfCodingSystem().setValue("HL70189");



        } catch (HL7Exception e) {
            log.error("HL7 Error: ",e.getMessage());
        }

        return oru;
    }

    public static ORU_R01 orcSegment(PatientRecord patientRecord, ORU_R01 oru){
        Terser terser = new Terser(oru);

        OBXRecord obxRec = patientRecord.getObxList().get(0);

        ORU_R01_ORDER_OBSERVATION orderObservation = oru.getPATIENT_RESULT(0).getORDER_OBSERVATION();

        try {


            ORC orc = orderObservation.getORC();

            // ORC 1
            terser.set(orc,1,0,1,1,"RE");
            // ORC 2
            terser.set(orc,2,0,1,1,patientRecord.getAccessionNo());

            //ORC 3
            terser.set(orc,3,0,1,1,patientRecord.getOrderNumber());

            //ORC 4
            terser.set(orc,4,0,1,1,patientRecord.getOrderNumber());

            // ORC 12
            terser.set(orc,12,0,1,1,patientRecord.getProviderId());

            // ORC 12_2
            String orderingPhyName = patientRecord.getOrderingPhyName();
            log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
            if(StringUtils.contains(orderingPhyName, "^")){
                String[] phyNameArray = StringUtils.split(orderingPhyName, "\\^");
                if((phyNameArray != null) && (phyNameArray.length >= 2)){
                    log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
                    log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
//                    orc.getOrderingProvider(0).getFamilyName().getSurname().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
//                    orc.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name
                    terser.set(orc,12,0,2,1,phyNameArray[0]);
                    terser.set(orc,12,0,3,1,phyNameArray[1]);
                }
            }else{
                //orc.getOrderingProvider(0).getFamilyName().getSurname().setValue(orderingPhyName); //last name
                terser.set(orc,12,0,2,1,orderingPhyName);
            }




            // ORC 12_9
//            orc.getOrc12_OrderingProvider(0).getAssigningAuthority().getNamespaceID().setValue("NPI");
//            orc.getOrc12_OrderingProvider(0).getAssigningAuthority().getUniversalID().setValue(ORC12_9_2);
//            orc.getOrc12_OrderingProvider(0).getAssigningAuthority().getUniversalIDType().setValue("L");

            terser.set(orc,12,0,9,1,"NPI");
            terser.set(orc,12,0,9,2,ORC12_9_2);
            terser.set(orc,12,0,10,1,"L");
            terser.set(orc,12,0,13,1,"XX");


            //orc.getOrc12_OrderingProvider(0).getIdentifierTypeCode().setValue("XX");




            // ORC 14
            terser.set(orc,14,0,2,1,"WPN");
            terser.set(orc,14,0,3,1,"PH");
            terser.set(orc,14,0,5,1,"1");
            terser.set(orc,14,0,6,1,patientRecord.getFacilityAreaCode());
            terser.set(orc,14,0,7,1,patientRecord.getFacilityPhoneNumber());

            //orc.getCallBackPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code

            //orc.getCallBackPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
            //orc.getCallBackPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
            //orc.getCallBackPhoneNumber(0).getCountryCode().setValue("1");
            //orc.getCallBackPhoneNumber(0).getTelecommunicationUseCode().setValue("WPN");


//               orc.getOrderingFacilityAddress(0).getStreetAddress().getStreetOrMailingAddress().setValue(patientRecord.getFacilityAddress1());
//
//            orc.getOrderingFacilityAddress(0).getCity().setValue(patientRecord.getFacilityCity());
//            orc.getOrderingFacilityAddress(0).getStateOrProvince().setValue(patientRecord.getFacilityState());
//            orc.getOrderingFacilityAddress(0).getZipOrPostalCode().setValue(patientRecord.getFacilityZip());
//            orc.getOrderingFacilityPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code



            //ORC 21

            terser.set(orc,21,0,1,1,patientRecord.getFacilityName());
            terser.set(orc,21,0,2,1,"L");
            terser.set(orc,21,0,6,1,"Spectra East");
            terser.set(orc,21,0,6,2,"2.16.840.1.114222.4.3.22.29.7.1");
            terser.set(orc,21,0,7,1,"XX");

            // ORC 22
            terser.set(orc,22,0,1,1,patientRecord.getFacilityAddress1());
            terser.set(orc,22,0,3,1,patientRecord.getFacilityCity());
            terser.set(orc,22,0,4,1,patientRecord.getFacilityState());
            terser.set(orc,22,0,5,1,patientRecord.getFacilityZip());

            terser.set(orc,22,0,6,1,"USA");
            terser.set(orc,22,0,7,1,"B");

            // ORC 23
//            orc.getOrderingFacilityPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
//            orc.getOrderingFacilityPhoneNumber(0).getTelecommunicationUseCode().setValue("WPN");
//            orc.getOrderingFacilityPhoneNumber(0).getCountryCode().setValue("1");
//            orc.getOrderingFacilityPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
            terser.set(orc,23,0,2,1,"WPN");
            terser.set(orc,23,0,3,1,"PH");
            terser.set(orc,23,0,5,1,"1");
            terser.set(orc,23,0,6,1,patientRecord.getFacilityAreaCode());
            terser.set(orc,23,0,7,1,patientRecord.getFacilityPhoneNumber());



            // ORC 24
            terser.set(orc,24,0,1,1,"8 King Road");
            terser.set(orc,24,0,2,1,"");
            terser.set(orc,24,0,3,1,"Rockleigh");
            terser.set(orc,24,0,4,1,"NJ");
            terser.set(orc,24,0,5,1,"07647");
            terser.set(orc,24,0,6,1,"USA");
            terser.set(orc,24,0,7,1,"B");



            List<OBXRecord> obxList = patientRecord.getObxList();

            // Get Loinccode
            LOINC_CODE lc = getLoincCodes(obxList.get(0).getMicroOrganismName(),"ORG");


        } catch (HL7Exception e) {
            log.error("HL7 Error: {}",e.getMessage());
        }

        return oru;
    }



    public static ORU_R01 obrSegment(PatientRecord patientRecord, ORU_R01 oru,int rep,ORU_R01_ORDER_OBSERVATION orderObservation) {
        Terser terser = new Terser(oru);

        OBXRecord obxRec = patientRecord.getObxList().get(0);

        //ORU_R01_ORDER_OBSERVATION orderObservation = oru.getPATIENT_RESULT(rep).getORDER_OBSERVATION();

        OBR obr = orderObservation.getOBR();

        OBXRecord obxList = patientRecord.getObxList().stream()
                .filter(t -> t.getSeqResultName().matches("Isolate(.*)"))
                .findFirst().get();

        // Get Loinccode
        LOINC_CODE lc = getLoincCodes(obxList.getMicroOrganismName(),"ORG");

        try
        {
        obr.getSetIDOBR().setValue(Integer.toString(rep + 1));

        // OBR 2
        obr.getPlacerOrderNumber().getEntityIdentifier().setValue(patientRecord.getAccessionNo());
//				obr.getPlacerOrderNumber().getNamespaceID().setValue(fileSendingFacility);
//				obr.getPlacerOrderNumber().getUniversalID().setValue(sendingFacilityId);
//				obr.getPlacerOrderNumber().getUniversalIDType().setValue(sendingFacilityIdType);

        // OBR 3
        obr.getFillerOrderNumber().getEntityIdentifier().setValue(patientRecord.getOrderNumber()); //patientrecord.getOrder_number
//				obr.getFillerOrderNumber().getNamespaceID().setValue(fileSendingFacility);
//				obr.getFillerOrderNumber().getUniversalID().setValue(sendingFacilityId);
//				obr.getFillerOrderNumber().getUniversalIDType().setValue(sendingFacilityIdType);


        // OBR 4

        if (obxRec.getMicroOrganismName() == null) {
            obr.getObr4_UniversalServiceIdentifier().getCe1_Identifier().setValue(obxRec.getLoincCode());
            obr.getObr4_UniversalServiceIdentifier().getCe2_Text().setValue(obxRec.getLoincName());
        } else {
            obr.getObr4_UniversalServiceIdentifier().getCe1_Identifier().setValue(lc.getLoinccode());
            obr.getObr4_UniversalServiceIdentifier().getCe2_Text().setValue(lc.getLoincname());
        }

        if(rep == 1){
            terser.set(obr,4,0,1,1,"29576-6");
            terser.set(obr,4,0,2,1,"Bacterial susceptibility panel");
        }


        obr.getObr4_UniversalServiceIdentifier().getCe3_NameOfCodingSystem().setValue("LN");

        obr.getObr4_UniversalServiceIdentifier().getCe4_AlternateIdentifier().setValue(obxRec.getCompoundTestCode()); //obxrecord.getCompound_test_code
        obr.getObr4_UniversalServiceIdentifier().getCe5_AlternateText().setValue(obxRec.getSeqTestName()); //obxrecord.getSeq_test_name
        obr.getObr4_UniversalServiceIdentifier().getCe6_NameOfAlternateCodingSystem().setValue("L");

        // OBR 7
        //obr.getObservationDateTime().getTime().setValue(patientRecord.getCollectionDate()); //patientrecord.getCollection_date
        obr.getRelevantClinicalInformation().setValue("Recurring patient"); // NEED REQUIREMENT FROM QA
        obr.getSpecimenActionCode().setValue("G");
        obr.getSpecimenSource().getSpecimenSourceNameOrCode().getIdentifier().setValue(patientRecord.getSpecimenSource()); //patientrecord.getSpecimen_source
        obr.getOrderingProvider(0).getIDNumber().setValue(patientRecord.getProviderId()); //patientrecord.getProvider_id
        //String orderingPhyName = patientRecord.getOrderingPhyName();
        //log.warn("toHL7Dto(): orderingPhyName: " + (orderingPhyName == null ? "NULL" : orderingPhyName));
        if (StringUtils.contains(patientRecord.getOrderingPhyName(), "^")) {
            String[] phyNameArray = StringUtils.split(patientRecord.getOrderingPhyName(), "\\^");
            if ((phyNameArray != null) && (phyNameArray.length >= 2)) {
                //log.warn("toHL7Dto(): nameArray[0]: " + (phyNameArray[0] == null ? "NULL" : phyNameArray[0]));
                //log.warn("toHL7Dto(): nameArray[1]: " + (phyNameArray[1] == null ? "NULL" : phyNameArray[1]));
                obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(phyNameArray[0]); //patientrecord.getOrdering_phy_name
                obr.getOrderingProvider(0).getGivenName().setValue(phyNameArray[1]); //patientrecord.getOrdering_phy_name
            }
        } else {
            obr.getOrderingProvider(0).getFamilyName().getSurname().setValue(patientRecord.getOrderingPhyName()); //last name
        }
        obr.getOrderCallbackPhoneNumber(0).getAreaCityCode().setValue(patientRecord.getFacilityAreaCode()); //patientrecord.getFacility_area_code
        //obr.getOrderCallbackPhoneNumber(0).getTelephoneNumber().setValue(patientRecord.getFacilityPhoneNumber()); //patientrecord.getFacility_phone_number

        obr.getOrderCallbackPhoneNumber(0).getTelecommunicationEquipmentType().setValue("PH");
        obr.getOrderCallbackPhoneNumber(0).getLocalNumber().setValue(patientRecord.getFacilityPhoneNumber());
        obr.getOrderCallbackPhoneNumber(0).getTelecommunicationUseCode().setValue("WPN");
        obr.getOrderCallbackPhoneNumber(0).getCountryCode().setValue("1");

        obr.getResultsRptStatusChngDateTime().getTime().setValue(patientRecord.getResRprtStatusChngDtTime()); //patientrecord.getRes_rprt_status_chng_dt_time
        obr.getDiagnosticServSectID().setValue("LAB"); // NEED REQUIREMENT FROM QA
        obr.getResultStatus().setValue(patientRecord.getRequisitionStatus()); //patientrecord.getRequisition_status


        obr.getParent(); // NEED REQUIREMENT FROM QA
        obr.getReasonForStudy(); // NEED REQUIREMENT FROM QA

        obr.getObr16_OrderingProvider(0).getAssigningAuthority().getNamespaceID().setValue("NPI");
        obr.getObr16_OrderingProvider(0).getAssigningAuthority().getUniversalID().setValue("31D0961672"); // sendingFacilityId
        obr.getObr16_OrderingProvider(0).getAssigningAuthority().getUniversalIDType().setValue("CLIA");

        obr.getObr16_OrderingProvider(0).getIdentifierTypeCode().setValue("NPI");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-7", repo.convertDate(patientRecord.getCollectionDateformat()));
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-16-10", "L");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-14", repo.convertDate(patientRecord.getCollectionDateformat()));
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-22", repo.convertDate(patientRecord.getReleaseDate()));

        if(rep == 1) {
            terser.set(obr, 26, 0, 1, 1, lc.getLoinccode());
            terser.set(obr, 26, 0, 1, 2, lc.getLoincname());
            terser.set(obr, 26, 0, 1, 3, "LN");

            terser.set(obr, 26, 0, 2, 1, "1");
            terser.set(obr, 26, 0, 3, 1, lc.getOrganismname());

            terser.set(obr, 29, 0, 1, 1, obxRec.getAccessionNo());
            terser.set(obr, 29, 0, 2, 1, obxRec.getOrderNumber());
        }

    }   catch (HL7Exception e) {
            e.printStackTrace();
        }

        return oru;
    }

    public static ORU_R01 spmChildSegment(PatientRecord patientRecord, ORU_R01 oru,int obxrep,int obrctr,ORU_R01_ORDER_OBSERVATION orderObservation
            ,OBXRecord obxRecord) {
        Terser terser = new Terser(oru);

        SPM spm = orderObservation.getSPECIMEN().getSPM();

        SNOMED_MASTER sm = null;



        try {
            sm = getSnomedMaster(obxRecord.getAccessionNo().substring(0,2),null);

            terser.set(spm,1,0,1,1,"1");

            spm.getSpecimenID().getPlacerAssignedIdentifier().getEntityIdentifier().setValue(patientRecord.getAccessionNo());


            terser.set(spm,2,0,1,1,patientRecord.getAccessionNo());
            terser.set(spm,2,0,1,2,"Spectra East");
            terser.set(spm,2,0,1,3,"2.16.840.1.114222.4.3.22.29.7.1");
            terser.set(spm,2,0,1,4,"ISO");


            terser.set(spm,2,0,2,1,patientRecord.getAccessionNo());
            terser.set(spm,2,0,2,2,"Spectra East");
            terser.set(spm,2,0,2,3,"2.16.840.1.114222.4.3.22.29.7.1");
            terser.set(spm,2,0,2,4,"ISO");

            // SPM 4



            terser.set(spm,4,0,1,1,sm.getSnomedcode());
            terser.set(spm,4,0,2,1,sm.getPreferredname());
            terser.set(spm,4,0,3,1,"SCT");


            terser.set(spm,4,0,7,1,"1.0");
//
//				// SPM 8

            terser.set(spm,8,0,1,1,"NONE");
            terser.set(spm,8,0,2,1,"NONE");
            terser.set(spm,8,0,3,1,"HL70371");

//				// SPM 17
            terser.set(spm,17,0,1,1,repo.convertDate(patientRecord.getCollectionTimeformat()));
            terser.set(spm,17,0,2,1,repo.convertDate(patientRecord.getSpecimenRecDateformat()));

//				// SPM 18

            terser.set(spm,18,0,1,1,repo.convertDate(patientRecord.getSpecimenRecDateformat()));

        } catch (HL7Exception e) {
            log.error("HL7 Error: {}",e.getMessage() );
        }
        return oru;
    }

    public static ORU_R01 obxChildSegment(PatientRecord patientRecord, ORU_R01 oru,int obxrep,int obrctr,ORU_R01_ORDER_OBSERVATION orderObservation
        ,OBXRecord obxRecord) {
        Terser terser = new Terser(oru);

        OBX obx = orderObservation.getOBSERVATION(obxrep).getOBX();


        String infoSpectraAddress = null;
        String infoSpectraAddress2 = null;
        String infoSpectraPhone = null;
        String infoSpectraCity = null;
        String infoSpectraZip  = null;
        String infoSpectraState = null;
        String medicalDirector = patientRecord.getMedicalDirector();

        String mdFirstName = null;
        String mdLastName = null;
        if(medicalDirector.indexOf("M.D.") != -1){
            medicalDirector = medicalDirector.substring("M.D.".length());
            if(medicalDirector.indexOf(".") != -1){
                mdFirstName =  "Alex"; //medicalDirector.substring(0, (medicalDirector.lastIndexOf(".") + 1));
                mdLastName = "Ryder";//medicalDirector.substring((medicalDirector.lastIndexOf(".") + 1));
            }
        }
        if(medicalDirector.indexOf(" MD") != -1){
            medicalDirector = medicalDirector.substring(0, (medicalDirector.indexOf(" MD") + 1));
            mdFirstName = medicalDirector.substring(0, (medicalDirector.indexOf(" ") + 1));
            mdLastName = medicalDirector.substring(medicalDirector.indexOf(" "));
            if(mdLastName.indexOf(",") != -1){
                mdLastName = mdLastName.replaceAll(",", "");
            }
        }

        if(medicalDirector.indexOf("Dr") != -1){

            mdFirstName = "C.O.";
            mdLastName = "Burdick";

        }
        log.warn("getMessage(): mdFirstName: " + (mdFirstName == null ? "NULL" : mdFirstName));
        log.warn("getMessage(): mdLastName: " + (mdLastName == null ? "NULL" : mdLastName));



        if(patientRecord.getPerformingLabId().indexOf("SW") != -1){
            mdFirstName = "C.O.";
            mdLastName = "Burdick";
        } else {
            mdFirstName = mdFirstName.trim();
            mdLastName = mdLastName.trim();
        }


        LOINC_CODE lc = null;
        SNOMED_MASTER sm = null;




        try
        {
            lc = getLoincCodes(obxRecord.getSeqResultName(),"DRUG");
//                lc = getLoincCodes(obxRecord.getSeqResultName(), "DRUG");
//            } else {
            //sm = getSnomedMaster(obxRecord.getSeqResultName(),null);

            // OBX 1

            terser.set(obx,1,0,1,1,Integer.toString(obxrep + 1));

            // OBX 2

            String testResultType = null;

            terser.set(obx,2,0,1,1,"SN");


            // OBX 3
            terser.set(obx,3,0,1,1,lc.getLoinccode());
            terser.set(obx,3,0,2,1,lc.getLoincname());
            terser.set(obx,3,0,3,1,"LN");


            if ((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)) {
                StringBuilder builder = new StringBuilder();
                builder.append(obxRecord.getSubTestCode()).append("-1-001");

                terser.set(obx,3,0,4,1,builder.toString());
                terser.set(obx,3,0,5,1,obxRecord.getSeqTestName());
                terser.set(obx,3,0,6,1,"L");
            }



            // OBX 4

            terser.set(obx,4,0,1,1,"1");

            // OBX 5
            String op = null;
            String val = null;
            String result = obxRecord.getTextualNumResult();



            if(result.matches("^<=(.*)")) {
                op = result.substring(0, (result.indexOf("<=") + 2));
                val = result.substring((result.indexOf("<=") + 2));
            } else if (result.matches("^>=(.*)")) {
                op = result.substring(0, (result.indexOf(">=") + 2));
                val = result.substring((result.indexOf(">=") + 2));
            } else if (result.matches("^<(.*)")) {
                op = result.substring(0, (result.indexOf("<") + 1));
                val = result.substring((result.indexOf("<") + 1));
            } else if (result.matches("^>(.*)")) {
                op = result.substring(0, (result.indexOf(">") + 1));
                val = result.substring((result.indexOf(">") + 1));

            }else{
                op = "=";
                val = result;
            }

            testResultType = "SN";

            if(testResultType.equalsIgnoreCase("SN")) {


                terser.set(obx,5,0,1,1,op);
                terser.set(obx,5,0,2,1,val);
            } else {
                if(obxRecord.getMicroOrganismName() != null) {

                    terser.set(obx,5,0,1,1,sm.getSnomedcode());
                    terser.set(obx,5,0,2,1,sm.getPreferredname());
                    terser.set(obx,5,0,3,1,"SCT");
                }

            }

            // OBX 6 Units

            sm = getSnomedMaster("ug/mL","UNITS");
            terser.set(obx,6,0,1,1,sm.getSnomedcode());
            terser.set(obx,6,0,2,1,sm.getPreferredname());
            terser.set(obx,6,0,3,1,"UCUM");

            // OBX 7 Reference Range

            // OBX 8 Abnormal Flag
            sm = getSnomedMaster(obxRecord.getAbnormalFlag(),"HL70078");
            terser.set(obx,8,0,1,1,sm.getSnomedcode());
            terser.set(obx,8,0,2,1,sm.getPreferredname());
            terser.set(obx,8,0,3,1,"HL70078");



            // OBX 11 Result Status

            terser.set(obx,11,0,1,1,obxRecord.getResultStatus());

            // OBX 14 Observation Date
            terser.set(obx,14,0,1,1,repo.convertDate(obxRecord.getCollectionDateTime()));


            // OBX 15 CLIA Number^Performing Lab^CLIA

            // OBX-17 OrderMethod
            terser.set(obx,17,0,1,1,"SPECTRA-LIS");

            terser.set(obx,17,0,2,1,obxRecord.getDeviceName());


            // OBX 18



            // OBX 19 DateTime of Analysis

            terser.set(obx,19,0,1,1,repo.convertDate(obxRecord.getCollectionDateTime()));

            //OBX 23

            terser.set(obx,23,0,1,1,"Spectra East");
            terser.set(obx,23,0,2,1,"D");


            // OBX 23.6.1
            terser.set(obx,23,0,6,1,"CLIA");
            terser.set(obx,23,0,6,2,"2.16.840.1.114222.4.3.22.29.7.1");
            terser.set(obx,23,0,6,3,"ISO");

            // OBX 23.7
            terser.set(obx,23,0,7,1,"XX");

            terser.set(obx,23,0,10,1,MSH4_2);

            // OBX 24
            if((infoSpectraAddress2 != null) && (infoSpectraAddress2.length() > 0)){
                StringBuilder performOrgAddBuilder = new StringBuilder();
                performOrgAddBuilder.append(infoSpectraAddress).append(" ").append(infoSpectraAddress2);

                terser.set(obx,24,0,1,1,performOrgAddBuilder.toString());
            }else{

                terser.set(obx,24,0,1,1,"8 King Road");
            }


            terser.set(obx,24,0,3,1,"Rockleigh");
            terser.set(obx,24,0,4,1,"NJ");
            terser.set(obx,24,0,5,1,"07647");

            // OBX 25
            terser.set(obx,25,0,2,1,mdLastName);
            terser.set(obx,25,0,3,1,mdFirstName);


        } catch (HL7Exception e){
            log.error("HL7 Error: {}",e.getMessage());
        }



        return oru;
    }


    public static ORU_R01 obxSegment(PatientRecord patientRecord, ORU_R01 oru,int obxrep,int obrctr,ORU_R01_ORDER_OBSERVATION orderObservation) {

        String infoSpectraAddress = null;
        String infoSpectraAddress2 = null;
        String infoSpectraPhone = null;
        String infoSpectraCity = null;
        String infoSpectraZip  = null;
        String infoSpectraState = null;
        String medicalDirector = patientRecord.getMedicalDirector();

        String mdFirstName = null;
        String mdLastName = null;
        if(medicalDirector.indexOf("M.D.") != -1){
            medicalDirector = medicalDirector.substring("M.D.".length());
            if(medicalDirector.indexOf(".") != -1){
                mdFirstName =  "Alex"; //medicalDirector.substring(0, (medicalDirector.lastIndexOf(".") + 1));
                mdLastName = "Ryder";//medicalDirector.substring((medicalDirector.lastIndexOf(".") + 1));
            }
        }
        if(medicalDirector.indexOf(" MD") != -1){
            medicalDirector = medicalDirector.substring(0, (medicalDirector.indexOf(" MD") + 1));
            mdFirstName = medicalDirector.substring(0, (medicalDirector.indexOf(" ") + 1));
            mdLastName = medicalDirector.substring(medicalDirector.indexOf(" "));
            if(mdLastName.indexOf(",") != -1){
                mdLastName = mdLastName.replaceAll(",", "");
            }
        }

        if(medicalDirector.indexOf("Dr") != -1){

            mdFirstName = "C.O.";
            mdLastName = "Burdick";

        }
        log.warn("getMessage(): mdFirstName: " + (mdFirstName == null ? "NULL" : mdFirstName));
        log.warn("getMessage(): mdLastName: " + (mdLastName == null ? "NULL" : mdLastName));



        if(patientRecord.getPerformingLabId().indexOf("SW") != -1){
            mdFirstName = "C.O.";
            mdLastName = "Burdick";
        } else {
            mdFirstName = mdFirstName.trim();
            mdLastName = mdLastName.trim();
        }

        Terser terser = new Terser(oru);

        LOINC_CODE lc = null;
        SNOMED_MASTER sm = null;


        OBX obx = orderObservation.getOBSERVATION(obrctr).getOBX();

        OBXRecord obxRecord = patientRecord.getObxList().stream()
                .filter(t -> t.getSeqResultName().matches("Isolate(.*)"))
                .findFirst().get();



        try
        {
            lc = getLoincCodes(obxRecord.getMicroOrganismName(),"ORG");
//                lc = getLoincCodes(obxRecord.getSeqResultName(), "DRUG");
//            } else {
                sm = getSnomedMaster(obxRecord.getMicroOrganismName(),null);
//            }

            // OBX 1

            terser.set(obx,1,0,1,1,Integer.toString(1));

            // OBX 2

            String testResultType = null;

            terser.set(obx,2,0,1,1,"CE");

//            if(obxRecord.getMicroOrganismName() != null &&
//                    obrctr == 0){
//                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-2", "CE");
//                //obx.getValueType().setValue("CE");
//                testResultType = "CE";
//            } else {
//                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-2", "SN");
//                //obx.getValueType().setValue("SN");
//                testResultType = "SN";
//            }


            // OBX 3



            terser.set(obx,3,0,1,1,lc.getLoinccode());

            terser.set(obx,3,0,2,1,lc.getLoincname());
            terser.set(obx,3,0,3,1,"LN");

            if ((obxRecord.getSubTestCode() != null) && (obxRecord.getSubTestCode().length() > 0)) {
                StringBuilder builder = new StringBuilder();
                builder.append(obxRecord.getSubTestCode()).append("-1-001");

                terser.set(obx,3,0,4,1,builder.toString());
                terser.set(obx,3,0,5,1,obxRecord.getSeqTestName());
                terser.set(obx,3,0,6,1,"L");


            }



            // OBX 4
            //terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-4", "1");
            terser.set(obx,4,0,1,1,"1");

            // OBX 5
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
                op = "=";
                val = result.substring(2);
            }

            testResultType = "CE";

            if(testResultType.equalsIgnoreCase("SN")) {

//                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", op);
//                 terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", val);
                terser.set(obx,5,0,1,1,op);
                terser.set(obx,5,0,2,1,val);
            } else {
                if(obxRecord.getMicroOrganismName() != null) {
//                    terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-1", sm.getSnomedcode());
//                    terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-2", sm.getPreferredname());
//                    terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5-3", "SCT");
                    terser.set(obx,5,0,1,1,sm.getSnomedcode());
                    terser.set(obx,5,0,2,1,sm.getPreferredname());
                    terser.set(obx,5,0,3,1,"SCT");
                }

            }

            // OBX 6 Units
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-1", obxRecord.getUnits());
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-2", "");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-6-3", "UCUM");

            // OBX 7 Reference Range
            //obx.getReferencesRange().setValue(obxRecord.getRefRange());
            //terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-7", obxRecord.getRefRange());

            // OBX 8 Abnormal Flag


            // OBX 11 Result Status
            //obx.getObservationResultStatus().setValue(obxRecord.getResultStatus());
            //terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-11", obxRecord.getResultStatus());
            terser.set(obx,11,0,1,1,obxRecord.getResultStatus());

            // OBX 14 Observation Date
//							StringBuilder releaseDateTimeBuilder = new StringBuilder();
//							releaseDateTimeBuilder.append(obxRecord.getReleaseDateTimeStr()).append(obxRecord.getReleaseTime().substring(8, 12));
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-14-1", repo.convertDate(obxRecord.getCollectionDateTime()));
            terser.set(obx,14,0,1,1,repo.convertDate(obxRecord.getCollectionDateTime()));


            // OBX 15 CLIA Number^Performing Lab^CLIA
//							obx.getObx15_ProducerSReference().getCe1_Identifier().setValue(sendingFacilityId);
//							obx.getObx15_ProducerSReference().getCe2_Text().setValue(fileSendingFacility);
//							obx.getObx15_ProducerSReference().getCe3_NameOfCodingSystem().setValue("CLIA");


            // OBX-17 OrderMethod
            //obx.getObservationMethod(i).getIdentifier().setValue("SPECTRA-LIS");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-17-1", "SPECTRA-LIS");
            terser.set(obx,17,0,1,1,"SPECTRA-LIS");

            //obx.getObx17_ObservationMethod(i).getText().setValue(obxRecord.getDeviceName());
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-17-2", obxRecord.getDeviceName());
            terser.set(obx,17,0,2,1,obxRecord.getDeviceName());


            // OBX 18



            // OBX 19 DateTime of Analysis
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-19-1", repo.convertDate(obxRecord.getCollectionDateTime()));
            terser.set(obx,17,0,1,1,repo.convertDate(obxRecord.getCollectionDateTime()));

            //OBX 23
            //obx.getPerformingOrganizationName().getOrganizationIdentifier().setValue(sendingFacilityId);
//							obx.getPerformingOrganizationName().getOrganizationName().setValue(fileSendingFacility);
//							obx.getPerformingOrganizationName().getOrganizationNameTypeCode().setValue("D");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-1", MSH4_2);
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-2", "D");

            terser.set(obx,23,0,1,1,"Spectra East");
            terser.set(obx,23,0,2,1,"D");
            //obx.getPerformingOrganizationName().getIdentifierTypeCode().setValue("XXX");

            // OBX 23.6.1
//							obx.getPerformingOrganizationName().getAssigningAuthority().getNamespaceID().setValue("CLIA");
//							obx.getPerformingOrganizationName().getAssigningAuthority().getUniversalID().setValue("2.16.840.1.114222.4.3.22.29.7.1");
//							obx.getPerformingOrganizationName().getAssigningAuthority().getUniversalIDType().setValue("ISO");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-6-1", "CLIA");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-6-2", "2.16.840.1.114222.4.3.22.29.7.1");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-6-3", "ISO");
            terser.set(obx,23,0,6,1,"CLIA");
            terser.set(obx,23,0,6,2,"2.16.840.1.114222.4.3.22.29.7.1");
            terser.set(obx,23,0,6,3,"ISO");

            // OBX 23.7
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-7", "XX");
            terser.set(obx,23,0,7,1,"XX");

//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-23-10", MSH4_2);
            terser.set(obx,23,0,10,1,MSH4_2);
//
            //obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
            //obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
            if((infoSpectraAddress2 != null) && (infoSpectraAddress2.length() > 0)){
                StringBuilder performOrgAddBuilder = new StringBuilder();
                performOrgAddBuilder.append(infoSpectraAddress).append(" ").append(infoSpectraAddress2);
                //obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(performOrgAddBuilder.toString());
                //obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(performOrgAddBuilder.toString());
//                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-1", performOrgAddBuilder.toString());
                terser.set(obx,24,0,1,1,performOrgAddBuilder.toString());
            }else{
                //obx.getPerformingOrganizationAddress().getStreetAddress().getStreetName().setValue(infoSpectraAddress);
                //obx.getPerformingOrganizationAddress().getStreetAddress().getStreetOrMailingAddress().setValue(infoSpectraAddress);
//                terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-1", "8 King Road");
                terser.set(obx,24,0,1,1,"8 King Road");
            }

//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-3", "Rockleigh");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-4", "NJ");
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-24-5", "07647");
            terser.set(obx,24,0,3,1,"Rockleigh");
            terser.set(obx,24,0,4,1,"NJ");
            terser.set(obx,24,0,5,1,"07647");





//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-25-2", mdLastName);
//            terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-25-3", mdFirstName);
            terser.set(obx,25,0,2,1,mdLastName);
            terser.set(obx,25,0,3,1,mdFirstName);

//            SPM spm = orderObservation.getSPECIMEN().getSPM();
//
//            terser.set(spm,1,0,1,1,"1");


        } catch (HL7Exception e){
            log.error("HL7 Error: {}",e.getMessage());
        }



        return oru;
    }


        private static LOINC_CODE getLoincCodes(String organismname,String valuetype){
        MicroController microController = null;



        try {
            microController = new MicroController(new String [] {"db.json",null,null,null,"PROD"});
        } catch (IOException e) {
            log.error("MicroController error: {}",e.getMessage());
        }

        return microController.getMicroOrganismFilter("select * from micro_organism_filter where organismname = '" +
                organismname + "'");
    }

    private static SNOMED_MASTER getSnomedMaster(String localname, String valuetype){
        MicroController microController = null;

        try {
            microController = new MicroController(new String [] {"db.json",null,null,null,"PROD"});
        } catch (IOException e) {
            log.error("MicroController error: {}",e.getMessage());
        }

        return microController.getSnomedCode("select * from snomed_master where localname = '" +
                localname + "'");
    }
}
