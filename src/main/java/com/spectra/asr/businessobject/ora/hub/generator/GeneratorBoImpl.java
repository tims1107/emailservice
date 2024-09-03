package com.spectra.asr.businessobject.ora.hub.generator;

import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class GeneratorBoImpl implements GeneratorBo {
	//private Logger log = Logger.getLogger(GeneratorBoImpl.class);
	
	public GeneratorDto getLastGenerator(){
		GeneratorDto dto = null;
		GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
		if(generatorDao != null){
			dto = generatorDao.getLastGenerator();
		}
		return dto;
	}
	
	public List<GeneratorDto> getGenerator(GeneratorDto generatorDto) throws BusinessException{
		List<GeneratorDto> dtoList = null;
		if(generatorDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				dtoList = generatorDao.getGenerator(generatorDto);
			}
		}
		return dtoList;
	}
	
	public int insertGenerator(GeneratorDto generatorDto) throws BusinessException{
		int rowsInserted = 0;
		if(generatorDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				rowsInserted = generatorDao.insertGenerator(generatorDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null generatorDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateGenerator(GeneratorDto generatorDto) throws BusinessException{
		int rowsUpdated = 0;
		if(generatorDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				rowsUpdated = generatorDao.updateGenerator(generatorDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null generatorDto").toString());
		}
		return rowsUpdated;
	}
	
	public GeneratorFieldDto getLastGeneratorField(){
		GeneratorFieldDto dto = null;
		GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
		if(generatorDao != null){
			dto = generatorDao.getLastGeneratorField();
		}
		return dto;
	}
	
	public List<GeneratorFieldDto> getGeneratorFields(GeneratorFieldDto generatorFieldDto) throws BusinessException{
		List<GeneratorFieldDto> dtoList = null;
		if(generatorFieldDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				dtoList = generatorDao.getGeneratorField(generatorFieldDto);
			}
		}
		return dtoList;
	}
	
	public int insertGeneratorField(GeneratorFieldDto generatorFieldDto) throws BusinessException{
		int rowsInserted = 0;
		if(generatorFieldDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				rowsInserted = generatorDao.insertGeneratorField(generatorFieldDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null generatorFieldDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateGeneratorField(GeneratorFieldDto generatorFieldDto) throws BusinessException{
		int rowsUpdated = 0;
		if(generatorFieldDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				rowsUpdated = generatorDao.updateGeneratorField(generatorFieldDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null generatorDto").toString());
		}
		return rowsUpdated;
	}
	
	public GeneratorFieldDto getDistinctGeneratorFieldType(GeneratorFieldDto generatorFieldDto) throws BusinessException {
		GeneratorFieldDto dto = null;
		if(generatorFieldDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				dto = generatorDao.getDistinctGeneratorFieldType(generatorFieldDto);
			}
		}
		return dto;
	}
	
	public GeneratorFieldsMapDto getLastGeneratorFieldsMap(){
		GeneratorFieldsMapDto dto = null;
		GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
		if(generatorDao != null){
			dto = generatorDao.getLastGeneratorFieldsMap();
		}
		return dto;
	}
	
	public List<GeneratorFieldsMapDto> getGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException{
		List<GeneratorFieldsMapDto> dtoList = null;
		if(generatorFieldsMapDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				dtoList = generatorDao.getGeneratorFieldsMap(generatorFieldsMapDto);
			}
		}
		return dtoList;
	}
	
	public int insertGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException{
		int rowsInserted = 0;
		if(generatorFieldsMapDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				rowsInserted = generatorDao.insertGeneratorFieldsMap(generatorFieldsMapDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null generatorFieldsMapDto").toString());
		}
		return rowsInserted;
	}
	
	public int updateGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto) throws BusinessException{
		int rowsUpdated = 0;
		if(generatorFieldsMapDto != null){
			GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			if(generatorDao != null){
				rowsUpdated = generatorDao.updateGeneratorFieldsMap(generatorFieldsMapDto);
			}
		}else{
			throw new BusinessException(new IllegalArgumentException("null generatorFieldsMapDto").toString());
		}
		return rowsUpdated;
	}

	@Override
	public List<GeneratorFieldDto> getGeneratorFieldTypeByStateAbbrev(Map<String, Object> paramMap) throws BusinessException {
		List<GeneratorFieldDto> dtoList = null;
		GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
		if(generatorDao != null){
			dtoList = generatorDao.getGeneratorFieldTypeByStateAbbrev(paramMap);
		}else{
			throw new BusinessException(new IllegalArgumentException("null dao").toString());
		}
		return dtoList;
	}
}
