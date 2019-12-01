package itsoftware.datdot.bonch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import itsoftware.datdot.bonch.data.workers.CurrentState;
import itsoftware.datdot.bonch.data.workers.Target;

public class RecommendationActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private ArrayList<Target> targets;
    private static final String TAG = "RecommendationActivity";
    private FirebaseAuth mAuth;

    private CustomAdapter adapter;

    private String city = "sankt-peterburg"; // получить из мапс активити

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getTargets(city);
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
                        setList(targets);
                    }
                });
    }

    private void setList(ArrayList<Target> targets) {
        ListView listView = findViewById(R.id.listView);
        CustomAdapter adapter = new CustomAdapter(this, targets);

        listView.setAdapter(adapter);
        final RecommendationActivity self = this;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String targetid = ((TextView) v.findViewById(R.id.targetid)).getText().toString();
                Target target = getTargetById(targetid);
                //отправить в мапс таргет

                Intent intent = new Intent(self, MapsActivity.class);
                intent.putExtra("latitude", target.getLocation().getLatitude());
                intent.putExtra("longitude", target.getLocation().getLongitude());
                startActivity(intent);
                finish();
            }
        });
    }

    private Target getTargetById(String id) {
        for (Target target : targets) {
            if (target.getId().equals(id)) return target;
        }
        return new Target();
    }

}
