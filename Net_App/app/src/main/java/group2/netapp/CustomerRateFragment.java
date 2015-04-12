package group2.netapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardListView;
//import it.gmariotti.cardslib.library.view.CardExpandableListView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRequestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerRateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static int counter = 1;
    private CardListView listView;
    ArrayList<Card> cards;
    CardArrayAdapter cardListAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AsyncTask asynctask;
    private OnFragmentInteractionListener mListener;


    // TODO: Rename and change types and number of parameters
    public static CustomerRateFragment newInstance(String param1, String param2) {
        CustomerRateFragment fragment = new CustomerRateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CustomerRateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View infh = inflater.inflate(R.layout.fragment_service_rate, container, false);

        cards = new ArrayList<Card>();
        cardListAdapter = new CardArrayAdapter(getActivity().getApplicationContext(),cards);

        listView = (CardListView) infh.findViewById(R.id.ServiceReqCardList);
        if (listView != null) {
            listView.setAdapter(cardListAdapter);
        }

        Button LoadMore = new Button(getActivity().getApplicationContext());
        LoadMore.setText("Load More");

// Adding button to listview at footer
        listView.addFooterView(LoadMore);



        LoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                String email = saved_values.getString("email",null);
                ArrayList<NameValuePair> list1 = new ArrayList<NameValuePair>();
                list1.add(new BasicNameValuePair("tag", "2"));          //tag = 2 for customer rates
                list1.add(new BasicNameValuePair("counter", String.valueOf(counter)));
                list1.add(new BasicNameValuePair("email",email));
                if(asynctask.getStatus() == AsyncTask.Status.PENDING || asynctask.getStatus() == AsyncTask.Status.RUNNING)
                    asynctask.cancel(true);
                asynctask = new get_requests(list1, "http://netapp.byethost33.com/service_rate.php").execute(null, null, null);
            }
        });

        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String email = saved_values.getString("email",null);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("tag","2"));
        nameValuePairs.add(new BasicNameValuePair("counter", String.valueOf(counter)));
        nameValuePairs.add(new BasicNameValuePair("email",email));

        asynctask = new get_requests(nameValuePairs,"http://netapp.byethost33.com/service_rate.php").execute(null,null,null);
        return infh;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       /* try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
        ((HomeActivity) activity).onSectionAttached(3);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class get_requests extends AsyncTask<String,String,String>
    {
        public boolean running = true;
        private ArrayList<NameValuePair> list;
        private String host;
        HttpResponse response;
        private InputStream is;
        public get_requests(ArrayList<NameValuePair> l,String h)
        {
            list=l;
            host=h;
        }

        @Override
        protected void onCancelled() {
            running = false;
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");

            try {
                if(running)
                {
                    httppost.setEntity(new UrlEncodedFormEntity(list));

                    // Execute HTTP Post Request
                    response = httpclient.execute(httppost);
                    if (response != null) {
                        is = response.getEntity().getContent();
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        @Override
        protected void onPostExecute(String Result) {
            if(running)
            {Reader reader = new InputStreamReader(is);
                List<CustomerUser> posts = new ArrayList<CustomerUser>();
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject data = parser.parse(reader).getAsJsonObject();

                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Type listType = new TypeToken<List<CustomerUser>>() {
                    }.getType();
                    if (running)
                        posts = gson.fromJson(data.get("Ratings"), listType);          //EDIT THIS LINE
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cards.clear();
                if(posts==null){
                    Toast.makeText(getActivity().getApplicationContext(),"No pending reviews found",Toast.LENGTH_SHORT).show();;
                }
                if (posts != null && running)
                    for (int i = 0; i < posts.size(); ++i) {
                        CustomerUser a = posts.get(i);

                        CustomerUserReqCard card = new CustomerUserReqCard(getActivity().getApplicationContext(),a.location,a.emailId,a.timestamp);
                        CardHeader ch = new CardHeader(getActivity().getApplicationContext());
                        ch.setTitle(a.item);

                        card.addCardHeader(ch);

                        CustomerCustomCardExpand expand = new CustomerCustomCardExpand(getActivity().getApplicationContext());
                        card.addCardExpand(expand);

                        ch.setButtonExpandVisible(true);

                        ViewToClickToExpand viewToClickToExpand = ViewToClickToExpand.builder()
                                .highlightView(true)
                                .setupCardElement(ViewToClickToExpand.CardElementUI.CARD);
                        card.setViewToClickToExpand(viewToClickToExpand);

                        cards.add(card);
                    }
                cardListAdapter.notifyDataSetChanged();

                try {
                    reader.close();
                    is.close();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
    }

}

class CustomerCustomCardExpand extends CardExpand {
    //Use your resource ID for your inner layout
    public CustomerCustomCardExpand(Context context)
    {
        super(context, R.layout.service_rate_expand);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, final View view) {

        if (view == null) return;

        RatingBar rating = (RatingBar) view.findViewById(R.id.serviceratingBar);
        final EditText review = (EditText) view.findViewById(R.id.servicereviewedit);
        Button submit = (Button)view.findViewById(R.id.servicereviewsubmit);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.requestFocus();
            }
        });
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                review.requestFocus();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ADD REVIEW SUBMIT BUTTON FUNCTIONALITY HERE
                Toast.makeText(getContext(),review.getText().toString() + "Review submitted",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

class CustomerUserReqCard extends Card {

    private String item, location, timestamp, emailId;

    public CustomerUserReqCard(Context context, String l, String t, String e){
        super(context, R.layout.service_rate_card);
        location = l;
        emailId = e;
        timestamp = t;

    }
    public CustomerUserReqCard(Context context){
        super(context, R.layout.service_rate_card);
    }



    @Override
    public void setupInnerViewElements(ViewGroup parent, View view){

        //Retrieve TextView elements
        TextView tx2 = (TextView) view.findViewById(R.id.serviceemail);
        TextView tx3 = (TextView) view.findViewById(R.id.servicelocation);
        TextView tx4 = (TextView) view.findViewById(R.id.servicetimestamp);
        //Set value in text views
        tx2.setText(emailId);
        tx3.setText(location);
        tx4.setText(timestamp);
    }
}

class CustomerUser{

    public String item;
    public String timestamp;
    public String location;
    public String emailId;
}