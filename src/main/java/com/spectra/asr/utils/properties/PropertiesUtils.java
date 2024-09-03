package com.spectra.asr.utils.properties;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.Properties;
@Slf4j
public abstract class PropertiesUtils {
	//private static Logger log = Logger.getLogger(PropertiesUtils.class);
	
	public static Properties getProperties(String propFileName){
		Properties props = null;
		if(propFileName != null){
			props = new Properties();
			File propFile = new File(propFileName);
			InputStream is = null;
			try{
				if(propFile.exists()){
					is = new FileInputStream(propFile);
					props.load(is);
				}else{
					is = PropertiesUtils.class.getResourceAsStream(propFileName);
					props.load(PropertiesUtils.class.getResourceAsStream(propFileName));
				}				
			}catch(FileNotFoundException fnfe){
				log.error(String.valueOf(fnfe));
				fnfe.printStackTrace();
				System.exit(0);
			}catch(IOException ioe){
				log.error(String.valueOf(ioe));
				ioe.printStackTrace();
				System.exit(0);
			}finally{
				if(is != null){
					try{
						is.close();
					}catch(IOException ioe){
						log.error(String.valueOf(ioe));
						ioe.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
		return props;
	}
	
	public static Properties getXmlProperties(String xmlPropFileName){
		Properties props = null;
		if(xmlPropFileName != null){
			props = new Properties();
			File propFile = new File(xmlPropFileName);
			InputStream is = null;
			try{
				if(propFile.exists()){
					is = new FileInputStream(propFile);
					props.loadFromXML(is);
				}else{
					is = PropertiesUtils.class.getResourceAsStream(xmlPropFileName);
					props.loadFromXML(is);
				}				
			}catch(FileNotFoundException fnfe){
				log.error(String.valueOf(fnfe));
				fnfe.printStackTrace();
				System.exit(0);
			}catch(IOException ioe){
				log.error(String.valueOf(ioe));
				ioe.printStackTrace();
				System.exit(0);
			}finally{
				if(is != null){
					try{
						is.close();
					}catch(IOException ioe){
						log.error(String.valueOf(ioe));
						ioe.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
		return props;
	}
}
