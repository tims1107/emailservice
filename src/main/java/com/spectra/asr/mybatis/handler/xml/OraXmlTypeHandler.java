package com.spectra.asr.mybatis.handler.xml;

import com.spectra.framework.logic.ConditionChecker;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.OPAQUE;
import oracle.xdb.XMLType;
import oracle.xml.parser.v2.XMLParseException;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.apache.logging.log4j.LogManager;

@Slf4j
public class OraXmlTypeHandler implements TypeHandler<Document> {
	//private Logger log = Logger.getLogger(OraXmlTypeHandler.class);
	
    public Document getResult(ResultSet resultSet, String columnName) throws SQLException{
    	Document doc = null;
    	if(ConditionChecker.checkNotNull(resultSet) && ConditionChecker.checkValidString(columnName)){
            if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
                OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
                OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnName);
                if(ConditionChecker.checkNotNull(opaqueValue)){
                    XMLType xmlResult = XMLType.createXML(opaqueValue);
                    //log.warn("getResult(): stringVal: " + (xmlResult.getStringVal() == null ? "NULL" : xmlResult.getStringVal()));
                    //log.warn("getResult(): rootElement: " + (xmlResult.getRootElement() == null ? "NULL" : xmlResult.getRootElement()));
                    doc = xmlResult.getDocument();
                }else{
                    doc = (Document) null;
                }
            }else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }    		
    	}
    	return doc;
    }
    
    public Document getResult(ResultSet resultSet, int columnIndex) throws SQLException{
    	Document doc = null;
    	if(ConditionChecker.checkNotNull(resultSet) && columnIndex != 0){
            if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
                OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
                OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnIndex);
                if(ConditionChecker.checkNotNull(opaqueValue)){
                    XMLType xmlResult = XMLType.createXML(opaqueValue);
                    doc = xmlResult.getDocument();
                }else{
                    doc = (Document) null;
                }
            }else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }    		
    	}
    	return doc;
    }
    
    public Document getResult(CallableStatement callableStatement, int columnIndex) throws SQLException{
    	Document doc = null;
    	if(ConditionChecker.checkNotNull(callableStatement) && columnIndex != 0){
            if(callableStatement.unwrap(OracleCallableStatement.class) instanceof OracleCallableStatement){
                OracleCallableStatement oracleCallableStatement = (OracleCallableStatement) callableStatement.unwrap(OracleCallableStatement.class);
                OPAQUE opaqueValue = oracleCallableStatement.getOPAQUE(columnIndex);
                if(opaqueValue != null){
                    XMLType xmlResult = XMLType.createXML(opaqueValue);
                    doc = xmlResult.getDocument();
                }else{
                    doc = (Document) null;
                }
            }else{
                throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
            }    		
    	}
    	return doc;
    }
    
    public void setParameter(PreparedStatement preparedStatement, int i, Document parameter, JdbcType jdbcType) throws SQLException{
        if(preparedStatement.unwrap(OraclePreparedStatement.class) instanceof OraclePreparedStatement){
            OraclePreparedStatement oraclePreparedStatement = (OraclePreparedStatement) preparedStatement.unwrap(OraclePreparedStatement.class);
            if(parameter == null){
                oraclePreparedStatement.setNull(i, oracle.jdbc.OracleTypes.OPAQUE, "SYS.XMLTYPE");
            }else{
                //XMLType xmlInput = XMLType.createXML(oraclePreparedStatement.getConnection(), (Document) parameter);
            	XMLType xmlInput = XMLType.createXML(oraclePreparedStatement.getConnection(), parameter);
                oraclePreparedStatement.setObject(i, xmlInput);
            }
        }else{
            throw new UnsupportedOperationException("XMLType mapping only supported for Oracle RDBMS");
        }
    }

    public Object valueOf(String s){
        try{
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            return db.parse(new InputSource(new StringReader(s)));
        }catch (Exception e){
            if(e instanceof XMLParseException){
                throw new IllegalArgumentException("Argument for valueOf() doesn't describe a XML Document");
            }else{
                throw new RuntimeException("Error creating XML document.  Cause: " + e);
            }
        }
    }    
}
