package com.example.arife.geofirelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddUserActivity extends AppCompatActivity {

    Button addUserButton;
    EditText userNameText;
    EditText userLatitudeText;
    EditText userLongitudeText;
    Button goMainPageButton;
    DatabaseReference db;
    GeoFire geoFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

        addUserButton = findViewById(R.id.addUserButton);
        userNameText = findViewById(R.id.userNameText);
        userLatitudeText = findViewById(R.id.userLatitudeText);
        userLongitudeText = findViewById(R.id.userLongitudeText);
        goMainPageButton = findViewById(R.id.mainPageButton);

        //database referası belirledik altıa user diye bir key koyduk ve her user ı bir value olarak alacak
        db = FirebaseDatabase.getInstance().getReference().child("Users");
        //verilerimizi geofire kullanarak database yazacağız referans
        // adresimizi geofire ımaza koyduk oraya ekleme yapacağız
        geoFire = new GeoFire(db);


        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(userNameText.getText()) != null && String.valueOf(userLatitudeText.getText()) != null &&
                        String.valueOf(userLongitudeText.getText()) != null) {
                    User u = new User();
                    u.setUserName(userNameText.getText().toString());
                    u.setUserLatitude(Double.parseDouble(userLatitudeText.getText().toString()));
                    u.setUserLongitude(Double.parseDouble(userLongitudeText.getText().toString()));
                    //setLocation methodu ile database e ekleme yapıyoruz key userımız ismi olacak
                    // userımızın konum bilgilerini de location bilgisi şekliyle kaydedeceğiz
                    geoFire.setLocation(u.getUserName(), new GeoLocation(u.getUserLatitude(), u.getUserLongitude()));
                    Toast.makeText(getApplicationContext(),"writing is successful",Toast.LENGTH_LONG).show();
                } else
                    System.out.print("one is null");
            }
        });

        goMainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainPageActivity.class));
                finish();
            }
        });


    }
}
