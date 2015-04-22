package group2.netapp.bidding.cards;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
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
    String ratings,numRated;
    int index;


    public ParticipatingCard(Context context, JSONObject j,int i) {
        super(context, R.layout.participating_card);
        try {
            this.index=i;
            this.auctionLocation =j.getString("location");
            this.desc = j.getString("description");
            this.idUser = j.getString("idUser");
            this.start_time = j.getString("start_time");
            this.end_time = j.getString("end_time");
            this.idAuction = j.getString("idAuction");
            this.expected_time = j.getString("expctd_time");
            this.price = j.getString("Price");
            this.ratings=j.getString("rating");
            this.numRated=j.getString("numRated");
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
        RatingBar ratingsView = (RatingBar)parent.findViewById(R.id.participatingratings);
        TextView numRatedView = (TextView)parent.findViewById(R.id.participatingnumRated);
        TextView end_timeView = (TextView)parent.findViewById(R.id.participatingend_time);
        TextView expected_timeView = (TextView)parent.findViewById(R.id.participatingexpected_time);

        auctionLocView.setText(auctionLocation);
        priceView.setText("₹" + price);
        descView.setText(desc);
        ratingsView.setRating(Float.parseFloat(ratings));
        numRatedView.setText("rated by : "+numRated+" users");
        end_timeView.setText("Bidding Ends in : "+end_time);
        expected_timeView.setText("Expected Delivery : "+expected_time);

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

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getNumRated() {
        return numRated;
    }

    public void setNumRated(String numRated) {
        this.numRated = numRated;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
