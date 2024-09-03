package com.spectra.result.transporter.dataaccess.spring.ora.rr;

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
public final class RepositoryDAOFactory implements ApplicationContextAware {
	///private Logger log = Logger.getLogger(RepositoryDAOFactory.class);
	
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
	private static RepositoryDAOFactory daoFactory = null;

	/**
	 * Default constructor.This is to restrict instantiation of this class.
	 */
	private RepositoryDAOFactory() {

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
	
	public Object getDAOImpl(final String daoName){
		log.debug("getDAOImpl(): daoName: " + (daoName == null ? "NULL" : daoName));
		Object dao = null;
		for (RepositoryDAOEnum daoEnum : RepositoryDAOEnum.values()) {
			//log.debug("getDAOImpl(): daoEnum.getDaoName(): " + (daoEnum.getDaoName() == null ? "NULL" : daoEnum.getDaoName()));
			if (daoEnum.getDaoName().equalsIgnoreCase(daoName)) {
				dao = this.context.getBean(daoEnum.getDaoName());
				//log.debug("getDAOImpl(): daoName: " + (daoName == null ? "NULL" : daoName));
				//log.debug("getDAOImpl(): daoEnum.getDaoName(): " + (daoEnum.getDaoName() == null ? "NULL" : daoEnum.getDaoName()));
				//log.debug("getDAOImpl(): dao: " + (dao == null ? "NULL" : dao.toString()));
				break;
			}
		}
		return dao;
	}
	
	public Object getContextDAOImpl(final String daoName){
		log.debug("getDAOImpl(): daoName: " + (daoName == null ? "NULL" : daoName));
		Object dao = null;
		for (RepositoryDAOEnum daoEnum : RepositoryDAOEnum.values()) {
			if (daoEnum.getDaoName().equalsIgnoreCase(daoName)) {
				StringBuilder daoNameBuilder = new StringBuilder();
				daoNameBuilder.append(daoEnum.getDaoName()).append("Proxy");
				//dao = this.context.getBean(daoEnum.getDaoName());
				dao = this.context.getBean(daoNameBuilder.toString());
				//log.debug("getContextDAOImpl(): daoName: " + (daoName == null ? "NULL" : daoName));
				//log.debug("getContextDAOImpl(): daoEnum.getDaoName(): " + (daoEnum.getDaoName() == null ? "NULL" : daoEnum.getDaoName()));
				//log.debug("getContextDAOImpl(): dao: " + (dao == null ? "NULL" : dao.toString()));
				//log.debug("getContextDAOImpl(): daoNameBuilder: " + (daoNameBuilder == null ? "NULL" : daoNameBuilder.toString()));
				break;
			}
		}
		return dao;
	}
}
