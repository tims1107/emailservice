package com.spectra.asr.hl7.creator.v251;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.PipeParser;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.hl7.creator.PAAbstractHL7Creator;
import com.spectra.asr.hl7.message.HL7Message;
import com.spectra.asr.hl7.message.factory.HL7MessageFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class PAHL7Creator extends PAAbstractHL7Creator {
	//private Logger log = Logger.getLogger(PAHL7Creator.class);
	
	@Override
	public AbstractMessage getMessage(PatientRecord patientRecord, Map<String, Object> paramMap) throws HL7Exception {
		AbstractMessage msg = null;
		if((patientRecord != null) && (paramMap != null)){
			HL7Message hl7Msg = HL7MessageFactory.getPAHL7v251Message();
			msg = hl7Msg.getMessage(patientRecord, paramMap);
		}
//		System.exit(0);
		return msg;
	}

	@Override
	public String getPipedHL7Message(AbstractMessage hl7Message, Map<String, Object> paramMap) throws HL7Exception {
		String hl7Str = null;
		if(hl7Message != null){
	        Parser parser = new PipeParser();
	        hl7Str = parser.encode(hl7Message);
		}

		// System.exit(0);
		return hl7Str;
	}

	@Override
	public String getXmlHL7Message(AbstractMessage hl7Message, Map<String, Object> paramMap) throws HL7Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
