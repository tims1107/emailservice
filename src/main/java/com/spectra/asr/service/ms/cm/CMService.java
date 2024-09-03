package com.spectra.asr.service.ms.cm;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.cm.CMFacilityDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface CMService {
	List<CMFacilityDto> getCmFacilities(Map<String, Object> paramMap) throws BusinessException;
}
