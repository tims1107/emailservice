package com.spectra.asr.app;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Application;
import ca.uhn.hl7v2.conf.spec.message.Seg;
import ca.uhn.hl7v2.model.Group;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.*;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.ParserConfiguration;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import ca.uhn.hl7v2.util.Terser;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.spectra.asr.hl7.creator.v251.ILHL7Creator;
import com.spectra.asr.utils.file.writer.AsrFileWriter;
import com.spectra.controller.MicroController;
import com.spectra.dto.test.PatientRecord;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class MappingTestMain {

    private String pathway;

    private static String micro_qry = "select * from vw_micro_results";
    private MicroController microController;

    public static void main(String[] args) {
        new MappingTestMain(args);
    }

    MappingTestMain (String [] args) {
        this.pathway = "patientrecord.csv";

        //createMapTest();
        try {
            microController = new MicroController(args);
            microController._runProcess(micro_qry);
            //createTerser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Segment getSegment(Terser terser,String obj){

        try {
            return terser.getSegment(obj);
        } catch (HL7Exception e) {
            e.printStackTrace();
        }

       return null;

    }

    private String writeTerser(String message,String filename){
        AsrFileWriter.write(filename,message);
        return message;
    }

    private String getMessage(Message msg){
        HapiContext context = new DefaultHapiContext();
        Parser p = context.getGenericParser();




        try {
            Structure msh =msg.get("MSH");
            System.out.println("MSH -> " + p.encode(msh.getMessage()));
            return p.encode(msg);
        } catch (HL7Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void createTerser() throws Exception {

        StringBuilder sb = new StringBuilder();

        String fhs ="FHS|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|%1$s||%1$s\r" +
                "BHS|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|%1$s||%1$s\r" ;



//        String msg = "MSH|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^2.16.840.1.114222.4.3.22.29.7.1^ISO|ALNBS^2.16.840.1.114222.4.5.1^ISO|ALNBS^2.16.840.1.114222.4.5.1^ISO|20231114121721||ORU^R01^ORU_R01|202311141217034|T|2.5.1|||||USA||||PHLabReport-NoAck^ELR_Receiver^2.16.840.1.113883.9.11^ISO\r" +
//                "SFT|Spectra East|1.0|ASR|1||20231103\r" +
//                "PID|1||8002624163^^^Spectra East&31D0961672&ISO^PI||LASTMICRO^FIRSTMICRO^^^^^L||19610810|F||2054-5^Black^HL70005|97 HATTIE DRIVE^^LISMAN^AL^36912^USA||^PRN^PH^^1^252^7572784|||||||||N^Non-Hispanic^HL70189\r" +
//                "ORC|RE|652136Y82|2136Y82|2136Y82||||||||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^^^^NPI||^WPN^PH^^1^205^4872800|||||||4183:BMA Northwest Alabama^L|638 Tahoe Road^^Huntsville^AL^35594|^WPN^PH^^1^205^4872800|8 King Road^^Rockleigh^NJ^07647\r" +
//                "OBR|1|652136Y82|2136Y82|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750^Culture, Blood|||202202250000||||G||Recurring patient|20220226000000-0500||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|||||||\r" +
//                "OBX|1|CE|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750-1-001^Culture, Blood|1|6265002^Citrobacter freundii (organism)^SCT^^^^2.5.1|||A|||F|||202202250000|||SPECTRA-LIS^E-CATHEX||20220228000000-0500||||Spectra East^D^^^^Spectra East&31D0961672&CLIA^XX^^^31D0961672|8 King Road Rockleigh, NJ 07647^^Rockleigh^NJ^07647|^ Gupta ^Suresh \r" +
//                "OBR|2|652136Y82|2136Y82|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750^Culture, Blood^L|||202202250000||||G||Recurring patient|20220226000000-0500||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|43409-2&BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE&LN^1^Citrobacter freundii|||^2136Y82&HNAM_ORDERID&2.16.840.1.113883.3.7799.03&ISO\r" +
//                "OBX|1|SN|28-1^Ertapenem:Susc:Pt:Isolate:OrdQn:MIC^LN^750C^ERTAPENEM|1|<=^8|||S|||F|||20220422001200-0400|||||20220425104249-0400||||Spectra Labs^^^^^Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road^^Rockleigh^NJ^07647^USA^B\r" +
//                "SPM|1|652136Y82&Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO^652136Y82&Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO||446131002^Blood specimen obtained for blood culture^SCT^^^^1.0|||||||||||||202202250000|20220226\r";

        String ncmsg = "MSH|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240426101253||ORU^R01^ORU_R01|202404261012106|P|2.5.1|||||USA||||PHLabReport-NoAck^ELR_Receiver^2.16.840.1.113883.9.11^ISO\r" +
                "SFT|Spectra East|1.0|ASR|1||20240426\r" +
                "PID|1||8000296354^^^Spectra East&31D0961672&CLIA^PI||NCMICROLAST^NCMICROFNAME^^^^^L||19540628|F||2054-5^Black^HL70005|115 STRIGHT RD^APT 2^ROANOKE RAPIDS^NC^27870^USA||^PRN^PH^^1^236^45987|||||||||N^Non-Hispanic^HL70189\r" +
                "ORC|RE|602104BYK|2104BYK|2104BYK||||||||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^^^^NPI||^WPN^PH^^1^800^4333773|||||||Southaven External Test 1^L|525 Sycamore Dr^^Milpitas^CA^95035|^WPN^PH^^1^800^4333773|1280 Stateline Road East^^Southaven^MS^38671\r" +
                "OBR|1|602104BYK|2104BYK|43409-2^Bacteria identified:Prid:Pt:Isolate:Nom:Culture^LN^750^Culture, Blood|||20240313||||G||Recurring patient|20240314112700-0400||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^800^4333773|||||202403141153||LAB|F\r" +
                "OBX|1|CE|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750-1-001^Culture, Blood|1|6265002^Citrobacter freundii (organism)^SCT^^^^2.5.1|||A|||F|||202202250000|||SPECTRA-LIS^E-CATHEX||20220228000000-0500||||Spectra East^D^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road Rockleigh, NJ 07647^^Rockleigh^NJ^07647|^ Gupta ^Suresh \r" +
                "SPM|1|602104BYK&Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO^602104BYK&Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO||119364003^Serum specimen^SCT^^^^1.0||||119364003^Serum specimen^SCT|||||||||20240313|20240314\r" +
                "OBR|2|602104BYK|2104BYK|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750^Culture, Blood^L|||202202250000||||G||Recurring patient|20220226000000-0500||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|43409-2&BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE&LN^1^Citrobacter freundii|||^2104BYK&HNAM_ORDERID&2.16.840.1.113883.3.7799.03&ISO\r" +
                "OBX|1|SN|18864-9^Ampicillin:Susc:Pt:Isolate:OrdQn^LN^750C^Ampicillin|1|<=^8|||S|||F|||20220422001200-0400|||||20220425104249-0400||||Spectra Labs^^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road^^Rockleigh^NJ^07647^USA^B\r" ;

        String ilmsg = "MSH|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240426101253||ORU^R01^ORU_R01|202404261012106|P|2.5.1|||||USA||||PHLabReport-NoAck^ELR_Receiver^2.16.840.1.113883.9.11^ISO\r" +
                "SFT|Spectra East|1.0|ASR|1||20240426\r" +
                "PID|1||8000296354^^^Spectra East&31D0961672&CLIA^PI||NCMICROLAST^NCMICROFNAME^^^^^L||19540628|F||2054-5^Black^HL70005|115 STRIGHT RD^APT 2^ROANOKE RAPIDS^NC^27870^USA||^PRN^PH^^1^236^45987|||||||||N^Non-Hispanic^HL70189\r" +
                "ORC|RE|602104BYK|2104BYK|2104BYK||||||||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^^^^NPI||^WPN^PH^^1^800^4333773|||||||Southaven External Test 1^L|525 Sycamore Dr^^Milpitas^CA^95035|^WPN^PH^^1^800^4333773|1280 Stateline Road East^^Southaven^MS^38671\r" +
                "OBR|1|602104BYK|2104BYK|43409-2^Bacteria identified:Prid:Pt:Isolate:Nom:Culture^LN^750^Culture, Blood|||20240313||||G||Recurring patient|20240314112700-0400||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^800^4333773|||||202403141153||LAB|F\r" +
                "OBX|1|CE|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750-1-001^Culture, Blood|1|6265002^Citrobacter freundii (organism)^SCT^^^^2.5.1|||A|||F|||202202250000|||SPECTRA-LIS^E-CATHEX||20220228000000-0500||||Spectra East^D^^^^Spectra East&31D0961672&CLIA^XX^^^31D0961672|8 King Road Rockleigh, NJ 07647^^Rockleigh^NJ^07647|^ Gupta ^Suresh \r" +
                "SPM|1|602104BYK&Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO^602104BYK&Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO||119364003^Serum specimen^SCT^^^^1.0||||119364003^Serum specimen^SCT|||||||||20240313|20240314\r" +
                "OBR|2|602104BYK|2104BYK|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750^Culture, Blood^L|||202202250000||||G||Recurring patient|20220226000000-0500||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|43409-2&BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE&LN^1^Citrobacter freundii|||^2104BYK&HNAM_ORDERID&2.16.840.1.113883.3.7799.03&ISO\r" +
                "OBX|1|SN|18864-9^Ampicillin:Susc:Pt:Isolate:OrdQn^LN^750C^Ampicillin|1|<=^8|||S|||F|||20220422001200-0400|||||20220425104249-0400||||Spectra Labs^^^^^Spectra Labs&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road^^Rockleigh^NJ^07647^USA^B\r" ;



        String fileDate = new SimpleDateFormat("yyyyMMddHHmmZ").format(new Date());

        HapiContext context = new DefaultHapiContext();
        ParserConfiguration pc = context.getParserConfiguration();

        Parser p = context.getPipeParser();
        Message hapiMsg = p.parse(ncmsg);

        ORU_R01 oruMsg = (ORU_R01)hapiMsg;
//
        Terser terser = new Terser(hapiMsg);

        int obrnum = 0;
        // HL7 datetime format
        String newDate = new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());

        // MSH
        System.out.println(newDate);
        terser.set("/MSH-7",newDate);
        // D - Development
        terser.set("/MSH-11","D");

        //MSH.15	Accept Acknowledgment Type
        //MSH.16	Application Acknowledgment Type

        terser.set("/MSH-15","NE");
        terser.set("/MSH-16","NE");

        System.out.println(((ORU_R01) hapiMsg).getPATIENT_RESULT().getMessage());

        // SFT Segment
        terser.set("/SFT-1-2","L");
        terser.set("/SFT-1-6-1","Spectra Laboratories");
        terser.set("/SFT-1-6-2","2.16.840.1.114222.4.3.22.29.7.1");
        terser.set("/SFT-1-6-3","ISO");

        terser.set("/SFT-1-7","XX");

        terser.set("/SFT-1-10","Spectra Laboratories");


        // ORC Segment
        ORC orc = oruMsg.getPATIENT_RESULT().getORDER_OBSERVATION().getORC();

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-9-1","NPI");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-9-2","2.16.840.1.114222.4.3.22.29.7.1");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-9-3","");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-10","L");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-12-13","XX");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-21-6-1","Spectra Laboratories");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-21-6-2","2.16.840.1.114222.4.3.22.29.7.1");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-21-7","XX");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-22-6","USA");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-22-7","B");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-6","USA");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/ORC-24-7","B");


        //System.out.println("Text" + orc.encode());

        // OBR 1
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(0)/OBR-4-6","L");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-3-6","L");

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-8-1","");


        // OBR 2

        obrnum++;

        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-3-6","L");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-6-1","ug/mL");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-6-2","MicroGramsPerMilliLiter [Mass Concentration Units]");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBSERVATION(0)/OBX-6-3","UCUM");

        // HL7 Table -> HL70078
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-6-1","S");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + obrnum + ")/OBSERVATION(0)/OBX-6-2","Susceptible. Indicates for microbiology susceptibilities only.");
        terser.set("PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBSERVATION(0)/OBX-6-3","HL70078");





        // SPM Segment
        //terser.set("PATIENT_RESULT/SPM-4-1","446131002");
        //System.out.println(terser.get("PATIENT_RESULT/ORDER_OBSERVATION(1)/SPM-4-1"));
        Segment seg = terser.getFinder().findSegment("SPM",0);
        System.out.println(seg.getName());

        terser.set("SPM-4-1","446131002");
        terser.set("SPM-4-2","Blood specimen obtained for blood culture");
        terser.set("SPM-4-3","SCT");

        terser.set("SPM-8-1","NONE");
        terser.set("SPM-8-2","None");
        terser.set("SPM-8-3","HL70371");

        terser.set("SPM-17-1","20240313103412-0400");
        terser.set("SPM-17-2","20240313103412-0400");
        terser.set("SPM-18","20240314103132-0400");

