package group2.netapp.bidding;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardListView;

public class BidFormActivity extends Activity {

    CardListView listView;
    ArrayList<Card> cards;
    CardArrayAdapter cardListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_form);
        listView = (CardListView)findViewById(R.id.BidFormCardList);
        cards = new ArrayList<Card>();
        cardListAdapter = new CardArrayAdapter(getApplicationContext(),cards);

        if (listView != null) {
            listView.setAdapter(cardListAdapter);
        }

        //Adding one order
        OrderCard a = new OrderCard(getApplicationContext());
        cards.add(a);
        cardListAdapter.notifyDataSetChanged();

        Button AddItem = new Button(getApplicationContext());
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
                OrderCard a = new OrderCard(getApplicationContext());
                cards.add(a);
                int count = cardListAdapter.getCount();
                params.height = itemMeasuredHeight*count + dividerHeight*(count-1);
                listView.setLayoutParams(params);
                listView.requestLayout();
                cardListAdapter.notifyDataSetChanged();
                listView.requestFocus();
            }
        });

        Button placeBid = (Button)findViewById(R.id.PlaceBid_button);
        placeBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText locationView=(EditText) findViewById(R.id.locationfield);
                EditText desView = (EditText) findViewById(R.id.descriptionfield);

                String bidlocation = locationView.getText().toString();
                String des = desView.getText().toString();

                Log.d("BidFormActivity",bidlocation);
                Log.d("BidFormActivity",des);

                JSONArray order=new JSONArray();

                for (int i=0;i<cards.size();i++)
                {
                    JSONObject temp=new JSONObject();
                    OrderCard c=(OrderCard)cards.get(i);
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
                
                Log.d("BidFormActivity",order.toString());

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bid_form, menu);
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