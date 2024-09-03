package com.spectra.result.transporter.businessobject.spring.ora.rr;

import java.util.List;
import java.util.Map;

import com.spectra.scorpion.framework.exception.BusinessException;

import com.spectra.result.transporter.dataaccess.spring.ora.rr.RepositoryDAOFactory;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.PositivityFilterDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;

public interface RepositoryBo {
	void setDaoFactory(RepositoryDAOFactory repositoryDAOFactory);
	
	List<RepositoryResultDto> callStateResultsStoredProc(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> callStateEipResultsStoredProc(Map paramMap) throws BusinessException;
	List<PositivityFilterDto> getPositivityFilters(String filterKey) throws BusinessException;
	List<RepositoryResultDto> getDailyTestResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getStateDailyTestResults(Map paramMap) throws BusinessException;
	List<TestPropertiesDto> getTestPropertiesByCtc(Map paramMap) throws BusinessException;
	Map<String, List<TestPropertiesDto>> getTestPropertiesMap(Map paramMap) throws BusinessException;
	Map<String, List<TestPropertiesDto>> getTestPropertiesMap(List<TestPropertiesDto> dtoList) throws BusinessException;
	List<RepositoryResultDto> getMugsiResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getMrsaResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getCdiffResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getMssaResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getCreResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getStateResults(String procType, String state) throws BusinessException;
	Map<String, List<RepositoryResultDto>> getStateResultsByCounty(String procType, String state) throws BusinessException;
	
	List<RepositoryResultDto> getStateResults(String procType, String state, String ewFlag) throws BusinessException;
	Map<String, List<RepositoryResultDto>> getStateResultsByCounty(String procType, String state, String ewFlag) throws BusinessException;
	
	int insertRsResultsProcessed(RepositoryResultDto repositoryResultDto) throws BusinessException;
	int insertRsEipResultsProcessed(RepositoryResultDto repositoryResultDto) throws BusinessException;
	int updateRsResultsExtract(RepositoryResultDto repositoryResultDto) throws BusinessException;
	
	Map<String, List<RepositoryResultDto>> getStateResultsFromDtoList(List<RepositoryResultDto> dtoList) throws BusinessException;
	Map<String, List<RepositoryResultDto>> getStateResultsFromDtoListByCounty(List<RepositoryResultDto> dtoList) throws BusinessException;
	
	void setCountyForStateResults(List<RepositoryResultDto> dtoList) throws BusinessException;
	
	List<StatePropertiesDto> getStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException;
	int updateRsStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException;
	int insertRsStateProperties(StatePropertiesDto statePropertiesDto) throws BusinessException;
	
	List<String> getNonElrStateProperties(Map paramMap) throws BusinessException;
/*
	void callResultsStoredProc() throws BusinessException;
	Integer getLatestLoadCount() throws BusinessException;
	List<String> getResultRequisitions() throws BusinessException;
	List<String> getFacilityIds() throws BusinessException;
	List<RepositoryResultDto> getResultWithoutDemographic() throws BusinessException;
	List<RepositoryResultDto> getResultByRequisition(String reqNo) throws BusinessException;
	List<RepositoryResultDto> getResultByFacilityId(String facilityId) throws BusinessException;
	
	Map<String, List<PatientRecord>> getListMap() throws BusinessException;
	Map<String, List<PatientRecord>> getFacilityPatientRecordListMap() throws BusinessException;
	
	//netlims
	Integer insertShielPatient() throws BusinessException;
*/	
	/*
	//east
	void setEastProviderDto(ProviderDto providerDto) throws BusinessException;
	void updateEastProviderDto(ProviderDto providerDto) throws BusinessException;
	List<ProviderDto> getAllEastNotSyncedWithCorp() throws BusinessException;
	List<ProviderDto> getAllEastNotSyncedWithCorpNotCallable() throws BusinessException;
	ProviderContainerDto getProviderDtoFromNpi(String npi) throws BusinessException;
	ProviderContainerDto getProviderDtoFromNpi(List<String> npiList) throws BusinessException;
	ProviderContainerDto getProviderDtoFromNpi(String[] npiArray) throws BusinessException;
	void updateEastNotSyncedWithCorp() throws BusinessException;
	void updateEastNotSyncedWithCorpNotCallable() throws BusinessException;
	
	//west
	void setWestProviderDto(ProviderDto providerDto) throws BusinessException;
	void updateWestProviderDto(ProviderDto providerDto) throws BusinessException;
	List<ProviderDto> getAllWestNotSyncedWithCorp() throws BusinessException;
	List<ProviderDto> getAllWestNotSyncedWithCorpNotCallable() throws BusinessException;
	//ProviderContainerDto getProviderDtoFromNpi(String npi) throws BusinessException;
	//ProviderContainerDto getProviderDtoFromNpi(List<String> npiList) throws BusinessException;
	//ProviderContainerDto getProviderDtoFromNpi(String[] npiArray) throws BusinessException;
	void updateWestNotSyncedWithCorp() throws BusinessException;
	void updateWestNotSyncedWithCorpNotCallable() throws BusinessException;
	*/
}
