package food.restra.hungerbite.feature.chef.item_list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;

/**
 * Created by Yeahia Muhammad Arif on 05,February,2021
 */
public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.MyViewHolder> {
    private Context context;
    private List<FoodItem> foodItemList;
    private ItemListAdapter.ItemClickListener clickListener;

    public ItemListAdapter(Context context, List<FoodItem> foodItemList, ItemListAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.foodItemList = foodItemList;
        this.clickListener = clickListener;
    }

    public void setPostList(List<FoodItem> foodItemList) {
        this.foodItemList = foodItemList;
        notifyDataSetChanged();
    }

    public void delete(int position) {
        this.foodItemList.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ItemListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_chef_item, parent, false);
        return new ItemListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListAdapter.MyViewHolder holder, int position) {
        int price = Integer.parseInt(this.foodItemList.get(position).getPrice());
        holder.tvTitle.setText(this.foodItemList.get(position).getTitle());
        holder.tvItemTitle.setText(this.foodItemList.get(position).getTitle());
        holder.tvPrice.setText(String.valueOf(this.foodItemList.get(position).getPrice()));
        Glide.with(context)
                .load(this.foodItemList.get(position).getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> clickListener.onFoodItemClick(foodItemList.get(position)));
        holder.btDelete.setOnClickListener(v -> clickListener.onDelete(foodItemList.get(position), holder.getAdapterPosition()));
        holder.btEdit.setOnClickListener(view -> clickListener.onEdit(foodItemList.get(position), holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if(this.foodItemList != null) {
            return this.foodItemList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvItemTitle;
        ImageView imageView, btDelete, btEdit;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            imageView = (ImageView)itemView.findViewById(R.id.tvImage);
            btEdit = itemView.findViewById(R.id.btEdit);
            tvItemTitle = itemView.findViewById(R.id.tvItemTitle);
            btDelete = itemView.findViewById(R.id.btDelete);
        }
    }


    public interface ItemClickListener{
        public void onFoodItemClick(FoodItem FoodItem);
        public void onEdit(FoodItem FoodItem,int position);
        public void onDelete(FoodItem FoodItem, int position);
    }
}
