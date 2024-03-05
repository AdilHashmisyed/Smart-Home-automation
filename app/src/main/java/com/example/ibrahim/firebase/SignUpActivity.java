/*package com.example.ibrahim.firebase;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Patterns;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.Task;
        //import com.google.firebase.auth.AuthResult;
        //import com.google.firebase.auth.FirebaseAuth;
        //import com.google.firebase.auth.FirebaseAuthUserCollisionException;
        import java.util.ArrayDeque;
        public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
        ProgressBar progressBar;
        EditText editTextEmail, editTextPassword;
        //private FirebaseAuth mAuth;
        SharedPreferences sharedPreferences;
        static public ArrayDeque<String> arrayDeque;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        //mAuth = FirebaseAuth.getInstance();
        arrayDeque=new ArrayDeque<>();
        Button button=findViewById(R.id.buttonSignUp);
        TextView textView=findViewById(R.id.textViewLogin);
        button.setOnClickListener(this);
        textView.setOnClickListener(this);
        sharedPreferences=getSharedPreferences("Email",Context.MODE_PRIVATE);
        }
        private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        if (email.isEmpty()) {
        editTextEmail.setError("Email is required");
        editTextEmail.requestFocus();
        return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        editTextEmail.setError("Please enter a valid email");
        editTextEmail.requestFocus();
        //arrayDeque.push(email);
        return;
        }
        /*if (password.isEmpty()) {
        editTextPassword.setError("Password is required");
        editTextPassword.requestFocus();
        return;
        }
        if (password.length() < 4 ) {
        editTextPassword.setError("Minimum length of password should be 6");
        editTextPassword.requestFocus();
        return;
        }
        if (password.length() > 6) {
        editTextPassword.setError("Maximum length of password should be 6");
        editTextPassword.requestFocus();
        return;
        }* /
        Intent intent = new Intent(SignUpActivity.this, Control.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        //progressBar.setVisibility(View.VISIBLE);
        //arrayDeque.push(email);
        / *SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.commit();/
        /*
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
        progressBar.setVisibility(View.GONE);
        if (task.isSuccessful()) {
        Intent intent = new Intent(SignUpActivity.this, Control.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        } else {
        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
        Toast.makeText(getApplicationContext(), "Already Registered", Toast.LENGTH_SHORT).show();
        } else {
        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
        }
        }
        }
        });
        * /
        }
        @Override
        public void onClick(View view) {/*
        switch (view.getId()) {
        case R.id.buttonSignUp:
        registerUser();
        break;
        case R.id.textViewLogin:
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        break;
        }
        * /
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        }
        }*/