package com.spectra.asr.dao.ora.hub.portal;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.portal.EntityResultCountDto;

public interface PortalDao {
	List<EntityResultCountDto> getEntityResultCount(Map<String, Object> paramMap);
	List<String> getEntityByType(String entityType);
	
	void truncateTmpPrevResults();
	int insertIntoTmpPrevResults(Map<String, Object> paramMap);
}
