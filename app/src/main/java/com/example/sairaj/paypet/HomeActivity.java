package com.example.sairaj.paypet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private boolean show = false ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home) ;

        findViewById(R.id.hit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( (!TextUtils.isEmpty(((TextView) findViewById(R.id.mobileNumberEnter)).getText().toString())) &&  (!TextUtils.isEmpty(((TextView) findViewById(R.id.nameEnter)).getText().toString())) ) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference dbRefToBakras = firebaseDatabase.getReference()
                            .child(ConstantsTree.BAKRAS);
                    dbRefToBakras.child(((TextView) findViewById(R.id.mobileNumberEnter)).getText().toString()).setValue(((TextView) findViewById(R.id.nameEnter)).getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ((TextView) findViewById(R.id.mobileNumberEnter)).setText("");
                            ((TextView) findViewById(R.id.nameEnter)).setText("");
                        }
                    });
                }
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            Intent intentToMainAct = new Intent(HomeActivity.this,MainActivity.class) ;
            intentToMainAct.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
            startActivity(intentToMainAct) ;
        }
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            if(!show) {
                findViewById(R.id.mobileNumberEnter).setVisibility(View.VISIBLE);
                findViewById(R.id.nameEnter).setVisibility(View.VISIBLE);
                findViewById(R.id.hit).setVisibility(View.VISIBLE);
                show = !show ;
            }else{
                findViewById(R.id.mobileNumberEnter).setVisibility(View.GONE);
                findViewById(R.id.nameEnter).setVisibility(View.GONE);
                findViewById(R.id.hit).setVisibility(View.GONE);
                show = !show ;
            }
        }
        return true;
    }
}
