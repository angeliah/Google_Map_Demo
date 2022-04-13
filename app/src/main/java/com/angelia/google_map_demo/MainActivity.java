package com.angelia.google_map_demo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private SupportMapFragment mapFragment;
    private GoogleMap googleMap;
    public Switch show_location_switch;



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

    }
    //End of OnCreate Method

    //For getting user permission
    private void requestPermission() {
        if (show_location_switch.isChecked())
        {
            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                //We will show the location

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
                //Resume
            }

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}