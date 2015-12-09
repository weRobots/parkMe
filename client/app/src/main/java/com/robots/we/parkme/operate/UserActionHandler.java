package com.robots.we.parkme.operate;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.robots.we.parkme.AuthenticationHandler;
import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.R;
import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Slot;
import com.robots.we.parkme.convert.CarParkXMLParser;
import com.robots.we.parkme.network.HttpRequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * handle all actions related to slots.
 * <p/>
 * Created by supun.hettigoda on 12/4/2015.
 */
public class UserActionHandler {

    private static final String TAG = "UserActionHandler";
    private final HomeActivity context;

    // current location
    String latitude;
    String longitude;


    public UserActionHandler(HomeActivity context) {
        this.context = context;
    }

    /**
     * show action set that can be performed upon the selected slot
     *
     * @param v
     */
    public void defineActions(SlotView v) {
        final List<ActionPanel> actionList = buildActionViews(v.getSlot());
        final View actionContainer = buildActionContainer(actionList);
        final PopupWindow popup = createPopupWindow();

        //
        popup.setContentView(actionContainer);

        // Displaying the popup at the bottom screen
        popup.showAtLocation(actionContainer, Gravity.BOTTOM, 0, 0);
    }

    private PopupWindow createPopupWindow() {
        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        popup.setWidth(display.getWidth());
        popup.setHeight(200);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.PopupWindowAnimation);
        popup.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popup.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        return popup;
    }

    private List<ActionPanel> buildActionViews(Slot v) {
        List<ActionPanel> actions = new ArrayList<ActionPanel>();

        switch (v.getStatus()) {

            // current user slot
            case MY_SLOT:
                // send move notifications
                actions.add(new ActionPanel(context, v, R.mipmap.notify, "send notifications", ActionPanel.ActionType.SEND_NOTIFICATION));

                // release
                actions.add(new ActionPanel(context, v, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));
                break;

            // already allocated slot
            case ALLOCATED:

                // view details
                actions.add(new ActionPanel(context, v, -1, "", ActionPanel.ActionType.VIEW));

                if (AuthenticationHandler.isAdmin())
                    // admin can release
                    actions.add(new ActionPanel(context, v, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));


                break;
            case AVAILABLE:
                actions.add(new ActionPanel(context, v, R.mipmap.allocate, "allocate for me", ActionPanel.ActionType.ALLOCATE));

                if (AuthenticationHandler.isAdmin()) {
                    // admin can block
                    actions.add(new ActionPanel(context, v, R.mipmap.block, "block", ActionPanel.ActionType.BLOCK));
                    actions.add(new ActionPanel(context, v, R.mipmap.allocate, "", ActionPanel.ActionType.ADMIN_ALLOCATE));
                }

                break;
            case BLOCKED:
                if (AuthenticationHandler.isAdmin())
                    // admin can release
                    actions.add(new ActionPanel(context, v, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));

                else
                    // view details
                    actions.add(new ActionPanel(context, v, -1, "", ActionPanel.ActionType.VIEW));
                break;
        }

        // add action performer
        for (ActionPanel actionPanel :
                actions) {
            actionPanel.setClickable(true);
            actionPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleActionPerformed((ActionPanel) v);
                }
            });

        }

        return actions;
    }

    private View buildActionContainer(List<ActionPanel> actionList) {
        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context
                .findViewById(R.id.popupLinearLayout);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.popup_layout, viewGroup);
        layout.setOrientation(LinearLayout.VERTICAL);


        for (ActionPanel action :
                actionList) {

            // action param
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.addView(action, param);
        }

        return layout;
    }


    private void handleActionPerformed(ActionPanel actionPanel) {

        // only if network data available
        if (!HomeActivity.DATA_CONNECTED) {
            showNetworkError();
            return;
        }

        switch (actionPanel.getActionType()) {
            case ALLOCATE:

                // perform further if only location data available
                if (!HomeActivity.GPS_TRACKER.canGetLocation()) {
                    HomeActivity.GPS_TRACKER.showSettingsAlert();
                    return;
                }

                // get current location
                Location location = HomeActivity.GPS_TRACKER.getLocation();

                if (location != null) {
                    latitude = Double.toString(location.getLatitude());
                    longitude = Double.toString(location.getLongitude());
                }

                new AllocateTask().execute(actionPanel.getSlot());
                break;
            case BLOCK:
                new BlockTask().execute(actionPanel.getSlot());
                break;
            case RELEASE:
                new ReleaseTask().execute(actionPanel.getSlot());
                break;
            case SEND_NOTIFICATION:
                new NotifyTask().execute(actionPanel.getSlot());
                break;
        }
    }

    // task to allocate
    private class AllocateTask extends AsyncTask<Slot, Void, CarPark> {

        @Override
        protected CarPark doInBackground(Slot... slot) {
            try {
                InputStream result = HttpRequestHandler.allocate(slot[0], latitude, longitude);
                return CarParkXMLParser.parse(result);

            } catch (IOException e) {
                Log.i(TAG, "allocation unsuccessful");
                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            if (carPark == null)
                Log.i(TAG, "allocation action fail ..");
            else {
                Log.i(TAG, "allocation action success ..");
                HomeActivity.CAR_PARK_VIEW_BUILDER.build(carPark);
            }
        }
    }

    // task to release
    private class ReleaseTask extends AsyncTask<Slot, Void, CarPark> {

        @Override
        protected CarPark doInBackground(Slot... slot) {
            try {
                InputStream result = HttpRequestHandler.release(slot[0]);
                return CarParkXMLParser.parse(result);

            } catch (IOException e) {
                Log.i(TAG, "allocation unsuccessful");
                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            if (carPark == null)
                Log.i(TAG, "release action fail ..");
            else {
                Log.i(TAG, "release action success ..");
                HomeActivity.CAR_PARK_VIEW_BUILDER.build(carPark);
            }

        }
    }

    // task to notify blockers
    private class NotifyTask extends AsyncTask<Slot, Void, CarPark> {

        @Override
        protected CarPark doInBackground(Slot... slot) {
            try {
                InputStream result = HttpRequestHandler.notify(slot[0]);
                return CarParkXMLParser.parse(result);

            } catch (IOException e) {
                Log.i(TAG, "notify blocking users unsuccessful");
                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            if (carPark == null)
                Log.i(TAG, "notify blocking users action fail ..");
            else {
                Log.i(TAG, "notify blocking users action success ..");
                HomeActivity.CAR_PARK_VIEW_BUILDER.build(carPark);
            }
        }
    }

    // task to notify blockers
    private class BlockTask extends AsyncTask<Slot, Void, CarPark> {

        @Override
        protected CarPark doInBackground(Slot... slot) {
            try {
                InputStream result = HttpRequestHandler.block(slot[0]);
                return CarParkXMLParser.parse(result);

            } catch (IOException e) {
                Log.i(TAG, "Block slot unsuccessful");
                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            if (carPark == null)
                Log.i(TAG, "Block slot action fail ..");
            else {
                Log.i(TAG, "Block slot action success ..");
                HomeActivity.CAR_PARK_VIEW_BUILDER.build(carPark);
            }

        }
    }


    // Displays an error if the app is unable to load content.
    private void showNetworkError() {
        // The specified network connection is not available. Displays error message.
        Toast.makeText(this.context, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    // Displays an error if the app is unable to get location.
    private void showLocationError() {
        // The specified network connection is not available. Displays error message.
        Toast.makeText(this.context, R.string.location_service_error, Toast.LENGTH_SHORT).show();
    }
}