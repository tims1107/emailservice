package com.spectra.result.transporter.commons.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.net.URL;
//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ClasspathAwarePropertiesConfiguration extends PropertiesConfiguration {
	//private static Logger log = Logger.getLogger(ClasspathAwarePropertiesConfiguration.class);
	
	public ClasspathAwarePropertiesConfiguration(){
		super();
	}
	
	public ClasspathAwarePropertiesConfiguration(File file) throws ConfigurationException{
		super(file);
	}
	
	public ClasspathAwarePropertiesConfiguration(String fileName) throws ConfigurationException{
		super(fileName);
	}
	
	public ClasspathAwarePropertiesConfiguration(URL url) throws ConfigurationException{
		super(url);
	}
	
	public void setUrl(String url){
		log.debug("setUrl(): url: " + (url == null ? "NULL" : url));
		//LOG.debug("setUrl(): servletContext: " + (servletContext == null ? "NULL" : servletContext.toString()));
		if(url != null){
			URL ctxUrl = this.getClass().getResource(url);
			log.debug("setUrl(): ctxUrl: " + (ctxUrl == null ? "NULL" : ctxUrl.toString()));
			if(ctxUrl != null){
				this.setURL(ctxUrl);
			}
		}
	}
}
