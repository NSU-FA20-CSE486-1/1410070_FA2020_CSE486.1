package food.restra.hungerbite.feature.customer.feed.adapter;

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
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;

/**
 * Created by Yeahia Muhammad Arif on 29,January,2021
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.MyViewHolder> {
    private Context context;
    private List<FoodItem> postList;
    private ItemClickListener clickListener;

    public PostListAdapter(Context context, List<FoodItem> postList, ItemClickListener clickListener) {
        this.context = context;
        this.postList = postList;
        this.clickListener = clickListener;
    }

    public void setPostList(List<FoodItem> postList) {
        this.postList = postList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(this.postList.get(position).getTitle());
        holder.tvPrice.setText(this.postList.get(position).getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(postList.get(position));
            }
        });

        Glide.with(context)
                .load(this.postList.get(position).getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

        Glide.with(context)
                .load(this.postList.get(position).getUploaderImage())
                .into(holder.ivUploaderImage);
    }

    @Override
    public int getItemCount() {
        if(this.postList != null) {
            return this.postList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvUploaderName, tvPrice;
        ImageView imageView,ivUploaderImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.titleView);
            imageView = itemView.findViewById(R.id.imageView);
            tvUploaderName = itemView.findViewById(R.id.tvUploaderName);
            ivUploaderImage = itemView.findViewById(R.id.profile_image);
            tvPrice = itemView.findViewById(R.id.tvPrice);

        }
    }


    public interface ItemClickListener{
        public void onItemClick(FoodItem movie);
    }
}
