package food.restra.hungerbite.feature.customer.search.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.feed.adapter.PostListAdapter;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.feed.viewmodel.ShoppingListViewModel;
import food.restra.hungerbite.feature.customer.product_detail.ProductDetailActivity;

public class FoodSearchFragment extends Fragment implements PostListAdapter.ItemClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    SearchView searchView;
    private List<FoodItem> foodItemList;
    private PostListAdapter adapter;
    private ShoppingListViewModel viewModel;
    private Gson gson;
    public FoodSearchFragment() {
        // Required empty public constructor
    }

    public static FoodSearchFragment newInstance(String param1, String param2) {
        FoodSearchFragment fragment = new FoodSearchFragment();
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
        return inflater.inflate(R.layout.fragment_food_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ImageView imageView = view.findViewById(R.id.ivNoItemFound);
        searchView = view.findViewById(R.id.seachView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PostListAdapter(getContext(), foodItemList, this);
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(ShoppingListViewModel.class);

        viewModel.getDinnerListLiveData("").observe(getViewLifecycleOwner(), Observable -> {});
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                viewModel.getSearchedLiveData(s).observe(getViewLifecycleOwner(), Observable -> {});
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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