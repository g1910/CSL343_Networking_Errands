package group2.netapp.bidding.currAuctionTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by gaurav on 16/3/15.
 */
public class CurrAuctionTabsAdapter extends FragmentPagerAdapter {
    public CurrAuctionTabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: return new ToParticipateFragment();
            case 1: return new CurrParticipatingFragment();
        };
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "To Participate";
            case 1: return "Participating";
        };
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
