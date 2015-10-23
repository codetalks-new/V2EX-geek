package com.banxi1988.v2exgeek.api.retrofit;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.banxi1988.v2exgeek.BuildConfig;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import retrofit.Converter;

/**
 * Created by banxi on 15/10/23.
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody,T> {
    private final Type type;
    private final Feature [] features;

    FastJsonResponseBodyConverter(Type type,Feature... features){
        this.type = type;
        this.features = features;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        Reader is = value.charStream();
        try {
            String text = readfully(is);
            return JSON.parseObject(text,type,features);
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }

    private static String readfully(Reader is) throws IOException{
        try {
            BufferedReader br  = new BufferedReader(is);
            StringBuilder sb = new StringBuilder(4096);
            String line = br.readLine();
            while (line != null){
                sb.append(line);
                line = br.readLine();
            }
            return sb.toString();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }
}
