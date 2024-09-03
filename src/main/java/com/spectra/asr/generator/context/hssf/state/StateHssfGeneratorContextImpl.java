package com.spectra.asr.generator.context.hssf.state;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;


@Slf4j
public class StateHssfGeneratorContextImpl implements GeneratorContext<Workbook, StateResultDto> {
	//private Logger log = Logger.getLogger(StateHssfGeneratorContextImpl.class);
	
	private GeneratorStrategy<Workbook, StateResultDto> generatorStrategy;
	private GeneratorDto generatorDto;
	
	public void setStrategy(GeneratorStrategy<Workbook, StateResultDto> generatorStrategy){
		this.generatorStrategy = generatorStrategy;
	}
	
	public GeneratorStrategy<Workbook, StateResultDto> getStrategy(){
		return this.generatorStrategy;
	}
	
	public void setGenerator(GeneratorDto generatorDto){
		this.generatorDto = generatorDto;
	}
	
	public Map<String, List<Workbook>> executeStrategy(List<StateResultDto> dtoList){
		Map<String, List<Workbook>> dtoListMap = null;
		if(dtoList != null){
			if(this.generatorStrategy != null){

				dtoListMap = this.generatorStrategy.generate(dtoList);
			}
		}
		return dtoListMap;
	}	
}
