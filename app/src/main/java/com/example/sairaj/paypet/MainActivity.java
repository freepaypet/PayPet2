package com.example.sairaj.paypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String number  ;
    private String description ;
    private String amount  ;
    public static int AMT_INDEK = 0 ;
    public static int DES_INDEX = 1 ;
    public static int NUM_INDEX = 2 ;
    public static ProgressDialog progress ;

    ArrayList<EditText> requiredEditTextList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar() ;
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.header));
        actionBar.setTitle("");

        description = ((EditText) findViewById(R.id.editText2)).getText().toString() ;
        Button button = findViewById(R.id.btn_proceed);

        requiredEditTextList = new ArrayList<>() ;
        requiredEditTextList.add(((EditText) findViewById(R.id.editText))) ;
        requiredEditTextList.add(((EditText) findViewById(R.id.editText1))) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progress = new ProgressDialog(MainActivity.this);
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setMessage("Proceeding to Payment...");
                progress.show();

                boolean doWorkd =  true ;
                for ( EditText editText :  requiredEditTextList ) {
                    if ( TextUtils.isEmpty(editText.getText().toString())){
                        doWorkd = false ;
                    }
                }
                if(doWorkd){
                    description = ((EditText) findViewById(R.id.editText2)).getText().toString() ;
                    amount = ((EditText) findViewById(R.id.editText1)).getText().toString() ;
                    number = ((EditText) findViewById(R.id.editText)).getText().toString() ;
                    Intent superIntent = new Intent(MainActivity.this,SuperDuperActivity.class) ;
                    ArrayList<String> arrayListString = new ArrayList<>() ;
                    arrayListString.add(AMT_INDEK,amount) ;
                    arrayListString.add(DES_INDEX,description) ;
                    arrayListString.add(NUM_INDEX,number) ;
                    superIntent.putStringArrayListExtra(SuperDuperActivity.DATA_FROM_MAIN_ACT,arrayListString) ;
                    //progress.dismiss();
                    ((EditText) findViewById(R.id.editText)).setText("");
                    ((EditText) findViewById(R.id.editText1)).setText("");
                    ((EditText) findViewById(R.id.editText2)).setText("");
                    startActivity(superIntent);
                }
            }
        });


    }
}
