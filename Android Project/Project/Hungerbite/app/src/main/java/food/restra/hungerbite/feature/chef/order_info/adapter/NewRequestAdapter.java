package food.restra.hungerbite.feature.chef.order_info.adapter;

/**
 * Created by Yeahia Muhammad Arif on 04,February,2021
 */
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
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;

/**
 * Created by Yeahia Muhammad Arif on 03,February,2021
 */
public class NewRequestAdapter extends RecyclerView.Adapter<NewRequestAdapter.MyViewHolder> {
    private Context context;
    private List<OrderModel> orderModelList;
    private NewRequestAdapter.ItemClickListener clickListener;

    public NewRequestAdapter(Context context, List<OrderModel> orderModelList, NewRequestAdapter.ItemClickListener clickListener) {
        this.context = context;
        this.orderModelList = orderModelList;
        this.clickListener = clickListener;
    }

    public void setPostList(List<OrderModel> orderModelList) {
        this.orderModelList = orderModelList;
        notifyDataSetChanged();
    }

    public void delete(int position) {
        this.orderModelList.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewRequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_new_request, parent, false);
        return new NewRequestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewRequestAdapter.MyViewHolder holder, int position) {
        holder.tvTitle.setText(this.orderModelList.get(position).getCart().getItem().getTitle());
        holder.tvOrderId.setText("Order ID: " + this.orderModelList.get(position).getOrderId().substring(0, 7));
        holder.tvItemCount.setText("x"+ this.orderModelList.get(position).getCart().getItemCount());
        holder.tvPrice.setText(String.valueOf(this.orderModelList.get(position).getCart().getItem().getPrice()));
        Glide.with(context)
                .load(this.orderModelList.get(position).getCart().getItem().getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> clickListener.onOrderModelClick(orderModelList.get(position)));
        holder.btDelete.setOnClickListener(v -> clickListener.onDelete(orderModelList.get(position), holder.getAdapterPosition()));
        holder.switchButton.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                clickListener.onApprove(orderModelList.get(position), holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(this.orderModelList != null) {
            return this.orderModelList.size();
        }
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvPrice, tvCount, tvOrderId, tvItemCount;
        ImageView imageView, btDelete;
        Switch switchButton;


        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            tvPrice = (TextView)itemView.findViewById(R.id.tvPrice);
            imageView = (ImageView)itemView.findViewById(R.id.tvImage);
            switchButton = itemView.findViewById(R.id.swApprove);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            btDelete = itemView.findViewById(R.id.btDelete);
            tvItemCount = itemView.findViewById(R.id.tvItemCount);
        }
    }


    public interface ItemClickListener{
        public void onOrderModelClick(OrderModel OrderModel);
        public void onApprove(OrderModel OrderModel,int position);
        public void onDelete(OrderModel OrderModel, int position);
    }
}
