package com.spectra.asr.dao.ms.cm;

import com.spectra.asr.dataaccess.factory.CmdbSqlSessionFactory;
import com.spectra.asr.dto.cm.CMFacilityDto;
import com.spectra.asr.mapper.mybatis.ms.cm.CMFacilityMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

@Slf4j
public class CMDaoImpl implements CMDao {
	//private Logger log = Logger.getLogger(CMDaoImpl.class);
	
	@Override
	public List<CMFacilityDto> getCmFacilities(Map<String, Object> paramMap) {
		List<CMFacilityDto> dtoList = null;
		SqlSession sqlSession = CmdbSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			CMFacilityMapper cmFacilityMapper = sqlSession.getMapper(CMFacilityMapper.class);
			if(cmFacilityMapper != null){
				try {
					dtoList = cmFacilityMapper.selectCmFacilities(paramMap);
				} catch (Exception e){
					e.printStackTrace();
				}finally{
					sqlSession.close();
				}
			}
		}
		return dtoList;
	}

}
