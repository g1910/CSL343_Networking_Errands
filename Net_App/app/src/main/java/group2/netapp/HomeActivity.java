package group2.netapp;

import android.app.Activity;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import it.gmariotti.cardslib.library.internal.CardHeader;


public class HomeActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private String pname;
    private String picurl;
    private String email;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        pname = getIntent().getStringExtra("pname");
        email = getIntent().getStringExtra("email");
        picurl = getIntent().getStringExtra("picurl");
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = saved_values.edit();

        String id = saved_values.getString("id","");

        if(id.equals("")) {
            new adduser( "http://netapp.byethost33.com/add_user.php").execute(null, null, null);
        }
        editor.putString("user_name", pname);
        editor.putString("email", email);
        editor.putString("picurl", picurl);
        editor.apply();

        String picpath = saved_values.getString("picpath",null);
        if(picpath == null){
            picurl = getIntent().getStringExtra("picurl");
            picurl+="0";
            new LoadProfileImage().execute(picurl);
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {
            case 1:
                fragment = new ProfileFragment();
                break;
            case 4:
                fragment = new UserRequestFragment();
                break;
            case 2:
                fragment = new FeedbackFragment();
                break;
            case 3:
                fragment = new RateFragment();
                break;
            case 0:
                fragment = new HomeFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, fragment, "home")
                        .commit();
                break;
            case 5:
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = saved_values.edit();
                editor.clear();
                editor.apply();
                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                logout.putExtra("loggedout", 1);
                startActivity(logout);
                finish();
                break;

        }
        if(position!=5 && position!=0)
        fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.slide_out)
                .replace(R.id.container, fragment,"other")
                .commit();


    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_activity_home);
                break;
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;


        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment;
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragment = fragmentManager.findFragmentByTag("other");
        if(fragment != null && fragment.isVisible()){
            Fragment homefragment=new HomeFragment();
            fragmentManager.beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.slide_out)
                    .replace(R.id.container, homefragment,"home")
                   .commit();
        } else
            super.onBackPressed();
    }


    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {


        protected Bitmap doInBackground(String... urls) {

            String url = urls[0];
            Bitmap icon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                icon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return icon;
        }

        protected void onPostExecute(Bitmap result) {

            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/NetApp_Data");
            myDir.mkdirs();
            String fname = "ProfilePic.jpg";
            File file = new File (myDir, fname);

            if (file.exists ())
                file.delete ();
            try {
                FileOutputStream out = new FileOutputStream(file);
                result.compress(Bitmap.CompressFormat.JPEG, 100, out);
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = saved_values.edit();
                editor.putString("picpath", myDir.getAbsolutePath()+"/");
                editor.apply();

                out.flush();
                out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //System.out.println(item);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((HomeActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }


    class adduser extends AsyncTask<String, String, String> {
        private String  host;

        public adduser(String c) {
            host = c;
        }

        @Override
        protected String doInBackground(String[] params) {


            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            //HttpHost proxy = new HttpHost("10.1.201.4", 3128);
            //httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            try {


                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("pname", pname));
                nameValuePairs.add(new BasicNameValuePair("email", email));
                nameValuePairs.add(new BasicNameValuePair("picurl", picurl));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                if(response != null)
                {
                    InputStream is = response.getEntity().getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    try {
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    String id = sb.toString().trim();
                    if(id.length()>0)
                    {
                        System.out.println("User added or already present with id: "+id);
                        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor=saved_values.edit();
                        editor.putString("id",id);
                        Log.d("HomeActivity", id);
                        editor.apply();

                    }
                    else
                    {
                        System.out.println("Failed to add user");
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }


    }
}




