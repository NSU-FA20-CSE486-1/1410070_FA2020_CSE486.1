package food.restra.hungerbite.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import food.restra.hungerbite.R;
import food.restra.hungerbite.customer.home.activity.CustomerHomeActivity;

public class CustomerSelectActivity extends AppCompatActivity {
    LinearLayout layoutCustomer, layoutChef, layoutDeliveryMan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_select);
        initView();
    }

    public void initView(){
        layoutCustomer = findViewById(R.id.llCustomer);
        layoutChef = findViewById(R.id.llChef);
        layoutDeliveryMan = findViewById(R.id.llDeliveryMan);

        layoutCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerSelectActivity.this, CustomerHomeActivity.class);
                startActivity(intent);
            }
        });
    }
}