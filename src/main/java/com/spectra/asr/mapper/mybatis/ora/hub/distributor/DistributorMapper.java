package com.spectra.asr.mapper.mybatis.ora.hub.distributor;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;

public interface DistributorMapper {
	List<DistributorItemsMapDto> selectDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto);
	int updateDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto);
	int insertDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto);
	
	List<DistributorItemDto> selectJustDistributorItem(Map<String, Object> paramMap);
	List<DistributorItemDto> selectOnlyDistributorItem(DistributorItemDto distributorItemDto);
	List<DistributorItemDto> selectDistributorItem(DistributorItemDto distributorItemDto);
	int updateDistributorItem(DistributorItemDto distributorItemDto);
	int insertDistributorItem(DistributorItemDto distributorItemDto);
	
	List<DistributorDto> selectDistributor(DistributorDto distributorDto);
	int updateDistributor(DistributorDto distributorDto);
	int insertDistributor(DistributorDto distributorDto);
	
	DistributorItemsMapDto selectLastDistributorItemsMap();
	DistributorItemDto selectLastDistributorItem();
	DistributorDto selectLastDistributor();
	
	List<DistributorDto> selectDistributorCron(Map<String, Object> paramMap);
}
