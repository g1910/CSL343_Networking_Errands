package group2.netapp.bidding;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
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
import group2.netapp.bidding.cards.AuctionPlacedBidCard;
import group2.netapp.bidding.cards.GeneralBidCard;
import group2.netapp.bidding.cards.NotParticipatingCard;
import group2.netapp.bidding.cards.Order_Card;
import group2.netapp.bidding.cards.ParticipatingCard;
import group2.netapp.utilFragments.ServerConnect;
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
    int auctionId,bidId;
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
                auctionId=auction_details.getInt("idAuction");
                bid_details=(JSONObject)((CurrAuctionActivity) getActivity()).getBids().get(bidIndex);
                bidId=bid_details.getInt("idBid");
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

        NotParticipatingCard card=null;
        card=new NotParticipatingCard(getActivity(),auction_details,-1);
        cards.add(card);

        GeneralBidCard card_run=null;
        card_run=new GeneralBidCard(getActivity(),bid_details,-1);
        cards.add(card_run);

        JSONArray placed= null;
        Log.d("HmMmm","HMMMM");
        try {
            placed = (JSONArray)bid_details.get("placed");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray partiAuctions=((CurrAuctionActivity)getActivity()).getParticipating();

        for (int i=0;i<placed.length();i++)
        {
            AuctionPlacedBidCard aucCard=null;
            try {
                String loc=null;
                String ranks=null;
                String prices=null;
                String status=null;

                Log.d("LOL",partiAuctions.length()+" ");
                String tempId=((JSONObject)(placed.get(i))).getString("idAuction");

                for (int j=0;j<partiAuctions.length();j++)
                {
                    JSONObject tm=((JSONObject)partiAuctions.get(j));
                    Log.e("Check HERE",tm.getString("idAuction").toString());
                    Log.d("CHECK LOL",tempId);
                    if (tm.getString("idAuction").toString().equals(tempId))
                    {
                        loc=tm.getString("location");
                        ranks=tm.getString("rank");
                        prices=tm.getString("Price");
                        status=tm.getString("status");
                        break;
                    }
                }




                aucCard=new AuctionPlacedBidCard(getActivity(),loc,status,ranks,prices,Integer.parseInt(((JSONObject) placed.get(i)).get("idAuction").toString()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cards.add(aucCard);

        }

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
        Log.d("Sucess",auctionId+" ");
        Log.d("Success",bidId+" ");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        final LayoutInflater inflater = LayoutInflater.from(getActivity().getApplicationContext());

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setTitle("Place Bid")
                        // Add action buttons
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        Log.e("Dialog", "HI");
                        Dialog f = (Dialog) dialog;
                        Log.d("Auction ID", auctionId + " ");
                        Log.d("BID ID", bidId + " ");

                        Log.d("Here", "Setting new Price");
                        ServerConnect myServer = new ServerConnect(getActivity());

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("idAuction",String.valueOf(auctionId)));
                        nameValuePairs.add(new BasicNameValuePair("idBid",String.valueOf(bidId)));

                        Log.d("ParticipatingFragment",getString(R.string.IP) + "placeBid.php");

                        myServer.execute(getString(R.string.IP) + "placeBid.php", nameValuePairs);

                        Log.d("ParticipatingFragment", getString(R.string.IP) + "getAllAuctions.php");
                        Log.e("ParticipatingFragment", "Hi");

                        Log.d("ParticipatingFragment", "ProgressAuctionOpened");


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
}
