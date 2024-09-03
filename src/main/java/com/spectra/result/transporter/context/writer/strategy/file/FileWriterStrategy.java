package com.spectra.result.transporter.context.writer.strategy.file;

import com.spectra.framework.config.ConfigException;
import com.spectra.framework.service.config.ConfigService;
import com.spectra.result.transporter.context.writer.strategy.WriterStrategy;
import com.spectra.result.transporter.utils.file.writer.RTFileWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//import org.apache.logging.log4j.LogManager;
//import com.spectra.result.transporter.businessobject.spring.hl7.HL7WriterBo;
//import com.spectra.result.transporter.dto.rr.RepositoryResultDto;
//import com.spectra.result.transporter.dto.hl7.state.NTERecord;
//import com.spectra.result.transporter.dto.hl7.state.OBXRecord;
//import com.spectra.result.transporter.dto.hl7.state.PatientRecord;

@Slf4j
public class FileWriterStrategy implements WriterStrategy<String> {
	//private Logger log = Logger.getLogger(FileWriterStrategy.class);
	
	protected ConfigService configService;
	
	public void setConfigService(ConfigService configService){
		this.configService = configService;
	}
	
	@Override
	public Boolean write(String resultStr){
		return this.write(null, resultStr);
	}
	
	@Override
	public Boolean write(String county, String resultStr){
		Boolean wrote = Boolean.FALSE;
		if(resultStr != null){
			if(this.configService != null){
				try{
					String localFolder = this.configService.getString("folder.local");
					String dropFolder = this.configService.getString("folder.drop");
					String archiveFolder = this.configService.getString("folder.archive");
					String state = this.configService.getString("state.process");
					log.debug("write(): localFolder: " + (localFolder == null ? "NULL" : localFolder));
					log.debug("write(): dropFolder: " + (dropFolder == null ? "NULL" : dropFolder));
					log.debug("write(): archiveFolder: " + (archiveFolder == null ? "NULL" : archiveFolder));
					if(localFolder != null && archiveFolder != null && state != null){
						//String state = listMap.values().iterator().next().get(0).getPatientState();
						//String hl7String = this.toHL7String(listMap);
						//log.debug("writeHL7(): hl7String: " + (hl7String == null ? "NULL" : hl7String));
						//if(hl7String != null){
							//DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
							//DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
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

							//log.debug("writeHL7(): builder: " + (builder == null ? "NULL" : builder.toString()));
	
							RTFileWriter.write(builder.toString(), resultStr);
							/*
							localFile = new File(builder.toString());
							FileWriter fw = null;
							BufferedWriter bw = null;
							try{
								if(!localFile.exists()){
									localFile.createNewFile();
								}			
								fw = new FileWriter(localFile, true);
								bw = new BufferedWriter(fw);
								bw.write(resultStr);
							}catch(IOException ioe){
								ioe.printStackTrace();
								return Boolean.FALSE;
							}finally{
								if(bw != null){
									try{
										bw.close();
									}catch(IOException ioe){
										ioe.printStackTrace();
										return Boolean.FALSE;
									}
								}
								if(fw != null){
									try{
										fw.close();
									}catch(IOException ioe){
										ioe.printStackTrace();
										return Boolean.FALSE;
									}
								}
							}
							*/
							// end local
							
							// drop
							//StringBuilder dropFileBuilder = new StringBuilder();
							//dropFileBuilder.append(dropFolder).append(archFile);
							//log.debug("write(): dropFileBuilder: " + (dropFileBuilder == null ? "NULL" : dropFileBuilder.toString()));
							//this.copyFile(builder.toString(), dropFileBuilder.toString());
							
							//this.copyFile(localFolder, dropFolder, archFile);
							
							RTFileWriter.copyFile(localFolder, dropFolder, archFile);
							// end drop
							
							// archive
							//StringBuilder archFileBuilder = new StringBuilder();
							//archFileBuilder.append(archiveFolder).append(archFile);
							//log.debug("write(): archFileBuilder: " + (archFileBuilder == null ? "NULL" : archFileBuilder.toString()));
							//this.moveFileToDir(builder.toString(), archiveFolder);
							
							//this.moveFileToDir(localFolder, archiveFolder, archFile);
							
							RTFileWriter.moveFileToDir(localFolder, archiveFolder, archFile);
							// end archive
							
							wrote = Boolean.TRUE;
							log.debug("write(): builder: " + (builder == null ? "NULL" : builder.toString()));
		
						//}//if
					}
				}catch(ConfigException ce){
					log.error(ce.getMessage());
					ce.printStackTrace();
					wrote = Boolean.FALSE;
				}catch(IOException ioe){
					log.error(ioe.getMessage());
					ioe.printStackTrace();
					wrote = Boolean.FALSE;
				}
			}
		}
		return wrote;
	}
	
