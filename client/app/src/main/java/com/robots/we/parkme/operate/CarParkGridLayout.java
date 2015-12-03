package com.robots.we.parkme.operate;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Slot;

import java.util.Set;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class CarParkGridLayout extends GridLayout {

    final Context context;
    int gridScale;

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
        addView(new SlotView(this.context, slot, gridScale));
    }


}
