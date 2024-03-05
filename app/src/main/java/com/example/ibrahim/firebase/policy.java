package com.example.ibrahim.firebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

public class policy extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy);
        String htmlAsString = getString(R.string.policy);
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView
        TextView textView = (TextView) findViewById(R.id.textView6);
        textView.setText(htmlAsSpanned);
    }
}
