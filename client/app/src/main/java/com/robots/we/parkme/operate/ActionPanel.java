package com.robots.we.parkme.operate;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by supun.hettigoda on 12/4/2015.
 */
public class ActionPanel extends LinearLayout {

    private final int icon;
    private final String actionText;
    private final ActionType actionType;
    private final Context context;

    public ActionPanel(Context context, final int icon, final String actionText, final ActionType actionType) {
        super(context);
        this.context = context;
        this.icon = icon;
        this.actionText = actionText;
        this.actionType = actionType;
        initialize();
    }

    private void initialize() {
        setClickable(true);
        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // add  icon
        LinearLayout.LayoutParams icon_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        icon_param.gravity = Gravity.LEFT;
        ImageView iconView = new ImageView(this.context);
        iconView.setLayoutParams(icon_param);
        addView(iconView);

        // add text
        TextView textView = new TextView(this.context);
        textView.setText(this.actionText);
        LinearLayout.LayoutParams text_param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        text_param.setMargins(10, 0, 0, 0);
        text_param.gravity = Gravity.LEFT;
        textView.setLayoutParams(text_param);
        addView(textView);
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

    public enum ActionType {
        ALLOCATE,

        RELEASE,

        SEND_NOTIFICATION,
    }
}
