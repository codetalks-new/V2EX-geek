package com.banxi1988.v2exgeek.api;

import com.banxi1988.v2exgeek.api.retrofit.FastJsonConverterFactory;

import retrofit.Retrofit;

/**
 * Created by banxi on 15/5/31.
 */
public class ApiServiceManager {
    private static final String V2EX_API_ENDPOINT = "https://www.v2ex.com/api";
    private static V2exService _v2exService;
    public static V2exService v2exService(){
        if(_v2exService == null){
            Retrofit adapter = createV2exRestAdapter();
            _v2exService = adapter.create(V2exService.class);
        }
        return _v2exService;
    }

    private static Retrofit createV2exRestAdapter(){
        return new Retrofit.Builder()
                .addConverterFactory(FastJsonConverterFactory.create())
                .baseUrl(V2EX_API_ENDPOINT)
                .build();
    }
}
