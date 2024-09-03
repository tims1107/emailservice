package com.spectra.result.transporter.utils.props;

import com.spectra.framework.logic.ConditionChecker;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

//import com.spectra.utils.db.IDbUtil;
//import com.spectra.utils.math.MathParser;
@Slf4j
public class PropertiesUtil implements IPropertiesUtil{
	//private logger log = logger.getlogger(PropertiesUtil.class);
	
	private String propFileName;
	private String propClassName;
	
	public String getPropClassName() {
		return propClassName;
	}
	public void setPropClassName(String propClassName) {
		this.propClassName = propClassName;
	}
	public String getPropFileName() {
		return propFileName;
	}
	public void setPropFileName(String propFileName) {
		this.propFileName = propFileName;
	}

	public Properties getProperties() throws IllegalArgumentException{
/*		//if(args.length != 2){
		if(args.length < 2){
			throw new IllegalArgumentException("Expecting <dbname> and <filename.properties>");
		}
		String dbname = args[0];
		String propFileName = args[1];
*/		
		Properties props = null;
		if(this.propFileName != null){
			props = new Properties();
			File propFile = new File(this.propFileName);
			try{
				if(propFile.exists()){
					props.load(new FileInputStream(propFile));
				}else{
					props.load(PropertiesUtil.class.getResourceAsStream(this.propFileName));
				}				
			}catch(FileNotFoundException fnfe){
				log.error(fnfe.getMessage());
				fnfe.printStackTrace();
				System.exit(0);
			}catch(IOException ioe){
				log.error(ioe.getMessage());
				ioe.printStackTrace();
				System.exit(0);
			}
		}
		return props;
	}
	
	public Properties getXmlProperties(){
/*
		//if(args.length != 2){
		if(args.length < 2){
			throw new IllegalArgumentException("Expecting <dbname> and <filename.xml>");
		}
		String dbname = args[0];
		String propFileName = args[1];
*/		
		Properties props = null;
		if(this.propFileName != null){
			props = new Properties();
			File propFile = new File(this.propFileName);
			try{
				if(propFile.exists()){
					props.loadFromXML(new FileInputStream(propFile));
				}else{
					props.loadFromXML(PropertiesUtil.class.getResourceAsStream(this.propFileName));
				}				
			}catch(FileNotFoundException fnfe){
				log.error(fnfe.getMessage());
				fnfe.printStackTrace();
				System.exit(0);
			}catch(IOException ioe){
				log.error(ioe.getMessage());
				ioe.printStackTrace();
				System.exit(0);
			}
		}
		return props;
	}
    
	public Object loadBeanProperties(){
		Object propBean = null;
		if(ConditionChecker.checkValidString(this.propFileName) && ConditionChecker.checkValidString(this.propClassName)){
			try{
				//Class<InterfaceProperties> propClass = (Class<InterfaceProperties>)Class.forName(this.propClassName);
				Class propClass = Class.forName(this.propClassName);
				if(ConditionChecker.checkNotNull(propClass)){
					propBean = propClass.newInstance();
					if(ConditionChecker.checkNotNull(propBean)){
						this.loadBeanProperties(propBean);
					}
				}
			}catch(ClassNotFoundException cnfe){
    			log.error(cnfe.getMessage());
    			cnfe.printStackTrace();
    			System.exit(0);
    		}catch(IllegalAccessException iae){
    			log.error(iae.getMessage());
    			iae.printStackTrace();
    			System.exit(0);
    		}catch(InstantiationException ie){
    			log.error(ie.getMessage());
    			ie.printStackTrace();
    			System.exit(0);
    		}
		}
		return propBean;
	}
	
    public void loadBeanProperties(Object propBean){
    	Properties props = this.getProperties();

    	List<Class> clazzList = getSuperClasses(propBean);
    	log.debug("loadBeanProperties(): clazzList: " + clazzList.toString());
    	for(Class clazz : clazzList){
        	Field[] clazzFields = clazz.getDeclaredFields();
        	for(Field field : clazzFields){
        		String fieldName = field.getName();
        		//log.debug("loadBeanProperties(): field: " + fieldName);
        		StringBuilder setterBuilder = new StringBuilder();
        		setterBuilder.append("set").append(Character.toUpperCase(fieldName.charAt(0))).append(fieldName.substring(1));
        		//log.debug("loadBeanProperties(): setterBuilder: " + setterBuilder.toString());
        		Class[] types = null;
        		Method setterMethod = null;
        		try{
            		types = new Class[]{ String.class, };   			
        			setterMethod = clazz.getMethod(setterBuilder.toString(), types);
        			String prop = props.getProperty(fieldName);
        			if(prop == null){
        				prop = new String();
        			}
        			setterMethod.invoke(propBean, new Object[]{ prop, });
/*
        			if(fieldName.equals(PROPERTYS)){
                		types = new Class[]{ DBParams.class, };
            			setterMethod = clazz.getMethod(setterBuilder.toString(), types);
            			if(propertys == null){
            				propertys = new DBParams();
            			}
            			setterMethod.invoke(propBean, new Object[]{ propertys, });
            		}else{
                		types = new Class[]{ String.class, };   			
            			setterMethod = clazz.getMethod(setterBuilder.toString(), types);
            			String prop = props.getProperty(fieldName);
            			if(prop == null){
            				prop = new String();
            			}
            			//setterMethod.invoke(propBean, new Object[]{ props.getProperty(fieldName), });
            			setterMethod.invoke(propBean, new Object[]{ prop, });
            		}
*/            		
        		}catch(NoSuchMethodException nsme){
        			log.error(nsme.getMessage());
        			nsme.printStackTrace();
        			System.exit(0);
        		}catch(IllegalAccessException iae){
        			log.error(iae.getMessage());
        			iae.printStackTrace();
        			System.exit(0);
        		}catch(InvocationTargetException ite){
        			log.error(ite.getMessage());
        			ite.printStackTrace();
        			System.exit(0);
        		}
        	}    		
    	}
    }
    
    List<Class> getSuperClasses(Object obj) {
    	List<Class> clazzList = new ArrayList<Class>();
		Class clazz = obj.getClass();
		clazzList.add(clazz);
		Class superclazz = clazz.getSuperclass();
		while(superclazz != null){
			clazzList.add(superclazz);
			superclazz = superclazz.getSuperclass();
		}
    	return clazzList;
	}
}
