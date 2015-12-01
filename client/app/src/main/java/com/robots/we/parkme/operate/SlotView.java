package com.robots.we.parkme.operate;


import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;

import com.robots.we.parkme.beans.Slot;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class SlotView extends Button {
    private final Slot slot;
    private final int gridScale;

    public SlotView(final Context context, final Slot slot, final int gridScale) {
        super(context);

        if (slot == null) {
            throw new UnsupportedOperationException();
        }

        this.slot = slot;
        this.gridScale = gridScale;
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
        param.setGravity(Gravity.FILL);
        param.setMargins(5, 5, 5, 5);

        switch (this.slot.getSlotType()) {
            case HORIZONTAL:
                param.columnSpec = GridLayout.spec(getSlot().getColumnIndex(), 2);
                param.rowSpec = GridLayout.spec(getSlot().getRawIndex());
                setWidth(gridScale * 2);
                setHeight(gridScale);
                break;
            case VERTICAL:
                param.columnSpec = GridLayout.spec(getSlot().getColumnIndex());
                param.rowSpec = GridLayout.spec(getSlot().getRawIndex(), 2);
                setWidth(gridScale);
                setHeight(gridScale * 2);
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
