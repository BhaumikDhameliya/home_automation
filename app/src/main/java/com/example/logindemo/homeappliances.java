package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class homeappliances extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Switch s1,s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeappliances);
        logout = (Button)findViewById(R.id.logoutbtn);
            firebaseAuth = FirebaseAuth.getInstance();
//
//        logout = (Button)findViewById(R.id.btnLogout);
//
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        s1 = (Switch)findViewById(R.id.tvSwitch);
        s2 = (Switch)findViewById(R.id.acSwitch);
//        test = (Button)findViewById(R.id.testbtn);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("led1");
                if(isChecked){
                    myRef.setValue(1);
                }
                else {
                    myRef.setValue(0);
                }
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("led2");
                if(isChecked){
                    myRef.setValue(1);
                }
                else {
                    myRef.setValue(0);
                }
            }
        });
//        test.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseDatabase database = FirebaseDatabase.getInstance();
//                DatabaseReference myRef = database.getReference("led1");
//                myRef.setValue(1);
//            }
//        });
    }
//
    private void logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(homeappliances.this, MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu); // give value to the menu variable
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}