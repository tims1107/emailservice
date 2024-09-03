package com.spectra.asr.generator.strategy.hssf.eip.abc;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.hssf.eip.abc.AbcEIPHssfBo;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hssf.eip.EIPHssfGeneratorStrategyImpl;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

@Slf4j
public class AbcEIPHssfGeneratorStrategyImpl extends EIPHssfGeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(AbcEIPHssfGeneratorStrategyImpl.class);
	
	@Override
	public Map<String, List<Workbook>> generate(List<StateResultDto> dtoList) {
		Map<String, List<Workbook>> workbookListMap = null;
		if(dtoList != null){
			if(this.generatorDto != null){
				if(this.targetList == null){
					String targetListStr = this.generatorDto.getStateTarget();
					// TEST REMOVE !!
					//targetListStr += ",CA";
					// END TEST REMOVE !!
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
				// do the conversion here
				Map<String, List<StateResultDto>> listMap = null;
				if((targetDtoList != null) && (targetDtoList.size() > 0)){
					listMap = new HashMap<String, List<StateResultDto>>();
					//listMap.put(this.generatorDto.getStateAbbreviation(), targetDtoList);
					for(StateResultDto targetDto : targetDtoList){
						// comment and uncomment for results by facility/patient state
						String state = targetDto.getPatientAccountState();
						//String state = targetDto.getReportableState();
						if(state != null){
							if(listMap.containsKey(state)){
								List<StateResultDto> srDtoList = listMap.get(state);
								srDtoList.add(targetDto);
							}else{
								List<StateResultDto> srDtoList = new ArrayList<StateResultDto>();
								srDtoList.add(targetDto);
								listMap.put(state, srDtoList);
							}
						}
					}
					
					AbcEIPHssfBo bo = AsrBoFactory.getAbcEIPHssfBo();
					Workbook wb = null;
					if(bo != null){
						try{
							workbookListMap = new HashMap<String, List<Workbook>>();
							Set<Map.Entry<String, List<StateResultDto>>> entrySet = listMap.entrySet();
							for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
								Map stateListMap = new HashMap<String, List<StateResultDto>>();
								String state = entry.getKey();
								stateListMap.put(state, entry.getValue());
								wb = bo.toWorkbook(stateListMap, this.generatorDto);
								if(wb != null){
									List<Workbook> wbList = new ArrayList<Workbook>();
									wbList.add(wb);
									//workbookListMap = new HashMap<String, List<Workbook>>();
									//workbookListMap.put(this.generatorDto.getWriteBy(), wbList);
									StringBuilder keyBuilder = new StringBuilder();
									keyBuilder.append(this.generatorDto.getStateAbbreviation()).append(".").append(state);
									workbookListMap.put(keyBuilder.toString(), wbList);
								}
							}
						}catch(BusinessException be){
							log.error(String.valueOf(be));
							be.printStackTrace();
						}
					}					
				}
			}
		}
		return workbookListMap;
	}

}
