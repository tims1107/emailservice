package com.spectra.asr.businessobject.ora.hub.audit;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.audit.AsrAuditDao;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class AsrAuditBoImpl implements AsrAuditBo {
	//private Logger log = Logger.getLogger(AsrAuditBoImpl.class);
	
	@Override
	public List<AsrAuditDto> getAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException {
		List<AsrAuditDto> dtoList = null;
		if(asrAuditDto != null){
			AsrAuditDao asrAuditDao = (AsrAuditDao)AsrDaoFactory.getDAOImpl(AsrAuditDao.class.getSimpleName());
			if(asrAuditDao != null){
				dtoList = asrAuditDao.getAsrAudit(asrAuditDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null asrAuditDto").toString());
		}
		return dtoList;
	}

	@Override
	public int updateAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException {
		int rowsUpdated = 0;
		if(asrAuditDto != null){
			AsrAuditDao asrAuditDao = (AsrAuditDao)AsrDaoFactory.getDAOImpl(AsrAuditDao.class.getSimpleName());
			if(asrAuditDao != null){
				rowsUpdated = asrAuditDao.updateAsrAudit(asrAuditDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null asrAuditDto").toString());
		}
		return rowsUpdated;
	}

	@Override
	public int insertAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException {
		int rowsInserted = 0;
		if(asrAuditDto != null){
			AsrAuditDao asrAuditDao = (AsrAuditDao)AsrDaoFactory.getDAOImpl(AsrAuditDao.class.getSimpleName());
			if(asrAuditDao != null){
				rowsInserted = asrAuditDao.insertAsrAudit(asrAuditDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null asrAuditDto").toString());
		}
		return rowsInserted;
	}

}
