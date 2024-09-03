package com.spectra.asr.dao.ora.hub.condition;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.state.condition.ConditionFilterDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.mapper.mybatis.ora.hub.condition.ConditionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
@Slf4j
public class ConditionDaoImpl implements ConditionDao {
	//private Logger log = Logger.getLogger(ConditionDaoImpl.class);
	
	public List<String> getDistinctOTCByEntity(Map<String, Object> paramMap) {
		List<String> otcList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
			if(conditionMapper != null){
				try{
					otcList = conditionMapper.selectDistinctOTCByEntity(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return otcList;
	}
	
	public List<ConditionMasterDto> getConditionMaster(Map<String, Object> paramMap){
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectConditionMaster(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<ConditionMasterDto> getConditionFromConditionMaster(Map<String, Object> paramMap){
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectConditionFromConditionMaster(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;		
	}
	
	public int insertConditionMaster(ConditionMasterDto conditionMasterDto){
		int rowsInserted = 0;
		if(conditionMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsInserted = conditionMapper.insertConditionMaster(conditionMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateConditionMaster(ConditionMasterDto conditionMasterDto){
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsUpdated = conditionMapper.updateConditionMaster(conditionMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}

	public ConditionMasterDto getLastConditionMaster(){
		ConditionMasterDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
			if(conditionMapper != null){
				try{
					dto = conditionMapper.selectLastConditionMaster();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	

	public ConditionFilterDto getLastConditionFilter(){
		ConditionFilterDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
			if(conditionMapper != null){
				try{
					dto = conditionMapper.selectLastConditionFilter();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public List<ConditionFilterDto> getConditionFilters(Map<String, Object> paramMap) {
		List<ConditionFilterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectConditionFilters(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public int insertConditionFilter(ConditionFilterDto conditionFilterDto){
		int rowsInserted = 0;
		if(conditionFilterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsInserted = conditionMapper.insertConditionFilter(conditionFilterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public int updateConditionFilter(ConditionFilterDto conditionFilterDto){
		int rowsUpdated = 0;
		if(conditionFilterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ConditionMapper conditionMapper = sqlSession.getMapper(ConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsUpdated = conditionMapper.updateConditionFilter(conditionFilterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsUpdated;
	}
}
