package com.robots.we.parkme.config;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gcm.play.android.GCMPreferences;
import com.google.gson.Gson;
import com.robots.we.parkme.AuthenticationHandler;
import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.R;
import com.robots.we.parkme.UserPreferences;
import com.robots.we.parkme.beans.User;
import com.robots.we.parkme.network.HttpRequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * Created by suppa on 27/11/2015.
 */
public class ConfigurationPage extends Fragment implements AuthenticationHandler.UserProfileBuilder {
    private static final String TAG = "ConfigurationPage";
    private Button buttonUserProfile;
    private View userProfileView;
    private RadioGroup radioGroup;
    private FrameLayout rootView;

    // map
    private final HashMap<Integer, String> parkList_radioMap = new HashMap<>();

    public ConfigurationPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(R.layout.configuration_page, container, false);
        rootView.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

        // user profile
        userProfileView = rootView.findViewById(R.id.slider);
        userProfileView.setVisibility(View.INVISIBLE);

        // register to receive when to build the profile
        AuthenticationHandler.registerUserProfileBuilder(this);

        // load user
        loadUser();

        // set up saving actions
        Button saveButton = (Button) userProfileView.findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });
        Button signButton = (Button) userProfileView.findViewById(R.id.button_sign);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUser();
            }
        });

        // set up searching action
        ImageView buttonLocation = (ImageView) rootView.findViewById(R.id.button_location);
        buttonLocation.setClickable(true);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });

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

        // radio group for park list
        radioGroup = ((RadioGroup) rootView.findViewById(R.id.park_list_radio));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String carParkId = parkList_radioMap.get(checkedId);

                // need to update this as the selected car park
                // first save this as the shared as, selected car park
                SharedPreferences.Editor editor;
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());
                editor = sharedPreferences.edit();
                editor.putString(UserPreferences.SELECTED_CAR_PARK_ID, carParkId);
                editor.commit();

                // call home activity method to apply the new car park to system
                ((HomeActivity) getActivity()).updateToCurrentCarPark();
            }
        });

        // build park list radio view
        rebuildParkList();

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

        // role
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

    @Override
    public void updatePreferenceData() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());
        sharedPreferences.edit().putString(UserPreferences.USER_ID, AuthenticationHandler.USER.getUserId()).commit();
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

    private void saveUser() {
        if (HomeActivity.DATA_CONNECTED) {

            // update the saved token
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());
            String token = sharedPreferences.getString(GCMPreferences.TOKEN, "");
            AuthenticationHandler.USER.setRegistrationToken(token);

            mapUIDataToUser();
            AuthenticationHandler.save();
        }
    }

    private void mapUIDataToUser() {

        // get default preferences to get user id
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());
        String userId = sharedPreferences
                .getString(UserPreferences.USER_ID, null);
        AuthenticationHandler.USER.setUserId(userId);

        // name
        EditText nameField = (EditText) userProfileView.findViewById(R.id.name_value);
        AuthenticationHandler.USER.setName(nameField.getText().toString());

        // mobile no
        EditText mobile = (EditText) userProfileView.findViewById(R.id.mobile_no);
        AuthenticationHandler.USER.setMobileNumber(mobile.getText().toString());
    }

    private void search() {

        // only if network data available
        if (!HomeActivity.DATA_CONNECTED) {
            showNetworkError();
            return;
        }

        // perform further if only location data available
        if (!HomeActivity.GPS_TRACKER.canGetLocation()) {
            showLocationError();
            return;
        }

        // get current location
        Location location = HomeActivity.GPS_TRACKER.getLocation();

        new FindTask().execute(new String[]{Double.toString(location.getLatitude()), Double.toString(location.getLongitude())});

    }

    // task to release
    private class FindTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... slot) {
            try {
                InputStream result = HttpRequestHandler.find(slot[0], slot[1]);
                return HttpRequestHandler.getStringFromInputStream(result);

            } catch (IOException e) {
                Log.i(TAG, "find near car park unsuccessful");
                return null;
            }
        }

        @Override
        protected void onPostExecute(String id) {
            if (id == null || id.isEmpty())
                Log.i(TAG, "no accessible car park near ..");
            else {
                Log.i(TAG, "Car park found ..");

                if (!isAlreadyExisting(id)) {
                    addCarPark(id);
                    rebuildParkList();
                }
            }
        }
    }

    private boolean isAlreadyExisting(String id) {
        for (String existingId :
                parkList_radioMap.values()) {
            if (existingId.equals(id))
                return true;
        }
        return false;
    }

    // car park shared preferences related methods
    public void saveCarParks(List<String> favorites) {

        SharedPreferences.Editor editor;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());
        editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String jsonCarParks = gson.toJson(favorites);

        editor.putString(UserPreferences.CONFIGURED_CAR_PARK_IDS, jsonCarParks);
        editor.commit();
    }

    private void addCarPark(String carPark) {
        ArrayList<String> carParks = getCarParks();
        if (carParks == null)
            carParks = new ArrayList<String>();
        carParks.add(carPark);
        saveCarParks(carParks);
    }

    private void removeCarPark(String carPark) {
        ArrayList<String> carParks = getCarParks();
        if (carParks != null) {
            carParks.remove(carPark);
            saveCarParks(carParks);
        }
    }

    private ArrayList<String> getCarParks() {

        List<String> carParks;
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences((HomeActivity) getActivity());

        if (sharedPreferences.contains(UserPreferences.CONFIGURED_CAR_PARK_IDS)) {
            String jsonFavorites = sharedPreferences.getString(UserPreferences.CONFIGURED_CAR_PARK_IDS, null);
            Gson gson = new Gson();
            String[] carParkItems = gson.fromJson(jsonFavorites,
                    String[].class);

            carParks = Arrays.asList(carParkItems);
            carParks = new ArrayList<String>(carParks);
        } else
            return null;

        return (ArrayList<String>) carParks;
    }
    // car park shared preferences related methods ends

    // Displays an error if the app is unable to load content.
    private void showNetworkError() {
        // The specified network connection is not available. Displays error message.
        Toast.makeText((HomeActivity) getActivity(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    // Displays an error if the app is unable to get location.
    private void showLocationError() {
        // The specified network connection is not available. Displays error message.
        Toast.makeText((HomeActivity) getActivity(), R.string.location_service_error, Toast.LENGTH_SHORT).show();
    }

    // re build radio group
    private void rebuildParkList() {

        // clear existing
        radioGroup.removeAllViews();

        // clear id map
        parkList_radioMap.clear();

        // list from shared preferences
        List<String> list = getCarParks();

        if (list != null && !list.isEmpty()) {
            for (String id :
                    list) {
                RadioButton rdbtn = new RadioButton(getActivity());
                rdbtn.setText(id);
                RadioGroup.LayoutParams rprms = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                radioGroup.addView(rdbtn, rprms);

                // create id map
                parkList_radioMap.put(rdbtn.getId(), id);

                // current one need to be selected
                if (HomeActivity.CURRENT_SELECTED_CAR_PARK == id)
                    radioGroup.check(rdbtn.getId());
            }
        }
    }
}
