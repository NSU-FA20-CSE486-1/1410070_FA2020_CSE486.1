package food.restra.hungerbite.feature.customer.feed.viewmodel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.feed.network.APIService;
import food.restra.hungerbite.feature.customer.feed.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
public class PostListViewModelRetrofit extends ViewModel {

    private MutableLiveData<List<FoodItem>> postList;

    public PostListViewModelRetrofit(){
        postList = new MutableLiveData<>();
    }

    public MutableLiveData<List<FoodItem>> getPostListObserver() {
        return postList;

    }

    public void makeApiCall() {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<FoodItem>> call = apiService.getMovieList();
        call.enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                postList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                postList.postValue(null);
            }
        });
    }
}
