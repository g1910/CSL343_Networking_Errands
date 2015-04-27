package group2.netapp.auction;


import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.BidsTabAdapter;
import group2.netapp.auction.cards.AuctionDetailCard;
import group2.netapp.bidding.cards.NotParticipatingCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class AuctionDashboardFragment extends Fragment{

    private ViewPager viewPager;
    private BidsTabAdapter mAdapter;
    private JSONObject aucDetails;

    int isRunning;

    private AuctionDashboardListener listener;

    CardViewNative cardView;

    public AuctionDashboardFragment() {
        // Required empty public constructor
    }

    public interface AuctionDashboardListener{
        public void openBidRequestFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (AuctionDashboardListener) activity;
        setRetainInstance(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashboard,menu);
        Log.d("AuctionDashboard", "Menu inflated");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_requests: listener.openBidRequestFragment();
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_auction_dashboard, container, false);
        isRunning = ((AuctionActivity)getActivity()).getIsRunning();
        Log.d("AuctionDashboard","settingTaba...");

        Bundle args = getArguments();
        try {
            aucDetails = new JSONObject(new JSONTokener(args.getString("auction","")));

            cardView = (CardViewNative) v.findViewById(R.id.auc_details_cardview);
            Card aucCard =  new AuctionDetailCard(getActivity(),aucDetails);
            cardView.setCard(aucCard);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setUpTabs(v);
        setHasOptionsMenu(true);

        return v;
    }

    public void setUpTabs(View v){
        viewPager = (ViewPager) v.findViewById(R.id.auc_pager);
        mAdapter = new BidsTabAdapter(getChildFragmentManager(),((AuctionActivity) getActivity()).getRunningBids(),isRunning);

        viewPager.setAdapter(mAdapter);

        if(viewPager==null || mAdapter==null){
            Log.d("AuctionDashboard","nullllllll");
        }

    }

}
