package com.spectra.dto.batch_csv;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.Manager;

import java.awt.geom.Area;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TRANSFER_CSV {
// hlabnum
    // fromlab
    // tolab
    // process


    @CsvBindByPosition(position = 0)
    //@CsvBindByName(column = "DL ID",required = true)
    private String hlabnum;

    @CsvBindByPosition(position = 1)
    //@CsvBindByName(column = "DL Name",required = true)
    private String fromlab;

    @CsvBindByPosition(position = 2)
    //@CsvBindByName(column = "Status",required = true)
    private String tolab;

    @CsvBindByPosition(position = 3)
    //@CsvBindByName(column = "Status Date",required = true)
    private String process;


}

