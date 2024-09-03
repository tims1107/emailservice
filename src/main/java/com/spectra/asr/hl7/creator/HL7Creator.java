package com.spectra.asr.hl7.creator;

import java.util.List;
import java.util.Map;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.AbstractMessage;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface HL7Creator {
	HL7Dto toHL7Dto(Map<String, List<PatientRecord>> listMap, GeneratorDto generatorDto) throws BusinessException;
	AbstractMessage getMessage(PatientRecord patientRecord, Map<String, Object> paramMap) throws HL7Exception;
	String getPipedHL7Message(AbstractMessage hl7Message, Map<String, Object> paramMap) throws HL7Exception;
	String getXmlHL7Message(AbstractMessage hl7Message, Map<String, Object> paramMap) throws HL7Exception;
}
