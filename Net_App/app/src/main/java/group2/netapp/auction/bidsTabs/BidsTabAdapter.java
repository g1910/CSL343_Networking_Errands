package group2.netapp.auction.bidsTabs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import group2.netapp.R;
import group2.netapp.auction.BidRequestsFragment;

/**
 * Created by mohit on 16/3/15.
 */
public class BidsTabAdapter extends FragmentPagerAdapter {

    JSONArray acceptedBids;
    int isRunning;

    public BidsTabAdapter(FragmentManager fm, JSONArray acceptedBids, int isRunning) {
        super(fm);
        this.acceptedBids = acceptedBids;
        this.isRunning = isRunning;
        Log.d("BidTabsAdapter", acceptedBids.toString());
    }


    @Override
    public Fragment getItem(int position) {
        Fragment f;
        if(isRunning == 1) {
            f = new AcceptedBids();
        }else{

            f = new PostAcceptedBids();
        }
        Bundle args = new Bundle();
        try {
            args.putString("auction_category",acceptedBids.getJSONObject(position).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        f.setArguments(args);
        return f;
    }

    @Override
    public int getCount() {
        return acceptedBids.length();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            Log.d("BidTabsAdapter",acceptedBids.length()+" "+position);
            Log.d("BidTabsAdapter",acceptedBids.getJSONObject(position).getInt("minPrice")+"");
            String title = "Dashboard - \u20B9 " + acceptedBids.getJSONObject(position).getInt("minPrice");
            Log.d("BidTabsAdapter",title);
            return title;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "Dashboard " + position;
    }
}
