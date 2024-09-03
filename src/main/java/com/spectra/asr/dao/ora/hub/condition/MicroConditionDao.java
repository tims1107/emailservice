package com.spectra.asr.dao.ora.hub.condition;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.micro.MicroConsolidatedDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;

public interface MicroConditionDao {
	List<MicroConsolidatedDto> getConsolidatedList(Map<String, Object> paramMap);
	int insertMicroConditionMaster(ConditionMasterDto microConditionMasterDto);
	ConditionMasterDto getLastMicroConditionMaster();
	
	List<ConditionMasterDto> getMicroConditionMaster(Map<String, Object> paramMap);
	int updateMicroConditionMaster(ConditionMasterDto conditionMasterDto);
}
