package com.example.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

private EditText mEmail;
private EditText mPassword;
private ProgressBar progressBar;
private FirebaseAuth auth;
private Button btnsignUp;
private Button btnLogin;
private Button btnReset;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //get firebase auth instance
        auth = FirebaseAuth.getInstance();

        //check if user has an account
        if(auth.getCurrentUser() != null){
    startActivity(new Intent(LoginActivity.this, userMainScreen.class));
    finish();
        }
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emailLoginField);
        mPassword = findViewById(R.id.passwordLoginfield);
        progressBar = new ProgressBar(this);
        btnsignUp = findViewById(R.id.regBtn);
        btnLogin = findViewById(R.id.loginBtn);
        btnReset = findViewById(R.id.forgotBtn);

        auth = FirebaseAuth.getInstance();


        //open signup  activity
        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccount.class));
            }
        });

        //open reset activity
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });


        //login activity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if(!validate(email,password)){
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //authenticate user
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //If sign has failed, displaymeesage to user
                        //if succeeds auth state listner will be notified and logic handle the user
                        progressBar.setVisibility(View.GONE);
                        //there was an error
                        if(!task.isSuccessful()){
                            Toast.makeText(LoginActivity.this,"Authentication failed", Toast.LENGTH_LONG ).show();
                        }
                        else{
                            Intent intent = new Intent(LoginActivity.this,userMainScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        });




    }



    /*
     * validate :boolean
     *
     * This function checks if the email and password fields are empty or
     * the length is wrong. Also outputs a error message
     *
     * returns if valid is right
     * */
    public boolean validate(String email, String password){
        boolean valid = true;

        //get contains of the fields


        //check if email field is empty or email is wrong format
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPassword.setError(null);
        }
        return valid;
    }
}
