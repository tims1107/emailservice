package com.spectra.asr.generator.strategy.hssf.state.noaddr;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.hssf.state.StateHssfBo;
import com.spectra.asr.converter.patient.ConverterUtil;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hssf.HssfGeneratorStrategyImpl;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class StateHssfNoaddrGeneratorStrategyImpl extends HssfGeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(StateHssfNoaddrGeneratorStrategyImpl.class);
	
	
	@Override
	public Map<String, List<Workbook>> generate(List<StateResultDto> dtoList) {
		Map<String, List<Workbook>> workbookListMap = null;
		if(dtoList != null){
			if(this.generatorDto != null){
				if(this.targetList == null){
					String targetListStr = this.generatorDto.getStateTarget();
					// TEST REMOVE !!
					//targetListStr += ",IL,CA,TX,NJ,MD,PA,OH,MA,GA";
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
						/*
						for(String target : this.targetList){
							// comment and uncomment for results by facility/patient state
							String state = dto.getPatientAccountState();
							String reportableState = dto.getReportableState();
							log.warn("generate(): state: " + (state == null ? "NULL" : state));
							log.warn("generate(): reportableState: " + (reportableState == null ? "NULL" : reportableState));
							log.warn("generate(): target: " + (target == null ? "NULL" : target));
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
						}//for
						*/
						targetDtoList.add(dto);
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
				// do the conversion here
				Map<String, List<PatientRecord>> patRecListMap = ConverterUtil.toPatientRecordListMapByRequisitionNumber(targetDtoList);
				log.warn("generate(): patRecListMap: size: " + (patRecListMap == null ? "NULL" : String.valueOf(patRecListMap.size())));
				log.warn("generate(): patRecListMap: keySet: " + (patRecListMap == null ? "NULL" : patRecListMap.keySet().toString()));
				if((patRecListMap != null) && (patRecListMap.size() > 0)){
					StateHssfBo bo = AsrBoFactory.getStateHssfBo();
					Workbook wb = null;
					if(bo != null){
						try{
							wb = bo.toWorkbook(patRecListMap, this.generatorDto);
							if(wb != null){
								List<Workbook> wbList = new ArrayList<Workbook>();
								wbList.add(wb);
								workbookListMap = new HashMap<String, List<Workbook>>();
								//workbookListMap.put(this.generatorDto.getWriteBy(), wbList);
								workbookListMap.put(this.generatorDto.getStateAbbreviation(), wbList);
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
