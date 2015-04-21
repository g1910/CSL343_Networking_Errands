package group2.netapp.bidding.currAuctionTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.bidding.CurrAuctionActivity;
import group2.netapp.bidding.cards.ParticipatingCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class CurrParticipatingFragment extends Fragment {

    CardArrayRecyclerViewAdapter auctionViewAdapter;
    CardRecyclerView auctionView;

    public CurrParticipatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_curr_participating, container, false);

        setUpAuctionView(v);
        return v;
    }

    private void setUpAuctionView(View v) {

        auctionView = (CardRecyclerView) v.findViewById(R.id.participating_recyclerview);
        auctionView.setHasFixedSize(false);

        auctionViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setDummyBids());
        auctionView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(auctionView != null){
            auctionView.setAdapter(auctionViewAdapter);
        }

    }

    private List<Card> setDummyBids() {

        ArrayList<Card> cards = new ArrayList<Card>();

        JSONArray Participating = ((CurrAuctionActivity)getActivity()).getParticipating();

        for(int i = 0; i <  Participating.length() ;++i) {
            //      Card card = new Card(getActivity());

            ParticipatingCard card = null;
            try {
                card = new ParticipatingCard(getActivity(),(JSONObject)Participating.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cards.add(card);

        }

        return cards;
    }


}
