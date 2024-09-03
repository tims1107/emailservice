package com.spectra.result.transporter.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ConversionContextFactory implements ApplicationContextAware {
	//private Logger log = Logger.getLogger(ConversionContextFactory.class);
	
	private static ConversionContextFactory conversionContextFactory = null;
	
	ApplicationContext context;
	
	private ConversionContextFactory(){
		
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if(this.context == null){
			this.context = context;
		}
	}

	public ConversionContext getHL7ConversionContext(){
		ConversionContext conversionContext = null;
		if(this.context != null){
			conversionContext = (ConversionContext)this.context.getBean("HL7ConversionContext");
		}
		return conversionContext;
	}
}
