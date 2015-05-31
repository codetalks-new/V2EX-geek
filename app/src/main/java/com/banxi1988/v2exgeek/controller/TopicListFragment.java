package com.banxi1988.v2exgeek.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banxi1988.v2exgeek.R;
import com.banxi1988.v2exgeek.api.ApiServiceManager;
import com.banxi1988.v2exgeek.model.Topic;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by banxi on 15/5/31.
 */
public class TopicListFragment extends Fragment {
    public static final int TopicListAliasHot = 1;
    public static final int TopicListAliasLatest = 2;
    private int mTopicListAlias = TopicListAliasHot;

    private static final String ARG_TOPIC_LIST_TYPE = "topic_list_type";
    private RecyclerView mRecyclerView;
    private TopicRecyclerAdapter mAdapter;

    public static TopicListFragment newInstance(int typeId) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TOPIC_LIST_TYPE, typeId);
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
               mTopicListAlias = args.getInt(ARG_TOPIC_LIST_TYPE);
           }
        }
        mAdapter = new TopicRecyclerAdapter(new ArrayList<Topic>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.topic_list_fragment,container,false );

        mRecyclerView = (RecyclerView)rootView;
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        loadTopicList();
        return rootView;
    }

    private String currentListScope(){
        if(mTopicListAlias == TopicListAliasHot){
            return "hot";
        }else{
            return "latest";
        }
    }

    private void loadTopicList(){
        ApiServiceManager.v2exService().listTopicByScope(currentListScope(), new Callback<List<Topic>>() {
            @Override
            public void success(List<Topic> topics, Response response) {
                handleResponse(topics);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleResponse(List<Topic> topics){
        mAdapter.updateTopics(topics);

    }
}
class TopicViewHolder extends RecyclerView.ViewHolder{
    private TextView titleView;
    private ImageView avatarView;
    public TopicViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView)itemView.findViewById(R.id.title);
        avatarView = (ImageView)itemView.findViewById(R.id.avatar);
    }

    public void bind(Topic topic){
        titleView.setText(topic.title);
        Picasso.with(titleView.getContext()).load("http:"+topic.member.avatar_mini).into(avatarView);
    }
}

class TopicRecyclerAdapter extends RecyclerView.Adapter<TopicViewHolder>{
    private final List<Topic> topics;
    public TopicRecyclerAdapter(List<Topic> topics){
       this.topics = topics;
    }

    public void updateTopics(List<Topic> topics){
        this.topics.clear();
        this.topics.addAll(topics);
        notifyDataSetChanged();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.topic_list_item,parent,false);
        return  new TopicViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        holder.bind(topicAtPosition(position));
    }


    public Topic topicAtPosition(int position){
        return (Topic)topics.get(position);
    }
    @Override
    public int getItemCount() {
        return topics.size();
    }
}