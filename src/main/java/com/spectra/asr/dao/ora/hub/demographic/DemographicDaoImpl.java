package com.spectra.asr.dao.ora.hub.demographic;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.demographic.AsrActivityNoDemoDto;
import com.spectra.asr.dto.demographic.AsrPhysicianDto;
import com.spectra.asr.dto.lab.LabDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.dto.state.extract.LovTestCategoryDto;
import com.spectra.asr.mapper.mybatis.ora.hub.demographic.DemographicMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;
@Slf4j
public class DemographicDaoImpl implements DemographicDao {
	//private Logger log = Logger.getLogger(DemographicDaoImpl.class);
	
	@Override
	public void callProcActivityNoDemo(){
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
			if(demographicMapper != null){
				try{
					demographicMapper.callProcActivityNoDemo();
				}finally{
					sqlSession.close();
				}
			}
		}
	}
	
	public List<AsrActivityNoDemoDto> getNoDemo(Map<String, Object> paramMap){
		List<AsrActivityNoDemoDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
			if(demographicMapper != null){
				try{
					dtoList = demographicMapper.selectNoDemo(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
	
	public List<AsrActivityNoDemoDto> getNoDemoPrevDay(String hasDemo){
		List<AsrActivityNoDemoDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
			if(demographicMapper != null){
				try{
					dtoList = demographicMapper.selectNoDemoPrevDay(hasDemo);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
	
	public List<AsrPhysicianDto> getLabOrderPhysician(String requisitionId){
		List<AsrPhysicianDto> dtoList = null;
		if(requisitionId != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
				if(demographicMapper != null){
					try{
						dtoList = demographicMapper.selectLabOrderPhysician(requisitionId);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public List<StateResultDto> getIntraLabsNoDemo(Map<String, Object> paramMap){
		List<StateResultDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
			if(demographicMapper != null){
				try{
					dtoList = demographicMapper.selectIntraLabsNoDemo(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
	
	public int updateIntraLabsNoDemo(StateResultDto stateResultDto){
		int rowsUpdated = 0;
		if(stateResultDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
				if(demographicMapper != null){
					try{
						rowsUpdated = demographicMapper.updateIntraLabsNoDemo(stateResultDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsUpdated;
	}
	
	public int insertIntraLabsNoDemo(StateResultDto stateResultDto){
		int rowsInserted = 0;
		if(stateResultDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
				if(demographicMapper != null){
					try{
						rowsInserted = demographicMapper.insertIntraLabsNoDemo(stateResultDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsInserted;
	}
	
	public List<LovTestCategoryDto> getLovTestCategory(Map<String, Object> paramMap) {
		List<LovTestCategoryDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
			if(demographicMapper != null){
				try{
					dtoList = demographicMapper.selectLovTestCategory(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
	
	public List<LabDto> getLab(Map<String, Object> paramMap){
		List<LabDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DemographicMapper demographicMapper = sqlSession.getMapper(DemographicMapper.class);
			if(demographicMapper != null){
				try{
					dtoList = demographicMapper.selectLab(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
}
