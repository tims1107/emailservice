package com.spectra.asr.dao.ora.hub.generator;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.dto.generator.GeneratorFieldsMapDto;
import com.spectra.asr.mapper.mybatis.ora.hub.generator.GeneratorMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

@Slf4j
public class GeneratorDaoImpl implements GeneratorDao {
	//private Logger log = Logger.getLogger(GeneratorDaoImpl.class);
	
	@Override
	public int insertGenerator(GeneratorDto generatorDto) {
		int rowsInserted = 0;
		if(generatorDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			//log.warn("insertGenerator(): sqlSession: " + (sqlSession == null ? "NULL" : sqlSession.toString()));
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				//log.warn("insertGenerator(): generatorMapper: " + (generatorMapper == null ? "NULL" : generatorMapper.toString()));
				if(generatorMapper != null){
					try{
						rowsInserted = generatorMapper.insertGenerator(generatorDto);
						//log.warn("insertGenerator(): rowsInserted: " + (rowsInserted == 0 ? "ZARO" : String.valueOf(rowsInserted)));
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}

	@Override
	public int insertGeneratorField(GeneratorFieldDto generatorFieldDto) {
		int rowsInserted = 0;
		if(generatorFieldDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			log.warn("insertGeneratorField(): sqlSession: " + (sqlSession == null ? "NULL" : sqlSession.toString()));
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				log.warn("insertGeneratorField(): generatorMapper: " + (generatorMapper == null ? "NULL" : generatorMapper.toString()));
				if(generatorMapper != null){
					try{
						rowsInserted = generatorMapper.insertGeneratorField(generatorFieldDto);
						log.warn("insertGeneratorField(): rowsInserted: " + (rowsInserted == 0 ? "ZARO" : String.valueOf(rowsInserted)));
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateGenerator(GeneratorDto generatorDto){
		int rowsUpdated = 0;
		if(generatorDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						rowsUpdated = generatorMapper.updateGenerator(generatorDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}
	
	public int updateGeneratorField(GeneratorFieldDto generatorFieldDto){
		int rowsUpdated = 0;
		if(generatorFieldDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						rowsUpdated = generatorMapper.updateGeneratorField(generatorFieldDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}
	
	
	public GeneratorFieldDto getLastGeneratorField(){
		GeneratorFieldDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
			if(generatorMapper != null){
				try{
					dto = generatorMapper.selectLastGeneratorField();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public GeneratorDto getLastGenerator(){
		GeneratorDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
			if(generatorMapper != null){
				try{
					dto = generatorMapper.selectLastGenerator();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public List<GeneratorDto> getGenerator(GeneratorDto generatorDto){
//		StringBuffer sb = new StringBuffer();
//		sb.append("getGenerator: " + this.getClass().getName() + " " + generatorDto.getState() );
//		sb.append("\n");
//
//		fileChannelWrite(strbuf_to_bb(sb),"./asr_run",true);
//		sb.setLength(0);

		List<GeneratorDto> dtoList = null;
		if(generatorDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						dtoList = generatorMapper.selectGenerator(generatorDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<GeneratorFieldDto> getGeneratorField(GeneratorFieldDto generatorFieldDto){
		List<GeneratorFieldDto> dtoList = null;
		if(generatorFieldDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						dtoList = generatorMapper.selectGeneratorField(generatorFieldDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	
	public GeneratorFieldDto getDistinctGeneratorFieldType(GeneratorFieldDto generatorFieldDto){
		GeneratorFieldDto dto = null;
		if(generatorFieldDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						List<GeneratorFieldDto> dtoList = generatorMapper.selectDistinctGeneratorFieldType(generatorFieldDto);
						if((dtoList != null) && (dtoList.size() > 0)){
							dto = dtoList.get(0);
						}
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dto;
	}
	
	
	public GeneratorFieldsMapDto getLastGeneratorFieldsMap(){
		GeneratorFieldsMapDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
			if(generatorMapper != null){
				try{
					dto = generatorMapper.selectLastGeneratorFieldsMap();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public List<GeneratorFieldsMapDto> getGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto){
		List<GeneratorFieldsMapDto> dtoList = null;
		if(generatorFieldsMapDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						dtoList = generatorMapper.selectGeneratorFieldsMap(generatorFieldsMapDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public int updateGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto){
		int rowsUpdated = 0;
		if(generatorFieldsMapDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						rowsUpdated = generatorMapper.updateGeneratorFieldsMap(generatorFieldsMapDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}
	
	public int insertGeneratorFieldsMap(GeneratorFieldsMapDto generatorFieldsMapDto){
		int rowsInserted = 0;
		if(generatorFieldsMapDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
				if(generatorMapper != null){
					try{
						rowsInserted = generatorMapper.insertGeneratorFieldsMap(generatorFieldsMapDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public Integer getMaxGeneratorFieldsGroup(){
		Integer maxGroup = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
			if(generatorMapper != null){
				try{
					maxGroup = generatorMapper.selectMaxGeneratorFieldsGroup();
				}finally{
					sqlSession.close();
				}
			}
		}
		return maxGroup;
	}
	
	@Override
	public List<GeneratorFieldDto> getGeneratorFieldTypeByStateAbbrev(Map<String, Object> paramMap) {
		List<GeneratorFieldDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			GeneratorMapper generatorMapper = sqlSession.getMapper(GeneratorMapper.class);
			if(generatorMapper != null){
				try{
					dtoList = generatorMapper.selectGeneratorFieldTypeByStateAbbrev(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
}
