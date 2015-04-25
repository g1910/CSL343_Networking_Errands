package group2.netapp.bidding;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.utilFragments.ServerConnect;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipatingBidFragment extends Fragment {

    String auctionLocation,desc,idUser,start_time,end_time,expected_time;
    String ratings,numRated;
    int index,idAuction;
    int bidId,price,rank;
    String bidLocation;
    JSONArray order;

    public ParticipatingBidFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.increase_bid_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.increase_bid:
                increaseBid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_participating_bid, container, false);
        setUpView(v);
        setHasOptionsMenu(true);
        return v;
    }

    private void increaseBid() {

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
                        EditText e = (EditText) f.findViewById(R.id.increase_bid);
                        String text = e.getText().toString();
                        Log.d("Dialog", text);
                        int newVal = Integer.valueOf(text);
                        Log.d("New Price", newVal + " ");
                        Log.d("Old Price", price + " ");
                        Log.d("Auction ID", idAuction + " ");
                        Log.d("Bid ID", bidId + " ");

                        if (newVal < price) {
                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                    "New Price Must be more than old price",
                                    Toast.LENGTH_LONG);
                            toast.show();
                        } else {
                            Log.d("Here", "Setting new Price");
                            ServerConnect myServer = new ServerConnect(getActivity());

                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("id_user", "1"));
                            nameValuePairs.add(new BasicNameValuePair("idAuction",String.valueOf(idAuction)));
                            nameValuePairs.add(new BasicNameValuePair("idBid",String.valueOf(bidId)));
                            nameValuePairs.add(new BasicNameValuePair("price",String.valueOf(newVal)));
                            Log.d("ParticipatingFragment",getString(R.string.IP) + "updatePrice.php");

                            myServer.execute(getString(R.string.IP) + "updatePrice.php", nameValuePairs);
                            Log.d("ParticipatingFragment", getString(R.string.IP) + "getAllAuctions.php");
                            Log.e("ParticipatingFragment", "Hi");

                            Log.d("ParticipatingFragment", "ProgressAuctionOpened");

                        }

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
                    this.idAuction = j.getInt("idAuction");
                    this.expected_time = j.getString("expctd_time");
                    this.price = j.getInt("Price");
                    this.ratings=j.getString("rating");
                    this.numRated=j.getString("numRated");
                    this.bidId=j.getInt("idBid");
                    this.rank=j.getInt("rank");
                    Log.e("Rank",rank+ " ");

                    JSONArray jr=((CurrAuctionActivity)getActivity()).getBids();

                    for (int i=0;i<jr.length();i++)
                    {
                        JSONObject temp=(JSONObject)jr.get(i);
                        if (temp.getInt("idBid")==bidId)
                        {
                            bidLocation=temp.getString("location");
                            order=(JSONArray)temp.get("order");
                            Log.d("Here",bidLocation);
                            Log.d("Here",bidId+" ");
                            break;
                        }
                    }

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
                TextView rankView=(TextView)v.findViewById(R.id.participatingrank);

                auctionLocView.setText(auctionLocation);
                priceView.setText("₹" + price);
                descView.setText(desc);
                ratingsView.setRating(Float.parseFloat(ratings));
                numRatedView.setText("rated by : "+numRated+" users");
                end_timeView.setText("Bidding Ends in : "+end_time);
                expected_timeView.setText("Expected Delivery : "+expected_time);
                rankView.setText(rank+" ");

/*                Button increase= (Button) v.findViewById(R.id.bid_increase);

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
                                        EditText e = (EditText) f.findViewById(R.id.increase_bid);
                                        String text = e.getText().toString();
                                        Log.d("Dialog", text);
                                        int newVal = Integer.valueOf(text);
                                        Log.d("New Price", newVal + " ");
                                        Log.d("Old Price", price + " ");
                                        Log.d("Auction ID", idAuction + " ");
                                        Log.d("Bid ID", bidId + " ");

                                        if (newVal < price) {
                                            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                                                    "New Price Must be more than old price",
                                                    Toast.LENGTH_LONG);
                                            toast.show();
                                        } else {
                                            Log.d("Here", "Setting new Price");
                                            ServerConnect myServer = new ServerConnect(getActivity());

                                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                                            nameValuePairs.add(new BasicNameValuePair("id_user", "1"));
                                            nameValuePairs.add(new BasicNameValuePair("idAuction",String.valueOf(idAuction)));
                                            nameValuePairs.add(new BasicNameValuePair("idBid",String.valueOf(bidId)));
                                            nameValuePairs.add(new BasicNameValuePair("price",String.valueOf(newVal)));
                                            Log.d("ParticipatingFragment",getString(R.string.IP) + "updatePrice.php");

                                            myServer.execute(getString(R.string.IP) + "updatePrice.php", nameValuePairs);
                                            Log.d("ParticipatingFragment", getString(R.string.IP) + "getAllAuctions.php");
                                            Log.e("ParticipatingFragment", "Hi");

                                            Log.d("ParticipatingFragment", "ProgressAuctionOpened");

                                        }

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

*/
            }

        }

    }


}
