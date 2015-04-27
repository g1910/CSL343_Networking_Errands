package group2.netapp.auction;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import group2.netapp.R;
import group2.netapp.auction.cards.AuctionDetailCard;
import group2.netapp.auction.cards.BidCard;
import group2.netapp.auction.cards.ConfirmBidCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ConfirmBidsFragment extends Fragment implements Card.OnCardClickListener {



    CardArrayRecyclerViewAdapter bidViewAdapter;
    CardRecyclerView bidView;
    ConfirmBidsListener bListener;

    JSONArray confirmedBids;

    private TextView timer;

    private JSONObject aucDetails;
    CardViewNative cardView;

    public interface ConfirmBidsListener{
        public void openConfirmedBid(JSONObject bid);
    }


    public ConfirmBidsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        bListener = (ConfirmBidsListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_confirm_bids, container, false);
        setUpBidView(v);

        return v;
    }

    public void setUpBidView(View v){
        Bundle args = getArguments();
        try {
            aucDetails = new JSONObject(new JSONTokener(args.getString("auction","")));
            cardView = (CardViewNative) v.findViewById(R.id.auc_details_cardview);
            Card aucCard =  new AuctionDetailCard(getActivity(),aucDetails);
            cardView.setCard(aucCard);

            setUpCountDown(v);

            confirmedBids = ((AuctionActivity) getActivity()).getConfirmedBids();
            bidView = (CardRecyclerView) v.findViewById(R.id.frag_auc_bid_recyclerview);
            bidView.setHasFixedSize(false);

            bidViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setBids());
            bidView.setLayoutManager(new LinearLayoutManager(getActivity()));

            if(bidView != null){
                bidView.setAdapter(bidViewAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Card> setBids(){
        ArrayList<Card> cards = new ArrayList<Card>();
        JSONObject bid;
        try {
            for(int i = 0; i< confirmedBids.length(); ++i) {
                bid = confirmedBids.getJSONObject(i);
                Log.d("BidRequestsTab","Location:"+bid.getString("location")+" Order:"+ bid.getJSONArray("orders").length()+" items ordered");
                ConfirmBidCard card = new ConfirmBidCard(getActivity(),bid);
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
        ConfirmBidCard bCard = (ConfirmBidCard) card;
        Log.d("Requests", "Fragment Added");
        bListener.openConfirmedBid(bCard.getBid());


    }


    public void setUpCountDown(View v) throws JSONException{
        timer = (TextView) v.findViewById(R.id.auc_countdown);
        Calendar c = Calendar.getInstance();
        Timestamp end = Timestamp.valueOf(aucDetails.getString("expctd_time"));
        final long millis = end.getTime() - c.getTimeInMillis();
        new CountDownTimer(millis, 1000) {

            public void onTick(long millisUntilFinished) {
                long sec =  millisUntilFinished/1000;
                long min =  sec/60;
                long hour = min/60;
                min = min%60;
                sec = sec%60;

                timer.setText(hour + " h "+min+" m "+sec+" s ");
            }

            public void onFinish() {
                timer.setText("Time's Up! Reload!!");
            }
        }.start();
    }


}
