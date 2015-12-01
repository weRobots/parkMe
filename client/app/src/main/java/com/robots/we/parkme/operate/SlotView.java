package com.robots.we.parkme.operate;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.robots.we.parkme.beans.Slot;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class SlotView extends ImageButton {
    private final Slot slot;

    public SlotView(final Context context, final Slot slot) {
        super(context);

        if (slot == null) {
            throw new UnsupportedOperationException();
        }

        this.slot = slot;
        initialize();
    }

    private void initialize() {
        setUI();
        setBounds();
    }

    private void setUI() {
        //setImageResource(R.drawable);
        setBackgroundColor(Color.GREEN);
    }

    private void setBounds() {
        GridLayout.LayoutParams param = new GridLayout.LayoutParams();
        //param.rightMargin = 5;
        //param.topMargin = 5;
        param.setGravity(Gravity.CENTER);

        switch (this.slot.getSlotType()) {
            case HORIZONTAL:
                param.columnSpec = GridLayout.spec(getSlot().getColumnIndex(), 2);
                param.rowSpec = GridLayout.spec(getSlot().getRawIndex());
                break;
            case VERTICAL:
                param.columnSpec = GridLayout.spec(getSlot().getColumnIndex());
                param.rowSpec = GridLayout.spec(getSlot().getRawIndex(), 2);
                break;
        }

        setLayoutParams(param);
    }

    /**
     * @return
     */
    public Slot getSlot() {
        return slot;
    }
}
