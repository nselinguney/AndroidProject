package com.example.pedometer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    EditText nameLastName;
    EditText eMail;
    EditText password;

    String nameLastNameString;
    String emailString;
    String passwordString;

    //input controller flags
    Boolean nullCheckFlag= false;
    Boolean matchCheckFlag=false;
    Boolean flag=false;

    //giriş buton tanımlama
    Button loginBtn;
    Button signUpBtn;
    Button signUpRegistBtn;

    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        //Realm.init(this);
        loginBtn = findViewById(R.id.logInButton);
        signUpBtn = findViewById(R.id.signUpButton);
        signUpRegistBtn =findViewById(R.id.kayitBtn);

        nameLastName = findViewById(R.id.nameLastNameEditText);
        eMail = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //tvWelcome = findViewById(R.id.tvWelcome);

                nameLastNameString = nameLastName.getText().toString();
                emailString = eMail.getText().toString();
                passwordString = password.getText().toString();

                nullCheckFlag = nullCheck(nameLastNameString, emailString, passwordString);
                matchCheckFlag = matchCheck(nameLastNameString, emailString, passwordString);

                /*Log.e("adSoyad", adSoyadString);
                Log.e("email", emailString);
                Log.e("sifre", sifreString); */
                // todo edittexten mail ve şifre al fonksiyonda kontrol et

                //Toast.makeText(getApplicationContext(),"Bilgilendirme mesajı",Toast.LENGTH_LONG).show();
                flag=inputControl();

                Intent intent = new Intent(MainActivity.this, LogInActivity.class);

                if (flag) {
                    startActivity(intent);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }


    // girilen bilgilerin boş olup olmadığını kontrol eder.
    public boolean nullCheck(String text, String text2, String text3){

        return text.isEmpty() || text2.isEmpty() || text3.isEmpty();
    }

    // girilen bilgilerin veritabanıyla eşleşip eşleşmediğini kontrol eder.
    public boolean matchCheck(String text, String text2, String text3) {

        SharedPreferences MyPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        String adSoyadsP = MyPrefs.getString("adSoyad", "");
        String eMailsP = MyPrefs.getString("eMail", "");
        String sifreSp = MyPrefs.getString("sifre", "");

        Log.e("adSoyadSp", adSoyadsP);

        return text.equalsIgnoreCase(adSoyadsP) && text2.equalsIgnoreCase(eMailsP) && text3.equalsIgnoreCase(sifreSp);

    }

    // inputların bos ya da yanlıs olup olmadıgını döndürür.
    public boolean inputControl() {


        if (nullCheckFlag) {
            Toast.makeText(getApplicationContext(), "Lütfen bilgileri eksiksiz giriniz.", Toast.LENGTH_LONG).show();
            return false;
        } else {

            if (!matchCheckFlag) {
                Toast.makeText(getApplicationContext(), "Girdiğiniz bilgiler sistemdeki bilgilerinizle eşleşmiyor. Tekrar deneyiniz", Toast.LENGTH_LONG).show();
                return false;
            } else  {
                Toast.makeText(getApplicationContext(), "Giriş yapılıyor...", Toast.LENGTH_LONG).show();
                return true;
            }
        }
    }
}