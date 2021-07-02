package com.sport.playsqorr.adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.sport.playsqorr.fragments.MyCardsLive;
import com.sport.playsqorr.fragments.MyCardsSettled;
import com.sport.playsqorr.fragments.MyCardsUpComing;

public class TabsAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TabsAdapter(FragmentManager fm, int NoofTabs) {
        super(fm);
        this.mNumOfTabs = NoofTabs;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MyCardsUpComing upComing = new MyCardsUpComing();
//                Log.d("tttt-----------",upComing.getTag());
                return upComing;
            case 1:
                MyCardsLive live = new MyCardsLive();
//                Log.d("live-----------",live.getTag());

                return live;
            case 2:
                MyCardsSettled settled = new MyCardsSettled();
                return settled;
            default:
                return null;
        }
    }
}