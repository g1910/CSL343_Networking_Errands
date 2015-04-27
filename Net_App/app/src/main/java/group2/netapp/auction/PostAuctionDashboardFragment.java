package group2.netapp.auction;


import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.sql.Timestamp;
import java.util.Calendar;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.BidsTabAdapter;
import group2.netapp.auction.cards.AuctionDetailCard;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardViewNative;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 */
public class PostAuctionDashboardFragment extends Fragment{

    private ViewPager viewPager;
    private BidsTabAdapter mAdapter;
    private JSONObject aucDetails;

    private TextView timer;

    private PostAuctionDashboardListener listener;

    CardViewNative cardView;

    public PostAuctionDashboardFragment() {
        // Required empty public constructor
    }

    public interface PostAuctionDashboardListener{
        public void openBidRequestFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (PostAuctionDashboardListener) activity;
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
        View v = inflater.inflate(R.layout.fragment_post_auction_dashboard, container, false);
        Log.d("AuctionDashboard","settingTaba...");

        Bundle args = getArguments();
        try {
            aucDetails = new JSONObject(new JSONTokener(args.getString("auction","")));

            cardView = (CardViewNative) v.findViewById(R.id.auc_details_cardview);
            Card aucCard =  new AuctionDetailCard(getActivity(),aucDetails);
            cardView.setCard(aucCard);

            setUpCountDown(v);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setUpTabs(v);


        setHasOptionsMenu(false);

        return v;
    }

    public void setUpTabs(View v){
        viewPager = (ViewPager) v.findViewById(R.id.auc_pager);
        mAdapter = new BidsTabAdapter(getChildFragmentManager(),((AuctionActivity) getActivity()).getRunningBids());

        viewPager.setAdapter(mAdapter);

        if(viewPager==null || mAdapter==null){
            Log.d("AuctionDashboard","nullllllll");
        }

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
