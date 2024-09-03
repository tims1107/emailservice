package com.spectra.asr.generator.strategy.hl7;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.GeneratorStrategy;

public interface HL7GeneratorStrategy extends GeneratorStrategy<HL7Dto, StateResultDto> {
	Map<String, List<HL7Dto>> generate(List<StateResultDto> dtoList);
	
}
