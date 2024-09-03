package com.spectra.asr.dao.ora.hub;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.asr.dto.state.zip.StateZipCodeDto;

public interface AsrDao extends StaterptDao {
	List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap);
	List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap);
	List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap);
	int insertConditionMaster(ConditionMasterDto conditionMasterDto);
	int updateConditionMaster(ConditionMasterDto conditionMasterDto);
	
	List<StateZipCodeDto> getStateZipCodeByZip(String zip);
	StateZipCodeDto getStateDemoByZipCityState(Map<String, Object> paramMap);
	List<StateZipCodeDto> getStateStateAbbrev();
	
	int insertStateMaster(StateMasterDto stateMasterDto);
	int updateStateMaster(StateMasterDto stateMasterDto);
	List<StateMasterDto> getStateMaster(StateMasterDto stateMasterDto);
	
	List<StateResultDto> callStateMicroResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> callStateAbnMicroResults(ResultExtractDto resultExtractDto);
	
	StateMasterDto getLastStateMaster();
	
	void callProcTrackingInsert(String state);
	void callProcTrackingUpdate(String state);
	
	void callProcTrackResults(String state);

	List<StateResultDto> getAbcResults(ResultExtractDto resultExtractDto);
	
	Integer callEipResultsExtract(Map<String, Object> paramMap);

	List<StateResultDto> callAsrMicroIncludeResults(ResultExtractDto resultExtractDto);
}
