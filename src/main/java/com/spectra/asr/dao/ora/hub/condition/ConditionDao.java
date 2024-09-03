package com.spectra.asr.dao.ora.hub.condition;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;

public interface ConditionDao {
	List<String> getDistinctOTCByEntity(Map<String, Object> paramMap);
	
	List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap);
	List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap);
	int insertConditionMaster(ConditionMasterDto conditionMasterDto);
	int updateConditionMaster(ConditionMasterDto conditionMasterDto);
	ConditionMasterDto getLastConditionMaster();
	
	ConditionFilterDto getLastConditionFilter();
	List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap);
	int insertConditionFilter(ConditionFilterDto conditionFilterDto);
	int updateConditionFilter(ConditionFilterDto conditionFilterDto);
}
