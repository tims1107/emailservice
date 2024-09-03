package com.spectra.result.transporter.context.writer.factory;

import java.util.HashMap;
import java.util.Map;

import com.spectra.scorpion.framework.constants.ExceptionConstants;
import com.spectra.scorpion.framework.exception.ApplicationException;

/*
import com.spectra.result.transporter.dao.ora.rr.ResultRepositoryDao;
import com.spectra.result.transporter.dao.ora.rr.ResultRepositoryDaoImpl;

import com.spectra.result.transporter.dao.netlims.NetlimsFileDao;
import com.spectra.result.transporter.dao.netlims.NetlimsFileDaoImpl;
*/

import com.spectra.result.transporter.context.writer.WriterContextImpl;
import com.spectra.result.transporter.context.writer.poi.PoiWriterContextImpl;

/**
 * Enumeration class to hold the WriterContext implementation name as key and the
 * fully qualified path as the value.
 */
public enum WriterContextEnum {

	/**
	 * Constants for the individual Context implementation.
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
	/*
	ResultRepositoryDao(ResultRepositoryDao.class.getSimpleName(),ResultRepositoryDaoImpl.class.getName()),
	NetlimsFileDao(NetlimsFileDao.class.getSimpleName(),NetlimsFileDaoImpl.class.getName());
	*/
	
	FileWriterContext("FileWriterContext",WriterContextImpl.class.getName()),
	SambaFileWriterContext("SambaFileWriterContext",WriterContextImpl.class.getName()),
	PoiFileWriterContext("PoiFileWriterContext",PoiWriterContextImpl.class.getName()),
	EipPoiFileWriterContext("EipPoiFileWriterContext",PoiWriterContextImpl.class.getName());
	
	/**
	 * Holds the name of a particular Context.
	 */
	private String ctxName;

	/**
	 * Holds the ctx implementation object.
	 */
	private Object ctx;

	public String getCtxName(){
		return ctxName;
	}
	
	/**
	 * Overloaded constructor.
	 * 
	 * @param ctxName
	 *            Holds the name of a particular Context.
	 * @param fullyQualifiedPath
	 *            Holds the fully qualified path of the Context.
	 */
	WriterContextEnum(final String ctxName, final String fullyQualifiedPath) {
		this.ctxName = ctxName;
		try {
			this.ctx = Class.forName(fullyQualifiedPath).newInstance();
		} catch (InstantiationException instantiationException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("ctxName", ctxName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					instantiationException, hashMap);
		} catch (IllegalAccessException illegalAccessException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("ctxName", ctxName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					illegalAccessException, hashMap);
		} catch (ClassNotFoundException classNotFoundException) {
			// Construct a HashMap holding parameters of this method and pass it
			// to ApplicationException for logging purpose.
			Map<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("ctxName", ctxName);
			hashMap.put("fullyQualifiedPath", fullyQualifiedPath);
			throw new ApplicationException(ExceptionConstants.SYSTEM_ERROR,
					classNotFoundException, hashMap);
		}
		// Returns the instance of the ctx.

	}

	/**
	 * Gets the fully qualified path of the DAO.
	 * 
	 * @param ctxName
	 *            Holds the name of a particular DAO.
	 * @return String the fully qualified path of the DAO.
	 */
	public static Object getCtx(final String ctxName) {
		Object object = null;
		for (WriterContextEnum ctxEnum : WriterContextEnum.values()) {
			if (ctxEnum.ctxName.equalsIgnoreCase(ctxName)) {
				object = ctxEnum.ctx;
				break;
			}
		}
		return object;
	}

}
