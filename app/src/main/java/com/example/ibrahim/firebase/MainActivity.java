package com.example.ibrahim.firebase;

/*import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.test.InstrumentationRegistry;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

//import android.support.test.runner.AndroidJUnit4;
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button scanBtn;


    //new IntentIntegrator(this).initiateScan();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanBtn = (Button) findViewById(R.id.scan_button);

        //scanBtn.setOnClickListener(this);
        move_to_home(this);
    }

    private static void move_to_home(MainActivity mainActivity) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(mainActivity, Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainActivity.startActivity(intent);
    }

    private static void userLogin(MainActivity mainActivity) {
    // String email="";
    //String email = editTextEmail.getText().toString().trim();
    //String email= mainActivity.sharedPreferences.getString("email","hello");
    //if(arrayDeque!=null)
    //email= (String) arrayDeque.peek();
    String password = mainActivity.editTextPassword.getText().toString().trim();
    if (password.length() < 4 ) {
    mainActivity.editTextPassword.setError("Minimum length of password should be 4");
    mainActivity.editTextPassword.requestFocus();
    return;
    }
    if (password.length() > 6) {
    mainActivity.editTextPassword.setError("Maximum length of password should be 6");
    mainActivity.editTextPassword.requestFocus();
    return;
    }
    int result = Integer.parseInt(password);
    if(result==5498) {
    Intent intent = new Intent(mainActivity, Home.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    mainActivity.startActivity(intent);
    }
    else{
    mainActivity.editTextPassword.setError("Incorrect Password");
    mainActivity.editTextPassword.requestFocus();
    return;
    }
    //progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View view) {
        //llSearch.setVisibility(View.GONE);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
        /* switch(view.getId()) {
        case R.id.textViewSignup:
        Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
        break;
        case R.id.buttonLogin:
        userLogin(this);
        break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                move_to_home(this);
                //Toast.makeText(this, "msg"+result, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            //Toast.makeText(this, "msg"+resultCode, Toast.LENGTH_LONG).show();
        }
    }
}*/


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
//this is random comment
public class MainActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, Home.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}