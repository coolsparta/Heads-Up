package com.example.myapplication;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView text;
    //TextView x;
    //TextView y;
    //TextView z;
    AutoCompleteTextView editText;
    ImageButton dropdown;
    Button start;
    Button sitch;
    Sensor MySensor;
    SensorManager sensorManager;
   MediaPlayer mediaPlayer;
   int screen = 0;
    private Map<String, String[]> dataMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        text = findViewById(R.id.textmind);
        // x = findViewById(R.id.textView);
        // y = findViewById(R.id.textView2);
        // z = findViewById(R.id.textView3);
        editText = findViewById(R.id.editText);
        dropdown = findViewById(R.id.dropdown);
        start = findViewById(R.id.start);
        sitch = findViewById(R.id.stich);

        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        String input = editText.getText().toString();

        dataMap = new HashMap<>();
        dataMap.put("Cricket", new String[]{ "Kohli", "Rohit", "Tendulkar", "Warner", "Smith", "Bumrah", "Cummins", "Ben Stokes", "Chris Gayle", "ABD"} );
        dataMap.put("Football", new String[]{"Messi", "Ronaldo", "Mbappe", "Neymar", "Haaland", "Hary Kane", "Zidane", "Sergio Ramos" } );
        dataMap.put("Bollywood", new String[]{   "Stree", "Jawan", "Animal", "Hera Pheri", "Tamasha", "Ra One", "Sultan", "YJHD", "ZNMD", "Dhoom", "Krrish", "Dangal", "Andhadhun", "DDLJ", "3 Idiots", "Jab We Met", "Rockstar", "Lakshaya", "Heropanti", "Agneepath", "Barfi", "Dabangg", "Kick", "Ek tha Tiger","12th Fail","Sholay","Lagaan","PK","Om Shanti Om","Happy New Year","Darr","Baazigar","Secret Superstar","War","Singham","Drishyam","Bodyguard","Bala","Dream Girl","Badhaai Ho","Race"} );
        dataMap.put("Songs", new String[]{ "APT", "Perfect", "Cruel Summer", "As it was", "Good 4 u", "Sorry", "Baby", "Despacito", "Faded", "Espresso", "Big Dawgs", "Not Like Us", "Spectre", "Skyfall", "Paradise", "Thunder", "Bones", "Believer", "Attention", "Light Switch", "Calm Down", "Senorita", "Yummy", "Peaches", "Stay", "Levitating", "Diamonds", "Roar", "Titanium", "Company"} );
        dataMap.put("Objects", new String[]{  "Knife", "Pen", "Pencil", "Paper", "Table", "Chair", "Book", "Notebook",
                "Ruler", "Eraser", "Sharpener", "Bag", "Bottle", "Clock", "Lamp",
                "Mirror", "Phone", "Key", "Wallet", "Spectacles"} );
        dataMap.put("Hollywood",new String[]{"Rocky","Gladiator","Dark Knight","Deadpool","Joker","Inception","Interstellar","Oppenheimer","Dunkrik","Star Wars","Top Gun","Harry Potter","Spider Man","Iron Man","Mad Max","Lion King","Despicable Me","Mission Impossible","James Bond","Lord of the Rings","Terminator","Avatar","Die Hard","King Kong","Indiana Jones","Jurassic Park","Jumanjii","Kung fu Panda","Moana","Big Hero 6","Wreck-it Ralph","Shrek","Puss in Boots","Toy Story","Madagascar","Jungle Book","Cars","Monsters Inc.","Frozen","Sonic The HedgeHog","WALL-E"});
        dataMap.put("Games",new String[]{"Brookhaven RP","Blox Fruits","Adopt Me","Tower of Hell","Valorant","Minecraft","Jail Break","God of War","Last of Us","GTA","CS GO","Dota","Uncharted","Cyberpunk","Resident Evil","Arsenal","Temple Run","Subway Surfers","Candy Crush","Angry Birds","Geometry Dash","Call of Duty","Plants vs Zombies","Fruit Ninja","PUBG","Clash of Clans","Among Us","Jetpack Joyride","Pokemon GO","Fall Guys","Fortnite","Stumble Guys","Hill Climb Racing"});


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        MySensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener((SensorEventListener) this, MySensor, SensorManager.SENSOR_DELAY_NORMAL);
        String[] itemArray = getResources().getStringArray(R.array.itemList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line, itemArray);
        editText.setAdapter(arrayAdapter);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.showDropDown();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                editText.setVisibility(View.INVISIBLE);
                dropdown.setVisibility(View.INVISIBLE);
                start.setVisibility(View.INVISIBLE);
                text.setVisibility(View.VISIBLE);
                sitch.setVisibility(View.INVISIBLE);

            }
        });
        sitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screen = 2;
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                startActivity(intent);
            }
        });
    }

   /* String[] Objects = {
          "Knife", "Pen", "Pencil", "Paper", "Table", "Chair", "Book", "Notebook",
           "Ruler", "Eraser", "Sharpener", "Bag", "Bottle", "Clock", "Lamp",
           "Mirror", "Phone", "Key", "Wallet", "Spectacles"
    };

    String[] Cricket = {
            "Kohli", "Rohit", "Tendulkar", "Warner", "Smith", "Bumrah", "Cummins", "Ben Stokes", "Chris Gayle", "ABD"
    };
     String[] Football = {
            "Messi", "Ronaldo", "Mbappe", "Neymar", "Haaland", "Hary Kane", "Zidane", "Sergio Ramos"
    };
    String[] Songs = {
            "APT", "Perfect", "Cruel Summer", "As it was", "Good 4 u", "Sorry", "Baby", "Despacito", "Faded", "Espresso", "Big Dawgs", "Not Like Us", "Spectre", "Skyfall", "Paradise", "Thunder", "Bones", "Believer", "Attention", "Light Switch", "Calm Down", "Senorita", "Yummy", "Peaches", "Stay", "Levitating", "Diamonds", "Roar", "Titanium", "Company"
    };

    String[] Bollywood = {
            "Stree", "Jawan", "Animal", "Hera Pheri", "Tamasha", "Ra One", "Sultan", "YJHD", "ZNMD", "Dhoom", "Krrish", "Dangal", "Andhadhun", "DDLJ", "3 Idiots", "Jab We Met", "Rockstar", "Lakshaya", "Heropanti", "Agneepath", "Barfi", "Dabangg", "Kick", "Ek tha Tiger"
    };
    */

    @Override
    public void onSensorChanged(SensorEvent event) {
       /* String input = editText.getText().toString();
        String[] sample = dataMap.get(input);
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

                Random random = new Random();
                float x_axis = sensorEvent.values[0];
                float y_axis = sensorEvent.values[1];
                float z_axis = sensorEvent.values[2];
                //x.setText("X: "+x_axis);
                //y.setText("Y: "+y_axis);
                //z.setText("Z: "+z_axis);

                if (x_axis < 9.00 && z_axis < -1.00) {
                    text.setTextColor(Color.GREEN);


                    if (sample != null) {
                        int choice = 0;
                        choice = random.nextInt(sample.length);
                        text.setText(sample[choice]);
                    }



                }
                if (x_axis < 5.00 && z_axis > 7.00) {
                    text.setTextColor(Color.RED);
                    if (sample != null) {
                        int choice = 0;
                        choice = random.nextInt(sample.length);
                        text.setText(sample[choice]);
                    }

                }
            }

        */
        if(screen == 0) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                getAccelerometer(event);
            }
        }
        }





    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }




        private void getAccelerometer (SensorEvent event){
            if(screen == 0) {

                Random random = new Random();

                    String[] sample = dataMap.get(editText.getText().toString());
                // Movement
                float xVal = event.values[0];
                float yVal = event.values[1];
                float zVal = event.values[2];


                float accelerationSquareRoot = (xVal * xVal + yVal * yVal + zVal * zVal) /
                        (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

                if (accelerationSquareRoot >= 2) {
                    text.setTextColor(Color.GREEN);


                    if (sample != null && sample.length > 0) {
                        int choice = random.nextInt(sample.length);
                        text.setText(sample[choice]);


                    }

                    if (mediaPlayer != null) {
                        mediaPlayer.start(); // Play the sound
                    }
                }
                if (accelerationSquareRoot <= -2) {
                    text.setTextColor(Color.RED);
                    if (sample != null) {
                        int choice = 0;
                        choice = random.nextInt(sample.length);
                        text.setText(sample[choice]);
                    }
                    if (mediaPlayer != null) {
                        mediaPlayer.start(); // Play the sound
                    }
                }
            }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (sensorManager != null) {
            sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
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
            mediaPlayer.release(); // Release the media player
            mediaPlayer = null;
        }
    }
    }

