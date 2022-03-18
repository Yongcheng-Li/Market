package com.example.phase2.trader_activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.phase2.R;
import com.example.phase2.highabstract.BundleActivity;
import com.example.phase2.users.TraderManager;

import gestures.OnSwipeTouchListener;

/**
 * This class is responsible for changing trader's password.
 */
public class ChangeTraderPassword extends BundleActivity {
    private TraderManager tm;
    private String username;

    /**
     * Sets up the activity.
     * @param savedInstanceState A bundle that has all the necessary objects
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_trader_password);
        tm = (TraderManager) getUseCase(TRADERKEY);
        username = getUsername();

        View view = findViewById(android.R.id.content);
        view.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeRight() {
                onBackPressed();
            }
        });
    }

    /**
     * This method is called when the user presses the enter button. It updates the user's password
     * to the new password that user has entered.
     * @param view A view
     */
    public void submitTraderPassword(View view){
        EditText editText = findViewById(R.id.editTextTextPassword2);
        String newPassword = editText.getText().toString();

        if(newPassword.equals("")){
            Toast.makeText(this, R.string.invalid_password, Toast.LENGTH_SHORT).show();
        }else {
            tm.changePassword(username, newPassword);
            Toast.makeText(this, R.string.successfully_changed_password, Toast.LENGTH_SHORT).show();
        }
        editText.setText("");

    }

    /**
     * Called when the user presses the back button. Updates use case class.
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        replaceUseCase(tm);
    }
}