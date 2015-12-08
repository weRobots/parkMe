package com.robots.we.parkme.operate;


import android.content.Context;
import android.graphics.Color;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.robots.we.parkme.AuthenticationHandler;
import com.robots.we.parkme.beans.Slot;
import com.robots.we.parkme.beans.SlotStatus;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class SlotView extends ImageView {
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
        switch (this.slot.getStatus()) {
            case BLOCKED:
                setBackgroundColor(Color.BLACK);
                break;
            case AVAILABLE:
                setBackgroundColor(Color.GREEN);
                break;
            case ALLOCATED:
                if (isMySlot()) {
                    setBackgroundColor(Color.YELLOW);
                    this.slot.setStatus(SlotStatus.MY_SLOT);
                    break;
                }

                setBackgroundColor(Color.RED);
                break;
        }
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

    private boolean isMySlot() {
        return (this.slot.getUser().getUserId().equals(AuthenticationHandler.USER.getUserId()));
    }

    /**
     * @return
     */
    public Slot getSlot() {
        return slot;
    }
}
