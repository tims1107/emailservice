package com.spectra.asr.businessobject.abnormal;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
import com.spectra.scorpion.framework.exception.BusinessException;

//import com.spectra.cm.db.dto.ami.label.AmiLabelDto;
//import com.spectra.scorpion.framework.exception.BusinessException;

public interface AbnormalBo {
	List<StateResultDto> getStateAbnormalResult(ResultExtractDto resultExtractDto) throws BusinessException;
	Map<String, List<ConditionMasterDto>> getConditionMasterMap(String state) throws BusinessException;
	List<StateResultDto> getAbnormalTestResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	/*
	AmiLabelDto getEastAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException;
	void setEastAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException;
	void updateEastAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException;
	AmiLabelDto getWestAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException;
	void setWestAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException;
	void updateWestAmiLabel(AmiLabelDto amiLabelDto) throws BusinessException;
	
	void truncateEastAmiLabel() throws BusinessException;
	void truncateWestAmiLabel() throws BusinessException;
	
	void deleteEastAmiLabel() throws BusinessException;
	void deleteWestAmiLabel() throws BusinessException;
	*/
}
