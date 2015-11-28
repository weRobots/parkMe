package com.robots.we.parkme.operate;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robots.we.parkme.R;

/**
 * Created by suppa on 27/11/2015.
 */
public class UserOperationsPage extends Fragment {

    public UserOperationsPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText("User operations");
        return rootView;
    }
}
