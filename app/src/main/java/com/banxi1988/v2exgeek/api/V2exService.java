package com.banxi1988.v2exgeek.api;

import com.banxi1988.v2exgeek.model.Reply;
import com.banxi1988.v2exgeek.model.TopicWrapper;
import com.banxi1988.v2exgeek.model.Topic;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by banxi on 15/5/31.
 */
public interface V2exService {

    @GET("/topics/{scope}.json")
    Call<List<Topic>> listTopicByScope(@Path("scope") String scope);

    @GET("/topics/show.json")
    Call<List<Topic>>  listTopicByNodeId(@Query("node_id") String nodeId);

    @GET("/topics/show.json")
    Call<List<Topic>>  listTopicByNodeName(@Query("node_name") String nodeName);

    @GET("/topics/show.json")
    Call<List<Topic>>  topicById(@Query("id") String id);

    @GET("/topics/replies/show.json")
    Call<List<Reply>>  listReply(@Query("topic_id") long topicId);

}
