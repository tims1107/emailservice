package com.spectra.result.transporter.businessobject.spring.ora.rr;

import com.spectra.demographic.loader.dto.state.StateZipCodeDto;
import com.spectra.result.transporter.dao.ora.rr.ResultRepositoryDao;
import com.spectra.result.transporter.dataaccess.spring.ora.rr.RepositoryDAOFactory;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.PositivityFilterDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/*
import com.spectra.physician.master.dataaccess.spring.provider.ProviderDAOFactory;
import com.spectra.physician.master.dto.provider.ProviderContainerDto;
import com.spectra.physician.master.dto.provider.ProviderDto;
import com.spectra.scorpion.framework.exception.BusinessException;
//import com.spectra.physician.master.dao.ms.provider.ProviderDao;
import com.spectra.physician.master.dao.ms.provider.plac.PlacEastProviderDao;
import com.spectra.physician.master.dao.ms.provider.plac.PlacWestProviderDao;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.physician.master.dao.ws.provider.CorpProviderWsDao;
*/
@Slf4j
public class RepositoryBoImpl implements RepositoryBo {
	//private Logger log = Logger.getLogger(RepositoryBoImpl.class);
	
	protected RepositoryDAOFactory repositoryDAOFactory;
	
	@Override
	public void setDaoFactory(RepositoryDAOFactory repositoryDAOFactory) {
		this.repositoryDAOFactory = repositoryDAOFactory;
	}
	
	public List<RepositoryResultDto> callStateResultsStoredProc(Map paramMap)  throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dao.callStateResultsStoredProc(paramMap);
					dtoList = (List<RepositoryResultDto>)paramMap.get("stateResults");
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> callStateEipResultsStoredProc(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dao.callStateEipResultsStoredProc(paramMap);
					dtoList = (List<RepositoryResultDto>)paramMap.get("stateResults");
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<PositivityFilterDto> getPositivityFilters(String filterKey) throws BusinessException {
		List<PositivityFilterDto> dtoList = null;
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			if(dao != null){
				dtoList = dao.getPositivityFilters(filterKey);
			}
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getDailyTestResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getDailyTestResults(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;		
	}
	
	public List<RepositoryResultDto> getStateDailyTestResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(paramMap.containsKey("state")){
				Map<String, List<TestPropertiesDto>> dtoListMap = this.getTestPropertiesMap(paramMap);
				if(dtoListMap != null){
					dtoList = new ArrayList<RepositoryResultDto>();
					Set<Map.Entry<String, List<TestPropertiesDto>>> entrySet = dtoListMap.entrySet();
					for(Map.Entry<String, List<TestPropertiesDto>> entry : entrySet){
						String entryTestCode = entry.getKey();
						List<TestPropertiesDto> mapDtoList = entry.getValue();
						Map<String, Object> pMap = new HashMap<String, Object>();
						pMap.put("state", (String)paramMap.get("state"));
						if(paramMap.containsKey("ewFlag")){
							pMap.put("ewFlag", (String)paramMap.get("ewFlag"));
						}
						List<String> positiveFilterList = null;
						if(mapDtoList != null){
							positiveFilterList = new ArrayList<String>();
							if(mapDtoList.size() == 1){
								TestPropertiesDto mapDto = mapDtoList.get(0);
								pMap.put("positivityCtc", mapDto.getCompoundTestCode());
								pMap.put("positivityTc", mapDto.getTestCode());
								pMap.put("positiveCtc", mapDto.getCompoundTestCode());
								pMap.put("positiveTc", mapDto.getTestCode());		
								pMap.put("positivityCtcClose", mapDto.getCompoundTestCode());
								if(mapDto.getTestCode() != null){
									pMap.put("positivityTcClose", mapDto.getTestCode() + "%");
								}
								
								//List<String> positivityFilterList = new ArrayList<String>();
								String filterKey = mapDto.getPositivity();
								List<PositivityFilterDto> positivityFilterList = null;
								if(filterKey != null){
									positivityFilterList = this.getPositivityFilters(filterKey);
									if(positivityFilterList != null){
										pMap.put("positivityFilterList", positivityFilterList);
										String valueType = mapDto.getValueType();
										String typeValue = mapDto.getTypeValue();
										/*
										StringBuilder positiveFilterBuilder = new StringBuilder();
										positiveFilterBuilder.append("and (");
										if(valueType != null){
											if(valueType.equals("ST")){
												positiveFilterBuilder.append("r.value_type = 'ST'");
											}else if(valueType.equals("NM")){
												positiveFilterBuilder.append("r.value_type = 'NM'");
											}
										}
										if(typeValue != null){
											if(valueType.equals("ST")){
												positiveFilterBuilder.append(" and r.textual_numeric_result like '%").append(typeValue).append("%'");
											}else if(valueType.equals("NM")){
												positiveFilterBuilder.append(" and to_number(ltrim(rtrim(r.textual_numeric_result)), '9999999.9999999999') ").append(typeValue);
											}
										}
										positiveFilterBuilder.append(")");
										*/
										String posFilter = this.getPositiveFilter(valueType, typeValue);
										//List<String> positiveFilterList = new ArrayList<String>();
										positiveFilterList.add(posFilter);
										pMap.put("positiveFilterList", positiveFilterList);
									}
								}
								

							}else if(mapDtoList.size() > 1){
								boolean isPositivitySet = false;
								boolean isPositiveSet = false;
								for(TestPropertiesDto mapDto : mapDtoList){
									if(!(isPositivitySet)){
										String filterKey = mapDto.getPositivity();
										List<PositivityFilterDto> positivityFilterList = null;
										if(filterKey != null){
											positivityFilterList = this.getPositivityFilters(filterKey);
											if(positivityFilterList != null){
												pMap.put("positivityCtc", mapDto.getCompoundTestCode());
												pMap.put("positivityTc", mapDto.getTestCode());
												pMap.put("positivityFilterList", positivityFilterList);
												pMap.put("positivityCtcClose", mapDto.getCompoundTestCode());
												if(mapDto.getTestCode() != null){
													pMap.put("positivityTcClose", mapDto.getTestCode() + "%");
												}
												isPositivitySet = true;
											}
										}
										continue;
									}//if
									if(!(isPositiveSet)){
										pMap.put("positiveCtc", mapDto.getCompoundTestCode());
										pMap.put("positiveTc", mapDto.getTestCode());
										isPositiveSet = true;
									}
									String filterKey = mapDto.getPositivity();
									log.debug("getStateDailyTestResults(): filterKey for positive filter: " + (filterKey == null ? "NULL" : filterKey));
									//List<PositivityFilterDto> positiveFilterList = null;
									if(filterKey != null){
										List<PositivityFilterDto> posFilterList = this.getPositivityFilters(filterKey);
										log.debug("getStateDailyTestResults(): posFilterList for positive filter: " + (posFilterList == null ? "NULL" : String.valueOf(posFilterList.size())));
										if((posFilterList != null) && (posFilterList.size() == 0)){
											String valueType = mapDto.getValueType();
											String typeValue = mapDto.getTypeValue();
											String posFilter = this.getPositiveFilter(valueType, typeValue);
											log.debug("getStateDailyTestResults(): valueType for positive filter: " + (valueType == null ? "NULL" : valueType));
											log.debug("getStateDailyTestResults(): typeValue for positive filter: " + (typeValue == null ? "NULL" : typeValue));
											log.debug("getStateDailyTestResults(): posFilter for positive filter: " + (posFilter == null ? "NULL" : posFilter));
											//List<String> positiveFilterList = new ArrayList<String>();
											positiveFilterList.add(posFilter);
										}
									}
								}//for
								pMap.put("positiveFilterList", positiveFilterList);
							}
						}//if
						log.debug("getStateDailyTestResults(): pMap: " + (pMap == null ? "NULL" : pMap.toString()));
						List<RepositoryResultDto> testDtoList = this.getDailyTestResults(pMap);
						if(testDtoList != null){
							log.debug("getStateDailyTestResults(): testDtoList: " + (testDtoList == null ? "NULL" : testDtoList.toString()));
							dtoList.addAll(testDtoList);
						}
					}//for
				}
			}
		}
		return dtoList;
	}
	
