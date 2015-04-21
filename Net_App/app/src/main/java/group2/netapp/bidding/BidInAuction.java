package group2.netapp.bidding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import group2.netapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidInAuction extends Fragment {

    int bidId;
    TextView location;

    public BidInAuction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid_in_auction, container, false);
        setUpView(v);
        return v;
    }

    private void setUpView(View v) {

        Bundle b = getArguments();
        if(b!=null){
            bidId = b.getInt("id",-1);

            location = (TextView) v.findViewById(R.id.auction_location);
   //         order = (TextView) v.findViewById(R.id.auc_bidview_order);

            if(bidId != -1){
                location.setText("BidId:"+bidId);
         //       order.setText("Order: " + b.getString("order","No order specified"));
            }

        }

    }

}
