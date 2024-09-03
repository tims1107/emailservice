package com.spectra.asr.generator.context.hl7;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class HL7GeneratorContextImpl<X, Y> implements HL7GeneratorContext<X, Y> {
	//private Logger log = Logger.getLogger(HL7GeneratorContextImpl.class);
	
	GeneratorStrategy<X, Y> generatorStrategy;
	GeneratorDto generatorDto;
	
	public void setStrategy(GeneratorStrategy<X, Y> generatorStrategy){
		this.generatorStrategy = generatorStrategy;
	}
	
	public GeneratorStrategy<X, Y> getStrategy(){
		return this.generatorStrategy;
	}
	
	public void setGenerator(GeneratorDto generatorDto){
		this.generatorDto = generatorDto;
	}
	
	public Map<String, List<X>> executeStrategy(List<Y> dtoList){
		Map<String, List<X>> dtoListMap = null;
		if(dtoList != null){
			if(this.generatorStrategy != null){
				dtoListMap = this.generatorStrategy.generate(dtoList);
			}
		}
		return dtoListMap;
	}
}
