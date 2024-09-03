package com.spectra.result.transporter.context.writer.strategy.file.poi;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.writer.strategy.PoiWriterStrategy;
import com.spectra.result.transporter.service.spring.ResultSchedulerServiceFactory;
import com.spectra.result.transporter.service.spring.executor.WinCmdExecutorService;
import com.spectra.result.transporter.utils.calendar.CalendarUtils;
import com.spectra.result.transporter.utils.file.writer.RTFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class PoiFileWriterStrategy implements PoiWriterStrategy {
	//private Logger log = Logger.getLogger(PoiFileWriterStrategy.class);
	
	protected ConfigService configService;
	protected ResultSchedulerServiceFactory resultSchedulerServiceFactory;
	
	public void setConfigService(ConfigService configService){
		this.configService = configService;
	}
	
	public void setServiceFactory(ResultSchedulerServiceFactory resultSchedulerServiceFactory){
		this.resultSchedulerServiceFactory = resultSchedulerServiceFactory;
	}
	
	@Override
	public Boolean write(Workbook resultBook) {
		Boolean wrote = Boolean.FALSE;
		if(resultBook != null){
			if(this.configService != null){
				wrote = this.write(true, resultBook);
			}
		}
		return wrote;
	}
	
	@Override
	public Boolean write(boolean hasDemo, Workbook resultBook){
		Boolean wrote = Boolean.FALSE;
		if(resultBook != null){
			if(this.configService != null){
				wrote = this.write(hasDemo, null, resultBook);
			}
		}
		return wrote;
	}
	
	@Override
	public Boolean write(boolean hasDemo, String loc, Workbook resultBook){
		Boolean wrote = Boolean.FALSE;
		if(resultBook != null){
			if(this.configService != null){
				wrote = this.write(hasDemo, loc, null, resultBook);
			}
		}
		return wrote;
	}
	
	@Override
	public Boolean write(boolean hasDemo, String loc, String patientName, Workbook resultBook) {
		Boolean wrote = Boolean.FALSE;
		if(resultBook != null){
			if(this.configService != null){
				WinCmdExecutorService winCmdExecutorService = (WinCmdExecutorService)this.resultSchedulerServiceFactory.getContextService(WinCmdExecutorService.class.getSimpleName());
				if(winCmdExecutorService != null){
					try{
						String localFolder = this.configService.getString("folder.local");
						String dropFolder = this.configService.getString("folder.drop");
						String dropNodemoFolder = this.configService.getString("folder.nodemo.drop");
						String archiveFolder = this.configService.getString("folder.archive");
						String archiveNodemoFolder = this.configService.getString("folder.nodemo.archive");
						String state = this.configService.getString("state.process");
						String runFrequency = this.configService.getString("run.frequency");
						String filenameDf = this.configService.getString("filename.df");
						log.debug("write(): localFolder: " + (localFolder == null ? "NULL" : localFolder));
						log.debug("write(): dropFolder: " + (dropFolder == null ? "NULL" : dropFolder));
						log.debug("write(): dropNodemoFolder: " + (dropNodemoFolder == null ? "NULL" : dropNodemoFolder));
						log.debug("write(): archiveFolder: " + (archiveFolder == null ? "NULL" : archiveFolder));
						log.debug("write(): archiveNodemoFolder: " + (archiveNodemoFolder == null ? "NULL" : archiveNodemoFolder));
						log.debug("write(): filenameDf: " + (filenameDf == null ? "NULL" : filenameDf));
						
						String shareMode = configService.getString("share.mode");
						
						String ip = null;
						String user = null;
						String pwd = null;
						String runMode = null;
						String netStart = null; 
						String netEnd = null;
						
						if(shareMode.equals("samba")){
							ip = configService.getString("samba.ip");
							user = configService.getString("samba.user");
							pwd = configService.getString("samba.pwd");
							runMode = configService.getString("samba.run.mode");
							netStart = configService.getString("net.start");
							netEnd = configService.getString("net.end");
						}else if(shareMode.equals("local")){
							ip = configService.getString("local.ip");
							user = configService.getString("local.user");
							pwd = configService.getString("local.pwd");
							runMode = configService.getString("local.run.mode");
							netStart = configService.getString("net.start.local");
							netEnd = configService.getString("net.end.local");							
						}
						log.debug("testExec(): ip: " + (ip == null ? "NULL" : ip));
						log.debug("testExec(): user: " + (user == null ? "NULL" : user));
						log.debug("testExec(): pwd: " + (pwd == null ? "NULL" : pwd));
						log.debug("testExec(): runMode: " + (runMode == null ? "NULL" : runMode));
						log.debug("testExec(): netStart: " + (netStart == null ? "NULL" : netStart));
						log.debug("testExec(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
						
						String stateLoc = null;
						if(loc != null){
							if(loc.indexOf(".") != -1){
								stateLoc = loc.substring(0, loc.indexOf("."));
							}
						}
						
						if((runMode != null) && (runMode.equals("share"))){
							netStart = MessageFormat.format(netStart, new String[]{ ip, user, pwd, });
							log.debug("testExec(): netStart: " + (netStart == null ? "NULL" : netStart));
							winCmdExecutorService.exec(netStart);
						}					
						
						DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
						DateFormat filenameDateFormat = null;
						if(filenameDf == null){
							filenameDateFormat = dateFormat;
						}else{
							filenameDateFormat = new SimpleDateFormat(filenameDf);
						}
						Date date = new Date();
						String year  = dateFormat.format(date).substring(0, 4);
						String month = dateFormat.format(date).substring(4, 6);
						String day = dateFormat.format(date).substring(6, 8);
						String dt = dateFormat.format(date);
	
						Calendar now = Calendar.getInstance();
						Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
						Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
						int currMo = CalendarUtils.getCurrentMonth(now);
						
						//now.add(Calendar.MONTH, -1);
						date = now.getTime();
						String filenameDt = filenameDateFormat.format(date);
						
						// local
						StringBuilder builder = new StringBuilder();
						//builder.append(parms.myArchPath).append(parms.myArchFile);
						//builder.append(parms.myArchPath).append(year).append("_").append(month).append("/");
						builder.append(localFolder);
						File localFile = new File(builder.toString());
						boolean created = false;
						if(!localFile.exists()){
							created = localFile.mkdirs();
							log.debug("write(): created: " + String.valueOf(created) + " " + localFile.toString());
						}
	
						String archFile = this.configService.getString("arch.file");
						//archFile = archFile.substring(0, (archFile.lastIndexOf(".") + 1));
						//archFile = MessageFormat.format(archFile, new String[]{ dt, });
						archFile = MessageFormat.format(archFile, new String[]{ filenameDt, });
						
						String[] archFileArray = archFile.split("\\.");
						if(archFileArray != null){
							for(String archItem : archFileArray){
								log.debug("write(): archItem: " + (archItem == null ? "NULL" : archItem));
							}
						}
						if(loc != null){
							StringBuilder fnBuilder = new StringBuilder();
							loc = loc.replaceAll("\\s", "");
							if(patientName != null){
								patientName = patientName.replaceAll("\\s", "");
								patientName = patientName.replaceAll(",", "_");
								if(patientName.charAt(patientName.length() - 1) == '_'){
									patientName = patientName.substring(0, patientName.length() - 1);
								}
								fnBuilder.append(archFileArray[0]).append(".").append(loc).append(".").append(patientName).append(".").append(archFileArray[1]).append(".").append(archFileArray[2]);
							}else{
								fnBuilder.append(archFileArray[0]).append(".").append(loc).append(".").append(archFileArray[1]).append(".").append(archFileArray[2]);
							}
							archFile = fnBuilder.toString();
						}else{
							StringBuilder fnBuilder = new StringBuilder();
							if(patientName != null){
								patientName = patientName.replaceAll("\\s", "");
								patientName = patientName.replaceAll(",", "_");
								if(patientName.charAt(patientName.length() - 1) == '_'){
									patientName = patientName.substring(0, patientName.length() - 1);
								}								
								fnBuilder.append(archFileArray[0]).append(".").append(patientName).append(".").append(archFileArray[1]).append(".").append(archFileArray[2]);
								archFile = fnBuilder.toString();
							}
						}
						log.debug("write(): archFile: " + (archFile == null ? "NULL" : archFile));
	
						//builder.append(archFile).append(dt).append(".txt");
						builder.append(archFile);
						boolean wroteLocal = RTFileWriter.writeWorkbook(builder.toString(), resultBook);
						// end local
						
						//drop
						StringBuilder dropBuilder = null;
						if(hasDemo){
							dropBuilder = new StringBuilder(dropFolder);
						}else{
							dropBuilder = new StringBuilder(dropNodemoFolder);
						}
						if(stateLoc != null){
							dropBuilder.append(stateLoc).append("\\");
						}
						if(runFrequency.equals("daily")){
							dropBuilder.append(year).append(month).append(day).append("\\");
						}else if(runFrequency.equals("monthly")){
							dropBuilder.append((currMo == 1 ? prevMonthYear : year)).append(prevMonth.toString()).append("\\");
						}
						log.debug("write(): dropBuilder: " + (dropBuilder == null ? "NULL" : dropBuilder.toString()));
						//RTFileWriter.copyFile(localFolder, dropFolder, archFile);
						boolean copiedDrop = RTFileWriter.copyFile(localFolder, dropBuilder.toString(), archFile);
						//end drop
						
						//archive
						StringBuilder archBuilder = null;
						if(hasDemo){
							archBuilder = new StringBuilder(archiveFolder);
						}else{
							archBuilder = new StringBuilder(archiveNodemoFolder);
						}
						if(stateLoc != null){
							archBuilder.append(stateLoc).append("\\");
						}						
						if(runFrequency.equals("daily")){
							archBuilder.append(year).append(month).append(day).append("\\");
						}else if(runFrequency.equals("monthly")){
							//archBuilder.append(year).append(prevMonth.toString()).append("\\");
							archBuilder.append((currMo == 1 ? prevMonthYear : year)).append(prevMonth.toString()).append("\\");
						}
						//RTFileWriter.moveFileToDir(localFolder, archiveFolder, archFile);
						boolean movedArch = RTFileWriter.moveFileToDir(localFolder, archBuilder.toString(), archFile);
						//end archive		
						wrote = (wroteLocal && copiedDrop && movedArch);
						
						if((runMode != null) && (runMode.equals("share"))){
							winCmdExecutorService.exec(netEnd);
						}						
					}catch(ConfigException ce){
						log.error(ce.getMessage());
						ce.printStackTrace();
					}catch(IOException ioe){
						log.error(ioe.getMessage());
						ioe.printStackTrace();
						wrote = Boolean.FALSE;
					}catch(Exception e){
						log.error(e.getMessage());
						e.printStackTrace();
						wrote = Boolean.FALSE;
					}
				}//if
			}
		}
		return wrote;
	}

	@Override
	public Boolean write(String county, Workbook resultStr) {
		// TODO Auto-generated method stub
		return null;
	}

}
