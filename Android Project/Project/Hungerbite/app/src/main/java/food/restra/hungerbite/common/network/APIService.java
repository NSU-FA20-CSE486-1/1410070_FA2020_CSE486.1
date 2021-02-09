package food.restra.hungerbite.common.network;

/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */


import com.squareup.okhttp.ResponseBody;

import food.restra.hungerbite.common.network.model.RootModel;
import food.restra.hungerbite.common.util.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers({"Authorization: key=" + Constants.SERVER_KEY, "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body RootModel root);
}

