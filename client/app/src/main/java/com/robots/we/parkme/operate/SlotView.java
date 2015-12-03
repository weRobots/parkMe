package com.robots.we.parkme.operate;


import android.content.Context;
import android.graphics.Color;
import android.widget.GridLayout;
import android.widget.TextView;

import com.robots.we.parkme.beans.Slot;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class SlotView extends TextView {
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

        switch (this.slot.getSlotType()) {
            case HORIZONTAL:
                param.columnSpec = GridLayout.spec(getSlot().getColumnIndex(), 2);
                param.rowSpec = GridLayout.spec(getSlot().getRawIndex());
                param.width = this.gridScale * 2;
                param.height = this.gridScale;
                param.setMargins(5, 5, 5, 0);
                break;
            case VERTICAL:
                param.columnSpec = GridLayout.spec(getSlot().getColumnIndex());
                param.rowSpec = GridLayout.spec(getSlot().getRawIndex(), 2);
                param.width = this.gridScale;
                param.height = this.gridScale * 2;
                param.setMargins(5, 5, 5, 5);
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
