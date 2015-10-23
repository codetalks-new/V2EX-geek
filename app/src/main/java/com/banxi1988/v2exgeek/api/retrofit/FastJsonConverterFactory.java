package com.banxi1988.v2exgeek.api.retrofit;

import android.util.Log;

import com.alibaba.fastjson.parser.Feature;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Created by banxi on 15/10/23.
 */
public class FastJsonConverterFactory extends Converter.Factory {
    private final Feature[] features;
    private FastJsonConverterFactory(Feature ...features){
       this.features = features;
    }

    public static FastJsonConverterFactory create(){
        return new FastJsonConverterFactory();
    }

    @Override
    public Converter<?, RequestBody> toRequestBody(Type type, Annotation[] annotations) {
        return new FastJsonRequestBodyConverter<>();
    }

    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new FastJsonResponseBodyConverter<>(type,features);
    }
}
