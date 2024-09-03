package com.spectra.result.transporter.dao.ora.rr;

import com.spectra.demographic.loader.dto.state.StateZipCodeDto;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.PositivityFilterDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;

import java.util.List;
import java.util.Map;

public interface ResultRepositoryDao {
	void callStateResultsStoredProc(Map paramMap);
	void callStateEipResultsStoredProc(Map paramMap);
	List<PositivityFilterDto> getPositivityFilters(String filterKey);
	List<RepositoryResultDto> getDailyTestResults(Map paramMap);
	List<TestPropertiesDto> getTestPropertiesByCtc(Map paramMap);	
	List<RepositoryResultDto> getMugsiResults(Map paramMap);
	List<RepositoryResultDto> getMrsaResults(Map paramMap);
	List<RepositoryResultDto> getCdiffResults(Map paramMap);
	List<RepositoryResultDto> getMssaResults(Map paramMap);
	List<RepositoryResultDto> getCreResults(Map paramMap);
	List<com.spectra.demographic.loader.dto.state.StateZipCodeDto> getStateZipCodeByZip(String zip);
	StateZipCodeDto getStateDemoByZipCityState(Map paramMap);
	int insertRsResultsProcessed(RepositoryResultDto repositoryResultDto);
	int insertRsEipResultsProcessed(RepositoryResultDto repositoryResultDto);
	int updateRsResultsExtract(RepositoryResultDto repositoryResultDto);
	
	List<StatePropertiesDto> getStateProperties(StatePropertiesDto statePropertiesDto);
	int updateRsStateProperties(StatePropertiesDto statePropertiesDto);
	int insertRsStateProperties(StatePropertiesDto statePropertiesDto);
	
	List<String> getNonElrStateProperties(Map paramMap);
/*	
	void callResultsStoredProc();
	Integer getLatestLoadCount();
	List<String> getResultRequisitions();
	List<String> getFacilityIds();
	List<RepositoryResultDto> getResultWithoutDemographic();
	List<RepositoryResultDto> getResultByRequisition(String reqNo);
	List<RepositoryResultDto> getResultByFacilityId(String facilityId);
	
	//netlims
	Timestamp getMaxFileLastUpdateTime();
	int insertShielPatient(ShielPatient shielPatient);
*/	
}