	String getPositiveFilter(String valueType, String typeValue){
		StringBuilder positiveFilterBuilder = new StringBuilder();
		positiveFilterBuilder.append("and (");
		if(valueType != null){
			if(valueType.equals("ST")){
				positiveFilterBuilder.append("r.value_type = 'ST'");
			}else if(valueType.equals("NM")){
				positiveFilterBuilder.append("r.value_type = 'NM'");
			}
		}
		if(typeValue != null){
			if(valueType.equals("ST")){
				positiveFilterBuilder.append(" and r.textual_numeric_result like '%").append(typeValue).append("%'");
			}else if(valueType.equals("NM")){
				positiveFilterBuilder.append(" and to_number(ltrim(rtrim(r.textual_numeric_result)), '9999999.9999999999') ").append(typeValue);
			}else if(valueType.equals("NAM")){
				positiveFilterBuilder.append(typeValue);
			}
		}
		positiveFilterBuilder.append(")");
		return positiveFilterBuilder.toString();
	}
	
	public List<TestPropertiesDto> getTestPropertiesByCtc(Map paramMap) throws BusinessException{
		List<TestPropertiesDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getTestPropertiesByCtc(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;		
	}
	
	public Map<String, List<TestPropertiesDto>> getTestPropertiesMap(Map paramMap) throws BusinessException{
		Map<String, List<TestPropertiesDto>> dtoListMap = null;
		if(paramMap != null){
			List<TestPropertiesDto> dtoList = this.getTestPropertiesByCtc(paramMap);
			if(dtoList != null){
				dtoListMap = this.getTestPropertiesMap(dtoList);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoListMap;
	}
	
	public Map<String, List<TestPropertiesDto>> getTestPropertiesMap(List<TestPropertiesDto> dtoList) throws BusinessException{
		Map<String, List<TestPropertiesDto>> dtoListMap = null;
		if(dtoList != null){
			dtoListMap = new HashMap<String, List<TestPropertiesDto>>();
			for(TestPropertiesDto dto : dtoList){
				String ctc = dto.getCompoundTestCode();
				if(dtoListMap.containsKey(ctc)){
					List<TestPropertiesDto> mapDtoList = dtoListMap.get(ctc);
					mapDtoList.add(dto);
				}else{
					List<TestPropertiesDto> mapDtoList = new ArrayList<TestPropertiesDto>();
					mapDtoList.add(dto);
					dtoListMap.put(ctc, mapDtoList);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL dtoList").getMessage());
		}
		return dtoListMap;
	}
	
	public List<RepositoryResultDto> getMugsiResults(Map paramMap) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getMugsiResults(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getMrsaResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getMrsaResults(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getCdiffResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getCdiffResults(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getMssaResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getMssaResults(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getCreResults(Map paramMap) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getCreResults(paramMap);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL paramMap").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getStateResults(String procType, String state) throws BusinessException {
		List<RepositoryResultDto> dtoList = null;
		if((procType != null) && (state != null)){
			if(this.repositoryDAOFactory != null){
				Map paramMap = new HashMap();
				paramMap.put("procType", procType);
				paramMap.put("state", state);
				paramMap.put("stateResults", dtoList);
				
				//dtoList = this.callStateResultsStoredProc(paramMap);
				dtoList = this.getStateDailyTestResults(paramMap);
			}
		}
		return dtoList;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsByCounty(String procType, String state) throws BusinessException {
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if((procType != null) && (state != null)){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					List<RepositoryResultDto> dtoList = this.getStateResults(procType, state);
					if(dtoList != null){
						dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
						for(RepositoryResultDto dto : dtoList){
							String zip = dto.getPatientAccountZip();
							String city = dto.getPatientAccountCity();
							//String state = dto.getPatientAccountState();
							Map<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("zip", zip);
							paramMap.put("city", city);
							paramMap.put("state", state);
							if(zip != null){
								StateZipCodeDto zipDto = dao.getStateDemoByZipCityState(paramMap);
								
								String county = (zipDto == null ? null : zipDto.getCounty());
								if(dtoListMap.containsKey(county)){
									List<RepositoryResultDto> dtoCountyList = dtoListMap.get(county);
									dtoCountyList.add(dto);
								}else{
									List<RepositoryResultDto> dtoCountyList = new ArrayList<RepositoryResultDto>();
									dtoCountyList.add(dto);
									dtoListMap.put(county, dtoCountyList);
								}
							}
						}//for
					}					
				}
			}
		}
		return dtoListMap;
	}
	
	public List<RepositoryResultDto> getStateResults(String procType, String state, String ewFlag) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if((procType != null) && (state != null)){
			if(this.repositoryDAOFactory != null){
				Map paramMap = new HashMap();
				paramMap.put("procType", procType);
				paramMap.put("state", state);
				paramMap.put("stateResults", dtoList);
				paramMap.put("ewFlag", ewFlag);
				
				//dtoList = this.callStateResultsStoredProc(paramMap);
				dtoList = this.getStateDailyTestResults(paramMap);
			}
		}
		return dtoList;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsByCounty(String procType, String state, String ewFlag) throws BusinessException{
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if((procType != null) && (state != null)){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					List<RepositoryResultDto> dtoList = this.getStateResults(procType, state, ewFlag);
					if(dtoList != null){
						dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
						for(RepositoryResultDto dto : dtoList){
							String zip = dto.getPatientAccountZip();
							String city = dto.getPatientAccountCity();
							//String state = dto.getPatientAccountState();
							Map<String, String> paramMap = new HashMap<String, String>();
							paramMap.put("zip", zip);
							paramMap.put("city", city);
							paramMap.put("state", state);
							if(zip != null){
								StateZipCodeDto zipDto = dao.getStateDemoByZipCityState(paramMap);
								
								String county = (zipDto == null ? null : zipDto.getCounty());
								if(dtoListMap.containsKey(county)){
									List<RepositoryResultDto> dtoCountyList = dtoListMap.get(county);
									dtoCountyList.add(dto);
								}else{
									List<RepositoryResultDto> dtoCountyList = new ArrayList<RepositoryResultDto>();
									dtoCountyList.add(dto);
									dtoListMap.put(county, dtoCountyList);
								}
							}
						}//for
					}					
				}
			}
		}
		return dtoListMap;
	}
	
	public int insertRsResultsProcessed(RepositoryResultDto repositoryResultDto) throws BusinessException {
		int rowsInserted = 0;
		if(repositoryResultDto != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					rowsInserted = dao.insertRsResultsProcessed(repositoryResultDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryResultDto").getMessage());
		}
		return rowsInserted;
	}
	
	public int insertRsEipResultsProcessed(RepositoryResultDto repositoryResultDto) throws BusinessException{
		int rowsInserted = 0;
		if(repositoryResultDto != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					rowsInserted = dao.insertRsEipResultsProcessed(repositoryResultDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryResultDto").getMessage());
		}
		return rowsInserted;
	}
	
	public int updateRsResultsExtract(RepositoryResultDto repositoryResultDto) throws BusinessException {
		int rowsUpdated = 0;
		if(repositoryResultDto != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					rowsUpdated = dao.updateRsResultsExtract(repositoryResultDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryResultDto").getMessage());
		}
		return rowsUpdated;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsFromDtoList(List<RepositoryResultDto> dtoList) throws BusinessException{
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if(dtoList != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){			
					dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
					for(RepositoryResultDto dto : dtoList){
						String city = dto.getPatientAccountCity();
						String state = dto.getPatientAccountState();
						String zip = dto.getPatientAccountZip();

						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("zip", zip);
						paramMap.put("city", city);
						paramMap.put("state", state);
						
						StateZipCodeDto zipDto = dao.getStateDemoByZipCityState(paramMap);
						String county = (zipDto == null ? null : zipDto.getCounty());
						dto.setPatientAccountCounty(county);
						
						if(dtoListMap.containsKey(state)){
							List<RepositoryResultDto> dtoStateList = dtoListMap.get(state);
							dtoStateList.add(dto);
						}else{
							List<RepositoryResultDto> dtoStateList = new ArrayList<RepositoryResultDto>();
							dtoStateList.add(dto);
							dtoListMap.put(state, dtoStateList);
						}				
					}
				}
			}
		}
		return dtoListMap;
	}
	
	public Map<String, List<RepositoryResultDto>> getStateResultsFromDtoListByCounty(List<RepositoryResultDto> dtoList) throws BusinessException{
		Map<String, List<RepositoryResultDto>> dtoListMap = null;
		if(dtoList != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoListMap = new HashMap<String, List<RepositoryResultDto>>();
					for(RepositoryResultDto dto : dtoList){
						String zip = dto.getPatientAccountZip();
						String city = dto.getPatientAccountCity();
						String state = dto.getPatientAccountState();
						
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("zip", zip);
						//paramMap.put("city", city);
						paramMap.put("state", state);

						StateZipCodeDto zipDto = dao.getStateDemoByZipCityState(paramMap);
						//String state = (zipDto == null ? null : zipDto.getStateAbbr());
						String county = (zipDto == null ? null : zipDto.getCounty());
						dto.setPatientAccountCounty(county);
						StringBuilder keyBuilder = new StringBuilder();
						//keyBuilder.append(zip).append(".").append(state).append(".").append(county);
						keyBuilder.append(state).append(".").append(county);
						String key = keyBuilder.toString();
						if(dtoListMap.containsKey(key)){
							List<RepositoryResultDto> dtoCountyList = dtoListMap.get(key);
							dtoCountyList.add(dto);
						}else{
							List<RepositoryResultDto> dtoCountyList = new ArrayList<RepositoryResultDto>();
							dtoCountyList.add(dto);
							dtoListMap.put(key, dtoCountyList);
						}
					}					
				}
			}
		}
		return dtoListMap;
	}
	
	public void setCountyForStateResults(List<RepositoryResultDto> dtoList) throws BusinessException{
		if(dtoList != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					for(RepositoryResultDto dto : dtoList){
						String zip = dto.getPatientAccountZip();
						String city = dto.getPatientAccountCity();
						String state = dto.getPatientAccountState();
						Map<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("zip", zip);
						paramMap.put("city", city);
						paramMap.put("state", state);
						if(zip != null){
							StateZipCodeDto zipDto = dao.getStateDemoByZipCityState(paramMap);
							
							String county = (zipDto == null ? null : zipDto.getCounty());
							dto.setPatientAccountCounty(county);
						}
					}//for					
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL dtoList").getMessage());
		}
	}
	
	public List<StatePropertiesDto> getStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException{
		List<StatePropertiesDto> dtoList = null;
		if(statePropertiesDto != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getStateProperties(statePropertiesDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL statePropertiesDto").getMessage());
		}
		return dtoList;
	}
	
	public int updateRsStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException{
		int rowsUpdated = -1;
		if(statePropertiesDto != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					rowsUpdated = dao.updateRsStateProperties(statePropertiesDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL statePropertiesDto").getMessage());
		}
		return rowsUpdated;
	}
	
	public int insertRsStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException{
		int rowsInserted = -1;
		if(statePropertiesDto != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					rowsInserted = dao.insertRsStateProperties(statePropertiesDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL statePropertiesDto").getMessage());
		}
		return rowsInserted;
	}
	
	public List<String> getNonElrStateProperties(Map paramMap) throws BusinessException {
		List<String> stateList = null;
		if(paramMap != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					stateList = dao.getNonElrStateProperties(paramMap);
				}
			}
		}
		return stateList;
	}
	
/*	
	public void callResultsStoredProc() throws BusinessException{
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			dao.callResultsStoredProc();
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
		}
	}
	
	public Integer getLatestLoadCount() throws BusinessException{
		Integer loadCount = null;
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			if(dao != null){
				loadCount = dao.getLatestLoadCount();
			}
		}
		return loadCount;
	}
	
	public List<String> getResultRequisitions() throws BusinessException{
		List<String> reqList = null;
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			if(dao != null){
				reqList = dao.getResultRequisitions();
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
		}
		return reqList;
	}
	
	public List<String> getFacilityIds() throws BusinessException{
		List<String> facilityList = null;
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			if(dao != null){
				facilityList = dao.getFacilityIds();
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
		}
		return facilityList;
	}
	
	public List<RepositoryResultDto> getResultWithoutDemographic() throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			if(dao != null){
				dtoList = dao.getResultWithoutDemographic();
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getResultByRequisition(String reqNo) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(reqNo != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getResultByRequisition(reqNo);
				}
			}else{
				throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL reqNo").getMessage());
		}
		return dtoList;
	}
	
	public List<RepositoryResultDto> getResultByFacilityId(String facilityId) throws BusinessException{
		List<RepositoryResultDto> dtoList = null;
		if(facilityId != null){
			if(this.repositoryDAOFactory != null){
				ResultRepositoryDao dao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
				if(dao != null){
					dtoList = dao.getResultByFacilityId(facilityId);
				}
			}else{
				throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL reqNo").getMessage());
		}
		return dtoList;
	}
	
	public Map<String, List<PatientRecord>> getListMap() throws BusinessException{
		Map<String, List<PatientRecord>> listMap = null;
		if(this.repositoryDAOFactory != null){
			this.callResultsStoredProc();
			listMap = this.getFacilityPatientRecordListMap();
		}
		return listMap;
	}
	
	public Map<String, List<PatientRecord>> getFacilityPatientRecordListMap() throws BusinessException{
		Map<String, List<PatientRecord>> listMap = null;
		if(this.repositoryDAOFactory != null){
			listMap = new HashMap<String, List<PatientRecord>>();
			
			List<String> reqList = this.getResultRequisitions();
			if(reqList != null){
				for(String req : reqList){
					List<RepositoryResultDto> resultDtoList = this.getResultByRequisition(req);
					if(resultDtoList != null){
						RepositoryResultDto dto = resultDtoList.get(0);
						PatientRecord patientRecord = this.repositoryResultDtoToPatientRecord(dto);
						if(patientRecord != null){
							List<OBXRecord> obxList = new ArrayList<OBXRecord>();
							List<NTERecord> nteList = new ArrayList<NTERecord>();
							for(RepositoryResultDto resultDto : resultDtoList){
								OBXRecord obxRecord = this.repositoryResultDtoToOBXRecord(resultDto);
								obxList.add(obxRecord);
								
								NTERecord nteRecord = this.repositoryResultDtoToNTERecord(resultDto);
								nteList.add(nteRecord);
							}
							
							patientRecord.setObxList(obxList);
							patientRecord.setNteList(nteList);
							
							String facilityId = dto.getFacilityId();
							if(facilityId != null){
								if(listMap.containsKey(facilityId)){
									List<PatientRecord> patientRecList = listMap.get(facilityId);
									if(patientRecList != null){
										patientRecList.add(patientRecord);
									}
								}else{
									List<PatientRecord> patientRecList = new ArrayList<PatientRecord>();
									patientRecList.add(patientRecord);
									listMap.put(facilityId, patientRecList);
								}
							}
						}
					}
				}//for
			}//if
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL repositoryDAOFactory").getMessage());
		}
		return listMap;
	}
	
	
	
	PatientRecord repositoryResultDtoToPatientRecord(RepositoryResultDto dto){
		PatientRecord patientRecord = null;
		if(dto != null){
			patientRecord = new PatientRecord();
			patientRecord.setMhsOrderingFacId(dto.getFacilityId());
			patientRecord.setCid(dto.getCid());
			patientRecord.setAccessionNo(dto.getAccessionNo());
			//patientRecord.setAltPatientId(dto.getMrn());
			patientRecord.setAltPatientId(dto.getPatientId());
			patientRecord.setPatientName(dto.getPatientName());
			patientRecord.setDob(dto.getDateOfBirth());
			patientRecord.setGender(dto.getGender());
			patientRecord.setPatientSsn(dto.getPatientSsn());
			patientRecord.setProviderId(dto.getNpi());
			patientRecord.setOrderingPhyName(dto.getOrderingPhysicianName());
			patientRecord.setReportNteComment(dto.getReportNotes());
			patientRecord.setSpecimenRecDateformat(dto.getSpecimenReceiveDate());
			patientRecord.setCollectionDateformat(dto.getCollectionDate());
			patientRecord.setCollectionTimeformat(dto.getCollectionTime());
			patientRecord.setDrawFreq(dto.getDrawFreq());
			patientRecord.setResRptStatusChngDtTimeformat(dto.getResRprtStatusChngDtTime());
			patientRecord.setRequisitionStatus(dto.getOrderStatus());
			patientRecord.setCompoundTestCode(dto.getCompoundTestCode());
			patientRecord.setSubTestCode(dto.getTestCode());
			patientRecord.setSpecimenSource(dto.getSpecimenSource());
			patientRecord.setOrderNumber(dto.getOrderNumber());
			
			patientRecord.setFacilityName(dto.getFacilityName());
			patientRecord.setFacilityAddress1(dto.getFacilityAddress1());
			patientRecord.setFacilityAddress2(dto.getFacilityAddress2());
			patientRecord.setFacilityCity(dto.getFacilityCity());
			patientRecord.setFacilityState(dto.getFacilityState());
			patientRecord.setFacilityZip(dto.getFacilityZip());
			patientRecord.setFacilityPhone(dto.getFacilityPhone());
			
			patientRecord.setPatientAddress1(dto.getPatientAccountAddress1());
			patientRecord.setPatientAddress2(dto.getPatientAccountAddress2());
			patientRecord.setPatientCity(dto.getPatientAccountCity());
			patientRecord.setPatientState(dto.getPatientAccountState());
			patientRecord.setPatientZip(dto.getPatientAccountZip());
			patientRecord.setPatientPhone(dto.getPatientHomePhone());
			patientRecord.setMrnId(dto.getMrn());
			
			patientRecord.setFacilityId(dto.getFacilityId());
		}
		return patientRecord;
	}
	
	OBXRecord repositoryResultDtoToOBXRecord(RepositoryResultDto dto){
		OBXRecord obxRecord = null;
		if(dto != null){
			obxRecord = new OBXRecord();
			obxRecord.setAccessionNo(dto.getAccessionNo());
			obxRecord.setCompoundTestCode(dto.getCompoundTestCode());
			obxRecord.setSubTestCode(dto.getTestCode());
			obxRecord.setSeqResultName(dto.getResultName());
			obxRecord.setSeqTestName(dto.getTestName());
			//obxRecord.setOrderStatus(dto.getResultStatus());
			obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setTextualNumResult(dto.getTextualNumericResult());
			obxRecord.setUnits(dto.getUnits());
			obxRecord.setRefRange(dto.getReferenceRange());
			obxRecord.setAbnormalFlag(dto.getAbnormalFlag());
			obxRecord.setRelDateFormat(dto.getReleaseDate());
			obxRecord.setRelTimeFormat(dto.getReleaseTime());
			obxRecord.setPerformingLabId(dto.getPerformingLabId());
			obxRecord.setOrderMethod(dto.getOrderMethod());
			obxRecord.setOrderNumber(dto.getOrderNumber());
			//obxRecord.setRrTestCode(dto.getNewHorizonCode());
			//obxRecord.setOldTestCode(dto.getTestCode());
			obxRecord.setSourceOfComment(dto.getSourceOfComment());
			obxRecord.setSensitivityFlag(dto.getAbnormalFlag());
			obxRecord.setAntibioticTextualNumResult(dto.getTextualNumericResult());
			//obxRecord.setResultStatus(dto.getResultStatus());
			obxRecord.setLoincCode(dto.getLoincCode());
			obxRecord.setLoincName(dto.getLoincName());
		}
		return obxRecord;
	}
	
	NTERecord repositoryResultDtoToNTERecord(RepositoryResultDto dto){
		NTERecord nteRecord = null;
		if(dto != null){
			nteRecord = new NTERecord();
			nteRecord.setNteCompTestCode(dto.getCompoundTestCode());
			nteRecord.setNteResultName(dto.getResultName());
			nteRecord.setTestNteComment(dto.getResultComments());
			nteRecord.setNteTestCode(dto.getTestCode());
		}
		return nteRecord;
	}
	
	//netlims
	
	public Integer insertShielPatient() throws BusinessException{
		Integer loadCount = null;
		if(this.repositoryDAOFactory != null){
			ResultRepositoryDao resultRepositoryDao = (ResultRepositoryDao)this.repositoryDAOFactory.getDAOImpl(ResultRepositoryDao.class.getSimpleName());
			NetlimsFileDao netlimsFileDao = (NetlimsFileDao)this.repositoryDAOFactory.getDAOImpl(NetlimsFileDao.class.getSimpleName());
			if((resultRepositoryDao != null) && (netlimsFileDao != null)){
				Timestamp maxFileUpdateTime = resultRepositoryDao.getMaxFileLastUpdateTime();
				Map<String, List<ShielPatient>> patListMap = netlimsFileDao.getShielPatientFromResources(maxFileUpdateTime);
				if(patListMap != null){
					List<ShielPatient> valPatList = patListMap.get("valPatList");
					List<ShielPatient> invalPatList = patListMap.get("invalPatList");
					if(valPatList != null){
						int cnt = 0;
						for(ShielPatient pat : valPatList){
							cnt += resultRepositoryDao.insertShielPatient(pat);
						}
						loadCount = new Integer(cnt);
					}
				}
			}
		}
		return loadCount;
	}
*/	
	
	
/*	
	@Override
	public void setEastProviderDto(ProviderDto providerDto) throws BusinessException {
		if(providerDto != null){
			if(this.providerDAOFactory != null){
				PlacEastProviderDao dao = (PlacEastProviderDao)this.providerDAOFactory.getDAOImpl(PlacEastProviderDao.class.getSimpleName());
				if(dao != null){
					dao.setProviderDto(providerDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL providerDto").getMessage());
		}
	}

	@Override
	public void updateEastProviderDto(ProviderDto providerDto) throws BusinessException {
		if(providerDto != null){
			if(this.providerDAOFactory != null){
				PlacEastProviderDao dao = (PlacEastProviderDao)this.providerDAOFactory.getDAOImpl(PlacEastProviderDao.class.getSimpleName());
				if(dao != null){
					dao.updateProviderDto(providerDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL providerDto").getMessage());
		}
	}

	@Override
	public List<ProviderDto> getAllEastNotSyncedWithCorp() throws BusinessException {
		List<ProviderDto> dtoList = null;
		if(this.providerDAOFactory != null){
			PlacEastProviderDao dao = (PlacEastProviderDao)this.providerDAOFactory.getDAOImpl(PlacEastProviderDao.class.getSimpleName());
			if(dao != null){
				//dtoList = dao.getAllNotSyncedWithCorp();
				dtoList = dao.callPhysicianDataHavingDateSyncedWithCorpNull();
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					//throw businessException;
					businessException.printStackTrace();
				}
			}
		}
		return dtoList;
	}
	
	public List<ProviderDto> getAllEastNotSyncedWithCorpNotCallable() throws BusinessException {
		List<ProviderDto> dtoList = null;
		if(this.providerDAOFactory != null){
			PlacEastProviderDao dao = (PlacEastProviderDao)this.providerDAOFactory.getDAOImpl(PlacEastProviderDao.class.getSimpleName());
			if(dao != null){
				dtoList = dao.getAllNotSyncedWithCorp();
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					//throw businessException;
					businessException.printStackTrace();
				}
			}
		}
		return dtoList;
	}

	public ProviderContainerDto getProviderDtoFromNpi(String npi) throws BusinessException{
		ProviderContainerDto dto = null;
		if(npi != null){
			if(this.providerDAOFactory != null){
				CorpProviderWsDao dao = (CorpProviderWsDao)this.providerDAOFactory.getDAOImpl(CorpProviderWsDao.class.getSimpleName());
				if(dao != null){
					dto = dao.getProviderDtoFromNpi(npi);
					if(dto == null){
						BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
						businessException.addParameter(dto);
						//throw businessException;
						businessException.printStackTrace();
					}
				}
			}
		}
		return dto;
	}
	
	@Override
	public ProviderContainerDto getProviderDtoFromNpi(List<String> npiList) throws BusinessException {
		ProviderContainerDto dto = null;
		if(npiList != null){
			if(this.providerDAOFactory != null){
				CorpProviderWsDao dao = (CorpProviderWsDao)this.providerDAOFactory.getDAOImpl(CorpProviderWsDao.class.getSimpleName());
				if(dao != null){
					dto = dao.getProviderDtoFromNpi(npiList);
					if(dto == null){
						BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
						businessException.addParameter(dto);
						//throw businessException;
						businessException.printStackTrace();
					}
				}
			}
		}
		return dto;
	}

	@Override
	public ProviderContainerDto getProviderDtoFromNpi(String[] npiArray) throws BusinessException {
		ProviderContainerDto dto = null;
		if(npiArray != null){
			if(this.providerDAOFactory != null){
				CorpProviderWsDao dao = (CorpProviderWsDao)this.providerDAOFactory.getDAOImpl(CorpProviderWsDao.class.getSimpleName());
				if(dao != null){
					dto = dao.getProviderDtoFromNpi(npiArray);
					if(dto == null){
						BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
						businessException.addParameter(dto);
						//throw businessException;
						businessException.printStackTrace();
					}
				}
			}
		}
		return dto;
	}

	public void updateEastNotSyncedWithCorp() throws BusinessException {
		if(this.providerDAOFactory != null){
			List<ProviderDto> dtoList = this.getAllEastNotSyncedWithCorp();
			if(dtoList != null){
				//int ctr = 0; // REMOVE TEST
				for(ProviderDto dto : dtoList){

					//// BEGIN REMOVE TEST
					//if(ctr >= 10){
					//	break;
					//}else{
					//	ctr++;
					//}
					//// END REMOVE TEST

					String npi = dto.getNpi();
					if(npi != null){
						ProviderContainerDto pcdto = this.getProviderDtoFromNpi(npi);
						log.debug("updateNotSyncedWithCorp(): pcdto: " + (pcdto == null ? "NULL" : pcdto.toString()));
						if(pcdto != null){
							List<ProviderDto> pdtoList = pcdto.getProviderList();
							if(pdtoList != null){
								for(ProviderDto pdto : pdtoList){
									this.updateEastProviderDto(pdto);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void updateEastNotSyncedWithCorpNotCallable() throws BusinessException {
		if(this.providerDAOFactory != null){
			List<ProviderDto> dtoList = this.getAllEastNotSyncedWithCorpNotCallable();
			if(dtoList != null){
				//int ctr = 0; // REMOVE TEST
				for(ProviderDto dto : dtoList){

					//// BEGIN REMOVE TEST
					//if(ctr >= 10){
					//	break;
					//}else{
					//	ctr++;
					//}
					//// END REMOVE TEST

					String npi = dto.getNpi();
					if(npi != null){
						ProviderContainerDto pcdto = this.getProviderDtoFromNpi(npi);
						log.debug("updateNotSyncedWithCorp(): pcdto: " + (pcdto == null ? "NULL" : pcdto.toString()));
						if(pcdto != null){
							List<ProviderDto> pdtoList = pcdto.getProviderList();
							if(pdtoList != null){
								for(ProviderDto pdto : pdtoList){
									this.updateEastProviderDto(pdto);
								}
							}
						}
					}
				}
			}
		}
	}	
	
	@Override
	public void setWestProviderDto(ProviderDto providerDto) throws BusinessException {
		if(providerDto != null){
			if(this.providerDAOFactory != null){
				PlacWestProviderDao dao = (PlacWestProviderDao)this.providerDAOFactory.getDAOImpl(PlacWestProviderDao.class.getSimpleName());
				if(dao != null){
					dao.setProviderDto(providerDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL providerDto").getMessage());
		}
	}

	@Override
	public void updateWestProviderDto(ProviderDto providerDto) throws BusinessException {
		if(providerDto != null){
			if(this.providerDAOFactory != null){
				PlacWestProviderDao dao = (PlacWestProviderDao)this.providerDAOFactory.getDAOImpl(PlacWestProviderDao.class.getSimpleName());
				if(dao != null){
					dao.updateProviderDto(providerDto);
				}
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("NULL providerDto").getMessage());
		}
	}

	@Override
	public List<ProviderDto> getAllWestNotSyncedWithCorp() throws BusinessException {
		List<ProviderDto> dtoList = null;
		if(this.providerDAOFactory != null){
			PlacWestProviderDao dao = (PlacWestProviderDao)this.providerDAOFactory.getDAOImpl(PlacWestProviderDao.class.getSimpleName());
			if(dao != null){
				//dtoList = dao.getAllNotSyncedWithCorp();
				dtoList = dao.callPhysicianDataHavingDateSyncedWithCorpNull();
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					//throw businessException;
					businessException.printStackTrace();
				}
			}
		}
		return dtoList;
	}
	
	public List<ProviderDto> getAllWestNotSyncedWithCorpNotCallable() throws BusinessException {
		List<ProviderDto> dtoList = null;
		if(this.providerDAOFactory != null){
			PlacWestProviderDao dao = (PlacWestProviderDao)this.providerDAOFactory.getDAOImpl(PlacWestProviderDao.class.getSimpleName());
			if(dao != null){
				dtoList = dao.getAllNotSyncedWithCorp();
				if(dtoList == null){
					BusinessException businessException = new BusinessException(ExceptionConstants.NO_MSG_FOUND);
					businessException.addParameter(dtoList);
					//throw businessException;
					businessException.printStackTrace();
				}
			}
		}
		return dtoList;
	}
	
	public void updateWestNotSyncedWithCorp() throws BusinessException {
		if(this.providerDAOFactory != null){
			List<ProviderDto> dtoList = this.getAllWestNotSyncedWithCorp();
			if(dtoList != null){
				//int ctr = 0; // REMOVE TEST
				for(ProviderDto dto : dtoList){

					//// BEGIN REMOVE TEST
					//if(ctr >= 10){
					//	break;
					//}else{
					//	ctr++;
					//}
					//// END REMOVE TEST

					String npi = dto.getNpi();
					if(npi != null){
						ProviderContainerDto pcdto = this.getProviderDtoFromNpi(npi);
						log.debug("updateNotSyncedWithCorp(): pcdto: " + (pcdto == null ? "NULL" : pcdto.toString()));
						if(pcdto != null){
							List<ProviderDto> pdtoList = pcdto.getProviderList();
							if(pdtoList != null){
								for(ProviderDto pdto : pdtoList){
									this.updateWestProviderDto(pdto);
								}
							}
						}
					}
				}
			}
		}
	}	
	
	public void updateWestNotSyncedWithCorpNotCallable() throws BusinessException {
		if(this.providerDAOFactory != null){
			List<ProviderDto> dtoList = this.getAllWestNotSyncedWithCorpNotCallable();
			if(dtoList != null){
				//int ctr = 0; // REMOVE TEST
				for(ProviderDto dto : dtoList){

					//// BEGIN REMOVE TEST
					//if(ctr >= 10){
					//	break;
					//}else{
					//	ctr++;
					//}
					//// END REMOVE TEST

					String npi = dto.getNpi();
					if(npi != null){
						ProviderContainerDto pcdto = this.getProviderDtoFromNpi(npi);
						log.debug("updateNotSyncedWithCorp(): pcdto: " + (pcdto == null ? "NULL" : pcdto.toString()));
						if(pcdto != null){
							List<ProviderDto> pdtoList = pcdto.getProviderList();
							if(pdtoList != null){
								for(ProviderDto pdto : pdtoList){
									this.updateWestProviderDto(pdto);
								}
							}
						}
					}
				}
			}
		}
	}
*/	
}
