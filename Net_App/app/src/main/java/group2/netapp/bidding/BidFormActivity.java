package group2.netapp.bidding;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

    private int quantity;
    private String item_name;

    public OrderCard(Context context){
        super(context, R.layout.bid_card);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view){

        EditText item = (EditText)parent.findViewById(R.id.itemName);

        item.addTextChangedListener(new TextWatcher() {
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
        });

    }
}