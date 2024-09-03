package com.spectra.asr.service.notification;

import java.util.List;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface NoDemoNotificationService {
	boolean notifyByFile(List<StateResultDto> dtoList, GeneratorDto gDto) throws BusinessException;
}
