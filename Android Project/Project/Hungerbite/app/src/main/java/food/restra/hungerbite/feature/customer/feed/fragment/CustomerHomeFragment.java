package food.restra.hungerbite.feature.customer.feed.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private Gson gson;


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
        ImageView imageView = view.findViewById(R.id.ivNoItemFound);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PostListAdapter(getContext(), foodItemList, this);
        recyclerView.setAdapter(adapter);

        llDinner.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "dinner", Toast.LENGTH_LONG).show();
            viewModel.getDinnerListLiveData().observe(getViewLifecycleOwner(), Observable -> {});
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
        });

        llLunch.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "lunch", Toast.LENGTH_LONG).show();
            viewModel.getLunchListLiveData().observe(getViewLifecycleOwner(), Observable -> {});
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
        });

        llBreakfast.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "breakfast", Toast.LENGTH_LONG).show();
            viewModel.getBreakfastListLiveData().observe(getViewLifecycleOwner(), Observable -> {});
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
        });

        llDesert.setOnClickListener(view1 -> {
            Toast.makeText(getContext(), "desert", Toast.LENGTH_LONG).show();
            viewModel.getDessertLiveData().observe(getViewLifecycleOwner(), Observable -> {});
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
        });


        viewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);
        viewModel.getDinnerListLiveData().observe(getViewLifecycleOwner(), Observable -> {});
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

    @Override
    public void onItemClick(FoodItem movie) {
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        String item = gson.toJson(movie);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}