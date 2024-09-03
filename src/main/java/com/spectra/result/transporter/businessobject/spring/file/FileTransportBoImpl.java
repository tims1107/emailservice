package com.spectra.result.transporter.businessobject.spring.file;

import com.spectra.result.transporter.properties.ResultInterfaceProperties;
import com.spectra.result.transporter.utils.props.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
@Slf4j
public abstract class FileTransportBoImpl implements FileTransportBo {
	//private Logger log = Logger.getLogger(FileTransportBoImpl.class);

	protected PropertiesUtil propertiesUtil;
	protected ResultInterfaceProperties ip;
	
	@Override
	public void setPropertiesUtil(PropertiesUtil propertiesUtil) {
		this.propertiesUtil = propertiesUtil;
		ip = (ResultInterfaceProperties)this.propertiesUtil.loadBeanProperties();
	}
	
	protected boolean copytoArchive() {
		boolean copiedToArchive = true;
		if((this.propertiesUtil != null) && (ip != null)){
			String fileName = "";
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String year  = dateFormat.format(date).substring(0, 4);
			String month = dateFormat.format(date).substring(4, 6);
	    	String source = ip.getLocalFolder();
			File src = new File(source);
		    File[] listOfFiles = src.listFiles();
			if((listOfFiles != null) && (listOfFiles.length > 0)){
				for(int i = 0; i < listOfFiles.length; i++){
					if (listOfFiles[i].isFile()) {
						fileName = listOfFiles[i].getName();
						String filesource = source + fileName;
						File filesrc = new File(filesource);
						//String destination = MGSP.getPropertys().getMyArch() + year + "_" + month + "\\" + fileName;
						//String destDir = MGSP.getPropertys().getMyArch() + year + "_" + month;
						String destination = ip.getArchiveFolder() + year + "_" + month + "\\" + fileName;
						String destDir = ip.getArchiveFolder() + year + "_" + month;
						File dst = new File(destDir);
						if(!dst.exists()){
							dst.mkdirs();
						}
						dst = new File(destination);
						if (!dst.exists()) {
							dst = new File(destination);
						}
						try {
							//copyDirectory(filesrc, dst);
							FileUtils.copyFile(filesrc, dst, true);
						}catch (IOException e) {
							log.error(String.valueOf(e));
							e.printStackTrace();
							//System.err.println( " EXCEPTION on copytoArchive : " + e );
							copiedToArchive = false;
						}
					}
				}//for
			}			
		}
		return copiedToArchive;
   	}
	
	protected void copyDirectory(File srcPath1, File dstPath1) throws IOException {
		InputStream in = new FileInputStream(srcPath1);
		OutputStream out = new FileOutputStream(dstPath1);
		try{
			in = new FileInputStream(srcPath1);
			out = new FileOutputStream(dstPath1);
			
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
		}finally{
			if(out != null){
				out.close();
			}
			if(in != null){
				in.close();
			}
		}
	}
	
	protected void rename(String destinationBuilderStr, File destinationFile) throws IOException{
		if((this.propertiesUtil != null) && (ip != null)){
			if((destinationBuilderStr != null) && (destinationFile != null)){
				StringBuilder fnBuilder = new StringBuilder();
				//fnBuilder.append(destinationBuilderStr).append(".GHS");
				fnBuilder.append(destinationBuilderStr).append(ip.getResultFileExt());
				String renamedDest = fnBuilder.toString();
				File destinationFileFinal = new File(renamedDest);
				boolean renamed = true;
				int ctr = 0;
				while(ctr < 2){
					renamed = destinationFile.renameTo(destinationFileFinal);
					log.debug("rename: (" + String.valueOf(ctr) + ") renaming " + destinationFile.getName() + " to " + destinationFileFinal.getName());
					if(renamed){
						break;
					}else{
						ctr++;
					}
				}
				if(!renamed){
					log.debug("rename(): failed to rename: " + destinationFile.getName() + " to " + destinationFileFinal.getName());
					throw new IOException("failed to rename: " + destinationFile.getName() + " to " + destinationFileFinal.getName());
				}			
			}			
		}
	}
	
	protected void move(File src, File dest) throws FileNotFoundException, IOException {
		if((src != null) && (dest != null)){
			copy(src, dest);
			//src.delete();
			boolean deleted = src.delete();
			if(!deleted){
				log.debug("move(): failed to delete " + src.getName());
				throw new IOException("move(): failed to delete " + src.getName());
			}
		}
	}

	protected void copy(File src, File dest) throws IOException {
		if((src != null) && (dest != null)){
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				inputStream = new FileInputStream(src);
				outputStream = new FileOutputStream(dest);
				byte[] buf = new byte[10 * 1024];
				int len;
				while ((len = inputStream.read(buf)) > 0) {
					outputStream.write(buf, 0, len);
				}
			} catch (FileNotFoundException fnfe) {
				// handle it
				fnfe.printStackTrace();
				log.error(String.valueOf(fnfe));
				log.debug( " EXCEPTION in copy() method ::::: "  + fnfe );
			} finally {
				if(inputStream != null){
					inputStream.close();
				}
				if(outputStream != null){
					outputStream.close();
				}
			}
		}
	}
	
	public static void nioCopy(File src, File dest) throws Exception {
		if((src == null) || (dest == null)){
			return;
		}
	    FileInputStream fis = new FileInputStream(src);
	    FileOutputStream fos = new FileOutputStream(dest);

	    FileChannel fci = fis.getChannel();
	    FileChannel fco = fos.getChannel();

		//FileLock lock = fco.lock();
		FileLock lock = fco.tryLock();
		////System.out.println("nioCopy(): Lock is shared: " + lock.isShared());
		
		if(lock != null){
		    ByteBuffer buffer = ByteBuffer.allocate(1024);

		    while(true){
		    	int read = fci.read(buffer);

		    	if(read == -1){
		    		break;
		    	}
		    	buffer.flip();
		    	fco.write(buffer);
		    	buffer.clear();
		    }
		    lock.release();
		    //lock.close();		    
		}
	    fco.close();
	    fci.close();
	    fos.close();
	    fis.close();
	}
	
	/*
	protected void nioCopy(File src, File dest) throws Exception {
		if((src == null) || (dest == null)){
			return;
		}
	    FileInputStream fis = null;
	    FileOutputStream fos = null;

	    FileChannel fci = null;
	    FileChannel fco = null;

	    try{
	    	fis = new FileInputStream(src);
	    	fos = new FileOutputStream(dest);
	    	fci = fis.getChannel();
	    	fco = fos.getChannel();
	    	
			//FileLock lock = fco.lock();
			FileLock lock = fco.tryLock();
			////System.out.println("nioCopy(): Lock is shared: " + lock.isShared());
			
			if(lock != null){
			    ByteBuffer buffer = ByteBuffer.allocate(1024);

			    while(true){
			    	int read = fci.read(buffer);

			    	if(read == -1){
			    		break;
			    	}
			    	buffer.flip();
			    	fco.write(buffer);
			    	buffer.clear();
			    }
			    lock.release();
			    //lock.close();		    
			}	    	
	    }finally{
	    	if(fco != null){
	    		fco.close();
	    	}
	    	if(fci != null){
	    		fci.close();
	    	}
	    	if(fos != null){
	    		fos.close();
	    	}
	    	if(fis != null){
	    		fis.close();
	    	}
	    }
	}
	*/
}
