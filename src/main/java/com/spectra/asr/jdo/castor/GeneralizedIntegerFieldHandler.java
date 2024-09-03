package com.spectra.asr.jdo.castor;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

public class GeneralizedIntegerFieldHandler extends GeneralizedFieldHandler {

    /* (non-Javadoc)
     * @see org.exolab.castor.mapping.GeneralizedFieldHandler#convertUponGet(java.lang.Object)
     */

    public Object convertUponGet(Object arg0) {
        if(arg0 == null){
            return arg0;
        }
        Integer integer = (Integer)arg0;
        return integer;
    }

    /* (non-Javadoc)
     * @see org.exolab.castor.mapping.GeneralizedFieldHandler#convertUponSet(java.lang.Object)
     */

    public Object convertUponSet(Object arg0) {
        Integer integer = new Integer((String)arg0);
        return integer;
    }

    /* (non-Javadoc)
     * @see org.exolab.castor.mapping.GeneralizedFieldHandler#getFieldType()
     */

    public Class getFieldType() {
        return Integer.class;
    }

    public Object newInstance(Object parent) throws IllegalStateException{
        return null;
    }    
}

