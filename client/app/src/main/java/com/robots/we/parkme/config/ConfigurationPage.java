package com.robots.we.parkme.config;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.R;
import com.robots.we.parkme.UserPreferences;
import com.robots.we.parkme.beans.User;
import com.robots.we.parkme.operate.AuthenticationHandler;

/**
 * Created by suppa on 27/11/2015.
 */
public class ConfigurationPage extends Fragment implements AuthenticationHandler.UserProfileBuilder {

    private Button buttonUserProfile;
    private View userProfileView;

    public ConfigurationPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FrameLayout rootView = (FrameLayout) inflater.inflate(R.layout.configuration_page, container, false);
        rootView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // user profile
        userProfileView = rootView.findViewById(R.id.slider);
        userProfileView.setVisibility(View.INVISIBLE);

        // register to receive when to build the profile
        AuthenticationHandler.registerUserProfileBuilder(this);

        // load user
        loadUser();

        // add user profile toggle action
        buttonUserProfile = (Button) rootView.findViewById(R.id.button_user_profile);
        buttonUserProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // slider toggle when click
                ViewGroup.LayoutParams params = userProfileView.getLayoutParams();

                params.height = params.height == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : 0;

                userProfileView.setLayoutParams(params);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(final Context context) {
        super.onAttach(context);

    }

    @Override
    public void buildUserProfile() {
        userProfileView.setVisibility(View.VISIBLE);

        User user = AuthenticationHandler.USER;
        final boolean isSigned = (user.getUserId() != null) && !user.getUserId().isEmpty();

        // user id
        TextView idFiled = (TextView) userProfileView.findViewById(R.id.user_id_value);
        idFiled.setText(isSigned ? user.getUserId() : "ur not registered yet");

        // name
        EditText nameField = (EditText) userProfileView.findViewById(R.id.name_value);
        nameField.setText(user.getName());

        // mobile no
        EditText mobile = (EditText) userProfileView.findViewById(R.id.mobile_no);
        mobile.setText(user.getMobileNumber());

        // mobile no
        TextView role = (TextView) userProfileView.findViewById(R.id.role);
        role.setText(user.getRole().toString());

        Button saveButton = (Button) userProfileView.findViewById(R.id.button_save);
        Button signButton = (Button) userProfileView.findViewById(R.id.button_sign);

        if (isSigned) {
            saveButton.setEnabled(true);
            signButton.setEnabled(false);
        } else {
            saveButton.setEnabled(false);
            signButton.setEnabled(true);
            idFiled.setTextColor(Color.RED);
        }

        // close after build
        ViewGroup.LayoutParams params = userProfileView.getLayoutParams();
        params.height = 0;
        userProfileView.setLayoutParams(params);
    }

    private void loadUser() {
        if (HomeActivity.DATA_CONNECTED) {
            // load user
            // get default preferences to get user id
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());
            String userId = sharedPreferences
                    .getString(UserPreferences.USER_ID, null);
            AuthenticationHandler.load(userId);
        }
    }
}
