package group2.netapp.auction;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.LinearLayout;
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

import group2.netapp.R;
import group2.netapp.auction.cards.BidCard;
import group2.netapp.bidding.cards.Order_Card;
import group2.netapp.utilFragments.ServerConnect;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AucBidsFragment extends Fragment {

    TextView location,order;
    JSONObject bid;
    boolean isRequest;
    JSONArray aucCategories;
    RadioGroup rG;

    CardArrayRecyclerViewAdapter bidViewAdapter;
    CardRecyclerView bidView;

    public AucBidsFragment() {
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
        isRequest = b.getBoolean("isRequest",false);
        if(isRequest) setHasOptionsMenu(true);
        aucCategories = ((AuctionActivity)getActivity()).getRunningBids();
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
//        location = (TextView) v.findViewById(R.id.auc_bidview_loc);
//        order = (TextView) v.findViewById(R.id.auc_bidview_order);






//        try {
//            setUpCards();
//            bid = new JSONObject(new JSONTokener(b.getString("bid","")));
//            location.setText("BidId: "+bid.getString("location"));
//            order.setText("Order: " + bid.getJSONArray("orders").length()+" orders");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
     }

    public ArrayList setUpCards() throws JSONException{
        ArrayList<Card> cards = new ArrayList<>();

        BidCard card = new BidCard(getActivity(),bid);
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
        inflater.inflate(R.menu.menu_bid_requests,menu);
        Log.d("AucBidFrag", "Menu inflated");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.accept_bid: acceptBid();
                break;
            case R.id.reject_bid: rejectBid();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void rejectBid(){
        try {
            int auctionId = ((AuctionActivity)getActivity()).getAuctionDetails().getInt("idAuction");
            int bidId = bid.getInt("idBid");
            ServerConnect myServer=new ServerConnect(getActivity());
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("id_auc",auctionId+""));
            nameValuePairs.add(new BasicNameValuePair("id_bid",bidId+""));
            Log.d("AuctionActivity",getString(R.string.IP)+"reject_bid.php");
            myServer.execute(getString(R.string.IP)+"reject_bid.php",nameValuePairs);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void acceptBid(){

        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View vi = inflater.inflate(R.layout.dialog_auc_category,null);
        rG = (RadioGroup) vi.findViewById(R.id.auc_category_rg);
        populateRadioButtons();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Auction Category")
                .setView(vi)
                .setPositiveButton("Accept",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            int auctionId = ((AuctionActivity)getActivity()).getAuctionDetails().getInt("idAuction");
                            int bidId = bid.getInt("idBid");
                            int catId = aucCategories.getJSONObject(rG.getCheckedRadioButtonId()%100).getInt("idCategory");
                            int minPrice = aucCategories.getJSONObject(rG.getCheckedRadioButtonId()%100).getInt("minPrice");
                            ServerConnect myServer=new ServerConnect(getActivity());
                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                            nameValuePairs.add(new BasicNameValuePair("id_auc",auctionId+""));
                            nameValuePairs.add(new BasicNameValuePair("id_bid",bidId+""));
                            nameValuePairs.add(new BasicNameValuePair("id_cat",catId+""));
                            nameValuePairs.add(new BasicNameValuePair("min_price",catId+""));
                            Log.d("AuctionActivity",getString(R.string.IP)+"accept_bid.php");
                            myServer.execute(getString(R.string.IP)+"accept_bid.php",nameValuePairs);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        if(aucCategories.length()<2) {
            builder.setNeutralButton("New", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    View vN = inflater.inflate(R.layout.dialog_new_category, null);
                    final int max = findMaxBaseBid();
                    final EditText base = (EditText) vN.findViewById(R.id.auc_new_cat_base);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Add to New Category")
                            .setView(vN)
                            .setPositiveButton("Create and Accept", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String curr = base.getText().toString();
                                        if (curr.isEmpty()) {
                                            Toast.makeText(getActivity(), "No base bid specified...Nothing done!", Toast.LENGTH_SHORT).show();
                                        } else if (Integer.valueOf(curr) <= max) {
                                            Toast.makeText(getActivity(), "Base bid should be higher than previous base bids...Nothing done!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            int auctionId = ((AuctionActivity) getActivity()).getAuctionDetails().getInt("idAuction");
                                            int bidId = bid.getInt("idBid");
                                            ServerConnect myServer = new ServerConnect(getActivity());
                                            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                                            nameValuePairs.add(new BasicNameValuePair("id_auc", auctionId + ""));
                                            nameValuePairs.add(new BasicNameValuePair("id_bid", bidId + ""));
                                            nameValuePairs.add(new BasicNameValuePair("min_price", curr + ""));
                                            Log.d("AuctionActivity", getString(R.string.IP) + "create_accept_bid.php");
                                            myServer.execute(getString(R.string.IP) + "create_accept_bid.php", nameValuePairs);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            });
        }

        builder.show();
     }

    public void populateRadioButtons(){
        for(int i = 0;i<aucCategories.length();++i){
            RadioButton btn = new RadioButton(getActivity());
            btn.setId(100+i);
            try {
                btn.setText("Base Bid: \u20B9 "+aucCategories.getJSONObject(i).getInt("minPrice"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            rG.addView(btn);

        }
        ((RadioButton)rG.getChildAt(0)).setChecked(true);
    }

    public int findMaxBaseBid(){
        int max = 0, curr;
        for(int i = 0;i<aucCategories.length();++i){
            try {
                curr = aucCategories.getJSONObject(i).getInt("minPrice");
               if(max < curr){
                   max = curr;
               }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return max;
    }


}

