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
 * Created by mohit on 16/4/15.
 */
public class ParticipatingCard extends Card {

    String auctionLocation,price,desc,idUser,start_time,end_time,idAuction,expected_time;


    public ParticipatingCard(Context context, JSONObject j) {
        super(context,R.layout.participating_card);
        try {
            this.auctionLocation =j.getString("location");
            this.desc = j.getString("description");
            this.idUser = j.getString("idUser");
            this.start_time = j.getString("start_time");
            this.end_time = j.getString("end_time");
            this.idAuction = j.getString("idAuction");
            this.expected_time = j.getString("expctd_time");
            this.price = "0";
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        super.setupInnerViewElements(parent, view);

        TextView auctionLocView = (TextView) parent.findViewById(R.id.participatinglocation);
        TextView priceView = (TextView) parent.findViewById(R.id.participatingprice);
        TextView descView = (TextView)parent.findViewById(R.id.participatingdesc);

        auctionLocView.setText(auctionLocation);
        priceView.setText(price);
        descView.setText(desc);
    }

    public String getAuctionLocation() {
        return auctionLocation;
    }

    public void setAuctionLocation(String auctionLocation) {
        this.auctionLocation = auctionLocation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIdAuction() {
        return idAuction;
    }

    public void setIdAuction(String idAuction) {
        this.idAuction = idAuction;
    }

    public String getExpected_time() {
        return expected_time;
    }

    public void setExpected_time(String expected_time) {
        this.expected_time = expected_time;
    }
}
