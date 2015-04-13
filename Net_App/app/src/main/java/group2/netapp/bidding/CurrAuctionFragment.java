package group2.netapp.bidding;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group2.netapp.R;
import group2.netapp.bidding.currAuctionTabs.CurrAuctionTabsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrAuctionFragment extends Fragment implements ActionBar.TabListener {

    private ViewPager viewPager;
    private CurrAuctionTabsAdapter mAdapter;
    private ActionBar actionBar;

    private String[] tabs = { "To Participate", "Participating"};


    public CurrAuctionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_curr_auction, container, false);

        setUpTabs(v);

        return v;
    }

    private void setUpTabs(View v) {

        viewPager = (ViewPager) v.findViewById(R.id.curr_auc_pager);
        actionBar = getActivity().getActionBar();
        mAdapter = new CurrAuctionTabsAdapter(getChildFragmentManager());

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tabName : tabs){
            actionBar.addTab(actionBar.newTab().setText(tabName).setTabListener(this));
        }

        viewPager.setOnPageChangeListener(onTabChanged);
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        viewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    ViewPager.OnPageChangeListener onTabChanged = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            actionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
