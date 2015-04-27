package group2.netapp.auction.cards;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import group2.netapp.R;
import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by mohit on 12/4/15.
 */
public class AuctionDetailCard extends Card {

    JSONObject aucDetails;

    public AuctionDetailCard(Context context, JSONObject aucDetails) {
        super(context, R.layout.auction_details_card);

        this.aucDetails = aucDetails;

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView auctionLocView = (TextView) parent.findViewById(R.id.notparticipatinglocation);
        TextView descView = (TextView)parent.findViewById(R.id.notparticipatingdesc);
        TextView end_timeView = (TextView)parent.findViewById(R.id.notparticipatingend_time);
        TextView expected_timeView = (TextView)parent.findViewById(R.id.notparticipatingexpected_time);
        TextView orderLimit = (TextView)parent.findViewById(R.id.participatingorderlimit);

        try {
            auctionLocView.setText(aucDetails.getString("location"));
            descView.setText(aucDetails.getString("description"));
            orderLimit.setText(aucDetails.getString("orderLimit"));

            end_timeView.setText(aucDetails.getString("end_time"));
            expected_timeView.setText(aucDetails.getString("expctd_time"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getAucDetails() {
        return aucDetails;
    }

    public void setAucDetails(JSONObject aucDetails) {
        this.aucDetails = aucDetails;
    }
}
