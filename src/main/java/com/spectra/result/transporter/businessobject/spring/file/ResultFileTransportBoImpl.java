package com.spectra.result.transporter.businessobject.spring.file;

import com.spectra.result.transporter.businessobject.spring.hl7.HL7WriterBo;
import com.spectra.result.transporter.businessobject.spring.ora.rr.RepositoryBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Slf4j
public class ResultFileTransportBoImpl extends FileTransportBoImpl implements ResultFileTransportBo {
	//private Logger log = Logger.getLogger(ResultFileTransportBoImpl.class);
	
	protected HL7WriterBo writerBo;
	protected RepositoryBo repositoryBo;
	
	public void setWriterBo(HL7WriterBo writerBo){
		this.writerBo = writerBo;
	}
	
	public void setRepositoryBo(RepositoryBo repositoryBo){
		this.repositoryBo = repositoryBo;
	}
	
	protected void waitFor(int n){
		long t0, t1;
        t0 =  System.currentTimeMillis();
        do{
        	t1 = System.currentTimeMillis();
        }while ((t1 - t0) < (n * 1000));
	}	
	
	public void run() {
/*
		if(this.ip != null){
			if((this.writerBo != null) && (this.repositoryBo != null)){
				StringBuilder fileNameListBuilder = null;
				try{
					Map<String, List<PatientRecord>> listMap = this.repositoryBo.getListMap();
					if(listMap != null){
						this.writerBo.writeHL7(listMap);
						this.waitFor(20);
					}
					boolean copiedToArchive = this.copytoArchive();
					waitFor(20);
					if(copiedToArchive){
						//String commandConnect53 = MessageFormat.format(MGSP.getCommandConnect(), new String[]{ MGSP.getIpAddress(), MGSP.getNetUid(), MGSP.getNetPwd(), });
						String commandConnect53 = MessageFormat.format(ip.getCommandConnect(), new String[]{ ip.getShareIp(), ip.getShareUid(), ip.getSharePwd(), });
						Runtime.getRuntime().exec(commandConnect53);
					    waitFor(40);
						String fileName = "";
						//File src = new File(MGSP.getPropertys().getMyPath());
						File src = new File(ip.getLocalFolder());
					    File[] listOfFiles = src.listFiles();
						if((listOfFiles != null) && (listOfFiles.length > 0)){
							fileNameListBuilder = new StringBuilder();
							for (int i = 0; i < listOfFiles.length; i++) {
								if (listOfFiles[i].isFile()) {
									fileName = listOfFiles[i].getName();
									fileNameListBuilder.append(fileName).append(",");
									try {
										this.movedToShared(fileName);
									} catch (FileNotFoundException fnfex) {
										//handle
										log.error(fnfex);
										fnfex.printStackTrace();
										log.error( " EXCEPTION ::::: "  + fnfex );
									}
								}
							}//for
						}
						
						//=============================================

					}
				}catch(IOException ioe){
					log.error(ioe);
					ioe.printStackTrace();
				}catch(BusinessException be){
					log.error(be);
					be.printStackTrace();
				}finally {
					//String commandDisconnect53 = MessageFormat.format(MGSP.getCommandDisconnect(), new String[]{ MGSP.getIpAddress(), });
					String commandDisconnect53 = MessageFormat.format(ip.getCommandDisconnect(), new String[]{ ip.getShareIp(), });
					try {
						Runtime.getRuntime().exec(commandDisconnect53);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						log.error(e);
						e.printStackTrace();
					}
				}
				
				if(fileNameListBuilder != null){
					if(fileNameListBuilder.indexOf(",") != -1){
						ip.setFileNameList(fileNameListBuilder.substring(0, fileNameListBuilder.lastIndexOf(",")));
					}else{
						ip.setFileNameList(fileNameListBuilder.toString());
					}

				}
				if(Boolean.parseBoolean(ip.getSendEmailNotification())){
					EmailUtil.sendHtmlEmail(ip);
				}
				
				log.info("run() : $$$$ End of " + this.getClass().getName() + " $$$$ ");				
			}
		}
*/

		
/*		
		if (System.currentTimeMillis() - scheduledExecutionTime() >= Long.parseLong(MGSP.getMaxTardiness())){
			return;
		}		
		StringBuilder fileNameListBuilder = null;
		try {
			try{
				PropertiesUtil.loadBeanProperties(ARGS, MGSP);
				if(MGSP != null){
					HL7MainClass.startProcess(MGSP);
				}else{
					throw new IllegalArgumentException("null MiqsGhsSavisProperties");
				}
				waiting(20);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			copytoArchive();

			waiting(20);
			//String commandConnect53 = "cmd /c net use \\\\" + ipAddress53 + "\\ipc$ /user:" + userName53 + " " + password;
			String commandConnect53 = MessageFormat.format(MGSP.getCommandConnect(), new String[]{ MGSP.getIpAddress(), MGSP.getNetUid(), MGSP.getNetPwd(), });
			Runtime.getRuntime().exec(commandConnect53);
		    waiting(40);
			String fileName = "";
			File src = new File(MGSP.getPropertys().getMyPath());
			////System.out.println("run(): src: " + src.toString());
		    File[] listOfFiles = src.listFiles();
			if((listOfFiles != null) && (listOfFiles.length > 0)){
				fileNameListBuilder = new StringBuilder();
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						fileName = listOfFiles[i].getName();
						fileNameListBuilder.append(fileName).append(",");
						try {
							TimerMain.moveFileUsingIPCandIOStream(fileName);
						} catch (FileNotFoundException fnfex) {
							//handle
							System.err.println( " EXCEPTION ::::: "  + fnfex );
						}
					}
				}//for
			}
			//=============================================
			File src1 = new File(MGSP.getPropertys().getMyPath());
		    File[] listOfFiles1 = src1.listFiles();
			if((listOfFiles1 != null) && (listOfFiles1.length > 0)){
		    	System.err.println("No. of files missed :" + listOfFiles1.length);
				System.err.println("No. of files missed in Initial Transfer : " + listOfFiles1.length );
				for (int x = 0; x < listOfFiles.length; x++) {
					if (listOfFiles[x].isFile()) {
						fileName = listOfFiles[x].getName();
						try {
							//System.out.println("RE-TRANSFERING Files missed :" + fileName.toString());
							//waiting(15);
							TimerMain.moveFileUsingIPCandIOStream(fileName);
						}catch(FileNotFoundException fnfexp) {
							//handle
							System.err.println( " EXCEPTION while tansering missed files ::::: "  + fnfexp );
						}
					}//if
				}//for
			}//if
			//==============================================
		}catch (IOException e) {
			e.printStackTrace();
			//System.out.println(" ::: Exception in run() method : " + e );
		}finally {
			//String commandDisconnect144 = "cmd /c net use \\\\" + ipAddress144	+ "\\ipc$ /d";
			//String commandDisconnect53 = "cmd /c net use \\\\" + ipAddress53	+ "\\ipc$ /d";
			String commandDisconnect53 = MessageFormat.format(MGSP.getCommandDisconnect(), new String[]{ MGSP.getIpAddress(), });
			try {
				//Runtime.getRuntime().exec(commandDisconnect144);
				Runtime.getRuntime().exec(commandDisconnect53);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(fileNameListBuilder != null){
			if(fileNameListBuilder.indexOf(",") != -1){
				MGSP.setFileNameList(fileNameListBuilder.substring(0, fileNameListBuilder.lastIndexOf(",")));
			}else{
				MGSP.setFileNameList(fileNameListBuilder.toString());
			}

		}
		if(Boolean.parseBoolean(MGSP.getSendEmailNotification())){
			EmailUtil.sendHtmlEmail(MGSP);
		}
		
		//System.out.println("run() : $$$$ End of EMR MIQS/GHS process $$$$ ");
*/		
	}//run
		
	
	@Override
	public void movedToShared(String fileName) throws IOException {
		if(this.ip != null){
			if(fileName != null){
				try {
					StringBuilder builder = new StringBuilder();
					//builder.append(MGSP.getPropertys().getMyPath()).append(fileName);
					builder.append(ip.getLocalFolder()).append(fileName);
					log.debug("moveToShared(): builder: " + builder.toString());
					File fileToMove = new File(builder.toString());
					File destinationFile = null;
					
					StringBuilder destinationBuilder = new StringBuilder();
					
					//if((fileName.indexOf("FIRE") != -1) || (fileName.indexOf("SAND") != -1)){
					if((fileName.indexOf(ip.getTokenFIRE()) != -1) || (fileName.indexOf(ip.getTokenSAND()) != -1)){
						destinationFile = new File(ip.getShareFolderNBA());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolderNBA() + fileName);
						destinationBuilder.append(ip.getShareFolderNBA()).append(fileName).append(".temp");
					//}else if(fileName.indexOf("ERIE") != -1){
					}else if(fileName.indexOf(ip.getTokenERIE()) != -1){
						destinationFile = new File(ip.getShareFolderERIE());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolderERIE() + fileName);
						destinationBuilder.append(ip.getShareFolderERIE()).append(fileName).append(".temp");
					}else if((fileName.indexOf(ip.getTokenMILL()) != -1) ||
							(fileName.indexOf(ip.getTokenOCEAN()) != -1) ||
							(fileName.indexOf(ip.getTokenKISE()) != -1) ||
							(fileName.indexOf(ip.getTokenDOJD()) != -1) ||
							(fileName.indexOf(ip.getTokenBDIAL()) != -1)){
							destinationFile = new File(ip.getShareFolderM4());
							if(!destinationFile.exists()){
								destinationFile.mkdirs();
							}
							//destinationFile = new File(MGSP.getShareFolderM4() + fileName);
							destinationBuilder.append(ip.getShareFolderM4()).append(fileName).append(".temp");
					}else if((fileName.indexOf(ip.getTokenROGO()) != -1)){
						destinationFile = new File(ip.getShareFolderMIQS());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolderMIQS() + fileName);
						destinationBuilder.append(ip.getShareFolderMIQS()).append(fileName).append(".temp");
					}else if((fileName.indexOf(ip.getTokenUMD()) != -1)){
						destinationFile = new File(ip.getShareFolderUMD());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolderUMD() + fileName);
						destinationBuilder.append(ip.getShareFolderUMD()).append(fileName).append(".temp");
					}else if((fileName.indexOf(ip.getTokenGHS()) != -1)){
						destinationFile = new File(ip.getShareFolderGHS());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolderGHS() + fileName);
						destinationBuilder.append(ip.getShareFolderGHS()).append(fileName).append(".temp");
					}else if((fileName.indexOf(ip.getTokenEPIC()) != -1)){
						destinationFile = new File(ip.getShareFolderEPIC());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolderEPIC() + fileName);
						destinationBuilder.append(ip.getShareFolderEPIC()).append(fileName).append(".temp");
					}else{
						destinationFile = new File(ip.getShareFolder());
						if(!destinationFile.exists()){
							destinationFile.mkdirs();
						}
						//destinationFile = new File(MGSP.getShareFolder() + fileName);
						destinationBuilder.append(ip.getShareFolder()).append(fileName).append(".temp");
					}
		
					destinationFile = new File(destinationBuilder.toString());
					
					//move(fileToMove, destinationFile);
					//nioCopy(fileToMove, destinationFile);
					//copy(fileToMove, destinationFile);
					FileUtils.copyFile(fileToMove, destinationFile, true);
					
					rename(destinationBuilder.substring(0, destinationBuilder.lastIndexOf(".")), destinationFile);
					
					FileUtils.forceDelete(fileToMove);
				}catch (Exception ex) {
					// TODO: handle exception
					ex.printStackTrace();
				}
			}			
		}
	}

}
