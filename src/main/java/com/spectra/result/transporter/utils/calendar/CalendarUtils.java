package com.spectra.result.transporter.utils.calendar;

import lombok.extern.slf4j.Slf4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
@Slf4j
public final class CalendarUtils {
	//static Logger log = Logger.getLogger(CalendarUtils.class);
	/*
	public static int getPrevMonth(final Calendar cal){
		int prevMon = -1;
		if(cal != null){
			int currMonth = cal.get(Calendar.MONTH);
			if(currMonth == Calendar.JANUARY){
				currMonth = Calendar.DECEMBER;
			}else{
				currMonth = currMonth - 1;
			}
			prevMon = currMonth + 1;
		}
		return prevMon;
	}
	*/
	public static int getPrevMonth(final Calendar cal){
		int prevMonth = -1;
		if(cal != null){
			int currMonth = cal.get(Calendar.MONTH);
			if(currMonth == Calendar.JANUARY){
				currMonth = Calendar.DECEMBER;
			}else{
				currMonth = currMonth - 1;
			}
			prevMonth = currMonth + 1;
		}
		return prevMonth;
	}	
	
	public static Integer getPrevMonthAsInteger(final Calendar cal) {
		Integer prevMonth = null;
		if(cal != null) {
			int prevMon = getPrevMonth(cal);
			prevMonth = new Integer(prevMon);
		}
		return prevMonth;
	}
	
	public static String getPrevMonthAsString(final Calendar cal){
		String prevMonth = null;
		if(cal != null){
			cal.add(Calendar.MONTH, -1);
			Date dt = cal.getTime();
			DateFormat df = new SimpleDateFormat("MMM");
			prevMonth = df.format(dt);
		}
		return prevMonth;
	}
	
	public static int getPrevMonthYear(final Calendar cal) {
		int yr = -1;
		if(cal != null) {
			yr = cal.get(Calendar.YEAR);
			int currMonth = cal.get(Calendar.MONTH);
			if(currMonth == Calendar.JANUARY){
				yr -= 1;
			}
		}
		return yr;
	}
	
	public static Integer getPrevMonthYearAsInteger(final Calendar cal) {
		Integer prevMonthYear = null;
		if(cal != null){
			int yr = getPrevMonthYear(cal);
			prevMonthYear = new Integer(yr);
		}
		return prevMonthYear;
	}
	
	public static int getPrevDayOfMonth(final Calendar cal){
		int dom = -1;
		if(cal != null){
			cal.add(Calendar.DATE, -1);
			dom = cal.get(Calendar.DAY_OF_MONTH);
		}
		return dom;
	}
	
	public static Integer getPrevDayOfMonthAsInteger(final Calendar cal){
		Integer prevDom = null;
		if(cal != null){
			int dom = getPrevDayOfMonth(cal);
			prevDom = new Integer(dom);
		}
		return prevDom;
	}
	
	public static int getCurrentMonth(final Calendar cal){
		int currMonth = -1;
		if(cal != null){
			currMonth = cal.get(Calendar.MONTH);
			currMonth = (currMonth + 1);
		}
		/*if(currMonth == Calendar.JANUARY){
			currMonth = Calendar.DECEMBER;
		}else{
			currMonth = currMonth - 1;
		}*/
		return currMonth;
	}
	
	public static Integer getCurrentMonthAsInteger(final Calendar cal){
		Integer currMon = null;
		if(cal != null){
			int curr = getCurrentMonth(cal);
			currMon = new Integer(curr);
		}
		return currMon;
	}
}
