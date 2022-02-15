package com.bijoy.wetalk.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bijoy.wetalk.R;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    AppCompatButton register_btn,signin_btn;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        register_btn= findViewById(R.id.register_btn) ;
        signin_btn = findViewById(R.id.signin_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartActivity.this,RegisterActivity.class));
                finish();
            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(StartActivity.this,MainActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){

            startActivity(new Intent(StartActivity.this,MainActivity.class));


            finish();

        }else{
            startActivity(new Intent(StartActivity.this,SignInActivity.class));
            finish();
        }
    }
}