package com.spectra.asr.mapper.mybatis.ora.hub.condition;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.micro.MicroConsolidatedDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;

public interface MicroConditionMapper {
	List<MicroConsolidatedDto> selectConsolidatedList(Map<String, Object> paramMap);
	int insertMicroConditionMaster(ConditionMasterDto microConditionMasterDto);
	ConditionMasterDto selectLastMicroConditionMaster();
	
	List<ConditionMasterDto> selectMicroConditionMaster(Map<String, Object> paramMap);
	int updateMicroConditionMaster(ConditionMasterDto conditionMasterDto);

}
