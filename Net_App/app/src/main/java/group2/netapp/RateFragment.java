package group2.netapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
public class RateFragment extends Fragment {
    private static final int TWO_FRAGMENTS = 2;
    private ViewPager mViewPager;
    private TabContentFactory mFactory = new TabContentFactory() {

        @Override
        public View createTabContent(String tag) {
            View v = new View(getActivity());
            v.setMinimumHeight(0);
            return v;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragContent = inflater.inflate(R.layout.tabs_with_viewpager,
                container, false);
        mViewPager = (ViewPager) fragContent.findViewById(R.id.pager);
        mViewPager.setAdapter(new TransactionInnerPagerAdapter(getChildFragmentManager()));
        final TabHost tabHost = (TabHost) fragContent
                .findViewById(android.R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("Tab1").setIndicator("Rate Service").setContent(mFactory));
        tabHost.addTab(tabHost.newTabSpec("Tab2").setIndicator("Rate Customer").setContent(mFactory));
        mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equals("Tab1")) {
                    mViewPager.setCurrentItem(0);
                } else if (tabId.equals("Tab2")) {
                    mViewPager.setCurrentItem(1);
                } else if (tabId.equals("Tab3")) {
                    mViewPager.setCurrentItem(2);
                }
            }
        });
        return fragContent;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class TransactionInnerPagerAdapter extends FragmentPagerAdapter {

        public TransactionInnerPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            if (position == 0) {
                return new ServiceRateFragment();
            } else if (position == 1) {
                return new CustomerRateFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return TWO_FRAGMENTS;
        }

    }
}