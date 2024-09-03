package com.spectra.asr.businessobject.micro;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.AsrDao;
import com.spectra.asr.dto.cm.CMFacilityDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.dto.state.zip.StateZipCodeDto;
import com.spectra.asr.service.demographic.AsrDemographicService;
import com.spectra.asr.service.factory.AsrServiceFactory;
import com.spectra.asr.service.ms.cm.CMService;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.*;

//import com.spectra.cm.db.constants.CMDBConstants;
//import com.spectra.cm.db.dto.ami.label.AmiLabelDto;
//import com.spectra.scorpion.framework.constants.ExceptionConstants;
//import com.spectra.scorpion.framework.exception.BusinessException;
//import com.spectra.cm.db.dao.ami.label.AmiLabelDao;
//import com.spectra.cm.db.dataaccess.util.DAOFactory;

@Slf4j
public class MicroBoImpl implements MicroBo {
	//private Logger log = Logger.getLogger(MicroBoImpl.class);
	
	public List<StateResultDto> getStateMicroResult(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		List<StateResultDto> filteredDtoList = new ArrayList<StateResultDto>();
		String filterStateBy = null;
		String entityState = null;
		if(resultExtractDto != null){
			filterStateBy = resultExtractDto.getFilterStateBy();
			if(filterStateBy == null){
				filterStateBy = "p";
			}
			entityState = resultExtractDto.getState();
			
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				CMService cmService = (CMService)AsrServiceFactory.getServiceImpl(CMService.class.getSimpleName());
				AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
				//dtoList = asrDao.callStateMicroResults(resultExtractDto);
				//dtoList = asrDao.callStateAbnMicroResults(resultExtractDto);
				dtoList = asrDao.callAsrMicroIncludeResults(resultExtractDto);
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;
						
/*						
						if(filterStateBy.equals("p")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
						}else if(filterStateBy.equals("f")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
						}else if(filterStateBy.equals("pf")){
							if(entityState != null){
								String patientAccountState = dto.getPatientAccountState();
								String facilityState = dto.getFacilityState();
								if(patientAccountState != null){
									if(patientAccountState.equalsIgnoreCase(entityState)){
										zip = dto.getPatientAccountZip();
										state = dto.getPatientAccountState();
									}
								}
								if(facilityState != null){
									if(facilityState.equalsIgnoreCase(entityState)){
										zip = dto.getFacilityZip();
										state = dto.getFacilityState();
									}
								}
								

								//if(dto.getPatientAccountState().equalsIgnoreCase(entityState)){
								//	zip = dto.getPatientAccountZip();
								//	state = dto.getPatientAccountState();
								//}else if(dto.getFacilityState().equalsIgnoreCase(entityState)){
								//	zip = dto.getFacilityZip();
								//	state = dto.getFacilityState();
								//}

							}
						}
*/						
						
						String sourceState = dto.getSourceState();
						String patLastName = dto.getPatientLastName();
						if(sourceState == null){
							sourceState = "patient";
						}
						if((patLastName == null) || (patLastName.trim().length() == 0)){
							dto.setSourceState("sendout");
						}
						if(sourceState.equals("patient")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();	
						}else if(sourceState.equals("facility")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setPatientAccountAddress1(dto.getFacilityAddress1());
							dto.setPatientAccountAddress2(dto.getFacilityAddress2());
							dto.setPatientAccountCity(dto.getFacilityCity());
							dto.setPatientAccountState(dto.getFacilityState());
							dto.setPatientAccountZip(dto.getFacilityZip());
						}
						
						//String zip = dto.getPatientAccountZip();
						if(zip != null){
							if(zip.length() > 5){
								zip = zip.substring(0, 5);
							}
							//String state = dto.getPatientAccountState();
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("zip", zip);
							//paramMap.put("city", "Northvale");
							if(state != null){
								paramMap.put("state", state);
							}
							StateZipCodeDto zipDto = asrDao.getStateDemoByZipCityState(paramMap);
							if(zipDto != null){
								//log.warn("getStateAbnormalResult(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
								dto.setPatientAccountCounty(zipDto.getCounty());
							}
						}
						
						
						if(cmService != null){
							Map<String, Object> paramMap = new HashMap<String, Object>();
							paramMap.put("hlabNum", dto.getFacilityId());
							
							List<CMFacilityDto> cmDtoList = null;
							cmDtoList = cmService.getCmFacilities(paramMap);
							if((cmDtoList != null) && (cmDtoList.size() > 0)){
								//log.warn("getStateAbnormalResult(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
								CMFacilityDto cmDto = cmDtoList.get(0);
								String facilityPref = cmDto.getFacilityPref();
								String facilityName = dto.getFacilityName();
								if((facilityPref != null) && (facilityPref.trim().length() > 0)){
									StringBuilder facilityNamebuilder = new StringBuilder();
									if(facilityName != null){
										if(facilityName.indexOf(":") != -1){
											String[] facilityNameArray = facilityName.split(":");
											facilityNamebuilder.append(facilityNameArray[0]).append(":").append(facilityPref).append(" ").append(facilityNameArray[1]);
										}else{
											facilityNamebuilder.append(facilityPref).append(" ").append(facilityName);
										}
									}
									dto.setFacilityName(facilityNamebuilder.toString());
									log.warn("getStateAbnormalResult(): facilityNamebuilder: " + (facilityNamebuilder == null ? "NULL" : facilityNamebuilder.toString()));
								}
							}

							if(asrDemographicService != null){
								//if(sourceState.equals("sendout")){
								if((sourceState.equals("sendout")) || (sourceState.equals("noaddr"))){	
									String orderNumber = dto.getOrderNumber();
									String accessionNo = dto.getAccessionNo();
									String otc = dto.getOrderTestCode();
									String rtc = dto.getResultTestCode();		
									StringBuilder dtoKeyBuilder = new StringBuilder();
									dtoKeyBuilder.append(orderNumber).append(accessionNo).append(otc).append(rtc);
									if(orderNumber != null){
										Map<String, Object> pMap = new HashMap<String, Object>();
										pMap.put("orderNumber", orderNumber);
										List<StateResultDto> ilndDtoList = asrDemographicService.getIntraLabsNoDemo(pMap);
										if((ilndDtoList == null) || (ilndDtoList.size() == 0)){								
											dto.setNotifiedFlag("N");
											asrDemographicService.insertIntraLabsNoDemo(dto);
										}else{
											Map<String, StateResultDto> checkMap = new HashMap<String, StateResultDto>();
											for(StateResultDto ilnDto : ilndDtoList){
												StringBuilder keyBuilder = new StringBuilder();
												keyBuilder.append(ilnDto.getOrderNumber());
												keyBuilder.append(ilnDto.getAccessionNo());
												keyBuilder.append(ilnDto.getOrderTestCode());
												keyBuilder.append(ilnDto.getResultTestCode());
												checkMap.put(keyBuilder.toString(), ilnDto);
											}
											if(!checkMap.containsKey(dtoKeyBuilder.toString())){
												dto.setNotifiedFlag("N");
												asrDemographicService.insertIntraLabsNoDemo(dto);
											}											
											/*
											for(StateResultDto ilnDto : ilndDtoList){
												if((ilnDto.getOrderNumber().equalsIgnoreCase(orderNumber)) &&
														(ilnDto.getAccessionNo().equalsIgnoreCase(accessionNo)) &&
														(ilnDto.getOrderTestCode().equalsIgnoreCase(otc)) &&
														(ilnDto.getResultTestCode().equalsIgnoreCase(rtc))){
													
												}else{
													dto.setNotifiedFlag("N");
													asrDemographicService.insertIntraLabsNoDemo(dto);
												}
											}//for
											*/
										}
									}
								}else{
									filteredDtoList.add(dto);
								}
							}
						}//if						
					}//for
				}
			}//if
		}
		//return dtoList;
		return filteredDtoList;
	}
	
	public List<StateResultDto> getMicroTestResults(ResultExtractDto resultExtractDto) throws BusinessException {
//		StringBuffer sb = new StringBuffer();
//		sb.append("getMicroTestResults: " + this.getClass().getName() + " " + resultExtractDto.getState() + " " + resultExtractDto.getGeneratorStateTarget());
//		sb.append("\n");

//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//		sb.setLength(0);

		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			String state = resultExtractDto.getState();
			String stateTarget = resultExtractDto.getGeneratorStateTarget();
			if((state != null) && (stateTarget != null)){
				AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
				if(asrDao != null){
					 Map<String, List<ConditionMasterDto>> dtoListMap = this.getConditionMasterMap(state);
					 if(dtoListMap != null){
						 dtoList = new ArrayList<StateResultDto>();
						 Set<Map.Entry<String, List<ConditionMasterDto>>> entrySet = dtoListMap.entrySet();
						 if(entrySet != null){
							 //GET EAST OR WEST FLAG FROM CONDITION FILTER TABLE!!!
							 if(resultExtractDto.getEwFlag() != null){
								 Map<String, Object> pMap = new HashMap<String, Object>();
								 pMap.put("condition", resultExtractDto.getEwFlag());
								 pMap.put("status", "active");
								 List<ConditionFilterDto> ewFlagFilterDtoList = asrDao.getConditionFilters(pMap);
								 if(ewFlagFilterDtoList != null){
									 ConditionFilterDto ewFlagFilterDto = ewFlagFilterDtoList.get(0);
									 resultExtractDto.setEwFlag(ewFlagFilterDto.getFilter());
								 }
							 }
							 
							 // get micro org not like condition
								Map<String, Object> paramMap = new HashMap<String, Object>();
								////paramMap.put("state", state);
								//paramMap.put("stateAbbreviation", state);
								paramMap.put("stateAbbreviation", stateTarget);
								paramMap.put("status", "active");
								List<String> includeConditionList = new ArrayList<String>();
								includeConditionList.add("Organism Name Not Like");
								paramMap.put("includeConditionList", includeConditionList);
								List<ConditionMasterDto> excludeMicroOrgDtoList = asrDao.getConditionMaster(paramMap);
								if(excludeMicroOrgDtoList != null){
									String excludeBlock = getMicroOrganismExcludeBlock(excludeMicroOrgDtoList);
									resultExtractDto.setMicroOrganismNameExcludeBlock(excludeBlock);
								}

							 
							 if(resultExtractDto.getState() != null){
								 dtoList = getStateMicroResult(resultExtractDto);
								 /*
								 List<StateResultDto> resultDtoList = getStateMicroResult(resultExtractDto);
								 if(resultDtoList != null){
									 log.warn("getMicroTestResults(): resultDtoList: " + (resultDtoList == null ? "NULL" : resultDtoList.toString()));
									 dtoList.addAll(resultDtoList);
								 }
								 */
							 }							 

/*							 
							 for(Map.Entry<String, List<ConditionMasterDto>> entry : entrySet){
								 ResultExtractDto paramRed = new ResultExtractDto();
								 paramRed.setState(resultExtractDto.getState());
								 paramRed.setOtc(resultExtractDto.getOtc());
								 paramRed.setRtc(resultExtractDto.getRtc());
								 paramRed.setOtcOuter(resultExtractDto.getOtcOuter());
								 paramRed.setRtcOuter(resultExtractDto.getRtcOuter());
								 paramRed.setOtcClose(resultExtractDto.getOtcClose());
								 paramRed.setRtcClose(resultExtractDto.getRtcClose());
								 paramRed.setFilterInner(resultExtractDto.getFilterInner());
								 paramRed.setFilterOuter(resultExtractDto.getFilterOuter());
								 paramRed.setEwFlag(resultExtractDto.getEwFlag());
								 
								 String entryOrderTestCode = entry.getKey();
								 List<ConditionMasterDto> mapDtoList = entry.getValue();
								 List<String> filterInnerList = null;
								 
								 if(mapDtoList != null){
									 filterInnerList = new ArrayList<String>();
									 
									 if(mapDtoList.size() == 1){
										 ConditionMasterDto mapDto = mapDtoList.get(0);
										 paramRed.setOtc(mapDto.getOrderTestCode());
										 paramRed.setRtc(mapDto.getResultTestCode());
										 paramRed.setOtcOuter(mapDto.getOrderTestCode());
										 paramRed.setRtcOuter(mapDto.getResultTestCode());
										 paramRed.setOtcClose(mapDto.getOrderTestCode());
										 if(mapDto.getResultTestCode() != null){
											 paramRed.setRtcClose(mapDto.getResultTestCode() + "%");
										 }
										 
										 //filter
										 Integer conditionFilterFk = mapDto.getConditionFilterFk();
										 String condition = mapDto.getCondition();
										 Map<String, Object> paramMap = new HashMap<String, Object>();
										 paramMap.put("conditionFilterPk", conditionFilterFk);
										 paramMap.put("condition", condition);
										 List<ConditionFilterDto> innerFilterDtoList = asrDao.getConditionFilters(paramMap);
										 if(innerFilterDtoList != null){
											 ConditionFilterDto innerFilterDto = innerFilterDtoList.get(0);
											 if(innerFilterDto != null){
												 String innerFilter = innerFilterDto.getFilter();
												 if(innerFilterDto != null){
													 paramRed.setFilterInner(innerFilter);
												 }
												 String valueType = mapDto.getValueType();
												 String conditionValue = mapDto.getConditionValue();
												 String outerFilter = this.getOuterFilter(valueType, conditionValue, innerFilter);
												 paramRed.setFilterOuter(outerFilter);
											 }
										 }
									 }else if(mapDtoList.size() > 1){
										 boolean isInnerTestSet = false;
										 boolean isOuterTestSet = false;
										 List<String> outerFilterList = new ArrayList<String>();

										 paramRed.setState(resultExtractDto.getState());
										 paramRed.setOtc(resultExtractDto.getOtc());
										 paramRed.setRtc(resultExtractDto.getRtc());
										 paramRed.setOtcOuter(resultExtractDto.getOtcOuter());
										 paramRed.setRtcOuter(resultExtractDto.getRtcOuter());
										 paramRed.setOtcClose(resultExtractDto.getOtcClose());
										 paramRed.setRtcClose(resultExtractDto.getRtcClose());
										 paramRed.setFilterInner(resultExtractDto.getFilterInner());
										 paramRed.setFilterOuter(resultExtractDto.getFilterOuter());
										 paramRed.setEwFlag(resultExtractDto.getEwFlag());
										 
										 for(ConditionMasterDto mapDto : mapDtoList){

											 
											 if(!(isInnerTestSet)){
												 Integer conditionFilterFk = mapDto.getConditionFilterFk();
												 String condition = mapDto.getCondition();
												 Map<String, Object> paramMap = new HashMap<String, Object>();
												 paramMap.put("conditionFilterPk", conditionFilterFk);
												 paramMap.put("condition", condition);
												 List<ConditionFilterDto> innerFilterDtoList = asrDao.getConditionFilters(paramMap);
												 if(innerFilterDtoList != null){
													 ConditionFilterDto innerFilterDto = innerFilterDtoList.get(0);
													 if(innerFilterDto != null){
														 String innerFilter = innerFilterDto.getFilter();
														 paramRed.setOtc(mapDto.getOrderTestCode());
														 paramRed.setRtc(mapDto.getResultTestCode());
														 paramRed.setFilterInner(innerFilter);
														 paramRed.setOtcClose(mapDto.getOrderTestCode());
														 if(mapDto.getResultTestCode() != null){
															 paramRed.setRtcClose(mapDto.getResultTestCode() + "%");
														 }
														 isInnerTestSet = true;
													 }
												 }
												 continue;
											}//if
											if(!(isOuterTestSet)){
												paramRed.setOtcOuter(mapDto.getOrderTestCode());
												paramRed.setRtcOuter(mapDto.getResultTestCode());
												isOuterTestSet = true;
											}
											Integer conditionFilterFk = mapDto.getConditionFilterFk();
											String condition = mapDto.getCondition();
											Map<String, Object> paramMap = new HashMap<String, Object>();
											paramMap.put("conditionFilterPk", conditionFilterFk);
											paramMap.put("condition", condition);
											List<ConditionFilterDto> outerFilterDtoList = asrDao.getConditionFilters(paramMap);
											if(outerFilterDtoList != null){
												ConditionFilterDto outerFilterDto = outerFilterDtoList.get(0);
												if(outerFilterDto != null){
													String innerFilter = outerFilterDto.getFilter();
													String valueType = mapDto.getValueType();
													String conditionValue = mapDto.getConditionValue();
													String outerFilter = this.getOuterFilter(valueType, conditionValue, innerFilter);
													//resultExtractDto.setFilterOuter(outerFilter);
													outerFilterList.add(outerFilter);
												}
											}
										 }//for
										 if((outerFilterList != null) && (outerFilterList.size() > 0)){
											 StringBuilder outerFilterBuilder = new StringBuilder();
											 for(String outerFilter : outerFilterList){
												 outerFilterBuilder.append(outerFilter).append(" or ");
											 }
											 String outerFilter = outerFilterBuilder.substring(0, outerFilterBuilder.lastIndexOf(" or "));
											 paramRed.setFilterOuter(outerFilter);
										 }
									 }//else
									 log.warn("getAbnormalTestResults(): paramRed: " + (paramRed == null ? "NULL" : paramRed.toString()));
								 }//if
								 if(paramRed.getOtc() != null){
									 List<StateResultDto> resultDtoList = getStateAbnormalResult(paramRed);
									 if(resultDtoList != null){
										 log.warn("getAbnormalTestResults(): resultDtoList: " + (resultDtoList == null ? "NULL" : resultDtoList.toString()));
										 dtoList.addAll(resultDtoList);
									 }
								 }
							 }//for
*/							 
						 }
					 }
				}
			}
		}
		return dtoList;
	}
	
	String getMicroOrganismExcludeBlock(List<ConditionMasterDto> excludeMicroOrgDtoList){
		String excludeBlock = null;
		if((excludeMicroOrgDtoList != null) && (excludeMicroOrgDtoList.size() > 0)){
			StringBuilder excludeBlockBuilder = new StringBuilder();
			excludeBlockBuilder.append("(");
			excludeBlockBuilder.append("GTT_MICRO_RESULTS_EXTRACT.order_method = 'MICRO'");
			excludeBlockBuilder.append(" AND (");
			excludeBlockBuilder.append("GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like '").append(excludeMicroOrgDtoList.get(0).getConditionValue()).append("%'");
			//for(ConditionMasterDto cmDto : excludeMicroOrgDtoList){
			for(int i = 1; i < excludeMicroOrgDtoList.size(); i++){
				excludeBlockBuilder.append(" OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like '").append(excludeMicroOrgDtoList.get(i).getConditionValue()).append("%'");
			}
			excludeBlockBuilder.append(")");
			excludeBlockBuilder.append(")");
/*			
			( 
		            GTT_MICRO_RESULTS_EXTRACT.order_method = ''MICRO'' 
		            AND (GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Acinetobacter baumannii%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Acinetobacter species%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Coagulase Negative Staphylococcus%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Candida glabrata (Torulopsis)%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Corynebacterium jeikeium (jk)%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Corynebacterium species%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Cupriavidus pauculus%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Enterococcus faecalis%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Gram Positive Cocci Isolated%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Gram Variable Identification To Follow%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Klebsiella pneumoniae%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Micrococcus luteus%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Moraxella catarrhalis%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Gram Positive Identification To Follow%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Gram Negative Identification To Follow%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Klebsiella oxytoca%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Gram Negative Bacilli Isolated%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Proteus mirabilis%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Aerococcus viridans%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Citrobacter koseri (C. diversus)%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Stenotrophomonas maltophilia (Xanthomonas)%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Serratia marcescens%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Yeast Isolated%''
		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Streptococcus agalactiae%''

		                  OR GTT_MICRO_RESULTS_EXTRACT.TEXTUAL_RESULT_FULL like ''Complete''                  
		                )
		              )
*/			
			excludeBlock = excludeBlockBuilder.toString();
		}
		return excludeBlock;
	}
	
	String getOuterFilter(String valueType, String conditionValue, String filter){
		StringBuilder outerFilterBuilder = new StringBuilder();
		//outerFilterBuilder.append("and (");
		outerFilterBuilder.append("(");
		/*if(valueType != null){
			if(valueType.equals("ST")){
				outerFilterBuilder.append("r.value_type = 'ST'");
			}else if(valueType.equals("NM")){
				outerFilterBuilder.append("r.value_type = 'NM'");
			}
		}*/
		if(conditionValue != null){
			if(valueType.equals("ST")){
				if(filter.indexOf("{0}") != -1){
					//String outerFilter = MessageFormat.format(filter, new Object[]{ conditionValue, });
					String outerFilter = StringUtils.replace(filter, "{0}", conditionValue);
					//log.warn("getOuterFilter(): conditionValue: " + (conditionValue == null ? "NULL" : conditionValue));
					log.warn("getOuterFilter(): outerFilter: " + (outerFilter == null ? "NULL" : outerFilter));
					outerFilterBuilder.append(outerFilter);
				}
				//positiveFilterBuilder.append(" and r.textual_numeric_result like '%").append(typeValue).append("%'");
			}else if(valueType.equals("NM")){
				if(filter.indexOf("{0}") != -1){
					//String outerFilter = MessageFormat.format(filter, new Object[]{ conditionValue, });
					String outerFilter = StringUtils.replace(filter, "{0}", conditionValue);
					log.warn("getOuterFilter(): outerFilter: " + (outerFilter == null ? "NULL" : outerFilter));
					outerFilterBuilder.append(outerFilter);
				}
				//positiveFilterBuilder.append(" and to_number(ltrim(rtrim(r.textual_numeric_result)), '9999999.9999999999') ").append(typeValue);
			}else if(valueType.equals("NAM")){
				outerFilterBuilder.append(filter);
				//positiveFilterBuilder.append(typeValue);
			}
		}else{
			if(valueType != null){
				if(valueType.equals("ST")){
					outerFilterBuilder.append("r.value_type = 'ST'");
				}else if(valueType.equals("NM")){
					outerFilterBuilder.append("r.value_type = 'NM'");
				}
			}
		}
		outerFilterBuilder.append(")");
		return outerFilterBuilder.toString();
	}
	
	public Map<String, List<ConditionMasterDto>> getConditionMasterMap(String state) {
		Map<String, List<ConditionMasterDto>> dtoListMap = null;
		if(state != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				//paramMap.put("state", state);
				paramMap.put("stateAbbreviation", state);
				paramMap.put("status", "active");
				List<String> includeConditionList = new ArrayList<String>();
				includeConditionList.add("Organism Name Not Like");
				paramMap.put("includeConditionList", includeConditionList);				
				List<ConditionMasterDto> dtoList = asrDao.getConditionMaster(paramMap);
				log.warn("getConditionMasterMap(): dtoList: " + (dtoList == null ? "NULL" : dtoList.toString()));
				if(dtoList != null){
					dtoListMap = new HashMap<String, List<ConditionMasterDto>>();
					for(ConditionMasterDto dto : dtoList){
						String otc = dto.getOrderTestCode();
						if(otc != null){
							if(dtoListMap.containsKey(otc)){
								List<ConditionMasterDto> mapDtoList = dtoListMap.get(otc);
								mapDtoList.add(dto);
							}else{
								List<ConditionMasterDto> mapDtoList = new ArrayList<ConditionMasterDto>();
								mapDtoList.add(dto);
								dtoListMap.put(otc, mapDtoList);
							}
						}else{
							String condition = dto.getCondition();
							if(condition != null){
								if(dtoListMap.containsKey(condition)){
									List<ConditionMasterDto> mapDtoList = dtoListMap.get(condition);
									mapDtoList.add(dto);
								}else{
									List<ConditionMasterDto> mapDtoList = new ArrayList<ConditionMasterDto>();
									mapDtoList.add(dto);
									dtoListMap.put(condition, mapDtoList);
								}
							}
						}
					}
				}
			}
		}
		return dtoListMap;
	}

