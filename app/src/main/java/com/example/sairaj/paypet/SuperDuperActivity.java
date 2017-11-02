package com.example.sairaj.paypet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.viewanimator.ViewAnimator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SuperDuperActivity extends AppCompatActivity {

    public static String DATA_FROM_MAIN_ACT = "data_from_main_acty" ;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
       /* if(hasFocus){
            YoYo.with(Techniques.ZoomIn)
                    .duration(700)
                    .repeat(10)
                    .delay(52)
                    .playOn(findViewById(R.id.animationStatus));
        }*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_duper);

        //For Animation

        ImageView image = findViewById(R.id.animationStatus) ;
        ViewAnimator
                .animate(image)
                .duration(2000)
                .thenAnimate(image)
                .scale(1f, 0.5f, 1f)
                .accelerate()
                .duration(1000)
                .repeatCount(50)
                .start();
        //End

        getActionBar();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.yy));
        actionBar.setTitle("");

        Intent receievedIntentFromMain = getIntent() ;
        if(receievedIntentFromMain != null ){
            ArrayList<String> arrayList = receievedIntentFromMain.getStringArrayListExtra(DATA_FROM_MAIN_ACT) ;
            String amount = arrayList.get(MainActivity.AMT_INDEK) ;
            String description = arrayList.get(MainActivity.DES_INDEX) ;
            String mobileNumber = arrayList.get(MainActivity.NUM_INDEX) ;
            HashMap<String,String> mobile2nameMap = new HashMap<String, String>();
            mobile2nameMap.put("8308171711","Sairaj Sawant");
            mobile2nameMap.put("8087583519","Suryaa Jha");
            mobile2nameMap.put("9272012345","Rajesh Kandamwar"); //can3
            mobile2nameMap.put("9975596533","Deepak");  //can3
            mobile2nameMap.put("8624084713","Raju");    //tea
            mobile2nameMap.put("9850161313","Siddhivinayak Vada");
            mobile2nameMap.put("9049105574","Nilesh"); //roll
            mobile2nameMap.put("7757931082","Sham"); //chotu
            ((TextView) findViewById(R.id.rsShowerId)).append(amount);
            Date dNow = new Date( );
            String pattern = "hh:mm a ',' dd MMM yyyy";
            SimpleDateFormat ft =
                    new SimpleDateFormat (pattern);
            String curr_date_and_time = ft.format(dNow);
            for (Map.Entry<String,String> entry : mobile2nameMap.entrySet()) {
              //  System.out.printf("%s -> %s%n", entry.getKey(), entry.getValue());
                if(Objects.equals(entry.getKey(), mobileNumber)){
                    ((TextView) findViewById(R.id.nameOfPerson)).setText(entry.getValue());
                }
                else {
                   // Toast.makeText(this, "No name in map", Toast.LENGTH_SHORT).show();
                }
            }
            ((TextView) findViewById(R.id.mobileNumber)).setText(mobileNumber);
            ((TextView) findViewById(R.id.time_and_date)).setText(curr_date_and_time);
            ((TextView) findViewById(R.id.description)).setText("\" " + description + " \"");
            long number = (long) Math.floor(Math.random() * 9_000_000_0L) + 1_000_000_0L;
            String txn_id = "Wallet Txn ID:150" + number;
            ((TextView) findViewById(R.id.wallet_txn_id)).setText(txn_id);

        }


    }

    @Override
    public void onBackPressed() {
        MainActivity.progress.dismiss();
        super.onBackPressed();
    }

}
