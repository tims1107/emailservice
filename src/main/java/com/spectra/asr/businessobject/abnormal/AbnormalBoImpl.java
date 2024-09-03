package com.spectra.asr.businessobject.abnormal;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

//import com.spectra.cm.db.constants.CMDBConstants;
//import com.spectra.cm.db.dto.ami.label.AmiLabelDto;
//import com.spectra.scorpion.framework.constants.ExceptionConstants;
//import com.spectra.scorpion.framework.exception.BusinessException;
//import com.spectra.cm.db.dao.ami.label.AmiLabelDao;
//import com.spectra.cm.db.dataaccess.util.DAOFactory;
@Slf4j
public class AbnormalBoImpl implements AbnormalBo {
	//private Logger log = Logger.getLogger(AbnormalBoImpl.class);
	
	public List<StateResultDto> getStateAbnormalResult(ResultExtractDto resultExtractDto) throws BusinessException{
		log.warn("getStateAbnormalResult(): resultExtractDto: " + (resultExtractDto == null ? "NULL" : resultExtractDto.toString()));
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
			if(resultExtractDto.getOtc().equals("336"))
				System.out.println(resultExtractDto);

			if(asrDao != null){
				CMService cmService = (CMService)AsrServiceFactory.getServiceImpl(CMService.class.getSimpleName());
				AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
				dtoList = asrDao.callStateResults(resultExtractDto);
				log.warn("getStateAbnormalResult(): dtoList: " + (dtoList == null ? "NULL" : String.valueOf(dtoList.size())));
				
				//TODO: if same req has sourceState noaddr and sendout, filter out noaddr and keep only sendout. filter in map
				
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;

						String eastWest = resultExtractDto.getEastWestFlag();
						Integer labFk = dto.getLabFk();
						if((labFk.intValue() == 5) && (eastWest.equalsIgnoreCase("East"))){
							dto.setEastWest(eastWest);
						}else if((labFk.intValue() == 7) && (eastWest.equalsIgnoreCase("South"))){
							dto.setEastWest(eastWest);
						}else if((labFk.intValue() == 6) && (eastWest.equalsIgnoreCase("West"))){
							dto.setEastWest(eastWest);
						}else{
							dto.setEastWest(eastWest);
						}
//
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
							log.warn("getStateAbnormalResult(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
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
						}//if
						
						log.warn("getStateAbnormalResult(): sourceState: " + (sourceState == null ? "NULL" : sourceState));
						if(asrDemographicService != null){
							//if(sourceState.equals("sendout")){
							if((sourceState.equals("sendout")) || (sourceState.equals("noaddr"))){
								String orderNumber = dto.getOrderNumber();
								String accessionNo = dto.getAccessionNo();
								String otc = dto.getOrderTestCode();
								String rtc = dto.getResultTestCode();
								StringBuilder dtoKeyBuilder = new StringBuilder();
								dtoKeyBuilder.append(orderNumber).append(accessionNo).append(otc).append(rtc);
								log.warn("getStateAbnormalResult(): dtoKeyBuilder: " + (dtoKeyBuilder == null ? "NULL" : dtoKeyBuilder.toString()));
								if(orderNumber != null){
									Map<String, Object> pMap = new HashMap<String, Object>();
									pMap.put("orderNumber", orderNumber);
									List<StateResultDto> ilndDtoList = asrDemographicService.getIntraLabsNoDemo(pMap);
									//log.warn("getStateAbnormalResult(): ilndDtoList: " + (ilndDtoList == null ? "NULL" : ilndDtoList.toString()));
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

									}
								}
							}else{
								filteredDtoList.add(dto);
							}
						}
					}//for
				}
			}
		}
		//return dtoList;
		return filteredDtoList;
	}

	/**
	 * @param resultExtractDto
	 * @return
	 * @throws BusinessException
	 */
	public List<StateResultDto> getAbnormalTestResults(ResultExtractDto resultExtractDto) throws BusinessException {
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			String state = resultExtractDto.getState();
			String stateTarget = resultExtractDto.getGeneratorStateTarget();
			String ewFlag = resultExtractDto.getEwFlag();
			resultExtractDto.setEastWestFlag(ewFlag);
			log.warn("getAbnormalTestResults(): stateTarget: " + (stateTarget == null ? "NULL" : stateTarget));
			if((state != null) && (stateTarget != null)){

// Break point start asrDao
				AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
				if(asrDao != null){
					 Map<String, List<ConditionMasterDto>> dtoListMap = this.getConditionMasterMap(state);
					 if(dtoListMap != null){
						 dtoList = new ArrayList<StateResultDto>();
						 Set<Map.Entry<String, List<ConditionMasterDto>>> entrySet = dtoListMap.entrySet();
						 log.warn("getAbnormalTestResults(): entrySet: " + (entrySet == null ? "NULL" : entrySet.toString()));
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

							List<ConditionMasterDto> conditionMasterDtoList = entrySet.stream()
									.map(t -> t.getValue())
									 .flatMap(Collection::stream)
									 .collect(Collectors.toList());

							log.info(stateTarget);
							 conditionMasterDtoList.stream()
							.forEach(System.out::println);

							 //System.exit(0);
							 
							 
							 //asrDao.callProcTrackResults(state);
							 asrDao.callProcTrackResults(stateTarget);
// *****************************

							 //List<StateResultDto> sendoutdtoList = IntraLabsSendoutHandler.handle(resultExtractDto);
							 //log.warn("getAbnormalTestResults(): sendoutdtoList: " + (sendoutdtoList == null ? "NULL" : sendoutdtoList.toString()));

							 
							 for(Map.Entry<String, List<ConditionMasterDto>> entry : entrySet){
								 ResultExtractDto paramRed = new ResultExtractDto();
								 //paramRed.setState(resultExtractDto.getState());
								 paramRed.setState(stateTarget);
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
										 paramMap.put("status", "active");

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
												 //log.warn("getAbnormalTestResults(): valueType: " + (valueType == null ? "NULL" : valueType));
												 //log.warn("getAbnormalTestResults(): conditionValue: " + (conditionValue == null ? "NULL" : conditionValue));
												 //log.warn("getAbnormalTestResults(): outerFilter: " + (outerFilter == null ? "NULL" : outerFilter));
												 paramRed.setFilterOuter(outerFilter);

												 System.out.println(outerFilter);
											 }
										 }


									 }else if(mapDtoList.size() > 1){
										 boolean isInnerTestSet = false;
										 boolean isOuterTestSet = false;
										 List<String> outerFilterList = new ArrayList<String>();

										 //paramRed.setState(resultExtractDto.getState());
										 paramRed.setState(stateTarget);
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
												 paramMap.put("status", "active");
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
											paramMap.put("status", "active");

											 System.out.println(paramMap);
											List<ConditionFilterDto> outerFilterDtoList = asrDao.getConditionFilters(paramMap);
											if(outerFilterDtoList != null){
												ConditionFilterDto outerFilterDto = outerFilterDtoList.get(0);
												if(outerFilterDto != null){
													String innerFilter = outerFilterDto.getFilter();
													String valueType = mapDto.getValueType();
													String conditionValue = mapDto.getConditionValue();
													String outerFilter = this.getOuterFilter(valueType, conditionValue, innerFilter);
													 log.warn("getAbnormalTestResults(): valueType: " + (valueType == null ? "NULL" : valueType));
													 log.warn("getAbnormalTestResults(): conditionValue: " + (conditionValue == null ? "NULL" : conditionValue));
													 log.warn("getAbnormalTestResults(): innerFilter: " + (innerFilter == null ? "NULL" : innerFilter));
													 log.warn("getAbnormalTestResults(): outerFilter: " + (outerFilter == null ? "NULL" : outerFilter));
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
									 if(paramRed.getFilterInner().indexOf("{0}") != -1){
										 paramRed.setFilterInner(null);
									 }
									 
									 paramRed.setFilterStateBy(resultExtractDto.getFilterStateBy());

									 System.out.println(paramRed);
									 
									 log.warn("getAbnormalTestResults(): paramRed: " + (paramRed == null ? "NULL" : paramRed.toString()));
								 }//if
								 if(paramRed.getOtc() != null){
									 paramRed.setEastWestFlag(ewFlag);
									 log.warn("getAbnormalTestResults(): paramRed: " + (paramRed == null ? "NULL" : paramRed.toString()));
									 List<StateResultDto> resultDtoList = getStateAbnormalResult(paramRed);
									 if(resultDtoList != null){
										 List<StateResultDto> filteredMicroList = new ArrayList<StateResultDto>();
										 for(StateResultDto resultDto : resultDtoList){
											 String orderMethod = resultDto.getOrderMethod();
											 if((orderMethod != null) && (orderMethod.equalsIgnoreCase("MICRO"))){
												 String resultTestName = resultDto.getResultTestName().trim();
												 //if((resultTestName != null) && (resultTestName.indexOf("Isolate") != -1)){
													 filteredMicroList.add(resultDto);
												 //}
											 }else{
												 filteredMicroList.add(resultDto);
											 }
										 }
										 //log.warn("getAbnormalTestResults(): resultDtoList: " + (resultDtoList == null ? "NULL" : resultDtoList.toString()));
										 //dtoList.addAll(resultDtoList);
										 log.warn("getAbnormalTestResults(): filteredMicroList: " + (filteredMicroList == null ? "NULL" : filteredMicroList.toString()));
										 dtoList.addAll(filteredMicroList);
									 }
								 }
							 }//for
						 }
					 }
				}
			}
		}

		log.info("dtoList");
		dtoList.stream()
				.forEach(System.out::println);




		return dtoList;
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
				log.warn("getOuterFilter(): filter: " + (filter == null ? "NULL" : filter));
			}
		}else{
			if(valueType != null){
				if(valueType.equals("ST")){
					outerFilterBuilder.append("r.value_type = 'ST'");
				}else if(valueType.equals("NM")){
					outerFilterBuilder.append("r.value_type = 'NM'");
				}else if(valueType.equals("NAM")){
					outerFilterBuilder.append(filter);
					//positiveFilterBuilder.append(typeValue);
					log.warn("getOuterFilter(): filter: " + (filter == null ? "NULL" : filter));
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
				List<String> excludeConditionList = new ArrayList<String>();
				excludeConditionList.add("Organism Name Not Like");
				paramMap.put("excludeConditionList", excludeConditionList);
				List<ConditionMasterDto> dtoList = asrDao.getConditionMaster(paramMap);
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
