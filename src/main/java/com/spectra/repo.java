package com.spectra;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class repo {



    public static String newDate = new SimpleDateFormat("yyyyMMddHHmmssZ").format(new Date());

    public static String IL_FILE_MASK = "{0}.HL7.{1}.tst";

    //SPECTRAEAST.31D0961672.20240103140000.txt

    public static String getFileName(String state){
        switch (state){
            case "IL":
                return  MessageFormat.format(IL_FILE_MASK, new Object[]{ state, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) });
            case "NC":
                return  MessageFormat.format("SPECTRAEAST.31D0961672.{0}.hl7", new Object[]{ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) });
            default:
                return "NO STATE";
        }

    }

    public static String setMicroTemplate(String template){
        StringBuilder sb = new StringBuilder();


        return sb.append(template).append(AMPH).toString();

    }

    public static String fhs ="FHS|^~\\&|Spectra East^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|%1$s||%1$s\r" +
            "BHS|^~\\&|Spectra East^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|%1$s||%1$s\r" ;

    public static String observationDate(Date date){
        return new SimpleDateFormat("yyyyMMddHHmmZ").format(Calendar.getInstance().getTime());
    }

    public static String convertDate(Date date){
        return new SimpleDateFormat("yyyyMMddHHmmZ").format(date);
    }



    public static String AMPH =   "OBR|2|602104BYK|2104BYK|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750^Culture, Blood^L|||202202250000||||G||Recurring patient|20220226000000-0500||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|43409-2&BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE&LN^1^Citrobacter freundii|||\r" +
           "OBX|1|SN|18864-9^Ampicillin:Susc:Pt:Isolate:OrdQn^LN^750C^Ampicillin|1|<=^8|||S|||F|||20220422001200-0400|||||20220425104249-0400||||Spectra East^^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road^^Rockleigh^NJ^07647^USA^B\r" ;


    public static String ncmsg = "MSH|^~\\&|Spectra East^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240426101253||ORU^R01^ORU_R01|202404261012106|P|2.5.1|||||USA||||PHLabReport-NoAck^ELR_Receiver^2.16.840.1.113883.9.11^ISO\r" +
            "SFT|Spectra East|1.0|ASR|1||20240426\r" +
            "PID|1||8000296354^^^Spectra East&31D0961672&CLIA^PI||NCLNAMETEST^NCFNAMETEST^^^^^L||19540628|F||2054-5^Black^HL70005|115 STRIGHT RD^APT 2^ROANOKE RAPIDS^NC^27870^USA||^PRN^PH^^1^236^45987|||||||||N^Non-Hispanic^HL70189\r" +
            "ORC|RE|602104BYK|2104BYK|2104BYK||||||||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^^^^NPI||^WPN^PH^^1^800^4333773|||||||Southaven External Test 1^L|525 Sycamore Dr^^Milpitas^CA^95035|^WPN^PH^^1^800^4333773|8 King Rd^^Southaven^MS^38671\r" +
            "OBR|1|602104BYK|2104BYK|43409-2^Bacteria identified:Prid:Pt:Isolate:Nom:Culture^LN^750^Culture, Blood|||20240313||||G||Recurring patient|20240314112700-0400||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^800^4333773|||||202403141153||LAB|F\r" +
            "OBX|1|CE||1||||A|||F|||202202250000|||SPECTRA-LIS^E-CATHEX||20220228000000-0500||||Spectra East^D^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road Rockleigh, NJ 07647^^Rockleigh^NJ^07647|^ Gupta ^Suresh \r" +
            "SPM|1|602104BYK&Spectra East&2.16.840.1.114222.4.3.22.29.7.1&ISO^602104BYK&Spectra East&2.16.840.1.114222.4.3.22.29.7.1&ISO||119364003^Serum specimen^SCT^^^^1.0||||119364003^Serum specimen^SCT|||||||||20240313|20240314\r" ;
//            "OBR|2|602104BYK|2104BYK|43409-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750^Culture, Blood^L|||202202250000||||G||Recurring patient|20220226000000-0500||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|43409-2&BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE&LN^1^Citrobacter freundii|||\r" +
//            "OBX|1|SN|18864-9^Ampicillin:Susc:Pt:Isolate:OrdQn^LN^750C^Ampicillin|1|<=^8|||S|||F|||20220422001200-0400|||||20220425104249-0400||||Spectra East^^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road^^Rockleigh^NJ^07647^USA^B\r" ;


    public static String ilmsg = "MSH|^~\\&|Spectra East^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|IL-ELR|ILDOH|202404190132||ORU^R01^ORU_R01|202404190132038|P|2.5.1|||||||||PHLabReport-NoAck^ELR_Receiver^2.16.840.1.113883.9.11^ISO\r" +
            "SFT|Spectra East|1.0|ASR|1||20240419\r" +
            "PID|1||8000296354^^^Spectra East&31D0961672&CLIA^PI||ILLINOISTEST^CHITOWN^^^^^L||19540628|F||2054-5^Black^HL70005|123 TEST AVE TEST^^CHICAGO^IL^60629^USA||^PRN^PH^^1^236^45987|||||||||N^Non-Hispanic^HL70189\r" +
            "ORC|RE|xxx|xxx|xxx||||||||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^^^^NPI||^WPN^PH^^1^800^4333773|||||||Southaven External Test 1^L|525 Sycamore Dr^^Milpitas^CA^95035|^WPN^PH^^1^800^4333773|1280 Stateline Road East^^Southaven^MS^38671\r" +
            "OBR|1|xx|xx|xx-2^xx identified:Prid:Pt:Isolate:Nom:Culture^LN^xx^xx, Blood|||20240101||||G||Recurring patient|20240314112700-0400||1236545663^TESTING^PHYSICIAN^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^800^4333773|||||202403141153||LAB|F\r" +
            "OBX|1|CE|xx-2^xx IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^xx-1-001^xx, Blood|1||||A|||F|||202202250000|||SPECTRA-LIS^E-CATHEX||20220228000000-0500||||Spectra East^D^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road Rockleigh, NJ 07647^^Rockleigh^NJ^07647|^ Gupta ^Suresh \r" +
            "SPM|1|xx&Spectra East&2.16.840.1.114222.4.3.22.29.7.1&ISO^xx&Spectra East&2.16.840.1.114222.4.3.22.29.7.1&ISO||119364003^Serum specimen^SCT^^^^1.0||||119364003^Serum specimen^SCT|||||||||20240313|20240314\r" ;
//            "OBR|2|xx|xx|xx-2^BACTERIA IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE^LN^750C^AMPICILLIN^L|||20240101||||G||Recurring patient|20240101||1407858632^KENDRICK^WILLIAM^^^^^^NPI&31D0961672&CLIA^L^^^NPI|^WPN^PH^^1^205^4872800|||||202202280000||LAB|F|xx-2&xx IDENTIFIED:PRID:PT:ISOLATE:NOM:CULTURE&LN^1^xx freundii|||\r" +
//            "OBX|1|SN|18864-9^Ampicillin:Susc:Pt:Isolate:OrdQn^LN^750C^Ampicillin|1|<=^8|||S|||F|||20220422001200-0400|||||20240101||||Spectra East^^^^^CLIA&2.16.840.1.114222.4.3.22.29.7.1&ISO^XX^^^31D0961672|8 King Road^^Rockleigh^NJ^07647^USA^B\r" ;



}
