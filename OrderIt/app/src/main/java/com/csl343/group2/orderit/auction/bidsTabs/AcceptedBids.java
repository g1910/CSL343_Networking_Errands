package com.csl343.group2.orderit.auction.bidsTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csl343.group2.orderit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcceptedBids extends Fragment {


    public AcceptedBids() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_bids, container, false);
    }


}
