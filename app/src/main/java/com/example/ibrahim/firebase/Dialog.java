package com.example.ibrahim.firebase;
        import android.content.DialogInterface;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        public class Dialog extends AppCompatActivity {
        AlertDialog.Builder builder;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        builder=new AlertDialog.Builder(getApplicationContext());
        builder.setPositiveButton("Agree", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
        }).setNegativeButton("Decline", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
        finish();
        }
        });
        }
        }