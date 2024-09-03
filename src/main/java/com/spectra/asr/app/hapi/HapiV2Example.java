package com.spectra.asr.app.hapi;


/**
 * The contents of this file are subject to the Mozilla Public License Version 1.1
 * (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.mozilla.org/MPL/
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for the
 * specific language governing rights and limitations under the License.
 *
 * The Original Code is "ExampleUseTerser.java".  Description:
 * "Example Code"
 *
 * The Initial Developer of the Original Code is University Health Network. Copyright (C)
 * 2001.  All Rights Reserved.
 *
 * Contributor(s): James Agnew
 *
 * Alternatively, the contents of this file may be used under the terms of the
 * GNU General Public License (the  ?GPL?), in which case the provisions of the GPL are
 * applicable instead of those above.  If you wish to allow use of your version of this
 * file only under the terms of the GPL and not to allow others to use your version
 * of this file under the MPL, indicate your decision by deleting  the provisions above
 * and replace  them with the notice and other provisions required by the GPL License.
 * If you do not delete the provisions above, a recipient may use your version of
 * this file under either the MPL or the GPL.
 *
 */



import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.GenericMessage;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.DefaultModelClassFactory;
import ca.uhn.hl7v2.parser.GenericModelClassFactory;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Stream;

/**
 * Example code illustrating how to handle multiple versions of HL7 from one codebase
 *
 * @author <a href="mailto:jamesagnew@sourceforge.net">James Agnew</a>
 * @version $Revision: 1.1 $ updated on $Date: 2011-05-22 16:52:21 $ by $Author: jamesagnew $
 */

@Slf4j
public class HapiV2Example {

