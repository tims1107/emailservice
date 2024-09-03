package com.spectra.asr.dao.ora.hub.generator;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;

public interface GeneratorDao {
	int insertGenerator(GeneratorDto generatorDto);
	int insertGeneratorField(GeneratorFieldDto generatorFieldDto);
	
	int updateGenerator(GeneratorDto generatorDto);
	int updateGeneratorField(GeneratorFieldDto generatorFieldDto);
	
	GeneratorFieldDto getLastGeneratorField();
	GeneratorDto getLastGenerator();
	List<GeneratorDto> getGenerator(GeneratorDto generatorDto);
	List<GeneratorFieldDto> getGeneratorField(GeneratorFieldDto generatorFieldDto);
	GeneratorFieldDto getDistinctGeneratorFieldType(GeneratorFieldDto generatorFieldDto);
	
	GeneratorFieldsMapDto getLastGeneratorFieldsMap();
	List<GeneratorFieldsMapDto> getGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto);
	int updateGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto);
	int insertGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto);
	
	Integer getMaxGeneratorFieldsGroup();
	
	List<GeneratorFieldDto> getGeneratorFieldTypeByStateAbbrev(Map<String, Object> paramMap);
}
