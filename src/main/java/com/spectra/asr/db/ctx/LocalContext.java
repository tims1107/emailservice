package com.spectra.asr.db.ctx;

import com.spectra.asr.db.ds.LocalDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.naming.spi.InitialContextFactoryBuilder;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class LocalContext extends InitialContext implements InitialContextFactoryBuilder, InitialContextFactory {
	//private Logger log = Logger.getLogger(LocalContext.class);
			
	Map<Object,Object> dataSources;
	
	LocalContext() throws NamingException {
		super();
		dataSources = new HashMap<Object,Object>();
	}
	
	public void addDataSource(String name, String connectionString, String username, String password) {
		this.dataSources.put(name, new LocalDataSource(connectionString,username,password));
	}

	public InitialContextFactory createInitialContextFactory(Hashtable<?, ?> hsh) throws NamingException {
		dataSources.putAll(hsh);
		return this;
	}

	public Context getInitialContext(Hashtable<?, ?> arg0) throws NamingException {
		return this;
	}

	@Override
	public Object lookup(String name) throws NamingException {
		Object ret = null;
		if(name != null){
			if(dataSources != null){
				log.warn("lookup(): dataSources: " + (dataSources == null ? "NULL" : dataSources.toString()));
				ret = dataSources.get(name);
				//return (ret != null) ? ret : super.lookup(name);
			}
		}
		return ret;
	}

}
