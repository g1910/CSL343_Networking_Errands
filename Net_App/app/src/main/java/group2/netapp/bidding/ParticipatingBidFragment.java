package group2.netapp.bidding;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
import group2.netapp.bidding.cards.RunningBidCard;
import group2.netapp.bidding.cards.Order_Card;
import group2.netapp.bidding.cards.ParticipatingCard;
import group2.netapp.utilFragments.ServerConnect;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParticipatingBidFragment extends Fragment {

    CardArrayRecyclerViewAdapter auctionViewAdapter;
    CardRecyclerView auctionView;

    String auctionLocation,desc,idUser,start_time,end_time,expected_time;
    String ratings,numRated,status;
    int index,idAuction;
    int bidId,price;
    String rank;
    String bidLocation;
    JSONArray order;
    JSONObject auction_details,bid_details;

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

        Log.d("Incerasesae",this.status);
        if (this.status.equals("A"))
        {

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
                                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                final String fid = saved_values.getString("id",null);
                                nameValuePairs.add(new BasicNameValuePair("id_user",fid));
                                nameValuePairs.add(new BasicNameValuePair("idAuction",String.valueOf(idAuction)));
                                nameValuePairs.add(new BasicNameValuePair("idBid",String.valueOf(bidId)));
                                nameValuePairs.add(new BasicNameValuePair("price",String.valueOf(newVal)));
                                Log.d("ParticipatingFragment",getString(R.string.IP) + "updatePrice.php");

                                myServer.execute(getString(R.string.IP) + "updatePrice.php", nameValuePairs);
                                Log.d("ParticipatingFragment", getString(R.string.IP) + "getAllAuctions.php");
                                Log.e("Partici                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          patingFragment", "Hi");

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

        else
        {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                    "It is still not Accepted",
                    Toast.LENGTH_LONG);
            toast.show();
        }



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
                    auction_details=j;
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
                    this.rank=j.getString("rank");
                    this.status=j.getString("status");
                    Log.e("Rank",rank+ " ");

                    JSONArray jr=((CurrAuctionActivity)getActivity()).getBids();

                    for (int i=0;i<jr.length();i++)
                    {
                        JSONObject temp=(JSONObject)jr.get(i);
                        if (temp.getInt("idBid")==bidId)
                        {
                            bid_details=temp;
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

                auctionView = (CardRecyclerView) v.findViewById(R.id.participating_bid_recyclerview);
                auctionView.setHasFixedSize(false);

                auctionViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setDummyBids());
                auctionView.setLayoutManager(new LinearLayoutManager(getActivity()));

                if(auctionView != null){
                    auctionView.setAdapter(auctionViewAdapter);
                }

/*                TextView auctionLocView = (TextView) v.findViewById(R.id.participatinglocation);
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
*/
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

    private List<Card> setDummyBids() {

        ArrayList<Card> cards = new ArrayList<Card>();

        ParticipatingCard card=null;
        card=new ParticipatingCard(getActivity(),auction_details,-1);
        cards.add(card);

        RunningBidCard card_run=null;
        card_run=new RunningBidCard(getActivity(),bid_details,-1,this.rank,this.price,this.status,this.idUser);


        cards.add(card_run);



        Log.d("OrderCard",order.toString());

        for (int i=0;i<order.length();i++)
        {
//            Card orderCard = new OrderCard(getActivity());
            Order_Card orderCard=null;
            try {
                Log.d("OrderCard",order.get(i).toString());
                orderCard= new Order_Card(getActivity(),(JSONObject)order.get(i),i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cards.add(orderCard);
        }



//        for(int i = 0; i <  Participating.length() ;++i) {
            //      Card card = new Card(getActivity());

  //          ParticipatingCard card = null;
    //        try {
      //          card = new ParticipatingCard(getActivity(),(JSONObject)Participating.get(i),i);
        //    } catch (JSONException e) {
       //         e.printStackTrace();
         //   }
  //          cards.add(card);

//        }

        return cards;
    }


}
