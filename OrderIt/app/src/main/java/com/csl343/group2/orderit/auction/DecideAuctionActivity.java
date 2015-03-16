package com.csl343.group2.orderit.auction;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.csl343.group2.orderit.R;
import com.csl343.group2.orderit.utilFragments.ServerConnect;

import org.apache.http.HttpResponse;

import java.io.IOException;

public class DecideAuctionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decide_auction);
        ProgressBar bar=(ProgressBar) findViewById(R.id.decideBar);
        ServerConnect myServer=new ServerConnect();
     //   myServer.execute("http://10.20.9.85/Networks/CSL343_Networking_Errands/Server/getAuction.php");
        Log.e("OrderIt","Hi");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_decide_auction, menu);
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
}