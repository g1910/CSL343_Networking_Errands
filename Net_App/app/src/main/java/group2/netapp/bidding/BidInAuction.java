package group2.netapp.bidding;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidInAuction extends Fragment {

    int index;
    String auctionLocation,price,desc,idUser,start_time,end_time,expected_time;
    int idAuction;
    String ratings,numRated;

    JSONObject j;

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
            index = b.getInt("index",-1);

            if(index != -1){

                try {
                    j=(JSONObject)((CurrAuctionActivity)getActivity()).getNotParticipating().get(index);
                    this.auctionLocation =j.getString("location");
                    this.desc = j.getString("description");
                    this.idUser = j.getString("idUser");
                    this.start_time = j.getString("start_time");
                    this.end_time = j.getString("end_time");
                    this.idAuction = j.getInt("idAuction");
                    this.expected_time = j.getString("expctd_time");
                    this.price = "0";
                    this.ratings=j.getString("rating");
                    this.numRated=j.getString("numRated");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView auctionLocView = (TextView) v.findViewById(R.id.bidinauctionlocation);
                TextView priceView = (TextView) v.findViewById(R.id.bidinauctionprice);
                TextView descView = (TextView)v.findViewById(R.id.bidinauctiondesc);
                RatingBar ratingsView = (RatingBar)v.findViewById(R.id.bidinauctionratings);
                TextView numRatedView = (TextView)v.findViewById(R.id.bidinauctionnumRated);
                TextView end_timeView = (TextView)v.findViewById(R.id.bidinauctionend_time);
                TextView expected_timeView = (TextView)v.findViewById(R.id.bidinauctionexpected_time);

                auctionLocView.setText(auctionLocation);
                priceView.setText("₹" + price);
                descView.setText(desc);
                ratingsView.setRating(Float.parseFloat(ratings));
                numRatedView.setText("rated by : "+numRated+" users");
                end_timeView.setText("Bidding Ends in : "+end_time);
                expected_timeView.setText("Expected Delivery : "+expected_time);



         //       order.setText("Order: " + b.getString("order","No order specified"));
            }

        }

    }

}
