package com.spectra.asr.dev.service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.spectra.asr.dev.dto.clientmaster.BATCH_DIALYZE;
import com.spectra.asr.dev.dto.clientmaster.BATCH_DIALYZE;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ProcessNCE implements LoadService {


    @FunctionalInterface
    interface readFileText {
        void readText();
        // interface method -> void
        static void _getResultCode(List<String> resultlist){
            AtomicInteger cnt = new AtomicInteger();
            Set<Integer> fields = new TreeSet<>();

            if(resultlist.get(0).startsWith("MSH")) {
                fields.addAll(Arrays.asList(15, 20));
            } else if (resultlist.get(0).startsWith("OBR")) {
                //fields.addAll(Arrays.asList(3,4,5,7));
            } else if (resultlist.get(0).startsWith("OBX")) {
                //fields.addAll(Arrays.asList(3,4,6,7,9,10,11,12,13));
            }

            var newlist =  resultlist.stream()
                    .filter(t -> !t.startsWith(resultlist.get(0)))
                    .map(t -> List.of(t.split("\\^")))
                    .collect(Collectors.toList());

            newlist.stream().forEach(t -> t.forEach(a -> {
                cnt.getAndIncrement();
                if(fields.contains(cnt.get()))
                    log.info("Field: {}:{} {}",resultlist.get(0),cnt.get(),a);

            }));


        }

        static long getResultCount(File file) {
            long count = 0;
            List<String> allLines = new ArrayList<>();
            try {
                allLines =  Files.readAllLines(Paths.get(file.getAbsolutePath())).stream().filter(t -> t.startsWith("MSH")).collect(Collectors.toList());
                //allLines.stream().forEach(t -> log.info("Message : {} ",t));
                count = allLines.stream().filter(t -> t.startsWith("MSH")).count();

            } catch (IOException e) {
                log.error("Error with count: {}",e.getMessage());
            }

            return count;
        }

        // interface method -> return File
        static File read(File file){

            try {
                List<String> allLines = Files.readAllLines(Paths.get(file.getAbsolutePath()));
//                allLines.stream()
//                        .filter(t -> t.startsWith("OBX") || t.startsWith("OBR"))
//                        .forEach(System.out::println);

//                allLines.stream()
//                        //.filter(t -> t.startsWith("MSH") || t.startsWith("OBR"))
//                        .map(t -> List.of(t.split("\\|")))
//                        .forEach(t ->
//                            log.info(String.valueOf(t))
//                        );

                allLines.stream()
                        //.filter(t -> t.startsWith("OBR") || t.startsWith("OBX"))
                        .map(t -> List.of(t.split("\\|")))
                        .forEach(readFileText::_getResultCode);




            } catch (IOException e) {
                log.error(e.getMessage());
            }



            return file;
        }
    }

    private long _getResultCounts(File dir) {

        log.info("Get Results Count: ");
        long count = 0;

        List<File> filelist = Arrays.stream(dir.listFiles()).collect(Collectors.toList());


        count = filelist.stream()
                .map(readFileText::getResultCount)
                .filter(t -> t > 0)
                .reduce(count,(a, b) -> a + b);
        //Arrays.stream(dir.listFiles()).map(getProd::prodName).forEach(System.out::println);

        return count;
    }

    private List<File> copyFile(File dir) {

        List<File> filelist = Arrays.stream(dir.listFiles()).collect(Collectors.toList());


        return filelist.stream().map(readFileText::read ).collect(Collectors.toList());

        //Arrays.stream(dir.listFiles()).map(getProd::prodName).forEach(System.out::println);

    }

    @FunctionalInterface
    interface CopyFileFrom {
        void copyfrom();

        static void copyFileFrom(File infile, File outfile){
            log.info("Production copy from: {} -> {} ",infile,outfile);

              try {

            FileUtils.copyFile(infile, new File(outfile.getAbsolutePath() + File.separator + infile.getName()), true);
            log.info("Production completed : {} CopyTo: {} ",infile,outfile.getAbsolutePath() + File.separator + infile.getName());
            } catch (IOException e) {
               log.error("File copy error : {} ",e.getMessage());
            }
        }

    }




    @Override
    public void startService() {
        log.info("Starting service ...");

        try {
            _parseDialyze();
        } catch (FileNotFoundException e) {
            log.error("Parser error : {}" ,e.getMessage());
        }

    }


    private void _parseDialyze() throws FileNotFoundException {
        log.info("Parse Dialyze Direct");

        Map< String, BATCH_DIALYZE> csvMap = (Map<String, BATCH_DIALYZE>) new CsvToBeanBuilder(new FileReader("c:/batchfiles/dialyze/dialyze_lost.csv"))
                .withType(BATCH_DIALYZE.class)
                .withSkipLines(1)
                .withSeparator('|')
                .withIgnoreQuotations(true)
                .build()
                .parse()
                .stream()
                .collect(Collectors.toMap(BATCH_DIALYZE::getHlabnum, Function.identity(), (a, b) -> b, LinkedHashMap::new));



        csvMap.entrySet().stream().forEach(t -> log.info("Dialyze: {}",t.getKey()));
    }

    private void _readAndCopyResults() throws IOException{
        Map<File,File> fromto = new HashMap<>();
        AtomicLong count = new AtomicLong();

        fromto.put(new File("P:/AL/al_doh/") ,new File("Y:/al_doh"));
        fromto.put(new File("P:/CA/ca_doh/") ,new File("Y:/ca_doh"));
        fromto.put(new File("P:/LA/la_doh_p/") ,new File("Y:/la_doh_p"));
        fromto.put(new File("P:/MD/md_doh/") ,new File("Y:/md_doh"));
        fromto.put(new File("P:/nj_doh/nj_doh/") ,new File("Y:/nj_doh"));
        fromto.put(new File("P:/NM/nm_doh/") ,new File("Y:/nm_doh"));
        fromto.put(new File("P:/OR/or_doh/") ,new File("Y:/or_doh"));
        fromto.put(new File("P:/TX/tx_doh/") ,new File("Y:/tx_doh"));
        fromto.put(new File("P:/NY/ny_doh/") ,new File("P:/NY/archive"));

            fromto.entrySet().forEach(t -> {
                //log.info("Copy from: {} Copy to: {} ", t.getKey(), t.getValue());
                // functional interface copy files to ensemble
                //copyFile(t.getKey()).stream().forEach(infile -> CopyFileFrom.copyFileFrom(infile, t.getValue()));
                // function to get counts
                log.info("Total count: {} for {}",_getResultCounts(t.getKey()),t.getKey().getName());
            });




    }
}
