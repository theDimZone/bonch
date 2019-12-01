package itsoftware.datdot.bonch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import itsoftware.datdot.bonch.data.workers.Target;
import itsoftware.datdot.bonch.data.workers.User;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser fb_user;
    private FirebaseFirestore fs;
    private User user;
    final String TAG = "PROFILE";
    private String cityAddress = "sankt-peterburg"; // получить из мапс активити

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fs = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        fb_user = mAuth.getCurrentUser();


        fs.collection("users")
                .whereEqualTo("id", fb_user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    setProfile(document.toObject(User.class));
                                }

                        }
                    }
                });
    }

    private void setProfile(User fetched) {
        this.user = fetched;

        ((TextView) findViewById(R.id.nickname)).setText(this.user.getNickname());
        String level = String.valueOf(this.user.getExperience() / 100);
        ((TextView) findViewById(R.id.level)).setText(level);

        final ArrayList<Target> targets = new ArrayList<Target>();

        fs.collection("cities").document(this.cityAddress)
                .collection("targets").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "target");
                                targets.add(document.toObject(Target.class));
                            }
                            setVisited(targets);
                        }
                    }
                });


        
    }

    private void setVisited(ArrayList<Target> fetched) {
        ListView listView = findViewById(R.id.visited);

        ArrayList<Target> targets = new ArrayList<Target>();
        ArrayList<String> ids = this.user.getVisited_target();
        for(Target target: fetched) {
            for(String id : ids) {
                Log.d(TAG, id);
                Log.d(TAG, target.getId());
                if(id.equals(target.getId())) targets.add(target);
            }
        }

        CustomAdapter adapter = new CustomAdapter(this, targets);

        listView.setAdapter(adapter);
    }

}
