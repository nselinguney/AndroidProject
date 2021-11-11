package com.example.pedometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUpRegistBtn;

    String nameLastNameString;
    String emailString;
    String passwordString;

    EditText nameLastName;
    EditText eMail;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_sign_up);

        signUpRegistBtn = findViewById(R.id.kayitBtn);

        nameLastName = findViewById(R.id.nameLastNameSignUpEditText);
        eMail = findViewById(R.id.emailSignUpEditText);
        password = findViewById(R.id.passwordSignUpEditText);

        nameLastNameString = nameLastName.getText().toString();
        emailString = eMail.getText().toString();
        passwordString = password.getText().toString();

        signUpRegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                nameLastNameString = nameLastName.getText().toString();
                emailString = eMail.getText().toString();
                passwordString = password.getText().toString();

                SharedPreferences MyPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = MyPrefs.edit();

                editor.putString("adSoyad", nameLastNameString); //string değer ekleniyor
                editor.putString("eMail", emailString);
                editor.putString("sifre", passwordString);
                editor.apply(); //Kayıt

                //String adSoyadsP = MyPrefs.getString("adSoyad", "");
                //Log.e( "adSoyadsP: ",adSoyadsP);

                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);

                //sifreString = sifre.getText().toString();

                if (nameLastNameString.isEmpty() || emailString.isEmpty() || passwordString.isEmpty()) { //alınan değerlerin boş olup olmaması kontrol ediliyor

                    Toast.makeText(getApplicationContext(), "Tüm Alanları Doldurunuz", Toast.LENGTH_LONG).show();

                } else { //değerler boş değilse

                    //createEmployeeTable();
                    //addUser();
                    //kisiEkle();
                    startActivity(intent);

                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }

}