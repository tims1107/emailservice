package com.spectra.asr.service.eip.extract;

import com.spectra.asr.businessobject.factory.AsrBoFactory;
import com.spectra.asr.businessobject.ora.hub.AsrBo;
import com.spectra.asr.utils.calendar.CalendarUtils;
import com.spectra.scorpion.framework.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;

@Slf4j
public class EipResultsExtractServiceImpl implements EipResultsExtractService {
	//private Logger log = Logger.getLogger(EipResultsExtractServiceImpl.class);
	
	@Override
	public Integer extractResults() throws BusinessException {
		Calendar now = Calendar.getInstance();
		Integer prevMonth = CalendarUtils.getPrevMonthAsInteger(now);
		log.warn("extractResults(): prevMonth: " + (prevMonth <= 0 ? "ZARO" : prevMonth.toString()));
		
		now = Calendar.getInstance();
		Integer prevMonthYear = CalendarUtils.getPrevMonthYearAsInteger(now);
		log.warn("extractResults(): prevMonthYear: " + (prevMonthYear <= 0 ? "ZARO" : prevMonthYear.toString()));
		
		now = Calendar.getInstance();
		Integer prevDayOfMonth = CalendarUtils.getPrevDayOfMonthAsInteger(now);
		log.warn("extractResults(): prevDayOfMonth: " + (prevDayOfMonth <= 0 ? "ZARO" : prevDayOfMonth.toString()));
		
		now = Calendar.getInstance();
		Integer currMonth = CalendarUtils.getCurrentMonthAsInteger(now);
		log.warn("extractResults(): currMonth: " + (currMonth <= 0 ? "ZARO" : currMonth.toString()));
		
		now = Calendar.getInstance();
		Integer currDayOfMonth = CalendarUtils.getCurrentDayOfMonthAsInteger(now);
		log.warn("extractResults(): currDayOfMonth: " + (currDayOfMonth <= 0 ? "ZARO" : currDayOfMonth.toString()));
		
		now = Calendar.getInstance();
		Integer currMonthYear = CalendarUtils.getCurrentMonthYearAsInteger(now);
		log.warn("extractResults(): currMonthYear: " + (currMonthYear <= 0 ? "ZARO" : currMonthYear.toString()));
		return this.extractResults(prevMonth, prevMonthYear);
	}

	@Override
	public Integer extractResults(Integer prevMonth, Integer prevMonthYear) throws BusinessException {
		Integer rowsExtracted = null;
		if((prevMonth != null) && (prevMonthYear != null)){
			AsrBo asrBo = AsrBoFactory.getAsrBo();
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("p_month", new Integer(prevMonth));
			paramMap.put("p_year", new Integer(prevMonthYear));
			
			try{
				rowsExtracted = asrBo.callEipResultsExtract(paramMap);
				log.warn("extractResults(): rowsExtracted: " + (rowsExtracted == null ? "NULL" : rowsExtracted.toString()));
			}catch(BusinessException be){
				log.error(String.valueOf(be));
				be.printStackTrace();
			}			
		}
		return rowsExtracted;
	}

}
