package com.example.phase2.trader_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.phase2.R;
import com.example.phase2.browse_trades_activities.DeliverTradeActivity;
import com.example.phase2.meetings.MeetingManager;
import com.example.phase2.highabstract.ClickableList;
import com.example.phase2.highabstract.UpdatableBundleActivity;
import com.example.phase2.users.TraderManager;

import java.util.List;

public class DeliverItemActivity extends UpdatableBundleActivity implements ClickableList {

    private MeetingManager meetingManager;
    private TraderManager traderManager;
    private String currentTrader;
    private Integer trade;
    /**
     * Called when the activity is starting.
     * @param savedInstanceState If the activity is being re-initialized after previously being
     * shut down then this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deliver_trades);
        currentTrader = (String) getUseCase(USERNAMEKEY);
        updateUseCases();
    }
    /**
     * Updates the Manager classes in the bundle
     */
    protected void updateUseCases(){
        meetingManager = (MeetingManager) getUseCase(MEETINGKEY);
        traderManager = (TraderManager) getUseCase(TRADERKEY);
        viewList();
    }
    /**
     * Updates the ListView object in the XML file
     */
    public void viewList(){
        final List<Integer> onDeliverTrades = meetingManager.getOnDeliverMeetings();
        ListView listView = findViewById(R.id.tradesList1);
        ArrayAdapter<Integer> allTradesAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, onDeliverTrades);
        listView.setAdapter(allTradesAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                trade = onDeliverTrades.get(i);
                displayDeliveryTrade();
            }
        });
    }

    /**
     * Displays the editTrade dialog.
     */
    public void displayDeliveryTrade(){
        Intent intent = new Intent(this, DeliverTradeActivity.class);
        intent.putExtra("Trade", trade);
        putBundle(intent);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }

    /**
     * Returns to the previous activity.
     */
    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

}
