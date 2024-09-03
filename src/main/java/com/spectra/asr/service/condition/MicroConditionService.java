package com.spectra.asr.service.condition;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.micro.MicroConsolidatedDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface MicroConditionService {
	List<MicroConsolidatedDto> getConsolidatedList(Map<String, Object> paramMap) throws BusinessException;
	List<ConditionMasterDto> getMicroConditionMaster(StateMasterDto stateMasterDto) throws BusinessException;
	int insertMicroConditionMaster(ConditionMasterDto microConditionMasterDto) throws BusinessException;
	int insertMicroConditionMasterList(List<ConditionMasterDto> microConditionMasterDtoList) throws BusinessException;
	ConditionMasterDto getLastMicroConditionMaster() throws BusinessException;
	
	List<ConditionMasterDto> getMicroConditionMaster(Map<String, Object> paramMap) throws BusinessException;
	int updateMicroConditionMaster(ConditionMasterDto conditionMasterDto) throws BusinessException;
}
