package com.example.ibrahim.firebase;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class globelly extends YourActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.globelly);
        TextView txt2= (TextView) findViewById(R.id.textView7);
        txt2.setMovementMethod(LinkMovementMethod.getInstance());
        txt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //startActivity(intent);
                Toast.makeText(getBaseContext(), "Smart Home is up to Date!",
                        Toast.LENGTH_SHORT).show();
            }


        });
        TextView txt1= (TextView) findViewById(R.id.textView8); //txt is object of TextView
        txt1.setMovementMethod(LinkMovementMethod.getInstance());
        txt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://app.termly.io/document/terms-and-conditions/62239a63-b665-4a7e-9c50-bd030cbb4218"));
                startActivity(browserIntent);
            }
        });

    }
}