package com.spectra.asr.businessobject.ora.hub.distributor;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.distributor.DistributorDao;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class DistributorBoImpl implements DistributorBo {
	//private Logger log = Logger.getLogger(DistributorBoImpl.class);
	
	public List<DistributorDto> getDistributor(DistributorDto distributorDto) throws BusinessException{
		List<DistributorDto> dtoList = null;
		if(distributorDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				dtoList = distributorDao.getDistributor(distributorDto);
			}
		}
		return dtoList;
	}
	
	public int insertDistributor(DistributorDto distributorDto) throws BusinessException{
		int rowsInserted = 0;
		if(distributorDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				rowsInserted = distributorDao.insertDistributor(distributorDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null distributorDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateDistributor(DistributorDto distributorDto) throws BusinessException{
		int rowsUpdated = 0;
		if(distributorDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				rowsUpdated = distributorDao.updateDistributor(distributorDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null distributorDto").toString());
		}
		return rowsUpdated;
	}
	
	public List<DistributorItemDto> getJustDistributorItem(Map<String, Object> paramMap) throws BusinessException {
		List<DistributorItemDto> dtoList = null;
		DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
		if(distributorDao != null){
			dtoList = distributorDao.getJustDistributorItem(paramMap);
		}
		return dtoList;
	}
	
	public List<DistributorItemDto> getOnlyDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException {
		List<DistributorItemDto> dtoList = null;
		if(distributorItemDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				dtoList = distributorDao.getOnlyDistributorItem(distributorItemDto);
			}
		}
		return dtoList;
	}
	
	public List<DistributorItemDto> getDistributorItems(DistributorItemDto distributorItemDto) throws BusinessException{
		List<DistributorItemDto> dtoList = null;
		if(distributorItemDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				dtoList = distributorDao.getDistributorItem(distributorItemDto);
			}
		}
		return dtoList;
	}
	
	public int insertDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException{
		int rowsInserted = 0;
		if(distributorItemDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				rowsInserted = distributorDao.insertDistributorItem(distributorItemDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null distributorItemDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException{
		int rowsUpdated = 0;
		if(distributorItemDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				rowsUpdated = distributorDao.updateDistributorItem(distributorItemDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null distributorItemDto").toString());
		}
		return rowsUpdated;
	}
	
	public List<DistributorItemsMapDto> getDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException{
		List<DistributorItemsMapDto> dtoList = null;
		if(distributorItemsMapDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				dtoList = distributorDao.getDistributorItemsMap(distributorItemsMapDto);
			}
		}
		return dtoList;
	}
	
	public int insertDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException{
		int rowsInserted = 0;
		if(distributorItemsMapDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				rowsInserted = distributorDao.insertDistributorItemsMap(distributorItemsMapDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null distributorItemsMapDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException{
		int rowsUpdated = 0;
		if(distributorItemsMapDto != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				rowsUpdated = distributorDao.updateDistributorItemsMap(distributorItemsMapDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null distributorItemsMapDto").toString());
		}
		return rowsUpdated;
	}

	public DistributorItemsMapDto getLastDistributorItemsMap() throws BusinessException{
		DistributorItemsMapDto dto = null;
		DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
		if(distributorDao != null){
			dto = distributorDao.getLastDistributorItemsMap();
		}
		return dto;
	}
	
	public DistributorItemDto getLastDistributorItem() throws BusinessException{
		DistributorItemDto dto = null;
		DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
		if(distributorDao != null){
			dto = distributorDao.getLastDistributorItem();
		}
		return dto;
	}
	
	public DistributorDto getLastDistributor() throws BusinessException{
		DistributorDto dto = null;
		DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
		if(distributorDao != null){
			dto = distributorDao.getLastDistributor();
		}
		return dto;
	}
	
	public List<DistributorDto> getDistributorCron(Map<String, Object> paramMap) throws BusinessException{
		List<DistributorDto> dtoList = null;
		if(paramMap != null){
			DistributorDao distributorDao = (DistributorDao)AsrDaoFactory.getDAOImpl(DistributorDao.class.getSimpleName());
			if(distributorDao != null){
				dtoList = distributorDao.getDistributorCron(paramMap);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null paramMap").toString());
		}
		return dtoList;
	}
}
