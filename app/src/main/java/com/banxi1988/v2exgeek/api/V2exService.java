package com.banxi1988.v2exgeek.api;

import com.banxi1988.v2exgeek.model.Reply;
import com.banxi1988.v2exgeek.model.TopicWrapper;
import com.banxi1988.v2exgeek.model.Topic;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by banxi on 15/5/31.
 */
public interface V2exService {

    @GET("/topics/{scope}.json")
    void listTopicByScope(@Path("scope") String scope, Callback<List<Topic>> cb);

    @GET("/topics/show.json")
    void listTopicByNodeId(@Query("node_id") String nodeId,Callback<List<Topic>> cb);

    @GET("/topics/show.json")
    void listTopicByNodeName(@Query("node_name") String nodeName,Callback<List<Topic>> cb);

    @GET("/topics/show.json")
    void topicById(@Query("id") String id,Callback<Topic> cb);

    @GET("/topics/replies/show.json")
    void listReply(@Query("topic_id") long topicId,Callback<List<Reply>> cb);

}
