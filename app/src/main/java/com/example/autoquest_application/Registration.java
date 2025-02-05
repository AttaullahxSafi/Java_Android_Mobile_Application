package com.example.autoquest_application;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {
    // Variable declaration
    TextInputEditText editTextFirstname, editTextSurname, editTextEmail, editTextMobileNo, editTextPassword;
    Button callLogin, createAccount;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), Home_page.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        // Initialize elements
        mAuth = FirebaseAuth.getInstance();
        editTextFirstname = findViewById(R.id.firstname);
        editTextSurname = findViewById(R.id.surname);
        editTextEmail = findViewById(R.id.emailReg);
        editTextMobileNo = findViewById(R.id.mobileNO);
        editTextPassword = findViewById(R.id.passwordReg);
        createAccount = findViewById(R.id.signupbtn);
        callLogin = findViewById(R.id.gobackbtn);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname, surname, email, mobileNo, password;
                firstname = editTextFirstname.getText().toString().trim();
                surname = editTextSurname.getText().toString().trim();
                mobileNo = editTextMobileNo.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(firstname)) {
                    Toast.makeText(Registration.this, "Please enter your firstname.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(surname)) {
                    Toast.makeText(Registration.this, "Please enter your surname.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobileNo)) {
                    Toast.makeText(Registration.this, "Please enter your mobile number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registration.this, "Please enter your email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registration.this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Account created.", Toast.LENGTH_SHORT).show();
                                    mAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> signInTask) {
                                                    if (signInTask.isSuccessful()) {
                                                        Intent intent = new Intent(Registration.this, Home_page.class);
                                                        startActivity(intent);
                                                        finish();
                                                    } else {
                                                        Toast.makeText(Registration.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(Registration.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        // Go to the login page from signup
        callLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
