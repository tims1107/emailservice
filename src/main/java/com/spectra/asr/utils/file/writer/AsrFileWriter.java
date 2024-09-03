package com.spectra.asr.utils.file.writer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//import org.apache.logging.log4j.LogManager;
@Slf4j
public final class AsrFileWriter {
	//static Logger log = Logger.getLogger(AsrFileWriter.class);

	public static void fileChannelWrite(ByteBuffer byteBuffer, String updateFile)
			throws IOException {

		Set options = new HashSet();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.APPEND);



		Path path = Paths.get("./" + updateFile);

		FileChannel fileChannel = FileChannel.open(path, options);
		fileChannel.write(byteBuffer);

		fileChannel.close();
	}

	private final Charset charset = Charset.forName("UTF-8");
	private final CharsetEncoder encoder = charset.newEncoder();
	private final CharsetDecoder decoder = charset.newDecoder();

	private ByteBuffer str_to_bb(String msg, Charset charset){
		return ByteBuffer.wrap(msg.getBytes(charset));
	}

	private String bb_to_str(ByteBuffer buffer, Charset charset){
		byte[] bytes;
		if(buffer.hasArray()) {
			bytes = buffer.array();
		} else {
			bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
		}
		return new String(bytes, charset);
	}


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
				fw = new FileWriter(localFile, false);
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
				log.error(String.valueOf(e));
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
	
	public static boolean moveDir(String srcDir, String destDir) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null)){
			File srcDirFile = new File(srcDir);
			if(!srcDirFile.exists()){
				srcDirFile.mkdirs();
			}
			File destDirFile = new File(destDir);
			if(!destDirFile.exists()){
				destDirFile.mkdirs();
			}
			if(srcDirFile.exists()){
				//FileUtils.moveDirectory(srcDirFile, destDirFile);
				//wrote = true;
				wrote = moveDir(srcDirFile, destDirFile);
			}
		}
		return wrote;
	}
	
	public static boolean moveDir(File srcDir, File destDir) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null)){
			if(srcDir.exists()){
				FileUtils.moveDirectory(srcDir, destDir);
				wrote = true;
			}
		}
		return wrote;
	}
	
	public static boolean moveFilesToDir(File srcDir, File destDir) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null)){
			if(srcDir.exists()){
				Collection<File> listFiles = FileUtils.listFiles(srcDir, null, false);
				if(listFiles != null){
					for(File file : listFiles){
						FileUtils.moveFileToDirectory(file, destDir, true);
					}
					wrote = true;
				}
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
	
	public static boolean copyDir(String srcDir, String destDir) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null)){
			File srcDirFile = new File(srcDir);
			if(!srcDirFile.exists()){
				srcDirFile.mkdirs();
			}
			File destDirFile = new File(destDir);
			if(!destDirFile.exists()){
				destDirFile.mkdirs();
			}
			if(srcDirFile.exists()){
				//FileUtils.copyDirectory(srcDirFile, destDirFile);
				//wrote = true;
				wrote = copyDir(srcDirFile, destDirFile);
			}
		}
		return wrote;
	}
	
	public static boolean copyDir(File srcDir, File destDir) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null)){
			if(srcDir.exists()){
				FileUtils.copyDirectory(srcDir, destDir);
				wrote = true;
			}			
		}
		return wrote;
	}
	
	public static boolean copyFilesToDir(File srcDir, File destDir) throws IOException {
		boolean wrote = false;


		if((srcDir != null) && (destDir != null)){
			if(srcDir.exists()){
				Collection<File> listFiles = FileUtils.listFiles(srcDir, null, false);
				if(listFiles != null){
					for(File file : listFiles){
						try {
							FileUtils.copyFileToDirectory(file, destDir,true);
						} catch (IOException ioe) {
							System.out.println(file.getAbsolutePath() + " " + destDir.getAbsolutePath());
							System.out.println(ioe.getMessage());
						}
						//Files.copy(file.toPath(),destDir.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
					}
					wrote = true;
				}
			}
		}
		return wrote;
	}
	
	public static boolean copyAFileToDir(File srcFile, File destDir) throws IOException {
		boolean wrote = false;
		if((srcFile != null) && (destDir != null)){
			if(srcFile.exists()){
				FileUtils.copyFileToDirectory(srcFile, destDir);
				wrote = true;
			}
		}
		return wrote;
	}
	
	public static boolean copyTempFilesToDir(File srcDir, File destDir) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destDir != null)){
			if(srcDir.exists()){
				Collection<File> listFiles = FileUtils.listFiles(srcDir, null, false);
				if(listFiles != null){
					for(File file : listFiles){
						////log.warn("copyTempFilesToDir(): file: absSrcPath: " + (file == null ? "NULL" : file.getAbsoluteFile()));
						//log.warn("copyTempFilesToDir(): file: absSrcPath: " + (file == null ? "NULL" : file.getAbsolutePath()));
						//log.warn("copyTempFilesToDir(): file: absSrcPath: " + (file == null ? "NULL" : file.getName()));
						
						String srcFileName = file.getName();
						
						String tempFileName = srcFileName + ".temp";
						//log.warn("copyTempFilesToDir(): tempFileName: " + (tempFileName == null ? "NULL" : tempFileName));
						//log.warn("copyTempFilesToDir(): file: absDestPath: " + (destDir == null ? "NULL" : destDir.getAbsolutePath()));
						
						String destDirAbsPath = destDir.getAbsolutePath();
						
						StringBuilder destFilePathBuilder = new StringBuilder();
						destFilePathBuilder.append(destDirAbsPath).append("\\").append(tempFileName);
						
						String tempDestFilePath = destFilePathBuilder.toString();
						//log.warn("copyTempFilesToDir(): tempDestFilePath: " + (tempDestFilePath == null ? "NULL" : tempDestFilePath));
						File tempDestFile = new File(tempDestFilePath);
						FileUtils.copyFile(file, tempDestFile);
						
						String destFilePath = tempDestFilePath.substring(0, tempDestFilePath.indexOf(".temp"));
						File destFile = new File(destFilePath);
						FileUtils.moveFile(tempDestFile, destFile);
					}
					wrote = true;
				}
			}
		}
		return wrote;
	}
	
