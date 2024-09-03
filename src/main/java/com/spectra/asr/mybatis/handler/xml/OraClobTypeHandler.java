package com.spectra.asr.mybatis.handler.xml;

import com.spectra.framework.logic.ConditionChecker;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;
import org.apache.commons.dbcp.PoolableConnection;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;

//import org.apache.logging.log4j.LogManager;

//public class OraClobTypeHandler extends ClobTypeHandler {
@Slf4j
public class OraClobTypeHandler implements TypeHandler<String> { 
	//private Logger log = Logger.getLogger(OraClobTypeHandler.class);
	
	public String getResult(ResultSet resultSet, String columnName) throws SQLException{
		String result = null;
		if(ConditionChecker.checkNotNull(resultSet) && ConditionChecker.checkValidString(columnName)){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnName);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					Clob clob = xmlResult.getClobVal();
					long clobLen = clob.length();
					result = clob.getSubString(1, (int)clobLen);
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}
		return result;
	}
	
	public String getResult(ResultSet resultSet, int columnIndex) throws SQLException{
		String result = null;
		if(ConditionChecker.checkNotNull(resultSet) && columnIndex != 0){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					Clob clob = xmlResult.getClobVal();
					long clobLen = clob.length();
					result = clob.getSubString(1, (int)clobLen);
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}
		return result;		
	}
	
	public String getResult(CallableStatement callableStatement, int columnIndex) throws SQLException{
		String result = null;
		if(ConditionChecker.checkNotNull(callableStatement) && columnIndex != 0){
			if(callableStatement.unwrap(OracleCallableStatement.class) instanceof OracleCallableStatement){
				OracleCallableStatement oracleCallableStatement = (OracleCallableStatement) callableStatement.unwrap(OracleCallableStatement.class);
				OPAQUE opaqueValue = oracleCallableStatement.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					Clob clob = xmlResult.getClobVal();
					long clobLen = clob.length();
					result = clob.getSubString(1, (int)clobLen);
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}
		return result;
	}
	
    //public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)throws SQLException{
	public void setParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)throws SQLException{
            String inputParam = (String) parameter;
            //if ((null == inputParam) || inputParam.equals("")) {
            if(ConditionChecker.checkInvalidString(parameter)){
                /* empty string is equal to NULL under Oracle */
                ps.setNull(i, Types.CLOB);
            } else {
                Connection conn = ps.getConnection();
                if (conn instanceof PoolableConnection) {
                    conn = ((PoolableConnection) conn).getDelegate();
                }
                Clob newClob = conn.createClob();
                newClob.truncate(0);
                newClob.setString(1, inputParam);
                ps.setClob(i, newClob);
            }
        }
}
