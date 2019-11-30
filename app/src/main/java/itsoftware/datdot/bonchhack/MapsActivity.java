package itsoftware.datdot.bonchhack;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

import itsoftware.datdot.bonchhack.data.workers.Target;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    boolean isFirst = true;
    private GoogleMap mMap;
    private FirebaseFirestore db;
    private ArrayList<Target> targets;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        db = FirebaseFirestore.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isGeoDisabled()) {
            showAlertDialog();
        }
    }

    private void getCurrentLocation(double lat, double lon) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
            Address address = geocoder.getFromLocation(lat, lon, 1).get(0);
            String cityAddress = address.getLocality().toLowerCase(Locale.ENGLISH);
            getTargets(cityAddress);
        } catch (Exception ignored) {
        }
    }

    public void getTargets(String cityAddress) {
        targets = new ArrayList<>();
        db.collection("cities").document(cityAddress)
                .collection("targets").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document :
                                queryDocumentSnapshots.getDocuments()) {
                            targets.add(document.toObject(Target.class));
                            for (Target target : targets) {
                                LatLng latLng = new LatLng(
                                        target.getLocation().getLatitude(),
                                        target.getLocation().getLongitude());
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(latLng);
                                markerOptions.title(target.getName());
                                mMap.addMarker(markerOptions);
                            }
                        }
                    }
                });
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
        if (isFirst) {
            isFirst = false;
            getCurrentLocation(there.latitude, there.longitude);
        }
        CameraPosition.Builder builder = new CameraPosition.Builder();
        builder.zoom(17);
        builder.tilt(65);
        builder.target(there);
        builder.bearing(location.getBearing());
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
