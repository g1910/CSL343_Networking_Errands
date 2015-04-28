package group2.netapp;


import android.app.Activity;
import android.app.ProgressDialog;
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
import it.gmariotti.cardslib.library.view.CardListView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServiceFeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerFeedbackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ProgressDialog progressdialoglistview;
    private static int counter = 1;
    private CardListView listView;
    ArrayList<Card> cards;
    CardArrayAdapter cardListAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AsyncTask asynctask;
    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CustomerFeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CustomerFeedbackFragment newInstance(String param1, String param2) {
        CustomerFeedbackFragment fragment = new CustomerFeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CustomerFeedbackFragment() {
        // Required empty public constructor
        this.setArguments(new Bundle());
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

        View infh = inflater.inflate(R.layout.fragment_feedback, container, false);

        cards = new ArrayList<Card>();
        cardListAdapter = new CardArrayAdapter(getActivity().getApplicationContext(), cards);

        listView = (CardListView) infh.findViewById(R.id.FeedbackCardList);
        if (listView != null) {
            listView.setAdapter(cardListAdapter);
        }
        progressdialoglistview = new ProgressDialog(getActivity());
        progressdialoglistview.setMessage("Loading");
        progressdialoglistview.setCanceledOnTouchOutside(true);
        progressdialoglistview.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressdialoglistview.setIndeterminate(true);
        Button LoadMore = new Button(getActivity().getApplicationContext());
        LoadMore.setText("Load More");

// Adding button to listview at footer
        listView.addFooterView(LoadMore);


        LoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                String id = getArguments().getString("id",null);
                ArrayList<NameValuePair> list1 = new ArrayList<NameValuePair>();
                list1.add(new BasicNameValuePair("tag", "2"));          //tag = 1 for customer rates
                list1.add(new BasicNameValuePair("counter", String.valueOf(counter)));
                list1.add(new BasicNameValuePair("id", id));
                if (asynctask.getStatus() == AsyncTask.Status.PENDING || asynctask.getStatus() == AsyncTask.Status.RUNNING)
                    asynctask.cancel(true);
                asynctask = new get_feedback(list1, "http://netapp.byethost33.com/get_feedback.php").execute(null, null, null);
            }
        });

        String id = getArguments().getString("id",null);
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("tag", "2"));
        nameValuePairs.add(new BasicNameValuePair("counter", String.valueOf(counter)));
        nameValuePairs.add(new BasicNameValuePair("id", id));

        asynctask = new get_feedback(nameValuePairs, "http://netapp.byethost33.com/get_feedback.php").execute(null, null, null);
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
//        int ishome = getArguments().getInt("ishome",-1);
//        if(ishome==1)
            ((HomeActivity) activity).onSectionAttached(2);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        if(asynctask.getStatus() == AsyncTask.Status.PENDING || asynctask.getStatus() == AsyncTask.Status.RUNNING)
            asynctask.cancel(true);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public class get_feedback extends AsyncTask<String,String,String>
    {
        public boolean running = true;
        private ArrayList<NameValuePair> list;
        private String host;
        HttpResponse response;
        private InputStream is;
        public get_feedback(ArrayList<NameValuePair> l,String h)
        {
            list=l;
            host=h;
        }

        @Override
        protected void onCancelled() {
            running = false;
        }

        @Override
        protected  void onPreExecute()
        {
            progressdialoglistview.show();
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
                List<FeedbackData> posts = new ArrayList<FeedbackData>();
                try {
                    JsonParser parser = new JsonParser();
                    JsonObject data = parser.parse(reader).getAsJsonObject();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Type listType = new TypeToken<List<FeedbackData>>() {
                    }.getType();
                    if (running)
                        posts = gson.fromJson(data.get("Feedbacks"), listType);          //EDIT THIS LINE
                } catch (Exception e) {
                    e.printStackTrace();
                }

                cards.clear();
                if(posts==null){
                    Toast.makeText(getActivity().getApplicationContext(), "No Customer Feedbacks found", Toast.LENGTH_SHORT).show();;
                }
                if (posts != null && running)
                    for (int i = 0; i < posts.size(); ++i) {
                        FeedbackData a = posts.get(i);
                        CustomerFeedbackCard card = new CustomerFeedbackCard(getActivity().getApplicationContext(),a.stars,a.description,a.name);
                        cards.add(card);
                    }
                cardListAdapter.notifyDataSetChanged();
                if(progressdialoglistview.isShowing())
                    progressdialoglistview.dismiss();
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

class CustomerFeedbackCard extends Card {
    int stars;
    private String name, description;

    public CustomerFeedbackCard(Context context, int star, String desc, String nam){
        super(context, R.layout.feedback_card);
        stars = star;
        description = desc;
        name = nam;
    }
    public CustomerFeedbackCard(Context context){
        super(context, R.layout.feedback_card);
    }



    @Override
    public void setupInnerViewElements(ViewGroup parent, View view){

        //Retrieve TextView elements
        RatingBar rating = (RatingBar) view.findViewById(R.id.FeedbackRating);
        TextView tx3 = (TextView) view.findViewById(R.id.FeedbackDescription);
        TextView tx4 = (TextView) view.findViewById(R.id.FeedbackName);
        //Set value in text view
        rating.setNumStars(5);
        rating.setRating(stars);
        // rating.setStepSize((float)0.5);
        tx3.setText(description);
        tx4.setText(name);
    }
}



