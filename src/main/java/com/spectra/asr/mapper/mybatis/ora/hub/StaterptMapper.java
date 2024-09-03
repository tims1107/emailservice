package com.spectra.asr.mapper.mybatis.ora.hub;

import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;

//import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
//import com.spectra.result.transporter.dto.shiel.ShielPatient;

import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.dto.state.zip.StateZipCodeDto;


public interface StaterptMapper {
	List<StateResultDto> callAsrMicroIncludeResults(Map<String, Object> paramMap);
	
	void callEipResultsExtract(Map<String, Object> paramMap);
	
	void callProcTrackResults(Map<String, Object> paramMap);
	
	void callProcTrackingInsert(String state);
	void callProcTrackingUpdate(String state);
	
	//List<StateResultDto> callStateResults(ResultExtractDto resultExtractDto);
	List<StateResultDto> callStateResults(Map<String, Object> paramMap);
	
	List<StateResultDto> callStateMicroResults(Map<String, Object> paramMap);
	
	List<StateResultDto> callStateAbnMicroResults(Map<String, Object> paramMap);
	
	List<StateResultDto> selectCdiffResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> selectMrsaResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> selectMssaResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> selectMugsiResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> selectAbcResults(ResultExtractDto resultExtractDto);
/*	
	List<ConditionMasterDto> selectConditionMaster(Map<String, Object> paramMap);
	int insertConditionMaster(ConditionMasterDto conditionMasterDto);
	int updateConditionMaster(ConditionMasterDto conditionMasterDto);
	
	List<ConditionFilterDto> selectConditionFilters(Map<String, Object> paramMap);
	
	List<ConditionMasterDto> selectConditionFromConditionMaster(Map<String, Object> paramMap);
*/	
	List<StateZipCodeDto> selectStateZipCodeByZip(String zip);
	List<StateZipCodeDto> selectStateDemoByZipCityState(Map<String, Object> paramMap);
	List<StateZipCodeDto> selectStateStateAbbrev();
	
	int insertStateMaster(StateMasterDto stateMasterDto);
	int updateStateMaster(StateMasterDto stateMasterDto);
	List<StateMasterDto> selectStateMaster(StateMasterDto stateMasterDto);
	StateMasterDto selectLastStateMaster();

	
	//void callStateResultsStoredProc(Map paramMap);
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
