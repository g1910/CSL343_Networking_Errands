package group2.netapp.bidding.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by mohit on 26/4/15.
 */
public class Order_Card extends Card {

    String item;
    int price_per_item,quantity;
    int bidId;


    public Order_Card(Context context, JSONObject j, int i) {
        super(context, R.layout.order_card);

        try {
            this.price_per_item = j.getInt("price_per_item");
            this.quantity=j.getInt("quantity");
            this.item=j.getString("item");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //      this.bidOrder = bidOrder;
        //    this.bidId = bidId;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView itemView = (TextView) parent.findViewById(R.id.itemtext);
        TextView priceView = (TextView) parent.findViewById(R.id.ppiText);
        TextView quantityView = (TextView) parent.findViewById(R.id.orderquantity);
//        TextView bidOrderView = (TextView) parent.findViewById(R.id.auc_bid_order_summary);

        itemView.setText(item);
        priceView.setText(String.valueOf(price_per_item));
        quantityView.setText(String.valueOf(quantity));

        //      bidOrderView.setText(bidOrder);
    }

}
