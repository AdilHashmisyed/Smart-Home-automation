package com.example.ibrahim.firebase;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.os.AsyncTask;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import java.util.List;
        public class AppInfo extends AppCompatActivity {
        EditText name, reg;
        Button pair;
        SharedPreferences sharedPreferences;
        private String vehicle_name, reg_no, unique_no;
        RecyclerAdapter adapter;
        List<CardViewData> list;
        // public CardViewData data;
        int FLAG=1;
        public AppInfo(){
        }
        public AppInfo (RecyclerAdapter adapter, List<CardViewData> list){
        this.adapter=adapter;
        this.list=list;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);
        name = findViewById(R.id.info_name);
        reg = findViewById(R.id.info_reg);
        pair = findViewById(R.id.info_pair);
        // sharedPreferences=getSharedPreferences("DATA",Context.MODE_PRIVATE);
        pair.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        vehicle_name = name.getText().toString().trim();
        reg_no = reg.getText().toString().trim();
        //unique_no = "5000";
        // SharedPreferences.Editor editor=sharedPreferences.edit();
        //editor.putString("Name",vehicle_name);
        Intent intent=new Intent(getApplicationContext(),Home.class);
        intent.putExtra("name",vehicle_name);
        intent.putExtra("status","Paired");
        intent.putExtra("flag",FLAG);
        startActivity(intent);
        finish();
        // new ConnectTask().execute(vehicle_name,reg_no,unique_no);
        }
        });
        }
        /* class ConnectTask extends AsyncTask<String,String,String>{
        String vehicle_name, reg_no, unique_no;
        @Override
        protected String doInBackground(String... strings) {
        vehicle_name=strings[0];
        reg_no=strings[1];
        unique_no=strings[2];
        return null;
        }
        }*/
        }