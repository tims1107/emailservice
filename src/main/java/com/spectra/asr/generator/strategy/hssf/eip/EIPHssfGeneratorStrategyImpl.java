package com.spectra.asr.generator.strategy.hssf.eip;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public abstract class EIPHssfGeneratorStrategyImpl implements GeneratorStrategy<Workbook, StateResultDto> {
	//private Logger log = Logger.getLogger(EIPHssfGeneratorStrategyImpl.class);
	
	protected List<String> targetList;
	protected GeneratorDto generatorDto;
	
	public List<String> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<String> targetList) {
		this.targetList = targetList;
	}

	public void setGenerator(GeneratorDto generatorDto){
		this.generatorDto = generatorDto;
	}
}
