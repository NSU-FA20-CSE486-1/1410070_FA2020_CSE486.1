package task.y34h1a.cse486.happybirthday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.y34h1a.cse486.happybirthday.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", "OnCreate");

    }
}