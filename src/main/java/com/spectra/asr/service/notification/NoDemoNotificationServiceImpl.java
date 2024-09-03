package com.spectra.asr.service.notification;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.businessobject.ora.hub.distributor.DistributorBo;
import com.spectra.asr.businessobject.ora.hub.generator.GeneratorBo;
import com.spectra.asr.dto.distributor.DistributorItemDto;
import com.spectra.asr.dto.generator.GeneratorDto;
import com.spectra.asr.dto.state.StateMasterDto;
import com.spectra.asr.dto.state.StateResultDto;
import com.spectra.asr.executor.WinCmdExecutor;
import com.spectra.asr.generator.context.GeneratorContext;
import com.spectra.asr.generator.context.factory.GeneratorContextFactory;
import com.spectra.framework.utils.crypt.AESCrypt;
import com.spectra.scorpion.framework.exception.BusinessException;
import com.spectra.scorpion.framework.util.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
public class NoDemoNotificationServiceImpl implements NoDemoNotificationService {
	//private Logger log = Logger.getLogger(NoDemoNotificationServiceImpl.class);
	
	@Override
	public boolean notifyByFile(List<StateResultDto> dtoList, GeneratorDto gDto) throws BusinessException {
		log.warn("notifyByFile(): gDto.getEastWestFlag(): " + (gDto == null ? "NULL" : gDto.getEastWestFlag()));
		boolean notified = false;
		if((dtoList != null) && (gDto != null)){
			try{
				AsrBo asrBo = AsrBoFactory.getAsrBo();
				GeneratorBo generatorBo = AsrBoFactory.getGeneratorBo();
				DistributorBo distributorBo = AsrBoFactory.getDistributorBo();
				
				String gDtoState = gDto.getState();
				String gDtoStateAbbr = gDto.getStateAbbreviation();
				
				//hssf_flavor
				StateMasterDto stateMasterDto = new StateMasterDto();
				stateMasterDto.setStateAbbreviation("HSSF_FLAVOR");
				stateMasterDto.setStatus("active");
				List<StateMasterDto> stdList = asrBo.getStateMaster(stateMasterDto);
				log.warn("notifyByFile(): stdList: " + (stdList == null ? "NULL" : stdList.toString()));
				if(stdList.size() == 1){
					stateMasterDto = stdList.get(0);
				}else{
					throw new BusinessException(new IllegalArgumentException("entity must be unique").toString());
				}
				
				GeneratorDto generatorDto = new GeneratorDto();
				generatorDto.setStateFk(stateMasterDto.getStateMasterPk());
				generatorDto.setStatus("active");
				generatorDto.setWriteBy("state");
				List<GeneratorDto> generatorDtoList = generatorBo.getGenerator(generatorDto);
				log.warn("notifyByFile(): generatorDtoList: " + (generatorDtoList == null ? "NULL" : generatorDtoList.toString()));
				if((generatorDtoList != null) && (generatorDtoList.size() > 0)){
					generatorDto = generatorDtoList.get(0);
					generatorDto.setStateTarget(gDtoStateAbbr);
					generatorDto.setEastWestFlag(gDto.getEastWestFlag());
					generatorDto.setConversionContext("StateHssfNoaddrGeneratorContext");
				}
				
				log.warn("notifyByFile(): generatorDto: " + (generatorDto == null ? "NULL" : generatorDto.toString()));
				
				GeneratorContext<Workbook, StateResultDto> generatorContext = (GeneratorContext<Workbook, StateResultDto>)GeneratorContextFactory.getContextImpl(generatorDto);
				log.warn("notify(): generatorContext: " + (generatorContext == null ? "NULL" : generatorContext.toString()));
				
				Map<String, List<Workbook>> dtoListMap = generatorContext.executeStrategy(dtoList);
				log.warn("notify(): dtoListMap: size: " + (dtoListMap == null ? "NULL" : String.valueOf(dtoListMap.size())));
				log.warn("notify(): dtoListMap: " + (dtoListMap == null ? "NULL" : dtoListMap.toString()));
				// end hssf_flavor
				
				
/*				
				DistributorDto distributorDto = new DistributorDto();
				distributorDto.setGeneratorFk(generatorDto.getGeneratorPk());
				distributorDto.setStateFk(generatorDto.getStateFk());
				distributorDto.setState(generatorDto.getState());
				distributorDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
				distributorDto.setDistributionType("local");
				distributorDto.setStatus("active");
				List<DistributorDto> distributorDtoList = distributorBo.getDistributor(distributorDto);
				if(distributorDtoList != null){
					distributorDto = distributorDtoList.get(0);
				}
				
				String distributorStateTarget = distributorDto.getStateTarget();
				
				DistributorItemDto distributorItemDto = new DistributorItemDto();
				distributorItemDto.setState(generatorDto.getState());
				distributorItemDto.setStateAbbreviation(generatorDto.getStateAbbreviation());
				distributorItemDto.setStatus("active");
				List<DistributorItemDto> distributorItemDtoList = distributorBo.getDistributorItems(distributorItemDto);
				
				Map<String, String> distItemMap = new HashMap<String, String>();
				for(DistributorItemDto itemDto : distributorItemDtoList){
					distItemMap.put(itemDto.getDistributorItem(), itemDto.getDistributorItemValue());
				}
*/				

/*				
				//dist item group 5
				DistributorItemDto paramDto = new DistributorItemDto();
				paramDto.setDistributorItemsGroup(new Integer(5));
				List<DistributorItemDto> grp5DtoList = distributorBo.getOnlyDistributorItem(paramDto);
				Map<String, String> grp5DistItemMap = new HashMap<String, String>();
				for(DistributorItemDto grp5ItemDto : grp5DtoList){
					grp5DistItemMap.put(grp5ItemDto.getDistributorItem(), grp5ItemDto.getDistributorItemValue());
				}
*/				
				
				//dist item sendout
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("distributorItem", "%sendout%");
				paramMap.put("status", "active");
				List<DistributorItemDto> grp5DtoList = distributorBo.getJustDistributorItem(paramMap);
				Map<String, String> grp5DistItemMap = new HashMap<String, String>();
				for(DistributorItemDto grp5ItemDto : grp5DtoList){
					grp5DistItemMap.put(grp5ItemDto.getDistributorItem(), grp5ItemDto.getDistributorItemValue());
				}
				
				String netStart = ApplicationProperties.getProperty("net.start.drive");
				String netEnd = ApplicationProperties.getProperty("net.end.drive");
				
				//if run mode is share, get run mode, ip, user, pwd from distributor item where distributor items group = 2
				//todo: create a distributor select mapping
				//String localRunMode = grp5DistItemMap.get("sendout.local.run.mode");
				//String localIp = grp5DistItemMap.get("sendout.local.ip");
				//String localUser = grp5DistItemMap.get("sendout.local.user");
				//String localPwd = grp5DistItemMap.get("sendout.local.pwd");
				//String folderDrop = grp5DistItemMap.get("sendout.folder.drop");

				String localRunMode = null;
				String localIp = null;
				String localUser = null;
				String localPwd = null;
				String folderDrop = null;
				String driveLetter = null;
				
				String ewFlag = gDto.getEastWestFlag();
/*				
				if(ewFlag.equalsIgnoreCase("East")){
					localRunMode = grp5DistItemMap.get("sendout.local.run.mode.east");
					localIp = grp5DistItemMap.get("sendout.local.ip.east");
					localUser = grp5DistItemMap.get("sendout.local.user.east");
					localPwd = grp5DistItemMap.get("sendout.local.pwd.east");
					folderDrop = grp5DistItemMap.get("sendout.folder.drop.east");
				}else if(ewFlag.equalsIgnoreCase("West")){
					localRunMode = grp5DistItemMap.get("sendout.local.run.mode.west");
					localIp = grp5DistItemMap.get("sendout.local.ip.west");
					localUser = grp5DistItemMap.get("sendout.local.user.west");
					localPwd = grp5DistItemMap.get("sendout.local.pwd.west");
					folderDrop = grp5DistItemMap.get("sendout.folder.drop.west");
				}
*/				
				
				localRunMode = ApplicationProperties.getProperty("sendout.drive.run.mode");
				localIp = ApplicationProperties.getProperty("sendout.drive.ip");
				localUser = ApplicationProperties.getProperty("sendout.drive.user");
				localPwd = ApplicationProperties.getProperty("sendout.drive.pwd");
				driveLetter = ApplicationProperties.getProperty("sendout.drive.letter");
				if(ewFlag.equalsIgnoreCase("East")){
					folderDrop = driveLetter + ApplicationProperties.getProperty("sendout.drive.folder.drop.east");
				}else if(ewFlag.equalsIgnoreCase("West")){
					folderDrop = driveLetter + ApplicationProperties.getProperty("sendout.drive.folder.drop.west");
				} else if(ewFlag.equalsIgnoreCase("South")){
					folderDrop = driveLetter + ApplicationProperties.getProperty("sendout.drive.folder.drop.south");
				}
				

				String key = ApplicationProperties.getProperty("aes.key");
				localPwd = AESCrypt.decrypt(localPwd, key);
				
				//test
/*				
				localUser = "spectraeastnj\\serviceuser";
				localPwd = "chocolate";
				localIp = "\\\\cafs01\\QA Shared Spectra East-West";
				localRunMode = "share";
				driveLetter = "z:";
				if(ewFlag.equalsIgnoreCase("East")){
					//folderDrop = "\\\\cafs01\\QA Shared Spectra East-West\\STATE REPORTING-NO ADD\\east\\{0}\\";
					folderDrop = driveLetter + "\\STATE REPORTING-NO ADD\\east\\{0}\\";
				}else if(ewFlag.equalsIgnoreCase("West")){
					//folderDrop = "\\\\cafs01\\QA Shared Spectra East-West\\STATE REPORTING-NO ADD\\west\\{0}\\";
					folderDrop = driveLetter + "\\STATE REPORTING-NO ADD\\west\\{0}\\";
				}
*/				
				//end test
				
				WinCmdExecutor winCmdExecutor = new WinCmdExecutor();
				if((localRunMode != null) && (localRunMode.equals("share"))){
					//netStart = MessageFormat.format(netStart, new String[]{ localIp, localUser, localPwd, });
					netStart = MessageFormat.format(netStart, new String[]{ driveLetter, localIp, localUser, localPwd, });
					log.warn("notifyByFile(): netStart: " + (netStart == null ? "NULL" : netStart));
					winCmdExecutor.exec(netStart);
				}
				log.warn("notifyByFile(): netStart: " + (netStart == null ? "NULL" : netStart));
				log.warn("notifyByFile(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
				log.warn("notifyByFile(): localRunMode: " + (localRunMode == null ? "NULL" : localRunMode));
				log.warn("notifyByFile(): localIp: " + (localIp == null ? "NULL" : localIp));
				log.warn("notifyByFile(): localUser: " + (localUser == null ? "NULL" : localUser));
				log.warn("notifyByFile(): localPwd: " + (localPwd == null ? "NULL" : localPwd));
				
				
				if(dtoListMap != null){
					DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
					String dropDirStr = folderDrop.substring(0, folderDrop.lastIndexOf("\\"));
					dropDirStr = dropDirStr.substring(0, dropDirStr.lastIndexOf("\\"));
					log.warn("notifyByFile(): dropDirStr: " + (dropDirStr == null ? "NULL" : dropDirStr));
					File dropDir = new File(dropDirStr);
					boolean canMkdirs = false;
					if(!dropDir.exists()){
						canMkdirs = dropDir.mkdirs();
						log.warn("notifyByFile(): canMkdirs: " + (String.valueOf(canMkdirs)));
					}
					//log.warn("notifyByFile(): canMkdirs: " + (String.valueOf(canMkdirs)));

					//WriterContext<Boolean, Workbook> writerContext = (WriterContext<Boolean, Workbook>)WriterContextFactory.getContextImpl(generatorDto);
					for(List<Workbook> wbList : dtoListMap.values()){
						for(Workbook wb : wbList){
							//String dt = df.format(new Date());
							String dt = String.valueOf(System.currentTimeMillis());
							StringBuilder fnBuilder = new StringBuilder();
							//fnBuilder.append(gDto.getStateAbbreviation());
							fnBuilder.append(gDtoStateAbbr).append(".INTRA-LAB").append(".NO_ADD");
							if(gDto.getWriteBy().equals("state_micro")){
								fnBuilder.append(".Micro");
							}
							fnBuilder.append(".").append(dt).append(".xls");
							String dropFile = MessageFormat.format(folderDrop, fnBuilder.toString());
							log.warn("notifyByFile(): dropFile: " + (dropFile == null ? "NULL" : dropFile));
							
							FileOutputStream fileOut = new FileOutputStream(dropFile);
							try{
								wb.write(fileOut);
								fileOut.flush();
							}finally{
								if(fileOut != null){
									try{
										fileOut.close();
									}catch(IOException ioe){
										log.error(String.valueOf(ioe));
										ioe.printStackTrace();
									}
								}
							}
							
							//writerContext.setFileName(fnBuilder.toString());
							//Boolean isWritten = writerContext.executeStrategy(wb);
							//log.warn("notify(): isWritten: " + (isWritten == null ? "NULL" : isWritten.toString()));
						}
					}
					notified = true;
				}//if
				
				if((localRunMode != null) && (localRunMode.equals("share"))){
					netEnd = MessageFormat.format(netEnd, new String[]{ driveLetter, });
					log.warn("notifyByFile(): netEnd: " + (netEnd == null ? "NULL" : netEnd));
					winCmdExecutor.exec(netEnd);
				}				
			}catch(BusinessException be){
				log.error(String.valueOf(be));
				be.printStackTrace();
			}catch(Exception e){
				log.error(String.valueOf(e));
				e.printStackTrace();
			}
		}
		return notified;
	}

}
