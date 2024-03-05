package com.example.ibrahim.firebase;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Set;

import static android.os.SystemClock.sleep;
import static com.example.ibrahim.firebase.R.layout.bluetoot;

public class Bluetooth extends AppCompatActivity
{
    TextView b1,b3,b4;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice>pairedDevices;
    ListView lv;
    private OutputStream outputStream;
    private InputStream inStream;
    BluetoothSocket socket = null;
    ParcelUuid[] uuids=null;
    BluetoothDevice result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(bluetoot);

        b1 = (Button) findViewById(R.id.button);
        b3=(Button)findViewById(R.id.send);
        b4=(Button)findViewById(R.id.button4);

        BA = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView)findViewById(R.id.listView);
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on",Toast.LENGTH_LONG).show();
            sleep(100);
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }
        try {
            Method getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);
            ParcelUuid[] uuids = (ParcelUuid[]) getUuidsMethod.invoke(BA, null);

            if(uuids != null) {
                for (ParcelUuid uuid : uuids) {
                    Log.d("asad", "UUID: " + uuid.getUuid().toString());
                }
            }else{
                Log.d("asad", "Uuids not found, be sure to enable Bluetooth!");
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }


    public void off(View v){
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off" ,Toast.LENGTH_LONG).show();
    }


    public  void visible(View v){
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivityForResult(getVisible, 0);
    }

    public void send(View v){
        String tosend="I am android app by adil\n";
        try {
            socket = result.createRfcommSocketToServiceRecord(uuids[0].getUuid());
        } catch (Exception e) {Log.e(":error:","Error creating socket");}

        try {
            socket.connect();
            Log.e("1","Connected");
            outputStream = socket.getOutputStream();
            outputStream.write(tosend.getBytes());
            inStream = socket.getInputStream();
            //new Toast(context, "Already Registered", Toast.LENGTH_SHORT).send();
            Log.w("try1:","sent");
        } catch (IOException e) {
            Log.e(":error:",e.getMessage());
            try {
                Log.e("2","trying fallback...");

                socket =(BluetoothSocket) result.getClass().getMethod("createRfcommSocket", new Class[] {int.class}).invoke(result,1);
                socket.connect();
                Log.e("","Connected");
                outputStream = socket.getOutputStream();
                outputStream.write(tosend.getBytes());
                inStream = socket.getInputStream();
                //new Toast(context, "Already Registered", Toast.LENGTH_SHORT).send();
                Log.w("try2:","sent");
            }
            catch (Exception e2) {
                Log.e(":error:", "Couldn't establish Bluetooth connection!");
            }
        }
    }
    public void list(View v){
        String deviceName = "ESP32test";
        pairedDevices = BA.getBondedDevices();
        ArrayList list = new ArrayList();
        if (pairedDevices != null) {
            for (BluetoothDevice device : pairedDevices) {
                if (deviceName.equals(device.getName())) {
                    uuids= device.getUuids();
                    result = device;
                    break;
                }
            }
        }
        //for(BluetoothDevice bt : pairedDevices) list.add(bt.getName());
        Toast.makeText(getApplicationContext(), "connecting to"+deviceName+" Devices",Toast.LENGTH_SHORT).show();
        //connect.invoke(proxy, result);
        //final ArrayAdapter adapter = new  ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        //lv.setAdapter(adapter);
    }
    /*
    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                Log.e(TAG, "Socket's create() method failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.
            bluetoothAdapter.cancelDiscovery();

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    Log.e(TAG, "Could not close the client socket", closeException);
                }
                return;
            }

            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            manageMyConnectedSocket(mmSocket);
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the client socket", e);
            }
        }
    }*/
}
