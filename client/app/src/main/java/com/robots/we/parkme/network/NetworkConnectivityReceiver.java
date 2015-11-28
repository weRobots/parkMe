package com.robots.we.parkme.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * This BroadcastReceiver intercepts the android.net.ConnectivityManager.CONNECTIVITY_ACTION,
 * which indicates a connection change.
 * <p>
 * Created by suppa on 28/11/2015.
 */
public class NetworkConnectivityReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null)
        {
            Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT).show();
        }

    }


}
