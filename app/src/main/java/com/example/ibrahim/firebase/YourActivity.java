package com.example.ibrahim.firebase;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.RecyclerView;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class  YourActivity extends AppCompatActivity {

    GridLayout gridLayout;
    ImageButton myImageButton;
    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youractivity);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);


        gridLayout=(GridLayout)findViewById(R.id.mainGrid);

        setSingleEvent(gridLayout);

    }

    // we are setting onClickListener for each element
    private void setSingleEvent(GridLayout gridLayout) {
        for(int i = 0; i<gridLayout.getChildCount();i++){
            CardView cardView=(CardView)gridLayout.getChildAt(i);
            final int finalI= i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(finalI==3){
                        Intent intent=new Intent(getApplicationContext(), globelly.class);
                        startActivity(intent);
                    }
                    if(finalI==4){
                        Intent intent=new Intent(getApplicationContext(), policy.class);
                        startActivity(intent);
                    }
                    if(finalI==2){
                        Intent intent=new Intent(getApplicationContext(), Bluetooth.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

       /* navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent s2 = new Intent(YourActivity.this, Home.class);
                        startActivity(s2);
                        return true;
                    case R.id.scan_button:
                        Toast.makeText(getBaseContext(), "To Scan make Sure You are in Home Page!",
                                Toast.LENGTH_SHORT).show();
                         return true;
                    case R.id.add:
                        Toast.makeText(getBaseContext(), "To Add make Sure You are in Home Page!",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.settings:
                        Intent s = new Intent(YourActivity.this, YourActivity.class);
                        startActivity(s);
                        return true;
                }

                return false;
            }
        });*/

    }



