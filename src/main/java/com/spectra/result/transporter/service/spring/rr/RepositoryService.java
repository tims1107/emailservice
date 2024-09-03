package com.spectra.result.transporter.service.spring.rr;

import java.util.List;
import java.util.Map;

import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBoFactory;
import com.spectra.result.transporter.dto.hl7.state.PatientRecord;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface RepositoryService {
	void setBoFactory(RepositoryBoFactory repositoryBoFactory);

	List<RepositoryResultDto> callStateResultsStoredProc(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> callStateEipResultsStoredProc(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getStateDailyTestResults(Map paramMap) throws BusinessException;
	List<RepositoryResultDto> getDailyTestResults(Map paramMap) throws BusinessException;
	List<TestPropertiesDto> getTestPropertiesByCtc(Map paramMap) throws BusinessException;
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
*/	
}