/*	
	@Override
	public AmiLabelDto getEastAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException {
		AmiLabelDto dto = null;
		if(amiLabelDto != null){
			AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
			if(amiLabelDao != null){
				dto = amiLabelDao.getEastAmiLabel(amiLabelDto);
				if(dto == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dto);
					throw businessException;				
				}
			}
		}
		return dto;
	}

	@Override
	public void setEastAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException {
		if(amiLabelDto != null){
			AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
			if(amiLabelDao != null){
				try{
					amiLabelDao.setEastAmiLabel(amiLabelDto);
				}catch(Exception e){
					throw new BusinessException(e.getMessage());
				}
			}			
		}
	}

	@Override
	public void updateEastAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException {
		if(amiLabelDto != null){
			AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
			if(amiLabelDao != null){
				try{
					amiLabelDao.updateEastAmiLabel(amiLabelDto);
				}catch(Exception e){
					throw new BusinessException(e.getMessage());
				}
			}			
		}

	}

	@Override
	public AmiLabelDto getWestAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException {
		AmiLabelDto dto = null;
		if(amiLabelDto != null){
			AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
			if(amiLabelDao != null){
				dto = amiLabelDao.getWestAmiLabel(amiLabelDto);
				if(dto == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dto);
					throw businessException;				
				}
			}			
		}
		return dto;
	}

	@Override
	public void setWestAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException {
		if(amiLabelDto != null){
			AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
			if(amiLabelDao != null){
				try{
					amiLabelDao.setWestAmiLabel(amiLabelDto);
				}catch(Exception e){
					throw new BusinessException(e.getMessage());
				}
			}			
		}
	}

	@Override
	public void updateWestAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException {
		if(amiLabelDto != null){
			AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
			if(amiLabelDao != null){
				try{
					amiLabelDao.updateWestAmiLabel(amiLabelDto);
				}catch(Exception e){
					throw new BusinessException(e.getMessage());
				}
			}			
		}

	}

	public void truncateEastAmiLabel() throws BusinessException{
		AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
		if(amiLabelDao != null){
			try{
				amiLabelDao.truncateEastAmiLabel();
			}catch(Exception e){
				throw new BusinessException(e.getMessage());
			}
		}
	}
	
	public void truncateWestAmiLabel() throws BusinessException{
		AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
		if(amiLabelDao != null){
			try{
				amiLabelDao.truncateWestAmiLabel();
			}catch(Exception e){
				throw new BusinessException(e.getMessage());
			}
		}
	}
	
	public void deleteEastAmiLabel() throws BusinessException{
		AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
		if(amiLabelDao != null){
			try{
				amiLabelDao.deleteEastAmiLabel();
			}catch(Exception e){
				throw new BusinessException(e.getMessage());
			}
		}
	}
	
	public void deleteWestAmiLabel() throws BusinessException{
		AmiLabelDao amiLabelDao = (AmiLabelDao)DAOFactory.getDAOImpl(CMDBConstants.AMI_LABEL_DAO);
		if(amiLabelDao != null){
			try{
				amiLabelDao.deleteWestAmiLabel();
			}catch(Exception e){
				throw new BusinessException(e.getMessage());
			}
		}
	}
*/	
}
