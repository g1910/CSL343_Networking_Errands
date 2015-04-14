package group2.netapp.auction;


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
public class AucBidsFragment extends Fragment {

    TextView location,order;

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
            location = (TextView) v.findViewById(R.id.auc_bidview_loc);
            order = (TextView) v.findViewById(R.id.auc_bidview_order);

            location.setText("Location: "+b.getString("location","No location specified"));
            order.setText("Order: " + b.getString("order","No order specified"));
        }

    }


}
