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
@Slf4j
public class OraXmlTypeFromStringBuilderHandler implements TypeHandler<StringBuilder> {
	//private Logger log = Logger.getLogger(OraXmlTypeFromStringBuilderHandler.class);
	
	public StringBuilder getResult(ResultSet resultSet, String columnName) throws SQLException{
		StringBuilder builder = null;
		if(ConditionChecker.checkNotNull(resultSet) && ConditionChecker.checkValidString(columnName)){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnName);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					builder = new StringBuilder(xmlResult.getStringVal());
					//log.warn("getResult(): builder: " + (builder == null ? "NULL" : builder.toString()));
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            } 
		}
		return builder;
	}
	
	public StringBuilder getResult(ResultSet resultSet, int columnIndex) throws SQLException{
		StringBuilder builder = null;
		if(ConditionChecker.checkNotNull(resultSet) && columnIndex != 0){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					builder = new StringBuilder(xmlResult.getStringVal());
					//log.warn("getResult(): builder: " + (builder == null ? "NULL" : builder.toString()));
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            } 
		}
		return builder;
	}
	
	public StringBuilder getResult(CallableStatement callableStatement, int columnIndex) throws SQLException{
		StringBuilder builder = null;
		if(ConditionChecker.checkNotNull(callableStatement) && columnIndex != 0){
			if(callableStatement.unwrap(OracleCallableStatement.class) instanceof OracleCallableStatement){
				OracleCallableStatement oracleCallableStatement = (OracleCallableStatement) callableStatement.unwrap(OracleCallableStatement.class);
				OPAQUE opaqueValue = oracleCallableStatement.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					builder = new StringBuilder(xmlResult.getStringVal());
					//log.warn("getResult(): builder: " + (builder == null ? "NULL" : builder.toString()));
				}
			}else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }
		}
		return builder;
	}
	
	public void setParameter(PreparedStatement preparedStatement, int i, StringBuilder parameter, JdbcType jdbcType) throws SQLException{
		if(ConditionChecker.checkNotNull(preparedStatement) && (i != 0) && ConditionChecker.checkNotNull(parameter)){
			if(preparedStatement.unwrap(OraclePreparedStatement.class) instanceof OraclePreparedStatement){
				OraclePreparedStatement oraclePreparedStatement = (OraclePreparedStatement) preparedStatement.unwrap(OraclePreparedStatement.class);
				//log.warn("setParameter(): parameter: " + (parameter == null ? "NULL" : parameter.toString()));
				if(ConditionChecker.checkNull(parameter)){
					oraclePreparedStatement.setNull(i, oracle.jdbc.OracleTypes.OPAQUE, "SYS.XMLTYPE");
				}else{
					XMLType xmlInput = XMLType.createXML(oraclePreparedStatement.getConnection(), parameter.toString());
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
	
	public StringBuilder valueOf(String s){
		return new StringBuilder(s);
	}
}
