package com.spectra.asr.generator.strategy.hssf.eip.cdiff;

import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hssf.eip.EIPHssfGeneratorStrategyImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class CdiffHssfGeneratorStrategyImpl extends EIPHssfGeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(CdiffHssfGeneratorStrategyImpl.class);
	
	@Override
	public Map<String, List<Workbook>> generate(List<StateResultDto> dtoList) {
		Map<String, List<Workbook>> workbookListMap = null;
		if(dtoList != null){
			if(this.generatorDto != null){
				if(this.targetList == null){
					String targetListStr = this.generatorDto.getStateTarget();
					if(targetListStr != null){
						String[] targetListArray = targetListStr.split(",");
						this.targetList = new ArrayList<String>();
						for(String targetStr : targetListArray){
							this.targetList.add(targetStr);
						}
					}
				}
				log.warn("generate(): targetList: " + (targetList == null ? "NULL" : targetList.toString()));
				List<StateResultDto> targetDtoList = null;
				if(this.targetList != null){
					targetDtoList = new ArrayList<StateResultDto>();
					for(StateResultDto dto : dtoList){
						for(String target : this.targetList){
							// comment and uncomment for results by facility/patient state
							String state = dto.getPatientAccountState();
							String reportableState = dto.getReportableState();
							if(state != null){
								if(target.equalsIgnoreCase(state)){
									targetDtoList.add(dto);
									break;
								}
							}else if(reportableState != null){
								if(target.equalsIgnoreCase(reportableState)){
									targetDtoList.add(dto);
									break;
								}
							}
							/*
							if(target.equalsIgnoreCase(dto.getPatientAccountState())){
							//if(target.equalsIgnoreCase(dto.getReportableState())){
								targetDtoList.add(dto);
								break;
							}
							*/
						}//for
					}//for
					log.warn("generate(): targetDtoList: size: " + (targetDtoList == null ? "NULL" : String.valueOf(targetDtoList.size())));
					for(StateResultDto target : targetDtoList){
						log.warn("generate(): targetDtoList: target: order number: " + (target == null ? "NULL" : target.getOrderNumber()));
						log.warn("generate(): targetDtoList: target: facility number: " + (target == null ? "NULL" : target.getFacilityId()));
						log.warn("generate(): targetDtoList: target: state: " + (target == null ? "NULL" : target.getPatientAccountState()));
						log.warn("generate(): targetDtoList: target: county: " + (target == null ? "NULL" : target.getPatientAccountCounty()));
						log.warn("generate(): targetDtoList: target: pat name: " + (target == null ? "NULL" : target.getPatientLastName()));
					}
				}else{
					targetDtoList = dtoList;
				}				
			}
		}
		return workbookListMap;
	}

}
