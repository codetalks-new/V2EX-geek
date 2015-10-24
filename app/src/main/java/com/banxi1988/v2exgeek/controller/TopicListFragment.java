package com.banxi1988.v2exgeek.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.banxi1988.v2exgeek.R;
import com.banxi1988.v2exgeek.api.ApiServiceManager;
import com.banxi1988.v2exgeek.controller.adapter.TopicRecyclerAdapter;
import com.banxi1988.v2exgeek.controller.listener.OnTopicViewClickLister;
import com.banxi1988.v2exgeek.model.Node;
import com.banxi1988.v2exgeek.model.Topic;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Retrofit;

/**
 * Created by banxi on 15/5/31.
 */
public class TopicListFragment extends Fragment {
    private static final String TAG = "TopicListFragment";
    @IntDef({TOPIC_LIST_TYPE_HOT,TOPIC_LIST_TYPE_ALL,TOPIC_LIST_TYPE_NODE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TopicListType{}

    public static final int TOPIC_LIST_TYPE_HOT = 0;
    public static final int TOPIC_LIST_TYPE_ALL = 1;
    public static final int TOPIC_LIST_TYPE_NODE = 2;


    @TopicListType
    private int mTopicListType = TOPIC_LIST_TYPE_HOT;
    private static final String ARG_TOPIC_LIST_TYPE = "topic_list_type";
    private static final String ARG_NODE = "node";
    @Nullable
    private Node mCurrentNode;
    private RecyclerView mRecyclerView;
    private TopicRecyclerAdapter mAdapter;

    public static TopicListFragment newInstance(@TopicListType int typeId) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOPIC_LIST_TYPE, typeId);
        fragment.setArguments(args);
        return fragment;
    }

    public static TopicListFragment newInstance(@NonNull Node node) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOPIC_LIST_TYPE, TOPIC_LIST_TYPE_NODE);
        args.putParcelable(ARG_NODE,node);
        fragment.setArguments(args);
        return fragment;
    }

    public TopicListFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
           if(args.containsKey(ARG_TOPIC_LIST_TYPE)){
               mTopicListType = args.getInt(ARG_TOPIC_LIST_TYPE);
           }
            if(args.containsKey(ARG_NODE)){
                mCurrentNode = args.getParcelable(ARG_NODE);
            }
        }
        mAdapter = new TopicRecyclerAdapter(new ArrayList<Topic>(), new OnTopicViewClickLister() {
            @Override
            public void onClickAvatar(Topic topic) {
                Log.d(TAG, "onClickAvatar  " + topic.title);
            }

            @Override
            public void onClickTopic(Topic topic) {
                showTopicDetail(topic);
            }
        });
    }

    private void showTopicDetail(@NonNull Topic topic){
        Intent intent = new Intent(getActivity(),TopicDetailActivity.class);
        intent.putExtra(TopicDetailActivity.ARG_TOPIC,topic);
        startActivity(intent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.topic_list_fragment,container,false );

        mRecyclerView = (RecyclerView)rootView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        loadTopicList();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity main = (MainActivity)activity;
        if(mCurrentNode != null){
            main.onNodeAttached(mCurrentNode);
        }else{
            main.onSectionAttached(mTopicListType);
        }
    }

    private String currentListScope(){
        if(mTopicListType == TOPIC_LIST_TYPE_HOT){
            return "hot";
        }else{
            return "latest";
        }
    }

    private void loadTopicList(){
        if (mCurrentNode == null) {
            loadTopicListByScope(currentListScope());
        }else{
            loadTopicListByNode(mCurrentNode);
        }
    }

    private void loadTopicListByNode(@NonNull Node node){
        ApiServiceManager.v2exService().listTopicByNodeName(node.name).enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(retrofit.Response<List<Topic>> response, Retrofit retrofit) {
                handleResponse(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadTopicListByScope(String scope){
        ApiServiceManager.v2exService().listTopicByScope(scope).enqueue(new Callback<List<Topic>>() {
            @Override
            public void onResponse(retrofit.Response<List<Topic>> response, Retrofit retrofit) {
                handleResponse(response.body());
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e(TAG,t.getLocalizedMessage());
                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleResponse(List<Topic> topics){
        mAdapter.updateTopics(topics);

    }
}

