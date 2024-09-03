package com.spectra.asr.service.entity;

import java.util.List;

import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface EntityService {
	List<StateMasterDto> getStateMaster(StateMasterDto stateMasterDto) throws BusinessException;
	int insertStateMaster(StateMasterDto stateMasterDto) throws BusinessException;
	int updateStateMaster(StateMasterDto stateMasterDto) throws BusinessException;
}
