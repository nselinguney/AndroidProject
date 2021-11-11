package com.example.pedometer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity implements SensorEventListener {

    String AdSoyad;
    private TextView txtProgress;

    private SensorManager sensorManager;
    int count = 0;
    boolean activityRunning;
    ProgressBar progressBar;
    final private Handler handler = new Handler();

    PBarClass pBarObj = new PBarClass();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_log_in);

        txtProgress = (TextView) findViewById(R.id.txtProgress);

        progressBar = (ProgressBar)findViewById(R.id.progressBarOfSteps); // initiate the progress bar

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        SharedPreferences MyPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        AdSoyad = MyPrefs.getString("adSoyad", "Kayıt Yok");
        //Log.e("uyeolactivity: ", AdSoyad);

        Toast.makeText(getApplicationContext(), AdSoyad + " Hoş Geldiniz", Toast.LENGTH_LONG).show();

        progressBar = (ProgressBar) findViewById(R.id.progressBarOfSteps);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pBarObj.count <= 50000) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pBarObj.count);
                            txtProgress.setText((pBarObj.count) + "");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    @Override
    public void onSensorChanged (SensorEvent event){
        if (activityRunning) {

            count++;

            pBarObj.count = count;
            Log.e("Count: ", Integer.toString(pBarObj.count));

        }
    }

    @Override
    public void onAccuracyChanged (Sensor sensor, int accuracy){

    }

    @Override
    protected void onResume () {
        super.onResume();

        activityRunning = true;
        Sensor countSensor = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        }
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Count sensor not available!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause () {
        super.onPause();

        activityRunning = false;

    }
}

