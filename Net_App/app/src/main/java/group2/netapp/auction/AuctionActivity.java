package group2.netapp.auction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.auction.bidsTabs.AcceptedBids;
import group2.netapp.auction.bidsTabs.PostAcceptedBids;
import group2.netapp.utilFragments.ProgressFragment;
import group2.netapp.utilFragments.ServerConnect;


public class AuctionActivity extends FragmentActivity implements BidRequestsFragment.BidRequestsListener, ServerConnect.OnResponseListener, AcceptedBids.BidAcceptListener, PostAcceptedBids.BidAcceptListener, AuctionDashboardFragment.AuctionDashboardListener, PostAuctionDashboardFragment.PostAuctionDashboardListener{

    JSONObject auctionDetails;
    JSONArray pendingBids, runningBids;
    String idUser;
    int isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        idUser = sp.getString("id", "");
        Log.d("AuctionActivity","id user:"+idUser);
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
        nameValuePairs.add(new BasicNameValuePair("id_user",idUser));
        Log.d("AuctionActivity",getString(R.string.IP)+"getAuction.php");
        myServer.execute(getString(R.string.IP)+"getAuction.php",nameValuePairs);

    }

    @Override
    public void onResponse(JSONArray j) {
        try {
            String tag = ((JSONObject)j.get(0)).getString("tag");
            if(tag.equals("loading")) {
                isRunning = j.getJSONObject(1).getInt("isRunning");
                if (isRunning == 0){
                    openServerForm();
                } else {
                    auctionDetails = j.getJSONObject(2);
                    pendingBids = j.getJSONArray(3);
                    runningBids = j.getJSONArray(4);
                    Log.d("AuctionActivity", auctionDetails.toString());
                    Log.d("AuctionActivity", pendingBids.toString());
                    Log.d("AuctionActivity", runningBids.toString());
                    if (isRunning == 1) {
                        openDashboard();
                    } else if (isRunning == 2) {
                        openPostAuction();
                    }
                }
            }else if(tag.equals("bid_request")){
                boolean status = ((JSONObject)j.get(1)).getBoolean("status");
                if(status){
                    Toast.makeText(this, "Bid Request Accepted!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Bid Request can't be Accepted!", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(getIntent());
            }else if(tag.equals("bid_reject")){
                boolean status = ((JSONObject)j.get(1)).getBoolean("status");
                if(status){
                    Toast.makeText(this, "Bid Request Rejected!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Bid Request can't be Rejected!", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(getIntent());
            }else if(tag.equals("bid_new_category")){
                boolean status = ((JSONObject)j.get(1)).getBoolean("status");
                if(status){
                    Toast.makeText(this, "Bid Request Accepted in new Category!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Bid Request can't be Accepted!", Toast.LENGTH_SHORT).show();
                }
                finish();
                startActivity(getIntent());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void openDashboard(){
        Bundle args = new Bundle();
        args.putString("auction", auctionDetails.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment aDashFrag = new AuctionDashboardFragment();
        aDashFrag.setArguments(args);
        ft.replace(R.id.auction_frame,aDashFrag,"AuctionDashboard");
//        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "DashboardOpened");
    }

    public void openPostAuction(){
        Bundle args = new Bundle();
        args.putString("auction", auctionDetails.toString());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment aPostDashFrag = new PostAuctionDashboardFragment();
        aPostDashFrag.setArguments(args);
        ft.replace(R.id.auction_frame,aPostDashFrag,"AuctionDashboard");
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
//        if (id == R.id.action_requests) {
//            openBidRequestFragment();
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openBidRequestFragment(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment bReqFrag = new BidRequestsFragment();
        ft.replace(R.id.auction_frame,bReqFrag,"BidRequests");
        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "BidRequests Opened");
    }

    @Override
    public void openBidRequest(JSONObject bid, boolean isRequest) {
        Bundle args = new Bundle();
        args.putString("bid", bid.toString());
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

    public int getIsRunning() {
        return isRunning;
    }

    public void setIsRunning(int isRunning) {
        this.isRunning = isRunning;
    }
}
