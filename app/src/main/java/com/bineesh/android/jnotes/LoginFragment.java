package com.bineesh.android.jnotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


public class LoginFragment extends Fragment {

    LinearLayout googleLoginButton;
    ProgressBar progressBar;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_login, container, false);
        googleLoginButton = view.findViewById(R.id.google_login);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        googleLoginButton.setOnClickListener(v->{
            googleSignIn();
        });
        return view;
    }

    private void googleSignIn(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();
//        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getContext(),googleSignInOptions);
//        Intent signInIntent = googleSignInClient.getSignInIntent();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        resultLauncher.launch(signInIntent);

    }

    private final ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            progressBar.setVisibility(View.VISIBLE);
            if(result.getResultCode() == Activity.RESULT_OK){
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData())
                        .addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                            @Override
                            public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                                if(task.isSuccessful()){
                                    System.out.println("inside task success");
                                    Toast.makeText(getContext(),"Signed in Successfully",Toast.LENGTH_LONG).show();
                                    try {
                                        GoogleSignInAccount account = task.getResult(ApiException.class);
                                        String userName = account.getEmail();
                                        System.out.println(userName);
                                        SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                        sharedPreferences.edit().putString("user_name",userName).apply();
                                        progressBar.setVisibility(View.VISIBLE);
                                        ((AppCompatActivity)getContext()).getSupportFragmentManager()
                                                .beginTransaction().replace(R.id.main_frame,new NotesFragment()).commit();
                                    } catch (ApiException e) {
                                        throw new RuntimeException(e);
                                    }
                                }else{
                                    System.out.println("inside task else");
                                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().putString("user_name","google").apply();
                                    progressBar.setVisibility(View.VISIBLE);
                                    ((AppCompatActivity)getContext()).getSupportFragmentManager()
                                            .beginTransaction().replace(R.id.main_frame,new NotesFragment()).commit();
                                }
                            }
                            });

            }
            SharedPreferences sharedPreferences = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("user_name","google").apply();
            progressBar.setVisibility(View.VISIBLE);
            ((AppCompatActivity)getContext()).getSupportFragmentManager()
                    .beginTransaction().replace(R.id.main_frame,new NotesFragment()).commit();
        }
    });

}