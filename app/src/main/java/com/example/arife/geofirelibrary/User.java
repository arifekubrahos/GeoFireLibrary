package com.example.arife.geofirelibrary;

/**
 * Created by Arife on 19.01.2018.
 */

import com.firebase.geofire.GeoLocation;

public class User {
    String userName;
    double userLongitude; // boylam
    double userLatitude;  // enlem

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getUserLongitude() {
        return userLongitude;
    }

    public void setUserLongitude(double userLongitude) {
        this.userLongitude = userLongitude;
    }

    public double getUserLatitude() {
        return userLatitude;
    }

    public void setUserLatitude(double userLatitude) {
        this.userLatitude = userLatitude;
    }



}
