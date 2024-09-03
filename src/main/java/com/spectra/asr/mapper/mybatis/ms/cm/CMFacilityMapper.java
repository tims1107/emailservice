package com.spectra.asr.mapper.mybatis.ms.cm;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.cm.CMFacilityDto;

public interface CMFacilityMapper {
	List<CMFacilityDto> selectCmFacilities(Map<String, Object> paramMap);
}
