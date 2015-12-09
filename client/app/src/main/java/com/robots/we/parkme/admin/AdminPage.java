package com.robots.we.parkme.admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robots.we.parkme.AuthenticationHandler;
import com.robots.we.parkme.R;
import com.robots.we.parkme.beans.UserRole;

/**
 * Created by suppa on 27/11/2015.
 */
public class AdminPage extends Fragment {

    //
    TextView adminText;
    TextView parkId;
    LinearLayout mainGrid;

    public AdminPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.admin_page, container, false);
        adminText = (TextView) rootView.findViewById(R.id.text_admin);
        parkId = (TextView) rootView.findViewById(R.id.park_id);
        mainGrid = (LinearLayout) rootView.findViewById(R.id.main_grid);

        buildAdminPage();

        return rootView;
    }


    private void buildAdminPage() {
        if (UserRole.ADMIN.equals(AuthenticationHandler.USER.getRole()))
            buildForAdmin();
        else
            buildForDefault();
    }

    private void buildForDefault() {
        adminText.setText("You are a default user");
        mainGrid.setVisibility(View.INVISIBLE);
        parkId.setVisibility(View.INVISIBLE);
    }

    private void buildForAdmin() {
        adminText.setText("Admin tasks");
        mainGrid.setVisibility(View.VISIBLE);
        parkId.setVisibility(View.VISIBLE);
    }
}
