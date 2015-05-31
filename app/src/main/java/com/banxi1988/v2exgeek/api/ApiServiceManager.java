package com.banxi1988.v2exgeek.api;

import retrofit.RestAdapter;

/**
 * Created by banxi on 15/5/31.
 */
public class ApiServiceManager {
    private static final String V2EX_API_ENDPOINT = "https://www.v2ex.com/api";
    private static V2exService _v2exService;
    public static V2exService v2exService(){
        if(_v2exService == null){
            RestAdapter adapter = createV2exRestAdapter();
            _v2exService = adapter.create(V2exService.class);
        }
        return _v2exService;
    }

    private static RestAdapter createV2exRestAdapter(){
        return new RestAdapter.Builder()
                .converter(new FastjsonConverter())
                .endpoint(V2EX_API_ENDPOINT).build();
    }
}
