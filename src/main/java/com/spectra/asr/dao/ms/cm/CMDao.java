package com.spectra.asr.dao.ms.cm;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.cm.CMFacilityDto;

public interface CMDao {
	List<CMFacilityDto> getCmFacilities(Map<String, Object> paramMap);
}
