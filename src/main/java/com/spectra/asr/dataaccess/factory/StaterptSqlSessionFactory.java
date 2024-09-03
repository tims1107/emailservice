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
public class StaterptSqlSessionFactory {
	//private static Logger log = Logger.getLogger(StaterptSqlSessionFactory.class);
	
	private static SqlSessionFactory factory;
	
	 static{
		Reader reader = null;
		try {
			//System.out.println(AsrConstants.STATERPT_MYBATIS_CONFIG_KEY);
	    	String resource = ApplicationProperties.getProperty(AsrConstants.STATERPT_MYBATIS_CONFIG_KEY);
			//System.out.println(resource);
	    	log.warn("StaterptSqlSessionFactory(): resource: " + (resource == null ? "NULL" : resource));
	        reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		factory = new SqlSessionFactoryBuilder().build(reader, AsrConstants.STATERPT_MYBATIS_ENV_KEY);
	 }
	
	private StaterptSqlSessionFactory(){
		
	}
	
	public static SqlSessionFactory getSqlSessionFactory(){

		 return factory;
	}
}
