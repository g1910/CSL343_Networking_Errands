package group2.netapp;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class FeedbackActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        Bundle b = getIntent().getExtras();
        String id = b.getString("id");
        int tag = b.getInt("tag");


        if(tag==0)
        {
            ServiceFeedbackFragment fragment = new ServiceFeedbackFragment();
            Bundle bun = new Bundle();
            bun.putString("id",id);
            fragment.setArguments(bun);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.feedbacksContainer, fragment)
                    .commit();
        }
        else
        {
            CustomerFeedbackFragment fragment = new CustomerFeedbackFragment();
            Bundle bun = new Bundle();
            bun.putString("id",id);
            fragment.setArguments(bun);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.feedbacksContainer, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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
