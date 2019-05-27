package com.example.shi.abacusmentalmaths;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GameActivity extends Activity {

    List<String> integers = new ArrayList<String>();
    final int MAX_RUNS = 2;
    int totalRuns = 0;
    int count = 0;
    int correct = 0;
    int incorrect = 0;
    DataBaseHelper db;
    long correctScore = 0;
    int totalScore = 3;
    EditText editText;
    TextView correctView;
    TextView incorrectView;
    Date today = new Date();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
    String dateToStr = format.format(today);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        db = new DataBaseHelper(getApplicationContext());
        editText = (EditText) findViewById(R.id.editText);
        correctView = (TextView) findViewById(R.id.textViewCorrect);
        incorrectView = (TextView) findViewById(R.id.textViewIncorrect);

        regenerateList();

        Button okButton = (Button) findViewById((R.id.button));
        final Chronometer timer = (Chronometer) findViewById((R.id.chronometer));
        final MediaPlayer applausePlayer = MediaPlayer.create(this, R.raw.applause);
        final MediaPlayer booPlayer = MediaPlayer.create(this, R.raw.crowdboo);

        timer.start();

        okButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String valStr = editText.getText().toString();
                if (valStr == null || valStr.equals("")) {
                    editText.setError("Enter Value:");
                    editText.requestFocus();

                } else {

                    int inputNumber = Integer.parseInt(valStr);
                    String message = "";
                    if (count == inputNumber) {
                        applausePlayer.start();
                        message = "Correct";
                        correctView.setText("Correct: " + (++correct));
                    } else {
                        booPlayer.start();
                        message = "Incorrect: Answer is " + count;
                        incorrectView.setText("Incorrect: " + (++incorrect));
                    }
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    if (totalRuns < MAX_RUNS) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        totalRuns++;
                        regenerateList();
                        editText.setText("");
                        timer.setBase(SystemClock.elapsedRealtime());

                        timer.start();
                    } else {
                        Toast.makeText(getApplicationContext(), "Game Finished", Toast.LENGTH_SHORT).show();
                        timer.stop();
                        correctScore = Math.round(((correct * 1.0F) / totalScore) * 100.0F);
                        //Client client = new Client();
                        String PlayerName = getIntent().getStringExtra("PlayerName");
                        String PlayerPhoneNum = getIntent().getStringExtra("PlayerPhoneNum");
                        Client client = new Client();
                        client.name = PlayerName;
                        client.phone_number = PlayerPhoneNum;

                        client.score = correctScore;
                        client.test_date = dateToStr;
                        db.addClientDetail(client);
                        Intent j = new Intent(GameActivity.this, ScoreActivity.class);
                        j.putExtra("PlayerPhoneNum", PlayerPhoneNum);
                        j.putExtra("PlayerName", PlayerName);

                        startActivity(j);
                    }
                }
            }
        });
    }


    private void regenerateList() {

        Random random = new Random();

        integers.clear();
        count = 0;
        int counter = 0;
        while (counter < 5) {
            Integer nextInt = 50 - random.nextInt(101);
            count = count + nextInt;
            if (count < 0) {
                count = count - nextInt;
                continue;
            }
            integers.add(new Integer(nextInt).toString());
            counter++;
        }

        ListView list = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.textView2, integers);

        list.setAdapter(adapter);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("correct", correct);
        outState.putInt("incorrect", incorrect);
        outState.putInt("totalRuns", totalRuns);
        outState.putInt("count", count);

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            correctView.setText("Correct: " + savedInstanceState.getInt("correct"));
            incorrectView.setText("Incorrect: " + savedInstanceState.getInt("incorrect"));
            correct = savedInstanceState.getInt("correct");
            incorrect = savedInstanceState.getInt("incorrect");
            totalRuns = savedInstanceState.getInt("totalRuns");
            count = savedInstanceState.getInt("count");
        }
    }

    public void onBack(View view) {
        finish();
    }
}

