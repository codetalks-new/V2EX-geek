package com.banxi1988.v2exgeek.model;

/**
 * Created by banxi on 15/5/31.
 */
public class Member {
    public long id;
    public String username;
    public String tabline;
    public String avatar_mini;
    public String avatar_normal;
    public String avatar_large;

    // V2EX返回的没有带协议的如  "//cdn.v2ex.co/gravatar/8c628c1eac045670ad13b7b1d59924e3?s=48&d=retro"
    // s 表示大小 large的是 73
    public String getAvatar(){
       return "http:"+avatar_large;
    }
}
