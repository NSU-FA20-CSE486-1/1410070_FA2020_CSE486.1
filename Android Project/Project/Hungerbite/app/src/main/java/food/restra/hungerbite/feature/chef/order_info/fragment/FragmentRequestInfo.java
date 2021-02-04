package food.restra.hungerbite.feature.chef.order_info.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import food.restra.hungerbite.R;
import food.restra.hungerbite.common.util.Constants;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.customer.payment.model.OrderModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentRequestInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRequestInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RelativeLayout llNewReq, llApprovedReq, llCanceledReq, llDeliveredReq;
    TextView tvNewReqCount, tvApprovedReqCount, tvCanceledReqCount, tvDeliveredReqCount;
    FirebaseFirestore db;
    FirebaseAuth auth;
    List<OrderModel> newReqList = new ArrayList<>();
    List<OrderModel> approvedList = new ArrayList<>();
    List<OrderModel> deliveredList = new ArrayList<>();
    List<OrderModel> canceledList = new ArrayList<>();

    public FragmentRequestInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentRequestInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRequestInfo newInstance(String param1, String param2) {
        FragmentRequestInfo fragment = new FragmentRequestInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_request_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        getServerData();
        super.onViewCreated(view, savedInstanceState);
    }

    void initUi(View view){
        llApprovedReq = view.findViewById(R.id.llApprovedReq);
        llCanceledReq = view.findViewById(R.id.llCancelReq);
        llDeliveredReq = view.findViewById(R.id.llDeliveredReq);
        llNewReq = view.findViewById(R.id.llNewReq);

        tvNewReqCount = view.findViewById(R.id.tvNewReqCount);
        tvApprovedReqCount = view.findViewById(R.id.tvAppReqCount);
        tvCanceledReqCount = view.findViewById(R.id.tvCancelReqCount);
        tvDeliveredReqCount = view.findViewById(R.id.tvDeliveredReqCount);

        llApprovedReq.setOnClickListener(view1 -> {

        });

        llCanceledReq.setOnClickListener(view1 -> {

        });

        llDeliveredReq.setOnClickListener(view1 -> {

        });

        llNewReq.setOnClickListener(view1 -> {

        });
    }

    void getServerData(){
        db.collection("orders")
                .whereEqualTo("chefId", auth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<OrderModel> orderModelList = new ArrayList<>();
                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                OrderModel orderModel = document.toObject(OrderModel.class);
                                orderModelList.add(orderModel);
                            }
                        }
                        updateOrderInfo(orderModelList);
                    }
                });
    }

    void updateOrderInfo(List<OrderModel> orderModelList){
        int newReqCount = 0;
        int cancelReqCount = 0;
        int deliveredReqCount = 0;
        int approvedReqCount = 0;

        for (OrderModel order: orderModelList) {
            if(order.getOrderStatus().equals(Constants.PENDING_STATUS)){
                newReqCount++;
                newReqList.add(order);
            }
            if(order.getOrderStatus().equals(Constants.APPROVED_STATUS)){
                approvedReqCount++;
                approvedList.add(order);
            }
            if(order.getOrderStatus().equals(Constants.DELIVERED_STATUS)){
                deliveredReqCount++;
                deliveredList.add(order);
            }
            if(order.getOrderStatus().equals(Constants.CANCELED_STATUS)){
                cancelReqCount++;
                canceledList.add(order);
            }
        }

        tvNewReqCount.setText(String.valueOf(newReqCount));
        tvDeliveredReqCount.setText(String.valueOf(deliveredReqCount));
        tvCanceledReqCount.setText(String.valueOf(cancelReqCount));
        tvApprovedReqCount.setText(String.valueOf(approvedReqCount));
    }


}