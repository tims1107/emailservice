package com.spectra.asr.mapper.mybatis.ora.hub.condition;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;

public interface ConditionMapper {
	List<String> selectDistinctOTCByEntity(Map<String, Object> paramMap);
	
	List<ConditionMasterDto> selectConditionMaster(Map<String, Object> paramMap);
	int insertConditionMaster(ConditionMasterDto conditionMasterDto);
	int updateConditionMaster(ConditionMasterDto conditionMasterDto);
	List<ConditionMasterDto> selectConditionFromConditionMaster(Map<String, Object> paramMap);
	ConditionMasterDto selectLastConditionMaster();
	
	ConditionFilterDto selectLastConditionFilter();
	List<ConditionFilterDto> selectConditionFilters(Map<String, Object> paramMap);
	int insertConditionFilter(ConditionFilterDto conditionFilterDto);
	int updateConditionFilter(ConditionFilterDto conditionFilterDto);
}
