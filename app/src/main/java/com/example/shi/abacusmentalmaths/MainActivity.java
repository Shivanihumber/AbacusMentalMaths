package com.example.shi.abacusmentalmaths;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    EditText name, phone_no, id;
    Button addButton;
    List<Client> list = new ArrayList<Client>();
    DataBaseHelper db;
    ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(getApplicationContext());
        name = (EditText) findViewById(R.id.editText1);
        phone_no = (EditText) findViewById(R.id.editText2);
        searchButton = (ImageButton) findViewById(R.id.serbutton);
        addButton = (Button) findViewById(R.id.add);

        addButton.setOnClickListener(addButtonSetOnClickListener);
        searchButton.setOnClickListener(searchButtonSetOnClickListener);

    }


    Button.OnClickListener searchButtonSetOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent k = new Intent(MainActivity.this, HelpActivity.class);

            startActivity(k);

        }
    };
    Button.OnClickListener addButtonSetOnClickListener
            = new Button.OnClickListener() {

        @Override
        public void onClick(View V) {

            String playerName = name.getText().toString();
            String PlayerPhoneNum = phone_no.getText().toString();
            Log.d("oooo", "value MEM1" + playerName);
            Log.d("oooo", "value MEM1" + PlayerPhoneNum);
            if (playerName.equals("") || PlayerPhoneNum.equals("")) {
                name.setError("Enter Value:");
                name.requestFocus();
                phone_no.setError("Enter Value:");
                phone_no.requestFocus();
            } else {

                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.putExtra("PlayerName", playerName);
                i.putExtra("PlayerPhoneNum", PlayerPhoneNum);
                startActivity(i);
            }
        } // Toast.makeText(this,"new client added",Toast.LENGTH_SHORT).show();


    };
}


