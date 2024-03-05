package com.example.ibrahim.firebase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.net.wifi.WifiManager;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.support.design.widget.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class Home extends AppCompatActivity implements View.OnClickListener {
    public static int GETSTATUS = 1;
    EditText textmsg;

    static final int READ_BLOCK_SIZE = 100;
    private RecyclerView recyclerView;
    private WifiManager wifiManager;
    private WifiConfiguration wifiConfiguration;
    private RecyclerView.Adapter adapter;
    List<CardViewData> list;
    String ssid, password;
    public int FLAG = 0;
    private Button scanBtn;
    private ActionBar toolbar;
    int nnumber;

    //public Home home;
    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GETSTATUS && data != null) {
            if (data.hasExtra("SSID") && data.hasExtra("password")) {
                ssid = data.getStringExtra("SSID");
                list.add(new CardViewData(ssid, "Connected"));
                adapter.notifyDataSetChanged();
            }
        }*/
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "msg" + result, Toast.LENGTH_LONG).show();
                if (!result.getContents().isEmpty()) {
                    String qr_data = result.getContents();
                    String[] qr_val = qr_data.split("\n");
                    int i;
                    int count = qr_val.length;
                    if (count == 2) {
                        for (i = 0; i < qr_val.length; i++) {
                            String[] qr_para = qr_val[i].split(":");
                            if (qr_para[0].equals("ssid")) {
                                ssid = qr_para[1];
                            } else if (qr_para[0].equals("password")) {
                                password = qr_para[1];
                            }
                        }
                        make_wifi_ready(1);
                    } else {
                        Toast.makeText(getBaseContext(), "Invalid QR Code",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Empty Qr code",
                            Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            //Toast.makeText(this, "msg"+resultCode, Toast.LENGTH_LONG).show();
        }
    }

    public void make_wifi_ready(int j) {
        if (!ssid.isEmpty()) {
            wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if ((wifiManager.isWifiEnabled()) == false) {
                wifiManager.setWifiEnabled(true);
            }
            wifiConfiguration = new WifiConfiguration();
            wifiConfiguration.SSID = "\"" + ssid + "\"";
            wifiConfiguration.preSharedKey = "\"" + password + "\"";
            wifiManager.addNetwork(wifiConfiguration);
            //BackgroundTask backgroundTask = new BackgroundTask(wifiManager, wifiConfiguration);
            //backgroundTask.execute(ssid, password);
            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            // WifiInfo wifiInfo=wifiManager.getConnectionInfo();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();
                }
            }
            Writessid(1);
        } else {
            Toast.makeText(getBaseContext(), "Empty Qr code",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setListItem();
        String[][] all = read_all_wifi_conn();
        if (all != null) {
            int i = 0;
            Log.w("hi", "value length " + all.length);
            for (i = 0; i < all.length; i++) {
                if (all[i][0] == null) break;
                list.add(new CardViewData(all[i][0], "is " + i, i));
            }
            nnumber = i;
        }
        adapter.notifyDataSetChanged();
        buildRecyclerView();
        //nav();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.scan_button:
                        scan_qr_code(1);
                        return true;
                    case R.id.add:
                        Intent s1 = new Intent(Home.this, WifiConnection.class);
                        startActivity(s1);
                        return true;
                    case R.id.settings:
                        Intent s = new Intent(Home.this, YourActivity.class);
                        startActivity(s);
                        return true;
                }

                return false;
            }
        });

        //toolbar.setTitle("Home");


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WifiConnection.class);
                startActivityForResult(intent, GETSTATUS);
            }
        });
        fab.setImageResource(R.drawable.ic_add_black_24dp);*/
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/
        //toolbar = supportActionBar!!

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //scanBtn = (Button) findViewById(R.id.scan_button);

        //scanBtn.setOnClickListener(this);
        //navigationView.setNavigationItemSelectedListener(this);
    }




    /*private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    public void nav() {
        BottomNavigationView bottomNavigation = findViewById(R.id.navigationView);

        bottomNavigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan_qr_code(1);
            }
        });
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.home:
                //home();
                return true;
            case R.id.scan_button:
                scan_qr_code(1);
                return true;
            case R.id.add:
                //scan();
                return true;
            case R.id.settings:
                //scan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
    public void scan_qr_code(int i) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    private String[][] read_all_wifi_conn() {
        String s = read_all_recent_wifi(1);
        if (s != null) {
            String all_wifi[] = s.split("\n");
            //list.add(new CardViewData("Syed", "ATIF"));
            int j = 0;
            String[][] val = new String[all_wifi.length][2];
            for (int i = 0; i < all_wifi.length; i++) {
                if (!all_wifi[i].isEmpty()) {
                    val[j] = all_wifi[i].split(":");
                    j++;
                }
            }
            Log.w("read_all_wifi_conn", "value length " + all_wifi.length);
            return val;
        }
        return null;
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout = new LinearLayoutManager((getApplicationContext()));
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);
    }

    private void setListItem() {
        list = new ArrayList<>();
        adapter = new RecyclerAdapter(getApplicationContext(), list);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String s = bundle.getString("name", "name");
            String sta = bundle.getString("status", "status");
            FLAG = bundle.getInt("flag");
            list.add(new CardViewData(ssid, s, sta));
            adapter.notifyDataSetChanged();
        }
        ((RecyclerAdapter) adapter).setOnItemClickLIstener(new RecyclerAdapter.onItemClickLIstener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getApplicationContext(), Signal.class);
                startActivity(intent);
                /*if (FLAG == 1) {

                } else {
                    Intent intent = new Intent(getApplicationContext(), AppInfo.class);
                    startActivity(intent);
                    adapter.notifyDataSetChanged();
                }*/
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    private void delete(Home F) {
        read_all_recent_wifi(1);
    }

    public void Writessid(int v) {
        String pre = read_all_recent_wifi(1);
        int sta = check_wifi_data(pre, ssid, password);
        if (sta == 1) {
            try {
                if ((!ssid.isEmpty()) & (!password.isEmpty())) {
                    FileOutputStream fileout = openFileOutput("allwificonnections.txt", MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    outputWriter.write(pre + "\n" + ssid + ":" + password);
                    outputWriter.close();
                    list.add(new CardViewData(ssid, "NEW", nnumber));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getBaseContext(), "File saved successfully!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "fill all parameter",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getBaseContext(), "Already Saved \nplz check in home page",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public int check_wifi_data(String all, String s, String p) {
        String[] wifi_list = all.split("\n");
        int i;
        for (i = 0; i < wifi_list.length; i++) {
            String[] val = wifi_list[i].split(":");
            if (val[0].equals(s)) {
                if (val[1].equals(p)) {
                    return 0;
                }
            }
        }
        return 1;
    }

    public void WriteBtn(View v, String val) {
        // add-write text into file
        String pre = read_all_recent_wifi(1);
        try {
            FileOutputStream fileout = openFileOutput("allwificonnections.txt", MODE_PRIVATE);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.write(pre + textmsg.getText().toString());
            outputWriter.close();
            //display file saved message
            //Toast.makeText(getBaseContext(), "File saved successfully!",
            //Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Read text from file
    public String read_all_recent_wifi(int i) {
        try {
            FileInputStream fileIn = openFileInput("allwificonnections.txt");
            InputStreamReader InputRead = new InputStreamReader(fileIn);
            char[] inputBuffer = new char[READ_BLOCK_SIZE];
            String s = "";
            int charRead;
            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                s += readstring;
            }
            InputRead.close();
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

/*    public class BackgroundTask extends AsyncTask<String, Integer, String> {
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

            return "connected";
        }
    }*/
}