package com.banxi1988.v2exgeek.controller.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.banxi1988.v2exgeek.controller.holder.TopicViewHolder;
import com.banxi1988.v2exgeek.controller.listener.OnTopicViewClickLister;
import com.banxi1988.v2exgeek.databinding.TopicListItemDatabindingBinding;
import com.banxi1988.v2exgeek.model.Topic;

import java.util.List;

/**
 * Created by banxi on 15/10/24.
 */
public class TopicRecyclerAdapter extends RecyclerView.Adapter<TopicViewHolder>{
    private static final String TAG = "TopicRecyclerAdapter";
    private final List<Topic> topics;
    private OnTopicViewClickLister mTopicClickListener;
    public TopicRecyclerAdapter(List<Topic> topics){
       this(topics,null);
    }
    public TopicRecyclerAdapter(List<Topic> topics,OnTopicViewClickLister clickLister){
       this.topics = topics;
        mTopicClickListener = clickLister;
    }

    public void setOnTopicClickListener(OnTopicViewClickLister listener){
        mTopicClickListener = listener;
    }

    public void updateTopics(List<Topic> topics){
        if(topics == null || topics.isEmpty()){
            Log.w(TAG, "topics is null or empty");
            return;
        }
        this.topics.clear();
        this.topics.addAll(topics);
        notifyDataSetChanged();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TopicListItemDatabindingBinding binding = TopicListItemDatabindingBinding.inflate(inflater,parent,false);
        return  new TopicViewHolder(binding,mTopicClickListener);
    }


    @Override
    public void onBindViewHolder(TopicViewHolder holder, int position) {
        holder.bind(topicAtPosition(position));
    }


    public Topic topicAtPosition(int position){
        return topics.get(position);
    }
    @Override
    public int getItemCount() {
        return topics.size();
    }
}
