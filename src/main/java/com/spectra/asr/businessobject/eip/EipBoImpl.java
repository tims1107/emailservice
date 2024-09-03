package com.spectra.asr.businessobject.eip;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.AsrDao;
import com.spectra.asr.dto.cm.CMFacilityDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
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

//import org.apache.logging.log4j.LogManager;

/*
import com.spectra.cm.db.constants.CMDBConstants;
import com.spectra.cm.db.dto.ami.label.AmiLabelDto;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.cm.db.dao.ami.label.AmiLabelDao;
import com.spectra.cm.db.dataaccess.util.DAOFactory;
*/

@Slf4j
public class EipBoImpl implements EipBo {
	//private Logger log = Logger.getLogger(EipBoImpl.class);
	
	String[] getFilterZipState(StateResultDto dto, String filterStateBy, String[] targetListArray){
		String[] filterZipStateArray = null;
		if(dto != null){
			if((filterStateBy != null) && (targetListArray != null)){
				String zip = null;
				String state = null;
				for(String target : targetListArray){
					log.warn("getFilterZipState(): target: " + (target == null ? "NULL" : target));
					log.warn("getFilterZipState(): dto.getPatientAccountState(): " + (dto.getPatientAccountState() == null ? "NULL" : dto.getPatientAccountState()));
					log.warn("getFilterZipState(): dto.getFacilityState(): " + (dto.getFacilityState() == null ? "NULL" : dto.getFacilityState()));
					String patientAccountState = dto.getPatientAccountState();
					String facilityState = dto.getFacilityState();
					if(patientAccountState != null){
						if(patientAccountState.equalsIgnoreCase(target)){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
							dto.setReportableState(state);
							dto.setSourceState("patient");
							break;
						}
					}
					if(facilityState != null){
						if(facilityState.equalsIgnoreCase(target)){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setReportableState(state);
							dto.setSourceState("facility");
							break;
						}
					}
					/*
					if(dto.getPatientAccountState().equalsIgnoreCase(target)){
						zip = dto.getPatientAccountZip();
						state = dto.getPatientAccountState();
						dto.setReportableState(state);
						dto.setSourceState("patient");
						break;
					}else if(dto.getFacilityState().equalsIgnoreCase(target)){
						zip = dto.getFacilityZip();
						state = dto.getFacilityState();
						dto.setReportableState(state);
						dto.setSourceState("facility");
						break;
					}
					*/
				}
				log.warn("getFilterZipState(): zip: " + (zip == null ? "NULL" : zip));
				log.warn("getFilterZipState(): state: " + (state == null ? "NULL" : state));
				filterZipStateArray = new String[2];
				filterZipStateArray[0] = zip;
				filterZipStateArray[1] = state;
			}else{
				filterZipStateArray = new String[2];
				filterZipStateArray[0] = dto.getPatientAccountZip();
				filterZipStateArray[1] = dto.getPatientAccountState();
				dto.setReportableState(dto.getPatientAccountState());
				dto.setSourceState("patient");
			}
		}
		return filterZipStateArray;
	}
	
	StateZipCodeDto getStateZipCodeDto(String zip, String state, AsrDao asrDao){
		StateZipCodeDto zipDto = null;
		if(zip != null){
			if(zip.length() > 5){
				zip = zip.substring(0, 5);
			}
			log.warn("getStateZipCodeDto(): zip: " + (zip == null ? "NULL" : zip));
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("zip", zip);
			if(state != null){
				paramMap.put("state", state);
			}
			zipDto = asrDao.getStateDemoByZipCityState(paramMap);
		}
		return zipDto;
	}
	
