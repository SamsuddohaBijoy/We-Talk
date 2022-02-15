package com.bijoy.wetalk.Activites;

import static androidx.appcompat.app.AlertDialog.*;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private static Object builder;
    TextInputEditText user_text, user_number, user_email, user_password;

    AppCompatButton register_btn;

    TextView have_text;

    FirebaseAuth firebaseAuth;

    ProgressDialog dialog;

    DatabaseReference databaseReference;

    FirebaseUser firebaseUser;

    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();


        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        dialog = new ProgressDialog(RegisterActivity.this);
        dialog.setTitle("Please Wait...");

        user_text = findViewById(R.id.user_text);
        user_number = findViewById(R.id.user_number);
        user_email = findViewById(R.id.user_email);
        user_password = findViewById(R.id.user_password);
        register_btn = findViewById(R.id.register_btn);
        have_text = findViewById(R.id.have_text);

        have_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                finish();

            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = user_text.getText().toString().trim();
                String number = user_number.getText().toString().trim();
                String email = user_email.getText().toString().trim();
                String password = user_password.getText().toString().trim();

                if (name.isEmpty()) {


                    ShowAlert("Name field can not be empty !");

                }else if (number.isEmpty()){
                    ShowAlert("Number field can not be empty !");
                }

                else if (email.isEmpty()){
                    ShowAlert("Email field can not be empty !");
                }
                else if (password.isEmpty()){
                    ShowAlert("Password field can not be empty !");
                }else if (password.length()<6){
                    ShowAlert("Password should be more then 6 !");
                }else {
                    dialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            firebaseUser = firebaseAuth.getCurrentUser();
                            if (firebaseUser!=null){
                              currentUser =  firebaseUser.getUid();
                            }
                            dialog.dismiss();
                            if (task.isSuccessful()) {

                                HashMap<String, String> userMap = new HashMap<>();

                                userMap.put("UserName", name);
                                userMap.put("UserEmail", email);
                                userMap.put("UserNumber", number);
                                userMap.put("UserPassword", password);
                                userMap.put("UserId", currentUser);

                                databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "This account create successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, SignInActivity.class));
                                            finish();
                                        }
                                    }
                                });

                            }else {
                                String err = task.getException().getLocalizedMessage();
                                ShowAlert(err);

                            }
                        }
                    });

                }





            }
        });
    }

    private void ShowAlert(String s) {

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);

        builder.setTitle("Error");
        builder.setMessage(s);
        builder.setIcon(R.drawable.ic_baseline_error_24);

        builder.setPositiveButton("ok", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
    }

}