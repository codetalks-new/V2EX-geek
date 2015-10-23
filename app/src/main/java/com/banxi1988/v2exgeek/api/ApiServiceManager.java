package com.banxi1988.v2exgeek.api;

import com.banxi1988.v2exgeek.BuildConfig;
import com.banxi1988.v2exgeek.api.okhttp.HttpLoggingInterceptor;
import com.banxi1988.v2exgeek.api.retrofit.FastJsonConverterFactory;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Retrofit;

/**
 * Created by banxi on 15/5/31.
 */
public class ApiServiceManager {
    private static final String V2EX_API_ENDPOINT = "https://www.v2ex.com";
    private static class ApiServiceHolder{
        private static V2exService v2exService;
        static{
            Retrofit adapter = createV2exRestAdapter();
            v2exService = adapter.create(V2exService.class);
        }
    }

    private static class HttpClientHolder{
        private static OkHttpClient client = new OkHttpClient() ;
        static{
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.BASIC);
            client.interceptors().add(logging);
        }
    }

    public static V2exService v2exService(){
        return ApiServiceHolder.v2exService;
    }

    private static Retrofit createV2exRestAdapter(){
        return new Retrofit.Builder()
                .addConverterFactory(FastJsonConverterFactory.create())
                .client(HttpClientHolder.client)
                .baseUrl(V2EX_API_ENDPOINT)
                .build();
    }
}