	boolean moveFileToDir(String srcDir, String destDir, String fileName) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null) && (fileName != null)){
			File srcFile = new File(srcDir);
			if(!srcFile.exists()){
				srcFile.mkdirs();
			}
			File destFile = new File(destDir);
			if(!destFile.exists()){
				destFile.mkdirs();
			}
			
			StringBuilder srcBuilder = new StringBuilder();
			srcBuilder.append(srcDir).append(fileName);
			srcFile = new File(srcBuilder.toString());
			if(srcFile.exists()){
				FileUtils.moveFileToDirectory(srcFile, destFile, true);
				wrote = true;
			}			
		}
		return wrote;
	}
	
	/*boolean moveFileToDir(String srcFileName, String destDir) throws IOException {
		boolean wrote = false;
		if((srcFileName != null) && (destDir != null)){
			File srcFile = new File(srcFileName);
			if(srcFile.exists()){
				File destFile = new File(destDir);
				FileUtils.moveFileToDirectory(srcFile, destFile, true);
				wrote = true;
			}
		}
		return wrote;
	}*/
	
	boolean copyFile(String srcDir, String destDir, String fileName) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null) && (fileName != null)){
			File srcFile = new File(srcDir);
			if(!srcFile.exists()){
				srcFile.mkdirs();
			}
			File destFile = new File(destDir);
			if(!destFile.exists()){
				destFile.mkdirs();
			}
			
			StringBuilder srcBuilder = new StringBuilder();
			srcBuilder.append(srcDir).append(fileName);
			srcFile = new File(srcBuilder.toString());
			
			StringBuilder destBuilder = new StringBuilder();
			destBuilder.append(destDir).append(fileName);
			destFile = new File(destBuilder.toString());
			if(srcFile.exists()){
				FileUtils.copyFile(srcFile, destFile, true);
				wrote = true;
			}
		}
		return wrote;
	}
	
/*
	boolean writeToLocal(String localFolder, String localFileName){
		boolean wrote = false;
		if((localFolder != null) && (localFileName != null)){
			StringBuilder builder = new StringBuilder();
			//builder.append(parms.myArchPath).append(parms.myArchFile);
			//builder.append(parms.myArchPath).append(year).append("_").append(month).append("/");
			builder.append(localFolder);
			File localFile = new File(builder.toString());
			boolean created = false;
			if(!localFile.exists()){
				created = localFile.mkdirs();
				log.debug("writeToLocal(): created: " + String.valueOf(created) + " " + localFile.toString());
			}

			//String archFile = this.configService.getString("arch.file");
			//archFile = MessageFormat.format(archFile, new String[]{ state, dt, });
			builder.append(localFileName);

			//log.debug("writeHL7(): builder: " + (builder == null ? "NULL" : builder.toString()));

			localFile = new File(builder.toString());
			FileWriter fw = null;
			BufferedWriter bw = null;
			try{
				if(!localFile.exists()){
					localFile.createNewFile();
				}			
				fw = new FileWriter(localFile, true);
				bw = new BufferedWriter(fw);
				bw.write(resultStr);
			}catch(IOException ioe){
				ioe.printStackTrace();
			}finally{
				if(bw != null){
					try{
						bw.close();
					}catch(IOException ioe){
						ioe.printStackTrace();
					}
				}
				if(fw != null){
					try{
						fw.close();
					}catch(IOException ioe){
						ioe.printStackTrace();
					}
				}
			}
		}
	}
	*/
}
