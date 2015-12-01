package com.robots.we.parkme.operate;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Slot;

import java.util.Set;

/**
 * Created by supun.hettigoda on 11/30/2015.
 */
public class CarParkGridLayout extends GridLayout {

    Context context;

    public CarParkGridLayout(Context context) {
        super(context);
        this.context = context;
    }

    public CarParkGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarParkGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CarParkGridLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void initialize(CarPark carPark) {
        if (carPark != null) {
            setColumnCount(carPark.getColumns());
            setRowCount(carPark.getRaws());
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
        addView(new SlotView(this.context, slot));
    }
}
