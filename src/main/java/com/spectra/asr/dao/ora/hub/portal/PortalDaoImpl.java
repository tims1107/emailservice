package com.spectra.asr.dao.ora.hub.portal;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.portal.EntityResultCountDto;
import com.spectra.asr.mapper.mybatis.ora.hub.portal.PortalMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class PortalDaoImpl implements PortalDao {
	//private Logger log = Logger.getLogger(PortalDaoImpl.class);
	
	@Override
	public List<EntityResultCountDto> getEntityResultCount(Map<String, Object> paramMap) {
		List<EntityResultCountDto> dtoList = null;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				PortalMapper PortalMapper = sqlSession.getMapper(PortalMapper.class);
				if(PortalMapper != null){
					try{
						dtoList = PortalMapper.selectEntityResultCount(paramMap);
						if(dtoList != null){
							String entity = (String)paramMap.get("entity");
							for(EntityResultCountDto dto : dtoList){
								dto.setEntity(entity);
							}
						}
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public List<String> getEntityByType(String entityType) {
		List<String> entityList = null;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			PortalMapper PortalMapper = sqlSession.getMapper(PortalMapper.class);
			if(PortalMapper != null){
				try{
					entityList = PortalMapper.selectEntityByType(entityType);
				}finally{
					sqlSession.close();
				}
			}
		}
		return entityList;
	}

	public void truncateTmpPrevResults(){
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			PortalMapper PortalMapper = sqlSession.getMapper(PortalMapper.class);
			if(PortalMapper != null){
				try{
					PortalMapper.truncateTmpPrevResults();
				}finally{
					sqlSession.close();
				}
			}
		}
	}
	
	public int insertIntoTmpPrevResults(Map<String, Object> paramMap){
		int rowsInserted = 0;
		if(paramMap != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				PortalMapper PortalMapper = sqlSession.getMapper(PortalMapper.class);
				if(PortalMapper != null){
					try{
						rowsInserted = PortalMapper.insertIntoTmpPrevResults(paramMap);
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