//        Arrays.asList(seg.getNames()).stream()
//                .forEach(System.out::println);
//
//        oruMsg = (ORU_R01)hapiMsg;

//        MSH msh = oruMsg.getMSH();
//
//
//        getMessage(msh.getMessage());
//
//        System.out.println("Start **********");

//        System.out.println(terser.get("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBR-1"));
//        System.out.println(terser.get("PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(0)/OBX-2"));

//        Arrays.stream(oruMsg.getNames()).forEach(System.out::println);
//
//        System.out.println(terser.getFinder().iterate(true,false));

//       List<Structure> list = Arrays.asList(terser.getFinder().getCurrentChildReps()).stream()
//           .collect(Collectors.toList());
//
//        list.stream()
//                .map(t -> getMessage(t.getMessage()))
//                .forEach(System.out::println);


        //Arrays.stream(terser.getSegment("/").getNames())
        //        .forEach(System.out::println);
//
        //String sendingApplication = terser.get("/.MSH-2");
        //Group obr2 = terser.getFinder().findGroup("PATIENT_RESULT",0);
        //System.out.println(hapiMsg.printStructure());

//        for (Structure s : terser.getSegment("/.OBR").) {
//            try {
//
//                System.out.println("Name: " + s.getName());
//            } catch (Exception e){
//                log.error(e.getMessage());
//            }
//        }
        //String newDate = new SimpleDateFormat("yyyyddMMHHmmss").format(new Date());

        sb.append(String.format(fhs,newDate)).append(p.encode(hapiMsg)).append("BTS|" + 1 + "\rFTS|1\r");
        System.out.println(sb.toString());
        writeTerser(sb.toString(),"msg" + fileDate);


    }

    // Test code
    private void testTerserCode() throws IOException, HL7Exception {
        // ************************************************************************
        // Begin create hl7
        ORU_R01 oru = new ORU_R01();
        oru.initQuickstart("ORU","R01","P" );

        Terser terser = new Terser(oru);

        // Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO
        // 20240425

        // SFT
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMdd");
        SFT sft = oru.getSFT();
        sft.getSoftwareVendorOrganization().getOrganizationName().setValue("Spectra Labs");
        sft.getSoftwareCertifiedVersionOrReleaseNumber().setValue("1.0");
        sft.getSoftwareProductName().setValue("ASR");
        sft.getSoftwareBinaryID().setValue("1");
        String installDate = dateFormat2.format(new Date());
        sft.getSoftwareInstallDate().getTime().setValue(installDate);

        // PID segment
        ORU_R01_PATIENT_RESULT patientResult = oru.getPATIENT_RESULT();
        ORU_R01_PATIENT patient = patientResult.getPATIENT();

        // 8000525523^^^Spectra South&25D2227753&CLIA^PI

        // Begin PID set segment values
        PID pid = patient.getPID();

        pid.getSetIDPID().setValue("1");
        terser.set("/.PID-3-1","8000525523");
        terser.set("/.PID-3-2","");
        terser.set("/.PID-3-3","");
        terser.set("/.PID-3-4-1","Spectra South");
        terser.set("/.PID-3-4-2","25D2227753");
        terser.set("/.PID-3-4-3","CLIA");
        terser.set("/.PID-3-5","PI");

        //PID.5.1	Family Name
        terser.set("/.PID-5-1","ILLINOISTEST");
        terser.set("/.PID-5-2","CHITOWN");

        // ORC segment
        ORU_R01_ORDER_OBSERVATION orderObservation = patientResult.getORDER_OBSERVATION(0);
        ORU_R01_ORDER_OBSERVATION orderObservation2 = patientResult.getORDER_OBSERVATION(1);

        ORC orc = orderObservation.getORC();
        orc.getOrderControl().setValue("RE");

        // OBR Parent
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(" + 0 + ")/OBR-1", "1");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(" + 0 + ")/OBR-4-1", "43409-2");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(" + 0 + ")/OBR-4-2", "Bacteria identified:Prid:Pt:Isolate:Nom:Culture");


        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(" + 0 + ")/OBX-1", "" + (1));
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(" + 0 + ")/OBX-3", "ST" );
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(" + 0 + ")/OBX-4-1", "43409-2");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(" + 0 + ")/OBX-4-2", "Bacteria identified:Prid:Pt:Isolate:Nom:Culture" );
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(" + 0 + ")/OBX-5", "This is the value for rep " + 0 );

        //OBR obr = orderObservation2.getOBR();
        //obr.getSetIDOBR().setValue("2");


        // OBR Child - Drug sensitivity
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBR-1", "2");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBR-4-1", "43409-2");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(" + 1 + ")/OBR-4-2", "Bacteria identified:Prid:Pt:Isolate:Nom:Culture");

        //OBX obx = orderObservation2.getOBSERVATION().getOBX();
        //obx.getSetIDOBX().setValue("1");

        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 0 + ")/OBX-1", "1");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 0 + ")/OBX-4-1", "43409-2");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 0 + ")/OBX-4-2", "Bacteria identified:Prid:Pt:Isolate:Nom:Culture" );

        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 1 + ")/OBX-1", "1");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 1 + ")/OBX-4-1", "43409-2");
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 1 + ")/OBX-4-2", "Bacteria identified:Prid:Pt:Isolate:Nom:Culture" );
        // SPM segment (1)
        terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/SPECIMEN/SPM-1", "1");
        //terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 0 + ")/OBX-4-1", "43409-2");
        //terser.set("/PATIENT_RESULT/ORDER_OBSERVATION(1)/OBSERVATION(" + 0 + ")/OBX-4-2", "Bacteria identified:Prid:Pt:Isolate:Nom:Culture" );

        System.out.println(oru.printStructure());


        //AsrFileWriter.write("c:/projects/asr/onboardtesting/msg.txt",p.encode(oru));


    }

    private void createHL7(){

        String message = null;
        ILHL7Creator creator = new ILHL7Creator();

        HapiContext context = new DefaultHapiContext();

        // Create the MCF. We want all parsed messages to be for HL7 version 2.5,
        // despite what MSH-12 says.
        CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.5.1");
        context.setModelClassFactory(mcf);

        // Pass the MCF to the parser in its constructor
        PipeParser parser = context.getPipeParser();

        // The parser parses the v2.3 message to a "v25" structure
        //ca.uhn.hl7v2.model.v25.message.ORU_R01 msg = (ca.uhn.hl7v2.model.v25.message.ORU_R01) parser.parse(v23message);

        // 20169838-v23
        //System.out.println(msg.getMSH().getMessageControlID().getValue());

        // The parser also parses the v2.5 message to a "v25" structure



        ORU_R01 oru = null;

        oru = new ORU_R01();

        try {
            oru.initQuickstart("ORU", "R01", "P");

            MSH msh = oru.getMSH();
            msh.getReceivingFacility().getUniversalID().setValue("123");

            System.out.println(oru);
            SFT sft = oru.getSFT();
            sft.getSoftwareVendorOrganization().getOrganizationName().setValue("Spectra East");
//            sft.getSoftwareCertifiedVersionOrReleaseNumber().setValue("1.0");
//            sft.getSoftwareProductName().setValue("ASR");
//            sft.getSoftwareBinaryID().setValue("1");
//            String installDate = new SimpleDateFormat("yyyyddMM").format(new Date());
//            sft.getSoftwareInstallDate().getTime().setValue(installDate);


            //Begin PID
            ORU_R01_PATIENT_RESULT patientResult = oru.getPATIENT_RESULT();
            ORU_R01_PATIENT patient = patientResult.getPATIENT();

            System.out.println(oru);

            // Begin PID set segment values
            PID pid = patient.getPID();
//
//
            pid.getSetIDPID().setValue("1");

            ORU_R01_ORDER_OBSERVATION orderObservation = patientResult.getORDER_OBSERVATION();

            System.out.println(oru);

            ORC orc = orderObservation.getORC();
            orc.getOrderControl().setValue("RE");

            OBR obr = orderObservation.getOBR();
            obr.getSetIDOBR().setValue("1");

            ORU_R01_OBSERVATION observation = orderObservation.getOBSERVATION(0);

            OBX obx = observation.getOBX();
            obx.getSetIDOBX().setValue("1");

            //SPM spm = orderObservation.getSPECIMEN().getSPM();
            //spm.getSetIDSPM().setValue("1");

            Message msg = oru.getMessage();

            System.out.println(oru.printStructure());

        } catch (HL7Exception e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @FunctionalInterface
    interface mapList {
        void newlist();

        static String [] getPat(List<PatientRecord> patrecord){

           return null;
        }


    }

    private String [] strArr(PatientRecord t){
        System.out.println(t);
        //String [] newstr = Collections.addAll(t);
        return null;
    }

    private void writePatientRecord(List<PatientRecord> patientRecord)   {

        patientRecord.stream()
                .forEach(System.out::println);

        try {
            Writer writer = new FileWriter(pathway);

            StatefulBeanToCsv<PatientRecord> beanToCsv = new StatefulBeanToCsvBuilder<PatientRecord>(writer)
                    .build();

            beanToCsv.write(patientRecord);
            writer.close();

        } catch (IOException ioe) {

        } catch (CsvDataTypeMismatchException cdtm) {

        } catch (CsvRequiredFieldEmptyException crfe) {

        } finally {

        }



    }

    private void createMapTest(){
        Map<String, List<PatientRecord>> patMapList = new HashMap<>();
        List<PatientRecord> listPatRecord = new ArrayList<>();
        listPatRecord.add(new PatientRecord("ABC","Smith"));
        listPatRecord.add(new PatientRecord("ABD","Jones"));




        patMapList.put("ABC",listPatRecord);

        List<PatientRecord> list = patMapList.entrySet().stream()
                .map(t -> t.getValue())
                .flatMap(t -> t.stream())

                //.map(t -> writePatientRecord(t))
                .collect(Collectors.toList());

         //List<String[]> arrlist = list.stream()
         //        .toArray(PatientRecord::new );

         list.stream().forEach(System.out::println);

         list.stream()
                 .collect(Collectors.toMap(PatientRecord::getOrdernumber,Function.identity()));

                 //.forEach(System.out::println);

//        list.stream()
//                .map(t -> writePatientRecord(t))
//                .forEach(System.out::println);


        writePatientRecord(list);

    }

    public void writeLine(List<String[]> line) throws FileNotFoundException {
        try
        {
            FileOutputStream fos = new FileOutputStream(pathway);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            CSVWriter writer = new CSVWriter(osw);
            writer.writeAll(line);
            writer.close();
            osw.close();
            fos.close();
        } catch (Exception e){
            log.error(e.getMessage());
        }
    }

}
