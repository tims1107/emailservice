package com.spectra.asr.app;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.Segment;
import ca.uhn.hl7v2.model.Structure;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_ORDER_OBSERVATION;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT;
import ca.uhn.hl7v2.model.v251.group.ORU_R01_PATIENT_RESULT;
import ca.uhn.hl7v2.model.v251.message.ORU_R01;
import ca.uhn.hl7v2.model.v251.segment.*;
import ca.uhn.hl7v2.parser.CanonicalModelClassFactory;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.parser.ParserConfiguration;
import ca.uhn.hl7v2.parser.PipeParser;
import ca.uhn.hl7v2.util.Terser;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.spectra.asr.hl7.creator.v251.ILHL7Creator;
import com.spectra.asr.utils.file.writer.AsrFileWriter;
import com.spectra.controller.NCOnboardController;
import com.spectra.dto.test.PatientRecord;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class NCHL7Main {

    private String pathway;

    private static String micro_qry = "select * from vw_batch_tests";
    private static String BATCH_QRY = "select * from vw_batch_tests " +
            "where REQUISITIONID IN " +
            "(" +
            "'0877KJ4'" +
            ",'7387T46'" +
            ",'7539FF6'" +

            ")";

    private NCOnboardController microController;

    public static void main(String[] args) {
        new NCHL7Main(args);
    }

    NCHL7Main(String [] args) {
        this.pathway = "patientrecord.csv";

        //createMapTest();
        try {
            microController = new NCOnboardController(args);
            //microController._runProcess(micro_qry,"IL");
            microController._runProcess(BATCH_QRY,"NC");
            //createTerser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }







}
