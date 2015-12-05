package com.robots.we.parkme.operate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Slot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class CarParkGridLayout extends GridLayout {

    final Context context;
    int gridScale;
    OnClickListener slotClickListener;
    List<SlotClickListener> clickListeners = new ArrayList<SlotClickListener>();

    public CarParkGridLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CarParkGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CarParkGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public CarParkGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }

    public void initialize(CarPark carPark) {
        if (carPark != null) {
            setColumnCount(carPark.getColumns());
            setRowCount(carPark.getRaws());
            setUseDefaultMargins(true);

            // set grid scales
            FrameLayout parentView = (FrameLayout) getParent();
            int width = parentView.getWidth();
            int height = parentView.getHeight() - 300;

            int columnWidth = width / carPark.getColumns();
            int rowHeight = height / carPark.getRaws();

            gridScale = (columnWidth < rowHeight) ? columnWidth : rowHeight;

            // add a click lister
            slotClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyListers((SlotView) v);
                }
            };
        }
    }

    /**
     * insert the slots to display
     *
     * @param slots
     */
    public void insert(final Set<Slot> slots) {
        for (Slot slot :
                slots) {
            insert(slot);
        }

    }

    /**
     * insert the slot to display
     *
     * @param slot
     */
    public void insert(final Slot slot) {
        SlotView slotView = new SlotView(this.context, slot, gridScale);
        slotView.setClickable(true);
        slotView.setOnClickListener(slotClickListener);
        addView(slotView);
    }


    public void registerSlotClickListener(SlotClickListener slotClickListener) {
        // add a click lister
        this.clickListeners.add(slotClickListener);

    }

    /**
     * listener interface to catch a onClick event triggered for SlotView
     */
    public interface SlotClickListener {
        public void onClick(SlotView v);
    }

    private void notifyListers(SlotView v) {
        for (SlotClickListener slotClickListener :
                this.clickListeners) {
            slotClickListener.onClick(v);
        }
    }
}
