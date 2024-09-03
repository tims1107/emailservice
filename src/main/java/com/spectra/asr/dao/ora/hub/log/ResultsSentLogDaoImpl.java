package com.spectra.asr.dao.ora.hub.log;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.log.ResultsSentLogDto;
import com.spectra.asr.mapper.mybatis.ora.hub.log.ResultsSentLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;

import java.util.List;

public class ResultsSentLogDaoImpl implements ResultsSentLogDao {
//	private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//	private static Logger log = lc.getLogger("ResultsSentLogDaoImpl.class");
	
	@Override
	public List<ResultsSentLogDto> getResultsSentLog(ResultsSentLogDto resultsSentLogDto) {
		List<ResultsSentLogDto> dtoList = null;
		if(resultsSentLogDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ResultsSentLogMapper resultsSentLogMapper = sqlSession.getMapper(ResultsSentLogMapper.class);
				if(resultsSentLogMapper != null){
					try{
						dtoList = resultsSentLogMapper.selectResultsSentLog(resultsSentLogDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public int insertResultsSentLog(ResultsSentLogDto resultsSentLogDto) {
		int rowsInserted = 0;
		if(resultsSentLogDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				ResultsSentLogMapper resultsSentLogMapper = sqlSession.getMapper(ResultsSentLogMapper.class);
				if(resultsSentLogMapper != null){
					try{
						rowsInserted = resultsSentLogMapper.insertResultsSentLog(resultsSentLogDto);
						sqlSession.commit();
					}finally{
						sqlSession.close();
					}
				}
			}
		}		
		return rowsInserted;
	}

}
