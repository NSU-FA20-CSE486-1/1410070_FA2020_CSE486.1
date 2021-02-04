package food.restra.hungerbite.feature.customer.order_status.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.order_status.adapter.OrderStatusAdapter;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;

public class OrderStatus extends Fragment implements  OrderStatusAdapter.ItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OrderStatusAdapter orderAdapter;
    private List<OrderModel> orderList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private ImageView ivCartEmpty;
    private LinearLayout llRoot;
    private Gson gson;

    public OrderStatus() {
        // Required empty public constructor
    }

    public static OrderStatus newInstance(String param1, String param2) {
        OrderStatus fragment = new OrderStatus();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        gson = new Gson();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_status, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        orderAdapter =  new OrderStatusAdapter(getContext(), orderList, this);
        recyclerView.setAdapter(orderAdapter);
        initUI(view);
        getOrderItem();
        super.onViewCreated(view, savedInstanceState);
    }

    void initUI(View view){
        ivCartEmpty = view.findViewById(R.id.ivCartEmpty);
        llRoot = view.findViewById(R.id.llRoot);
    }

    void getOrderItem(){
        db.collection("orders")
                .whereEqualTo("customerId", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                OrderModel order = document.toObject(OrderModel.class);
                                order.setOrderId(document.getId());
                                orderList.add(order);
                            }
                        }
                        orderAdapter.setPostList(orderList);
                        orderAdapter.notifyDataSetChanged();
                        updatePriceCard();
                    }
                });
    }

    void updatePriceCard(){
        if(orderList.isEmpty()){
            llRoot.setVisibility(View.GONE);
            ivCartEmpty.setVisibility(View.VISIBLE);
        }else{
            llRoot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onOrderModelClick(OrderModel OrderModel) {

    }
}