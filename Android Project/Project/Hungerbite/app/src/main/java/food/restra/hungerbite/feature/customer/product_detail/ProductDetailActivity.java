package food.restra.hungerbite.feature.customer.product_detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.product_detail.model.Cart;
import food.restra.hungerbite.feature.login.model.Profile;

public class ProductDetailActivity extends AppCompatActivity {
    Gson gson;
    TextView tvTitle, tvSubtitle, tvPrice, tvSelectedCount, tvItemCount, tvPriceSum, tvLocation;
    ImageView ivProductImage;
    ImageButton btPlus, btMinus;
    RelativeLayout btAddCart;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    FoodItem item;
    int itemCount = 0;
    int priceCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initView();
        getItem();

    }

    void getItem(){
        gson = new Gson();
        String data = getIntent().getStringExtra("item");
        item  = gson.fromJson(data, FoodItem.class);
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        tvTitle.setText(item.getTitle());
        tvSubtitle.setText(item.getTitle());
        tvPrice.setText(item.getPrice() + "$");
        tvPriceSum.setText(priceCount + " $");
        tvItemCount.setText(itemCount + " items");
        tvLocation.setText(item.getLocation());
        tvSelectedCount.setText(String.valueOf(itemCount));
        Glide.with(getApplicationContext())
                .load(item.getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(ivProductImage);

        btPlus.setOnClickListener(view -> {
            itemCount++;
            priceCount += Integer.parseInt(item.getPrice());
            tvItemCount.setText(itemCount + " items");
            tvPriceSum.setText(priceCount + " $");
            tvSelectedCount.setText(String.valueOf(itemCount));
        });

        btMinus.setOnClickListener(view -> {
            if(itemCount > 0){
                itemCount--;
            }
            if(priceCount > 0){
                priceCount -= Integer.parseInt(item.getPrice());

            }
            tvItemCount.setText(itemCount + " items");
            tvPriceSum.setText(priceCount + " $");
            tvSelectedCount.setText(String.valueOf(itemCount));
        });

        btAddCart.setOnClickListener(view -> {
            if(itemCount > 0){
                Cart cart = new Cart();
                cart.setCustomerId(mAuth.getCurrentUser().getUid());
                cart.setUploaderId(item.getUploaderId());
                cart.setItemCount(itemCount);
                cart.setItem(item);
                ProgressDialog progressDialog = new ProgressDialog(ProductDetailActivity.this);
                progressDialog.show();

                db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Profile user = documentSnapshot.toObject(Profile.class);
                        if(user.getName() != null && user.getLocation() != null){
                            cart.setCustomerName(user.getName());
                            cart.setCustomerImage(user.getImage());
                            cart.setCustomerToken(user.getToken());
                            db.collection("users")
                                .document(mAuth.getCurrentUser().getUid())
                                .collection("cart")
                                .add(cart)
                                .addOnSuccessListener(documentReference -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(this, "Item added to cart successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                });
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Your profile is not complete", Toast.LENGTH_SHORT).show();
                        }

                    });

            }else{
                Toast.makeText(this, "Please add item", Toast.LENGTH_SHORT).show();
            }

        });
    }
    void initView(){
        tvTitle = findViewById(R.id.tvItemTitle);
        tvSubtitle = findViewById(R.id.tvItemSubtitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvSelectedCount = findViewById(R.id.tvSelectCounter);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvPriceSum = findViewById(R.id.tvPriceSum);
        ivProductImage = findViewById(R.id.ivPhoto);
        btPlus = findViewById(R.id.btPlus);
        btMinus = findViewById(R.id.btMinus);
        btAddCart = findViewById(R.id.btAddCart);
        tvLocation = findViewById(R.id.tvLocation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}