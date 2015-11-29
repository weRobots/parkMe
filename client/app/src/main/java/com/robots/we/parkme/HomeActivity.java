package com.robots.we.parkme;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.robots.we.parkme.network.CarParkFileOperationTest;
import com.robots.we.parkme.network.NetworkConnectivityReceiver;

import we.robots.parkme.park.CarPark;

public class HomeActivity extends AppCompatActivity implements NetworkConnectivityReceiver.ConnectivityStatusChangedListener {

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
     * Whether there is any internet connection.
     */
    private static boolean dataConnected = false;

    /**
     * The BroadcastReceiver that tracks network connectivity changes.
     */
    private NetworkConnectivityReceiver connectivityReceiver;

    /**
     * car park view handler
     */
    CarParkViewBuilder carParkViewBuilder;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        // adding refresh button
        FloatingActionButton refresh = (FloatingActionButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshUserOperationPage();
            }
        });

        // Register BroadcastReceiver to track connection changes.
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        connectivityReceiver = new NetworkConnectivityReceiver();
        connectivityReceiver.registerConnectivityStatusChangedListener(this);
        this.registerReceiver(connectivityReceiver, filter);
    }

    // Refreshes the user operation display if the network connection is available.
    @Override
    public void onStart() {
        super.onStart();

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
        if (dataConnected) {
            // AsyncTask subclass
            new RefreshTask().execute("empty");
        } else {
            showErrorPage();
        }
    }

    // Displays an error if the app is unable to load content.
    private void showErrorPage() {
        // TODO

        // The specified network connection is not available. Displays error message.
        Toast.makeText(getApplicationContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
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

    // Checks the network connection and sets the variables accordingly.
    private void updateConnectedFlags() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
        dataConnected = (activeInfo != null && activeInfo.isConnected());
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
        dataConnected = true;
    }

    /**
     * to register the  main car park view builder
     *
     * @param carParkViewBuilder
     */
    public void registerCarParkViewBuilder(CarParkViewBuilder carParkViewBuilder) {
        this.carParkViewBuilder = carParkViewBuilder;
    }

    // Implementation of AsyncTask used to download the latest car park XML and convert it.
    private class RefreshTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
/*            try {
                return HttpRequestHandler.refresh();
            } catch (IOException e) {
                return null;
            }*/

            return "";
        }

        @Override
        protected void onPostExecute(String result) {
            // create car park view again for the latest server information
            if (result != null) {
               /* XStream stream = new XStream(new StaxDriver());
                CarPark latest = (CarPark) stream.fromXML(result);*/

                CarPark latest = new CarParkFileOperationTest().createOfficeCarPark();

                if (HomeActivity.this.carParkViewBuilder != null) {
                    HomeActivity.this.carParkViewBuilder.build(latest);
                }
            }
        }
    }

    /**
     * builder definition for create the car park view.
     */
    public interface CarParkViewBuilder {
        void build(CarPark carPark);
    }
}
