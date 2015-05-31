package com.banxi1988.v2exgeek.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.converter.Converter;

/**
 * Created by banxi on 15/5/31.
 */
public class FastjsonConverter implements Converter {
    private  final MediaType mediaType ;
    private final Charset charset;

    public FastjsonConverter(){
        this(Charset.forName("UTF-8"));
    }

    public FastjsonConverter(Charset charset){
        this.charset = charset;
        this.mediaType =  MediaType.parse("application/json; charset=" + charset.name());
    }

    private static String readfully(InputStreamReader is) throws IOException{
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


    /**
     * Convert an HTTP response body to a concrete object of the specified type.
     *
     * @param body HTTP response body.
     * @param type Target object type.
     * @return Instance of {@code type} which will be cast by the caller.
     */
    @Override
    public Object fromBody(ResponseBody body, Type type) throws IOException {
        Charset charset = this.charset;
        if(body.contentType() != null){
            charset = body.contentType().charset(charset);
        }
        InputStream is = body.byteStream();
        InputStreamReader reader = new InputStreamReader(is,charset);
        try {
            return JSON.parseObject(readfully(reader),type);
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * Convert an object to an appropriate representation for HTTP transport.
     *
     * @param object Object instance to convert.
     * @param type
     * @return Representation of the specified object as bytes.
     */
    @Override
    public RequestBody toBody(Object object, Type type) {
        String json = JSON.toJSONString(object);
        return RequestBody.create(mediaType,json);
    }
}
