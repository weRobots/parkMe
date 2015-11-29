package com.robots.we.parkme.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * This BroadcastReceiver intercepts the android.net.ConnectivityManager.CONNECTIVITY_ACTION,
 * which indicates a connection change.
 * <p/>
 * Created by suppa on 28/11/2015.
 */
public class NetworkConnectivityReceiver extends BroadcastReceiver {

    private final List<ConnectivityStatusChangedListener> listeners;

    public NetworkConnectivityReceiver() {
        listeners = new ArrayList<ConnectivityStatusChangedListener>();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connMgr =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        ConnectivityStatus connectivityStatus =
                (networkInfo != null) ? ConnectivityStatus.CONNECTED : ConnectivityStatus.DISCONNECTED;

        // notify registered listeners
        for (ConnectivityStatusChangedListener listener : listeners) {
            listener.ConnectivityStatusChanged(connectivityStatus);
        }
    }

    public void registerConnectivityStatusChangedListener(ConnectivityStatusChangedListener listener) {
        listeners.add(listener);
    }

    public interface ConnectivityStatusChangedListener {
        public void ConnectivityStatusChanged(ConnectivityStatus status);
    }

    public enum ConnectivityStatus {
        CONNECTED,
        DISCONNECTED;
    }
}
