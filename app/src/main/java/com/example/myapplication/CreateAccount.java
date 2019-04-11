package com.example.myapplication;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateAccount extends AppCompatActivity {
    //define a constant for your tag in MainActivity
    private static final String TAG = "LoginActivity";

    private static final int REQUEST_SIGNUP = 0;

    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;


    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //validate whats entered on the fields
       if(!validate(email,password)){
           return;
       }

        progressDialog.setMessage("Registering User...");
        progressDialog.show();

    progressDialog.dismiss();
        /*
        Firebase
        * Create a new createAccount method which takes in an email address and password,
        * validates them and then creates a new user with the createUserWithEmailAndPassword method.
        *
        * */
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CreateAccount.this,"Registered Successfully", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                   updateUI(user);
                }else{
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CreateAccount.this,"Could not register", Toast.LENGTH_SHORT).show();

                    //use this to go to different activity
                    //updateUI(null);
                }
            }


        });

    }
//update the ui or send user to different activity

    public void updateUI(FirebaseUser user){

       // startActivity(new Intent(LoginActivity.this, CreateAccount.class));

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
            editTextEmail.setError("enter a valid email address");
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        return valid;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        buttonRegister = findViewById(R.id.buttonRegister);

        editTextEmail = findViewById(R.id.editText);

        editTextPassword = findViewById(R.id.editPassword);




//button press registers user
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == buttonRegister){
                    registerUser();
                }
            }
        });

    }
}
