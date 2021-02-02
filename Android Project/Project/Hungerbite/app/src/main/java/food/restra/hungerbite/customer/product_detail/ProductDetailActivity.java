package food.restra.hungerbite.customer.product_detail;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import food.restra.hungerbite.R;
import food.restra.hungerbite.customer.feed.model.FoodItem;

public class ProductDetailActivity extends AppCompatActivity {
    Gson gson;
    TextView tvTitle, tvSubtitle, tvPrice, tvSelectedCount, tvItemCount, tvPriceSum;
    ImageView ivProductImage;
    ImageButton btPlus, btMinus;
    RelativeLayout btAddCart;
    FoodItem item;
    int itemCount = 0;
    int priceCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initView();
        getItem();

    }

    void getItem(){
        gson = new Gson();
        String data = getIntent().getStringExtra("item");
        item  = gson.fromJson(data, FoodItem.class);

        tvTitle.setText(item.getTitle());
        tvSubtitle.setText(item.getTitle());
        tvPrice.setText(item.getPrice() + "$");
        tvPriceSum.setText(priceCount + " $");
        tvItemCount.setText(itemCount + " items");
        tvSelectedCount.setText(String.valueOf(itemCount));
        Glide.with(getApplicationContext())
                .load(item.getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(ivProductImage);

        btPlus.setOnClickListener(view -> {
            itemCount++;
            priceCount += Integer.parseInt(item.getPrice());
            tvItemCount.setText(itemCount + " items");
            tvPriceSum.setText(priceCount + " $");
            tvSelectedCount.setText(String.valueOf(itemCount));
        });

        btMinus.setOnClickListener(view -> {
            if(itemCount > 0){
                itemCount--;
            }
            if(priceCount > 0){
                priceCount -= Integer.parseInt(item.getPrice());

            }
            tvItemCount.setText(itemCount + " items");
            tvPriceSum.setText(priceCount + " $");
            tvSelectedCount.setText(String.valueOf(itemCount));
        });

        btAddCart.setOnClickListener(view -> {

        });
    }
    void initView(){
        tvTitle = findViewById(R.id.tvItemTitle);
        tvSubtitle = findViewById(R.id.tvItemSubtitle);
        tvPrice = findViewById(R.id.tvPrice);
        tvSelectedCount = findViewById(R.id.tvSelectCounter);
        tvItemCount = findViewById(R.id.tvItemCount);
        tvPriceSum = findViewById(R.id.tvPriceSum);
        ivProductImage = findViewById(R.id.ivPhoto);
        btPlus = findViewById(R.id.btPlus);
        btMinus = findViewById(R.id.btMinus);
        btAddCart = findViewById(R.id.btAddCart);
    }
}