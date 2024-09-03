package com.spectra.asr.dao.ora.hub.audit;

import java.util.List;

import com.spectra.asr.dto.audit.AsrAuditDto;

public interface AsrAuditDao {
	List<AsrAuditDto> getAsrAudit(AsrAuditDto asrAuditDto);
	int updateAsrAudit(AsrAuditDto asrAuditDto);
	int insertAsrAudit(AsrAuditDto asrAuditDto);
}
