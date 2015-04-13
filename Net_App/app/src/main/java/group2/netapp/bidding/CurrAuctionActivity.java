package group2.netapp.bidding;

import android.app.ActionBar;
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
import group2.netapp.auction.AuctionDashboardFragment;
import group2.netapp.utilFragments.ProgressFragment;
import group2.netapp.utilFragments.ServerConnect;

public class CurrAuctionActivity extends FragmentActivity implements ServerConnect.OnResponseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_auction);

        loadData();

    }

    private void loadData() {

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment aDashFrag = new ProgressFragment();

        ft.add(R.id.curr_auction_frame,aDashFrag,"ProgressAuction");
        ft.addToBackStack(null);
        ft.commit();

        ServerConnect myServer=new ServerConnect(this);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        myServer.execute("http://10.20.9.85/Networks/CSL343_Networking_Errands/Server/getAllAuctions.php",nameValuePairs);

        Log.e("AuctionActivity","Hi");

        Log.d("AuctionActivity", "ProgressAuctionOpened");

    }

    private void showCurrAuctions() {



        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Fragment aDashFrag = new CurrAuctionFragment();

        ft.replace(R.id.curr_auction_frame, aDashFrag, "CurrentAuction");
        ft.addToBackStack(null);
        ft.commit();
        Log.d("AuctionActivity", "CurrAuctionOpened");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_curr_auction, menu);
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
    public void onResponse(JSONArray j) {
        try {
            Log.e("AuctionActivity",(j.length()+" hi "  ));
            Log.e("AuctionActivity",((JSONObject)j.get(0)).toString());
            showCurrAuctions();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
