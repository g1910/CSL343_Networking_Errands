package group2.netapp.auction;


import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.ProfileActivity;
import group2.netapp.R;
import group2.netapp.auction.cards.BidCard;
import group2.netapp.auction.cards.ConfirmBidCard;
import group2.netapp.bidding.cards.Order_Card;
import group2.netapp.utilFragments.ServerConnect;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class ConfirmBidsDisplayFragment extends Fragment {

    JSONObject bid;
    JSONArray aucCategories;

    CardArrayRecyclerViewAdapter bidViewAdapter;
    CardRecyclerView bidView;

    public ConfirmBidsDisplayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_auc_bids, container, false);
        setUpView(v);
        return v;
    }

    public void setUpView(View v){
        Bundle b = getArguments();
        setHasOptionsMenu(true);
        try {
            bid = new JSONObject(new JSONTokener(b.getString("bid","")));
            bidView = (CardRecyclerView) v.findViewById(R.id.frag_auc_bid_recyclerview);
            bidView.setHasFixedSize(false);

            bidViewAdapter = new CardArrayRecyclerViewAdapter(getActivity(), setUpCards());
            bidView.setLayoutManager(new LinearLayoutManager(getActivity()));

            if(bidView != null){
                bidView.setAdapter(bidViewAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
     }

    public ArrayList setUpCards() throws JSONException{
        ArrayList<Card> cards = new ArrayList<>();

        ConfirmBidCard card = new ConfirmBidCard(getActivity(),bid);
        cards.add(card);
        JSONArray orders = bid.getJSONArray("orders");
        Order_Card orderCard;
        for (int i=0;i<orders.length();i++)
        {
            try {
                Log.d("OrderCard",orders.get(i).toString());
                orderCard= new Order_Card(getActivity(),orders.getJSONObject(i),i);
                cards.add(orderCard);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return cards;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_confirm_bid,menu);
        Log.d("AucBidFrag", "Menu inflated");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.view_profile: viewProfile();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewProfile(){
        Bundle args = new Bundle();
        try {
            args.putString("id", bid.getString("idUser"));
            Intent i = new Intent(getActivity(), ProfileActivity.class);
            i.putExtras(args);
            startActivity(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}

