package itsoftware.datdot.bonch;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG = "MapsActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mAuth = FirebaseAuth.getInstance();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Log.d(TAG, mAuth.getCurrentUser().getEmail());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isGeoDisabled()) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        AlertDialog turnOnGeo = new AlertDialog.Builder(MapsActivity.this)
                .setTitle(getString(R.string.turn_on_geo))
                .setMessage(getString(R.string.turn_on_message))
                .setPositiveButton(getResources().getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(android.provider.
                                        Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton(getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                .create();
        turnOnGeo.show();
    }

    public boolean isGeoDisabled() {
        LocationManager mLocationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);
        boolean mIsGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean mIsNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        return !mIsGPSEnabled && !mIsNetworkEnabled;
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                10000, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                10000, 10, locationListener);
        locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER,
                10000, 10, locationListener);
    }

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void showLocation(Location location) {
        if (location == null || mMap == null) return;

        LatLng there = new LatLng(location.getLatitude(), location.getLongitude());
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.zoom(17);
        builder.tilt(65);
        builder.target(there);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setIndoorEnabled(false);
        mMap.setTrafficEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);
    }
}
