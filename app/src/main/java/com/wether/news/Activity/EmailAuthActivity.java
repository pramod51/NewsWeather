package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.wether.news.R;
import com.wether.news.ViewModels.LoginSignupViewModel;

import java.util.Objects;

public class EmailAuthActivity extends AppCompatActivity {
    private TextInputLayout emailInputLayout,passwordInputLayout;
    private TextInputEditText email,password;
    private MaterialButton signIn,signUp;
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    //private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private LoginSignupViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAuth.getCurrentUser()!=null){
            goInMainActivity();
            return;
        }
        setContentView(R.layout.activity_email_auth);
        initViews();

        Log.v("tag","onCreate");

        viewModel = new ViewModelProvider(this).get(LoginSignupViewModel.class);
        viewModel.onSuccess().observe(this, firebaseUser -> {
            if (firebaseUser!=null) {
                hideProgressDialog();
                goInMainActivity();
            }
        });
        viewModel.onFailure().observe(this, s -> {
            if (progressDialog!=null){
                Toast.makeText(EmailAuthActivity.this,s,Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
        signIn.setOnClickListener(view -> {
            if (isValidCredentials()){
                showProgressDialog();
                if (!signIn.getText().toString().equals("Sign In")) {
                    viewModel.emailPasswordAuthSignUP(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(password.getText()).toString());
                } else {
                    viewModel.emailPasswordAuthSignIN(Objects.requireNonNull(email.getText()).toString(), Objects.requireNonNull(password.getText()).toString());
                }
            }
        });
        signUp.setOnClickListener(view -> {
            if (signUp.getText().toString().equals("Sign Up")){
                signIn.setText(R.string.sign_up);
                signUp.setText(R.string.sign_in);
            }
            else{
                signIn.setText(R.string.sign_in);
                signUp.setText(R.string.sign_up);
            }
        });
        Log.v("tag","hjhfhkj");


    }

    private void initViews() {
        emailInputLayout=findViewById(R.id.email_layout);
        passwordInputLayout=findViewById(R.id.pass_layout);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.sign_in);
        signUp=findViewById(R.id.sign_up);
    }

    private boolean isValidCredentials(){
        emailInputLayout.setError(null);
        password.setError(null);
        if (Objects.requireNonNull(email.getText()).toString().isEmpty()){
            emailInputLayout.setError("Email can't be empty");
            return false;
        }
        else if (Objects.requireNonNull(password.getText()).toString().isEmpty()){
            passwordInputLayout.setError("Password can't be empty");
            return false;
        }
        else {
            if (password.getText().toString().length() >= 6) {
                return true;
            }
            passwordInputLayout.setError("minimum password length should be 6");
            return false;
        }

    }


    private void goInMainActivity(){
        startActivity(new Intent(EmailAuthActivity.this,MainActivity.class));
        finish();
    }
    private void showProgressDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

}