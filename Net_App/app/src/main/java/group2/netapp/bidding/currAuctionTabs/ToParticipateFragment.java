package group2.netapp.bidding.currAuctionTabs;


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
import group2.netapp.bidding.CurrAuctionActivity;
import group2.netapp.bidding.cards.NotParticipatingCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ToParticipateFragment extends Fragment implements Card.OnCardClickListener{

    CardArrayRecyclerViewAdapter auctionViewAdapter;
    CardRecyclerView auctionView;
    BidInActivityListener blistener;

    public interface BidInActivityListener {
        public void openBidRequest(int index);
    }

    public ToParticipateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        blistener = (BidInActivityListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_to_participate, container, false);

        setUpAuctionView(v);
        return v;
    }

    private void setUpAuctionView(View v) {
        auctionView = (CardRecyclerView) v.findViewById(R.id.auctioncard_recyclerview);
        auctionView.setHasFixedSize(false);

        auctionViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setDummyBids());
        auctionView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if(auctionView != null){
            auctionView.setAdapter(auctionViewAdapter);
        }
    }

    public ArrayList<Card> setDummyBids(){
        ArrayList<Card> cards = new ArrayList<Card>();

        JSONArray notParticipating = ((CurrAuctionActivity)getActivity()).getNotParticipating();

        for(int i = 0; i <  notParticipating.length() ;++i) {
      //      Card card = new Card(getActivity());

            NotParticipatingCard card = null;
            try {
                card = new NotParticipatingCard(getActivity(),(JSONObject)notParticipating.get(i),i);
                card.setOnClickListener(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            card.setTitle("It's a Card!"+i);

  //          card.setOnClickListener(new Card.OnCardClickListener() {
    //            @Override
      //          public void onClick(Card card, View view) {
        //            Toast.makeText(getActivity().getApplicationContext(), card.getTitle(),
          //                  Toast.LENGTH_LONG).show();
            //    }
          //  });

            cards.add(card);


/*            MaterialLargeImageCard card = new MaterialLargeImageCard(getActivity());

            card.setTextOverImage("Hello World " + i);
            card.setDrawableIdCardThumbnail(R.drawable.sea);


            card.setTitle("LOLs");
            card.setSubTitle("How u doin");

            card.setOnClickListener(new Card.OnCardClickListener(){

                                        @Override
                                        public void onClick(Card card, View view) {
                                            Toast.makeText(getActivity().getApplicationContext(), card.getTitle(),
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
            );

            card.build();


            cards.add(card);*/




        }

        return cards;
    }

    @Override
    public void onClick(Card card, View view) {
        NotParticipatingCard c=(NotParticipatingCard)card;
        Log.d("Requests", "COPEY Added");
        blistener.openBidRequest(c.getIndex());

    }


}
