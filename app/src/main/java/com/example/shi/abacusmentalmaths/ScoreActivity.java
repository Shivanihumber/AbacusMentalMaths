package com.example.shi.abacusmentalmaths;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ScoreActivity extends Activity {
    List<Client> list = new ArrayList<Client>();
    DataBaseHelper db1;
    String PlayerPhoneNum;
    String PlayerName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        db1 = new DataBaseHelper(getApplicationContext());
        PlayerPhoneNum = getIntent().getStringExtra("PlayerPhoneNum");
        PlayerName = getIntent().getStringExtra("PlayerName");
        Client client = new Client();

        client.phone_number = PlayerPhoneNum;
        list = db1.getAllClientsList(PlayerPhoneNum);
        printAll(list);
    }

    private void printAll(List<Client> list) {

        String value = "";
        List<String> listOfResults = new ArrayList<>();
        for (Client sm : list) {
            listOfResults.add("Name: " + sm.name + "\nPhone: " + sm.phone_number + "\nDate: "
                    + sm.test_date + "\nScore: " + sm.score + "%");
        }
        ListView listView = findViewById(R.id.listViewScore);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView2, listOfResults);

        listView.setAdapter(adapter);
    }

    public void onBack(View view) {

        Intent k = new Intent(ScoreActivity.this, GameActivity.class);

        k.putExtra("PlayerPhoneNum", PlayerPhoneNum);
        k.putExtra("PlayerName", PlayerName);

        startActivity(k);
    }


}
