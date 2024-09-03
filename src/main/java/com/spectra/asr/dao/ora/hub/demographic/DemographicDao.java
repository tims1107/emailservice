package com.spectra.asr.dao.ora.hub.demographic;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.demographic.AsrActivityNoDemoDto;
import com.spectra.asr.dto.demographic.AsrPhysicianDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;

public interface DemographicDao {
	void callProcActivityNoDemo();
	List<AsrActivityNoDemoDto> getNoDemo(Map<String, Object> paramMap);
	List<AsrActivityNoDemoDto> getNoDemoPrevDay(String hasDemo);
	
	List<AsrPhysicianDto> getLabOrderPhysician(String requisitionId);
	
	List<StateResultDto> getIntraLabsNoDemo(Map<String, Object> paramMap);
	int updateIntraLabsNoDemo(StateResultDto stateResultDto);
	int insertIntraLabsNoDemo(StateResultDto stateResultDto);
	
	List<LovTestCategoryDto> getLovTestCategory(Map<String, Object> paramMap);
	
	List<LabDto> getLab(Map<String, Object> paramMap);
}
