package com.example.ibrahim.firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


interface BackTaskResponse {
    void processFinish(String output);
}

public class Signal extends AppCompatActivity implements BackTaskResponse{
    List<control_switch> c_switch = new ArrayList<>();
    private RecyclerView recyclerView;
    private control_switch_Adapter c_s_Adapter;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        BackTask backtask =new BackTask();
        backtask.delegate = this;
        backtask.execute("1","1");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        c_s_Adapter = new control_switch_Adapter(c_switch, this);

        // Create grids with 2 items in a row
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(c_s_Adapter);

    }
    @Override
    public void processFinish(String output){
        if(!output.isEmpty()) {
            Log.w("Signal class:", "processFinish output :" + output);
            String[] all_val = output.split(";");
            int i;
            control_switch c_switch_obj;
            if (all_val.length > 1) {
                spinner.setVisibility(View.GONE);
            }
            for (i = 0; i < all_val.length; i++) {
                Log.w("Signal class:", "line" + all_val[i]);
                String[] value = all_val[i].split(",");
                if (value[1].equals("value:1")) {
                    c_switch_obj = new control_switch("Switch", R.drawable.light, Integer.toString(i), Color.rgb(76, 76, 76));
                } else {
                    c_switch_obj = new control_switch("Switch", R.drawable.light, Integer.toString(i), Color.rgb(248, 223, 95));
                }
                c_switch.add(c_switch_obj);
            }
            c_s_Adapter.notifyDataSetChanged();
        }
    }

    class BackTask extends AsyncTask<String, String, String>{
        String  msg;

        public BackTaskResponse delegate = null;

        StringBuilder response  = new StringBuilder();
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            delegate.processFinish(response.toString());
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL("http://192.168.4.1:1500/status");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setInstanceFollowRedirects(false);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "text/plain");
                connection.setRequestProperty("raw", "utf-8");

                //connection.getResponseCode();
                connection.connect();
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));

                out.write("{\"id\":\"12356\"}");
                out.flush();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String strLine = null;
                while ((strLine = in.readLine()) != null)
                {
                    response.append(strLine);
                }
                in.close();
                msg = connection.getResponseMessage();
            } catch (Exception e) {

            }
            return response.toString();
        }

    }

}


