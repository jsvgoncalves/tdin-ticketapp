package org.up.fe.tdin.adapter;

import org.up.fe.tdin.tdintroubletickets.AssignedTicketsFragment;
import org.up.fe.tdin.tdintroubletickets.UnAssignedTicketsFragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new AssignedTicketsFragment();
        case 1:
            // Games fragment activity
            return new UnAssignedTicketsFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }
 
}