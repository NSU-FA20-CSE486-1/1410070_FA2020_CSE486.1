package food.restra.hungerbite.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import food.restra.hungerbite.R;

public class OtpVerificationActivity extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        initView();
    }

    public void initView(){
        button = findViewById(R.id.btVerifyOtp);

        button.setOnClickListener(view -> {
            Intent intent =  new Intent(OtpVerificationActivity.this, CustomerSelectActivity.class);
            startActivity(intent);
        });
    }
}