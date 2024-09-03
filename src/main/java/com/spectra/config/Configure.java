//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.spectra.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.management.RuntimeErrorException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Configure {
    public Configure() {
    }

    public static Map<?, ?> JsonConfigure(InputStream in, Class<?> u) throws IOException {
        Gson gson = (new GsonBuilder()).create();
        Map<Object, Object> map = new LinkedHashMap();
        Reader reader = new InputStreamReader(in);
        if (reader == null) {
            return map;
        } else {
            Object[] uri = null;
            Class<?> stringArrayClass = Array.newInstance(u, 0).getClass();
            uri = (Object[])((Object[])gson.fromJson(reader, stringArrayClass));
            Object[] arr$ = uri;
            int len$ = uri.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Object c = arr$[i$];

                try {
                    map.put(c.getClass().getMethod("getName", (Class[])null).invoke(c, (Object[])null), c);
                } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException var12) {
                    throw new RuntimeErrorException((Error)var12.getCause());
                }
            }

            if(reader != null){
                reader.close();
            }
            return map;
        }
    }
}

