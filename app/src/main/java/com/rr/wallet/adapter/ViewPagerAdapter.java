package com.rr.wallet.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rr.wallet.fragment.FragmentTab;

/**
 * Created by admin on 22-Mar-18.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new FragmentTab();
        }
        else if (position == 1)
        {
            fragment = new FragmentTab();
        }
        else if (position == 2)
        {
            fragment = new FragmentTab();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "ICICI";
        }
        else if (position == 1)
        {
            title = "PAYTM";
        }
        else if (position == 2)
        {
            title = "WALLET";
        }
        return title;
    }
}
