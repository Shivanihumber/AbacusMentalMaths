package com.example.shi.abacusmentalmaths;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.InputStream;
import java.util.Scanner;

public class HelpActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    public void onLook(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        String theword = editText.getText().toString();
        String definition = findDefention(theword);
        TextView textView = (TextView) findViewById(R.id.textView);
        if (definition != null) {
            textView.setText(definition);
        } else {
            textView.setText("Word not found");
        }
    }

    private String findDefention(String theword) {
        InputStream input = getResources().openRawResource(R.raw.help);
        Scanner scan = new Scanner(input);
        while (scan.hasNext()) {
            String line = scan.nextLine();
            String[] pieces = line.split("=");
            if (pieces[0].equalsIgnoreCase(theword.trim())) {
                return pieces[1];
            }
        }
        return null;

    }

    public void onBack(View view) {
        finish();
    }
}
