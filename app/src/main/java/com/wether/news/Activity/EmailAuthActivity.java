package com.wether.news.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.wether.news.R;
import com.wether.news.ViewModels.LoginSignupViewModel;
import com.wether.news.databinding.ActivityEmailAuthBinding;

import java.util.Objects;

public class EmailAuthActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    //private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private LoginSignupViewModel viewModel;
    private ActivityEmailAuthBinding emailAuthBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAuth.getCurrentUser()!=null){
            goInMainActivity();
            return;
        }
        emailAuthBinding= ActivityEmailAuthBinding.inflate(getLayoutInflater());
        setContentView(emailAuthBinding.getRoot());


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

        emailAuthBinding.signIn.setOnClickListener(view -> {
            if (isValidCredentials()){
                showProgressDialog();

                if (!emailAuthBinding.signIn.getText().toString().equals(getString(R.string.sign_in))) {
                    viewModel.emailPasswordAuthSignUP(Objects.requireNonNull(emailAuthBinding.email.getText()).toString(), Objects.requireNonNull(emailAuthBinding.password.getText()).toString());
                } else {
                    viewModel.emailPasswordAuthSignIN(Objects.requireNonNull(emailAuthBinding.email.getText()).toString(), Objects.requireNonNull(emailAuthBinding.password.getText()).toString());
                }
            }
        });
        emailAuthBinding.signUp.setOnClickListener(view -> {
            if (emailAuthBinding.signUp.getText().toString().equals(getString(R.string.sign_up))){
                emailAuthBinding.signIn.setText(R.string.sign_up);
                emailAuthBinding.signUp.setText(R.string.sign_in);
            }
            else{
                emailAuthBinding.signIn.setText(R.string.sign_in);
                emailAuthBinding.signUp.setText(R.string.sign_up);
            }
        });



    }


    private boolean isValidCredentials(){
        emailAuthBinding.emailLayout.setError(null);
        emailAuthBinding.password.setError(null);
        if (Objects.requireNonNull(emailAuthBinding.email.getText()).toString().isEmpty()){
            emailAuthBinding.emailLayout.setError(getString(R.string.email_cant_be_empty));
            return false;
        }
        else if (Objects.requireNonNull(emailAuthBinding.password.getText()).toString().isEmpty()){
            emailAuthBinding.passLayout.setError(getString(R.string.password_cant_be_empty));
            return false;
        }
        else {
            if (emailAuthBinding.password.getText().toString().length() >= 6) {
                return true;
            }
            emailAuthBinding.passLayout.setError(getString(R.string.minimum_length_should_be_6));
            return false;
        }

    }


    private void goInMainActivity(){
        startActivity(new Intent(EmailAuthActivity.this,MainActivity.class));
        finish();
    }
    private void showProgressDialog() {
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    private void hideProgressDialog() {
        progressDialog.dismiss();
    }

}