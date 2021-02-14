package food.restra.hungerbite.feature.login.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.chef.ChefHomeActivity;
import food.restra.hungerbite.feature.customer.CustomerHomeActivity;

public class CustomerSelectActivity extends AppCompatActivity {
    LinearLayout layoutCustomer, layoutChef, layoutDeliveryMan;
    FirebaseFirestore db ;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select);
        initView();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public void initView(){
        layoutCustomer = findViewById(R.id.llCustomer);
        layoutChef = findViewById(R.id.llChef);
        layoutDeliveryMan = findViewById(R.id.llDeliveryMan);


        layoutCustomer.setOnClickListener(view -> {
            FirebaseUser user =  mAuth.getCurrentUser();
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", user.getEmail());
            userData.put("type", "customer");

            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(CustomerSelectActivity.this, CustomerHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

        });

        layoutChef.setOnClickListener(view -> {
            FirebaseUser user =  mAuth.getCurrentUser();
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", user.getEmail());
            userData.put("type", "chef");

            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(CustomerSelectActivity.this, ChefHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        });

        layoutDeliveryMan.setOnClickListener(view -> {
            FirebaseUser user =  mAuth.getCurrentUser();
            Map<String, Object> userData = new HashMap<>();
            userData.put("email", user.getEmail());
            userData.put("type", "delivery");

            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Intent intent = new Intent(CustomerSelectActivity.this, CustomerHomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        });
    }
}