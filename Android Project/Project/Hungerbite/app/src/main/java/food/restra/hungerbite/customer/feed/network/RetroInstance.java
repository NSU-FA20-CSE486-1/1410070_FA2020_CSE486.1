package food.restra.hungerbite.customer.feed.network;

/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {


    public static String BASE_URL = "https://api.mocki.io/v1/";//volley_array.json

    private static Retrofit retrofit;

    public static Retrofit getRetroClient() {

        if(retrofit == null ) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
