package com.example.bhkalyani.samplefirebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

   private DatabaseReference databaseReference;
    private Toolbar toolbar;
    private EditText inputName, inputEmail, inputPassword,inputConfirmPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword,inputLayoutConfirmPassword;
    private Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ReplaceFont.ReplaceDefaultFont(this,"DEFAULT","HelveticaNeueIt.ttf");
        progressDialog = new ProgressDialog(this);

        /*toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
       inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_confirmpassword);

        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputConfirmPassword = (EditText) findViewById(R.id.input_confirmpassword);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputConfirmPassword.addTextChangedListener(new MyTextWatcher(inputConfirmPassword));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });
    }
        private void submitForm()
        {

        if (!validateName()) {
        return;
        }

        if (!validateEmail()) {
        return;
        }

       if (!validatePassword()) {
        return;
        }

            if(!equalPassword()){
                return;
            }

        //Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();

            String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();
        final String name = inputName.getText().toString().trim();
        final String price = "0";
        final String Order = null;

        progressDialog.setMessage("Registering the User");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    String user_id= firebaseAuth.getCurrentUser().getUid();
                    DatabaseReference current_user_db = databaseReference.child(user_id);
                    current_user_db.child("name").setValue(name);
                    current_user_db.child("Total").setValue(price);
                    current_user_db.child("Order").setValue(Order);
                    Toast.makeText(SignupActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                    finish();
                }
                else
                {
                    Toast.makeText(SignupActivity.this,"Not Registered Successfully..Please try again..",Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

        }

    private boolean equalPassword()
    {
        String password = inputPassword.getText().toString();
        String confirm_password = inputConfirmPassword.getText().toString();
        if(!password.equals(confirm_password))
        {
         inputLayoutConfirmPassword.setError("It should match with Password");
            requestFocus(inputConfirmPassword);
            return false;
        }
        else
        {
            inputLayoutConfirmPassword.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
        inputLayoutName.setError(getString(R.string.err_msg_name));
        requestFocus(inputName);
        return false;
        } else {
        inputLayoutName.setErrorEnabled(false);
        }

        return true;
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
                switch (view.getId()) {
                    case R.id.input_name:
                        validateName();
                        break;
                    case R.id.input_email:
                        validateEmail();
                        break;
                    case R.id.input_password:
                        validatePassword();
                        break;
                  case R.id.input_confirmpassword:
                        validatePassword();
                        break;
                }
            }
        }
}