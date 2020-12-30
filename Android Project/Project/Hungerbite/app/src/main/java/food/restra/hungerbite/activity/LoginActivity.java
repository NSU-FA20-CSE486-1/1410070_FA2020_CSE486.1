package food.restra.hungerbite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import food.restra.hungerbite.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.btSendOtp);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, OtpVerificationActivity.class);
            startActivity(intent);
        });
    }
}