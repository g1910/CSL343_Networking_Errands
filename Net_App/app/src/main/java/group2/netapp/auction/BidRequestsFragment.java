package group2.netapp.auction;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import group2.netapp.R;
import group2.netapp.auction.AuctionActivity;
import group2.netapp.auction.cards.BidCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class BidRequestsFragment extends Fragment implements Card.OnCardClickListener {



    CardArrayRecyclerViewAdapter bidViewAdapter;
    CardRecyclerView bidView;
    BidRequestsListener bListener;

    JSONArray pendingBids;

    public interface BidRequestsListener{
        public void openBidRequest(JSONObject bid, boolean isRequest);
    }


    public BidRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bListener = (BidRequestsListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_bid_requests, container, false);
        setUpBidView(v);

        return v;
    }

    public void setUpBidView(View v){
        pendingBids = ((AuctionActivity) getActivity()).getPendingBids();
        bidView = (CardRecyclerView) v.findViewById(R.id.auc_bids_recyclerview);
        bidView.setHasFixedSize(false);

        bidViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setBids());
        bidView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(bidView != null){
            bidView.setAdapter(bidViewAdapter);
        }
    }

    public ArrayList<Card> setBids(){
        ArrayList<Card> cards = new ArrayList<Card>();
        JSONObject bid;
        try {
            for(int i = 0; i< pendingBids.length(); ++i) {
                bid = pendingBids.getJSONObject(i);
                Log.d("BidRequestsTab","Location:"+bid.getString("location")+" Order:"+ bid.getJSONArray("orders").length()+" items ordered");
                BidCard card = new BidCard(getActivity(),bid);
                card.setOnClickListener(this);
                cards.add(card);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cards;
    }


    @Override
    public void onClick(Card card, View view) {
        BidCard bCard = (BidCard) card;
//        Fragment aBidFrag = new AucBidsFragment();
//
//        FragmentTransaction ft= getActivity().getSupportFragmentManager().beginTransaction();
////        Fragment oldBidFrag = getFragmentManager().findFragmentByTag("BidFrag");
//
//        ft.replace(R.id.auction_frame,aBidFrag,"BidFrag");
//        ft.commit();

        Log.d("Requests", "Fragment Added");
        bListener.openBidRequest(bCard.getBid(), true);


    }

}
