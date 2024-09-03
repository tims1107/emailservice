package com.spectra.asr.dev.controller;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.spectra.asr.dev.config.Configure;
import com.spectra.asr.dev.dto.asr.HL7_FILE;
import com.spectra.asr.dev.dto.asr.HL7_FILE2;
import com.spectra.asr.dev.dto.clientmaster.*;
import com.spectra.asr.dev.enums.ASR_ENUM;
import com.spectra.asr.dev.model.DataListener;
import com.spectra.asr.dev.model.OracleViewDAO;
import com.spectra.asr.dev.model.SqlServerViewDAO;
import com.spectra.asr.dev.model.SqlServerViewListener;
import com.spectra.asr.dev.service.LoadService;
import com.spectra.asr.dev.service.factory.LoadServiceFactory;
import com.spectra.asr.dev.dto.clientmaster.CLINIC_COMMENT;
import com.spectra.asr.dev.dto.clientmaster.Facility;
import com.spectra.asr.dev.dto.clientmaster.LEGACY_FACILITY;
import connections.ConcreteDAO;
import connections.ItemURL;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class CreateTestHL7Controller {

    private final Map<String, ItemURL> map;
    //private final Map<String, StatesAPI> statemap;

    private final OracleViewDAO cmOracleDAO;
    private final SqlServerViewDAO CDBHlab;
    private DataListener listener;

    private final String BASE_ENV;
    private List<String> list;


    public void setDataListener(DataListener listener) {
        this.listener = listener;

    }










    public CreateTestHL7Controller(String[] args) throws IOException {


        BASE_ENV = args[4].toLowerCase();

        StringBuffer sb = new StringBuffer();
        StringBuffer envcon = new StringBuffer();

        List<ItemURL> envlist = new LinkedList<ItemURL>();

        map = (Map<String, ItemURL>) Configure.JsonConfigure(getClass().getClassLoader().getResourceAsStream(args[0]), ItemURL.class);


        map.entrySet().stream().map(t -> t.getValue())
                .filter(t -> t.getName().endsWith(BASE_ENV))
                .forEach(t -> log.info("Schema Name: {} Type: [{}]", t.getName(), t.getType()));


        /*
        ===== IHUB DB =======
        state_rpt dev -> proxy_staterpt_owner_dev
        state_rpt prd -> proxy_staterpt_owner_prod

        ===== MDM DB =======
        CM dev -> proxy_cm_owner_dev
        CM prd -> proxy_cm_owner_prod

        ====== Legacy CM ======
        DEV -> cdbhlab_test
        PRD -> cdbhlab_prod

        =================================================

         */


        envcon.append("proxy_cm_owner").append("_").append(BASE_ENV.toLowerCase());
        log.debug(envcon.toString());

        cmOracleDAO = new OracleViewDAO(new ConcreteDAO(map.get(envcon.toString())));
        envcon.setLength(0);

        envcon.append("cdbhlab").append("_").append(BASE_ENV.toLowerCase());
        log.debug(envcon.toString());
        CDBHlab = new SqlServerViewDAO(new ConcreteDAO(map.get(envcon.toString())));


        log.error("System running " + BASE_ENV);

        setListeners();

    }

    private void setListeners() {
        log.info("Setting listeners .........");

        CDBHlab.setListener(new SqlServerViewListener() {
            @Override
            public void findFacility(Map<Integer, Facility> results) {

            }

            @Override
            public void insertFacility(List<LEGACY_FACILITY> list) {
                System.out.println("Facility count: " + list.size());
//                cmOracleDAO.Connect();
//                cmOracleDAO.moveToOracleDB(list,new LEGACY_FACILITY());
//                cmOracleDAO.disconnect();
            }

            @Override
            public void insertFacilityTable(List<FACILITY_TABLE> list) {
                System.out.println("Facility count: " + list.size());
                cmOracleDAO.Connect();
                cmOracleDAO.moveToOracleDB(list, new FACILITY_TABLE());
                cmOracleDAO.disconnect();
            }

            @Override
            public void insertMoreAccountInfo(List<MOREINFO_TABLE> list) {
                System.out.println("Facility count: " + list.size());
                cmOracleDAO.Connect();
                cmOracleDAO.moveToOracleDB(list, new MOREINFO_TABLE());
                cmOracleDAO.disconnect();
            }
        });


    }

    private List<String> _createList(final String path) {
//        final Spliterator<String> splt = Spliterators.spliterator(scanner,Long.MAX_VALUE,Spliterator.ORDERED | Spliterator.NONNULL);
//        return StreamSupport.stream(splt,false)
//                .onClose(scanner::close);
        URL url = CreateTestHL7Controller.class.getResource(path);
        Stream<String> lines = null;
        List<String> list = null;

        try {
            lines = Files.lines(Path.of(path));
            //lines.forEach(System.out::println);
            //Files.lines(Paths.get(url.getPath())).forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list = lines.collect(Collectors.toList());
        return list;

    }


    private List<HL7_FILE> _createList2(final String path) throws FileNotFoundException {
        List beans = new CsvToBeanBuilder(new FileReader(path))
                .withType(HL7_FILE.class)
                //.withSeparator(',')
                .build()
                .parse();

        return beans;

    }

    private Set<Map.Entry<String, HL7_FILE>> _getMessage(List<Map.Entry<String, HL7_FILE>> message, String field) {

        return message.stream().filter(t -> t.getKey().startsWith(field)).collect(Collectors.toSet());


    }

    private <T> List<HL7_FILE2> _createList3(final String path) throws FileNotFoundException {
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger component = new AtomicInteger(1);
        AtomicInteger subcount = new AtomicInteger(1);

        Map<String, HL7_FILE> message = new TreeMap<>();
        StringBuffer sb = new StringBuffer();

        CSVParser parserBuilder = new CSVParserBuilder()
                .withSeparator('|')
                .build();

        try {
            FileReader filereader = new FileReader(path);

            Optional<CSVReader> csvReader = Optional.of(new CSVReader(filereader));


            String[] nextRecord;
            //csvReader.get().readAll().stream().forEach(a -> {

            csvReader.get().readAll().stream().forEach(t -> {
                //System.out.println(Arrays.asList(t));
                Arrays.asList(t).stream().forEach(System.out::println);
                Arrays.asList(t).stream().filter(a -> a.startsWith("OBX")).forEach(a ->
                {
                    Arrays.asList(a.split("\\|")).stream().forEach(p -> {
                        System.out.println(count + " " + p);
//                        if (count.getAcquire() == 23) {
//                            if(p.equalsIgnoreCase("Spectra East^D^^^^Spectra East&31D0961672&CLIA^XX^^^31D0961672")) {
//                                System.out.println("Where good");
//                            }
//                        }

                        // component count
                        Arrays.asList(p.split("\\^")).stream().forEach(c -> {


                            sb.append(count + "." + component);
                            System.out.println(count + "." + component + " " + c);
                            if (c.indexOf("&") == -1)
                                message.put(count + "." + component, new HL7_FILE(sb.toString(), "^", c));

                            sb.setLength(0);

                            // subcount
                            Arrays.asList(c.split("&")).stream().forEach(s -> {

                                if (c.indexOf("&") != -1) {
                                    sb.append(subcount + "." + component);
                                    //System.out.println(count + "." + component + "." + subcount + " " + s);
                                    message.put(count + "." + component + "." + subcount.getAndIncrement(), new HL7_FILE(sb.toString(), "&", s));
                                }
                                sb.setLength(0);
                            });
                            subcount.getAndIncrement();
                            component.getAndIncrement();
                            sb.setLength(0);
                            subcount.set(1);
                        });
                        sb.setLength(0);
                        component.set(1);
                        count.getAndIncrement();
                    });
                });


                count.set(0);

            });

            sb.setLength(0);
            List<Integer> mapTo = Stream.iterate(0, n -> n + 1).limit(25).collect(Collectors.toList());


            mapTo.stream().forEach(m -> {
                _getMessage(message.entrySet().stream().collect(Collectors.toList()), m + ".").stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(t ->
                        {
//                    //Arrays.asList(t.getKey().split("\\.")).stream().forEach(p -> {
//                            System.out.println(p + " -> " + t.getValue().getStrvalue());
//                    //});
                            sb.append(t.getValue().getStrvalue())
                                    .append(t.getValue().getField());

                        });
                System.out.print(sb.toString().substring(0, sb.length() - 1) + "|");
                sb.setLength(0);
            });
            System.out.println();

//            Map<String,HL7_FILE> sortedMap = Arrays.asList("3","5","23").stream().forEach(m -> {
//                _getMessage(message.entrySet().stream().collect(Collectors.toList()), m).stream()
//                        .sorted(Map.Entry.comparingByKey())
//                        .collect(Collectors.toMap(Map.Entry<String,HL7_FILE>::getKey,Map.Entry<String,HL7_FILE>::getValue)),
//                                (e1,e2) -> e1, LinkedHashMap<String,HL7_FILE>::new);


//            while((nextRecord = csvReader.get().readNext())) {
//                Arrays.asList(nextRecord).stream().filter(a -> a.startsWith("OBX")).forEach( a -> {
//
//                    Arrays.asList(a.split("\\|")).stream().forEach(p -> {
//                        System.out.println(count + " " + p);
//                        if(count.getAcquire() == 23 &&
//                            p.equalsIgnoreCase("Spectra East^D^^^^Spectra East&31D0961672&CLIA^XX^^^31D096167") ) {
//                            System.out.println("Where good");
//                        } else {
//                            System.out.println("Not good");
//                        }
//                        Arrays.asList(p.split("\\^")).stream().forEach(c -> {
//                            System.out.println(count + "." + component.getAndIncrement() + " " + c);
//                        });
//                        component.set(1);
//                        count.getAndIncrement();
//                    });
//
//
//
//                });
//
//                count.set(0);
//            }


            List beans = new CsvToBeanBuilder(new FileReader(path))
                    .withType(HL7_FILE2.class)
                    .withSeparator('|')
                    .build()
                    .parse();

            return beans;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        return null;
    }

    private void _parseComment() throws FileNotFoundException {
        StringBuffer sb = new StringBuffer();

        System.out.println(new File("Z://CM_BATCH_FILE/update_comments_20231229.csv").getAbsolutePath());

        List<CLINIC_COMMENT> parsebean = new CsvToBeanBuilder(new FileReader("Z://CM_BATCH_FILE/update_comments_20231229.csv"))
                .withType(CLINIC_COMMENT.class)
                .withSkipLines(1)
                .withSeparator('|')
                .withIgnoreQuotations(true)
                .build()
                .parse();

        List<CLINIC_COMMENT> newlist = (List<CLINIC_COMMENT>) parsebean.stream().collect(Collectors.toList());

        cmOracleDAO.moveToOracleDB(newlist, newlist.get(0));

        Map<String, CLINIC_COMMENT> csvMap = newlist.stream()
                .collect(Collectors.toMap(CLINIC_COMMENT::getFacility_num, Function.identity(), (a, b) -> b, LinkedHashMap::new));

        Map<String, String> cmap = new HashMap<>();

        long cnt = csvMap.entrySet().stream().map(t -> t.getValue()).filter(t -> Optional.ofNullable(t).isPresent()).count();

        csvMap.entrySet().stream().map(Map.Entry::getValue).forEach(t -> {
            sb.append(t.getComment().substring(0, t.getComment().indexOf(". After business")).replaceAll("\"", "")).append(".\n");
            sb.append(t.getComment().substring(sb.length() + 2, t.getComment().indexOf(" 1") - 1).replaceAll("\"", "")).append(".\n");
            if (t.getComment().indexOf("2.") != -1) {
                sb.append(t.getComment().substring(sb.length() + 2, t.getComment().indexOf("2.") - 1).replaceAll("\"", "")).append(".\n");
                sb.append(t.getComment().substring(sb.length() + 1).replaceAll("\"", "")).append(".\n");
            } else {
                sb.append(t.getComment().substring(sb.length() + 2).replaceAll("\"", "")).append(".\n");
            }
            sb.append("Batch Update (IT 12/29/23)\n");
            cmap.put(t.getFacility_num(), sb.toString());
            //sb.append(t.getComment().substring(0,t.getComment().indexOf(". After business")).replaceAll("\"","")).append(".\n");
            //System.out.println(t.getFacility_num() + " " + t.getComment().replaceAll("\"",""));
            //System.out.print(t.getFacility_num() + "\n" + sb.toString());


            sb.setLength(0);
        });

        cmap.entrySet().stream().limit(1).forEach(t -> {
            System.out.println(t.getKey());
            System.out.println(t.getValue());
        });


        //csvMap.entrySet().stream().limit(100).forEach(System.out::println);


    }


    public void runProcess(ASR_ENUM process) throws FileNotFoundException {


        log.info("Run process .......");

        System.out.println(process);

        switch (process) {
            case RESULTS_READ:
                LoadService resultcopy = (LoadService) LoadServiceFactory.getServiceImpl("ReadCopyResultService");

                resultcopy.startService();

                //CDBHlab.Connect();
                //CDBHlab.executeProcess(process.ordinal(), null);
                //CDBHlab.disconnect();
                break;

            case CREATE_TEST_HL7:
                log.info("Creating test hl7 ");


            default:

        }


    }


}
