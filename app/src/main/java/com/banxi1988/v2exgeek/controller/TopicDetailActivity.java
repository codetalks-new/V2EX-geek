package com.banxi1988.v2exgeek.controller;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.banxi1988.v2exgeek.R;
import com.banxi1988.v2exgeek.databinding.ActivityTopicDetailBinding;
import com.banxi1988.v2exgeek.databinding.TopicDetailBinding;
import com.banxi1988.v2exgeek.model.Topic;
import com.squareup.picasso.Picasso;

public class TopicDetailActivity extends AppCompatActivity {
    private static final String TAG = "TopicDetailActivity";
    public static final String ARG_TOPIC = "topic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Topic topic = getIntent().getParcelableExtra(ARG_TOPIC);
        Log.d(TAG, "onCreate topic="+topic);
        ActivityTopicDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_topic_detail);
        TopicDetailBinding detailBinding = binding.topicDetail;
        detailBinding.topicContent.setMovementMethod(LinkMovementMethod.getInstance());
        binding.setTopic(topic);
        Picasso.with(this).load(topic.member.getAvatar()).into(detailBinding.avatar);
//        Html.fromHtml()
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topic_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
