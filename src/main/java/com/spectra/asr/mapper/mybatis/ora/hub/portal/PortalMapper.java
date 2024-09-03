package com.spectra.asr.mapper.mybatis.ora.hub.portal;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.portal.EntityResultCountDto;

public interface PortalMapper {
	List<EntityResultCountDto> selectEntityResultCount(Map<String, Object> paramMap);
	List<String> selectEntityByType(String entityType);
	
	void truncateTmpPrevResults();
	int insertIntoTmpPrevResults(Map<String, Object> paramMap);
}
