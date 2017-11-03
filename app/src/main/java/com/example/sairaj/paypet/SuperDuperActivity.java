package com.example.sairaj.paypet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuperDuperActivity extends AppCompatActivity {

    public static String DATA_FROM_MAIN_ACT = "data_from_main_acty";
    public static String MyPREFERENCES = "Mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_duper);

        //For Animation

        ImageView image = findViewById(R.id.animationStatus);
        ViewAnimator
                .animate(image)
                .thenAnimate(image)
                .scale(0.1f, 1f, 0.1f)
                .accelerate()
                .duration(2000)
                .repeatCount(1000)
                .start();
        //End

        getActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.yy));
        actionBar.setTitle("");

        Intent receievedIntentFromMain = getIntent();
        if (receievedIntentFromMain != null) {
            ArrayList<String> arrayList = receievedIntentFromMain.getStringArrayListExtra(DATA_FROM_MAIN_ACT);
            String amount = arrayList.get(MainActivity.AMT_INDEK);
            String description = arrayList.get(MainActivity.DES_INDEX);
            String mobileNumber = arrayList.get(MainActivity.NUM_INDEX);
            String nameOfPerson = arrayList.get(MainActivity.NAME_INDEX);
            ((TextView) findViewById(R.id.rsShowerId)).append(amount);
            Date dNow = new Date();
            String pattern = "hh:mm a ',' dd MMM yyyy";
            SimpleDateFormat ft =
                    new SimpleDateFormat(pattern);
            String curr_date_and_time = ft.format(dNow);

            ((TextView) findViewById(R.id.nameOfPerson)).setText(nameOfPerson);
            ((TextView) findViewById(R.id.mobileNumber)).setText(mobileNumber);
            ((TextView) findViewById(R.id.time_and_date)).setText(curr_date_and_time);
            if (!TextUtils.isEmpty(description))
                ((TextView) findViewById(R.id.description)).setText("\" " + description + " \"");
            long number = (long) Math.floor(Math.random() * 9_000_000_0L) + 1_000_000_0L;
            String txn_id = "Wallet Txn ID:150" + number;
            ((TextView) findViewById(R.id.wallet_txn_id)).setText(txn_id);

            modifyLootMoney(mobileNumber, Float.valueOf(amount));

            doSharedPref(mobileNumber, nameOfPerson);

        }


    }

    private void doSharedPref(String number, String nameOfPerson) {
        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(number, nameOfPerson);
        editor.commit();
    }

    private void modifyLootMoney(String number, final float cost) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRefToLoot = firebaseDatabase.getReference()
                .child(ConstantsTree.LOOT);
        final DatabaseReference dbRefToParLoot = dbRefToLoot.child(number);
        dbRefToParLoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Float oldValue = dataSnapshot.getValue(Float.class);
                if (oldValue == null)
                    oldValue = 0f;
                dbRefToParLoot.setValue(oldValue + cost);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
