package com.spectra.asr.businessobject.ora.hub.audit;

import java.util.List;

import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface AsrAuditBo {
	List<AsrAuditDto> getAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException;
	int updateAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException;
	int insertAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException;
}
