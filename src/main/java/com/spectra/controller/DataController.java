package com.spectra.controller;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;
import com.spectra.asr.enums.AUDIT_ENUM;
import com.spectra.asr.model.OracleViewDAO;
import com.spectra.asr.model.OracleViewListener;
import com.spectra.config.Configure;
import com.spectra.dto.asr.HL7_FILE;
import com.spectra.dto.asr.HL7_FILE2;
import com.spectra.dto.clientmaster.CLINIC_COMMENT;
import com.spectra.dto.clientmaster.SPECTRA_CSV;
import connections.ConcreteDAO;
import connections.ItemURL;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class DataController {

    private final Map<String, ItemURL> map;
    //private final Map<String, StatesAPI> statemap;

    private final OracleViewDAO cmOracleDAO;

    private OracleViewListener listener;


//    private static LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//    private static Logger logger = lc.getLogger("Controller");


    public void setDataListener(OracleViewListener listener){
        this.listener = listener;
    }

    public DataController(String[] args) throws IOException {




        StringBuffer sb = new StringBuffer();
        StringBuffer envcon = new StringBuffer();

        List<ItemURL> envlist = new LinkedList<ItemURL>();

        map = (Map<String, ItemURL>) Configure.JsonConfigure(getClass().getClassLoader().getResourceAsStream(args[0]), ItemURL.class);



        for (Map.Entry<String, ItemURL> con : map.entrySet()){
            sb.setLength(0);

            sb.append(con.getValue().getUsername())
                    .append(" : ")
                    .append(con.getValue().getPassword())
                    .append(" : ")
                    .append(con.getValue().getType());

            //if(ParseUtil.findAnyString(sb.toString().toUpperCase(), "oracle_mdm_prod|cmdb_prod|oracle_mdm_dev|".toUpperCase())) {
                System.out.println(sb.toString());
            //} ;
            

        }

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


//        envcon.append("proxy_cm_owner").append("_").append(BASE_ENV.toLowerCase());
//        logger.debug(envcon.toString());
//
        cmOracleDAO = new OracleViewDAO(new ConcreteDAO(map.get("proxy_staterpt_owner_dev")));
        envcon.setLength(0);
//
//        envcon.append("cdbhlab").append("_").append(BASE_ENV.toLowerCase());
//        logger.debug(envcon.toString());
//        CDBHlab = new SqlServerViewDAO(new ConcreteDAO(map.get(envcon.toString())));
//
//
//        logger.error("System running " + BASE_ENV);

        setListeners();

    }

    private void setListeners(){

    }

    private List<String> _createList(final String path){
//        final Spliterator<String> splt = Spliterators.spliterator(scanner,Long.MAX_VALUE,Spliterator.ORDERED | Spliterator.NONNULL);
//        return StreamSupport.stream(splt,false)
//                .onClose(scanner::close);
        URL url = DataController.class.getResource(path);
        Stream<String> lines = null;
        List<String> list = null;

//        try {
//             //lines = Files.lines(Path.of(path));
//            //lines.forEach(System.out::println);
//                    //Files.lines(Paths.get(url.getPath())).forEach(System.out::println);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        list = lines.collect(Collectors.toList());
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

    private Set<Map.Entry<String,HL7_FILE>> _getMessage(List<Map.Entry<String,HL7_FILE>> message, String field){

        return message.stream().filter(t -> t.getKey().startsWith(field)).collect(Collectors.toSet());


    }

    private <T> List<HL7_FILE2> _createList3(final String path) throws FileNotFoundException {
        AtomicInteger count = new AtomicInteger(0);
        AtomicInteger component = new AtomicInteger(1);
        AtomicInteger subcount = new AtomicInteger(1);

        Map<String,HL7_FILE> message = new TreeMap<>();
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
                                if(c.indexOf("&") == -1)
                                message.put(count + "." + component, new HL7_FILE(sb.toString(), "^", c));

                            sb.setLength(0);

                            // subcount
                            Arrays.asList(c.split("&")).stream().forEach(s -> {

                                if(c.indexOf("&") != -1) {
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
                System.out.print( sb.toString().substring(0,sb.length() -1) + "|");
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

            //cmOracleDAO.moveToOracleDB(newlist,newlist.get(0));

            Map<String, CLINIC_COMMENT> csvMap = newlist.stream()
                    .collect(Collectors.toMap(CLINIC_COMMENT::getFacility_num, Function.identity(),(a, b) -> b, LinkedHashMap::new));

            Map<String, String> cmap = new HashMap<>();

            long cnt = csvMap.entrySet().stream().map(t -> t.getValue()).filter(t -> Optional.ofNullable(t).isPresent()).count();

            csvMap.entrySet().stream().map(Map.Entry::getValue).forEach(t -> {
                sb.append(t.getComment().substring(0,t.getComment().indexOf(". After business")).replaceAll("\"","")).append(".\n");
                sb.append(t.getComment().substring(sb.length() + 2,t.getComment().indexOf(" 1") -1).replaceAll("\"","")).append(".\n");
                if(t.getComment().indexOf("2.") != -1) {
                    sb.append(t.getComment().substring(sb.length() + 2,t.getComment().indexOf("2.") -1).replaceAll("\"","")).append(".\n");
                    sb.append(t.getComment().substring(sb.length() + 1).replaceAll("\"", "")).append(".\n");
                } else {
                    sb.append(t.getComment().substring(sb.length() + 2 ).replaceAll("\"","")).append(".\n");
                }
                sb.append("Batch Update (IT 12/29/23)\n");
                cmap.put(t.getFacility_num(),sb.toString());
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



        public void runProcess(AUDIT_ENUM process) throws FileNotFoundException {
        List<String> list =  null;
        StringBuffer sb = new StringBuffer();
        Set<Integer> last = new TreeSet<>();
        AtomicInteger count = new AtomicInteger(1);


        log.info("Run process .......");

        System.out.println(process);

        switch (process){
            case LEGACY_TABLE:
//                CDBHlab.Connect();
//                CDBHlab.executeProcess(process.ordinal(),null);
//                CDBHlab.disconnect();
                break;

            case DELETE_TABLES:
                log.info("Delete Tables -> " + process);

                break;

            case PARSE_COMMENT:

                try
                {
                    cmOracleDAO.Connect();
                    _parseComment();
                    cmOracleDAO.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }

                break;

            case PARSE_CSV:
                log.debug("PARSE CSV -> " + process);
                System.out.println(new File("G:/Tulasi/Alex/Search_Extract.csv").getAbsolutePath());

                List<SPECTRA_CSV> parsebean = new CsvToBeanBuilder(new FileReader("G:/Tulasi/Alex/Search_Extract.csv"))
                        .withType(SPECTRA_CSV.class)
                        .withSkipLines(1)
                        .withSeparator(',')
                        .withIgnoreQuotations(true)
                        .build()
                        .parse();

                List<SPECTRA_CSV> newlist = (List<SPECTRA_CSV>) parsebean.stream().collect(Collectors.toList());

                Map<String, SPECTRA_CSV> csvMap = newlist.stream()
                        .collect(Collectors.toMap(SPECTRA_CSV::getFmc_number, Function.identity(),(a, b) -> b, LinkedHashMap::new));



                long cnt = csvMap.entrySet().stream().map(t -> t.getValue()).filter(t -> Optional.ofNullable(t).isPresent()).count();

                csvMap.entrySet().stream().map(t -> t.getValue()).limit(10).forEach(t -> {

                    System.out.println("FMC_NUMBER: " + t.getFmc_number() + " CLINICAL MANAGER: " + t.getClinicalmgr() + " MEDICAL DIRECTOR: " + t.getMeddir());
                });

                csvMap.entrySet().stream().map(t -> t.getValue()).filter(t -> t.getFacility_name().equals("Dialysis Specialists Fairfield")).forEach(System.out::println);

                newlist.stream().filter(t -> t.getFacility_name().startsWith("DS")).forEach(System.out::println);

                log.info(Long.toString(cnt));

//                newlist.stream().forEach(t ->  {
//
//                    if(t.getAreaname() == null){
//                        //System.out.println(t);
//                        t.setAreaname("A");
//                        System.out.println(t.getAreaname());
//                    };
//                });

                break;


            case CUSTOMER_NUM:
                log.debug("CUSTOMER_NUM -> " + process);
                Arrays.stream(new File("C:/services/ASR_AL_TIMER_TASK/HLAB_Test_Results_Service/HL7_destination/state/20231201").listFiles())
                        .forEach(t -> {
                            System.out.println(t.getAbsolutePath());
                            try {
                                List<HL7_FILE2> msh = _createList3(t.getAbsolutePath()).stream()
                                        .collect(Collectors.toList());
//                                msh.stream()
//                            .forEach(System.out::println);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                break;

            case CREATE_PRIMARY_LIST:

                try {
                    List<HL7_FILE> msh = _createList2("C:/Users/tsmithers/Downloads/202312051459543.csv").stream()
                            .filter(t -> t.getSegment().startsWith("MSH"))
                            .collect(Collectors.toList());
                    //msh.stream().forEach(t -> System.out.println(t));
                    //msh.stream().forEach(t -> System.out.println(t.getSegment().replaceAll("\\.",":")));

                    List<HL7_FILE> obx = _createList2("C:/Users/tsmithers/Downloads/202312051459543.csv").stream()
                            .filter(t -> t.getSegment().startsWith("OBX"))
                            .collect(Collectors.toList());
                    obx.stream().forEach(t -> System.out.println(t));

                    sb.append("MSH");
                    List<HL7_FILE> pid = _createList2("C:/Users/tsmithers/Downloads/202312051459543.csv").stream()
                            .filter(t -> t.getSegment().startsWith("PID"))
                            .collect(Collectors.toList());
                    pid.stream().forEach(t -> {
                        count.set(1);
                        //System.out.println(t.getValue());
                        Arrays.asList(    t.getSegment().replaceAll("\\.", ":").split(":")
                        ).forEach(
                                a ->
                                {
                                    switch (count.get()) {
                                        case 2:
                                            if(!last.contains(Integer.valueOf(a))) {
                                                //count.set(Integer.valueOf(a));
                                                last.add(Integer.valueOf(a));
                                                sb.append("|");
                                            } else {
                                                sb.append("^");
                                            }
                                           break;
                                        case 3:
                                            sb.append("^");
                                            break;
                                    }


                                    if (count.get() == 2) {
//                                        System.out.println(
//                                                "a: " + a + " " + count);
                                        if(!last.contains(Integer.valueOf(a))) {
                                            //count.set(Integer.valueOf(a));
                                            last.add(Integer.valueOf(a));
                                            sb.append("|");
                                        } else {
                                            sb.append("^");
                                        }
                                        sb.append(t.getStrvalue());

                                    }
                                    count.getAndIncrement();


                                }


                        );

                    });

                    //last.stream().forEach(System.out::println);
                    System.out.println(sb.toString());

                } catch (Exception e){
                    e.printStackTrace();
                }
                //list = _createList("cm1_primary_list.txt");
                //list = _createList("lab_level_list.txt");
//                list = _createList("C:/Users/tsmithers/Downloads/202312051459543.csv" );
//                StringBuffer sb = new StringBuffer();
//                AtomicInteger count = new AtomicInteger(1);
//                sb.append("MSH|");
//
//                list.subList(1,1).stream().forEach(System.out:: println);
//
//               list.stream().filter(t -> t.startsWith("MSH"))
//                        .forEach((t -> {
//                            List<String> arr =  Arrays.asList(t.split(","));
//                            arr.stream().forEach(a -> {
//
//                                    System.out.println(a.toString() + " " +  arr.size() + " " + count.get());
//
//                                    if(count.getAndIncrement() == arr.size()){
//                                        sb.append(arr.get(arr.size() -1));
//                                       sb.append("|");
//                                    } else {
//                                        sb.append("^");
//                                    }
//                        }
//
//                            );
//
//                            count.set(1);
//                        }));
//
//               count.set(1);
//
//                System.out.println(sb.toString());
                //obxs.stream().forEach(System.out::println);

//                cmOracleDAO.Connect();
//                cmOracleDAO.moveToOracleDB(list);
//                cmOracleDAO.disconnect();
                break;
            case MORE_ACCOUNT_INFO:

                log.info("Connecting to database ");
                cmOracleDAO.Connect();
                cmOracleDAO.beginETL(null);
                cmOracleDAO.disconnect();
                break;
            default:

        }


    }



}
