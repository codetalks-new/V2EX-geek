package com.banxi1988.v2exgeek;

import android.content.Context;
import android.widget.ArrayAdapter;

/**
 * Created by banxi on 15/5/31.
 */
public class NavigationDrawerAdapter extends ArrayAdapter<String> {
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     */
    public NavigationDrawerAdapter(Context context, int resource) {
        super(context, resource);
    }
    // new ArrayAdapter<String>(
//    getActionBar().getThemedContext(),
//    android.R.layout.simple_list_item_activated_1,
//    android.R.id.text1,
//            new String[]{
//        getString(R.string.title_section1),
//                getString(R.string.title_section2),
//                getString(R.string.title_section3),
//    })

}
