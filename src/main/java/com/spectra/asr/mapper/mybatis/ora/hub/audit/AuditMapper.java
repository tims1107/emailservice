package com.spectra.asr.mapper.mybatis.ora.hub.audit;

import java.util.List;

import com.spectra.asr.dto.audit.AsrAuditDto;

public interface AuditMapper {
	List<AsrAuditDto> selectAsrAudit(AsrAuditDto asrAuditDto);
	int updateAsrAudit(AsrAuditDto asrAuditDto);
	int insertAsrAudit(AsrAuditDto asrAuditDto);
}
