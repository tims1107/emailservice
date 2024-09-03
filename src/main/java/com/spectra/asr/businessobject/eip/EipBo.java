package com.spectra.asr.businessobject.eip;

import java.util.List;

import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.ResultExtractDto;
//import com.spectra.cm.db.dto.ami.label.AmiLabelDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface EipBo {
	List<StateResultDto> getCdiffResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	List<StateResultDto> getMrsaResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	List<StateResultDto> getMssaResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	List<StateResultDto> getMugsiResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	List<StateResultDto> getEipTestResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	List<StateResultDto> getEipResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
	List<StateResultDto> getAbcResults(ResultExtractDto resultExtractDto) throws BusinessException;
	
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
