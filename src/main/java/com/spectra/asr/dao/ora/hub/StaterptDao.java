package com.spectra.asr.dao.ora.hub;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;

//import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
//import com.spectra.result.transporter.dto.shiel.ShielPatient;

public interface StaterptDao {
	List<StateResultDto> callStateResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> getCdiffResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> getMrsaResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> getMssaResults(ResultExtractDto resultExtractDto);
	
	List<StateResultDto> getMugsiResults(ResultExtractDto resultExtractDto);
	
	//void callStateResultsStoredProc(Map paramMap);
/*	
	void callResultsStoredProc();
	Integer getLatestLoadCount();
	List<String> getResultRequisitions();
	List<String> getFacilityIds();
	List<RepositoryResultDto> getResultWithoutDemographic();
	List<RepositoryResultDto> getResultByRequisition(String reqNo);
	List<RepositoryResultDto> getResultByFacilityId(String facilityId);
	
	//netlims
	Timestamp getMaxFileLastUpdateTime();
	int insertShielPatient(ShielPatient shielPatient);
*/	
}
