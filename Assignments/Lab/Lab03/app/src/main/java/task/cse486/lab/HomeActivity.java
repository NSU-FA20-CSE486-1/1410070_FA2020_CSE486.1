package task.cse486.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String username = getIntent().getStringExtra(Constants.USERNAME_KEY);
        TextView textView = findViewById(R.id.tv_welcome);
        textView.setText(String.format(getString(R.string.welcome_message), username));
    }
}