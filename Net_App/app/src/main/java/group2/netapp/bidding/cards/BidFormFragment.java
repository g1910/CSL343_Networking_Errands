package group2.netapp.bidding.cards;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import group2.netapp.R;
import group2.netapp.bidding.CurrAuctionActivity;
import group2.netapp.utilFragments.ServerConnect;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BidFormFragment extends Fragment {

    CardListView listView;
    ArrayList<Card> cards;
    CardArrayAdapter cardListAdapter;
    int idAuction;


    public BidFormFragment() {
        // Required empty public constructor
    }


    String bidlocation;
    String des;
    JSONArray order;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v= inflater.inflate(R.layout.fragment_bid_form, container, false);
        Bundle b= getArguments();
        idAuction = b.getInt("idAuction");


        listView = (CardListView)v.findViewById(R.id.BidFormCardList);
        cards = new ArrayList<Card>();
        cardListAdapter = new CardArrayAdapter(getActivity().getApplicationContext(),cards);

        if (listView != null) {
            listView.setAdapter(cardListAdapter);
        }

        //Adding one order
        OrderCard a = new OrderCard(getActivity().getApplicationContext());
        cards.add(a);
        cardListAdapter.notifyDataSetChanged();

        Button AddItem = new Button(getActivity().getApplicationContext());
        AddItem.setText("Add item");
        listView.addFooterView(AddItem);

        View item = cardListAdapter.getView(0,null,listView);
        item.measure(0,0);
        final int itemMeasuredHeight = item.getMeasuredHeight();
        final int dividerHeight = listView.getDividerHeight();
        final ViewGroup.LayoutParams params = listView.getLayoutParams();
        listView.setSelection(listView.getCount()-1);

        AddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderCard a = new OrderCard(getActivity().getApplicationContext());
                cards.add(a);
                int count = cardListAdapter.getCount();
                params.height = itemMeasuredHeight*count + dividerHeight*(count-1);
                listView.setLayoutParams(params);
                listView.requestLayout();
                cardListAdapter.notifyDataSetChanged();
                listView.requestFocus();
                listView.setSelection(listView.getFooterViewsCount());
            }
        });

        Button placeBid = (Button)v.findViewById(R.id.PlaceBid_button);
        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {

                EditText locationView=(EditText) v.findViewById(R.id.locationfield);
                EditText desView = (EditText) v.findViewById(R.id.descriptionfield);

                bidlocation = locationView.getText().toString();
                des = desView.getText().toString();

                Log.d("BidFormActivity", idAuction + " ");
                Log.d("BidFormActivity",bidlocation);
                Log.d("BidFormActivity",des);

                order=new JSONArray();

                for (int i=0;i<cards.size();i++)
                {
                    JSONObject temp=new JSONObject();
                    OrderCard c=(OrderCard)cards.get(i);
                    if (!c.getItemView().getText().toString().equals(""))
                    {
                        try {
                            temp.put("item",c.getItemView().getText().toString()) ;
                            temp.put("price_per_item",c.getPriceView().getText().toString());
                            temp.put("quantity",c.getQuantityView().getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("BidFormActivity",temp.toString());

                        order.put(temp);

                    }


                }

                Log.d("BidFormActivity",order.toString());
                if(validate()) {
                    ServerConnect myServer = new ServerConnect(getActivity());

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                    final String id = saved_values.getString("id", null);
                    nameValuePairs.add(new BasicNameValuePair("id_user", id));
                    nameValuePairs.add(new BasicNameValuePair("idAuction", String.valueOf(idAuction)));
                    nameValuePairs.add(new BasicNameValuePair("location", String.valueOf(bidlocation)));
                    nameValuePairs.add(new BasicNameValuePair("desc", String.valueOf(des)));
                    nameValuePairs.add(new BasicNameValuePair("order", order.toString()));

                    Log.d("ParticipatingFragment", getString(R.string.IP) + "addBid.php");

                    myServer.execute(getString(R.string.IP) + "addBid.php", nameValuePairs);

                }
            }
        });
        return v;
    }

    public boolean validate(){
        if(bidlocation.isEmpty()){
            Toast.makeText(getActivity(),"Location should not be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(order.length()==0){
            Toast.makeText(getActivity(),"There should be atleast 1 order",Toast.LENGTH_SHORT).show();
            return false;
        }
        try {
            JSONObject j;
            for(int i=0;i<order.length();++i){
                j = order.getJSONObject(i);
                if(j.getString("item").isEmpty() || j.getString("price_per_item").isEmpty() || j.getString("quantity").isEmpty()){
                    Toast.makeText(getActivity(),"None of the order fields should be empty",Toast.LENGTH_SHORT).show();
                    return false;
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }


}

class OrderCard extends Card{

    private int quantity,price;
    private String item_name;

    private EditText itemView,priceView,quantityView;

    public OrderCard(Context context){
        super(context, R.layout.bid_card);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view){

        itemView = (EditText)parent.findViewById(R.id.itemName);
        priceView = (EditText) parent.findViewById(R.id.Priceperitem);
        quantityView = (EditText) parent.findViewById(R.id.numberPicker);

/*        item.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                item_name = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        priceView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                price = Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

       NumberPicker np = (NumberPicker)parent.findViewById(R.id.numberPicker);

        String[] nums = new String[20];
        for(int i=0; i<nums.length; i++)
            nums[i] = Integer.toString(i+1);

        np.setMinValue(1);
        np.setMaxValue(20);
        np.setWrapSelectorWheel(false);
        np.setDisplayedValues(nums);
        np.setValue(1);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                quantity = newVal;
            }
        });*/

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public EditText getItemView() {
        return itemView;
    }

    public void setItemView(EditText itemView) {
        this.itemView = itemView;
    }

    public EditText getPriceView() {
        return priceView;
    }

    public void setPriceView(EditText priceView) {
        this.priceView = priceView;
    }

    public EditText getQuantityView() {
        return quantityView;
    }

    public void setQuantityView(EditText quantityView) {
        this.quantityView = quantityView;
    }
}