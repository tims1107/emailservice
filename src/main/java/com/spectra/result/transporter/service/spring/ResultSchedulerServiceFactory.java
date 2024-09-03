package com.spectra.result.transporter.service.spring;

//import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//import com.spectra.scorpion.framework.processor.Processor;
//import com.spectra.ala.constants.spring.SpringProcessorEnum;
@Slf4j
public final class ResultSchedulerServiceFactory implements ApplicationContextAware {
	//private static Logger log = Logger.getLogger(ResultSchedulerServiceFactory.class);
	
	private static ResultSchedulerServiceFactory resultSchedulerServiceFactory;
	
	ApplicationContext context;
	
	public ApplicationContext getContext() {
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		if(this.context == null){
			this.context = context;
		}
	}
	
	private ResultSchedulerServiceFactory(){
		
	}
	
	public SpringService getContextService(final String serviceName){
		//log.debug("getContextService(): serviceName: " + (serviceName == null ? "NULL" : serviceName));
		SpringService uiService = null;
		for (ResultSchedulerServiceEnum serviceEnum : ResultSchedulerServiceEnum.values()) {
			log.debug("getContextService(): serviceEnum.getServiceName(): " + (serviceEnum.getServiceName() == null ? "NULL" : serviceEnum.getServiceName()));
			if (serviceEnum.getServiceName().equalsIgnoreCase(serviceName)) {
				log.debug("getContextService(): serviceEnum.getServiceName(): " + (serviceEnum.getServiceName() == null ? "NULL" : serviceEnum.getServiceName()));
				//LOG.debug("getContextService(): context: " + (context == null ? "NULL" : context.toString()));
				//return serviceEnum.processorObj;
				//WebApplicationContext wc = (WebApplicationContext)request.getSession().getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
				uiService = (SpringService)this.context.getBean(serviceName);
				//LOG.info("getContextService(): serviceName: " + (serviceName == null ? "NULL" : serviceName));
				//LOG.info("getContextService(): uiService: " + (uiService == null ? "NULL" : uiService.toString()));
				//LOG.info("getContextService(): context: " + (context == null ? "NULL" : context.toString()));

				break;
			}
		}
		return uiService;
	}
}
