package food.restra.hungerbite.feature.customer.cart.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.cart.adapter.CartAdapter;
import food.restra.hungerbite.feature.customer.product_detail.model.Cart;

public class CartFragment extends Fragment implements CartAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private CartAdapter cartAdapter;
    private List<Cart> cartList = new ArrayList<>();
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    private TextView tvItemTotalPrice, tvTotalPay, tvDelvieryFee;
    private AppCompatButton btPlaceOrder;
    private ImageView ivCartEmpty;
    private LinearLayout llRoot;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(String param1, String param2) {
        CartFragment fragment = new CartFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        cartAdapter =  new CartAdapter(getContext(), cartList, this);
        recyclerView.setAdapter(cartAdapter);
        initUI(view);
        getCartItem();

        super.onViewCreated(view, savedInstanceState);
    }

    void initUI(View view){
        tvItemTotalPrice = view.findViewById(R.id.tvItemTotal);
        tvTotalPay = view.findViewById(R.id.tvTotalPay);
        tvDelvieryFee = view.findViewById(R.id.tvDeliveryFee);
        btPlaceOrder = view.findViewById(R.id.btPlaceOrder);
        ivCartEmpty = view.findViewById(R.id.ivCartEmpty);
        llRoot = view.findViewById(R.id.llRoot);
    }

    void getCartItem(){
        db.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .collection("cart")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                Cart cart = document.toObject(Cart.class);
                                cartList.add(cart);
                            }
                        }
                        cartAdapter.setPostList(cartList);
                        cartAdapter.notifyDataSetChanged();
                        updatePriceCard();
                    }
                });
    }

    void updatePriceCard(){
        int totalPrice = 0;
        for (Cart cart:cartList) {
            totalPrice += Integer.parseInt(cart.getItem().getPrice()) * cart.getItemCount();
        }
        tvTotalPay.setText(String.valueOf(totalPrice));
        tvItemTotalPrice.setText(String.valueOf(totalPrice));

        if(cartList.isEmpty()){
            llRoot.setVisibility(View.GONE);
            ivCartEmpty.setVisibility(View.VISIBLE);
        }else{
            llRoot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onCartClick(Cart cart) {

    }
}