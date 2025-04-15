package com.example.myapplication;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity implements SensorEventListener {
    Button go;
    ImageButton add;
    EditText deck;
    TextView card;

    Sensor MySensor;
    SensorManager sensorManager;
    MediaPlayer mediaPlayer;
    Random random = new Random();
    List<String> sample = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        // Initialize UI elements properly
        go = findViewById(R.id.ownStart);
        add = findViewById(R.id.add);
        deck = findViewById(R.id.deck);
        card = findViewById(R.id.card);
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        MySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (MySensor != null) {
            sensorManager.registerListener(this, MySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        // Apply window insets (this should only be used for padding)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button click listeners
        add.setOnClickListener(v -> {
            String text = deck.getText().toString().trim(); // Trim whitespace
            if (!text.isEmpty()) {
                sample.add(text);
                deck.setText(""); // Clear input field

            }
        });



        go.setOnClickListener(v -> {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            add.setVisibility(View.INVISIBLE);
            go.setVisibility(View.INVISIBLE);
            deck.setVisibility(View.INVISIBLE);
            card.setVisibility(View.VISIBLE);

            System.out.println("DEBUG: Sample size -> " + sample.size()); // Log size of sample list

            if (!sample.isEmpty()) {

            } else {
                card.setText("No data added!");
            }
        });

    }

        @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            handleShake(event);
        }
    }

    private void handleShake(SensorEvent event) {
        float xVal = event.values[0];
        float yVal = event.values[1];
        float zVal = event.values[2];

        float acceleration = (xVal * xVal + yVal * yVal + zVal * zVal) /
                (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

        if (acceleration >= 2) {
            System.out.println("DEBUG: Shake detected - Sample size: " + sample.size());

            if (!sample.isEmpty()) {
                int choice = random.nextInt(sample.size());
                String selectedText = sample.get(choice);

                runOnUiThread(() -> {
                    card.setText(selectedText);
                    card.setTextColor(Color.GREEN);

                });
            } else {
                runOnUiThread(() -> card.setText("No data available!"));
            }

            // Play sound without overlap
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
                mediaPlayer.start();
            }
        }
    }




    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No action needed
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MySensor != null) {
            sensorManager.registerListener(this, MySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("sampleList", new ArrayList<>(sample));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<String> restoredSample = savedInstanceState.getStringArrayList("sampleList");
        if (restoredSample != null) {
            sample.addAll(restoredSample);
        }
    }

}
