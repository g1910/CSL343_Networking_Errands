package group2.netapp.bidding.currAuctionTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group2.netapp.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ToParticipateFragment extends Fragment {


    public ToParticipateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_participate, container, false);
    }


}
