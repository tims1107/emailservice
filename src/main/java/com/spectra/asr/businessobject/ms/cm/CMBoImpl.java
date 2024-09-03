package com.spectra.asr.businessobject.ms.cm;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ms.cm.CMDao;
import com.spectra.asr.dto.cm.CMFacilityDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class CMBoImpl implements CMBo {
	//private Logger log = Logger.getLogger(CMBoImpl.class);
	
	@Override
	public List<CMFacilityDto> getCmFacilities(Map<String, Object> paramMap) throws BusinessException {
		List<CMFacilityDto> dtoList = null;
		CMDao cmDao = (CMDao)AsrDaoFactory.getDAOImpl(CMDao.class.getSimpleName());
		if(cmDao != null){
			dtoList = cmDao.getCmFacilities(paramMap);
		}
		return dtoList;
	}

}
