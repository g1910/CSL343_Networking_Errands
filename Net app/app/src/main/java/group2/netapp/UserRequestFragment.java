package group2.netapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import it.gmariotti.cardslib.library.view.*;
import it.gmariotti.cardslib.library.internal.*;
//import it.gmariotti.cardslib.library.view.CardExpandableListView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserRequestFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserRequestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRequestFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static int counter=1;
    private CardListView list;
    ArrayList<Card> cards;
    CardArrayAdapter cardListAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRequestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserRequestFragment newInstance(String param1, String param2) {
        UserRequestFragment fragment = new UserRequestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UserRequestFragment() {
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

        View infh = inflater.inflate(R.layout.fragment_user_request, container, false);
        list=(CardListView) infh.findViewById(R.id.ReqCardList);
        cards = new ArrayList<Card>();
        cardListAdapter = new CardArrayAdapter(getActivity().getApplicationContext(),cards);

        CardListView listView = (CardListView) infh.findViewById(R.id.ReqCardList);
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
                ArrayList<NameValuePair> list1 = new ArrayList<NameValuePair>();
                list1.add(new BasicNameValuePair("tag", "1"));
                list1.add(new BasicNameValuePair("query", String.valueOf(counter)));
                new get_requests(list1, "http://netapp.byethost33.com/get_broadcast.php").execute(null, null, null);
            }
        });

        EditText search = (EditText)infh.findViewById(R.id.SearchText);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {
                    ArrayList<NameValuePair> list1 = new ArrayList<NameValuePair>();
                    list1.add(new BasicNameValuePair("tag", "2"));
                    list1.add(new BasicNameValuePair("query", s.toString()));
                    new get_requests(list1, "http://netapp.byethost33.com/get_broadcast.php").execute(null, null, null);
                }
                else
                {
                    counter=1;
                    ArrayList<NameValuePair> list1 = new ArrayList<NameValuePair>();
                    list1.add(new BasicNameValuePair("tag", "1"));
                    list1.add(new BasicNameValuePair("query", String.valueOf(counter)));
                    new get_requests(list1, "http://netapp.byethost33.com/get_broadcast.php").execute(null, null, null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("tag","1"));
        nameValuePairs.add(new BasicNameValuePair("query", String.valueOf(1)));
        new get_requests(nameValuePairs,"http://netapp.byethost33.com/get_broadcast.php").execute(null,null,null);
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
        ((HomeActivity) activity).onSectionAttached(4);
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
        protected String doInBackground(String... params) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(host);
            httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            String text;
            try
            {

                httppost.setEntity(new UrlEncodedFormEntity(list));

                // Execute HTTP Post Request
                response = httpclient.execute(httppost);
                if(response != null)
                {
                    is = response.getEntity().getContent();
                }

            } catch (Exception e) {
                System.out.println(e);
                // TODO Auto-generated catch block
            }
            return null;
        }

        @Override
        protected void onPostExecute(String Result) {

            Reader reader = new InputStreamReader(is);
            List<UserRequests> posts = new ArrayList<UserRequests>();
            try {
                JsonParser parser = new JsonParser();
                JsonObject data = parser.parse(reader).getAsJsonObject();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();

                Type listType = new TypeToken<List<UserRequests>>() {}.getType();
                posts = gson.fromJson(data.get("UserRequests"), listType);
            } catch (Exception e) {
                e.printStackTrace();
            }

            cards.clear();

            for(int i=0;i<posts.size();++i)
            {
                UserRequests a=posts.get(i);

                Card card = new Card(getActivity().getApplicationContext());
                CardHeader ch = new CardHeader(getActivity().getApplicationContext());
                ch.setTitle(a.location);
                card.setTitle(a.item);
                CustomCardExpand expand = new CustomCardExpand(getActivity().getApplicationContext(),a.quantity,a.exprice,a.exptime,a.expdate);
                card.addCardExpand(expand);
                card.setViewToClickToExpand(ViewToClickToExpand.builder());
                card.setViewToClickToExpand(card.getViewToClickToExpand());

                card.setOnClickListener(new Card.OnCardClickListener() {
                    @Override
                    public void onClick(Card card, View view) {
                        card.doToogleExpand();
                    }
                });
                card.addCardHeader(ch);

                CardThumbnail thumb = new CardThumbnail(getActivity().getApplicationContext());

                thumb.setDrawableResource(R.drawable.location);
                card.addCardThumbnail(thumb);

                cards.add(card);
            }
            cardListAdapter.notifyDataSetChanged();

            try
            {
                reader.close();
                is.close();
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }

}

class CustomCardExpand extends CardExpand {

    //Use your resource ID for your inner layout
    private String quantity, cost, time, date;
    public CustomCardExpand(Context context,String q, String c, String t, String d) {
        super(context, R.layout.expand_layout);
        quantity=q;
        cost=c;
        time=t;
        date=d;
    }
    public CustomCardExpand(Context context)
    {
        super(context, R.layout.expand_layout);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {

        if (view == null) return;

        //Retrieve TextView elements
        TextView tx1 = (TextView) view.findViewById(R.id.quantity);
        TextView tx2 = (TextView) view.findViewById(R.id.cost);
        TextView tx3 = (TextView) view.findViewById(R.id.time);
        TextView tx4 = (TextView) view.findViewById(R.id.date);
        //Set value in text views
//if(tx1!=null)
        tx1.setText(quantity);
        tx2.setText(cost);
        tx3.setText(time);
        tx4.setText(date);

    }
}

