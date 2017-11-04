package com.example.sairaj.paypet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.sairaj.paypet.SuperDuperActivity.MyPREFERENCES;

public class MainActivity extends AppCompatActivity {

    private String number;
    private String description;
    private String amount;
    public static int AMT_INDEK = 0;
    public static int DES_INDEX = 1;
    public static int NUM_INDEX = 2;
    public static int NAME_INDEX = 3;
    public static ProgressDialog progDialog;

    static final String[] NAME_NUM = new String[]{
            "8087583519",
            "8308171711",
            "9272012345",
            "7757931082"
    };

    private ArrayList<String> al = new ArrayList<>();

    ArrayList<EditText> requiredEditTextList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        Map<String, ?> prefMap = sharedpreferences.getAll();

        for (String key : prefMap.keySet()) {
            al.add(key + "      " + prefMap.get(key).toString());
        }


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.header));
        actionBar.setTitle("");

        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.editText);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.auto_complete, R.id.item, al);
        textView.setAdapter(adapter);

        description = ((EditText) findViewById(R.id.editText2)).getText().toString();
        Button button = findViewById(R.id.btn_proceed);

        requiredEditTextList = new ArrayList<>();
        requiredEditTextList.add(((EditText) findViewById(R.id.editText)));
        requiredEditTextList.add(((EditText) findViewById(R.id.editText1)));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progDialog = ProgressDialog.show(MainActivity.this, null, null, false, true);
                progDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progDialog.setContentView(R.layout.progress_bar);
                progDialog.show();

                boolean doWorkd = true;
                for (EditText editText : requiredEditTextList) {
                    if (TextUtils.isEmpty(editText.getText().toString())) {
                        doWorkd = false;
                    }
                }
                if (!Pattern.matches("^[789]\\d{9}$", requiredEditTextList.get(0).getText().toString())) {
                    doWorkd = false;
                    ((TextView) findViewById(R.id.mobile_error)).setVisibility(View.VISIBLE);
                    progDialog.dismiss();
                }
                if (doWorkd) {
                    description = ((EditText) findViewById(R.id.editText2)).getText().toString();
                    amount = ((EditText) findViewById(R.id.editText1)).getText().toString();
                    number = ((EditText) findViewById(R.id.editText)).getText().toString();
                    Intent superIntent = new Intent(MainActivity.this, SuperDuperActivity.class);
                    ArrayList<String> arrayListString = new ArrayList<>();
                    arrayListString.add(AMT_INDEK, amount);
                    arrayListString.add(DES_INDEX, description);
                    arrayListString.add(NUM_INDEX, number);

                    getNameOfNumber(number, arrayListString, superIntent);

                }
            }
        });


    }

    private void getNameOfNumber(String mobileNumber, final ArrayList<String> arrayListString, final Intent superIntent) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRefToBakras = firebaseDatabase.getReference()
                .child(ConstantsTree.BAKRAS);

        DatabaseReference dbRefToMobileNumberBakra = dbRefToBakras
                .child(mobileNumber);


        dbRefToMobileNumberBakra.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String nameOfThePerson = dataSnapshot.getValue(String.class);
                arrayListString.add(NAME_INDEX, nameOfThePerson);
                progDialog.dismiss();
                superIntent.putStringArrayListExtra(SuperDuperActivity.DATA_FROM_MAIN_ACT, arrayListString);
                //progress.dismiss();
                startActivity(superIntent);
                ((EditText) findViewById(R.id.editText)).setText("");
                ((EditText) findViewById(R.id.editText1)).setText("");
                ((EditText) findViewById(R.id.editText2)).setText("");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
