package group2.netapp.bidding;


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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.auction.cards.RunningBidCard;
import group2.netapp.bidding.cards.Order_Card;
import group2.netapp.bidding.cards.ParticipatingCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaceRunningBid extends Fragment {

    CardArrayRecyclerViewAdapter auctionViewAdapter;
    CardRecyclerView auctionView;
    int index;
    int auctionIndex,bidIndex;
    JSONObject auction_details,bid_details;
    JSONArray order;


    public PlaceRunningBid() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View v= inflater.inflate(R.layout.fragment_place_running_bid, container, false);
        setupBidView(v);
        return v;
    }

    private void setupBidView(View v) {

        Bundle b = getArguments();
        if (b != null) {
            index = b.getInt("index", -1);
            bidIndex = b.getInt("bidId");
            auctionIndex=b.getInt("auctionId");
            Log.d("HER",auctionIndex+" ");
            try {
                auction_details=(JSONObject)((CurrAuctionActivity)getActivity()).getNotParticipating().get(auctionIndex);
                bid_details=(JSONObject)((CurrAuctionActivity) getActivity()).getBids().get(bidIndex);
                order=(JSONArray)bid_details.get("order");
                Log.d("HRE",auction_details.toString());
                Log.d("HRE",bid_details.toString());
                Log.d("HRE",order.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (index != -1) {

                auctionView = (CardRecyclerView) v.findViewById(R.id.place_running_bid_recyclerview);
                auctionView.setHasFixedSize(false);

                auctionViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setDummyBids());
                auctionView.setLayoutManager(new LinearLayoutManager(getActivity()));

                if (auctionView != null) {
                    auctionView.setAdapter(auctionViewAdapter);
                }
            }
        }
    }

    private List<Card> setDummyBids() {
        ArrayList<Card> cards = new ArrayList<Card>();

/*        ParticipatingCard card=null;
        card=new ParticipatingCard(getActivity(),auction_details,-1);
        cards.add(card);

        RunningBidCard card_run=null;
        card_run=new RunningBidCard(getActivity(),bid_details,-1);
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
        }*/
        return cards;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.place_bid,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.place_bid:
                placeBid();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void placeBid() {
        Log.d("Success", "Success");
    }
}
