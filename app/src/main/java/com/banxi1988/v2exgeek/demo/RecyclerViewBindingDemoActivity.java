package com.banxi1988.v2exgeek.demo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.banxi1988.v2exgeek.R;
import com.banxi1988.v2exgeek.databinding.RecyclerListItemBinding;

import java.util.ArrayList;
import java.util.List;

class UserViewHolder extends RecyclerView.ViewHolder {
    private RecyclerListItemBinding mBinding;
    public UserViewHolder(RecyclerListItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(User user){
        mBinding.setUser(user);
    }
}

class DemoRecyclerViewAdapter extends  RecyclerView.Adapter<UserViewHolder>{
    private List<User> userList;
    private static final int layoutId = R.layout.recycler_list_item;

    public DemoRecyclerViewAdapter(List<User> users){
        this.userList = users;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerListItemBinding binding = DataBindingUtil.inflate(layoutInflater,layoutId,parent,false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}

public class RecyclerViewBindingDemoActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager =  new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        List<User> userList = new ArrayList<>();
        userList.add(new User("胡","刘兰"));
        userList.add(new User("张","大全"));
        userList.add(new User("李","无底"));
        userList.add(new User("刘","青山"));
        userList.add(new User("王","小状"));

        DemoRecyclerViewAdapter adapter = new DemoRecyclerViewAdapter(userList);
        mRecyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recycler_view_binding_demo, menu);
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
