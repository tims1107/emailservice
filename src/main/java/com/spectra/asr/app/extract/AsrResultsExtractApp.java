package com.spectra.asr.app.extract;

import com.spectra.asr.app.abnormal.AbnormalHL7LocalApp;
import com.spectra.asr.dao.factory.AsrDaoFactory;
import com.spectra.asr.dao.ora.hub.portal.PortalDao;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
public class AsrResultsExtractApp {
	//private static Logger log = Logger.getLogger(AsrResultsExtractApp.class);
	
	public static void main(String[] args){
		if(args.length == 5){
			String state = args[0];
			String day = args[1];
			String month = args[2];
			String year = args[3];
			String otc = args[4];
			
			log.warn("main(): state: " + (state == null ? "NULL" : state));
			log.warn("main(): day: " + (day == null ? "NULL" : day));
			log.warn("main(): month: " + (month == null ? "NULL" : month));
			log.warn("main(): year: " + (year == null ? "NULL" : year));
			log.warn("main(): otc: " + (otc == null ? "NULL" : otc));
			
			PortalDao portalDao = (PortalDao)AsrDaoFactory.getDAOImpl(PortalDao.class.getSimpleName());
			if(portalDao != null){
				Map<String, Object> paramMap = new HashMap<String, Object>();
				if(!state.equalsIgnoreCase("null")){
					paramMap.put("state", state);
				}
				if((!day.equalsIgnoreCase("null")) && (!day.equalsIgnoreCase("0"))){
					paramMap.put("day", new Integer(day));
				}
				if((!month.equalsIgnoreCase("null")) && (!month.equalsIgnoreCase("0"))){
					paramMap.put("month", new Integer(month));
				}
				if((!year.equalsIgnoreCase("null")) && (!year.equalsIgnoreCase("0"))){
					paramMap.put("year", new Integer(year));
				}
				
				if(!otc.equalsIgnoreCase("null")){
					String[] otcArray = null;
					if(otc.indexOf(",") != -1){
						otcArray = otc.split(",");
					}else{
						otcArray = new String[]{ otc, };
					}
					List<String> otcList = new ArrayList<String>();
					if(otcArray != null){
						for(String otcElem : otcArray){
							otcList.add(otcElem);
						}
					}
					paramMap.put("otcList", otcList);
				}
				
				int rowsInserted = portalDao.insertIntoTmpPrevResults(paramMap);
				log.warn("main(): rowsInserted: " + (rowsInserted == 0 ? "ZARO" : String.valueOf(rowsInserted)));
			}

		}else{
			log.error("Usage: " + AbnormalHL7LocalApp.class.getSimpleName() + " <state> <day> <month> <year> \"<order test code list>\"");
		}
	}
}
