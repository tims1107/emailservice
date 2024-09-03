package com.spectra.asr.service.demographic;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.demographic.AsrActivityNoDemoDto;
import com.spectra.asr.dto.demographic.AsrPhysicianDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface AsrDemographicService {
	void callProcActivityNoDemo() throws BusinessException;
	List<AsrActivityNoDemoDto> getNoDemo(Map<String, Object> paramMap) throws BusinessException;
	List<AsrActivityNoDemoDto> getNoDemoPrevDay(String hasDemo) throws BusinessException;
	boolean notifyNoDemoPrevDay() throws BusinessException;
	
	List<AsrPhysicianDto> getLabOrderPhysician(String requisitionId) throws BusinessException;
	boolean notifyNoPhysicianDemo(List<String> requisitionIds) throws BusinessException;
	boolean checkResultListForPhysician(List<StateResultDto> dtoList) throws BusinessException;
	
	List<StateResultDto> getIntraLabsNoDemo(Map<String, Object> paramMap) throws BusinessException;
	int updateIntraLabsNoDemo(StateResultDto stateResultDto) throws BusinessException;
	int insertIntraLabsNoDemo(StateResultDto stateResultDto) throws BusinessException;
	
	List<LovTestCategoryDto> getLovTestCategory(Map<String, Object> paramMap) throws BusinessException;
	
	List<LabDto> getLab(Map<String, Object> paramMap) throws BusinessException;
}
