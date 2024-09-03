package com.spectra.asr.dao.ora.hub.distributor;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.distributor.DistributorItemsMapDto;
import com.spectra.asr.mapper.mybatis.ora.hub.distributor.DistributorMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

@Slf4j
public class DistributorDaoImpl implements DistributorDao {
	//private Logger log = Logger.getLogger(DistributorDaoImpl.class);
	
	public List<DistributorItemsMapDto> getDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto){
		List<DistributorItemsMapDto> dtoList = null;
		if(distributorItemsMapDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						dtoList = distributorMapper.selectDistributorItemsMap(distributorItemsMapDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	public int updateDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto){
		int rowsUpdated = 0;
		if(distributorItemsMapDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						rowsUpdated = distributorMapper.updateDistributorItemsMap(distributorItemsMapDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsUpdated;
	}
	
	public int insertDistributorItemsMap(DistributorItemsMapDto distributorItemsMapDto){
		int rowsInserted = 0;
		if(distributorItemsMapDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						rowsInserted = distributorMapper.insertDistributorItemsMap(distributorItemsMapDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsInserted;
	}
	
	public List<DistributorItemDto> getJustDistributorItem(Map<String, Object> paramMap) {
		List<DistributorItemDto> dtoList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
			if(distributorMapper != null){
				try{
					dtoList = distributorMapper.selectJustDistributorItem(paramMap);
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}
	
	public List<DistributorItemDto> getOnlyDistributorItem(DistributorItemDto distributorItemDto) {
		List<DistributorItemDto> dtoList = null;
		if(distributorItemDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						dtoList = distributorMapper.selectOnlyDistributorItem(distributorItemDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
	
	@Override
	public List<DistributorItemDto> getDistributorItem(DistributorItemDto distributorItemDto) {
		List<DistributorItemDto> dtoList = null;
		if(distributorItemDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						dtoList = distributorMapper.selectDistributorItem(distributorItemDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public int updateDistributorItem(DistributorItemDto distributorItemDto) {
		int rowsUpdated = 0;
		if(distributorItemDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						rowsUpdated = distributorMapper.updateDistributorItem(distributorItemDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsUpdated;
	}

	@Override
	public int insertDistributorItem(DistributorItemDto distributorItemDto) {
		int rowsInserted = 0;
		if(distributorItemDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						rowsInserted = distributorMapper.insertDistributorItem(distributorItemDto);
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
	public List<DistributorDto> getDistributor(DistributorDto distributorDto) {
		List<DistributorDto> dtoList = null;
		if(distributorDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						dtoList = distributorMapper.selectDistributor(distributorDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public int updateDistributor(DistributorDto distributorDto) {
		int rowsUpdated = 0;
		if(distributorDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						rowsUpdated = distributorMapper.updateDistributor(distributorDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsUpdated;
	}

	@Override
	public int insertDistributor(DistributorDto distributorDto) {
		int rowsInserted = 0;
		if(distributorDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						rowsInserted = distributorMapper.insertDistributor(distributorDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}			
		}
		return rowsInserted;
	}

	public DistributorItemsMapDto getLastDistributorItemsMap(){
		DistributorItemsMapDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
			if(distributorMapper != null){
				try{
					dto = distributorMapper.selectLastDistributorItemsMap();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public DistributorItemDto getLastDistributorItem(){
		DistributorItemDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
			if(distributorMapper != null){
				try{
					dto = distributorMapper.selectLastDistributorItem();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public DistributorDto getLastDistributor(){
		DistributorDto dto = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
			if(distributorMapper != null){
				try{
					dto = distributorMapper.selectLastDistributor();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dto;
	}
	
	public List<DistributorDto> getDistributorCron(Map<String, Object> paramMap){
		List<DistributorDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				DistributorMapper distributorMapper = sqlSession.getMapper(DistributorMapper.class);
				if(distributorMapper != null){
					try{
						dtoList = distributorMapper.selectDistributorCron(paramMap);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}
}
