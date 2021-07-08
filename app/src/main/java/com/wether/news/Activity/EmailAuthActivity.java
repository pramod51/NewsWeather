package com.wether.news.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wether.news.R;

import java.util.HashMap;
import java.util.Map;

public class EmailAuthActivity extends AppCompatActivity {
    private TextInputLayout emailInputLayout,passwordInputLayout;
    private TextInputEditText email,password;
    private MaterialButton signIn,signUp;
    private final FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAuth.getCurrentUser()!=null){
            goInMainActivity();
            return;
        }
        setContentView(R.layout.activity_email_auth);
        initViews();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidCredentials()){
                    if (signIn.getText().toString().equals("Sign In")){
                        emailPasswordAuthSignIN(email.getText().toString(),password.getText().toString());
                    }
                    else{
                        emailPasswordAuthSignUP(email.getText().toString(),password.getText().toString());
                    }
                }

            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signUp.getText().toString().equals("Sign Up")){
                    signIn.setText("Sign UP");
                    signUp.setText("Sign In");
                }
                else{
                    signIn.setText("Sign In");
                    signUp.setText("Sign Up");
                }
            }
        });



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
        if (email.getText().toString().isEmpty()){
            emailInputLayout.setError("Email can't be empty");
            return false;
        }
        else if (password.getText().toString().isEmpty()){
            passwordInputLayout.setError("Password can't be empty");
            return false;
        }
        else if (password.getText().toString().length()<6){
            passwordInputLayout.setError("minimum password length should be 6");
            return false;
        }

        return true;
    }

    private void emailPasswordAuthSignUP(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Map<String, String> map=new HashMap<>();
                            map.put("email",email);
                            map.put("pass",password);
                            db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                    .set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    goInMainActivity();
                                }
                            });

                        }else if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                            emailInputLayout.setError("Email Already Used");
                        }
                        else {
                            // If sign in fails, display a message to the user.
                            Log.w("tag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(EmailAuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void emailPasswordAuthSignIN(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("tag", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            goInMainActivity();

                        } else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                            //invalid pass
                            passwordInputLayout.setError("Wrong password entered");
                        }
                        else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                            //Email not in used
                            emailInputLayout.setError("Wrong Email entered");

                        }
                        else {
                            Toast.makeText(EmailAuthActivity.this,"Authentication failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void goInMainActivity(){
        startActivity(new Intent(EmailAuthActivity.this,MainActivity.class));
        finish();
    }


}