package com.bijoy.wetalk.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bijoy.wetalk.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    TextInputEditText user_email,user_password;

    AppCompatButton signin_btn;

    TextView no_have_text;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        firebaseAuth = FirebaseAuth.getInstance();

        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        signin_btn = findViewById(R.id.signin_btn);
        no_have_text =findViewById(R.id.no_have_text);

        no_have_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,RegisterActivity.class));
                finish();

            }
        });

        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();

                if (email.isEmpty()) {

                    ShowAlert("")

                }
                else if (password.isEmpty()){
                    ShowAlert(s:"Password field can not be empty !");
                }else if (password.length()<6){
                    ShowAlert(s:"Password should be more then 6 !");
                }else {
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignInActivity.this, "Sign in successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        private void ShowAlert(String s) {

            AlertDialog.Builder builder = new AlertDialog.Builder(SignInActivity.this);

            builder.setTitle("Error");
            builder.setMessage(s);
            builder.setIcon(R.drawable.ic_baseline_error_24);

            builder.setPositiveButton(text:"ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        }

    }