package com.example.ibrahim.firebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.FileInputStream;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.HolderView> {
    private static RecyclerAdapter.onItemClickLIstener onItemClickLIstener;
    List<CardViewData> list;
    public Context context;



    //public onItemClickLIstener onItemClickLIstener;
    public interface onItemClickLIstener {
        void onItemClick(int position);
    }

    public void setOnItemClickLIstener(RecyclerAdapter.onItemClickLIstener onItemClickLIstener) {
        this.onItemClickLIstener = onItemClickLIstener;
    }

    public RecyclerAdapter(Context context, List<CardViewData> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @NonNull
    @Override
    public HolderView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.card_layout, viewGroup, false);
        return new HolderView(view, com.example.ibrahim.firebase.RecyclerAdapter.onItemClickLIstener,this.context);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderView holderView, int i) {
        final CardViewData cardViewData = list.get(i);
        holderView.vehicle_name.setText(cardViewData.getName());
        holderView.ssid.setText(cardViewData.getSsid());
        holderView.status.setText(cardViewData.getStatus());
        //holderView.id.setText(cardViewData.getId());
        /*cardViewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked"+cardViewData.getId(), Toast.LENGTH_SHORT).show();
                //showOptionsMenu(holderView.menu);
                //holder.image.setBackgroundColor(Color.rgb(255,235,97));
            }
        });*/
        //holderView.image_name.setText(cardViewData.getimage_name());
    }

    public static class HolderView extends RecyclerView.ViewHolder {
        private TextView vehicle_name, ssid, status, image_name;
        private WifiManager wifiManager;
        private WifiConfiguration wifiConfiguration;
        static final int READ_BLOCK_SIZE = 100;
        public Context context;

        public HolderView(@NonNull View itemView, final onItemClickLIstener listener, final Context val) {
            super(itemView);
            vehicle_name = itemView.findViewById(R.id.vehicle_name);
            ssid = itemView.findViewById(R.id.ssid_name);
            status = itemView.findViewById(R.id.status);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = getAdapterPosition();
                    listener.onItemClick(id);
                    Toast.makeText(val, "Room Number : " + id, Toast.LENGTH_SHORT).show();
                    make_wifi_ready(ssid.getText().toString(), val);
                    Toast.makeText(val, "Connecting", Toast.LENGTH_SHORT).show();
                }
            });
        }
        /*public static boolean isConnected(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }

            return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
        }*/
        public void make_wifi_ready(String ssid, Context val) {
            String pre = read_all_recent_wifi(1, val);
            String password = check_wifi_data(pre, ssid);
            if (!password.equals("no")) {
                wifiManager = (WifiManager) val.getSystemService(Context.WIFI_SERVICE);
                if ((wifiManager.isWifiEnabled()) == false) {
                    wifiManager.setWifiEnabled(true);
                }
                wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.SSID = "\"" + ssid + "\"";
                wifiConfiguration.preSharedKey = "\"" + password + "\"";
                wifiManager.addNetwork(wifiConfiguration);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String info_ssid = wifiInfo.getSSID().replaceAll("^\"(.*)\"$", "$1");;
                if(!info_ssid.equals(ssid)) {
                    List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
                    // WifiInfo wifiInfo=wifiManager.getConnectionInfo();
                    for (WifiConfiguration i : list) {
                        if (i.SSID != null && i.SSID.equals("\"" + ssid + "\"")) {
                            wifiManager.disconnect();
                            wifiManager.enableNetwork(i.networkId, true);
                            wifiManager.reconnect();
                        }
                    }
                }
            }
        }

        public String read_all_recent_wifi(int i, Context val) {
            try {
                FileInputStream fileIn = val.openFileInput("allwificonnections.txt");
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

        public String check_wifi_data(String all, String s) {
            String[] wifi_list = all.split("\n");
            int i;
            for (i = 0; i < wifi_list.length; i++) {
                String[] val = wifi_list[i].split(":");
                if (val[0].equals(s)) {
                    return val[1];
                }
            }
            return "no";
        }
    }
}


