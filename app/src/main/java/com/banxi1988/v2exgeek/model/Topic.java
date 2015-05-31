package com.banxi1988.v2exgeek.model;

/**
 * Created by banxi on 15/5/31.
 */
public class Topic {
    public long id;
    public String title;
    public String url;
    public String content;
    public String content_rendered;
    public int replies;
    public Member member;
    public Node node;

    // "created" : 1433034989,
    public long created;
    public long last_modified;
    public long last_touched;

}
