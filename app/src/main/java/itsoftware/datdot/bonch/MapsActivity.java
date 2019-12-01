package itsoftware.datdot.bonch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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

import itsoftware.datdot.bonch.data.workers.Target;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    boolean isFirst = true;
    private GoogleMap mMap;
    private FirebaseFirestore db;
    private ArrayList<Target> targets;
    private LocationManager locationManager;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        db = FirebaseFirestore.getInstance();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setDrawerLayout();
    }

    private void setDrawerLayout() {
        String[] mScreenTitles = getResources().getStringArray(R.array.screen_array);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        ListView mDrawerList = findViewById(R.id.left_drawer);
        mDrawerList.setBackgroundColor(Color.DKGRAY);
        mDrawerList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_list_item, mScreenTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        Toolbar toolbar = findViewById(R.id.toolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, 0, 1) {
            public void onDrawerClosed(View view) {
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                supportInvalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkGeo()) {
            ActivityCompat.requestPermissions(MapsActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        if (isGeoDisabled()) {
            showAlertDialog();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, QuestionsActivity.class);
                break;
            case 1:
                intent = new Intent(this, UsersActivity.class);
                break;
            default:
                break;
        }
        if (intent != null) startActivity(intent);
    }

    private boolean checkGeo() {
        return ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED;
    }

    private void getCurrentLocation(Location location) {
        try {
            Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
            Address address = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1).get(0);
            String cityAddress = address.getLocality().toLowerCase(Locale.ENGLISH);
            getTargets(cityAddress, location);
        } catch (Exception ignored) {
        }
    }

    public void getTargets(String cityAddress, final Location location) {
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
//                                if (targets != null) findTheNearestPoint(location);
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

        if (isFirst) {
            isFirst = false;
            double latitude = getIntent().getDoubleExtra("latitude", -666);
            double longitude = getIntent().getDoubleExtra("longitude", 666);

            if (latitude != -666 & longitude != 666) {
                ProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.INVISIBLE);

                LatLng there = new LatLng(latitude, longitude);
                CameraPosition.Builder builder = new CameraPosition.Builder();
                builder.target(there);
                builder.zoom(17);
                builder.tilt(65);
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(builder.build()));
            }
        }
        getCurrentLocation(location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setIndoorEnabled(false);
        mMap.setTrafficEnabled(false);
        mMap.setMyLocationEnabled(true);
        mMap.setBuildingsEnabled(true);
    }

    private void findTheNearestPoint(Location location) {
        boolean isFirst = true;
        double minLength = 0;
        for (Target target : targets) {
            double x_1 = target.getLocation().getLatitude();
            double y_1 = target.getLocation().getLongitude();
            double x_2 = location.getLatitude();
            double y_2 = location.getLongitude();
            if (isFirst) {
                isFirst = false;
                minLength = Math.sqrt(Math.pow(x_2 - x_1, 2) + Math.pow(y_2 - y_1, 2));
            } else {
                double nextLength = Math.sqrt(Math.pow(x_2 - x_1, 2) + Math.pow(y_2 - y_1, 2));
                if (nextLength < minLength) minLength = nextLength;
            }
        }
        if (minLength < 0.001) {
            if (isInArea(location)) {
                startActivity(new Intent(this, QuestionsActivity.class));
                finish();
            }
        }
    }

    private boolean isInArea(Location location) {
        boolean isFirst = true;
        double min_x = 0;
        double max_x = 0;
        double min_y = 0;
        double max_y = 0;

        for (Target target : targets) {
            if (isFirst) {
                isFirst = false;
                min_x = target.getLocation().getLatitude();
                max_x = target.getLocation().getLatitude();
                min_y = target.getLocation().getLongitude();
                max_y = target.getLocation().getLongitude();
            } else {
                double next_x = target.getLocation().getLatitude();
                double next_y = target.getLocation().getLongitude();
                if (next_x < min_x) {
                    min_x = next_x;
                } else if (next_x > max_x) max_x = next_x;
                if (next_y < min_y) {
                    min_y = next_y;
                } else if (next_y > max_y) max_y = next_y;
            }
        }
        double current_X = location.getLatitude();
        double current_Y = location.getLongitude();
        return current_X >= min_x && current_X <= max_x && current_Y >= min_y && current_Y <= max_y;
    }
}
