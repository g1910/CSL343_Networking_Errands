package group2.netapp.auction.bidsTabs;


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
import org.json.JSONTokener;

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
public class AcceptedBids extends Fragment implements Card.OnCardClickListener{

    CardArrayRecyclerViewAdapter bidViewAdapter;
    CardRecyclerView bidView;
    BidAcceptListener bListener;

    JSONObject auc_category;
    JSONArray  acceptedBids;

    public interface BidAcceptListener{
        public void openBidRequest(JSONObject bid, boolean isRequest);
    }

    public AcceptedBids() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bListener = (BidAcceptListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accepted_bids, container, false);

        Bundle args = getArguments();
        try {
            auc_category = new JSONObject(new JSONTokener(args.getString("auction_category","")));
            setUpBidView(v);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return v;
    }

    public void setUpBidView(View v) throws JSONException {
        if(auc_category!=null){
            acceptedBids = auc_category.getJSONArray("bids");
        }
        bidView = (CardRecyclerView) v.findViewById(R.id.auc_acc_bids_recyclerview);
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
            for(int i = 0; i< acceptedBids.length(); ++i) {
                bid = acceptedBids.getJSONObject(i);
                Log.d("AcceptedTab", "Location:" + bid.getString("location") + " Order:" + bid.getJSONArray("orders").length() + " items ordered");
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
        bListener.openBidRequest(bCard.getBid(), false);


    }


}
