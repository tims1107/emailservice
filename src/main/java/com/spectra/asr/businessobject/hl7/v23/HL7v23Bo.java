package com.spectra.asr.businessobject.hl7.v23;

import java.util.List;
import java.util.Map;

import com.spectra.asr.businessobject.hl7.state.HL7Bo;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.asr.dto.patient.PatientRecord;

public interface HL7v23Bo extends HL7Bo {
	//HL7Dto toHL7Dto(Map<String, List<PatientRecord>> listMap, GeneratorDto generatorDto) throws BusinessException;
}
