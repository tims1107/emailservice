package com.spectra.asr.dev.dto.clientmaster;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BATCH_DIALYZE {


    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "DL ID",required = true)
    private String corporation;

    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "DL Name",required = true)
    private String hlabnum;

    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "Status",required = true)
    private String facilityname;



}
