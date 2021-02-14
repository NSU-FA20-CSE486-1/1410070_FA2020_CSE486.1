package food.restra.hungerbite.feature.customer.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.product_detail.model.Cart;

/**
 * Created by Yeahia Muhammad Arif on 03,February,2021
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<Cart> cartList;
    private ItemClickListener clickListener;

    public CartAdapter(Context context, List<Cart> cartList, CartAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.cartList = cartList;
        this.clickListener = clickListener;
    }

    public void setPostList(List<Cart> cartList) {
        this.cartList = cartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart, parent, false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(this.cartList.get(position).getItem().getTitle());
        holder.tvPrice.setText(String.valueOf(this.cartList.get(position).getItem().getPrice()));
        holder.tvCount.setText("x"+this.cartList.get(position).getItemCount());

        holder.itemView.setOnClickListener(v -> clickListener.onCartClick(cartList.get(position)));
        Glide.with(context)
                .load(this.cartList.get(position).getItem().getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(this.cartList != null) {
            return this.cartList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvCount;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            tvCount = (TextView)itemView.findViewById(R.id.tvItemCount);
            imageView = (ImageView)itemView.findViewById(R.id.tvImage);
        }
    }


    public interface ItemClickListener{
        public void onCartClick(Cart cart);
    }
}