package com.spectra.asr.businessobject.ora.hub.generator;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;
import com.spectra.scorpion.framework.exception.BusinessException;

public interface GeneratorBo {
	GeneratorDto getLastGenerator();
	List<GeneratorDto> getGenerator(GeneratorDto generatorDto) throws BusinessException;
	int insertGenerator(GeneratorDto generatorDto) throws BusinessException;
	int updateGenerator(GeneratorDto generatorDto) throws BusinessException;
	
	GeneratorFieldDto getLastGeneratorField();
	List<GeneratorFieldDto> getGeneratorFields(GeneratorFieldDto generatorFieldDto) throws BusinessException;
	int insertGeneratorField(GeneratorFieldDto generatorFieldDto) throws BusinessException;
	int updateGeneratorField(GeneratorFieldDto generatorFieldDto) throws BusinessException;
	GeneratorFieldDto getDistinctGeneratorFieldType(GeneratorFieldDto generatorFieldDto) throws BusinessException;
	
	GeneratorFieldsMapDto getLastGeneratorFieldsMap();
	List<GeneratorFieldsMapDto> getGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException;
	int insertGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException;
	int updateGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException;
	
	List<GeneratorFieldDto> getGeneratorFieldTypeByStateAbbrev(Map<String, Object> paramMap) throws BusinessException;
}
