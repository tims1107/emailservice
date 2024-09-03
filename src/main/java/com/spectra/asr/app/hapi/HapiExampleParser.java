package com.spectra.asr.app.hapi;

/**
 The contents of this file are subject to the Mozilla Public License Version 1.1
 (the "License"); you may not use this file except in compliance with the License.
 You may obtain a copy of the License at http://www.mozilla.org/MPL/
 Software distributed under the License is distributed on an "AS IS" basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
 specific language governing rights and limitations under the License.

 The Original Code is "ExampleParseMessages.java".

 The Initial Developer of the Original Code is University Health Network. Copyright (C)
 2001.  All Rights Reserved.

 Contributor(s): ______________________________________.

 Alternatively, the contents of this file may be used under the terms of the
 GNU General Public License (the  ?GPL?), in which case the provisions of the GPL are
 applicable instead of those above.  If you wish to allow use of your version of this
 file only under the terms of the GPL and not to allow others to use your version
 of this file under the MPL, indicate your decision by deleting  the provisions above
 and replace  them with the notice and other provisions required by the GPL License.
 If you do not delete the provisions above, a recipient may use your version of
 this file under either the MPL or the GPL.

 */



import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v22.datatype.PN;
import ca.uhn.hl7v2.model.v22.message.ADT_A01;

import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.MSH;
import ca.uhn.hl7v2.parser.EncodingNotSupportedException;
import ca.uhn.hl7v2.parser.Parser;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * Example code for parsing messages.
 *
 * @author <a href="mailto:jamesagnew@sourceforge.net">James Agnew</a>
 * @version $Revision: 1.1 $ updated on $Date: 2007-02-19 02:24:46 $ by $Author: jamesagnew $
 */

@Slf4j
public class HapiExampleParser
{

