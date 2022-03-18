package com.example.phase2.browse_trades_activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.Dialogable;
import com.example.phase2.highabstract.UpdatableBundleActivity;
import com.example.phase2.items.ItemManager;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.trader_activities.DeliverItemActivity;
import com.example.phase2.trades.TradeManager;
import com.example.phase2.users.TraderManager;

import java.util.Objects;

import gestures.OnSwipeTouchListener;

public class DeliverTradeActivity extends UpdatableBundleActivity implements Dialogable {
    private TradeManager tradeManager;
    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    private ItemManager itemManager;

    private Dialog itemsDialog;

    /**
     * Updates the Manager classes in the bundle
     */
    protected void updateUseCases() {
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        updateScreen();
    }

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_trade_detail);

        trade = (Integer) Objects.requireNonNull(getIntent().getExtras()).getSerializable("Trade");
        tradeManager = (TradeManager) getUseCase(TRADEKEY);
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        currentTrader = (String) getUseCase(USERNAMEKEY);
        itemManager = (ItemManager) getUseCase(ITEMKEY);
        updateScreen();
        View view = findViewById(android.R.id.content);
        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                onBackPressed();
            }
        });
    }

    private void updateScreen(){
//        String tempTradeType = "Trade Type: " + tradeManager.getTradeType(trade);
//        TextView tradeType = findViewById(R.id.tradeType);
//        tradeType.setText(tempTradeType);
        String tempTradeDuration;
        if(tradeManager.isTradePermanent(trade)){
            tempTradeDuration = "Trade Duration: "+ "Permanent";
        }
        else{
            tempTradeDuration = "Trade Duration: "+ "1 Month";
        }
        TextView tradeDuration = findViewById(R.id.tradeDuration);
        tradeDuration.setText(tempTradeDuration);
        //todo
//        int item1 = tradeManager.getItems(tradeManager.getItems(trade).get());
        String temptradeWith = "Items in this trade: \n"
                               + itemManager.getItemNames(tradeManager.getItems(trade))
                                + tradeManager.getTradeInfo(trade);
        TextView tradeWith = findViewById(R.id.tradeWith);
        tradeWith.setText(temptradeWith);

        String tempDate = "Date: " + meetingManager.getMeetingDate(trade);
        TextView meetingDate = findViewById(R.id.meetingDate);
        meetingDate.setText(tempDate);
        String tempLocation = "Location: " + traderManager.getHomeCity(itemManager.getItemOwner(tradeManager.getItems(trade).get(0)))
                                + " ~ " + traderManager.getHomeCity(itemManager.getItemOwner(tradeManager.getItems(trade).get(1)));
        TextView meetingLocation = findViewById(R.id.meetingInformation);
        meetingLocation.setText(tempLocation);

    }


    public void onViewOtherTradesClicked(View view){
        super.onBackPressed();
    }

    public void onDeliverTradeClicked(View view){
        //meetingManager.removeMeeting(trade);
        meetingManager.setDeliverCompleted(trade);
        Toast.makeText(DeliverTradeActivity.this, "Trade is now under delivery", Toast.LENGTH_LONG).show();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("- You are assigned to delivered this trade  \n ")
                     .append("- Please drive safe:)  \n");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Trade In Delivery")
                .setMessage(stringBuilder);
        builder.show();
        //displayDelivery();
    }

    //Having issues currently unused
//    public void displayDelivery(){
//        Intent intent = new Intent(this, UnderDelivery.class);
//        //intent.putExtra("Trade", trade);
//        startActivityForResult(intent, RESULT_FIRST_USER);
//    }

    private void updateDialogs(){
        if(tradeManager.getTradeType(trade).contains("ONEWAY")){
            itemsDialog.setContentView(R.layout.activity_one_item);
            setUpOneItem();
        }
        else{
            itemsDialog.setContentView(R.layout.activity_two_item);
            setUpTwoItems();
        }
    }

    private void setUpOneItem(){
        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName = itemsDialog.findViewById(R.id.itemName);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = itemsDialog.findViewById(R.id.descriptionText);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Item Quality Rating: " + itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = itemsDialog.findViewById(R.id.itemRating);
        itemRating.setText(tempItemRating);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(0));
        TextView itemOwner = itemsDialog.findViewById(R.id.ownedBy);
        itemOwner.setText(tempOwner);
    }
    private void setUpTwoItems(){
        String tempItemName = itemManager.getItemName(tradeManager.getItems(trade).get(0));
        TextView itemName=itemsDialog.findViewById(R.id.itemName2);
        itemName.setText(tempItemName);

        String tempItemDescription = itemManager.getItemDescription(tradeManager.getItems(trade).get(0));
        TextView itemDescription = itemsDialog.findViewById(R.id.descriptionText2);
        itemDescription.setText(tempItemDescription);

        String tempItemRating = "Rating: " + itemManager.getItemQuality(tradeManager.getItems(trade).get(0));
        TextView itemRating = itemsDialog.findViewById(R.id.itemRating2);
        itemRating.setText(tempItemRating);

        String tempItemName2 = itemManager.getItemName(tradeManager.getItems(trade).get(1));
        TextView itemName2=itemsDialog.findViewById(R.id.secondItem);
        itemName2.setText(tempItemName2);

        String tempItemDescription2 = itemManager.getItemDescription(tradeManager.getItems(trade).get(1));
        TextView itemDescription2 = itemsDialog.findViewById(R.id.descriptionText3);
        itemDescription2.setText(tempItemDescription2);

        String tempItemRating2 = "Rating: "+itemManager.getItemQuality(tradeManager.getItems(trade).get(1));
        TextView itemRating2 = itemsDialog.findViewById(R.id.itemRating3);
        itemRating2.setText(tempItemRating2);

        String tempOwner = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(0));
        TextView itemOwner = itemsDialog.findViewById(R.id.ownedBy2);
        itemOwner.setText(tempOwner);

        String tempOwner2 = "Owned by: " + itemManager.getItemOwner(tradeManager.getItems(trade).get(1));
        TextView itemOwner2 = itemsDialog.findViewById(R.id.ownedBy3);
        itemOwner2.setText(tempOwner2);
    }

    /**
     * Called when the positive button is clicked.
     */
    @Override
    public void clickPositive() {
    }

    /**
     * Called when the negative button is clicked
     */
    @Override
    public void clickNegative() {
    }

    /**
     * Opens the itemsDialog.
     */
    @Override
    public void openDialog() {
        updateDialogs();
        itemsDialog.show();
    }
}