	public List<StateResultDto> getCdiffResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		String filterStateBy = null;
		String generatorStateTarget = null;
		if(resultExtractDto != null){
			filterStateBy = resultExtractDto.getFilterStateBy();
			if(filterStateBy == null){
				filterStateBy = "p";
			}
			generatorStateTarget = resultExtractDto.getGeneratorStateTarget();
			String[] targetListArray = null;
			if(generatorStateTarget != null){
				targetListArray = generatorStateTarget.split(",");
			}
			
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.getCdiffResults(resultExtractDto);
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}/*else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;
						if(filterStateBy.equals("p")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
							dto.setReportableState(state);
							dto.setSourceState("patient");
						}else if(filterStateBy.equals("f")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setReportableState(state);
							dto.setSourceState("facility");
						}else if(filterStateBy.equals("pf")){
							if(targetListArray != null){
								String[] filterZipStateArray = getFilterZipState(dto, filterStateBy, targetListArray);
								zip = filterZipStateArray[0];
								state = filterZipStateArray[1];
							}else{
								zip = dto.getPatientAccountZip();
								state = dto.getPatientAccountState();
								dto.setReportableState(state);
								dto.setSourceState("patient");
							}
						}
						
						//String zip = dto.getPatientAccountZip();
						if(zip != null){

							////String state = dto.getPatientAccountState();
							//Map<String, Object> paramMap = new HashMap<String, Object>();
							//paramMap.put("zip", zip);
							////paramMap.put("city", "Northvale");
							//if(state != null){
							//	paramMap.put("state", state);
							//}
							//StateZipCodeDto zipDto = asrDao.getStateDemoByZipCityState(paramMap);
							//if(zipDto != null){
							//	//log.warn("getCdiffResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
							//	dto.setPatientAccountCounty(zipDto.getCounty());
							//}
	
							StateZipCodeDto zipDto = getStateZipCodeDto(zip, state, asrDao);
							if(zipDto != null){
								//log.warn("getMugsiResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
								dto.setPatientAccountCounty(zipDto.getCounty());
							}							
						}
						
					}//for
				}*/
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getMrsaResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		String filterStateBy = null;
		String generatorStateTarget = null;
		if(resultExtractDto != null){
			filterStateBy = resultExtractDto.getFilterStateBy();
			if(filterStateBy == null){
				filterStateBy = "p";
			}
			generatorStateTarget = resultExtractDto.getGeneratorStateTarget();
			String[] targetListArray = null;
			if(generatorStateTarget != null){
				targetListArray = generatorStateTarget.split(",");
			}			
			
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.getMrsaResults(resultExtractDto);
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}/*else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;
						if(filterStateBy.equals("p")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
							dto.setReportableState(state);
							dto.setSourceState("patient");
						}else if(filterStateBy.equals("f")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setReportableState(state);
							dto.setSourceState("facility");
						}else if(filterStateBy.equals("pf")){
							if(targetListArray != null){
								String[] filterZipStateArray = getFilterZipState(dto, filterStateBy, targetListArray);
								zip = filterZipStateArray[0];
								state = filterZipStateArray[1];
							}else{
								zip = dto.getPatientAccountZip();
								state = dto.getPatientAccountState();
								dto.setReportableState(state);
								dto.setSourceState("patient");
							}
						}
						
						//String zip = dto.getPatientAccountZip();
						if(zip != null){

							////String state = dto.getPatientAccountState();
							//Map<String, Object> paramMap = new HashMap<String, Object>();
							//paramMap.put("zip", zip);
							////paramMap.put("city", "Northvale");
							//if(state != null){
							//	paramMap.put("state", state);
							//}
							//StateZipCodeDto zipDto = asrDao.getStateDemoByZipCityState(paramMap);
							//if(zipDto != null){
							//	//log.warn("getCdiffResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
							//	dto.setPatientAccountCounty(zipDto.getCounty());
							//}
							
							StateZipCodeDto zipDto = getStateZipCodeDto(zip, state, asrDao);
							if(zipDto != null){
								//log.warn("getMugsiResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
								dto.setPatientAccountCounty(zipDto.getCounty());
							}							
						}
						
					}//for
				}*/
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getMssaResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		String filterStateBy = null;
		String generatorStateTarget = null;
		if(resultExtractDto != null){
			filterStateBy = resultExtractDto.getFilterStateBy();
			if(filterStateBy == null){
				filterStateBy = "p";
			}
			generatorStateTarget = resultExtractDto.getGeneratorStateTarget();
			String[] targetListArray = null;
			if(generatorStateTarget != null){
				targetListArray = generatorStateTarget.split(",");
			}
			
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.getMssaResults(resultExtractDto);
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}/*else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;
						if(filterStateBy.equals("p")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
							dto.setReportableState(state);
							dto.setSourceState("patient");
						}else if(filterStateBy.equals("f")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setReportableState(state);
							dto.setSourceState("facility");
						}else if(filterStateBy.equals("pf")){
							if(targetListArray != null){
								String[] filterZipStateArray = getFilterZipState(dto, filterStateBy, targetListArray);
								zip = filterZipStateArray[0];
								state = filterZipStateArray[1];
							}else{
								zip = dto.getPatientAccountZip();
								state = dto.getPatientAccountState();
								dto.setReportableState(state);
								dto.setSourceState("patient");
							}
						}
						
						//String zip = dto.getPatientAccountZip();
						if(zip != null){

							////String state = dto.getPatientAccountState();
							//Map<String, Object> paramMap = new HashMap<String, Object>();
							//paramMap.put("zip", zip);
							////paramMap.put("city", "Northvale");
							//if(state != null){
							//	paramMap.put("state", state);
							//}
							//StateZipCodeDto zipDto = asrDao.getStateDemoByZipCityState(paramMap);
							//if(zipDto != null){
							//	//log.warn("getCdiffResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
							//	dto.setPatientAccountCounty(zipDto.getCounty());
							//}
					
							StateZipCodeDto zipDto = getStateZipCodeDto(zip, state, asrDao);
							if(zipDto != null){
								//log.warn("getMugsiResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
								dto.setPatientAccountCounty(zipDto.getCounty());
							}							
						}
						
					}//for
				}*/
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getMugsiResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		String filterStateBy = null;
		String generatorStateTarget = null;
		if(resultExtractDto != null){
			filterStateBy = resultExtractDto.getFilterStateBy();
			if(filterStateBy == null){
				filterStateBy = "p";
			}
			generatorStateTarget = resultExtractDto.getGeneratorStateTarget();
			String[] targetListArray = null;
			if(generatorStateTarget != null){
				targetListArray = generatorStateTarget.split(",");
			}
			
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.getMugsiResults(resultExtractDto);
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}/*else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;
						if(filterStateBy.equals("p")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
							dto.setReportableState(state);
							dto.setSourceState("patient");
						}else if(filterStateBy.equals("f")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setReportableState(state);
							dto.setSourceState("facility");
						}else if(filterStateBy.equals("pf")){
							if(targetListArray != null){
								String[] filterZipStateArray = getFilterZipState(dto, filterStateBy, targetListArray);
								zip = filterZipStateArray[0];
								state = filterZipStateArray[1];
								log.warn("getFilterZipState(): dto: " + (dto == null ? "NULL" : dto.toString()));
							}else{
								zip = dto.getPatientAccountZip();
								state = dto.getPatientAccountState();
								dto.setReportableState(state);
								dto.setSourceState("patient");
							}
						}
						
						//String zip = dto.getPatientAccountZip();
						if(zip != null){

							//if(zip.length() > 5){
							//	zip = zip.substring(0, 4);
							//}
							////String state = dto.getPatientAccountState();
							//Map<String, Object> paramMap = new HashMap<String, Object>();
							//paramMap.put("zip", zip);
							////paramMap.put("city", "Northvale");
							//if(state != null){
							//	paramMap.put("state", state);
							//}
							//StateZipCodeDto zipDto = asrDao.getStateDemoByZipCityState(paramMap);
							//if(zipDto != null){
							//	//log.warn("getCdiffResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
							//	dto.setPatientAccountCounty(zipDto.getCounty());
							//}
							
							StateZipCodeDto zipDto = getStateZipCodeDto(zip, state, asrDao);
							if(zipDto != null){
								//log.warn("getMugsiResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
								dto.setPatientAccountCounty(zipDto.getCounty());
							}
						}
						
					}//for
				}*/
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getAbcResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		String filterStateBy = null;
		String generatorStateTarget = null;
		if(resultExtractDto != null){
			filterStateBy = resultExtractDto.getFilterStateBy();
			if(filterStateBy == null){
				filterStateBy = "p";
			}
			generatorStateTarget = resultExtractDto.getGeneratorStateTarget();
			String[] targetListArray = null;
			if(generatorStateTarget != null){
				targetListArray = generatorStateTarget.split(",");
			}
			
			List<String> specimenSourceList = new ArrayList<String>();
			specimenSourceList.add("Blood");
			specimenSourceList.add("PD Fluid");
			resultExtractDto.setSpecimenSourceList(specimenSourceList);
			
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				dtoList = asrDao.getAbcResults(resultExtractDto);
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					throw businessException;				
				}/*else{
					for(StateResultDto dto : dtoList){
						String zip = null;
						String state = null;
						if(filterStateBy.equals("p")){
							zip = dto.getPatientAccountZip();
							state = dto.getPatientAccountState();
							dto.setReportableState(state);
							dto.setSourceState("patient");
						}else if(filterStateBy.equals("f")){
							zip = dto.getFacilityZip();
							state = dto.getFacilityState();
							dto.setReportableState(state);
							dto.setSourceState("facility");
						}else if(filterStateBy.equals("pf")){
							if(targetListArray != null){
								String[] filterZipStateArray = getFilterZipState(dto, filterStateBy, targetListArray);
								zip = filterZipStateArray[0];
								state = filterZipStateArray[1];
							}else{
								zip = dto.getPatientAccountZip();
								state = dto.getPatientAccountState();
								dto.setReportableState(state);
								dto.setSourceState("patient");
							}
						}
						
						
						if(zip != null){
							StateZipCodeDto zipDto = getStateZipCodeDto(zip, state, asrDao);
							if(zipDto != null){
								//log.warn("getMugsiResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
								dto.setPatientAccountCounty(zipDto.getCounty());
							}							
						}
						
					}//for
				}*/
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getWhereBlockEipResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		return dtoList;
	}
	
	public List<StateResultDto> getEipTestResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		List<StateResultDto> filteredDtoList = null;
		if(resultExtractDto != null){
			String state = resultExtractDto.getState();
			if(state != null){
				AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
				if(asrDao != null){
					//DemographicDao demographicDao = (DemographicDao)AsrDaoFactory.getDAOImpl(DemographicDao.class.getSimpleName());
					AsrDemographicService asrDemographicService = (AsrDemographicService)AsrServiceFactory.getServiceImpl(AsrDemographicService.class.getSimpleName());
					Map<String, List<ConditionMasterDto>> dtoListMap = this.getConditionMasterMap(state);
					if(dtoListMap != null){
						dtoList = new ArrayList<StateResultDto>();
						filteredDtoList = new ArrayList<StateResultDto>(); //20181101
						List<StateResultDto> intraLabDtoList = new ArrayList<StateResultDto>(); //20181101
						Set<Map.Entry<String, List<ConditionMasterDto>>> entrySet = dtoListMap.entrySet();
						if(entrySet != null){
							for(Map.Entry<String, List<ConditionMasterDto>> entry : entrySet){
								String entryCondition = entry.getKey();
								List<ConditionMasterDto> mapDtoList = entry.getValue();
								List<String> inList = null;
								String likeBlock = null;
								if(entryCondition.contains("In")){
									inList = this.getConditionList(mapDtoList);
								}else if(entryCondition.contains("Like")){
									ConditionMasterDto dto = mapDtoList.get(0);
									Map<String, Object> paramMap = new HashMap<String, Object>();
									paramMap.put("condition", dto.getCondition());
									paramMap.put("conditionFilterPk", dto.getConditionFilterFk());
									paramMap.put("status", "active");
									List<ConditionFilterDto> filterDtoList = asrDao.getConditionFilters(paramMap);
									ConditionFilterDto filterDto = filterDtoList.get(0);
									String filter = filterDto.getFilter();
									likeBlock = this.getConditionBlock(mapDtoList, filter);
								}
								
								if(entryCondition.contains("State In")){
									resultExtractDto.setStateInList(inList);
								}else if(entryCondition.contains("Result Test Code In")){
									resultExtractDto.setResultTestCodeInList(inList);
								}else if(entryCondition.contains("Order Test Code In")){
									resultExtractDto.setOrderTestCodeInList(inList);
								}else if(entryCondition.contains("Result Test Code Not In")){
									resultExtractDto.setResultTestCodeNotInList(inList);
								}else if(entryCondition.contains("Organism Name Like")){
									resultExtractDto.setMicroOrganismNameLikeBlock(likeBlock);
								}else if(entryCondition.contains("Result Test Name Like")){
									resultExtractDto.setResultTestNameLikeBlock(likeBlock);
								}else if(entryCondition.contains("Organism Name Not Like")){
									resultExtractDto.setMicroOrganismNameNotLikeBlock(likeBlock);
								}
							}//for
							
							log.warn("getEipTestResults(): resultExtractDto: " + (resultExtractDto == null ? "NULL" : resultExtractDto.toString()));

							if(state.contains("CDIFF")){
								dtoList = this.getCdiffResults(resultExtractDto);
							}else if(state.contains("MRSA")){
								dtoList = this.getMrsaResults(resultExtractDto);
							}else if(state.contains("MSSA")){
								dtoList = this.getMssaResults(resultExtractDto);
							}else if(state.contains("MUGSI")){
								dtoList = this.getMugsiResults(resultExtractDto);
							}else if(state.contains("ABC")){
								dtoList = this.getAbcResults(resultExtractDto);
							}
							
							if(dtoList != null){
								for(StateResultDto dto : dtoList){
									CMService cmService = (CMService)AsrServiceFactory.getServiceImpl(CMService.class.getSimpleName());
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
												log.warn("getEipTestResults(): facilityNamebuilder: " + (facilityNamebuilder == null ? "NULL" : facilityNamebuilder.toString()));
											}
										}
										
									}
									
									//filter
									//TEST
/*
									dto.setPatientAccountState(null);
									
									//dto.setOrderMethod("ARUP");

									if(dto.getLabFk() == 5){
										dto.setPerformingLabId("SW");
									}else if(dto.getLabFk() == 6){
										dto.setPerformingLabId("SE");
									}
*/
									//END TEST
									String patLastName = dto.getPatientLastName();
									if((patLastName == null) || (patLastName.trim().length() == 0)){
										dto.setReportableState(state);
										dto.setSourceState("sendout");
										dto.setNotifiedFlag("N");
										asrDemographicService.insertIntraLabsNoDemo(dto);
									}else if(dto.getPatientAccountState() != null){
										dto.setReportableState(state);
										dto.setSourceState("patient");
										filteredDtoList.add(dto);
									}else if((dto.getPatientAccountState() == null) || (dto.getPatientAccountState().trim().length() == 0)){
										Map<String, Object> paramMap = new HashMap<String, Object>();
										paramMap.put("customDepartment", "Sendout");
										List<LovTestCategoryDto> ltcDtoList = asrDemographicService.getLovTestCategory(paramMap);
										boolean isSendout = false;
										for(LovTestCategoryDto ltcDto : ltcDtoList){
											String testCategory = ltcDto.getTestCategory();
											String orderMethod = dto.getOrderMethod();
											isSendout = orderMethod.equalsIgnoreCase(testCategory);
											if(isSendout){
												break;
											}
										}
										if(((dto.getLabFk().intValue() == 5) && (dto.getPerformingLabId().indexOf("SE") != -1)) ||
												((dto.getLabFk().intValue() == 6) && (dto.getPerformingLabId().indexOf("SW") != -1)) ||
												((dto.getLabFk().intValue() == 5) && (isSendout)) ||
												((dto.getLabFk().intValue() == 6) && (isSendout))){
											dto.setPatientAccountAddress1(dto.getFacilityAddress1());
											dto.setPatientAccountAddress2(dto.getFacilityAddress2());
											dto.setPatientAccountCity(dto.getFacilityCity());
											dto.setPatientAccountState(dto.getFacilityState());
											dto.setPatientAccountZip(dto.getFacilityZip());
											dto.setReportableState(state);
											dto.setSourceState("facility");
											filteredDtoList.add(dto);
										}	
										
										if(((dto.getLabFk().intValue() == 5) && (dto.getPerformingLabId().indexOf("SW") != -1)) ||
												((dto.getLabFk().intValue() == 6) && (dto.getPerformingLabId().indexOf("SE") != -1))){

											String orderNumber = dto.getOrderNumber();
											String accessionNo = dto.getAccessionNo();
											String otc = dto.getOrderTestCode();
											String rtc = dto.getResultTestCode();
											if(orderNumber != null){
												Map<String, Object> pMap = new HashMap<String, Object>();
												pMap.put("orderNumber", orderNumber);
												List<StateResultDto> ilndDtoList = asrDemographicService.getIntraLabsNoDemo(pMap);
												if((ilndDtoList == null) || (ilndDtoList.size() == 0)){
													dto.setPerformingLabId("OUTSEND");
													dto.setReportableState(state);
													dto.setSourceState("sendout");
													dto.setNotifiedFlag("N");
													asrDemographicService.insertIntraLabsNoDemo(dto);
												}else{
													for(StateResultDto ilnDto : ilndDtoList){
														if((ilnDto.getOrderNumber().equalsIgnoreCase(orderNumber)) &&
																(ilnDto.getAccessionNo().equalsIgnoreCase(accessionNo)) &&
																(ilnDto.getOrderTestCode().equalsIgnoreCase(otc)) &&
																(ilnDto.getResultTestCode().equalsIgnoreCase(rtc))){
															
														}else{
															dto.setPerformingLabId("OUTSEND");
															dto.setReportableState(state);
															dto.setSourceState("sendout");
															dto.setNotifiedFlag("N");
															asrDemographicService.insertIntraLabsNoDemo(dto);
														}
													}
												}
											}

											//dto.setPerformingLabId("OUTSEND");
											//dto.setReportableState(state);
											//dto.setSourceState("sendout");
											//asrDao.insertIntraLabsNoDemo(dto);
										}
									}

									log.warn("getEipTestResults(): dto: " + (dto == null ? "NULL" : dto.toString()));
									// end filter
								}//for
								for(StateResultDto filteredDto : filteredDtoList){
									String patZip = filteredDto.getPatientAccountZip();
									String patState = filteredDto.getPatientAccountState();
									if(patZip != null){
										StateZipCodeDto zipDto = getStateZipCodeDto(patZip, patState, asrDao);
										if(zipDto != null){
											//log.warn("getMugsiResults(): zipDto: " + (zipDto == null ? "NULL" : zipDto.toString()));
											filteredDto.setPatientAccountCounty(zipDto.getCounty());
										}							
									}									
								}
								dtoList = null;
								dtoList = filteredDtoList;		
							}
						}
					}
				}
			}
		}//if
		return dtoList;
	}
	
	public List<StateResultDto> getEipResults(ResultExtractDto resultExtractDto) throws BusinessException{
		List<StateResultDto> dtoList = null;
		if(resultExtractDto != null){
			String state = resultExtractDto.getState();
			if(state != null){
				AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
				if(asrDao != null){
					Map<String, List<ConditionMasterDto>> dtoListMap = this.getConditionMasterMap(state);
					if(dtoListMap != null){
						dtoList = new ArrayList<StateResultDto>();
						Set<Map.Entry<String, List<ConditionMasterDto>>> entrySet = dtoListMap.entrySet();
						if(entrySet != null){
							for(Map.Entry<String, List<ConditionMasterDto>> entry : entrySet){
								String entryCondition = entry.getKey();
								List<ConditionMasterDto> mapDtoList = entry.getValue();
								List<String> inList = null;
								String likeBlock = null;
								if(entryCondition.contains("In")){
									inList = this.getConditionList(mapDtoList);
								}else if(entryCondition.contains("Like")){
									ConditionMasterDto dto = mapDtoList.get(0);
									Map<String, Object> paramMap = new HashMap<String, Object>();
									paramMap.put("condition", dto.getCondition());
									paramMap.put("conditionFilterPk", dto.getConditionFilterFk());
									List<ConditionFilterDto> filterDtoList = asrDao.getConditionFilters(paramMap);
									ConditionFilterDto filterDto = filterDtoList.get(0);
									String filter = filterDto.getFilter();
									likeBlock = this.getConditionBlock(mapDtoList, filter);
								}
								
								if(entryCondition.contains("State In")){
									resultExtractDto.setStateInList(inList);
								}else if(entryCondition.contains("Result Test Code In")){
									resultExtractDto.setResultTestCodeInList(inList);
								}else if(entryCondition.contains("Order Test Code In")){
									resultExtractDto.setOrderTestCodeInList(inList);
								}else if(entryCondition.contains("Result Test Code Not In")){
									resultExtractDto.setResultTestCodeNotInList(inList);
								}else if(entryCondition.contains("Organism Name Like")){
									resultExtractDto.setMicroOrganismNameLikeBlock(likeBlock);
								}else if(entryCondition.contains("Result Test Name Like")){
									if(likeBlock.indexOf("RESULT_TEST_NAME") != -1){
										int wordIdx = likeBlock.indexOf("RESULT_TEST_NAME");
										String beforeWord = likeBlock.substring(0, wordIdx);
										String atWord = likeBlock.substring(wordIdx);
										StringBuilder likeBlockBuilder = new StringBuilder();
										likeBlockBuilder.append(beforeWord).append("eip.").append(atWord);
										likeBlock = likeBlockBuilder.toString();
									}
									resultExtractDto.setResultTestNameLikeBlock(likeBlock);
								}else if(entryCondition.contains("Organism Name Not Like")){
									resultExtractDto.setMicroOrganismNameNotLikeBlock(likeBlock);
								}else if(entryCondition.contains("Lab Fk In")){
									resultExtractDto.setLabFkInList(inList);
								}else if(entryCondition.contains("Facility Name Not Like")){
									resultExtractDto.setFacilityNameNotLikeBlock(likeBlock);
								}else if(entryCondition.contains("Textual Result Not Like")){
									resultExtractDto.setTextualResultNotLikeBlock(likeBlock);
								}else if(entryCondition.contains("Micro Isolate Like")){
									resultExtractDto.setMicroIsolateLikeBlock(likeBlock);
								}
							}//for
							
							log.warn("getEipResults(): resultExtractDto: " + (resultExtractDto == null ? "NULL" : resultExtractDto.toString()));

							if(state.contains("CDIFF")){
								String whereBlock = this.getCdiffWhereBlock(resultExtractDto);
								resultExtractDto.setWhereBlock(whereBlock);
								dtoList = this.getWhereBlockEipResults(resultExtractDto);
							}else if(state.contains("MRSA")){
								dtoList = this.getMrsaResults(resultExtractDto);
							}else if(state.contains("MSSA")){
								dtoList = this.getMssaResults(resultExtractDto);
							}else if(state.contains("MUGSI")){
								dtoList = this.getMugsiResults(resultExtractDto);
							}
						}
					}
				}
			}
		}//if
		return dtoList;
	}	
	
	String getCdiffWhereBlock(ResultExtractDto resultExtractDto){
		String whereBlock = null;
		if(resultExtractDto != null){
			StringBuilder whereBuilder = new StringBuilder();
			whereBuilder.append("(");
			if(resultExtractDto.getMicroOrganismNameLikeBlock() != null){
				whereBuilder.append("(");
				whereBuilder.append(resultExtractDto.getMicroOrganismNameLikeBlock());
				whereBuilder.append(") ");
			}
			if(resultExtractDto.getOrderTestCodeInList() != null){
				whereBuilder.append("OR eip.order_test_code in ");
				whereBuilder.append("(");
				for(String orderTestCodeIn : resultExtractDto.getOrderTestCodeInList()){
					whereBuilder.append("'").append(orderTestCodeIn).append("'").append(",");
				}
				whereBuilder.deleteCharAt(whereBuilder.lastIndexOf(","));
				whereBuilder.append(") ");
			}
			whereBuilder.append(") ");
			if(resultExtractDto.getLabFkInList() != null){
				whereBuilder.append(" and ");
				whereBuilder.append("(");
				for(String labFkIn : resultExtractDto.getLabFkInList()){
					whereBuilder.append("'").append(labFkIn).append("'").append(",");
				}
				whereBuilder.deleteCharAt(whereBuilder.lastIndexOf(","));
				whereBuilder.append(") ");
			}
			if(resultExtractDto.getTextualResultNotLikeBlock() != null){
				whereBuilder.append(" and ");
				whereBuilder.append(resultExtractDto.getTextualResultNotLikeBlock());
			}
			if(resultExtractDto.getResultTestCodeInList() != null){
				whereBuilder.append("and eip.result_test_code in ");
				whereBuilder.append("(");
				for(String resultTestCodeIn : resultExtractDto.getResultTestCodeInList()){
					whereBuilder.append("'").append(resultTestCodeIn).append("'").append(",");
				}
				whereBuilder.deleteCharAt(whereBuilder.lastIndexOf(","));
				whereBuilder.append(") ");
			}
			if(resultExtractDto.getFacilityNameNotLikeBlock() != null){
				whereBuilder.append(" and ");
				whereBuilder.append(resultExtractDto.getFacilityNameNotLikeBlock());
			}
			whereBlock = whereBuilder.toString();
		}
		return whereBlock;
	}
	
	List<String> getConditionList(List<ConditionMasterDto> mapDtoList){
		List<String> conditionList = null;
		if(mapDtoList != null){
			conditionList = new ArrayList<String>();
			for(ConditionMasterDto dto : mapDtoList){
				conditionList.add(dto.getConditionValue());
			}
		}
		return conditionList;
	}
	
	String getConditionBlock(List<ConditionMasterDto> mapDtoList, String filter){
		String conditionBlock = null;
		if((mapDtoList != null) && (filter != null)){
			StringBuilder blockBuilder = new StringBuilder();
			for(ConditionMasterDto dto : mapDtoList){
				String conditionValue = dto.getConditionValue();
				if(conditionValue != null){
					StringBuilder valueBuilder = new StringBuilder();
					valueBuilder.append("%").append(conditionValue).append("%");
					String conditionFilter = StringUtils.replace(filter, "{0}", valueBuilder.toString());
					blockBuilder.append(conditionFilter).append(" or ");
				}
			}
			conditionBlock = blockBuilder.substring(0, blockBuilder.lastIndexOf(" or "));
		}
		return conditionBlock;
	}
	
	public Map<String, List<ConditionMasterDto>> getConditionMasterMap(String state) {
		Map<String, List<ConditionMasterDto>> dtoListMap = null;
		if(state != null){
			AsrDao asrDao = (AsrDao)AsrDaoFactory.getDAOImpl(AsrDao.class.getSimpleName());
			if(asrDao != null){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("state", state);
				paramMap.put("status", "active");
				List<ConditionMasterDto> dtoList = asrDao.getConditionMaster(paramMap);
				if(dtoList != null){
					dtoListMap = new HashMap<String, List<ConditionMasterDto>>();
					for(ConditionMasterDto dto : dtoList){
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