    /**
     * A simple example of parsing a message
     */
    public static void main(String[] args) {
        String msg = "MSH|^~\\&|HIS|RIH|EKG|EKG|199904140038||ADT^A01||P|2.2\r"
                + "PID|0001|00009874|00001122|A00977|SMITH^JOHN^M|MOM|19581119|F|NOTREAL^LINDA^M|C|564 SPRING ST^^NEEDHAM^MA^02494^US|0002|(818)565-1551|(425)828-3344|E|S|C|0000444444|252-00-4414||||SA|||SA||||NONE|V1|0001|I|D.ER^50A^M110^01|ER|P00055|11B^M011^02|070615^BATMAN^GEORGE^L|555888^NOTREAL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^NOTREAL^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|199904101200||||5555112333|||666097^NOTREAL^MANNY^P\r"
                + "NK1|0222555|NOTREAL^JAMES^R|FA|STREET^OTHER STREET^CITY^ST^55566|(222)111-3333|(888)999-0000|||||||ORGANIZATION\r"
                + "PV1|0001|I|D.ER^1F^M950^01|ER|P000998|11B^M011^02|070615^BATMAN^GEORGE^L|555888^OKNEL^BOB^K^DR^MD|777889^NOTREAL^SAM^T^DR^MD^PHD|ER|D.WT^1A^M010^01|||ER|AMB|02|070615^VOICE^BILL^L|ER|000001916994|D||||||||||||||||GDD|WA|NORM|02|O|02|E.IN^02D^M090^01|E.IN^01D^M080^01|199904072124|199904101200|||||5555112333|||666097^DNOTREAL^MANNY^P\r"
                + "PV2|||0112^TESTING|55555^PATIENT IS NORMAL|NONE|||19990225|19990226|1|1|TESTING|555888^NOTREAL^BOB^K^DR^MD||||||||||PROD^003^099|02|ER||NONE|19990225|19990223|19990316|NONE\r"
                + "AL1||SEV|001^POLLEN\r"
                + "GT1||0222PL|NOTREAL^BOB^B||STREET^OTHER STREET^CITY^ST^77787|(444)999-3333|(222)777-5555||||MO|111-33-5555||||NOTREAL GILL N|STREET^OTHER STREET^CITY^ST^99999|(111)222-3333\r"
                + "IN1||022254P|4558PD|BLUE CROSS|STREET^OTHER STREET^CITY^ST^00990||(333)333-6666||221K|LENIX|||19980515|19990515|||PATIENT01 TEST D||||||||||||||||||02LL|022LP554";

        String oru = //"FHS|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240207110030||20240207110030\r" +
                //"BHS|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240207110030||20240207110030\r" +
                "MSH|^~\\&|Spectra East^31D0961672^CLIA|Spectra East^31D0961672^CLIA|NC-ELR|NCPHD|202402091114||ORU^R01^ORU_R01|202402091114001|P|2.5.1|||||||||PHLabReport-NoAck^^2.16.840.1.114222.4.10.3^ISO\r" +
                "SFT|Spectra East|1.0|ASR|1||20240209\r" +
                "PID|1||8000494975^^^Spectra South&25D2227753&CLIA^PI||NECTARINETEST^NANO||19440915|M||2131-1^Other Race^CDCREC^U^Unknown^L^2.5.1^1.0|35 THIRTYFIVE ST^^WARNE^NC^28909^USA||^PRN^PH^^1^353^5353535|||||||||2186-5^Unknown^HL70189^U^Unknown^L^2.5.1^v_unknown\r" +
                "ORC|RE|119611NFK^Spectra South^25D2227753^CLIA|9611NFK^Spectra South^25D2227753^CLIA|9611NFK^Spectra South^25D2227753^CLIA||||||||1277777777^DOCTOR^TESTSEVEN^^^^^^NPI&25D2227753&CLIA^^^^NPI||^WPN^PH^^1^800^4333773|||||||Southaven External Test 1|525 Sycamore Dr^^Milpitas^CA^95035|^WPN^PH^^1^800^4333773|1280 Stateline Road East^^Southaven^MS^38671\r" +
                "OBR|1|119611NFK^Spectra South^25D2227753^CLIA|9611NFK^Spectra South^25D2227753^CLIA|94309-2^SARS coronavirus 2 RNA:PrThr:Pt:XXX:Ord:Probe.amp.tar^LN|||20240201||||G||Recurring patient||Swab|1277777777^DOCTOR^TESTSEVEN^^^^^^NPI&25D2227753&CLIA^^^^NPI|^WPN^PH^^1^800^4333773|||||202402021419||LAB|F\r" +
                "OBX|1|ST|94309-2^SARS coronavirus 2 RNA:PrThr:Pt:XXX:Ord:Probe.amp.tar^LN^332-1-001^COVID-19 PCR^L^2.5.1^V1||Positive||Negative|A^Abnormal^HL70078^A^Abnormal^HL70078^2.5.1^V1|||F|||20240201|25D2227753^Spectra South^CLIA||SPECTRA-LIS^S-COBASDEF^L^^^^V1||20240202141900||||Spectra South^D^^^^Spectra South&25D2227753&CLIA^XXX^^^25D2227753|1280 Stateline Road East Southaven, MS 38671^^Southaven^MS^38671|^Ryder^Alex\r" +
                "SPM|1|119611NFK&Spectra South&25D2227753&CLIA^119611NFK&Spectra South&25D2227753&CLIA||258500001^Nasopharyngeal swab^SCT^^^^1.0|||||||||||||20240201|20240202\r" +
                "MSH|^~\\&|Spectra East^31D0961672^CLIA|Spectra East^31D0961672^CLIA|NC-ELR|NCPHD|202402091114||ORU^R01^ORU_R01|202402091114002|P|2.5.1|||||||||PHLabReport-NoAck^^2.16.840.1.114222.4.10.3^ISO\r" +
                "SFT|Spectra East|1.0|ASR|1||20240209\r" +
                "PID|1||8000397593^^^Spectra South&25D2227753&CLIA^PI||NINETEST^WESTNINE||20030101|M||2131-1^Other Race^CDCREC^U^Unknown^L^2.5.1^1.0|11111 KING ROAD^^ADVANCE^NC^27006^USA||^PRN^PH^^1^121^2121313|||||||||2186-5^Unknown^HL70189^U^Unknown^L^2.5.1^v_unknown\r" +
                "ORC|RE|119611NEK^Spectra South^25D2227753^CLIA|9611NEK^Spectra South^25D2227753^CLIA|9611NEK^Spectra South^25D2227753^CLIA||||||||5556666666^HAPPYTEST^HENRY^^^^^^NPI&25D2227753&CLIA^^^^NPI||^WPN^PH^^1^800^5224662|||||||Korus Test Hemo|8 King Road^^Rockleigh^NJ^07647|^WPN^PH^^1^800^5224662|1280 Stateline Road East^^Southaven^MS^38671\r" +
                "OBR|1|119611NEK^Spectra South^25D2227753^CLIA|9611NEK^Spectra South^25D2227753^CLIA|94309-2^SARS coronavirus 2 RNA:PrThr:Pt:XXX:Ord:Probe.amp.tar^LN|||20240201||||G||Recurring patient||Swab|5556666666^HAPPYTEST^HENRY^^^^^^NPI&25D2227753&CLIA^^^^NPI|^WPN^PH^^1^800^5224662|||||202402021420||LAB|F\r" +
                "OBX|1|ST|94309-2^SARS coronavirus 2 RNA:PrThr:Pt:XXX:Ord:Probe.amp.tar^LN^332-1-001^COVID-19 PCR^L^2.5.1^V1||Positive||Negative|A^Abnormal^HL70078^A^Abnormal^HL70078^2.5.1^V1|||F|||20240201|25D2227753^Spectra South^CLIA||SPECTRA-LIS^S-COBASDEF^L^^^^V1||20240202142000||||Spectra South^D^^^^Spectra South&25D2227753&CLIA^XXX^^^25D2227753|1280 Stateline Road East Southaven, MS 38671^^Southaven^MS^38671|^Ryder^Alex\r" +
                "SPM|1|119611NEK&Spectra South&25D2227753&CLIA^119611NEK&Spectra South&25D2227753&CLIA||258500001^Nasopharyngeal swab^SCT^^^^1.0|||||||||||||20240201|20240202\r";
                //"BTS|0|\r" +
                //"FTS|1|";
        /*
         * The HapiContext holds all configuration and provides factory methods for obtaining
         * all sorts of HAPI objects, e.g. parsers.
         */
        HapiContext context = new DefaultHapiContext();

        /*
         * A Parser is used to convert between string representations of messages and instances of
         * HAPI's "Message" object. In this case, we are using a "GenericParser", which is able to
         * handle both XML and ER7 (pipe & hat) encodings.
         */
        Parser p = context.getGenericParser();

        Message hapiMsg;
        try {
            // The parse method performs the actual parsing
            hapiMsg = p.parse(oru);
        } catch (EncodingNotSupportedException e) {
            e.printStackTrace();
            return;
        } catch (HL7Exception e) {
            e.printStackTrace();
            return;
        }

        /*
         * This message was an ADT^A01 is an HL7 data type consisting of several components1, so we
         * will cast it as such. The ADT_A01 class extends from Message, providing specialized
         * accessors for ADT^A01's segments.
         *
         * HAPI provides several versions of the ADT_A01 class, each in a different package (note
         * the import statement above) corresponding to the HL7 version for the message.
         */
        //ADT_A01 adtMsg = (ADT_A01)hapiMsg;
        ORU_R01 oruMsg = (ORU_R01)hapiMsg;

        MSH msh = oruMsg.getMSH();

        log.info("MSH: {}" , msh);

        // Retrieve some data from the MSH segment
        //String msgType = msh.getMessageType().getMessageType().getValue();
        Arrays.stream(oruMsg.getNames()).forEach(System.out::println);

        //log.info("MSH: {} {}" , oruMsg.getMessage(),msgTrigger);

        // Prints "ADT A01"
        //System.out.println(msgType + " " + msgTrigger);

        /*
         * Now let's retrieve the patient's name from the parsed message.
         *
         * PN is an HL7 data type consisting of several components, such as
         * family name, given name, etc.
         */
        //PN patientName = adtMsg.getPID().getPatientName();

        // Prints "SMITH"
        //String familyName = patientName.getFamilyName().getValue();
        //System.out.println(familyName + msgType);

    }

}