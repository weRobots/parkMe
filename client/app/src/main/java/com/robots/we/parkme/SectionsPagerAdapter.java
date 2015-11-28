package com.robots.we.parkme;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.robots.we.parkme.admin.AdminPage;
import com.robots.we.parkme.config.ConfigurationPage;
import com.robots.we.parkme.operate.UserOperationsPage;

/**
 * Created by suppa on 27/11/2015.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                // user operation page
                return new UserOperationsPage();
            case 1:
                // configuration page
                return new ConfigurationPage();
            case 2:
                // return admin page
                return new AdminPage();
        }

        return null;
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "User";
            case 1:
                return "Configurations";
            case 2:
                return "Admin ";
        }
        return null;
    }
}
