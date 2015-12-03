package com.robots.we.parkme.gps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.robots.we.parkme.R;


/**
 * Created by suppa on 04/12/2015.
 */
public final class GPSTracker implements ConnectionCallbacks, OnConnectionFailedListener {

    private final AppCompatActivity mContext;

    /**
     * google service client
     */
    private GoogleApiClient mGPSClient;

    /**
     * location indicator
     */
    private MenuItem indicator;

    // flag for GPS status
    boolean isGPSEnabled = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    public GPSTracker(AppCompatActivity context) {
        this.mContext = context;
        this.indicator = ((Toolbar) this.mContext.findViewById(R.id.tool_bar)).getMenu().getItem(R.id.action_location);
        buildGPSClient();
        getLocation();
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stopUsingGPS() {
        if (mGPSClient != null) {
            mGPSClient.disconnect();
        }
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

    /**
     * Function to check GPS/wifi enabled
     *
     * @return boolean
     */
    public boolean isConnected() {
        return mGPSClient.isConnected();
    }

    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     */
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        // Setting Dialog Title
        alertDialog.setTitle("GPS settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onConnected(Bundle bundle) {
        indicator.setIcon(R.mipmap.location_active);
    }

    @Override
    public void onConnectionSuspended(int i) {
        indicator.setIcon(R.mipmap.location_inactive);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        indicator.setIcon(R.mipmap.location_inactive);
    }

    protected synchronized void buildGPSClient() {
        mGPSClient = new GoogleApiClient.Builder(this.mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
}
