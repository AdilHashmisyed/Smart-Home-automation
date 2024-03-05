package com.example.ibrahim.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

public class WifiConnection extends AppCompatActivity {
    private WifiManager wifiManager;
    private String SSID, password;
    private TextView ssid, pass;
    private Button connect;
    private WifiConfiguration wifiConfiguration;
    static final int READ_BLOCK_SIZE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_connection);
        ssid = findViewById(R.id.c_ssid);
        pass = findViewById(R.id.c_password);
        connect = findViewById(R.id.add);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if ((wifiManager.isWifiEnabled()) == false) {
            wifiManager.setWifiEnabled(true);
        }
        /*connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ssid.getText().equals("") || pass.getText().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter SSID and Password", Toast.LENGTH_LONG).show();
                    ;
                } else {
                    SSID = ssid.getText().toString().trim();
                    password = pass.getText().toString().trim();
                    wifiConfiguration = new WifiConfiguration();
                    wifiConfiguration.SSID = "\"" + SSID + "\"";
                    wifiConfiguration.preSharedKey = "\"" + password + "\"";
                    wifiManager.addNetwork(wifiConfiguration);
                    BackgroundTask backgroundTask = new BackgroundTask(wifiManager, wifiConfiguration);
                    backgroundTask.execute(SSID, password);
                    Writessid(1);
                }
            }
        });*/
    }

    public String read_all_recent_wifi(int i) {
        try {


            FileInputStream fileIn = openFileInput("allwificonnections.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            //textmsg.setText(s);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public void Writessid(int v) {
        String pre = read_all_recent_wifi(1);
        try {
            if ((!SSID.isEmpty()) & (!password.isEmpty())) {
                FileOutputStream fileout = openFileOutput("allwificonnections.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(pre + "\n" + SSID + ":" + password);
                outputWriter.close();
                //display file saved message
                Toast.makeText(getBaseContext(), "File saved successfully!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getBaseContext(), "fill all parameter",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class BackgroundTask extends AsyncTask<String, Integer, String> {
        String sid, pass;
        WifiConfiguration wifiConfiguration;
        WifiManager wifiManager;

        public BackgroundTask(WifiManager wifiManager, WifiConfiguration wifiConfiguration) {
            this.wifiManager = wifiManager;
            this.wifiConfiguration = wifiConfiguration;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent();
            intent.putExtra("SSID", sid);
            intent.putExtra("password", pass);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }

        @Override
        protected String doInBackground(String... strings) {
            sid = strings[0];
            pass = strings[1];
            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            // WifiInfo wifiInfo=wifiManager.getConnectionInfo();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + sid + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();
                }
            }
            return "connected";
        }
    }
}