    public static void main(String[] args) throws Exception {

        /*
         * Often, you will need to handle multiple versions of HL7 from a sending system
         * from within the same code base. Because HAPI uses different model classes for
         * each version, this can seem difficult.
         *
         * Before the first example, a bit of background information that is useful.
         * HL7 v2 is a backwards compatible standard, for the most part. New versions
         * of the standard will deprocate old fields and segments and groups, but they never
         * remove them entirely. They will also rename fields and groups, but this has
         * no effect on encoded messages if they are encoded using ER7 (pipe and hat)
         * encoding, only on the message structure objects themselves.
         *
         * Unfortunately, because of this renaming, it is not possible for the
         * HAPI library to create a single version of a structure JAR which covers
         * all versions of HL7 v2 (v2.1, v2.2, v2.3, etc). That said, it is always
         * possible to use a HAPI message structure object to parse or encode a
         * message of the same type from an earlier version of the standard. In
         * other words, if you have a v2.2 ADT^A01 message, you can use the v2.3
         * ADT_A01 structure class to parse it, and you can also use the v2.3 ADT_A01
         * structure class to create a new v2.2 message if you are not planning on
         * XML encoding it.
         *
         * The following example shows two ways of dealing with this situation. First,
         * for this example, consider the following messages. Each is identical, aside
         * from the version string: "2.5" and "2.3".
         */

//        String v25message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v25|T|2.5\r"
//                + "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
//                + "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
//                + "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
//                + "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
//                + "OBX|1|ST|||Test Value";

        String v25message = //"FHS|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240207110030||20240207110030\r" +
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

        String v23message = "MSH|^~\\&|ULTRA|TML|OLIS|OLIS|200905011130||ORU^R01|20169838-v23|T|2.3\r"
                + "PID|||7005728^^^TML^MR||TEST^RACHEL^DIAMOND||19310313|F|||200 ANYWHERE ST^^TORONTO^ON^M6G 2T9||(416)888-8888||||||1014071185^KR\r"
                + "PV1|1||OLIS||||OLIST^BLAKE^DONALD^THOR^^^^^921379^^^^OLIST\r"
                + "ORC|RE||T09-100442-RET-0^^OLIS_Site_ID^ISO|||||||||OLIST^BLAKE^DONALD^THOR^^^^L^921379\r"
                + "OBR|0||T09-100442-RET-0^^OLIS_Site_ID^ISO|RET^RETICULOCYTE COUNT^HL79901 literal|||200905011106|||||||200905011106||OLIST^BLAKE^DONALD^THOR^^^^L^921379||7870279|7870279|T09-100442|MOHLTC|200905011130||B7|F||1^^^200905011106^^R\r"
                + "OBX|1|ST|||Test Value";

        /*
         * The first (and probably better in most ways) technique is as follows. Use a model class
         * factory called the CanonicalModelClassFactory. This class forces a specific version of
         * HL7 to be used. Because HL7 v2.x is a backwards compatible standard, you can choose the
         * highest version you need to support, and the model classes will be compatible with
         * messages from previous versions.
         */

        HapiContext context = new DefaultHapiContext();

        // Create the MCF. We want all parsed messages to be for HL7 version 2.5,
        // despite what MSH-12 says.
        CanonicalModelClassFactory mcf = new CanonicalModelClassFactory("2.5");
        context.setModelClassFactory(mcf);

        // Pass the MCF to the parser in its constructor
        PipeParser parser = context.getPipeParser();

        // The parser parses the v2.3 message to a "v25" structure
        ca.uhn.hl7v2.model.v25.message.ORU_R01 msg = (ca.uhn.hl7v2.model.v25.message.ORU_R01) parser.parse(v23message);

        // 20169838-v23
        //System.out.println(msg.getMSH().getMessageControlID().getValue());

        // The parser also parses the v2.5 message to a "v25" structure
        msg = (ca.uhn.hl7v2.model.v25.message.ORU_R01) parser.parse(v25message);

        log.info("Message: {}",msg);

        // 20169838-v25
        //System.out.println(msg.getMSH().getMessageControlID().getValue());

        /*
         * The second technique is to use the Terser. The Terser allows you
         * to access field values using a path-like notation. For more information
         * on the Terser, see the example here:
         * http://hl7api.sourceforge.net/xref/ca/uhn/hl7v2/examples/ExampleUseTerser.html
         */

        // This time we just use a normal ModelClassFactory, which means we will be
        // using the standard version-specific model classes
        context.setModelClassFactory(new DefaultModelClassFactory());

        // 20169838-v23
        Message v23Message = parser.parse(v23message);
        Terser t23 = new Terser(v23Message);
        //System.out.println(t23.get("/MSH-10"));

        // 20169838-v25
        Message v25Message = parser.parse(v25message);
        Terser t25 = new Terser(v25Message);
        System.out.println(t25.get("/MSH-10"));

        /*
         * Note that this second technique has one major drawback: Although
         * message definitions are backwards compatible, some group names
         * change between versions. If you are accessing a group within
         * a complex message structure, this can cause issues.
         *
         * This is less of an issue for some message types where groups are
         * not used much (e.g. ADT)
         */

        // This works and prints "Test Value"
        //System.out.println(t23.get("/RESPONSE/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5"));

        // This fails...
        // //System.out.println(t25.get("/RESPONSE/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5"));

        // ...because this would be required to extract the OBX-5 value from a v2.5 message
        //System.out.println(t25.get("/PATIENT_RESULT/ORDER_OBSERVATION/OBSERVATION(0)/OBX-5"));

        /*
         * A third technique which may occasionally be useful is to simply use
         * a "Generic" message structure. Generic message structures can
         * represent anything within an HL7 message, but they don't actually
         * model all of the intricacies of the structure within the message,
         * but rather just model all of the data in an unstructured way.
         */

        // Create a new context using a Generic Model Class Factory
        context = new DefaultHapiContext();
        context.setModelClassFactory(new GenericModelClassFactory());

        v25message = //"FHS|^~\\&|Spectra Labs^2.16.840.1.114222.4.3.22.29.7.1^ISO|Spectra East^31D0961672^CLIA|NCDPH NCEDSS^2.16.840.1.113883.3.591.3.1^ISO|NCDPH EDS^2.16.840.1.113883.3.591.1.1^ISO|20240207110030||20240207110030\r" +
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

        // The parser will always parse this as a "GenericMessage"
        GenericMessage message = (GenericMessage) context.getPipeParser().parse(v25message);

        /*
         * A generic message has a flat structure, so you can ask for any
         * field by only its segment name, not a complex path
         */
        Terser t = new Terser(message);
        Stream.of(t.getFinder().getCurrentChildReps()).forEach(System.out::println);
        //System.out.println(t.get("/OBX-5"));
        // Prints: Test Value

        /*
         * This technique isn't great for messages with complex structures. For
         * example, the second OBX in the message above is a part of the base structure
         * because GenericMessage has no groups.
         *
         * It can be accessed using a new segment name (OBX2 instead of OBX)
         * but this is error prone, so use with caution.
         */
        //System.out.println(t.get("/OBX2-5"));
        // Prints: Value number 2

    }

}

