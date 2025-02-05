package com.example.autoquest_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home_page extends AppCompatActivity {

    FirebaseAuth auth;
    Button logoutButton;
    Button submitbutton;
    TextView textView;
    EditText carMake, carModel, carYear;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);

        auth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logoutBtn);
        textView = findViewById(R.id.HomeText);
        submitbutton = findViewById(R.id.submitButton);
        user = auth.getCurrentUser();
        carMake = findViewById(R.id.makeInput);
        carModel = findViewById(R.id.modelInput);
        carYear = findViewById(R.id.yearInput);


        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            textView.setText(user.getEmail());
        }

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String make, model, year;

                make =  carMake.getText().toString().trim();
                model = carModel.getText().toString().trim();
                year = carYear.getText().toString().trim();

                if (TextUtils.isEmpty(make)) {
                    Toast.makeText(Home_page.this, "Please enter the car's make.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(model)) {
                    Toast.makeText(Home_page.this, "Please enter the car's model.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(year)) {
                    Toast.makeText(Home_page.this, "Please enter the car's year.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(Home_page.this, AfterHomePage.class);
                intent.putExtra("make", make);
                intent.putExtra("model", model);
                intent.putExtra("year", year);
                startActivity(intent);
                finish();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}