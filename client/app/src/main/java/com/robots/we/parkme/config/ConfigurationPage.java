package com.robots.we.parkme.config;

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
public class ConfigurationPage extends Fragment {

    public ConfigurationPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.configuration_page, container, false);
        return rootView;
    }
}
