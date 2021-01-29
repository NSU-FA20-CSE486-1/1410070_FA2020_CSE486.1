package food.restra.hungerbite.customer.home.viewmodel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

import food.restra.hungerbite.customer.home.model.PostModel;
import food.restra.hungerbite.customer.home.network.APIService;
import food.restra.hungerbite.customer.home.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
public class PostListViewModel extends ViewModel {

    private MutableLiveData<List<PostModel>> postList;

    public PostListViewModel(){
        postList = new MutableLiveData<>();
    }

    public MutableLiveData<List<PostModel>> getPostListObserver() {
        return postList;

    }

    public void makeApiCall() {
        APIService apiService = RetroInstance.getRetroClient().create(APIService.class);
        Call<List<PostModel>> call = apiService.getMovieList();
        call.enqueue(new Callback<List<PostModel>>() {
            @Override
            public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {
                postList.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<PostModel>> call, Throwable t) {
                postList.postValue(null);
            }
        });
    }
}
