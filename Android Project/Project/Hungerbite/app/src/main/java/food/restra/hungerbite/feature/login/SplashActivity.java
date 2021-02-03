package food.restra.hungerbite.feature.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.chef.ChefHomeActivity;
import food.restra.hungerbite.feature.customer.feed.activity.CustomerHomeActivity;
import food.restra.hungerbite.feature.login.model.AppUser;

public class SplashActivity extends AppCompatActivity {
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            AppUser user = documentSnapshot.toObject(AppUser.class);
                            Intent intent;
                            switch(user.getType()) {
                                case "customer":
                                    intent = new Intent(SplashActivity.this, CustomerHomeActivity.class);
                                    break;
                                case "chef":
                                    intent = new Intent(SplashActivity.this, ChefHomeActivity.class);
                                    break;
                                default:
                                    intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    // code block
                            }
                            startActivity(intent);
                            finish();
                        }
                    });

        }else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}