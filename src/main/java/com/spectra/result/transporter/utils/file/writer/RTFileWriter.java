package com.spectra.result.transporter.utils.file.writer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
@Slf4j
public final class RTFileWriter {
	//static Logger log = Logger.getLogger(RTFileWriter.class);
	
	public static boolean write(String filePath, String content){
		boolean wrote = false;
		if((filePath != null) && (content != null)){
			File localFile = new File(filePath);
			FileWriter fw = null;
			BufferedWriter bw = null;
			try{
				if(!localFile.exists()){
					localFile.createNewFile();
				}			
				fw = new FileWriter(localFile, true);
				bw = new BufferedWriter(fw);
				bw.write(content);
				wrote = true;
			}catch(IOException ioe){
				ioe.printStackTrace();
				return Boolean.FALSE.booleanValue();
			}finally{
				if(bw != null){
					try{
						bw.close();
					}catch(IOException ioe){
						ioe.printStackTrace();
						return Boolean.FALSE.booleanValue();
					}
				}
				if(fw != null){
					try{
						fw.close();
					}catch(IOException ioe){
						ioe.printStackTrace();
						return Boolean.FALSE.booleanValue();
					}
				}
			}
		}
		return wrote;
	}
	
	public static boolean writeWorkbook(String filePath, Workbook wb){
		boolean wrote = false;
		if((filePath != null) && (wb != null)){
			File localFile = new File(filePath);
			FileOutputStream fos = null;
			try{
				if(!localFile.exists()){
					localFile.createNewFile();
				}
				fos = new FileOutputStream(localFile);
				wb.write(fos);
				wrote = true;
			}catch(Exception e){
				log.error(e.getMessage());
	            //e.printStackTrace(System.err);
	            e.printStackTrace();
	            System.exit(-1);
	        }finally{
	        	if(fos != null){
	        		try{
	        			fos.close();
	        		}catch(IOException ioe){
	        			ioe.printStackTrace();
	        		}
	        	}
	        }
		}
		return wrote;
	}
	
	public static boolean moveFileToDir(String srcDir, String destDir, String fileName) throws IOException {
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
	
	public static boolean copyFile(String srcDir, String destDir, String fileName) throws IOException {
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
}
