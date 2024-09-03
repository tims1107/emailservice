package com.spectra.result.transporter.context.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public class ApplicationContextProvider implements ApplicationContextAware {
	//private Logger log = Logger.getLogger(ApplicationContextProvider.class);
	
    private static ApplicationContext context = null;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac)throws BeansException {
    	log.info("setApplicationContext(): ac: " + (ac == null ? "NULL" : ac.toString()));
        context = ac;
    }
}
