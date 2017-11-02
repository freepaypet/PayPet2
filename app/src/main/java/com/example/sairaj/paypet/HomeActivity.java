package com.example.sairaj.paypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home) ;

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            Intent intentToMainAct = new Intent(HomeActivity.this,MainActivity.class) ;
            intentToMainAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
            startActivity(intentToMainAct) ;
        }
        return true;
    }
}
