package com.robots.we.parkme.operate;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robots.we.parkme.HomeActivity;
import com.robots.we.parkme.R;
import com.robots.we.parkme.beans.CarPark;

/**
 * Created by suppa on 27/11/2015.
 */
public class UserOperationsPage extends Fragment implements HomeActivity.CarParkViewBuilder {

    private CarParkGridLayout slotLayout;

    public UserOperationsPage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_operation_page, container, false);
        rootView.setBackgroundColor(Color.LTGRAY);
        slotLayout = (CarParkGridLayout) rootView.findViewById(R.id.slot_grid);
        slotLayout.registerSlotClickListener((HomeActivity) getActivity());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((HomeActivity) getActivity()).registerCarParkViewBuilder(this);
    }

    @Override
    public void build(CarPark carPark) {
        // building full slot view again
        slotLayout.removeAllViews();

        if (carPark != null) {
            slotLayout.initialize(carPark);
            slotLayout.insert(carPark.getSlots());
        }
    }
}
