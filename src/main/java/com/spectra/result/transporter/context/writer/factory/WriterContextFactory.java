package com.spectra.result.transporter.context.writer.factory;

//import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

//import org.apache.logging.log4j.LogManager;

/**
 * Factory class to return an instance of the required processor class.
 */
@Slf4j
public final class WriterContextFactory implements ApplicationContextAware {
	//private Logger log = Logger.getLogger(WriterContextFactory.class);
	
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
	
	/**
	 * Instance variable for DAO factory.
	 */
	private static WriterContextFactory ctxFactory = null;

	/**
	 * Default constructor.This is to restrict instantiation of this class.
	 */
	private WriterContextFactory() {

	}

	/**
	 * Returns an instance of dao factory.
	 *
	 * @return DAOFactory Instance of dao factory.
	 */
	/*public static synchronized SpringDAOFactory getInstance() {
		if (daoFactory == null) {
			daoFactory = new SpringDAOFactory();
		}
		return daoFactory;
	}*/

	/**
	 * This method returns the dao instance based on the dao name set on the
	 * client side.
	 *
	 * @param daoName
	 *            Holds the action string.
	 * @return Object Returns an instance of a dao class depending on the dao
	 *         name.
	 */
	/*public static Object getDAOImpl(final String daoName) {
		return SpringDAOEnum.getDao(daoName);
	}*/
	
	public Object getCtxImpl(final String ctxName){
		log.debug("getCtxImpl(): ctxName: " + (ctxName == null ? "NULL" : ctxName));
		Object ctx = null;
		for (WriterContextEnum ctxEnum : WriterContextEnum.values()) {
			//log.debug("getDAOImpl(): daoEnum.getDaoName(): " + (daoEnum.getDaoName() == null ? "NULL" : daoEnum.getDaoName()));
			if (ctxEnum.getCtxName().equalsIgnoreCase(ctxName)) {
				ctx = this.context.getBean(ctxEnum.getCtxName());
				//log.debug("getDAOImpl(): daoName: " + (daoName == null ? "NULL" : daoName));
				//log.debug("getDAOImpl(): daoEnum.getDaoName(): " + (daoEnum.getDaoName() == null ? "NULL" : daoEnum.getDaoName()));
				//log.debug("getDAOImpl(): ctx: " + (ctx == null ? "NULL" : ctx.toString()));
				break;
			}
		}
		return ctx;
	}
	
	public Object getContextCtxImpl(final String ctxName){
		log.debug("getContextCtxImpl(): ctxName: " + (ctxName == null ? "NULL" : ctxName));
		Object ctx = null;
		for (WriterContextEnum ctxEnum : WriterContextEnum.values()) {
			if (ctxEnum.getCtxName().equalsIgnoreCase(ctxName)) {
				StringBuilder ctxNameBuilder = new StringBuilder();
				ctxNameBuilder.append(ctxEnum.getCtxName()).append("Proxy");
				//ctx = this.context.getBean(ctxEnum.getDaoName());
				ctx = this.context.getBean(ctxNameBuilder.toString());
				//log.debug("getContextDAOImpl(): ctxName: " + (ctxName == null ? "NULL" : ctxName));
				//log.debug("getContextDAOImpl(): ctxEnum.getDaoName(): " + (ctxEnum.getDaoName() == null ? "NULL" : ctxEnum.getDaoName()));
				//log.debug("getContextDAOImpl(): ctx: " + (ctx == null ? "NULL" : ctx.toString()));
				//log.debug("getContextDAOImpl(): ctxNameBuilder: " + (ctxNameBuilder == null ? "NULL" : ctxNameBuilder.toString()));
				break;
			}
		}
		return ctx;
	}
}
