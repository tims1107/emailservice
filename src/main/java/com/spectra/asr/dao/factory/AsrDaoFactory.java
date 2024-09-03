/* ===================================================================*/
/* � 2015 Fresenius Medical Care Holdings, Inc. All rights reserved.     */
/* ===================================================================*/
package com.spectra.asr.dao.factory;

/**
 * Factory class to return an instance of the required processor class.
 */
public final class AsrDaoFactory {

	/**
	 * Instance variable for DAO factory.
	 */
	private static AsrDaoFactory daoFactory = null;

	/**
	 * Default constructor.This is to restrict instantiation of this class.
	 */
	private AsrDaoFactory() {

	}

	/**
	 * Returns an instance of dao factory.
	 *
	 * @return DAOFactory Instance of dao factory.
	 */
	public static synchronized AsrDaoFactory getInstance() {
		if (daoFactory == null) {
			daoFactory = new AsrDaoFactory();
		}
		return daoFactory;
	}

	/**
	 * This method returns the dao instance based on the dao name set on the
	 * client side.
	 *
	 * @param daoName
	 *            Holds the action string.
	 * @return Object Returns an instance of a dao class depending on the dao
	 *         name.
	 */
	public static Object getDAOImpl(final String daoName) {

		return AsrDaoEnum.getDao(daoName);
	}
}
