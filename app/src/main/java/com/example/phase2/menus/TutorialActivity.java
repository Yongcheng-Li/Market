package com.example.phase2.menus;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.tutorial_activities.TutorialBrowseItemsActivity;

import gestures.OnSwipeTouchListener;

/**
 * An activity class responsible for providing a tutorial for a trader the Trading System.
 */
public class TutorialActivity extends BundleActivity {

    //OnSwipeTouchListener onSwipeTouchListener;
    /**
     * Sets up the activity
     * @param savedInstanceState A bundle storing all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        View view = findViewById(android.R.id.content);
        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                TutorialActivity.super.onBackPressed();
            }
        });
    }

    /**
     * Called when the back button is pressed
     */
    @Override
    public void onBackPressed(){
        finish();
    }

    /**
     * Called when browse items button is pressed. It starts TutorialBrowseItemsActivity.
     * @param view A view
     */
    public void onBrowseItems(View view){
        Intent intent = new Intent(this, TutorialBrowseItemsActivity.class);
        putBundle(intent);
        startActivity(intent);
    }

    /**
     * Called when browse trades button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onBrowseTrades(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view or modify your trade  \n ")
                .append("- You could see all your trades' information  \n");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("BrowseTradePreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when edit inventory button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onEditInventory(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to post & edit your product \n ")
                .append("- You could view your items' information \n")
                .append("-You could remove items from your inventory \n")
                .append("-You could add items to your inventory \n")
                .append("-items need to be reviewed by an admin");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PostItemPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when edit wish list button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onEditWishList(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to edit your wishList   \n")
                .append("- You could view items in your wish list  \n")
                .append("-You could remove items from your wish list");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WishListPreview")
                .setMessage(stringBuilder);
        builder.show();

    }

    /**
     * Called when view my user info button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onViewInfo(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view your information \n")
                .append("- You could see your personal information here \n")
                .append("- You could check your recent trades  \n");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ViewInfoPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when request admin button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onRequestUnfreeze(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to view items to be delivered \n")
                .append("- You could view the products that agreed on trading and wait for delivery");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DeliveryPreview")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when change password button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onChangePassword(View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to change your password");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage(stringBuilder);
        builder.show();
    }

    /**
     * Called when change home city button is pressed. It uses a dialog to explain what a trader
     * could do using an account.
     * @param view A view
     */
    public void onChangeHomeCity (View view){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("You need an account to change your home city");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Change Home Location")
                .setMessage(stringBuilder);
        builder.show();
    }
}