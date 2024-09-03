package com.spectra.result.transporter.executor;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

//import com.util.DBUtil;
//import com.spectra.hlab.nys.util.DBUtil;
//import com.spectra.hlab.nys.process.thread.StreamReaderThread;;

@Slf4j
public class WinCmdExecutor {
	//public log log  = log.getlog(WinCmdExecutor.class);
	
	/**
	 * @param args
	 */
	
	//static final log log = log.getlog("SetSessionPath");
	/*
		INFO   | jvm 1    | 2017/09/15 15:11:58 | cmd : netstart.bat "//njnas01/CommonFS/JohnShen/test/spectraHLAB_NYC_Results/" phinms pHINM$123 | 
		INFO   | jvm 1    | 2017/09/15 15:11:58 | bat : netstart.bat "//njnas01/CommonFS/JohnShen/test/spectraHLAB_NYC_Results/" phinms pHINM$123 | 
		INFO   | jvm 1    | 2017/09/15 15:11:58 | Error : The option //NJNAS01/COMMONFS/JOHNSHEN/TEST/SPECTRAHLAB_NYC_RESULTS/ is unknown.
		INFO   | jvm 1    | 2017/09/15 15:11:58 | 
		INFO   | jvm 1    | 2017/09/15 15:11:58 | The syntax of this command is:
		INFO   | jvm 1    | 2017/09/15 15:11:58 | 
		INFO   | jvm 1    | 2017/09/15 15:11:58 | NET USE
		INFO   | jvm 1    | 2017/09/15 15:11:58 | [devicename | *] [\\computername\sharename[\volume] [password | *]]
		INFO   | jvm 1    | 2017/09/15 15:11:58 |         [/USER:[domainname\]username]
		INFO   | jvm 1    | 2017/09/15 15:11:58 |         [/USER:[dotted domain name\]username]
		INFO   | jvm 1    | 2017/09/15 15:11:58 |         [/USER:[username@dotted domain name]
		INFO   | jvm 1    | 2017/09/15 15:11:58 |         [/SMARTCARD]
		INFO   | jvm 1    | 2017/09/15 15:11:58 |         [/SAVECRED]
		INFO   | jvm 1    | 2017/09/15 15:11:59 |         [[/DELETE] | [/PERSISTENT:{YES | NO}]]
		INFO   | jvm 1    | 2017/09/15 15:11:59 | 
		INFO   | jvm 1    | 2017/09/15 15:11:59 | NET USE {devicename | *} [password | *] /HOME
		INFO   | jvm 1    | 2017/09/15 15:11:59 | 
		INFO   | jvm 1    | 2017/09/15 15:11:59 | NET USE [/PERSISTENT:{YES | NO}]
		INFO   | jvm 1    | 2017/09/15 15:11:59 | 
		INFO   | jvm 1    | 2017/09/15 15:11:59 | More help is available by typing NET HELPMSG 3506. 
	 */
	
	
	private void getdir(File file){
		
		if(file.exists())
			//log.error("Get Dir Exist : " + file);
			log.debug("Get Dir Exist : " + file+", " + new java.util.Date());
	}
	
	public void exec(String cmd) throws Exception{
		if((cmd != null)){
			log.debug("exec(): cmd : " + cmd);
			execNetstart(cmd);
		}
	}
	
	private void execNetstart(String bat) throws IOException, InterruptedException{
		if(bat != null){
			StringBuffer out = new StringBuffer();
			StringBuffer err = new StringBuffer();
			
			java.lang.Process proc;
			StreamReaderThread outthread,errthread;
			String [] lines;
			
			log.debug("execNetstart(): bat : " + bat);
			
			try {
				proc = Runtime.getRuntime().exec(bat);
				
				outthread = new StreamReaderThread(proc.getInputStream(),out);
				errthread = new StreamReaderThread(proc.getErrorStream(),err);
				outthread.start();
				errthread.start();
				proc.waitFor();
				
				outthread.join();
				errthread.join();
				
				lines = out.toString().split("\n");
				
				log.debug("execNetstart():Error : " + err.toString());
				log.debug("execNetstart():out : " + out.toString());
			} finally {
				proc = null;
				outthread = null;
			}
		}
	}	
	
