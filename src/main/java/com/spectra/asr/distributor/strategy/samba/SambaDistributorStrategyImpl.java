package com.spectra.asr.distributor.strategy.samba;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.distributor.strategy.DistributorStrategy;
import com.spectra.asr.dto.distributor.DistributorDto;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.generator.GeneratorFieldDto;
import com.spectra.asr.executor.WinCmdExecutor;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.asr.utils.file.writer.AsrFileWriter;
import com.spectra.framework.utils.crypt.AESCrypt;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class SambaDistributorStrategyImpl implements DistributorStrategy<Boolean> {
	//private Logger log = Logger.getLogger(SambaDistributorStrategyImpl.class);
	
	private String fileName;
	private GeneratorDto generatorDto;

	public GeneratorDto getGeneratorDto() {
		return generatorDto;
	}

	public void setGeneratorDto(GeneratorDto generatorDto) {
		this.generatorDto = generatorDto;
	}

	@Override
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public Boolean distribute(){
		Boolean distributed = null;
		if(this.generatorDto != null){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			
			GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
			
			DistributorBo distributorBo = AsrBoFactory.getDistributorBo();

			//GeneratorDao generatorDao = (GeneratorDao)AsrDaoFactory.getDAOImpl(GeneratorDao.class.getSimpleName());
			try{
				if(generatorBo != null){

					GeneratorFieldDto generatorFieldDto = new GeneratorFieldDto();
					generatorFieldDto.setGeneratorFk(this.generatorDto.getGeneratorPk());
					generatorFieldDto.setGeneratorFieldType("PATH_DIR");
					generatorFieldDto.setGeneratorField("folder.local");
					generatorFieldDto.setStatus("active");
					//List<GeneratorFieldDto> fieldDtoList = generatorDao.getGeneratorField(generatorFieldDto);
					List<GeneratorFieldDto> fieldDtoList = generatorBo.getGeneratorFields(generatorFieldDto);
					
					if(distributorBo != null){
						DistributorDto distributorDto = new DistributorDto();
						distributorDto.setGeneratorFk(this.generatorDto.getGeneratorPk());
						distributorDto.setStateFk(this.generatorDto.getStateFk());
						distributorDto.setState(this.generatorDto.getState());
						distributorDto.setStateAbbreviation(this.generatorDto.getStateAbbreviation());
						distributorDto.setDistributionType("samba");
						distributorDto.setStatus("active");
						List<DistributorDto> distributorDtoList = distributorBo.getDistributor(distributorDto);
						if(distributorDtoList != null){
							distributorDto = distributorDtoList.get(0);
						}
						
						DistributorItemDto distributorItemDto = new DistributorItemDto();
						//distributorItemDto.setDistributorFk(distributorDto.getDistributorPk());
						distributorItemDto.setState(this.generatorDto.getState());
						distributorItemDto.setStateAbbreviation(this.generatorDto.getStateAbbreviation());
						distributorItemDto.setStatus("active");
						List<DistributorItemDto> distributorItemDtoList = distributorBo.getDistributorItems(distributorItemDto);
						
						Map<String, String> distItemMap = new HashMap<String, String>();
						for(DistributorItemDto itemDto : distributorItemDtoList){
							distItemMap.put(itemDto.getDistributorItem(), itemDto.getDistributorItemValue());
						}
						
						String netStart = ApplicationProperties.getProperty("net.start");
						String netEnd = ApplicationProperties.getProperty("net.end");
						String sambaRunMode = distItemMap.get("samba.run.mode");
						String sambaIp = distItemMap.get("samba.ip");
						String sambaUser = distItemMap.get("samba.user");
						String sambaPwd = distItemMap.get("samba.pwd");
						String key = ApplicationProperties.getProperty("aes.key");
						sambaPwd = AESCrypt.decrypt(sambaPwd, key);
						WinCmdExecutor winCmdExecutor = new WinCmdExecutor();
						if((sambaRunMode != null) && (sambaRunMode.equals("share"))){
							netStart = MessageFormat.format(netStart, new String[]{ sambaIp, sambaUser, sambaPwd, });
							log.warn("distribute(): netStart: " + (netStart == null ? "NULL" : netStart));
							winCmdExecutor.exec(netStart);
						}
						log.warn("distribute(): netStart: " + (netStart == null ? "NULL" : netStart));

						log.warn("distribute(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
						log.warn("distribute(): sambaRunMode: " + (sambaRunMode == null ? "NULL" : sambaRunMode));
						log.warn("distribute(): sambaIp: " + (sambaIp == null ? "NULL" : sambaIp));
						log.warn("distribute(): sambaUser: " + (sambaUser == null ? "NULL" : sambaUser));
						log.warn("distribute(): sambaPwd: " + (sambaPwd == null ? "NULL" : sambaPwd));
						
						
						String filePath = fieldDtoList.get(0).getGeneratorFieldValue();
						//String filePath = ApplicationProperties.getProperty("writer.folder.local");
						if(filePath != null){
							filePath = MessageFormat.format(filePath, new Object[]{ this.generatorDto.getStateAbbreviation(), });
							log.warn("distribute(): filePath: " + (filePath == null ? "NULL" : filePath));
							/*
							File localFile = new File(filePath);
							boolean created = false;
							if(!localFile.exists()){
								created = localFile.mkdirs();
								log.warn("write(): created: " + String.valueOf(created) + " " + localFile.toString());
							}
							*/
							Calendar now = Calendar.getInstance();
							Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
							Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
							int currMo = CalendarUtils.getCurrentMonth(now);
							
							now.add(Calendar.MONTH, -1);
							Date prevMonthDate = now.getTime();
							
							DateFormat df = new SimpleDateFormat("yyyyMMdd");
							String dt = df.format(new Date());
							df = new SimpleDateFormat("yyyyMM");
							//String dtt = df.format(prevMonthDate);
							//df = new SimpleDateFormat("MMM dd yyyy");
							String dtt = df.format(new Date());
							//String dtt = df.format(prevMonthDate);
							String fileMask = this.generatorDto.getFileMask();
							/*
							String fName = null;
							log.warn("write(): fileName: " + (fileName == null ? "NULL" : fileName));
							if(this.fileName != null){
								//String fileName = MessageFormat.format(fileMask, new Object[]{ this.generatorDto.getStateAbbreviation(), dt, });
								//fName = MessageFormat.format(fileMask, new Object[]{ this.fileName, String.valueOf(System.currentTimeMillis()), });
								fName = MessageFormat.format(fileMask, new Object[]{ this.fileName, dtt, });
							}else{
								//fName = MessageFormat.format(fileMask, new Object[]{ this.generatorDto.getStateAbbreviation(), String.valueOf(System.currentTimeMillis()), });
								fName = MessageFormat.format(fileMask, new Object[]{ dto.getStateAbbreviation(), dtt, });
							}
							*/
							StringBuilder filePathBuilder = new StringBuilder();
							//filePathBuilder.append(filePath).append(this.generatorDto.getWriteBy()).append("/").append(dt).append("/").append(fName);
							filePathBuilder.append(filePath).append(this.generatorDto.getWriteBy()).append("/").append(dt).append("/");
							log.warn("distribute(): filePathBuilder: " + (filePathBuilder == null ? "NULL" : filePathBuilder.toString()));
							
							File localDir = new File(filePathBuilder.toString());
							
							String archivePath = distItemMap.get("folder.state.archive");
							log.warn("distribute(): archivePath: " + (archivePath == null ? "NULL" : archivePath));
							archivePath = MessageFormat.format(archivePath, new Object[]{ this.generatorDto.getStateAbbreviation(), });
							archivePath += dtt;
							log.warn("distribute(): archivePath: " + (archivePath == null ? "NULL" : archivePath));
							File archiveDir	= new File(archivePath);						
							
							String dropPath = distItemMap.get("folder.state.drop");
							log.warn("distribute(): dropPath: " + (dropPath == null ? "NULL" : dropPath));
							dropPath = MessageFormat.format(dropPath, new Object[]{ this.generatorDto.getStateAbbreviation(), });
							log.warn("distribute(): dropPath: " + (dropPath == null ? "NULL" : dropPath));
							File dropDir = new File(dropPath);
							
							boolean created = false;
							if(localDir.exists()){
								//created = localFile.mkdirs();
								//log.warn("write(): created: " + String.valueOf(created) + " " + localFile.toString());
								
								if(!archiveDir.exists()){
									created = archiveDir.mkdirs();
								}
								boolean wroteArchive = AsrFileWriter.copyFilesToDir(localDir, archiveDir);
								
								if(!dropDir.exists()){
									created = dropDir.mkdirs();
								}
								//boolean wroteDrop = AsrFileWriter.moveFilesToDir(localDir, dropDir);
								//boolean wroteDrop = AsrFileWriter.copyFilesToDir(localDir, dropDir);
								boolean wroteDrop = AsrFileWriter.copyTempFilesToDir(localDir, dropDir);
								
								//write ensemble semaphore file
								String ensembleIpList = ApplicationProperties.getProperty("ensemble.ip.list");
								String ensembleSemaphoreFile = ApplicationProperties.getProperty("ensemble.semaphore.file");
								ensembleSemaphoreFile = MessageFormat.format(ensembleSemaphoreFile, new Object[] { this.generatorDto.getStateAbbreviation(), });
								String[] ensembleIpArray = null;
								if(ensembleIpList.indexOf(",") != -1){
									ensembleIpArray = ensembleIpList.split(",");
								}else{
									ensembleIpArray = new String[] { ensembleIpList, };
								}
								boolean wroteSemaporeFile = false;
								for(String ensembleIp : ensembleIpArray){
									log.warn("distribute(): ensembleIp: " + (ensembleIp == null ? "NULL" : ensembleIp));
									//test
									ensembleIp = "njnas01";
									//end test
									if(dropPath.indexOf(ensembleIp) != -1){
										StringBuilder ensemblePathBuilder = new StringBuilder();
										ensemblePathBuilder.append(filePathBuilder.toString()).append(ensembleSemaphoreFile);
										log.warn("distribute(): ensemblePathBuilder: " + (ensemblePathBuilder == null ? "NULL" : ensemblePathBuilder.toString()));
										File semaphoreFile = new File(ensemblePathBuilder.toString());
										semaphoreFile.createNewFile();
										wroteSemaporeFile = AsrFileWriter.copyAFileToDir(semaphoreFile, dropDir);
										break;
									}
								}
								//end write ensemble semaphore file
								
								distributed = new Boolean(wroteArchive && wroteDrop);
								
								if(distributed){
									AsrFileWriter.deleteDir(localDir);
								}
							}
							//filePathBuilder.append(fName);
							//log.warn("write(): filePathBuilder: " + (filePathBuilder == null ? "NULL" : filePathBuilder.toString()));
						}//if
						if((sambaRunMode != null) && (sambaRunMode.equals("share"))){
							winCmdExecutor.exec(netEnd);
						}
					}//if
				}//if
			}catch(BusinessException be){
				log.error(String.valueOf(be));
				be.printStackTrace();
			}catch(Exception e){
				log.error(String.valueOf(e));
				e.printStackTrace();
			}
		}
		return distributed;
	}

}
