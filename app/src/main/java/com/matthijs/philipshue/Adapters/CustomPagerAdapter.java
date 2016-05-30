package com.matthijs.philipshue.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.matthijs.philipshue.Fragments.GroupsFragment;
import com.matthijs.philipshue.Fragments.LightsFragment;

/**
 * Created by Matthijs Overboom on 28-5-16.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {
    private int amountTabs = 2;

    public CustomPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Groups";
            case 1:
                return "Lights";
            default:
                return null;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GroupsFragment();
            case 1:
                return new LightsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return amountTabs;
    }
}
