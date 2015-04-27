package group2.netapp.bidding;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import group2.netapp.R;
import group2.netapp.auction.cards.RunningBidCard;
import group2.netapp.bidding.cards.NotParticipatingCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidInAuction extends Fragment  implements Card.OnCardClickListener {

    RunningBidListener blistener;



    public interface RunningBidListener {
        public void openRunningBids(int index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        blistener=(RunningBidListener)activity;
    }

    int index;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_bid_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_bid:
                newBid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void newBid() {

        Intent i=new Intent(getActivity(),BidFormActivity.class);
        Bundle b=new Bundle();
        JSONArray topar = ((CurrAuctionActivity)getActivity()).getNotParticipating();
        try {
            b.putInt("idAuction",((JSONObject)topar.get(index)).getInt("idAuction"));
            Log.d("BidInAuction",((JSONObject)topar.get(index)).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        i.putExtras(b);
        startActivity(i);
        //    b.putInt("");
    }

    CardArrayRecyclerViewAdapter auctionViewAdapter;
    CardRecyclerView auctionView;
//    String auctionLocation,price,desc,idUser,start_time,end_time,expected_time;
  //  int idAuction;
    //String ratings,numRated;

//    JSONObject j;

    public BidInAuction() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid_in_auction, container, false);
        setUpView(v);
        setHasOptionsMenu(true);
        return v;
    }

    private void setUpView(View v) {

        Bundle b = getArguments();
        if(b!=null){
            index = b.getInt("index",-1);

            if(index != -1){

                auctionView = (CardRecyclerView) v.findViewById(R.id.runningbids_recyclerview);
                auctionView.setHasFixedSize(false);

                auctionViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setDummyBids());
                auctionView.setLayoutManager(new LinearLayoutManager(getActivity()));

                if(auctionView != null){
                    auctionView.setAdapter(auctionViewAdapter);
                }


/*
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

*/

         //       order.setText("Order: " + b.getString("order","No order specified"));
            }

        }

    }

    public ArrayList<Card> setDummyBids(){
        ArrayList<Card> cards = new ArrayList<Card>();

        JSONArray bids = ((CurrAuctionActivity)getActivity()).getBids();

        for(int i = 0; i <  bids.length() ;++i) {
            //      Card card = new Card(getActivity());

            RunningBidCard card = null;
            try {
                card = new RunningBidCard(getActivity(),(JSONObject)bids.get(i),i);
                card.setOnClickListener(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cards.add(card);

        }

        return cards;
    }

    @Override
    public void onClick(Card card, View view) {
        RunningBidCard c=(RunningBidCard) card;
        Log.d("RunningBIds","HEERE");
        blistener.openRunningBids(c.getIndex());
    }

}
