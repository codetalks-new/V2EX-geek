package com.banxi1988.v2exgeek.controller;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;

import com.banxi1988.v2exgeek.R;
import com.banxi1988.v2exgeek.model.Node;


public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = "MainActivity";
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(MenuItem menuItem) {
        // update the main content by replacing fragments
        Fragment fragment = fragmentByNavigationItem(menuItem);
        showDetailFragment(fragment);
    }

    @Override
    public void onNavigationDrawerNodeSelected(Node node) {
        showDetailFragment(fragmentByNavigationNode(node));
    }

    private void showDetailFragment(Fragment fragment){
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Nullable
    private Fragment fragmentByNavigationItem(MenuItem menuItem){
        int itemId = menuItem.getItemId();
        if(itemId == R.id.navigation_item_hot){
            return TopicListFragment.newInstance(TopicListFragment.TOPIC_LIST_TYPE_HOT);
        }else if(itemId == R.id.navigation_item_all){
            return TopicListFragment.newInstance(TopicListFragment.TOPIC_LIST_TYPE_ALL);
        }
        return  null;
    }

    private Fragment fragmentByNavigationNode(@NonNull Node node){
        return TopicListFragment.newInstance(node);
    }

    public void onSectionAttached(int listType) {
        switch (listType) {
            case TopicListFragment.TOPIC_LIST_TYPE_HOT:
                mTitle = "热门";
                break;
            case TopicListFragment.TOPIC_LIST_TYPE_ALL:
                mTitle = "全部";
                break;
        }
    }

    public void onNodeAttached(Node node){
        Log.d(TAG, "onNodeAttached "+node);
        if (node != null){
            mTitle = node.title;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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
