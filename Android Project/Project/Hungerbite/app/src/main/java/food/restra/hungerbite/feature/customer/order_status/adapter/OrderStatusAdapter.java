package food.restra.hungerbite.feature.customer.order_status.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.common.util.Constants;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;

/**
 * Created by Yeahia Muhammad Arif on 04,February,2021
 */
public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.MyViewHolder> {
    private Context context;
    private List<OrderModel> orderModelList;
    private OrderStatusAdapter.ItemClickListener clickListener;

    public OrderStatusAdapter(Context context, List<OrderModel> orderModelList, OrderStatusAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.orderModelList = orderModelList;
        this.clickListener = clickListener;
    }

    public void setPostList(List<OrderModel> orderModelList) {
        this.orderModelList = orderModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderStatusAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_customer_order_status, parent, false);
        return new OrderStatusAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusAdapter.MyViewHolder holder, int position) {
        String orderStatus = this.orderModelList.get(position).getOrderStatus();
        holder.tvTitle.setText(this.orderModelList.get(position).getCart().getItem().getTitle());
        holder.tvPrice.setText(String.valueOf(this.orderModelList.get(position).getCart().getItem().getPrice()));
        holder.tvOrderTitle.setText(orderStatus);
        switch (orderStatus) {
            case Constants.PENDING_STATUS:
                holder.llStatusContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.new_req_background));
                break;
            case Constants.APPROVED_STATUS:
                holder.llStatusContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.approved_req_background));
                break;
            case Constants.CANCELED_STATUS:
                holder.llStatusContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.canceled_req_background));
                break;
            case Constants.DELIVERED_STATUS:
                holder.llStatusContainer.setBackground(ContextCompat.getDrawable(context, R.drawable.delivered_req_background));
                break;
        }

        holder.itemView.setOnClickListener(v -> clickListener.onOrderModelClick(orderModelList.get(position)));
        Glide.with(context)
                .load(this.orderModelList.get(position).getCart().getItem().getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        if(this.orderModelList != null) {
            return this.orderModelList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvOrderTitle;
        LinearLayout llStatusContainer;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            tvOrderTitle = (TextView)itemView.findViewById(R.id.tvStatusTitle);
            llStatusContainer = (LinearLayout)itemView.findViewById(R.id.llStatusContainer);
            imageView = (ImageView)itemView.findViewById(R.id.tvImage);
        }
    }


    public interface ItemClickListener{
        public void onOrderModelClick(OrderModel OrderModel);
    }
}
