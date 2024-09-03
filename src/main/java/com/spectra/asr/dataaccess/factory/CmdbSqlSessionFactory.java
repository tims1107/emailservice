package com.spectra.asr.dataaccess.factory;

import com.spectra.asr.constants.AsrConstants;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
@Slf4j
public class CmdbSqlSessionFactory {
	//private static Logger log = Logger.getLogger(CmdbSqlSessionFactory.class);
	
	private static SqlSessionFactory factory;
	
	 static{
		Reader reader = null;
		try {
	    	String resource = ApplicationProperties.getProperty(AsrConstants.CMDB_MYBATIS_CONFIG_KEY);
	    	log.warn("StaterptSqlSessionFactory(): resource: " + (resource == null ? "NULL" : resource));
	        reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		factory = new SqlSessionFactoryBuilder().build(reader, AsrConstants.CMDB_MYBATIS_ENV_KEY);
	 }	
	
	private CmdbSqlSessionFactory(){
		
	}
	
	public static SqlSessionFactory getSqlSessionFactory(){
		 return factory;
	}
	
}
