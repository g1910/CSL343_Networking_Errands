package group2.netapp.auction;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.AcceptedBids;
import group2.netapp.auction.bidsTabs.BidRequestsTab;
import group2.netapp.utilFragments.ProgressFragment;
import group2.netapp.utilFragments.ServerConnect;


public class AuctionActivity extends FragmentActivity implements BidRequestsTab.BidRequestsListener, ServerConnect.OnResponseListener, AcceptedBids.BidAcceptListener{

    JSONObject auctionDetails;
    JSONArray pendingBids, runningBids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_auction);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        loadData();
//        openDashboard();

    }

    public void loadData(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment progressFrag = new ProgressFragment();
        ft.add(R.id.auction_frame,progressFrag,"Progress");
        ft.commit();

        ServerConnect myServer=new ServerConnect(this);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("id_user","13"));
        Log.d("AuctionActivity",getString(R.string.IP)+"getAuction.php");
        myServer.execute(getString(R.string.IP)+"getAuction.php",nameValuePairs);

    }

    @Override
    public void onResponse(JSONArray j) {
        try {
            boolean isRunning=Boolean.valueOf (((JSONObject)j.get(0)).get("isRunning").toString());
            if (isRunning)
            {
                auctionDetails = j.getJSONObject(1);
                pendingBids = j.getJSONArray(2);
                runningBids = j.getJSONArray(3);
                Log.d("AuctionActivity",auctionDetails.toString());
                Log.d("AuctionActivity", pendingBids.toString());
                Log.d("AuctionActivity", runningBids.toString());
                openDashboard();
            }else {
                openServerForm();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openDashboard(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment aDashFrag = new AuctionDashboardFragment();
        ft.replace(R.id.auction_frame,aDashFrag,"AuctionDashboard");
//        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "DashboardOpened");
    }

    public void openServerForm(){
        Intent i = new Intent(this,ServerFormActivity.class);
        startActivity(i);
        finish();
        Log.d("AuctionActivity", "ServerForm");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auction, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openBidRequest(int bidId, boolean isRequest) {
        Bundle args = new Bundle();
        args.putInt("id", bidId);
        if(isRequest){
            args.putBoolean("isRequest",true);
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment aucBids = new AucBidsFragment();
        aucBids.setArguments(args);
        ft.replace(R.id.auction_frame, aucBids, "AuctionBids");
        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "AucBids");
    }

    public JSONObject getAuctionDetails() {
        return auctionDetails;
    }

    public void setAuctionDetails(JSONObject auctionDetails) {
        this.auctionDetails = auctionDetails;
    }

    public JSONArray getPendingBids() {
        return pendingBids;
    }

    public void setPendingBids(JSONArray pendingBids) {
        this.pendingBids = pendingBids;
    }

    public JSONArray getRunningBids() {
        return runningBids;
    }

    public void setRunningBids(JSONArray runningBids) {
        this.runningBids = runningBids;
    }

    public JSONObject getRunningBidAt(int i){
        try {
            return runningBids.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getPendingBidAt(int i){
        try {
            return pendingBids.getJSONObject(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}
