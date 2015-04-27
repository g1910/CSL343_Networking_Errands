package group2.netapp.auction.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by gaurav on 12/4/15.
 */
public class BidCard extends Card {

    JSONObject bid;
    public BidCard(Context context, JSONObject bid) {
        super(context, R.layout.running_bid_card);

      this.bid = bid;

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView bidLocationView = (TextView) parent.findViewById(R.id.auc_bid_location);
        TextView bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);

        try {
            bidLocationView.setText(bid.getString("location"));
            bidOrderView.setText(bid.getJSONArray("orders").length() + " Items Ordered\n"+bid.getString("bid_description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getBid() {
        return bid;
    }

    public void setBid(JSONObject bid) {
        this.bid = bid;
    }
}
