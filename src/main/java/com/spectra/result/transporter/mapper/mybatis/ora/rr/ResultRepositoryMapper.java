package com.spectra.result.transporter.mapper.mybatis.ora.rr;

import com.spectra.demographic.loader.dto.state.StateZipCodeDto;
import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
import com.spectra.result.transporter.dto.rr.state.StatePropertiesDto;
import com.spectra.result.transporter.dto.rr.test.PositivityFilterDto;
import com.spectra.result.transporter.dto.rr.test.TestPropertiesDto;

import java.util.List;
import java.util.Map;

public interface ResultRepositoryMapper {
	void callStateResultsStoredProc(Map paramMap);
	void callStateEipResultsStoredProc(Map paramMap);
	List<PositivityFilterDto> selectPositivityFilters(String filterKey);
	List<RepositoryResultDto> selectDailyTestResults(Map paramMap);
	List<TestPropertiesDto> selectTestPropertiesByCtc(Map paramMap);
	List<RepositoryResultDto> selectMugsiResults(Map paramMap);
	List<RepositoryResultDto> selectMrsaResults(Map paramMap);
	List<RepositoryResultDto> selectCdiffResults(Map paramMap);
	List<RepositoryResultDto> selectMssaResults(Map paramMap);
	List<RepositoryResultDto> selectCreResults(Map paramMap);
	List<StateZipCodeDto> selectStateZipCodeByZip(String zip);
	List<StateZipCodeDto> selectStateDemoByZipCityState(Map paramMap);
	int insertRsResultsProcessed(RepositoryResultDto repositoryResultDto);
	int insertRsEipResultsProcessed(RepositoryResultDto repositoryResultDto);
	int updateRsResultsExtract(RepositoryResultDto repositoryResultDto);
	
	List<StatePropertiesDto> selectStateProperties(StatePropertiesDto statePropertiesDto);
	int updateRsStateProperties(StatePropertiesDto statePropertiesDto);
	int insertRsStateProperties(StatePropertiesDto statePropertiesDto);
	
	List<String> selectNonElrStateProperties(Map paramMap);
	
/*	
	void callResultsStoredProc();
	Integer selectLatestLoadCount();
	List<String> selectResultRequisitions();
	List<String> selectFacilityIds();
	List<RepositoryResultDto> selectResultWithoutDemographic();
	List<RepositoryResultDto> selectResultByRequisition(String reqNo);
	List<RepositoryResultDto> selectResultByFacilityId(String facilityId);
	
	//netlims
	Timestamp selectMaxFileLastUpdateTime();
	int insertShielPatient(ShielPatient shielPatient);
*/	
}
