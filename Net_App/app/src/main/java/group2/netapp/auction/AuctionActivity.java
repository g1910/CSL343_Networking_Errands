package group2.netapp.auction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import group2.netapp.auction.bidsTabs.BidRequestsTab;
import group2.netapp.utilFragments.ProgressFragment;
import group2.netapp.utilFragments.ServerConnect;


public class AuctionActivity extends FragmentActivity implements BidRequestsTab.BidRequestsListener, ServerConnect.OnResponseListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction);

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
        nameValuePairs.add(new BasicNameValuePair("id_user","1"));
        myServer.execute("http://10.20.9.85/Networks/CSL343_Networking_Errands/Server/getAuction.php",nameValuePairs);

    }

    @Override
    public void onResponse(JSONArray j) {
        try {
            boolean isRunning=Boolean.valueOf (((JSONObject)j.get(0)).get("isRunning").toString());
            if (isRunning)
            {
                openDashboard();
            }
//            else {
//                Intent i = new Intent(this, ServerFormActivity.class);
//                startActivity(i);
//            }

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
    public void openBidRequest(String location, String order) {
        Bundle args = new Bundle();
        args.putString("location",location);
        args.putString("order",order);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment aucBids = new AucBidsFragment();
        aucBids.setArguments(args);
        ft.replace(R.id.auction_frame, aucBids, "AuctionBids");
        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "AucBids");
    }


}
