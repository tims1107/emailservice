package com.spectra.asr.generator.strategy.hssf.eip.common.county;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.hssf.eip.common.CommonEIPHssfBo;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hssf.eip.EIPHssfGeneratorStrategyImpl;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class CommonEIPCountyHssfGeneratorStrategyImpl extends EIPHssfGeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(CommonEIPCountyHssfGeneratorStrategyImpl.class);
	
	@Override
	public Map<String, List<Workbook>> generate(List<StateResultDto> dtoList) {
		Map<String, List<Workbook>> workbookListMap = null;
		List<String> stateTargetList = null;
		List<String> countyTargetList = null;
		if(dtoList != null){
			if(this.generatorDto != null){
				if(this.targetList == null){
					String targetListStr = this.generatorDto.getStateTarget();
					// TEST REMOVE !!
					//targetListStr += ",CA";
					// END TEST REMOVE !!
					if(targetListStr != null){
						String[] targetListArray = targetListStr.split(",");
						//this.targetList = new ArrayList<String>();
						stateTargetList = new ArrayList<String>();
						for(String targetStr : targetListArray){
							stateTargetList.add(targetStr);
						}
					}
					
					targetListStr = this.generatorDto.getCountyTarget();
					if(targetListStr != null){
						String[] targetListArray = targetListStr.split(",");
						//this.targetList = new ArrayList<String>();
						countyTargetList = new ArrayList<String>();
						for(String targetStr : targetListArray){
							countyTargetList.add(targetStr);
						}
					}
				}
				log.warn("generate(): stateTargetList: " + (stateTargetList == null ? "NULL" : stateTargetList.toString()));
				log.warn("generate(): countyTargetList: " + (countyTargetList == null ? "NULL" : countyTargetList.toString()));
				// filter state
				List<StateResultDto> targetDtoList = null;
				List<StateResultDto> stateTargetDtoList = null;
				if(stateTargetList != null){
					stateTargetDtoList = new ArrayList<StateResultDto>();
					for(StateResultDto dto : dtoList){
						for(String target : stateTargetList){
							// comment and uncomment for results by facility/patient state
							String state = dto.getPatientAccountState();
							String reportableState = dto.getReportableState();
							if(state != null){
								if(target.equalsIgnoreCase(state)){
									stateTargetDtoList.add(dto);
									break;
								}
							}else if(reportableState != null){
								if(target.equalsIgnoreCase(reportableState)){
									stateTargetDtoList.add(dto);
									break;
								}
							}	
							/*
							if(target.equalsIgnoreCase(dto.getPatientAccountState())){
							//if(target.equalsIgnoreCase(dto.getReportableState())){
								stateTargetDtoList.add(dto);
								break;
							}
							*/
						}//for
					}//for
					log.warn("generate(): stateTargetDtoList: size: " + (stateTargetDtoList == null ? "NULL" : String.valueOf(stateTargetDtoList.size())));
					for(StateResultDto target : stateTargetDtoList){
						log.warn("generate(): stateTargetDtoList: target: order number: " + (target == null ? "NULL" : target.getOrderNumber()));
						log.warn("generate(): stateTargetDtoList: target: facility number: " + (target == null ? "NULL" : target.getFacilityId()));
						log.warn("generate(): stateTargetDtoList: target: state: " + (target == null ? "NULL" : target.getPatientAccountState()));
						log.warn("generate(): stateTargetDtoList: target: county: " + (target == null ? "NULL" : target.getPatientAccountCounty()));
						log.warn("generate(): stateTargetDtoList: target: pat name: " + (target == null ? "NULL" : target.getPatientLastName()));
					}
				}
				
				// filter county
				List<StateResultDto> countyTargetDtoList = null;
				if(countyTargetList != null){
					countyTargetDtoList = new ArrayList<StateResultDto>();
					if((stateTargetDtoList != null) && (stateTargetDtoList.size() > 0)){
						for(StateResultDto dto : stateTargetDtoList){
							for(String target : countyTargetList){
								if(target.equalsIgnoreCase(dto.getPatientAccountCounty())){
									countyTargetDtoList.add(dto);
									break;
								}
							}//for
						}//for
						log.warn("generate(): countyTargetDtoList: size: " + (countyTargetDtoList == null ? "NULL" : String.valueOf(countyTargetDtoList.size())));
						for(StateResultDto target : countyTargetDtoList){
							log.warn("generate(): countyTargetDtoList: target: order number: " + (target == null ? "NULL" : target.getOrderNumber()));
							log.warn("generate(): countyTargetDtoList: target: facility number: " + (target == null ? "NULL" : target.getFacilityId()));
							log.warn("generate(): countyTargetDtoList: target: state: " + (target == null ? "NULL" : target.getPatientAccountState()));
							log.warn("generate(): countyTargetDtoList: target: county: " + (target == null ? "NULL" : target.getPatientAccountCounty()));
							log.warn("generate(): countyTargetDtoList: target: pat name: " + (target == null ? "NULL" : target.getPatientLastName()));
						}
					}
				}
				
				if((stateTargetList == null) && (countyTargetList == null)){
					targetDtoList = dtoList;
				}
				
				if(stateTargetDtoList != null){
					targetDtoList = stateTargetDtoList;
				}
				
				if(countyTargetDtoList != null){
					targetDtoList = countyTargetDtoList;
				}
				//log.warn("generate(): targetDtoList: " + (targetDtoList == null ? "NULL" : targetDtoList.toString()));
				
				// do the conversion here
				Map<String, List<StateResultDto>> listMap = null;
				if((targetDtoList != null) && (targetDtoList.size() > 0)){
					listMap = new HashMap<String, List<StateResultDto>>();
					for(StateResultDto targetDto : targetDtoList){
						// comment and uncomment for results by facility/patient state
						String state = targetDto.getPatientAccountState();
						//String state = targetDto.getReportableState();
						String county = targetDto.getPatientAccountCounty();
						StringBuilder keyBuilder = new StringBuilder();
						keyBuilder.append(state).append(".").append(county);
						String key = keyBuilder.toString();
						if(key != null){
							if(listMap.containsKey(key)){
								List<StateResultDto> srDtoList = listMap.get(key);
								srDtoList.add(targetDto);
							}else{
								List<StateResultDto> srDtoList = new ArrayList<StateResultDto>();
								srDtoList.add(targetDto);
								listMap.put(key, srDtoList);
							}
						}
					}//for
					
					CommonEIPHssfBo bo = AsrBoFactory.getCommonEIPHssfBo();
					Workbook wb = null;
					if(bo != null){
						try{
							workbookListMap = new HashMap<String, List<Workbook>>();
							Set<Map.Entry<String, List<StateResultDto>>> entrySet = listMap.entrySet();
							for(Map.Entry<String, List<StateResultDto>> entry : entrySet){
								Map<String, List<StateResultDto>> stateListMap = new HashMap<String, List<StateResultDto>>();
								String stateCounty = entry.getKey();
								stateListMap.put(stateCounty, entry.getValue());
								wb = bo.toWorkbook(stateListMap, this.generatorDto);
								if(wb != null){
									List<Workbook> wbList = new ArrayList<Workbook>();
									wbList.add(wb);
									//workbookListMap = new HashMap<String, List<Workbook>>();
									//workbookListMap.put(this.generatorDto.getWriteBy(), wbList);
									StringBuilder keyBuilder = new StringBuilder();
									keyBuilder.append(this.generatorDto.getStateAbbreviation()).append(".").append(stateCounty);
									workbookListMap.put(keyBuilder.toString(), wbList);
								}
							}

						}catch(BusinessException be){
							log.error(String.valueOf(be));
							be.printStackTrace();
						}
					}
				}//if
				
			}
		}
		return workbookListMap;
	}

}
