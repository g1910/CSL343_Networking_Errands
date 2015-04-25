package group2.netapp.bidding;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipatingBidFragment extends Fragment {

    String auctionLocation,price,desc,idUser,start_time,end_time,idAuction,expected_time;
    String ratings,numRated;
    int index;

    public ParticipatingBidFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_participating_bid, container, false);
        setUpView(v);
        return v;
    }

    private void setUpView(View v) {

        Bundle b=getArguments();
        if (b!=null)
        {
            index=b.getInt("index",-1);

            if (index!=-1)
            {
                try {
                    JSONObject j=(JSONObject)((CurrAuctionActivity)getActivity()).getParticipating().get(index);
                    this.auctionLocation =j.getString("location");
                    this.desc = j.getString("description");
                    this.idUser = j.getString("idUser");
                    this.start_time = j.getString("start_time");
                    this.end_time = j.getString("end_time");
                    this.idAuction = j.getString("idAuction");
                    this.expected_time = j.getString("expctd_time");
                    this.price = j.getString("Price");
                    this.ratings=j.getString("rating");
                    this.numRated=j.getString("numRated");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView auctionLocView = (TextView) v.findViewById(R.id.participatinglocation);
                TextView priceView = (TextView) v.findViewById(R.id.participatingprice);
                TextView descView = (TextView)v.findViewById(R.id.participatingdesc);
                RatingBar ratingsView = (RatingBar)v.findViewById(R.id.participatingratings);
                TextView numRatedView = (TextView)v.findViewById(R.id.participatingnumRated);
                TextView end_timeView = (TextView)v.findViewById(R.id.participatingend_time);
                TextView expected_timeView = (TextView)v.findViewById(R.id.participatingexpected_time);

                auctionLocView.setText(auctionLocation);
                priceView.setText("₹" + price);
                descView.setText(desc);
                ratingsView.setRating(Float.parseFloat(ratings));
                numRatedView.setText("rated by : "+numRated+" users");
                end_timeView.setText("Bidding Ends in : "+end_time);
                expected_timeView.setText("Expected Delivery : "+expected_time);

                Button increase= (Button) v.findViewById(R.id.bid_increase);

                increase.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        // Get the layout inflater
                        final LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());

                        // Inflate and set the layout for the dialog
                        // Pass null as the parent view because its going in the dialog layout
                        builder.setView(inflater.inflate(R.layout.dialog_increase_bid, null))
                                .setTitle("Increase Bid")
                                // Add action buttons
                                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {

                                        Log.e("Dialog", "HI");
                                        Dialog f = (Dialog) dialog;
                                        EditText e= (EditText) f.findViewById(R.id.increase_bid);
                                        String text=e.getText().toString();
                                        Log.e("Dialog",text);
                                        // sign in the user ...
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Dialog f = (Dialog) dialog;
                                        f.cancel();
                                    }
                                });

                        AlertDialog alert =  builder.create();
                        alert.show();




                    }
                });


            }

        }

    }


}
