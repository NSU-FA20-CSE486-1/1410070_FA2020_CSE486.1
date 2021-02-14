package food.restra.hungerbite.feature.chef.item_list.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.chef.addfood.fragment.FragmentAddItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentItemListContainer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentItemListContainer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    DemoCollectionAdapter demoCollectionAdapter;
    ViewPager2 viewPager;

    public FragmentItemListContainer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentItemList.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentItemListContainer newInstance(String param1, String param2) {
        FragmentItemListContainer fragment = new FragmentItemListContainer();
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
        return inflater.inflate(R.layout.fragment_item_list_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        demoCollectionAdapter = new DemoCollectionAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(demoCollectionAdapter);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if(position == 0){
                tab.setText("Breakfast");
            }else if(position == 1){
                tab.setText("Lunch");
            }else if(position == 2){
                tab.setText("Dinner");
            }else {
                tab.setText("Dessert");
            }
        }).attach();


        super.onViewCreated(view, savedInstanceState);
    }

    public static class DemoCollectionAdapter extends FragmentStateAdapter {
        public DemoCollectionAdapter(Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if(position == 0){
                return new FragmentItemList().newInstance("breakfast", "");
            }else if(position == 1){
                return new FragmentItemList().newInstance("lunch", "");
            }else if(position == 2){
                return new FragmentItemList().newInstance("dinner", "");
            }else{
                return new FragmentItemList().newInstance("dessert", "");
            }
        }

        @Override
        public int getItemCount() {
            return 4;
        }
    }
}
