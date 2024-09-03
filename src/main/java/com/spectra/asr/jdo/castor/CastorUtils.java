package com.spectra.asr.jdo.castor;

import com.spectra.asr.constants.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.xml.serialize.Method;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.DocumentHandler;

import java.io.*;
import java.net.URL;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public final class CastorUtils {
    //private static Logger log = Logger.getLogger(CastorUtils.class);
    
    private String mappingPath;
    
    public void setMappingPath(String mappingPath){
    	this.mappingPath = mappingPath;
    }
    
    public void marshal(Writer writer, String encoding, Object target) throws CastorException{
        URL mappingUrl = null;
        Mapping castorMapping = null;
        Marshaller marshaller = null;
    	if(mappingPath != null){
    		if((writer != null) && (target != null)){
    			try{
	                log.info("marshalWithCdataElements(): mappingPath = " + mappingPath);
	                mappingUrl = CastorUtils.class.getResource(mappingPath);
	                log.info("marshalWithCdataElements(): mappingUrl = " + (mappingUrl == null ? "NULL" : mappingUrl.getPath()));
	                castorMapping = new Mapping();
	                castorMapping.loadMapping(mappingUrl);
	                
	                CdataXmlSerializer serializer = new CdataXmlSerializer(writer, null);
	                DocumentHandler handler = serializer.asDocumentHandler();
	                		
	                marshaller = new Marshaller(handler);
	                marshaller.setMapping(castorMapping);
	                marshaller.marshal(target); 	                
    			}catch(IOException ioe){
    	            ioe.printStackTrace();
    	            throw new CastorException(ioe.toString());
    	        }catch(MappingException me){
    	            me.printStackTrace();
    	            throw new CastorException(me.toString());
    	        }catch(ValidationException ve){
    	            ve.printStackTrace();
    	            throw new CastorException(ve.toString());
    	        }catch(MarshalException e){
    	            e.printStackTrace();
    	            throw new CastorException(e.toString());
    	        }
    		}
    	}
    }
    
    public Object unmarshal(String xml) throws CastorException{
    	Object target = null;
    	if(mappingPath != null){
    		if(xml != null){
    			InputStream is = CastorUtils.class.getResourceAsStream(xml);
    			try{
	    			if(is != null){
	    				target = unmarshal(is);
	    			}
    			}finally{
    				if(is != null){
    					try{
    						is.close();
    					}catch(IOException ioe){
    						log.error(String.valueOf(ioe));
    						ioe.printStackTrace();
    						throw new CastorException(ioe);
    					}
    				}
    			}
    		}
    	}
    	return target;
    }
    
    public Object unmarshal(InputStream inputStream) throws CastorException{
        URL mappingUrl = null;
        Mapping castorMapping = null;
        Unmarshaller unmarshaller = null;
        Reader reader = null;
        Object target = null;
        if(mappingPath != null){
        	if(inputStream != null){
                try{
                    mappingUrl = CastorUtils.class.getResource(mappingPath);
                    castorMapping = new Mapping();
                    castorMapping.loadMapping(mappingUrl);
                    unmarshaller = new Unmarshaller(castorMapping);
                    //reader = new InputStreamReader(CastorUtils.class.getResourceAsStream(xml));
                    reader = new InputStreamReader(inputStream);
                    target = unmarshaller.unmarshal(reader);
                }catch(IOException ioe){
                    log.error(ioe.toString());
                    throw new CastorException(ioe.toString());
                }catch(MappingException me){
                    log.error(me.toString());
                    throw new CastorException(me.toString());
                }catch(ValidationException ve){
                    log.error(ve.toString());
                    throw new CastorException(ve.toString());
                }catch(MarshalException e){
                    log.error(e.toString());
                    throw new CastorException(e.toString());
                }        		
        	}
        }
        return target;
    } 
    
    
    public static void marshalWithCdataElements(String mappingPath, Writer writer, String[] cdataElements, String encoding, Object target) throws CastorException{
        URL mappingUrl = null;
        Mapping castorMapping = null;
        Marshaller marshaller = null;
        try{
            log.info("marshalWithCdataElements(): mappingPath = " + mappingPath);
            mappingUrl = CastorUtils.class.getResource(mappingPath);
            log.info("marshalWithCdataElements(): mappingUrl = " + (mappingUrl == null ? "NULL" : mappingUrl.getPath()));
            castorMapping = new Mapping();
            castorMapping.loadMapping(mappingUrl);
            String encode = (encoding == null ? ApplicationConstants.CHAR_ENCODING : encoding);
            OutputFormat format = new OutputFormat(Method.XML, encode, true);
            format.setCDataElements(cdataElements);
            format.setNonEscapingElements(cdataElements);
            XMLSerializer serializer = new XMLSerializer(writer, format);
            DocumentHandler handler = serializer.asDocumentHandler();
            marshaller = new Marshaller(handler);
            marshaller.setMapping(castorMapping);
            marshaller.marshal(target);            
        }catch(IOException ioe){
            ioe.printStackTrace();
            throw new CastorException(ioe.toString());
        }catch(MappingException me){
            me.printStackTrace();
            throw new CastorException(me.toString());
        }catch(ValidationException ve){
            ve.printStackTrace();
            throw new CastorException(ve.toString());
        }catch(MarshalException e){
            e.printStackTrace();
            throw new CastorException(e.toString());
        }
    }
    
    public static void marshalTarget(String mappingPath, Writer writer, String encoding, Object target) throws CastorException{
        URL mappingUrl = null;
        Mapping castorMapping = null;
        Marshaller marshaller = null;
        try{
            mappingUrl = CastorUtils.class.getResource(mappingPath);
            castorMapping = new Mapping();
            castorMapping.loadMapping(mappingUrl);
            String encode = (encoding == null ? ApplicationConstants.CHAR_ENCODING : encoding);
            marshaller = new Marshaller(writer);
            marshaller.setMapping(castorMapping);
            marshaller.setEncoding(encode);
            marshaller.marshal(target);
        }catch(IOException ioe){
            ioe.printStackTrace();
            throw new CastorException(ioe.toString());
        }catch(MappingException me){
            me.printStackTrace();
            throw new CastorException(me.toString());
        }catch(ValidationException ve){
            ve.printStackTrace();
            throw new CastorException(ve.toString());
        }catch(MarshalException e){
            e.printStackTrace();
            throw new CastorException(e.toString());
        }
    }
    
    public static Object unmarshalTarget(String mappingPath, String xml) throws CastorException{
        URL mappingUrl = null;
        Mapping castorMapping = null;
        Unmarshaller unmarshaller = null;
        Reader reader = null;
        Object target = null;
        try{
            mappingUrl = CastorUtils.class.getResource(mappingPath);
            castorMapping = new Mapping();
            castorMapping.loadMapping(mappingUrl);
            unmarshaller = new Unmarshaller(castorMapping);
            reader = new InputStreamReader(CastorUtils.class.getResourceAsStream(xml));
            target = unmarshaller.unmarshal(reader);
        }catch(IOException ioe){
            log.error(ioe.toString());
            throw new CastorException(ioe.toString());
        }catch(MappingException me){
            log.error(me.toString());
            throw new CastorException(me.toString());
        }catch(ValidationException ve){
            log.error(ve.toString());
            throw new CastorException(ve.toString());
        }catch(MarshalException e){
            log.error(e.toString());
            throw new CastorException(e.toString());
        }
        return target;
    }
    
    public static Object unmarshalTarget(String mappingPath, InputStream inputStream) throws CastorException{
        URL mappingUrl = null;
        Mapping castorMapping = null;
        Unmarshaller unmarshaller = null;
        Reader reader = null;
        Object target = null;
        try{
            mappingUrl = CastorUtils.class.getResource(mappingPath);
            castorMapping = new Mapping();
            castorMapping.loadMapping(mappingUrl);
            unmarshaller = new Unmarshaller(castorMapping);
            //reader = new InputStreamReader(CastorUtils.class.getResourceAsStream(xml));
            reader = new InputStreamReader(inputStream);
            target = unmarshaller.unmarshal(reader);
        }catch(IOException ioe){
            log.error(ioe.toString());
            throw new CastorException(ioe.toString());
        }catch(MappingException me){
            log.error(me.toString());
            throw new CastorException(me.toString());
        }catch(ValidationException ve){
            log.error(ve.toString());
            throw new CastorException(ve.toString());
        }catch(MarshalException e){
            log.error(e.toString());
            throw new CastorException(e.toString());
        }
        return target;
    }    
}

