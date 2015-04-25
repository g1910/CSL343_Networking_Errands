package group2.netapp.auction;


import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.BidsTabAdapter;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AuctionDashboardFragment extends Fragment{

    private ViewPager viewPager;
    private BidsTabAdapter mAdapter;

    private AuctionDashboardListener listener;
//    private ActionBar actionBar;
//
//
//    private FragmentTabHost aucTabHost;
//
//    // Tab titles
//    private String[] tabs = { "Dashboard", "Requests"};

    public AuctionDashboardFragment() {
        // Required empty public constructor
    }

    public interface AuctionDashboardListener{
        public void openBidRequestFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (AuctionDashboardListener) activity;
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard,menu);
        Log.d("AuctionDashboard", "Menu inflated");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_requests: listener.openBidRequestFragment();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_auction_dashboard, container, false);
        Log.d("AuctionDashboard","settingTaba...");
        setUpTabs(v);

        setHasOptionsMenu(true);

        return v;
    }

    public void setUpTabs(View v){
        viewPager = (ViewPager) v.findViewById(R.id.auc_pager);
        mAdapter = new BidsTabAdapter(getChildFragmentManager(),((AuctionActivity) getActivity()).getRunningBids());

        viewPager.setAdapter(mAdapter);

        if(viewPager==null || mAdapter==null){
            Log.d("AuctionDashboard","nullllllll");
        }

//        actionBar = getActivity().getActionBar();
//        actionBar.setHomeButtonEnabled(false);
//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//
////        Adding Tabs
//        for (String tab_name : tabs) {
//            actionBar.addTab(actionBar.newTab().setText(tab_name)
//                    .setTabListener(this));
//        }
//
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                // on changing the page
//                // make respected tab selected
//                actionBar.setSelectedNavigationItem(position);
//                Log.d("AuctionDashboard", "viewpager onpagechanged " + position);
//            }
//
//            @Override
//            public void onPageScrolled(int arg0, float arg1, int arg2) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int arg0) {
//            }
//        });
    }


//    @Override
//    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
//        viewPager.setCurrentItem(tab.getPosition());
//        Log.d("AuctionDashboard", "viewpager ontabselected" + tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//    }
//
//    @Override
//    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
//
//    }
}
