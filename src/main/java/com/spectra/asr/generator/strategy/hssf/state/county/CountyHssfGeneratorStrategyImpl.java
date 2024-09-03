package com.spectra.asr.generator.strategy.hssf.state.county;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.hssf.state.StateHssfBo;
import com.spectra.asr.converter.patient.ConverterUtil;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hssf.HssfGeneratorStrategyImpl;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class CountyHssfGeneratorStrategyImpl extends HssfGeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(CountyHssfGeneratorStrategyImpl.class);
	
	@Override
	public Map<String, List<Workbook>> generate(List<StateResultDto> dtoList) {
		Map<String, List<Workbook>> workbookListMap = null;
		List<String> stateTargetList = null;
		List<String> countyTargetList = null;
		if(dtoList != null){
			if(this.generatorDto != null){
				if(this.targetList == null){
					String targetListStr = this.generatorDto.getStateTarget();
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
						countyTargetList= new ArrayList<String>();
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
				//filter county
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
				/*
				if(this.targetList != null){
					targetDtoList = new ArrayList<StateResultDto>();
					for(StateResultDto dto : dtoList){
						for(String target : this.targetList){
							String county = dto.getPatientAccountCounty();
							if(county != null){
								if(target.equalsIgnoreCase(dto.getPatientAccountCounty())){
									targetDtoList.add(dto);
									break;
								}
							}
						}//for
					}//for
					log.warn("generate(): targetDtoList: size: " + (targetDtoList == null ? "NULL" : String.valueOf(targetDtoList.size())));
					for(StateResultDto target : targetDtoList){
						log.warn("generate(): targetDtoList: target: order number: " + (target == null ? "NULL" : target.getOrderNumber()));
						log.warn("generate(): targetDtoList: target: facility number: " + (target == null ? "NULL" : target.getFacilityId()));
						log.warn("generate(): targetDtoList: target: state: " + (target == null ? "NULL" : target.getPatientAccountState()));
						log.warn("generate(): targetDtoList: target: county: " + (target == null ? "NULL" : target.getPatientAccountCounty()));
						log.warn("generate(): targetDtoList: target: pat name: " + (target == null ? "NULL" : target.getPatientLastName()));
						log.warn("generate(): targetDtoList: target: release date: " + (target == null ? "NULL" : target.getReleaseDate()));
						log.warn("generate(): targetDtoList: target: release datetime: " + (target == null ? "NULL" : target.getReleaseDateTime()));
					}
				}else{
					targetDtoList = dtoList;
				}
				*/
				if((stateTargetList == null) && (countyTargetList == null)){
					targetDtoList = dtoList;
				}
				
				if(stateTargetDtoList != null){
					targetDtoList = stateTargetDtoList;
				}
				
				if(countyTargetDtoList != null){
					targetDtoList = countyTargetDtoList;
				}
				// do the conversion here
				Map<String, List<PatientRecord>> patRecListMap = ConverterUtil.toPatientRecordListMapByCounty(targetDtoList);
				log.warn("generate(): patRecListMap: size: " + (patRecListMap == null ? "NULL" : String.valueOf(patRecListMap.size())));
				log.warn("generate(): patRecListMap: keySet: " + (patRecListMap == null ? "NULL" : patRecListMap.keySet().toString()));
				log.warn("generate(): patRecListMap: value size: " + (patRecListMap == null ? "NULL" : String.valueOf(patRecListMap.values().size())));
				if((patRecListMap != null) && (patRecListMap.size() > 0)){
					StateHssfBo bo = AsrBoFactory.getStateHssfBo();
					if(bo != null){
						try{
							//List<Workbook> wbList = null;
							workbookListMap = new HashMap<String, List<Workbook>>();
							Set<Map.Entry<String, List<PatientRecord>>> entrySet = patRecListMap.entrySet();
							Workbook wb = null;
							if(entrySet != null){
								for(Map.Entry<String, List<PatientRecord>> entry : entrySet){
									String key = entry.getKey();
									Map<String, List<PatientRecord>> listMap = new HashMap<String, List<PatientRecord>>();
									listMap.put(entry.getKey(), entry.getValue());
									wb = bo.toWorkbook(listMap, this.generatorDto);
									List<Workbook> wbList = null;
									if(workbookListMap.containsKey(key)){
										wbList = workbookListMap.get(key);
										wbList.add(wb);
									}else{
										wbList = new ArrayList<Workbook>();
										wbList.add(wb);
										workbookListMap.put(key, wbList);
									}
	
	
									//StringBuilder keyBuilder = new StringBuilder();
									//keyBuilder.append(this.generatorDto.getStateAbbreviation()).append(".").append(entry.getKey());
									//workbookListMap.put(keyBuilder.toString(), wbList);
									//workbookListMap.put(entry.getKey(), wbList);
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