	public void run(String cmd){

		/**
		 *  serverqa.inbounddir=
		 *  //Njensemblesrv01/spectraHLAB_ami_east_inbound_label_app|
		 *  //njensemblesrv01/spectraHLAB_soarian_inbound_label_app|
		 *  //njensemblesrv02/spectraemr incoming orders| 
		 * 
		 * 
		 */
		
		if((cmd != null) && (cmd.indexOf("njvwresult01p.spectraeastnj.com") == -1)){
			//log.debug(cmd);
			log.debug("cmd : " + cmd);
			
			_netstart(cmd);
		}
		
//		if (option == 1) {
//
//			log.debug("option == 1");
//			
//			try {
//				
//				//netstat(""); // = ("net use")
//				
//				_netstart("", "netstart.bat");
//				// = net use "\\Njensemblesrv01\spectraHLAB_ami_east_inbound_label_app" /USER:sambausr l3tm3in 
//
//				netstat("");  // = ("net use")
//				
//			} catch (Exception e){
//				log.error("Error : " , e);
//			}
//			
//			return;
//			
//		} else {
//
//			log.debug("option == 0");
//
//			//  File dir = new File("//njensemblesrv01/labelapp orders");
//
//			File dir = new File("//Njensemblesrv01/spectraHLAB_ami_east_inbound_label_app");
//		
//			try {
//				
//				if(dir.exists()) log.debug("### Exists : " + dir);
//				if(!dir.exists()) log.debug("### NOT ### Exists : " + dir);
//				
//				netstat(""); // = ("net use")
//
//				if(!dir.exists()){
//
//					_netstart("", "netstart.bat");
//					// = net use "\\Njensemblesrv01\spectraHLAB_ami_east_inbound_label_app" /USER:sambausr l3tm3in 
//					
//					if(dir.exists())
//						log.debug("### EXISTS, After Net Use Run Exists : " + dir);
//				} else {
//					log.debug("Exists : " + dir);
//				}
//				
//				netstat("");
//				
//			} catch (Exception e){
//				log.error("Error : " , e);
//			}
//		}		
	}
	
	private void _netstart(String bat){
		StringBuffer out = new StringBuffer();
		StringBuffer err = new StringBuffer();
		
		java.lang.Process proc;
		StreamReaderThread outthread,errthread;
		String [] lines;
		
		//log.debug(bat);
		log.debug("bat : " + bat);
		
		try {
			//"net use \"\\\\njensemblesrv01\\labelapp orders\" /USER:sambausr l3tm3in"
			
			//proc = Runtime.getRuntime().exec("netstart.bat");
			
			proc = Runtime.getRuntime().exec(bat);
			
			outthread = new StreamReaderThread(proc.getInputStream(),out);
			errthread = new StreamReaderThread(proc.getErrorStream(),err);
			outthread.start();
			errthread.start();
			proc.waitFor();
			
			outthread.join();
			errthread.join();
			
			lines = out.toString().split("\n");
			
			//log.error(err.toString());
			//log.error(out.toString());
			log.debug("Error : " + err.toString());
			log.debug("out : " + out.toString());
			
			try {
//				Pattern Regex = Pattern.compile("(.*" + port + ".*)|(STATE.*)",
//						Pattern.CANON_EQ | Pattern.MULTILINE);
//				Matcher RegexMatcher = Regex.matcher(out.toString());
//				while (RegexMatcher.find()){
//					//System.out.println(RegexMatcher.group());
//				}
			} catch (PatternSyntaxException pse){
				
			}
			
			
		} catch (Exception e){
			//log.error("Error",e);
			log.debug("Error : " + e);
		} finally {
			proc = null;
			outthread = null;
		}
	}
	
	private void netstat(String port){
		StringBuffer out = new StringBuffer();
		StringBuffer err = new StringBuffer();
		
		java.lang.Process proc;
		StreamReaderThread outthread,errthread;
		String [] lines;
		
		//System.out.println(port);
		try {
			proc = Runtime.getRuntime().exec("net use");
			outthread = new StreamReaderThread(proc.getInputStream(),out);
			errthread = new StreamReaderThread(proc.getErrorStream(),err);
			outthread.start();
			errthread.start();
			proc.waitFor();
			
			outthread.join();
			errthread.join();
			
			lines = out.toString().split("\n");
			
			log.debug("Error : " + err.toString());
			log.debug("out : " + out.toString());
//			log.error(err.toString());
//			log.error(out.toString());
			
			try {
				Pattern Regex = Pattern.compile("(.*" + port + ".*)|(STATE.*)",
						Pattern.CANON_EQ | Pattern.MULTILINE);
				Matcher RegexMatcher = Regex.matcher(out.toString());
				while (RegexMatcher.find()){
					//System.out.println(RegexMatcher.group());
				}
			} catch (PatternSyntaxException pse){
				
			}
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			proc = null;
			outthread = null;
		}
	}
}
