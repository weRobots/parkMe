package com.robots.we.parkme.operate;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
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

import com.robots.we.parkme.AuthenticationHandler;
import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.R;
import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Slot;
import com.robots.we.parkme.beans.SlotType;
import com.robots.we.parkme.convert.CarParkXMLParser;
import com.robots.we.parkme.network.HttpRequestHandler;

import org.xmlpull.v1.XmlPullParserException;

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
                // TODO view

                if (AuthenticationHandler.isAdmin())
                    // admin can release
                    actions.add(new ActionPanel(context, v, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));


                break;
            case AVAILABLE:
                if (AuthenticationHandler.isAdmin())
                    // admin can block
                    actions.add(new ActionPanel(context, v, R.mipmap.block, "block", ActionPanel.ActionType.BLOCK));

                else
                    actions.add(new ActionPanel(context, v, R.mipmap.allocate, "allocate for me", ActionPanel.ActionType.ALLOCATE));

                break;
            case BLOCKED:
                if (AuthenticationHandler.isAdmin())
                    // admin can release
                    actions.add(new ActionPanel(context, v, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));

                else
                    // view details
                    // TODO view
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

        // action param
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (ActionPanel action :
                actionList) {
            layout.addView(action, param);
        }

        return layout;
    }

    private void handleActionPerformed(ActionPanel actionPanel) {
        switch (actionPanel.getActionType()) {
            case ALLOCATE:
                actionPanel.getSlot();
                break;
            case BLOCK:
                break;
            case RELEASE:
                break;
            case SEND_NOTIFICATION:
                break;
        }
    }

    //
    // task to load user
    private class allocateTask extends AsyncTask<Slot, Void, CarPark> {

        @Override
        protected CarPark doInBackground(Slot... slot) {
            try {
                // create car park view again for the latest server information
                InputStream result = HttpRequestHandler.alocate(slot[0]);
                return CarParkXMLParser.parse(result);
            } catch (IOException e) {
                Log.i(TAG, "allocation unsuccessful");
                return null;
            } catch (XmlPullParserException e) {
                Log.i(TAG, "allocation unsuccessful XML error");

                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            Log.i(TAG, "allocation success..");

        }
    }


}
