package com.banxi1988.v2exgeek;

import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.banxi1988.v2exgeek.controller.TopicListFragment;
import com.banxi1988.v2exgeek.model.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = R.id.navigation_item_hot;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;

    private static final List<Node> preselectNodeList  = new ArrayList<>();
    static{
        preselectNodeList.add(new Node(300,"programmer","程序员","//cdn.v2ex.co/navatar/94f6/d7e0/300_large.png?m=1433388951"));
        preselectNodeList.add(new Node(13,"iDev","iDev","//cdn.v2ex.co/navatar/c51c/e410/13_large.png?m=1433389041"));
        preselectNodeList.add(new Node(39,"android","Android","//cdn.v2ex.co/navatar/d67d/8ab4/39_large.png?m=1433389059"));
        preselectNodeList.add(new Node(90,"python","Python","//cdn.v2ex.co/navatar/8613/985e/90_large.png?m=1433389127"));
        preselectNodeList.add(new Node(17,"create","分享创造","//cdn.v2ex.co/navatar/70ef/df2e/17_large.png?m=1433388902"));
        preselectNodeList.add(new Node(215,"design","设计","//cdn.v2ex.co/navatar/3b8a/6142/215_large.png?m=1433056985"));
        preselectNodeList.add(new Node(519,"ideas","奇思妙想","//cdn.v2ex.co/navatar/6353/8fe6/519_large.png?m=1424694533"));
        preselectNodeList.add(new Node(43,"jobs","酷工作","//cdn.v2ex.co/navatar/17e6/2166/43_large.png?m=1433389006"));
        preselectNodeList.add(new Node(22,"macosx","Mac OS X","//cdn.v2ex.co/navatar/b6d7/67d2/22_large.png?m=1433389146"));
        preselectNodeList.add(new Node(69,"all4all","二手交易","//cdn.v2ex.co/navatar/14bf/a6bb/69_large.png?m=1419268224"));
        preselectNodeList.add(new Node(551,"free","免费赠送","//cdn.v2ex.co/navatar/7f24/d240/551_large.png?m=1431321114"));
        preselectNodeList.add(new Node(12,"qna","问与答","//cdn.v2ex.co/navatar/c20a/d4d7/12_large.png?m=1433389079"));
//        preselectNodeList.add(new Node(90,"","",""));
//        preselectNodeList.add(new Node(90,"","",""));
    }

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mNavigationView = (NavigationView)inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectMenuItem(menuItem);
                return false;
            }
        });
        for(Node node: preselectNodeList){
            mNavigationView.getMenu().add(TopicListFragment.TOPIC_LIST_TYPE_NODE,(int)node.id,0,node.title);
        }
        selectDefaultOrLastItem();
        return mNavigationView;
    }

    private void selectDefaultOrLastItem(){
        // Select either the default item (0) or the last selected item.
        MenuItem menuItem = mNavigationView.getMenu().findItem(mCurrentSelectedPosition);
        if(menuItem != null){
            selectMenuItem(menuItem);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectMenuItem(MenuItem menuItem) {
        mCurrentSelectedPosition = menuItem.getItemId();
        menuItem.setChecked(true);
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            Node selectedNode = findNodeByMenuItem(menuItem);
            if (selectedNode == null){
                mCallbacks.onNavigationDrawerItemSelected(menuItem);
            }else{
                mCallbacks.onNavigationDrawerNodeSelected(selectedNode);
            }
        }
    }

    @Nullable
    private Node findNodeByMenuItem(MenuItem menuItem){
        if(menuItem.getGroupId() == TopicListFragment.TOPIC_LIST_TYPE_NODE){
            int itemId = menuItem.getItemId();
            for(Node node : preselectNodeList){
                if(node.id == itemId){
                    return node;
                }
            }
        }
        return null;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public  interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(MenuItem menuItem);

        // Called when an node in the navigation drawer is selected
        void onNavigationDrawerNodeSelected(Node node);

    }
}
