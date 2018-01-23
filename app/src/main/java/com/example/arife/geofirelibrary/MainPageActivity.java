package com.example.arife.geofirelibrary;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainPageActivity extends AppCompatActivity implements GeoQueryEventListener {


    TextView enteredText;
    TextView exitedText;
    EditText newLatitudeText;
    EditText newLongitudeText;
    Button newLocationButton;
    EditText newDistanceText;


    GeoQuery geoQuery;
    GeoFire geoFire;
    DatabaseReference databaseReference;
    GeoLocation geoLocation;
    double radius;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);

        enteredText = findViewById(R.id.enteredText);
        exitedText = findViewById(R.id.exitText);
        newLatitudeText = findViewById(R.id.newLatitudeText);
        newLongitudeText = findViewById(R.id.newLongitudeText);
        newLocationButton = findViewById(R.id.newLocationButton);
        newDistanceText = findViewById(R.id.distanceText);



        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        //geofire a userlarımızı atayacağız böylelikle geoQuery bu userları kontrol edecek
        geoFire = new GeoFire(databaseReference);
        geoLocation = new GeoLocation(37.000350,37.00020);
        radius = 0.5; //km

        newLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                geoLocation = new GeoLocation(Double.parseDouble(String.valueOf(newLatitudeText.getText())), Double.parseDouble(String.valueOf(newLongitudeText.getText())));
                radius = Double.parseDouble(String.valueOf(newDistanceText.getText()));
                //center noktamızı değiştiriyoruz
                geoQuery.setCenter(geoLocation);
                geoQuery.setRadius(radius);
            }
        });

        //centerımız geoLocation diyerek belirlediğimiz şimdilik default değerimiz
        // alanımızın boyutu radiusu belirlediğimiz çember
        geoQuery = geoFire.queryAtLocation(geoLocation,radius);
        //oluşturduğumuz alanı dinliyoruz yukarıda bunu implement etmemiz gerek unutmayın!
        geoQuery.addGeoQueryEventListener(MainPageActivity.this);


    }



    @Override
    public void onKeyEntered(String key, GeoLocation location) {
        //çemberin içinde kalan userlar
        String s = key+" is in the area";
        enteredText.setText(s);
    }

    @Override
    public void onKeyExited(String key) {
        //çemberin dışında kalan userlar
        String s = key+ " is out of the area";
        exitedText.setText(s);
    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {

    }

    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("There was an unexpected error querying GeoFire: " + error.getMessage())
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
