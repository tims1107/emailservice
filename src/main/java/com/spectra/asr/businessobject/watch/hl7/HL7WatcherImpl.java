package com.spectra.asr.businessobject.watch.hl7;

import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.util.Hl7InputStreamMessageIterator;
import ca.uhn.hl7v2.util.Terser;
import com.spectra.asr.businessobject.watch.DirectoryWatcherImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Path;

@Slf4j
public class HL7WatcherImpl extends DirectoryWatcherImpl {
	//private Logger log = Logger.getLogger(HL7WatcherImpl.class);
	
	@Override
	public boolean handleEvent(Path filePath) {
		boolean handled = false;
		if(filePath != null){
			File hl7File = filePath.toFile();
			if((hl7File != null) && (hl7File.exists())){
				//FileReader fr = null;
				//BufferedReader br = null;
				InputStream is = null;
				BufferedInputStream bis = null;
				StringBuilder hl7Builder = null;
				try{
					//fr = new FileReader(hl7File);
					//br = new BufferedReader(fr);
					//hl7Builder = new StringBuilder();
					//String line = null;
					//while((line = br.readLine()) != null){
					//	hl7Builder.append(line).append("\r");
					//}
			        //PipeParser pipeParser = new PipeParser();
			        //Message message = pipeParser.parse(hl7Builder.toString());
			        //Terser terser = new Terser(message);

					
					is = new FileInputStream(hl7File);
					bis = new BufferedInputStream(is);
					
					Hl7InputStreamMessageIterator iter = new Hl7InputStreamMessageIterator(bis);
					if(iter != null){
						while(iter.hasNext()){
							Message msg = iter.next();
					        Terser terser = new Terser(msg);
						}
					}
					
			        
				}catch(FileNotFoundException fnfe){
					log.error(String.valueOf(fnfe));
					fnfe.printStackTrace();
				}catch(IOException ioe){
					log.error(String.valueOf(ioe));
					ioe.printStackTrace();
				}finally{
					if(bis != null){
						try{
							bis.close();
						}catch(IOException ioe){
							log.error(ioe.getMessage());
							ioe.printStackTrace();
						}
					}
					if(is != null){
						try{
							is.close();
						}catch(IOException ioe){
							log.error(ioe.getMessage());
							ioe.printStackTrace();
						}
					}
				}
			}
		}
		return handled;
	}

}
