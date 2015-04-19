package group2.netapp.auction;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;

import group2.netapp.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AucBidsFragment extends Fragment {

    TextView location,order;
    JSONArray orders;
    int bidId;
    public AucBidsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_auc_bids, container, false);
        setUpView(v);
        return v;
    }

    public void setUpView(View v){
        Bundle b = getArguments();
        if(b!=null){
            bidId = b.getInt("id",-1);

            location = (TextView) v.findViewById(R.id.auc_bidview_loc);
            order = (TextView) v.findViewById(R.id.auc_bidview_order);

            if(bidId != -1){
                location.setText("BidId:"+bidId);
                order.setText("Order: " + b.getString("order","No order specified"));
            }

        }

    }


}
