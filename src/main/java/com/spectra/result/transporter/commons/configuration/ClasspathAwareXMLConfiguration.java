package com.spectra.result.transporter.commons.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.File;
import java.net.URL;

//import javax.servlet.ServletContext;
//import org.apache.logging.log4j.LogManager;
//import org.springframework.web.context.ServletContextAware;

//public class ServletContextAwareXMLConfiguration extends XMLConfiguration implements ServletContextAware{
@Slf4j
public class ClasspathAwareXMLConfiguration extends XMLConfiguration{
	//private static Logger log = Logger.getLogger(ClasspathAwareXMLConfiguration.class);
	
	//private ServletContext servletContext;
	
	public ClasspathAwareXMLConfiguration(){
		super();
	}
	
	public ClasspathAwareXMLConfiguration(File file) throws ConfigurationException{
		super(file);
	}
	
	public ClasspathAwareXMLConfiguration(HierarchicalConfiguration c){
		super(c);
	}
	
	public ClasspathAwareXMLConfiguration(String fileName) throws ConfigurationException{
		super(fileName);
	}
	
	public ClasspathAwareXMLConfiguration(URL url) throws ConfigurationException{
		super(url);
	}
	
	/*public void setServletContext(ServletContext servletContext){
		this.servletContext = servletContext;
	}*/
	
	public void setUrl(String url){
		log.debug("setUrl(): url: " + (url == null ? "NULL" : url));
		//LOG.debug("setUrl(): servletContext: " + (servletContext == null ? "NULL" : servletContext.toString()));
		if(url != null){
			URL ctxUrl = this.getClass().getResource(url);
			log.debug("setUrl(): ctxUrl: " + (ctxUrl == null ? "NULL" : ctxUrl.toString()));
			if(ctxUrl != null){
				this.setURL(ctxUrl);
			}
			/*
			if(this.servletContext != null){
				URL ctxUrl = null;

				try{
					ctxUrl = this.servletContext.getResource(url);
					LOG.debug("setUrl(): ctxUrl: " + (ctxUrl == null ? "NULL" : ctxUrl.toString()));
					if(ctxUrl != null){
						this.setURL(ctxUrl);
					}
				}catch(MalformedURLException mue){
					LOG.error(mue);
					mue.printStackTrace();
				}

			}
			*/			
		}
	}
}
