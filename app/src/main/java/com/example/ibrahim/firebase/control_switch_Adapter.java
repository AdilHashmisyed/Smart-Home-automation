package com.example.ibrahim.firebase;

import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Stream;

import static android.os.SystemClock.sleep;


public class control_switch_Adapter extends RecyclerView.Adapter<control_switch_Adapter.CustomViewHolder> {

    private Context mContext;
    private List<control_switch> control_switch;
    int[] pinvalue;
    //final control_switch_Adapter.CustomViewHolder holder;
    public control_switch_Adapter(List<control_switch> control_switch, Context mContext) {

        this.mContext = mContext;
        this.control_switch = control_switch;
        //holder = null;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.scard, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final control_switch_Adapter.CustomViewHolder holder ,int position) {
        final control_switch control_switch_var = control_switch.get(position);
        holder.name.setText(control_switch_var.getName());
        holder.serialnumber.setText(control_switch_var.getserialnumber());
        holder.image.setImageResource(control_switch_var.getImageResource());
        holder.image.setBackgroundColor(control_switch_var.getcolor());
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "clicked"+control_switch_var.getName(), Toast.LENGTH_SHORT).show();
                funbacktask(holder,control_switch_var.getserialnumber());
                sleep(100);
                //holder.image.setBackgroundColor(Color.rgb(255,235,97));
            }
        });
    }
    private void funbacktask(final control_switch_Adapter.CustomViewHolder holder, final String pinnum) {
        class BackTask extends AsyncTask<String, Void, String> {
            String msg, value, pin;
            int pin1 = 10, value1 = 0;
            StringBuilder response = new StringBuilder();
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (pin1 == Integer.parseInt(pin)) {
                    control_switch control_switch_var = control_switch.get(pin1);
                    holder.image.setImageResource(control_switch_var.getImageResource());
                    if (value1 == 1) {
                        holder.image.setBackgroundColor(Color.rgb(76, 76, 76));
                        Log.w("Signal class:", "pin:is ON");
                    } else {
                        holder.image.setBackgroundColor(Color.rgb(248, 223, 95));
                        Log.w("Signal class:", "pin:is OFF");
                    }
                }
            }

            @Override
            protected String doInBackground(String... params) {
                pin = pinnum;
                //control_switch_Adapter.CustomViewHolder holder = null;
                try {

                    URL url = new URL("http://192.168.4.1:1500/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "text/plain");
                    connection.setRequestProperty("raw", "utf-8");
                    connection.connect();

                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    out.write("{\"pin\":\"" + pin + "\",\"id\":\"12356\"}");
                    out.flush();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String strLine = null;
                    while ((strLine = in.readLine()) != null) {
                        response.append(strLine);
                    }
                    in.close();
                    msg = connection.getResponseMessage();
                    Log.w("control_switc", "doInBackground: " + response.toString());
                    String[] all_val = response.toString().split(";");
                    int i;
                    for (i = 0; i < all_val.length; i++) {
                        String[] val = all_val[i].split(":");
                        if (val[0].equals("pin")) {
                            pin1 = Integer.parseInt(val[1]);
                        } else if (val[0].equals("value")) {
                            value1 = Integer.parseInt(val[1]);
                        }
                    }

                } catch (Exception e) {
                    Log.w("control_switc error", "doInBackground: " + e.toString());
                }
                return response.toString();
            }
        }
        new BackTask().execute();
    }
    //@Override
    /*public void csprocessFinish(String output){
        Log.w("Signal class:","processFinish output :"+output);
        int pin1;
        String value1;
        String[] all_val=output.split(";");
        int i;
        for(i=0;i<all_val.length;i++){
            String[] val=all_val[i].split(":");
            if(val[0].equals("pin")){
                pin1=Integer.parseInt(val[1]);
            }
            else if(val[0].equals("value")){
                value1=val[1];
            }
        }
        if(value1.equals("1")){
        Log.w("Signal class:","pin:is ON");}
        else{
            Log.w("Signal class:","pin:is OFF");}
    }*/
    /*private void funbacktask(final CustomViewHolder holder, String getserialnumber) {
        class BackTask extends AsyncTask<String, void, String> {
            String msg, value, pin;

            StringBuilder response = new StringBuilder();

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(String... strings) {
                value = strings[0];
                pin = strings[1];
                //control_switch_Adapter.CustomViewHolder holder = null;
                try {
                    int pin1 = 10, value1 = 0;
                    URL url = new URL("http://192.168.4.1:1500/");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setInstanceFollowRedirects(false);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "text/plain");
                    connection.setRequestProperty("raw", "utf-8");
                    connection.connect();

                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                    out.write("{\"pin\":\"" + pin + "\",\"id\":\"12356\"}");
                    out.flush();
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String strLine = null;
                    while ((strLine = in.readLine()) != null) {
                        response.append(strLine);
                    }
                    in.close();
                    msg = connection.getResponseMessage();
                    Log.w("control_switc", "doInBackground: " + msg);
                    String[] all_val = response.toString().split(";");
                    int i;
                    for (i = 0; i < all_val.length; i++) {
                        String[] val = all_val[i].split(":");
                        if (val[0].equals("pin")) {
                            pin1 = Integer.parseInt(val[1]);
                        } else if (val[0].equals("value")) {
                            value1 = Integer.parseInt(val[1]);
                        }
                    }
                    if (pin1 == Integer.parseInt(pin)) {
                        control_switch control_switch_var = control_switch.get(pin1);
                        holder.image.setImageResource(control_switch_var.getImageResource());
                        if (value1 == 1) {
                            holder.image.setBackgroundColor(Color.rgb(255, 235, 97));
                            Log.w("Signal class:", "pin:is ON");
                        } else {
                            holder.image.setBackgroundColor(Color.rgb(0, 235, 97));
                            Log.w("Signal class:", "pin:is OFF");
                        }
                    }
                } catch (Exception e) {
                    Log.w("control_switc error", "doInBackground: " + e.toString());
                }
                return response.toString();
            }
        }
    }*/

    @Override
    public int getItemCount() {
        return control_switch.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView name, serialnumber;
        public ImageView image, menu;

        public CustomViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.swName);
            serialnumber = (TextView) itemView.findViewById(R.id.swNumber);
            image = (ImageView) itemView.findViewById(R.id.swImage);
        }
    }


}