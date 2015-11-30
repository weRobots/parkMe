package com.robots.we.parkme.operate;

import android.content.Context;
import android.widget.ImageView;

import com.robots.we.parkme.beans.Slot;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class SlotView extends ImageView {
    private final Slot slot;

    public SlotView(final Context context, final Slot slot) {
        super(context);
        this.slot = slot;
    }

    /**
     * @return
     */
    public Slot getSlot() {
        return slot;
    }
}
