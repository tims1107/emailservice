package com.spectra.result.transporter.context.writer.strategy.file.samba;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.writer.strategy.WriterStrategy;
import com.spectra.result.transporter.service.spring.ResultSchedulerServiceFactory;
import com.spectra.result.transporter.service.spring.executor.WinCmdExecutorService;
import com.spectra.result.transporter.utils.file.writer.RTFileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class SambaFileWriterStrategy implements WriterStrategy<String> {
	//private Logger log = Logger.getLogger(SambaFileWriterStrategy.class);
	
	protected ConfigService configService;
	protected ResultSchedulerServiceFactory resultSchedulerServiceFactory;
	
	public void setConfigService(ConfigService configService){
		this.configService = configService;
	}
	
	public void setServiceFactory(ResultSchedulerServiceFactory resultSchedulerServiceFactory){
		this.resultSchedulerServiceFactory = resultSchedulerServiceFactory;
	}
	
	@Override
	public Boolean write(String resultStr) {
		Boolean wrote = Boolean.FALSE;
		if(resultStr != null){
			wrote = this.write(null, resultStr);
		}
		return wrote;
	}

	@Override
	public Boolean write(String county, String resultStr) {
		Boolean wrote = Boolean.FALSE;
		if(resultStr != null){
			if((this.configService != null) && (this.resultSchedulerServiceFactory != null)){
				WinCmdExecutorService winCmdExecutorService = (WinCmdExecutorService)this.resultSchedulerServiceFactory.getContextService(WinCmdExecutorService.class.getSimpleName());
				if(winCmdExecutorService != null){
					try{
						String localFolder = this.configService.getString("folder.local");
						String dropFolder = this.configService.getString("folder.drop");
						String archiveFolder = this.configService.getString("folder.archive");
						String state = this.configService.getString("state.process");
						log.debug("write(): localFolder: " + (localFolder == null ? "NULL" : localFolder));
						log.debug("write(): dropFolder: " + (dropFolder == null ? "NULL" : dropFolder));
						log.debug("write(): archiveFolder: " + (archiveFolder == null ? "NULL" : archiveFolder));
						
						String ip = configService.getString("samba.ip");
						String user = configService.getString("samba.user");
						String pwd = configService.getString("samba.pwd");
						String runMode = configService.getString("samba.run.mode");
						String netStart = configService.getString("net.start");
						String netEnd = configService.getString("net.end");
						log.debug("testExec(): ip: " + (ip == null ? "NULL" : ip));
						log.debug("testExec(): user: " + (user == null ? "NULL" : user));
						log.debug("testExec(): pwd: " + (pwd == null ? "NULL" : pwd));
						log.debug("testExec(): runMode: " + (runMode == null ? "NULL" : runMode));
						log.debug("testExec(): netStart: " + (netStart == null ? "NULL" : netStart));
						log.debug("testExec(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
						
						if((runMode != null) && (runMode.equals("share"))){
							netStart = MessageFormat.format(netStart, new String[]{ ip, user, pwd, });
							log.debug("testExec(): netStart: " + (netStart == null ? "NULL" : netStart));
							winCmdExecutorService.exec(netStart);
						}
						
						DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
						Date date = new Date();
						String year  = dateFormat.format(date).substring(0, 4);
						String month = dateFormat.format(date).substring(4, 6);
						String dt = dateFormat.format(date);
						
						// local
						StringBuilder builder = new StringBuilder();
						//builder.append(parms.myArchPath).append(parms.myArchFile);
						//builder.append(parms.myArchPath).append(year).append("_").append(month).append("/");
						builder.append(localFolder);
						File localFile = new File(builder.toString());
						boolean created = false;
						if(!localFile.exists()){
							created = localFile.mkdirs();
							log.debug("writeHL7(): created: " + String.valueOf(created) + " " + localFile.toString());
						}
	
						String archFile = this.configService.getString("arch.file");
						//archFile = archFile.substring(0, (archFile.lastIndexOf(".") + 1));
						archFile = MessageFormat.format(archFile, new String[]{ state, dt, });
						
						if(county != null){
							StringBuilder fnBuilder = new StringBuilder();
							county = county.replaceAll("\\s", "");
							fnBuilder.append(county).append(".").append(archFile);
							archFile = fnBuilder.toString();
						}

						//builder.append(archFile).append(dt).append(".txt");
						builder.append(archFile);
						RTFileWriter.write(builder.toString(), resultStr);
						// end local
						
						//drop
						RTFileWriter.copyFile(localFolder, dropFolder, archFile);
						//end drop
						
						//archive
						StringBuilder archBuilder = new StringBuilder(archiveFolder);
						archBuilder.append(year).append(month).append("\\");
						//RTFileWriter.moveFileToDir(localFolder, archiveFolder, archFile);
						RTFileWriter.moveFileToDir(localFolder, archBuilder.toString(), archFile);
						//end archive
						
						if((runMode != null) && (runMode.equals("share"))){
							winCmdExecutorService.exec(netEnd);
						}
					}catch(ConfigException ce){
						log.error(ce.getMessage());
						ce.printStackTrace();
						wrote = Boolean.FALSE;
					}catch(IOException ioe){
						log.error(ioe.getMessage());
						ioe.printStackTrace();
						wrote = Boolean.FALSE;
					}catch(Exception e){
						log.error(e.getMessage());
						e.printStackTrace();
						wrote = Boolean.FALSE;
					}
				}

			}
		}
		return wrote;
	}

}
