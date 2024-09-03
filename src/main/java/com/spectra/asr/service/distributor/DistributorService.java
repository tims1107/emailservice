package com.spectra.asr.service.distributor;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface DistributorService {
	List<DistributorDto> getDistributor(DistributorDto distributorDto) throws BusinessException;
	int insertDistributor(DistributorDto distributorDto) throws BusinessException;
	int updateDistributor(DistributorDto distributorDto) throws BusinessException;
	
	List<DistributorItemDto> getJustDistributorItem(Map<String, Object> paramMap) throws BusinessException;
	List<DistributorItemDto> getOnlyDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException;
	List<DistributorItemDto> getDistributorItems(DistributorItemDto distributorItemDto) throws BusinessException;
	int insertDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException;
	int updateDistributorItem(DistributorItemDto distributorItemDto) throws BusinessException;
	
	List<DistributorItemsMapDto> getDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException;
	int insertDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException;
	int updateDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto) throws BusinessException;
	
	DistributorItemsMapDto getLastDistributorItemsMap() throws BusinessException;
	DistributorItemDto getLastDistributorItem() throws BusinessException;
	DistributorDto getLastDistributor() throws BusinessException;
	
	List<DistributorDto> getDistributorCron(Map<String, Object> paramMap) throws BusinessException;
}
