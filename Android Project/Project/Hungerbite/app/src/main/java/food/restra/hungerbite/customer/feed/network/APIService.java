package food.restra.hungerbite.customer.feed.network;

/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
import java.util.List;

import food.restra.hungerbite.customer.feed.model.PostModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("ea02c45c")
    Call<List<PostModel>> getMovieList();
}
