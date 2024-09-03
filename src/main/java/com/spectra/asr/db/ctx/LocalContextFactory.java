package com.spectra.asr.db.ctx;

import lombok.extern.slf4j.Slf4j;

import javax.naming.spi.NamingManager;
//import org.apache.logging.log4j.LogManager;

@Slf4j
public class LocalContextFactory {
	//private static Logger log = Logger.getLogger(LocalContextFactory.class);
	
	/**
	 * do not instantiate this class directly. Use the factory method.
	 */
	//private LocalContextFactory() {}
	private LocalContextFactory() {}
	
	public static LocalContext createLocalContext(String databaseDriver) throws SimpleException {

		try { 
			LocalContext ctx = new LocalContext();
			Class.forName(databaseDriver);	
			NamingManager.setInitialContextFactoryBuilder(ctx); 			
			return ctx;
		}
		catch(Exception e) {
			throw new SimpleException("Error Initializing Context: " + e.getMessage(),e);
		}
	}

}
