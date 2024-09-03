package com.spectra.asr.generator.context.hl7.state;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class StateHL7GeneratorContextImpl implements GeneratorContext<HL7Dto, StateResultDto> {
	//private Logger log = Logger.getLogger(StateHL7GeneratorContextImpl.class);
	
	private GeneratorStrategy<HL7Dto, StateResultDto> generatorStrategy;
	private GeneratorDto generatorDto;
	
	public void setStrategy(GeneratorStrategy<HL7Dto, StateResultDto> generatorStrategy){
		this.generatorStrategy = generatorStrategy;
	}
	
	public GeneratorStrategy<HL7Dto, StateResultDto> getStrategy(){
		return this.generatorStrategy;
	}
	
	public void setGenerator(GeneratorDto generatorDto){
		this.generatorDto = generatorDto;
	}
	
	public Map<String, List<HL7Dto>> executeStrategy(List<StateResultDto> dtoList){
		Map<String, List<HL7Dto>> dtoListMap = null;
		if(dtoList != null){
			if(this.generatorStrategy != null){
				/*if(this.generatorDto != null){
					this.generatorStrategy.setGenerator(this.generatorDto);
				}*/

				// ******
				dtoListMap = this.generatorStrategy.generate(dtoList);
			}
		}
		return dtoListMap;
	}
}
