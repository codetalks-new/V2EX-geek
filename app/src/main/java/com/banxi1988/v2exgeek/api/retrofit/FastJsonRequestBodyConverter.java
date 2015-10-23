package com.banxi1988.v2exgeek.api.retrofit;

import com.alibaba.fastjson.JSON;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;
import java.nio.charset.Charset;

import retrofit.Converter;

/**
 * Created by banxi on 15/10/23.
 */
final class FastJsonRequestBodyConverter<T> implements Converter<T,RequestBody>{
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    @Override
    public RequestBody convert(T value) throws IOException {
        String json = JSON.toJSONString(value);
        return RequestBody.create(MEDIA_TYPE,json);
    }

}
