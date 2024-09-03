package com.spectra.asr.service.audit;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.audit.AsrAuditBo;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class AsrAuditServiceImpl implements AsrAuditService {
	//private Logger log = Logger.getLogger(AsrAuditServiceImpl.class);
	
	@Override
	public List<AsrAuditDto> getAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException {
		List<AsrAuditDto> dtoList = null;
		if(asrAuditDto != null){
			AsrAuditBo bo = AsrBoFactory.getAsrAuditBo();
			if(bo != null){
				dtoList = bo.getAsrAudit(asrAuditDto);
			}
		}
		return dtoList;
	}

	@Override
	public int updateAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException {
		int rowsUpdated = 0;
		if(asrAuditDto != null){
			AsrAuditBo bo = AsrBoFactory.getAsrAuditBo();
			if(bo != null){
				rowsUpdated = bo.updateAsrAudit(asrAuditDto);
			}
		}
		return rowsUpdated;
	}

	@Override
	public int insertAsrAudit(AsrAuditDto asrAuditDto) throws BusinessException {
		int rowsInserted = 0;
		if(asrAuditDto != null){
			AsrAuditBo bo = AsrBoFactory.getAsrAuditBo();
			if(bo != null){
				rowsInserted = bo.insertAsrAudit(asrAuditDto);
			}
		}
		return rowsInserted;
	}

}
