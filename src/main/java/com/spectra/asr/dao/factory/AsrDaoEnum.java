package com.spectra.asr.dao.factory;

import com.spectra.asr.dev.service.factory.LoadServiceEnum;
import com.spectra.scorpion.dataaccess.util.DAOEnum;
import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.logging.ApplicationRootLogger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.spectra.asr.dao.ora.hub.AsrDao;
import com.spectra.asr.dao.ora.hub.AsrDaoImpl;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDao;
import com.spectra.asr.dao.ora.hub.generator.GeneratorDaoImpl;
import com.spectra.asr.dao.ws.webservicex.WebservicexDataDao;
import com.spectra.asr.dao.ws.webservicex.WebservicexDataDaoImpl;
import com.spectra.asr.dao.ora.hub.distributor.DistributorDao;
import com.spectra.asr.dao.ora.hub.distributor.DistributorDaoImpl;
import com.spectra.asr.dao.ora.hub.audit.AsrAuditDao;
import com.spectra.asr.dao.ora.hub.audit.AsrAuditDaoImpl;
import com.spectra.asr.dao.ora.hub.condition.ConditionDao;
import com.spectra.asr.dao.ora.hub.condition.ConditionDaoImpl;
import com.spectra.asr.dao.ora.hub.log.ResultsSentLogDao;
import com.spectra.asr.dao.ora.hub.log.ResultsSentLogDaoImpl;
import com.spectra.asr.dao.ora.hub.portal.PortalDao;
import com.spectra.asr.dao.ora.hub.portal.PortalDaoImpl;
import com.spectra.asr.dao.ora.hub.demographic.DemographicDao;
import com.spectra.asr.dao.ora.hub.demographic.DemographicDaoImpl;
import com.spectra.asr.dao.ms.cm.CMDao;
import com.spectra.asr.dao.ms.cm.CMDaoImpl;
import com.spectra.asr.dao.ora.hub.condition.MicroConditionDao;
import com.spectra.asr.dao.ora.hub.condition.MicroConditionDaoImpl;

/**
 * Enumeration class to hold the JDBC DAO implementation name as key and the
 * fully qualified path as the value.
 */
public enum AsrDaoEnum {

	/**
	 * Constants for the individual DAO implementation.
	 */
	AsrDao(AsrDao.class.getSimpleName(), AsrDaoImpl.class.getName()),
	GeneratorDao(GeneratorDao.class.getSimpleName(), GeneratorDaoImpl.class.getName()),
	WebservicexDataDao(WebservicexDataDao.class.getSimpleName(), WebservicexDataDaoImpl.class.getName()),
	DistributorDao(DistributorDao.class.getSimpleName(), DistributorDaoImpl.class.getName()),
	AsrAuditDao(AsrAuditDao.class.getSimpleName(), AsrAuditDaoImpl.class.getName()),
	ConditionDao(ConditionDao.class.getSimpleName(), ConditionDaoImpl.class.getName()),
	ResultsSentLogDao(ResultsSentLogDao.class.getSimpleName(), ResultsSentLogDaoImpl.class.getName()),
	PortalDao(PortalDao.class.getSimpleName(), PortalDaoImpl.class.getName()),
	DemographicDao(DemographicDao.class.getSimpleName(), DemographicDaoImpl.class.getName()),
	CMDao(CMDao.class.getSimpleName(), CMDaoImpl.class.getName()),
	MicroConditionDao(MicroConditionDao.class.getSimpleName(), MicroConditionDaoImpl.class.getName());
/*
	PatientDAO("PatientDAO", "com.spectra.symfonielabs.dao.PatientDAOImpl"),
	FacilityDAO("FacilityDAO", "com.spectra.symfonielabs.dao.FacilityDAOImpl"),
	OrderDAO("OrderDAO", "com.spectra.symfonielabs.dao.OrderDAOImpl"),
	OrderSourceDAO("OrderSourceDAO", "com.spectra.symfonielabs.dao.OrderSourceDAOImpl"),
	StaffDAO("StaffDAO", "com.spectra.symfonielabs.dao.StaffDAOImpl"),
	OrderStaffDAO("OrderStaffDAO",
			"com.spectra.symfonielabs.dao.OrderStaffDAOImpl"),
	EquipmentDAO("EquipmentDAO",
			"com.spectra.symfonielabs.dao.EquipmentDAOImpl"),
    SymfonieUserDAO("SymfonieUserDAO",
    "com.spectra.symfonie.usermanagement.dao.SymfonieUserDAOImpl");
*/
             
	/**
	 * Holds the name of a particular DAO.
	 */
	private String daoName;

	/**
	 * Holds the dao implementation object.
	 */
	private Object dao;

	/**
    * Logger for this class.
    */
   private final Logger LOGGER = ApplicationRootLogger
           .getLogger(AsrDaoEnum.class.getName());
	/**
	 * Overloaded constructor.
	 * 
	 * @param daoName
	 *            Holds the name of a particular DAO.
	 * @param fullyQualifiedPath
	 *            Holds the fully qualified path of the DAO.
	 */
	AsrDaoEnum(final String daoName, final String fullyQualifiedPath) {
		this.daoName = daoName;
		try {
			this.dao = Class.forName(fullyQualifiedPath).newInstance();
		} catch (InstantiationException instantiationException) {
			final StringWriter stringWriter = new StringWriter();
			instantiationException.printStackTrace(
                     new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE,
					ExceptionConstants.APPLICATION_EXCEPTION + "\n"
							+ ExceptionConstants.EXCEPTION_STACK_TRACE
							+ stringWriter.toString());
		} catch (IllegalAccessException illegalAccessException) {
			final StringWriter stringWriter = new StringWriter();
			illegalAccessException.printStackTrace(
                     new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE,
					ExceptionConstants.APPLICATION_EXCEPTION + "\n"
							+ ExceptionConstants.EXCEPTION_STACK_TRACE
							+ stringWriter.toString());
		} catch (ClassNotFoundException classNotFoundException) {
			final StringWriter stringWriter = new StringWriter();
			classNotFoundException.printStackTrace(
                     new PrintWriter(stringWriter));
			LOGGER.log(Level.SEVERE,
					ExceptionConstants.APPLICATION_EXCEPTION + "\n"
							+ ExceptionConstants.EXCEPTION_STACK_TRACE
							+ stringWriter.toString());
		}
		// Returns the instance of the dao.

	}

	/**
	 * Gets the fully qualified path of the DAO.
	 * 
	 * @param daoName
	 *            Holds the name of a particular DAO.
	 * @return String the fully qualified path of the DAO.
	 */
	public static Object getDao(final String daoName) {
		AsrDaoEnum object = Arrays.asList(AsrDaoEnum.values())
				.stream()
				.filter(i -> i.daoName.equalsIgnoreCase(daoName))
				.findAny().get();
//		for (AsrDaoEnum daoEnum : AsrDaoEnum.values()) {
//			if (daoEnum.daoName.equalsIgnoreCase(daoName)) {
//				object = daoEnum.dao;
//				break;
//			}
//		}
		return object.dao;
	}

}
