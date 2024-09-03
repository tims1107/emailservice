package com.spectra.result.transporter.dataaccess.spring.ora.rr;

import java.util.HashMap;
import java.util.Map;

import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.exception.ApplicationException;

import com.spectra.result.transporter.dao.ora.rr.ResultRepositoryDao;
import com.spectra.result.transporter.dao.ora.rr.ResultRepositoryDaoImpl;

import com.spectra.result.transporter.dao.netlims.NetlimsFileDao;
import com.spectra.result.transporter.dao.netlims.NetlimsFileDaoImpl;

import com.spectra.result.transporter.dao.ws.webservicex.WebservicexDataDao;
import com.spectra.result.transporter.dao.ws.webservicex.WebservicexDataDaoImpl;

/**
 * Enumeration class to hold the JDBC DAO implementation name as key and the
 * fully qualified path as the value.
 */
public enum RepositoryDAOEnum {

	/**
	 * Constants for the individual DAO implementation.
	 */

	/*
	PlacEastProviderDao(PlacEastProviderDao.class.getSimpleName(),PlacEastProviderDaoImpl.class.getName()),
	PlacWestProviderDao(PlacWestProviderDao.class.getSimpleName(),PlacWestProviderDaoImpl.class.getName()),
	CorpProviderWsDao(CorpProviderWsDao.class.getSimpleName(),CorpProviderWsDaoImpl.class.getName()),
	NationalPlanProviderEnumerationSystemDao(NationalPlanProviderEnumerationSystemDao.class.getSimpleName(),NationalPlanProviderEnumerationSystemDaoImpl.class.getName()),
	RRProviderDao(RRProviderDao.class.getSimpleName(),RRProviderDaoImpl.class.getName()),
	CMProviderDao(CMProviderDao.class.getSimpleName(),CMProviderDaoImpl.class.getName()),
	MDMProviderDao(MDMProviderDao.class.getSimpleName(),MDMProviderDaoImpl.class.getName());
	*/
	ResultRepositoryDao(ResultRepositoryDao.class.getSimpleName(),ResultRepositoryDaoImpl.class.getName()),
	NetlimsFileDao(NetlimsFileDao.class.getSimpleName(),NetlimsFileDaoImpl.class.getName()),
	WebservicexDataDao(WebservicexDataDao.class.getSimpleName(),WebservicexDataDaoImpl.class.getName());
	
	/**
	 * Holds the name of a particular DAO.
	 */
	private String daoName;

	/**
	 * Holds the dao implementation object.
	 */
	private Object dao;

	public String getDaoName(){
		return daoName;
	}
	
	/**
	 * Overloaded constructor.
	 * 
	 * @param daoName
	 *            Holds the name of a particular DAO.
	 * @param fullyQualifiedPath
	 *            Holds the fully qualified path of the DAO.
	 */
	RepositoryDAOEnum(final String daoName, final String fullyQualifiedPath) {
		this.daoName = daoName;
		try {
			this.dao = Class.forName(fullyQualifiedPath).newInstance();
		} catch (InstantiationException instantiationException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("daoName", daoName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					instantiationException, hashMap);
		} catch (IllegalAccessException illegalAccessException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("daoName", daoName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					illegalAccessException, hashMap);
		} catch (ClassNotFoundException classNotFoundException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("daoName", daoName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					classNotFoundException, hashMap);
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
		Object object = null;
		for (RepositoryDAOEnum daoEnum : RepositoryDAOEnum.values()) {
			if (daoEnum.daoName.equalsIgnoreCase(daoName)) {
				object = daoEnum.dao;
				break;
			}
		}
		return object;
	}

}
