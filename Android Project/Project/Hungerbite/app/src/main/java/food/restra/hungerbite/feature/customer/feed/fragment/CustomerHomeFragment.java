package food.restra.hungerbite.feature.customer.feed.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.feed.adapter.PostListAdapter;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.feed.viewmodel.ShoppingListViewModel;
import food.restra.hungerbite.feature.customer.product_detail.ProductDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerHomeFragment extends Fragment implements PostListAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<FoodItem> foodItemList;
    private PostListAdapter adapter;
    private ShoppingListViewModel viewModel;
    private LinearLayout llDinner, llLunch, llBreakfast, llDesert;
    private ImageView ivDinner, ivLunch, ivBreakfast, ivDesert;
    private Gson gson;
    private SharedPreferences sharedPreferences;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CustomerHomeFragment() {
        // Required empty public constructor
    }
    public static CustomerHomeFragment newInstance(String param1, String param2) {
        CustomerHomeFragment fragment = new CustomerHomeFragment();
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
        gson =  new Gson();
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        llDinner = view.findViewById(R.id.llDinner);
        llLunch = view.findViewById(R.id.llLunch);
        llBreakfast = view.findViewById(R.id.llBreakfast);
        llDesert = view.findViewById(R.id.lldesert);
        ivLunch = view.findViewById(R.id.ivLunch);
        ivBreakfast = view.findViewById(R.id.ivBreakfast);
        ivDinner = view.findViewById(R.id.ivDinner);
        ivDesert = view.findViewById(R.id.ivDessert);


        ImageView imageView = view.findViewById(R.id.ivNoItemFound);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PostListAdapter(getContext(), foodItemList, this);
        recyclerView.setAdapter(adapter);

        llDinner.setOnClickListener(view1 -> {
            String location = sharedPreferences.getString("location","");
            viewModel.getDinnerListLiveData(location).observe(getViewLifecycleOwner(), Observable -> {});
            viewModel.getShoppingList().observe(getViewLifecycleOwner(), postModels -> {
                if(postModels != null) {
                    foodItemList = postModels;
                    adapter.setPostList(postModels);
                    adapter.notifyDataSetChanged();
                    imageView.setVisibility(View.GONE);
                    if(postModels.isEmpty()){
                        imageView.setVisibility(View.VISIBLE);
                    }else{
                        imageView.setVisibility(View.GONE);
                    }
                }
            });
            changeViewResource(llDinner, ivDinner);
        });

        llLunch.setOnClickListener(view1 -> {
            String location = sharedPreferences.getString("location","");
            viewModel.getLunchListLiveData(location).observe(getViewLifecycleOwner(), Observable -> {});
            viewModel.getShoppingList().observe(getViewLifecycleOwner(), postModels -> {
                if(postModels != null) {
                    foodItemList = postModels;
                    adapter.setPostList(postModels);
                    adapter.notifyDataSetChanged();
                    if(postModels.isEmpty()){
                        imageView.setVisibility(View.VISIBLE);
                    }else{
                        imageView.setVisibility(View.GONE);
                    }
                }
            });
            changeViewResource(llLunch, ivLunch);
        });

        llBreakfast.setOnClickListener(view1 -> {
            String location = sharedPreferences.getString("location","");
            viewModel.getBreakfastListLiveData(location).observe(getViewLifecycleOwner(), Observable -> {});
            viewModel.getShoppingList().observe(getViewLifecycleOwner(), postModels -> {
                if(postModels != null) {
                    foodItemList = postModels;
                    adapter.setPostList(postModels);
                    adapter.notifyDataSetChanged();
                    if(postModels.isEmpty()){
                        imageView.setVisibility(View.VISIBLE);
                    }else{
                        imageView.setVisibility(View.GONE);
                    }
                }
            });
            changeViewResource(llBreakfast, ivBreakfast);
        });

        llDesert.setOnClickListener(view1 -> {
            String location = sharedPreferences.getString("location","");
            viewModel.getDessertLiveData(location).observe(getViewLifecycleOwner(), Observable -> {});
            viewModel.getShoppingList().observe(getViewLifecycleOwner(), postModels -> {
                if(postModels != null) {
                    foodItemList = postModels;
                    adapter.setPostList(postModels);
                    adapter.notifyDataSetChanged();
                    if(postModels.isEmpty()){
                        imageView.setVisibility(View.VISIBLE);
                    }else{
                        imageView.setVisibility(View.GONE);
                    }
                }
            });
            changeViewResource(llDesert, ivDesert);
        });


        String location = sharedPreferences.getString("location","");
        viewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        viewModel.getDinnerListLiveData(location).observe(getViewLifecycleOwner(), Observable -> {});
        viewModel.getShoppingList().observe(getViewLifecycleOwner(), postModels -> {
            if(postModels != null) {
                foodItemList = postModels;
                adapter.setPostList(postModels);
                if(postModels.isEmpty()){
                    imageView.setVisibility(View.VISIBLE);
                }else{
                    imageView.setVisibility(View.GONE);
                }
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    private void changeViewResource(LinearLayout layout, ImageView icon){
        llDinner.setBackgroundResource(R.drawable.food_category_unselect);
        llLunch.setBackgroundResource(R.drawable.food_category_unselect);
        llBreakfast.setBackgroundResource(R.drawable.food_category_unselect);
        llDesert.setBackgroundResource(R.drawable.food_category_unselect);

        ivDesert.setColorFilter(ContextCompat.getColor(getContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        ivDinner.setColorFilter(ContextCompat.getColor(getContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        ivBreakfast.setColorFilter(ContextCompat.getColor(getContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        ivLunch.setColorFilter(ContextCompat.getColor(getContext(), R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);

        icon.setColorFilter(ContextCompat.getColor(getContext(), R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

        layout.setBackgroundResource(R.drawable.food_category_selected);
    }

    @Override
    public void onItemClick(FoodItem movie) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        String item = gson.toJson(movie);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}