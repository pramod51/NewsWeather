package com.wether.news.ViewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wether.news.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginSignupViewModel extends ViewModel {
    MutableLiveData<FirebaseUser> mutableLiveData=new MutableLiveData<>();
    MutableLiveData<String> stringMutableLiveData=new MutableLiveData<>();

    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    public LiveData<FirebaseUser> onSuccess(){
        return mutableLiveData;
    }
    public LiveData<String> onFailure(){
        return stringMutableLiveData;
    }


    public void emailPasswordAuthSignUP(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LoginSignupViewModel.class.getSimpleName(), "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Map<String, String> map=new HashMap<>();
                        map.put(Constants.EMAIL,email);
                        map.put(Constants.PASSWORD,password);
                        db.collection("Users").document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                .set(map).addOnSuccessListener(unused -> {
                                    //goInMainActivity();
                                    mutableLiveData.setValue(user);
                                });

                    }else if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        //emailInputLayout.setError("");
                        stringMutableLiveData.postValue("Email Already Used");
                        mutableLiveData.postValue(null);
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Log.e(LoginSignupViewModel.class.getSimpleName(), "createUserWithEmail:failure", task.getException());

                        stringMutableLiveData.postValue("Authentication failed.");
                        mutableLiveData.postValue(null);

                    }

                });
    }

    public void emailPasswordAuthSignIN(String email,String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LoginSignupViewModel.class.getSimpleName(), "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        mutableLiveData.postValue(user);

                    } else if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                        //invalid pass

                        stringMutableLiveData.postValue("Wrong password entered");
                        mutableLiveData.postValue(null);
                    }
                    else if(task.getException() instanceof FirebaseAuthInvalidUserException){
                        //Email not in used

                        stringMutableLiveData.postValue("Wrong Email entered");
                        mutableLiveData.postValue(null);

                    }
                    else {

                        stringMutableLiveData.postValue("Authentication failed");
                        mutableLiveData.postValue(null);
                    }
                });
    }

}
