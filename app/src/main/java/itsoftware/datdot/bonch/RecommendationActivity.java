package itsoftware.datdot.bonch;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import itsoftware.datdot.bonch.data.workers.Target;

public class RecommendationActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private ArrayList<Target> targets;
    private static final String TAG = "RecommendationActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();
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
                        }

                        for (Target target : targets) {
                            // add targets to table
                            Log.d(TAG, target.getName());
                        }
                    }
                });
    }
}
