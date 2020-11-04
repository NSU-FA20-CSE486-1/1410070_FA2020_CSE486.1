package task.cse486.lab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String username = "admin";
    private final String password = "admin";
    private EditText etPassword;
    private EditText etUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        etUserName = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
    }

    public void checkLogin(View view) {
        String usernameInput = etUserName.getText().toString();
        String passwordInput = etPassword.getText().toString();

        if(usernameInput.matches(username) && passwordInput.matches(password)){
            Toast.makeText(this,
                    getString(R.string.login_successful_message), Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,
                    getString(R.string.login_failed_message), Toast.LENGTH_SHORT).show();
        }
    }
}