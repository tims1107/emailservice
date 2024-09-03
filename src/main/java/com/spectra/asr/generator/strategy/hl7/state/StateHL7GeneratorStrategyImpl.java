package com.spectra.asr.generator.strategy.hl7.state;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.GeneratorStrategy;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public abstract class StateHL7GeneratorStrategyImpl implements GeneratorStrategy<HL7Dto, StateResultDto> {
	//private Logger log = Logger.getLogger(StateHL7GeneratorStrategyImpl.class);
	
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
	
	/*public Map<String, List<HL7Dto>> generate(List<StateResultDto> dtoList){
		Map<String, List<HL7Dto>> dtoListMap = null;
		if(dtoList != null){
			List<StateResultDto> targetDtoList = null;
			if(this.targetList != null){
				targetDtoList = new ArrayList<StateResultDto>();
				for(StateResultDto dto : dtoList){
					for(String target : this.targetList){
						if(target.equalsIgnoreCase(dto.getPatientAccountState())){
							targetDtoList.add(dto);
							break;
						}
					}//for
				}//for
			}
		}
		return dtoListMap;
	}*/
}
