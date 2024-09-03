package com.spectra.asr.service.audit;

import java.util.List;

import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface AsrAuditService {
	List<AsrAuditDto> getAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException;
	int updateAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException;
	int insertAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException;
}
