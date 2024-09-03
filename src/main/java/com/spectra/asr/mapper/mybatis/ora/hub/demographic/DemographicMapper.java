package com.spectra.asr.mapper.mybatis.ora.hub.demographic;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.demographic.AsrActivityNoDemoDto;
import com.spectra.asr.dto.demographic.AsrPhysicianDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;

public interface DemographicMapper {
	void callProcActivityNoDemo();
	List<AsrActivityNoDemoDto> selectNoDemo(Map<String, Object> paramMap);
	List<AsrActivityNoDemoDto> selectNoDemoPrevDay(String hasDemo);
	
	List<AsrPhysicianDto> selectLabOrderPhysician(String requisitionId);
	
	List<StateResultDto> selectIntraLabsNoDemo(Map<String, Object> paramMap);
	int updateIntraLabsNoDemo(StateResultDto stateResultDto);
	int insertIntraLabsNoDemo(StateResultDto stateResultDto);
	
	List<LovTestCategoryDto> selectLovTestCategory(Map<String, Object> paramMap);
	
	List<LabDto> selectLab(Map<String, Object> paramMap);
}
