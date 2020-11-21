package task.cse486.fruitlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final int TEXT_REQUEST = 1;
    int currentItem = 0;
    TextView item1, item2, item3, item4, item5, item6, item7, item8, item9, item10;
    Button btAddItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        item1 = findViewById(R.id.tvItem1);
        item2 = findViewById(R.id.tvItem2);
        item3 = findViewById(R.id.tvItem3);
        item4 = findViewById(R.id.tvItem4);
        item5 = findViewById(R.id.tvItem5);
        item6 = findViewById(R.id.tvItem6);
        item7 = findViewById(R.id.tvItem7);
        item8 = findViewById(R.id.tvItem8);
        item9 = findViewById(R.id.tvItem9);
        item10 = findViewById(R.id.tvItem10);

        btAddItem = findViewById(R.id.button);
        btAddItem.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddFruitActivity.class);
            startActivityForResult(intent, TEXT_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String item = data.getStringExtra("item");
                switch (currentItem){
                    case 0:
                        item1.setText(item);
                        break;
                    case 1:
                        item2.setText(item);
                        break;
                    case 2:
                        item3.setText(item);
                        break;
                    case 3:
                        item4.setText(item);
                        break;
                    case 4:
                        item5.setText(item);
                        break;
                    case 5:
                        item6.setText(item);
                        break;
                    case 6:
                        item7.setText(item);
                        break;
                    case 7:
                        item8.setText(item);
                        break;
                    case 8:
                        item9.setText(item);
                        break;
                    case 9:
                        item10.setText(item);
                        break;
                    default:
                        item1.setText(item);
                        currentItem = 0;
                        break;
                }
                currentItem++;
            }
        }
    }
}