/*	
	public static boolean copyFilesToDir(File srcDir, String destPath, String[] targetArray, String state) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destPath != null)){
			if(srcDir.exists()){
				Collection<File> listFiles = FileUtils.listFiles(srcDir, null, false);
				if(listFiles != null){
					log.warn("copyFilesToDir(): state: " + (state == null ? "NULL" : state.toString()));
					for(File file : listFiles){
						String fileName = file.getName();
						log.warn("copyFilesToDir(): fileName: " + (fileName == null ? "NULL" : fileName.toString()));
						if(targetArray != null){
							if(state != null){
								String st = null;
								if(fileName.contains(".")){
									String[] fnArray = fileName.split("\\.");
									for(String fn : fnArray){
										log.warn("copyFilesToDir(): fn: " + (fn));
									}
									if(fnArray[0].equals(state)){
										st = fnArray[1];
										for(String target : targetArray){
											log.warn("copyFilesToDir(): target: " + (target == null ? "NULL" : target));
											log.warn("copyFilesToDir(): st: " + (st == null ? "NULL" : st));
											if(target.equals(st)){
												StringBuilder destBuilder = new StringBuilder();
												destBuilder.append(destPath).append(state).append("\\").append(st).append("\\");
												log.warn("copyFilesToDir(): destBuilder: " + (destBuilder == null ? "NULL" : destBuilder.toString()));
												File destDir = new File(destBuilder.toString());
												//if(!destDir.exists()){
												//	boolean created = destDir.mkdirs();
												//}
												FileUtils.copyFileToDirectory(file, destDir);
												wrote = true;
												break;
											}
										}
									}
								}
							}
						}else{
							File destDir = new File(destPath);
							FileUtils.copyFileToDirectory(file, destDir);
							wrote = true;
						}
					}//for
					//wrote = true;
				}
			}
		}
		return wrote;
	}
*/

	public static boolean copyFilesToDir(File srcDir, String destPath, String[] targetArray, String state) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destPath != null)){
			wrote = copyFilesToDir(srcDir, destPath, targetArray, state, null);
		}
		return wrote;
	}
	
	public static boolean copyFilesToDir(File srcDir, String destPath, String[] targetArray, String state, String prevMonthDate) throws IOException {
		boolean wrote = false;
		if((srcDir != null) && (destPath != null)){
			if(srcDir.exists()){
				Collection<File> listFiles = FileUtils.listFiles(srcDir, null, false);
				if(listFiles != null){
					log.warn("copyFilesToDir(): state: " + (state == null ? "NULL" : state.toString()));
					for(File file : listFiles){
						String fileName = file.getName();
						log.warn("copyFilesToDir(): fileName: " + (fileName == null ? "NULL" : fileName.toString()));
						if(targetArray != null){
							if(state != null){
								String st = null;
								if(fileName.contains(".")){
									String[] fnArray = fileName.split("\\.");
									for(String fn : fnArray){
										log.warn("copyFilesToDir(): fn: " + (fn));
									}
									if(fnArray[0].equals(state)){
										st = fnArray[1];
										for(String target : targetArray){
											log.warn("copyFilesToDir(): target: " + (target == null ? "NULL" : target));
											log.warn("copyFilesToDir(): st: " + (st == null ? "NULL" : st));
											if(target.equals(st)){
												StringBuilder destBuilder = new StringBuilder();
												destBuilder.append(destPath).append(state).append("\\").append(st).append("\\");
												if(prevMonthDate != null){
													destBuilder.append(prevMonthDate).append("\\");
												}
												log.warn("copyFilesToDir(): destBuilder: " + (destBuilder == null ? "NULL" : destBuilder.toString()));
												File destDir = new File(destBuilder.toString());
												/*if(!destDir.exists()){
													boolean created = destDir.mkdirs();
												}*/
												FileUtils.copyFileToDirectory(file, destDir);
												wrote = true;
												break;
											}
										}
									}
								}
							}
						}else{
							File destDir = new File(destPath);
							FileUtils.copyFileToDirectory(file, destDir);
							wrote = true;
						}
					}//for
					//wrote = true;
				}
			}
		}
		return wrote;
	}	
	
	public static void deleteDir(File dir) throws IOException {
		if(dir != null){
			FileUtils.deleteDirectory(dir);
		}
	}
}
