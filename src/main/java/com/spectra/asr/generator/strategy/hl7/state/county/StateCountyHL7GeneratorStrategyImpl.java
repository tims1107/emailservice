package com.spectra.asr.generator.strategy.hl7.state.county;

import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hl7.state.StateHL7GeneratorStrategyImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

//public class StateCountyHL7GeneratorStrategyImpl implements GeneratorStrategy<HL7Dto, StateResultDto> {
@Slf4j
public class StateCountyHL7GeneratorStrategyImpl extends StateHL7GeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(StateCountyHL7GeneratorStrategyImpl.class);
	
	public Map<String, List<HL7Dto>> generate(List<StateResultDto> dtoList){
		Map<String, List<HL7Dto>> dtoListMap = null;
		//generator dto contains county lists in county target (nyc for example)
		//if generator dto county target is null, generate for all counties
		//target list created from county target contain counties
		//filter in only target counties
		//create listmap to generate hl7
		return dtoListMap;
	}

}
