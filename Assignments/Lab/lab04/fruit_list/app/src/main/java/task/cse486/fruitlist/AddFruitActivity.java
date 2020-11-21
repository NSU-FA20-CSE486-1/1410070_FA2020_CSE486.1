package task.cse486.fruitlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddFruitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fruit);
    }

    public void onItemClick(View view) {
        Button button = (Button)view;
        String item = button.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra("item", item);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}