package task.cse486.datetimepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SelectedTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_time);
        TextView text = findViewById(R.id.textView);

        Intent intent = getIntent();
        if(intent != null){
            String selectedTime = intent.getStringExtra("time");
            text.setText(selectedTime);
        }
    }
}