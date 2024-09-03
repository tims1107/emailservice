package com.spectra.asr.app.timer.task;

import com.spectra.asr.enums.AUDIT_ENUM;
import com.spectra.controller.DataController;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//import org.apache.logging.log4j.BasicConfigurator;
//import org.apache.logging.log4j.LogManager;

@Slf4j
public class TestMain {


	//private static Logger logger = LoggerFactory.getLogger(LegacyDataMain.class);


	private DataController controller;


	public TestMain(String args[])  {

		System.out.println(String.format("%s","Testing"));


		AddShutDownHook hook = new AddShutDownHook();
		hook.attachShutdownHook();

		try {
			controller = new DataController(args);

			run();

		} catch (IOException e ) {
			log.error(e.getMessage());
		}

	}


	public static void main(String[] args) {


		try {

			new TestMain(args);
			log.debug("Start main()");

			List<String> list = Arrays.asList("a","b");
			list.stream().forEach(System.out::println);




		} catch (NumberFormatException e) {
			log.error("Listener not started " + e.getMessage());
		}

	}

	private void run(){
		// Get Legacy Data
		Arrays.stream(AUDIT_ENUM.values()).forEach(System.out::println);
		try
		{

			controller.runProcess(AUDIT_ENUM.MORE_ACCOUNT_INFO);


		} catch (Exception e){
			log.error(e.getMessage());
		}

	}

	

}
