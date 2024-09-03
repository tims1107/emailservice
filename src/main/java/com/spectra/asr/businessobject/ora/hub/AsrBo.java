package com.spectra.asr.businessobject.ora.hub;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface AsrBo {
	List<StateMasterDto> getStateMaster(StateMasterDto stateMasterDto) throws BusinessException;
	int insertStateMaster(StateMasterDto stateMasterDto) throws BusinessException;
	int updateStateMaster(StateMasterDto stateMasterDto) throws BusinessException;
	StateMasterDto getLastStateMaster() throws BusinessException;

	void callProcTrackingInsert(String state) throws BusinessException;
	void callProcTrackingUpdate(String state) throws BusinessException;

	Integer callEipResultsExtract(Map<String, Object> paramMap) throws BusinessException;

	List<StateResultDto> callAsrMicroIncludeResults(ResultExtractDto resultExtractDto) throws BusinessException;
}
