package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=5;
    private TextView userregistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.etEmail);
        Password = (EditText) findViewById(R.id.etPaasword);
        Info = (TextView) findViewById(R.id.info);
        Login = (Button) findViewById(R.id.btnLogin);
        userregistration = (TextView)findViewById(R.id.userRegister);
        Info.setText("No. of attempts remaining: 5");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){
            finish();
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(email.getText().toString(), Password.getText().toString());
            }
        });

        userregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this , registrationActivity.class));
            }
        });
    }
    private void validate(String userName,String userPaasword){
        /*if ((userName.equals("Admin")) && (userPaasword.equals("1234"))){
            Intent intent =new Intent(MainActivity.this, SecondActivity.class);
            startActivity(intent);
        }else{
                counter--;

                Info.setText("No. of attempts remaining :"+ String.valueOf(counter));

                if(counter==0){
                    Login.setEnabled(false);

                }
        }*/
        progressDialog.setMessage("It takes some time");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(userName, userPaasword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
//                    Toast.makeText(MainActivity.this, "Login Sucessfull", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(MainActivity.this, SecondActivity.class));
                    checkEmailverification();
                }
                else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No. of attempts remaining: "+counter);
                    if (counter==0){
                        Login.setEnabled(false);
                    }
                }
            }
        });
    }

    private void checkEmailverification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag){
            finish();
//            startActivity(new Intent(MainActivity.this,SecondActivity.class));
            startActivity(new Intent(MainActivity.this,HomeActivity.class));
        }
        else {
            Toast.makeText(this, "Verify your Email", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
