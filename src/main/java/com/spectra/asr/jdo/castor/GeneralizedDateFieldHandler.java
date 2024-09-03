package com.spectra.asr.jdo.castor;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

import com.spectra.asr.constants.ApplicationConstants;

public class GeneralizedDateFieldHandler extends GeneralizedFieldHandler {
    
    public GeneralizedDateFieldHandler(){
        super();
    }

    /* (non-Javadoc)
     * @see org.exolab.castor.mapping.GeneralizedFieldHandler#convertUponGet(java.lang.Object)
     */

    public Object convertUponGet(Object arg0) {
        if(arg0 == null){
            return arg0;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstants.DP_DATE_ONLY);
        Date date = (Date)arg0;
        return sdf.format(date);
    }

    /* (non-Javadoc)
     * @see org.exolab.castor.mapping.GeneralizedFieldHandler#convertUponSet(java.lang.Object)
     */

    public Object convertUponSet(Object arg0) {
        SimpleDateFormat sdf = new SimpleDateFormat(ApplicationConstants.DP_DATE_ONLY);
        Date date = null;
        try{
            date = sdf.parse((String)arg0);
        }catch(ParseException pe){
            pe.printStackTrace();
            throw new IllegalArgumentException(pe.getMessage());
        }
        return date;
    }

    /* (non-Javadoc)
     * @see org.exolab.castor.mapping.GeneralizedFieldHandler#getFieldType()
     */

    public Class getFieldType() {
        return Date.class;
    }
    
    public Object newInstance(Object parent) throws IllegalStateException{
        return null;
    }

}

