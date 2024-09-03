package com.spectra.asr.service.notification;

import java.util.List;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface AbnormalResultsNotificationService {
	boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto) throws BusinessException;
	boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto, String eastWestFlag) throws BusinessException;
	boolean notify(List<StateResultDto> dtoList, GeneratorDto gDto, String eastWestFlag, String subject, String message) throws BusinessException;
}
