package com.spectra.asr.dao.ora.hub.distributor;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;

public interface DistributorDao {
	List<DistributorItemsMapDto> getDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto);
	int updateDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto);
	int insertDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto);
	
	List<DistributorItemDto> getJustDistributorItem(Map<String, Object> paramMap);
	List<DistributorItemDto> getOnlyDistributorItem(DistributorItemDto distributorItemDto);
	List<DistributorItemDto> getDistributorItem(DistributorItemDto distributorItemDto);
	int updateDistributorItem(DistributorItemDto distributorItemDto);
	int insertDistributorItem(DistributorItemDto distributorItemDto);
	
	List<DistributorDto> getDistributor(DistributorDto distributorDto);
	int updateDistributor(DistributorDto distributorDto);
	int insertDistributor(DistributorDto distributorDto);
	
	DistributorItemsMapDto getLastDistributorItemsMap();
	DistributorItemDto getLastDistributorItem();
	DistributorDto getLastDistributor();
	
	List<DistributorDto> getDistributorCron(Map<String, Object> paramMap);
}
