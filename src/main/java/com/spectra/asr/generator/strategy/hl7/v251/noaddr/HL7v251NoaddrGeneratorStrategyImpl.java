package com.spectra.asr.generator.strategy.hl7.v251.noaddr;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.hl7.v251.HL7v251Bo;
import com.spectra.asr.converter.patient.ConverterUtil;
import com.spectra.asr.dto.hl7.HL7Dto;
import com.spectra.asr.dto.patient.PatientRecord;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.generator.strategy.hl7.state.StateHL7GeneratorStrategyImpl;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class HL7v251NoaddrGeneratorStrategyImpl extends StateHL7GeneratorStrategyImpl {
	//private Logger log = Logger.getLogger(HL7v251NoaddrGeneratorStrategyImpl.class);
	
	@Override
	public Map<String, List<HL7Dto>> generate(List<StateResultDto> dtoList) {
		Map<String, List<HL7Dto>> dtoListMap = null;
		if(dtoList != null){
			if(this.generatorDto != null){
				log.warn("generate(): generatorDto: " + (generatorDto.getEastWestFlag() == null ? "NULL" : generatorDto.getEastWestFlag()));
				
				if(this.targetList == null){
					String targetListStr = this.generatorDto.getStateTarget();
					if(targetListStr != null){
						// TEST REMOVE !!
						//targetListStr += ",CA,MD,TX,IL,PA,NY";
						// END TEST REMOVE !!
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
							
							//if(target.equalsIgnoreCase(dto.getPatientAccountState())){
							////if(target.equalsIgnoreCase(dto.getReportableState())){
							//	targetDtoList.add(dto);
							//	break;
							//}
							
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
					}
				}else{
					targetDtoList = dtoList;
				}
				// do the conversion here
				Map<String, List<PatientRecord>> patRecListMap = ConverterUtil.toPatientRecordListMapByReqNo(targetDtoList);
				log.warn("generate(): patRecListMap: size: " + (patRecListMap == null ? "NULL" : String.valueOf(patRecListMap.size())));
				//log.warn("generate(): PAT_REC_LIST_MAP: " + (patRecListMap == null ? "NULL" : patRecListMap.toString()));
				HL7v251Bo bo = AsrBoFactory.getHL7v251Bo();
				HL7Dto hl7Dto = null;
				if(bo != null){
					try{
						hl7Dto = bo.toHL7Dto(patRecListMap, this.generatorDto);
						//log.warn("generate(): hl7Dto: " + (hl7Dto == null ? "NULL" : hl7Dto.toString()));
						if(hl7Dto != null){
							List<HL7Dto> hl7DtoList = new ArrayList<HL7Dto>();
							hl7DtoList.add(hl7Dto);
							dtoListMap = new HashMap<String, List<HL7Dto>>();
							//dtoListMap.put(this.generatorDto.getWriteBy(), hl7DtoList);
							dtoListMap.put(this.generatorDto.getStateAbbreviation(), hl7DtoList);
						}
					}catch(BusinessException be){
						log.error(String.valueOf(be));
						be.printStackTrace();
					}
				}
			}
		}
		return dtoListMap;
	}

}
