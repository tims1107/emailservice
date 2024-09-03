package com.spectra.asr.mybatis.handler.xml;

import com.spectra.framework.logic.ConditionChecker;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class OraXmlTypeStringHandler implements TypeHandler<String> {
	//private Logger log = Logger.getLogger(OraXmlTypeStringHandler.class);
	
	public String getResult(ResultSet resultSet, String columnName) throws SQLException {
		String result = null;
		if(ConditionChecker.checkNotNull(resultSet) && ConditionChecker.checkValidString(columnName)){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnName);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					result = xmlResult.getStringVal();
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}
		return result;
	}

	public String getResult(ResultSet resultSet, int columnIndex) throws SQLException {
		String result = null;
		if(ConditionChecker.checkNotNull(resultSet) && columnIndex != 0){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					result = xmlResult.getStringVal();
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}
		return result;
	}

	public String getResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		String result = null;
		if(ConditionChecker.checkNotNull(callableStatement) && columnIndex != 0){
			if(callableStatement.unwrap(OracleCallableStatement.class) instanceof OracleCallableStatement){
				OracleCallableStatement oracleCallableStatement = (OracleCallableStatement) callableStatement.unwrap(OracleCallableStatement.class);
				OPAQUE opaqueValue = oracleCallableStatement.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					result = xmlResult.getStringVal();
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}		
		return result;
	}

	public void setParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType) throws SQLException {
		if(ConditionChecker.checkNotNull(preparedStatement) && (i != 0) && ConditionChecker.checkNotNull(parameter)){
			if(preparedStatement.unwrap(OraclePreparedStatement.class) instanceof OraclePreparedStatement){
				OraclePreparedStatement oraclePreparedStatement = (OraclePreparedStatement) preparedStatement.unwrap(OraclePreparedStatement.class);
				//log.warn("setParameter(): parameter: " + (parameter == null ? "NULL" : parameter));
				if(ConditionChecker.checkInvalidString(parameter)){
					oraclePreparedStatement.setNull(i, oracle.jdbc.OracleTypes.OPAQUE, "SYS.XMLTYPE");
				}else{
					XMLType xmlInput = XMLType.createXML(oraclePreparedStatement.getConnection(), parameter);
					//log.warn("setParameter(): xmlInput: " + (xmlInput == null ? "NULL" : xmlInput.toString()));
					oraclePreparedStatement.setObject(i, xmlInput);
					//log.warn("setParameter(): i: " + (String.valueOf(i)));
					//log.warn("setParameter(): oraclePreparedStatement: " + (oraclePreparedStatement == null ? "NULL" : oraclePreparedStatement.toString()));
				}
			}else{
	            throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
	        }
		}
	}
	
	public String valueOf(String s){
		return ConditionChecker.checkValidString(s) ? s : null;
	}	
}
