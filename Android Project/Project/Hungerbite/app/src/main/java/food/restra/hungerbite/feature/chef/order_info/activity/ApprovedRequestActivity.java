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
import com.squareup.okhttp.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import food.restra.hungerbite.R;
import food.restra.hungerbite.common.network.APIService;
import food.restra.hungerbite.common.network.RetroInstance;
import food.restra.hungerbite.common.network.model.DataModel;
import food.restra.hungerbite.common.network.model.NotificationModel;
import food.restra.hungerbite.common.network.model.RootModel;
import food.restra.hungerbite.common.util.Constants;
import food.restra.hungerbite.feature.chef.order_info.adapter.ApprovedRequestAdapter;
import food.restra.hungerbite.feature.chef.order_info.adapter.NewRequestAdapter;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApprovedRequestActivity extends AppCompatActivity implements ApprovedRequestAdapter.ItemClickListener {

    ApprovedRequestAdapter adapter;
    List<OrderModel> orderModelList = new ArrayList<>();
    Gson gson;
    FirebaseFirestore db;
    FirebaseAuth auth;
    FirebaseUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_request);
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
        adapter =  new ApprovedRequestAdapter(getApplicationContext(), orderModelList, this);
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
                    Toast.makeText(getApplicationContext(), "Item Delivered", Toast.LENGTH_SHORT).show();
                    sendNotification(orderModel.getCart().getCustomerToken(), "Your order delivered");
                    orderModelList.remove(position);
                    adapter.notifyDataSetChanged();
                });
    }

    public void sendNotification(String token, String status) {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        RootModel rootModel = new RootModel(token, new NotificationModel(status, ""), new DataModel("Name", "30"));
        Call<ResponseBody> call = apiService.sendNotification(rootModel);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
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