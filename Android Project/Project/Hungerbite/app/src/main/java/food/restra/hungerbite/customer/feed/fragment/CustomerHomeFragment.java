package food.restra.hungerbite.customer.feed.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.customer.feed.adapter.PostListAdapter;
import food.restra.hungerbite.customer.feed.model.PostModel;
import food.restra.hungerbite.customer.feed.viewmodel.PostListViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CustomerHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CustomerHomeFragment extends Fragment implements PostListAdapter.ItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<PostModel> postModelList;
    private PostListAdapter adapter;
    private PostListViewModel viewModel;


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
        final TextView noresult = view.findViewById(R.id.noResultTv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new PostListAdapter(getContext(), postModelList, this);
        recyclerView.setAdapter(adapter);


        viewModel = ViewModelProviders.of(this).get(PostListViewModel.class);
        viewModel.getPostListObserver().observe(getViewLifecycleOwner(), postModels -> {
            if(postModels != null) {
                postModelList = postModels;
                adapter.setPostList(postModels);
                noresult.setVisibility(View.GONE);
            } else {
                noresult.setVisibility(View.VISIBLE);
            }
        });
        viewModel.makeApiCall();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMovieClick(PostModel movie) {
        Toast.makeText(getContext(), "Clicked Movie Name is : " +movie.getTitle(), Toast.LENGTH_SHORT).show();
    }
}