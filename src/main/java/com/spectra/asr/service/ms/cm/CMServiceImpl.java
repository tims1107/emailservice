package com.spectra.asr.service.ms.cm;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ms.cm.CMBo;
import com.spectra.asr.dto.cm.CMFacilityDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class CMServiceImpl implements CMService {
	//private Logger log = Logger.getLogger(CMServiceImpl.class);
	
	@Override
	public List<CMFacilityDto> getCmFacilities(Map<String, Object> paramMap) throws BusinessException {
		List<CMFacilityDto> dtoList = null;
		CMBo cmBo = AsrBoFactory.getCMBo();
		if(cmBo != null){
			dtoList = cmBo.getCmFacilities(paramMap);
		}
		return dtoList;
	}

}
