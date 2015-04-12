package group2.netapp.bidding.currAuctionTabs;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import group2.netapp.R;
import group2.netapp.bidding.BidsActivity;
import it.gmariotti.cardslib.library.cards.material.MaterialLargeImageCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ToParticipateFragment extends Fragment {

    CardArrayRecyclerViewAdapter auctionViewAdapter;
    CardRecyclerView auctionView;

    public ToParticipateFragment() {
        // Required empty public constructor
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

        for(int i = 0; i < 10;++i) {
      //      Card card = new Card(getActivity());

            auctionCard card = new auctionCard(getActivity());
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

}
