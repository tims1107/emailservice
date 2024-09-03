package com.spectra.asr.dao.ora.hub.audit;

import com.spectra.asr.dataaccess.factory.StaterptSqlSessionFactory;
import com.spectra.asr.dto.audit.AsrAuditDto;
import com.spectra.asr.mapper.mybatis.ora.hub.audit.AuditMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
@Slf4j
public class AsrAuditDaoImpl implements AsrAuditDao {
	//private Logger log = Logger.getLogger(AsrAuditDaoImpl.class);
	
	@Override
	public List<AsrAuditDto> getAsrAudit(AsrAuditDto asrAuditDto) {
		List<AsrAuditDto> dtoList = null;
		if(asrAuditDto != null){
			SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
			if(sqlSession != null){
				AuditMapper auditMapper = sqlSession.getMapper(AuditMapper.class);
				if(auditMapper != null){
					try{
						dtoList = auditMapper.selectAsrAudit(asrAuditDto);
					}finally{
						sqlSession.close();
					}
				}
			}
		}
		return dtoList;
	}

	@Override
	public int updateAsrAudit(AsrAuditDto asrAuditDto) {
		int rowsUpdated = 0;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			AuditMapper auditMapper = sqlSession.getMapper(AuditMapper.class);
			if(auditMapper != null){
				try{
					rowsUpdated = auditMapper.updateAsrAudit(asrAuditDto);
					sqlSession.commit();
				}finally{
					sqlSession.close();
				}
			}
		}
		return rowsUpdated;
	}

	@Override
	public int insertAsrAudit(AsrAuditDto asrAuditDto) {
		int rowsInserted = 0;
		SqlSession sqlSession = StaterptSqlSessionFactory.getSqlSessionFactory().openSession();
		if(sqlSession != null){
			AuditMapper auditMapper = sqlSession.getMapper(AuditMapper.class);
			if(auditMapper != null){
				try{
					rowsInserted = auditMapper.insertAsrAudit(asrAuditDto);
					sqlSession.commit();
				}finally{
					sqlSession.close();
				}
			}
		}
		return rowsInserted;
	}

}
