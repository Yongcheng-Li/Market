package com.example.phase2.browse_trades_activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.menus.TraderActivity;
import com.example.phase2.tutorial_activities.TutorialBrowseItemsActivity;

import java.util.Objects;

public class UnderDelivery extends BundleActivity {

    //private Integer trade;

    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_delivery);

        //trade = (Integer) Objects.requireNonNull(getIntent().getExtras()).getSerializable("Trade");
    }

    public void onDeliverFinishClicked(View view){
        Intent intent = new Intent(this, TraderActivity.class);
        startActivityForResult(intent, RESULT_FIRST_USER);
    }
}
