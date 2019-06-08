package com.example.bhkalyani.samplefirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private EditText inputEmail,inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    private TextView newAccount;
    private Button btnLogin;
    private FirebaseAuth.AuthStateListener authListener;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Firebase.setAndroidContext;
        newAccount = (TextView) findViewById(R.id.newAccount);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.keepSynced(true);


        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);

        btnLogin = (Button) findViewById(R.id.btn_login);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        inputEmail.addTextChangedListener(new LoginActivity.MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new LoginActivity.MyTextWatcher(inputPassword));

        btnLogin.setOnClickListener(this);
        newAccount.setOnClickListener(this);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()!=null){
                    //Intent intent = new Intent("com.sgp.pizzaria.MAINACTIVITY");
                    startActivity(new Intent(LoginActivity.this,TabbedActivity.class));
                    finish();
                }
            }
        };
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
            {
                submitForm();
                break;
            }
            case R.id.newAccount:
            {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
                finish();
                break;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authListener);
    }

    private void submitForm()
    {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();

        progressDialog.setMessage("Signing In");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                  //  firebaseAuth.addAuthStateListener(authListener);
                    Toast.makeText(getApplicationContext(),"Sign In successful",Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),TabbedActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Failed to Login",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }


    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId())
            {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
