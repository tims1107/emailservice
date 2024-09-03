package com.spectra.asr.mapper.mybatis.ora.hub.generator;

import java.util.List;
import java.util.Map;

import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;

public interface GeneratorMapper {
	int insertGenerator(GeneratorDto generatorDto);
	int insertGeneratorField(GeneratorFieldDto generatorFieldDto);
	
	int updateGenerator(GeneratorDto generatorDto);
	int updateGeneratorField(GeneratorFieldDto generatorFieldDto);
	
	GeneratorFieldDto selectLastGeneratorField();
	GeneratorDto selectLastGenerator();
	List<GeneratorDto> selectGenerator(GeneratorDto generatorDto);
	List<GeneratorFieldDto> selectGeneratorField(GeneratorFieldDto generatorFieldDto);
	List<GeneratorFieldDto> selectDistinctGeneratorFieldType(GeneratorFieldDto generatorFieldDto);
	
	GeneratorFieldsMapDto selectLastGeneratorFieldsMap();
	List<GeneratorFieldsMapDto> selectGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto);
	int updateGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto);
	int insertGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto);
	
	Integer selectMaxGeneratorFieldsGroup();
	
	List<GeneratorFieldDto> selectGeneratorFieldTypeByStateAbbrev(Map<String, Object> paramMap);
}
