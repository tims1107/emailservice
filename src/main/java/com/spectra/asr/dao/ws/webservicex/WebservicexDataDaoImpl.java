package com.spectra.asr.dao.ws.webservicex;

import com.spectra.asr.dto.ws.webservicex.WebservicexDataContainerDto;
import com.spectra.asr.jdo.castor.CastorException;
import com.spectra.asr.jdo.castor.CastorUtils;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

//import com.spectra.framework.service.config.ConfigService;
//import com.spectra.scorpion.framework.jdo.castor.CastorException;
//import com.spectra.scorpion.framework.jdo.castor.CastorUtils;

@Slf4j
public class WebservicexDataDaoImpl implements WebservicexDataDao {
	//private Logger log = Logger.getLogger(WebservicexDataDaoImpl.class);
/*	
	private CastorUtils castorUtils;
	
	private ConfigService configService;
	
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public CastorUtils getCastorUtils() {
		return castorUtils;
	}

	public void setCastorUtils(CastorUtils castorUtils) {
		this.castorUtils = castorUtils;
	}
*/	
	@Override
	public WebservicexDataContainerDto getWebservicexDataContainerFromZip(String zip) {
		WebservicexDataContainerDto containerDto = null;
		if(zip != null){
			String mappingPath = ApplicationProperties.getProperty("castor.ws.ex.mapping");
			//if((this.configService != null) && (this.castorUtils != null)){
			if((mappingPath != null)){
				CastorUtils castorUtils = new CastorUtils();
				castorUtils.setMappingPath(mappingPath);
				String xml = callWebservicexService(zip);
				StringBuilder xmlBuilder = new StringBuilder();
				if(xml != null){
					int idxOpenTag = xml.indexOf("<NewDataSet>");
					int idxCloseTag = xml.indexOf("</NewDataSet>");
					if(idxOpenTag != -1){
						xmlBuilder.append("<NewDataSet>");
					}
					if(idxCloseTag != -1){
						xmlBuilder.append("<Tables>");
						String tables = xml.substring((idxOpenTag + "<NewDataSet>".length()), idxCloseTag);
						xmlBuilder.append(tables);
						xmlBuilder.append("</Tables>");
					}
					if(idxCloseTag != -1){
						xmlBuilder.append("</NewDataSet>");
					}
					xml = xmlBuilder.toString();
					log.warn("getWebservicexDataContainerFromZip(): xml: " + (xml == null ? "NULL" : xml));
					if((xml != null) && (xml.length() > 0)){
						if(castorUtils != null){
							InputStream is = new ByteArrayInputStream(xml.trim().getBytes());
							try{
								containerDto = (WebservicexDataContainerDto)castorUtils.unmarshal(is);
								/*
								List<ProviderDto> pdtoList = dto.getProviderList();
								if((pdtoList != null) && (pdtoList.size() > 0)){
									for(ProviderDto pdto : pdtoList){
										if((pdto.getFirstName() != null) && (pdto.getLastName() != null)){
											StringBuilder fnBuilder = new StringBuilder();
											fnBuilder.append(pdto.getFirstName()).append(" ").append(pdto.getLastName());
											pdto.setFullName(fnBuilder.toString());
										}
									}
								}
								*/
							}catch(CastorException ce){
								log.error(String.valueOf(ce));
								ce.printStackTrace();
					        }finally{
					        	if(is != null){
					        		try{
					        			is.close();
					        		}catch(IOException ioe){
										log.error(String.valueOf(ioe));
										ioe.printStackTrace();
					        		}
					        	}
					        }					
						}
					}
				}//if
			}//if
		}
		return containerDto;
	}

	String callWebservicexService(String zip){
		String xml = null;
		if(zip != null){
			StringBuilder xmlBuilder = new StringBuilder();
			try {
				String wsUrl = null;
				//String wsAuth = null;
				//String wsContentType = null;
				//if(this.configService != null){
				if(ApplicationProperties.getProperty("ws.ex.url") != null){
					//wsUrl = this.configService.getString("ws.ex.url");
					wsUrl = ApplicationProperties.getProperty("ws.ex.url");
					//wsAuth = this.configService.getString("corp.provider.ws.auth");
					//wsContentType = this.configService.getString("corp.provider.ws.content.type");
				}
				wsUrl = MessageFormat.format(wsUrl, new String[]{ zip, }); 
				//StringBuilder urlBuilder = new StringBuilder(wsUrl);
				//urlBuilder.append(npi);

				//URL url = new URL(urlBuilder.toString());
				URL url = new URL(wsUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				//conn.setRequestProperty("Accept", "text/plain");
				//conn.setRequestProperty(HttpHeaders.ACCEPT, "text/xml");
				//conn.setRequestProperty(HttpHeaders.ACCEPT, "application/x-www-form-urlencoded");
				//conn.setRequestProperty("Accept", "application/json");
				//conn.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer 93706fd8b222b3e6b596963bdaf5a73");
				//conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
				
				//conn.setRequestProperty(HttpHeaders.AUTHORIZATION, wsAuth);
				//conn.setRequestProperty(HttpHeaders.CONTENT_TYPE, wsContentType);				
				  
				if (conn.getResponseCode() != 200) {
					//throw new RuntimeException("callPrvdrwsService(): Failed : HTTP error code : " + conn.getResponseCode());
					conn.disconnect();
					return null;
				}
				 
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				 
				String output = null;
				log.warn("callPrvdrwsService(): Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					//log.warn(output);
					xmlBuilder.append(output).append("\n");
				}
				xml = xmlBuilder.toString();
				
				conn.disconnect();
				br.close();
			} catch (MalformedURLException e) {
				log.error(e.getMessage());
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				log.error(e.getMessage());
				e.printStackTrace();
				return null;
			}catch(Exception e){
				log.error(e.getMessage());
				e.printStackTrace();
				return null;
			}
		}
		return xml;
	}
}
