package group2.netapp.auction.bidsTabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by mohit on 16/3/15.
 */
public class BidsTabAdapter extends FragmentPagerAdapter {

    public BidsTabAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new AcceptedBids();
            case 1:
                return new BidRequestsTab();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "Dashboard";

            case 1: return "Requests";
        }
        return super.getPageTitle(position);
    }
}
