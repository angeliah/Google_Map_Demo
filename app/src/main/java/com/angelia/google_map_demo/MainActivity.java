package com.angelia.google_map_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap map;
    public Switch show_location_switch;
    private FusedLocationProviderClient fused_location_provider_client;
    public Location my_location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.fragment);
        mapFragment.getMapAsync(this::onMapReady);
        show_location_switch = findViewById(R.id.switch_id);



        show_location_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Requesting permission to access location
                requestPermission();

            }
        });

        fused_location_provider_client = LocationServices.getFusedLocationProviderClient(this);


    }
    //End of OnCreate Method

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);



    }

    //For getting user permission
    private void requestPermission() {
        if (show_location_switch.isChecked())
        {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                //We will show the location
                showLocation();

            }

            else {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]
                        {
                        Manifest.permission.ACCESS_FINE_LOCATION

                },26 );

            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==26)
        {
            if (grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                //Showing location

                showLocation();

                }

            }

        }

    @SuppressLint("MissingPermission")
    private void showLocation() {
        fused_location_provider_client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task)
            {

                if (task.isSuccessful())
                {
                    my_location = task.getResult();
                    if (task != null)
                    {
                        LatLng latiLongi = new LatLng(my_location.getLongitude(), my_location.getLatitude());

                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latiLongi, 30));

                    }




                }

            }
        });


    }
}


//location interval,minimal battery consumption,add a marker,find the geocoordinates for home and show a marker for your home and lastlocatio
