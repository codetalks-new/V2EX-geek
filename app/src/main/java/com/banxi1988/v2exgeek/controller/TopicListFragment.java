package com.banxi1988.v2exgeek.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.banxi1988.v2exgeek.MainActivity;
import com.banxi1988.v2exgeek.R;
import com.banxi1988.v2exgeek.api.ApiServiceManager;
import com.banxi1988.v2exgeek.databinding.TopicListItemDatabindingBinding;
import com.banxi1988.v2exgeek.model.Topic;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;

/**
 * Created by banxi on 15/5/31.
 */
public class TopicListFragment extends Fragment {
    private static final String TAG = "TopicListFragment";
    @IntDef({TOPIC_LIST_TYPE_HOT,TOPIC_LIST_TYPE_ALL,TOPIC_LIST_TYPE_LATEST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TopicListType{}

    public static final int TOPIC_LIST_TYPE_HOT = 0;
    public static final int TOPIC_LIST_TYPE_ALL = 1;
    public static final int TOPIC_LIST_TYPE_LATEST = 2;


    @TopicListType
    private int mTopicListType = TOPIC_LIST_TYPE_HOT;

    private static final String ARG_TOPIC_LIST_TYPE = "topic_list_type";
    private RecyclerView mRecyclerView;
    private TopicRecyclerAdapter mAdapter;

    public static TopicListFragment newInstance(@TopicListType int typeId) {
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
               mTopicListType = args.getInt(ARG_TOPIC_LIST_TYPE);
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

    private void showTopicDetail(@NotNull Topic topic){
        Intent intent = new Intent(getActivity(),TopicDetailActivity.class);
        intent.putExtra(TopicDetailActivity.ARG_TOPIC,topic);
        startActivity(intent);
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_TOPIC_LIST_TYPE));
    }

    private String currentListScope(){
        if(mTopicListType == TOPIC_LIST_TYPE_HOT){
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
    private TopicListItemDatabindingBinding mBinding;
    private OnTopicViewClickLister mTopicClickListener;
    private View.OnClickListener mInnerOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mTopicClickListener == null && mBinding.getTopic() != null){
                return;
            }
            if(v == mBinding.avatar){
               mTopicClickListener.onClickAvatar(mBinding.getTopic());
            }else{
                mTopicClickListener.onClickTopic(mBinding.getTopic());
            }
        }
    };
    public TopicViewHolder(TopicListItemDatabindingBinding binding,OnTopicViewClickLister clickLister) {
        super(binding.getRoot());
        mBinding = binding;
        mTopicClickListener = clickLister;
        if(clickLister != null){
            binding.getRoot().setOnClickListener(mInnerOnClickListener);
            binding.avatar.setOnClickListener(mInnerOnClickListener);
        }
    }

    public void bind(Topic topic){
        mBinding.setTopic(topic);
        Context ctx = mBinding.avatar.getContext();
        Picasso.with(ctx).load(topic.member.getAvatar()).into(mBinding.avatar);
    }
}

interface OnTopicViewClickLister{
     void onClickAvatar(Topic topic);
    void onClickTopic(Topic topic);
}

class TopicRecyclerAdapter extends RecyclerView.Adapter<TopicViewHolder>{
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
        this.topics.clear();
        this.topics.addAll(topics);
        notifyDataSetChanged();
    }

    @Override
    public TopicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        TopicListItemDatabindingBinding binding = TopicListItemDatabindingBinding.inflate(inflater);
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