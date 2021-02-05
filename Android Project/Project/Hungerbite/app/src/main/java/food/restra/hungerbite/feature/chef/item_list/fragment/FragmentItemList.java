package food.restra.hungerbite.feature.chef.item_list.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.chef.item_list.adapter.ItemListAdapter;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.order_status.adapter.OrderStatusAdapter;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;


public class FragmentItemList extends Fragment implements ItemListAdapter.ItemClickListener {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<FoodItem> foodItemList = new ArrayList<>();
    private ItemListAdapter adapter;
    private ImageView ivNoItem;
    public FragmentItemList() { }


    public static FragmentItemList newInstance(String param1, String param2) {
        FragmentItemList fragment = new FragmentItemList();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ivNoItem = view.findViewById(R.id.ivNoItemFound);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new ItemListAdapter(getContext(), foodItemList, this);
        recyclerView.setAdapter(adapter);
        getOrderItem();
        super.onViewCreated(view, savedInstanceState);
    }

    void getOrderItem(){
        db.collection("posts")
                .whereEqualTo("uploaderId", mAuth.getCurrentUser().getUid())
                .whereEqualTo("category", mParam1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                FoodItem item = document.toObject(FoodItem.class);
                                item.setProductId(document.getId());
                                foodItemList.add(item);
                            }
                        }
                        adapter.setPostList(foodItemList);
                        adapter.notifyDataSetChanged();
                        if(foodItemList.isEmpty()){
                            ivNoItem.setVisibility(View.VISIBLE);
                        }else{
                            ivNoItem.setVisibility(View.GONE);
                        }

                    }
                });
    }

    @Override
    public void onFoodItemClick(FoodItem FoodItem) {

    }

    @Override
    public void onEdit(FoodItem FoodItem, int position) {

    }

    @Override
    public void onDelete(FoodItem FoodItem, int position) {

    }
}