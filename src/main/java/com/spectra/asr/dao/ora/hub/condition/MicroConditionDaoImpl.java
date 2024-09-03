package com.spectra.asr.dao.ora.hub.condition;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.micro.MicroConsolidatedDto;
import com.spectra.asr.dto.state.condition.ConditionMasterDto;
import com.spectra.asr.mapper.mybatis.ora.hub.condition.MicroConditionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

@Slf4j
public class MicroConditionDaoImpl implements MicroConditionDao {
	//private Logger log = Logger.getLogger(MicroConditionDaoImpl.class);
	
	@Override
	public List<MicroConsolidatedDto> getConsolidatedList(Map<String, Object> paramMap) {
		List<MicroConsolidatedDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			MicroConditionMapper conditionMapper = sqlSession.getMapper(MicroConditionMapper.class);
			if(conditionMapper != null){
				try{
					dtoList = conditionMapper.selectConsolidatedList(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}

	public int insertMicroConditionMaster(ConditionMasterDto microConditionMasterDto) {
		int rowsInserted = 0;
		if(microConditionMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				MicroConditionMapper microConditionMapper = sqlSession.getMapper(MicroConditionMapper.class);
				if(microConditionMapper != null){
					try{
						rowsInserted = microConditionMapper.insertMicroConditionMaster(microConditionMasterDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return rowsInserted;
	}
	
	public ConditionMasterDto getLastMicroConditionMaster(){
		ConditionMasterDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			MicroConditionMapper microConditionMapper = sqlSession.getMapper(MicroConditionMapper.class);
			if(microConditionMapper != null){
				try{
					dto = microConditionMapper.selectLastMicroConditionMaster();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public List<ConditionMasterDto> getMicroConditionMaster(Map<String, Object> paramMap){
		List<ConditionMasterDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				MicroConditionMapper conditionMapper = sqlSession.getMapper(MicroConditionMapper.class);
				if(conditionMapper != null){
					try{
						dtoList = conditionMapper.selectMicroConditionMaster(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public int updateMicroConditionMaster(ConditionMasterDto conditionMasterDto){
		int rowsUpdated = 0;
		if(conditionMasterDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				MicroConditionMapper conditionMapper = sqlSession.getMapper(MicroConditionMapper.class);
				if(conditionMapper != null){
					try{
						rowsUpdated = conditionMapper.updateMicroConditionMaster(conditionMasterDto);
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
