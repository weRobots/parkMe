package com.robots.we.parkme.operate;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.R;
import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Slot;
import com.robots.we.parkme.convert.CarParkXMLParser;
import com.robots.we.parkme.network.HttpRequestHandler;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by supun.hettigoda on 12/4/2015.
 */
public class ActionPanel extends LinearLayout {

    private static final String TAG = "ActionPanel";

    private final int icon;
    private final String actionText;
    private final ActionType actionType;
    private final Context context;
    private final Slot slot;

    public ActionPanel(Context context, Slot slot, final int icon, final String actionText, final ActionType actionType) {
        super(context);
        this.context = context;
        this.icon = icon;
        this.actionText = actionText;
        this.actionType = actionType;
        this.slot = slot;
        initialize();
    }

    private void initialize() {

        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setBackgroundColor(Color.WHITE);

        switch (this.actionType) {
            case VIEW:
                setClickable(false);
                setOrientation(VERTICAL);

                // add view info text
                // slot id
                TextView slot_id = new TextView(this.context);
                slot_id.setText(this.slot.getId());
                LinearLayout.LayoutParams view_text_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                view_text_param.setMargins(50, 10, 10, 0);
                view_text_param.gravity = Gravity.CENTER_VERTICAL;
                slot_id.setLayoutParams(view_text_param);
                addView(slot_id);

                // slot status
                TextView slot_status = new TextView(this.context);
                slot_status.setText(this.slot.getStatus().toString());
                LinearLayout.LayoutParams text_param_status = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                text_param_status.setMargins(50, 10, 10, 0);
                text_param_status.gravity = Gravity.CENTER_VERTICAL;
                slot_status.setLayoutParams(text_param_status);
                addView(slot_status);

                // user name
                TextView user = new TextView(this.context);
                user.setText(this.slot.getUser().getName());
                LinearLayout.LayoutParams user_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                user_param.setMargins(50, 10, 10, 0);
                user_param.gravity = Gravity.CENTER_VERTICAL;
                user.setLayoutParams(user_param);
                addView(user);

                // slot id
                TextView mobile = new TextView(this.context);
                mobile.setText(this.slot.getUser().getMobileNumber());
                LinearLayout.LayoutParams mobile_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                mobile_param.setMargins(50, 10, 10, 0);
                mobile_param.gravity = Gravity.CENTER_VERTICAL;
                mobile.setLayoutParams(mobile_param);
                addView(mobile);

                break;
            case ADMIN_ALLOCATE:
                // create panel for admin to allocate the slot for a mobile number
                setClickable(false);
                setOrientation(VERTICAL);

                // title text
                TextView title = new TextView(this.context);
                title.setText("Allocate slot: " + this.slot.getId() + " to");
                LinearLayout.LayoutParams title_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                title_param.setMargins(40, 10, 10, 0);
                title_param.gravity = Gravity.CENTER_VERTICAL;
                title.setLayoutParams(title_param);
                addView(title);

                // action panel
                LinearLayout actionLayout = new LinearLayout(this.context);
                actionLayout.setOrientation(HORIZONTAL);
                actionLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                // mobile number field
                final EditText mobile_edit = new EditText(this.context);
                mobile_edit.setInputType(InputType.TYPE_CLASS_PHONE);
                LinearLayout.LayoutParams edit_phone_param = new LinearLayout.LayoutParams(600, ViewGroup.LayoutParams.WRAP_CONTENT);
                edit_phone_param.setMargins(40, 10, 10, 0);
                edit_phone_param.gravity = Gravity.CENTER_VERTICAL;
                mobile_edit.setLayoutParams(edit_phone_param);
                actionLayout.addView(mobile_edit);

                // add allocate button
                ImageButton allocate = new ImageButton(this.context);
                allocate.setImageResource(this.icon);
                LinearLayout.LayoutParams allocate_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                allocate_param.setMargins(20, 10, 10, 30);
                edit_phone_param.gravity = Gravity.CENTER_VERTICAL;
                allocate.setLayoutParams(allocate_param);
                actionLayout.addView(allocate);
                addView(actionLayout, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                allocate.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // only if network data available
                        if (!HomeActivity.DATA_CONNECTED) {
                            showNetworkError();
                            return;
                        }

                        new AdminAllocateTask().execute(new String[]{ActionPanel.this.slot.getId(), mobile_edit.getText().toString()});
                    }
                });
                break;

            case SEND_NOTIFICATION:
            case RELEASE:
            case ALLOCATE:
            case BLOCK:
                setClickable(true);

                // add  icon
                LinearLayout.LayoutParams icon_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                icon_param.setMargins(20, 20, 20, 20);
                ImageView iconView = new ImageView(this.context);
                iconView.setLayoutParams(icon_param);
                iconView.setImageResource(this.icon);
                addView(iconView);

                // add text
                TextView textView = new TextView(this.context);
                textView.setText(this.actionText);
                LinearLayout.LayoutParams text_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                text_param.setMargins(20, 20, 20, 0);
                text_param.gravity = Gravity.CENTER_VERTICAL;
                textView.setLayoutParams(text_param);
                addView(textView);
                break;
        }
    }

    public int getIcon() {
        return icon;
    }

    public String getActionText() {
        return actionText;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public Slot getSlot() {
        return slot;
    }

    public enum ActionType {
        ALLOCATE,

        RELEASE,

        SEND_NOTIFICATION,

        BLOCK,

        ADMIN_ALLOCATE,

        VIEW
    }

    // task to notify blockers
    private class AdminAllocateTask extends AsyncTask<String, Void, CarPark> {

        @Override
        protected CarPark doInBackground(String... param) {
            try {
                InputStream result = HttpRequestHandler.adminAllocate(param[0], param[1]);
                return CarParkXMLParser.parse(result);

            } catch (IOException e) {
                Log.i(TAG, "admin allocate slot unsuccessful");
                return null;
            }
        }

        @Override
        protected void onPostExecute(CarPark carPark) {
            if (carPark == null)
                Log.i(TAG, "admin allocate slot action fail ..");
            else {
                Log.i(TAG, "admin allocate slot action success ..");
                HomeActivity.CAR_PARK_VIEW_BUILDER.build(carPark);
            }
        }
    }

    // Displays an error if the app is unable to load content.
    private void showNetworkError() {
        // The specified network connection is not available. Displays error message.
        Toast.makeText(this.context, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

}
