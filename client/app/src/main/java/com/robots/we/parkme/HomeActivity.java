package com.robots.we.parkme;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.gcm.play.android.GSMActivity;
import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.convert.CarParkXMLParser;
import com.robots.we.parkme.gps.GPSTracker;
import com.robots.we.parkme.network.HttpRequestHandler;
import com.robots.we.parkme.network.NetworkConnectivityReceiver;
import com.robots.we.parkme.operate.CarParkGridLayout;
import com.robots.we.parkme.operate.SlotView;
import com.robots.we.parkme.operate.UserActionHandler;

import java.io.IOException;
import java.io.InputStream;

public class HomeActivity extends AppCompatActivity implements NetworkConnectivityReceiver.ConnectivityStatusChangedListener, CarParkGridLayout.SlotClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * Client to connect to google play services
     */
    public static GPSTracker GPS_TRACKER;

    /**
     * action handler
     */
    private UserActionHandler userActionHandler;

    /**
     * Whether there is any internet connection.
     */
    public static boolean DATA_CONNECTED = false;

    /**
     * The BroadcastReceiver that tracks network connectivity changes.
     */
    private NetworkConnectivityReceiver connectivityReceiver;

    /**
     * car park view handler
     */
    public static CarParkViewBuilder CAR_PARK_VIEW_BUILDER;

    /**
     * selected car park
     */
    public static String CURRENT_SELECTED_CAR_PARK;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Register BroadcastReceiver to track connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new NetworkConnectivityReceiver();
        connectivityReceiver.registerConnectivityStatusChangedListener(this);
        this.registerReceiver(connectivityReceiver, filter);

        // build a Google API client to connect to google play service
        buildGPSTracker();

        // build the action handler
        buildUserActionHandler();

        // set current car park id
        updateCurrentCarParkId();
    }

    private void updateCurrentCarParkId() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        CURRENT_SELECTED_CAR_PARK = sharedPreferences
                .getString(UserPreferences.SELECTED_CAR_PARK_ID, null);
    }

    private void buildGPSTracker() {
        GPS_TRACKER = new GPSTracker(this);
    }

    private void buildUserActionHandler() {
        userActionHandler = new UserActionHandler(this);
    }

    // Refreshes the user operation display if the network connection is available.
    @Override
    public void onStart() {
        super.onStart();

        // start gps tracking
        GPS_TRACKER.start();

        // update data connection availability on start up
        updateConnectedFlags();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (connectivityReceiver != null) {
            this.unregisterReceiver(connectivityReceiver);
        }
    }

    private void refreshUserOperationPage() {
        if (DATA_CONNECTED) {
            // execute refresh task
            new RefreshTask().execute();
        } else {
            showErrorPage();
        }
    }

    // Displays an error if the app is unable to load content.
    private void showErrorPage() {
        // The specified network connection is not available. Displays error message.
        Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        GPS_TRACKER.registerIndicator(menu.findItem(R.id.action_location));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_map:

                Intent intent = new Intent(this, GSMActivity.class);
                startActivity(intent);

            case R.id.action_refresh:
                refreshUserOperationPage();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    // Checks the network connection and sets the variables accordingly.
    private void updateConnectedFlags() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        DATA_CONNECTED = (activeInfo != null && activeInfo.isConnected());
    }

    @Override
    public void ConnectivityStatusChanged(NetworkConnectivityReceiver.ConnectivityStatus status) {
/*        switch (status) {
            case CONNECTED:
                dataConnected = true;
                Toast.makeText(getApplicationContext(), R.string.connection_ok, Toast.LENGTH_SHORT).show();
            case DISCONNECTED:
                Toast.makeText(getApplicationContext(), R.string.connection_lost, Toast.LENGTH_SHORT).show();
                dataConnected = false;
        }*/
        DATA_CONNECTED = true;
    }

    @Override
    public void onClick(SlotView v) {
        userActionHandler.defineActions(v);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    // Implementation of AsyncTask used to download the latest car park XML and convert it.
    private class RefreshTask extends AsyncTask<Void, Void, CarPark> {

        @Override
        protected CarPark doInBackground(Void... urls) {
            try {
                // create car park view again for the latest server information
                InputStream result = HttpRequestHandler.refresh();
                return CarParkXMLParser.parse(result);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            if (HomeActivity.this.CAR_PARK_VIEW_BUILDER != null) {
                HomeActivity.this.CAR_PARK_VIEW_BUILDER.build(carPark);
            }
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        GPS_TRACKER.stop();
    }

    /**
     * builder definition for create the car park view.
     */
    public interface CarParkViewBuilder {
        void build(CarPark carPark);
    }

    /**
     * to register the  main car park view builder
     *
     * @param carParkViewBuilder
     */
    public void registerCarParkViewBuilder(CarParkViewBuilder carParkViewBuilder) {
        this.CAR_PARK_VIEW_BUILDER = carParkViewBuilder;
    }
}
