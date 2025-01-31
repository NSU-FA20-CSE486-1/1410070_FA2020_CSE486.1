package food.restra.hungerbite.feature.chef.order_info.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import food.restra.hungerbite.R;
import food.restra.hungerbite.common.util.Constants;
import food.restra.hungerbite.feature.chef.order_info.adapter.CanceledRequestAdapter;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;

public class DeliveredOrderActivity extends AppCompatActivity implements CanceledRequestAdapter.ItemClickListener {

    CanceledRequestAdapter adapter;
    List<OrderModel> orderModelList = new ArrayList<>();
    Gson gson;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_order);
        gson = new Gson();
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        mUser = auth.getCurrentUser();

        String data = getIntent().getStringExtra(Constants.NEW_REQUST_DATA);
        orderModelList = gson.fromJson(data, new TypeToken<List<OrderModel>>(){}.getType());
        initRecycleview();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void initRecycleview(){
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new CanceledRequestAdapter(getApplicationContext(), orderModelList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onOrderModelClick(OrderModel OrderModel) {
        Toast.makeText(this, "mainItemClicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onApprove(OrderModel orderModel, int position) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderStatus", Constants.DELIVERED_STATUS);
        db.collection("orders")
                .document(orderModel.getOrderId())
                .update(map)
                .addOnCompleteListener(task -> {
                    orderModelList.remove(position);
                    adapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
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