package com.banxi1988.v2exgeek.controller.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.banxi1988.v2exgeek.controller.listener.OnTopicViewClickLister;
import com.banxi1988.v2exgeek.databinding.TopicListItemDatabindingBinding;
import com.banxi1988.v2exgeek.model.Topic;
import com.squareup.picasso.Picasso;

/**
 * Created by banxi on 15/10/24.
 */
public class TopicViewHolder extends RecyclerView.ViewHolder{
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
