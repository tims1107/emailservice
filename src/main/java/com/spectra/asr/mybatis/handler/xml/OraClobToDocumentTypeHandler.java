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
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.sql.*;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Slf4j
public class OraClobToDocumentTypeHandler implements TypeHandler<Document> {
	//private Logger log = Logger.getLogger(OraClobToDocumentTypeHandler.class);
	
	public Document getResult(ResultSet resultSet, String columnName) throws SQLException{
		Document doc = null;
		if(ConditionChecker.checkNotNull(resultSet) && ConditionChecker.checkValidString(columnName)){
			if(resultSet.unwrap(OracleResultSet.class) instanceof OracleResultSet){
				String result = null;
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnName);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					Clob clob = xmlResult.getClobVal();
					long clobLen = clob.length();
					result = clob.getSubString(1, (int)clobLen);
					log.warn("getResult(): result: " + (result == null ? "NULL" : result));
					if(ConditionChecker.checkValidString(result)){
						try{
							DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
							doc = db.parse(new InputSource(new StringReader(result)));							
						}catch(IOException ioe){
							throw new SQLException(ioe);
						}catch(ParserConfigurationException pce){
							throw new SQLException(pce);
						}catch(SAXException saxe){
							throw new SQLException(saxe);
						}
					}
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
				String result = null;
				OracleResultSet oracleResultSet = (OracleResultSet) resultSet.unwrap(OracleResultSet.class);
				OPAQUE opaqueValue = oracleResultSet.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					Clob clob = xmlResult.getClobVal();
					long clobLen = clob.length();
					result = clob.getSubString(1, (int)clobLen);
					if(ConditionChecker.checkValidString(result)){
						try{
							DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
							doc = db.parse(new InputSource(new StringReader(result)));							
						}catch(IOException ioe){
							throw new SQLException(ioe);
						}catch(ParserConfigurationException pce){
							throw new SQLException(pce);
						}catch(SAXException saxe){
							throw new SQLException(saxe);
						}
					}					
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
				String result = null;
				OracleCallableStatement oracleCallableStatement = (OracleCallableStatement) callableStatement.unwrap(OracleCallableStatement.class);
				OPAQUE opaqueValue = oracleCallableStatement.getOPAQUE(columnIndex);
				if(ConditionChecker.checkNotNull(opaqueValue)){
					XMLType xmlResult = XMLType.createXML(opaqueValue);
					Clob clob = xmlResult.getClobVal();
					long clobLen = clob.length();
					result = clob.getSubString(1, (int)clobLen);
					if(ConditionChecker.checkValidString(result)){
						try{
							DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
							doc = db.parse(new InputSource(new StringReader(result)));							
						}catch(IOException ioe){
							throw new SQLException(ioe);
						}catch(ParserConfigurationException pce){
							throw new SQLException(pce);
						}catch(SAXException saxe){
							throw new SQLException(saxe);
						}
					}					
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
