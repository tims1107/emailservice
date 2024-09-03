package com.spectra.utils;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProcessResultSet {

    public static ResultSet closeResultSet(ResultSet rst){
        try {
            if(rst != null) rst.close();
            rst = null;
        } catch (SQLException se){

        } finally {
            if(rst != null){
                try{rst.close();} catch(Exception e) {}
            }
            rst = null;
        }
        return null;
    }

    public  static List<HashMap<String, Object>> GetListOfDataFromResultSet(ResultSet rs)  {
        ResultSetMetaData metaData = null;


        List<HashMap<String, Object>> lst = new LinkedList<>();
        try {
            metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            String[] columnName = new String[count];
            while (rs.next()) {
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 1; i <= count; i++) {
                    columnName[i - 1] = metaData.getColumnLabel(i).toUpperCase();
                    map.put(columnName[i - 1], rs.getObject(i));
                }
                lst.add(map);
            }

        } catch (SQLException se){
            se.printStackTrace();
        } finally {
           closeResultSet(rs);
        }



//        System.out.println("Size: " + lst.size());
//
//        for(int i = 0; i < lst.size(); i++){
//
//            HashMap<String,Object> m = lst.get(i);
//
//
//            for (Map.Entry<String, Object> k : m.entrySet()) {
//                if(k.getKey().equals("RESULT_SEQUENCE")) {
//                    if (k.getValue() instanceof String) {
//                        System.out.println(k.getKey() + " ==String ==> " + k.getValue());
//                    } else if (k.getValue() instanceof Date) {
//                        System.out.println(k.getKey() + " ==Date==> " + k.getValue());
//                    } else {
//                        System.out.println(k.getKey() + " ==Other==> " + k.getValue());
//                    }
//                }
//            }
//        }
        return lst;
    }

    /**
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    public @interface DBTable {
        public String columnName();
    }

    public static <T> List<T> selectQuery(Class<T> type, ResultSet rst) throws SQLException {
        List<T> list = new ArrayList<T>();
        ResultSetMetaData rsmd = rst.getMetaData();

           try {
               while (rst.next()) {
                   System.out.println(rsmd.getColumnCount());
                   T t = type.newInstance();
                   loadResultSetIntoObject(rst, t);// Point 4
                   list.add(t);
               }

           } catch (Exception e){
               e.printStackTrace();
           }



        return list;

    }

    public static String getOracleJsonElement(String colname, int coltype, Object curval, boolean startObject, boolean endObject){
        StringBuffer sb = new StringBuffer();

        if (startObject){sb.append("{");}

        switch (coltype){
            case 12:
                if (curval != null) {
                    sb.append("\"" + colname + "\":\"" + (String) curval + "\"");
                }else {
                    sb.append("\"" + colname + "\" : null");
                }
                break;
            case 2:
                if (curval != null) {
                    sb.append( "\"" + colname + "\" : " + (BigDecimal) curval);
                } else {
                    sb.append("\"" + colname + "\" : null");
                }
                break;
            default:
                sb.append( "\"" + colname + ": null");
        }

        if(endObject) { sb.append("}\n");} else {sb.append(",\n");}

            return sb.toString();
    }

    public static String getSqlJsonElement(String colname, int coltype, Object curval, boolean startObject, boolean endObject){
        StringBuffer sb = new StringBuffer();

        if (startObject){sb.append("{");}

        switch (coltype){
            case 12:
                if (curval != null) {
                    sb.append("\"" + colname + "\":\"" + (String) curval + "\"");
                }else {
                    sb.append("\"" + colname + "\" : null");
                }
                break;
            case 2:
                if (curval != null) {
                    sb.append( "\"" + colname + "\" : " + (Integer) curval);
                } else {
                    sb.append("\"" + colname + "\" : null");
                }
                break;
            default:
                sb.append( "\"" + colname + ": null");
        }

        if(endObject) { sb.append("}\n");} else {sb.append(",\n");}

        return sb.toString();

    }

    public static void loadResultSetIntoObject(ResultSet rst, Object object)
            throws IllegalArgumentException, IllegalAccessException, SQLException {
        Class<?> zclass = object.getClass();
        for (Field field : zclass.getDeclaredFields()) {
            field.setAccessible(true);
            DBTable column = field.getAnnotation(DBTable.class);
            Object value = rst.getObject(column.columnName());
            Class<?> type = field.getType();
            if (isPrimitive(type)) {//check primitive type(Point 5)
                Class<?> boxed = boxPrimitiveClass(type);//box if primitive(Point 6)
                value = boxed.cast(value);
            }
            field.set(object, value);
        }
    }

    public static boolean isPrimitive(Class<?> type) {
        return (type == int.class || type == long.class || type == double.class || type == float.class
                || type == boolean.class || type == byte.class || type == char.class || type == short.class);
    }

    public static Class<?> boxPrimitiveClass(Class<?> type) {
        if (type == int.class) {
            return Integer.class;
        } else if (type == long.class) {
            return Long.class;
        } else if (type == double.class) {
            return Double.class;
        } else if (type == float.class) {
            return Float.class;
        } else if (type == boolean.class) {
            return Boolean.class;
        } else if (type == byte.class) {
            return Byte.class;
        } else if (type == char.class) {
            return Character.class;
        } else if (type == short.class) {
            return Short.class;
        } else {
            String string = "class '" + type.getName() + "' is not a primitive";
            throw new IllegalArgumentException(string);
        }
    }





}
