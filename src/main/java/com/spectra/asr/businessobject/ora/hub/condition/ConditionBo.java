package com.spectra.asr.businessobject.ora.hub.condition;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface ConditionBo {
	List<String> getDistinctOTCByEntity(Map<String, Object> paramMap) throws BusinessException;
	
	List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap) throws BusinessException;
	List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap) throws BusinessException;
	int insertConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException;
	int updateConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException;
	ConditionMasterDto getLastConditionMaster() throws BusinessException;

	ConditionFilterDto getLastConditionFilter() throws BusinessException;
	List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap) throws BusinessException;
	int insertConditionFilter(ConditionFilterDto conditionFilterDto) throws BusinessException;
	int updateConditionFilter(ConditionFilterDto conditionFilterDto) throws BusinessException;
}
