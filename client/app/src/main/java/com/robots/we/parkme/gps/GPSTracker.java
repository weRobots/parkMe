package com.robots.we.parkme.gps;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
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

    double latitude; // latitude
    double longitude; // longitude

    public GPSTracker(AppCompatActivity context) {
        this.mContext = context;
        buildGPSClient();
        getLocation();
    }

    public Location getCurrentLocation() {
        if (isConnected())
            return LocationServices.FusedLocationApi.getLastLocation(mGPSClient);
    }

    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     */
    public void stop() {
        if (mGPSClient != null)
            mGPSClient.disconnect();
    }

    public void start() {
        if (mGPSClient != null)
            mGPSClient.connect();
    }

    /**
     * Function to get latitude
     */
    public double getLatitude() {
        if (location != null)
            latitude = location.getLatitude();

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null)
            longitude = location.getLongitude();

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
        if (indicator != null)
            indicator.setIcon(R.mipmap.location_active);
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (indicator != null)
            indicator.setIcon(R.mipmap.location_inactive);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (indicator != null)
            indicator.setIcon(R.mipmap.location_inactive);
    }

    protected synchronized void buildGPSClient() {
        mGPSClient = new GoogleApiClient.Builder(this.mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    /**
     * register menu indicator
     *
     * @param indicator
     */
    public void registerIndicator(MenuItem indicator) {
        this.indicator = indicator;
        if (indicator != null)
            if (isConnected())
                indicator.setIcon(R.mipmap.location_active);
            else
                indicator.setIcon(R.mipmap.location_inactive);
    }



}
