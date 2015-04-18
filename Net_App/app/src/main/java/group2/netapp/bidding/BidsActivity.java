package group2.netapp.bidding;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.recyclerview.internal.CardArrayRecyclerViewAdapter;
import it.gmariotti.cardslib.library.recyclerview.view.CardRecyclerView;

public class BidsActivity extends Activity {

    CardArrayRecyclerViewAdapter bidViewAdapter;
    CardRecyclerView bidView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bids);

        setUpBidView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bids, menu);
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

    public void setUpBidView(){
        bidView = (CardRecyclerView) findViewById(R.id.bidcard_recyclerview);
        bidView.setHasFixedSize(false);

        bidViewAdapter = new CardArrayRecyclerViewAdapter(this, setDummyBids());
        bidView.setLayoutManager(new LinearLayoutManager(this));

        if(bidView != null){
            bidView.setAdapter(bidViewAdapter);
        }
    }

    public ArrayList<Card> setDummyBids(){
        ArrayList<Card> cards = new ArrayList<Card>();

        for(int i = 0; i < 10;++i) {
            Card card = new Card(this);

            card.setTitle("It's a Card!"+i);

            card.setOnClickListener(new Card.OnCardClickListener() {
                @Override
                public void onClick(Card card, View view) {
                    
                }
            });

            cards.add(card);
        }

        return cards;
    }

}
