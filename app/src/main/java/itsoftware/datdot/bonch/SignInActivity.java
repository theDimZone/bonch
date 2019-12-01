package itsoftware.datdot.bonch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import itsoftware.datdot.bonch.data.workers.Target;
import itsoftware.datdot.bonch.data.workers.User;

public class SignInActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        db = FirebaseFirestore.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.google_maps_key))
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (currentUser != null) {
            //mAuth.signOut();
            //mGoogleSignInClient.signOut();
            updateUI(currentUser);

        }

        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_in_button) {
            signIn();
        }
    }


    private void registerOrLogin(final FirebaseUser user) {
        db.collection("users")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()) {
                                User userdata = new User();
                                userdata.setEmail(user.getEmail());
                                userdata.setNickname(user.getDisplayName());
                                userdata.setZoomer(true);
                                userdata.setExperience(0);
                                userdata.setAge(0);
                                userdata.setVisited_target(new ArrayList<String>());
                                userdata.setPassed_questions(new ArrayList<String>());
                                userdata.setId(user.getUid());

                                db.collection("users")
                                        .add(userdata)
                                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                                if (task.isSuccessful()) {
                                                    updateUI(user);
                                                }
                                            }
                                        });
                            } else {
                                updateUI(user);
                            }
                        }
                    }
                });
        /*
        db.collection("users")
                .whereEqualTo("email", user.getEmail())
                .addSnapshotListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (DocumentSnapshot document :
                                queryDocumentSnapshots.getDocuments()) {
                            //document.toObject(Target.class);
                        }
                    }
                });
*/
        /*
        db.collection("users").document(user.getEmail())
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
                */
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //mStatusTextView.setText(getString(R.string.google_status_fmt, user.getEmail()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            //intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(new Intent(this, MapsActivity.class));
            finish();
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            registerOrLogin(user);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "signInWithCredential:onActivity");

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

}
