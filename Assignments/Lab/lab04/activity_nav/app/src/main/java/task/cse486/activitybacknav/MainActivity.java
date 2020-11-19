package task.cse486.activitybacknav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btActivityTwo = findViewById(R.id.bt_activity_two);
        Button btActivityThree = findViewById(R.id.bt_activity_three);

        btActivityTwo.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityTwo.class);
            startActivity(intent);
        });

        btActivityThree.setOnClickListener(view -> {
            Intent intent = new Intent(this, ActivityThree.class);
            startActivity(intent);
        });
    }
}