package com.cisol.somenews.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by cisol on 16-11-19.
 */

public class SimpleFragment extends Fragment {
    private String mTitle;
    private static final String BUNDLE_TITLE = "title";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
        }

        TextView textView = new TextView(getActivity());
        textView.setText(mTitle);
        textView.setTextSize(50F);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.rgb(0, 255, 0));

        return textView;
    }

    public static SimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);

        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

}
