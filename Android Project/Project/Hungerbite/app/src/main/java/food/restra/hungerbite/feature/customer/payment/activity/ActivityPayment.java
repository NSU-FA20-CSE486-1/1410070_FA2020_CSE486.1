package food.restra.hungerbite.feature.customer.payment.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.common.util.Constants;
import food.restra.hungerbite.feature.customer.CustomerHomeActivity;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;
import food.restra.hungerbite.feature.customer.product_detail.model.Cart;

public class ActivityPayment extends AppCompatActivity {
    LinearLayout llBkash, llCard, llCashOnDelivery;
    Gson gson;
    List<Cart> carts;
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        llBkash = findViewById(R.id.llBaksh);
        llCard = findViewById(R.id.llVisa);
        llCashOnDelivery = findViewById(R.id.llCashOnDelivery);
        gson = new Gson();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        String itemJson = getIntent().getStringExtra(Constants.ORDER_ITEMS);
        carts = gson.fromJson(itemJson, new TypeToken<List<Cart>>(){}.getType());

        llBkash.setOnClickListener(view -> {
            List<OrderModel> orderList = mapCartToOrder(Constants.BKASH_PAYMENT);
            WriteBatch batch = db.batch();
            ProgressDialog progressDialog = new ProgressDialog(ActivityPayment.this);
            progressDialog.setTitle("Please Wait...");
            progressDialog.show();
            for (OrderModel order:orderList) {
                DocumentReference docRef = db.collection("orders").document();
                DocumentReference cartRef = db.collection("users")
                        .document(auth.getCurrentUser().getUid())
                        .collection("cart")
                        .document(order.getCart().getCartId());
                batch.delete(cartRef);
                batch.set(docRef, order);
            }
            batch.commit().addOnCompleteListener(task -> {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Order Submitted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            });
        });

        llCard.setOnClickListener(view -> {
            List<OrderModel> orderList = mapCartToOrder(Constants.CARD_PAYMENT);
            WriteBatch batch = db.batch();
            ProgressDialog progressDialog = new ProgressDialog(ActivityPayment.this);
            progressDialog.setTitle("Please Wait...");
            progressDialog.show();
            for (OrderModel order:orderList) {
                DocumentReference docRef = db.collection("orders").document();
                DocumentReference cartRef = db.collection("users")
                        .document(auth.getCurrentUser().getUid())
                        .collection("cart")
                        .document(order.getCart().getCartId());
                batch.delete(cartRef);
                batch.set(docRef, order);
            }
            batch.commit().addOnCompleteListener(task -> {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Order Submitted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            });
        });

        llCashOnDelivery.setOnClickListener(view -> {
            List<OrderModel> orderList = mapCartToOrder(Constants.CASH_ON_DELIVERY);
            WriteBatch batch = db.batch();
            ProgressDialog progressDialog = new ProgressDialog(ActivityPayment.this);
            progressDialog.setTitle("Please Wait...");
            progressDialog.show();
            for (OrderModel order:orderList) {
                DocumentReference docRef = db.collection("orders").document();
                DocumentReference cartRef = db.collection("users")
                        .document(auth.getCurrentUser().getUid())
                        .collection("cart")
                        .document(order.getCart().getCartId());
                batch.delete(cartRef);
                batch.set(docRef, order);
            }
            batch.commit().addOnCompleteListener(task -> {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Order Submitted", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            });
        });
    }

    List<OrderModel> mapCartToOrder(int paymentType){
        List<OrderModel> list = new ArrayList<>();
        for (Cart cart: carts) {
            OrderModel order = new OrderModel();
            order.setCustomerId(cart.getCustomerId());
            order.setChefId(cart.getItem().getUploaderId());
            order.setPaymentType(paymentType);
            if(paymentType == Constants.CASH_ON_DELIVERY){
                order.setPaymentStatus(Constants.UNPAID_STATUS);
            }else{
                order.setPaymentStatus(Constants.PAID_STATUS);
            }
            order.setOrderStatus(Constants.PENDING_STATUS);
            order.setCart(cart);
            list.add(order);
        }
        return list;
    }
}