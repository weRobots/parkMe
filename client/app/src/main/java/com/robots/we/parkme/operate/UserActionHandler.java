package com.robots.we.parkme.operate;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.robots.we.parkme.R;
import com.robots.we.parkme.beans.Slot;

import java.util.ArrayList;
import java.util.List;

/**
 * handle all actions related to slots.
 * <p/>
 * Created by supun.hettigoda on 12/4/2015.
 */
public class UserActionHandler {

    private final Activity context;


    public UserActionHandler(Activity context) {
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
                actions.add(new ActionPanel(context, R.mipmap.notify, "send notifications", ActionPanel.ActionType.SEND_NOTIFICATION));

                // release
                actions.add(new ActionPanel(context, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));
                break;

            // already allocated slot
            case ALLOCATED:

                // view details
                // TODO view

                if (AuthenticationHandler.isAdmin())
                    // admin can release
                    actions.add(new ActionPanel(context, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));


                break;
            case AVAILABLE:
                if (AuthenticationHandler.isAdmin())
                    // admin can block
                    actions.add(new ActionPanel(context, R.mipmap.block, "block", ActionPanel.ActionType.BLOCK));

                else
                    actions.add(new ActionPanel(context, R.mipmap.allocate, "allocate for me", ActionPanel.ActionType.ALLOCATE));

                break;
            case BLOCKED:
                if (AuthenticationHandler.isAdmin())
                    // admin can release
                    actions.add(new ActionPanel(context, R.mipmap.release, "release", ActionPanel.ActionType.RELEASE));

                else
                    // view details
                    // TODO view
                    break;
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
}
