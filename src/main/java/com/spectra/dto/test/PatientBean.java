package com.spectra.dto.test;

import com.opencsv.bean.CsvBindByPosition;

public class PatientBean {

    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "DL ID",required = true)
    private String ordernumber;

    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "DL Name",required = true)
    private String lastname;

}
