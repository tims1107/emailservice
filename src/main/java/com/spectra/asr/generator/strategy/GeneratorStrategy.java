package com.spectra.asr.generator.strategy;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;

public interface GeneratorStrategy<X, Y> {
	void setGenerator(GeneratorDto generatorDto);
	Map<String, List<X>> generate(List<Y> dtoList);
}
