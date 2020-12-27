package task.cse486.encriptedsms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncriptedSmsActivity extends AppCompatActivity {
    String key;
    String encriptedSms;
    String tvIV;
    String phoneNumber;
    String decryptedText;
    TextView tvDecryptedSms;
    Button btSendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encripted_sms);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        encriptedSms = intent.getStringExtra("sms");
        tvIV = intent.getStringExtra("iv");
        phoneNumber = intent.getStringExtra("phone");
        tvDecryptedSms = findViewById(R.id.tvDecryptedSms);
        btSendSms = findViewById(R.id.btSendSms);
        decode();

        btSendSms.setOnClickListener(view -> {
            Uri smsUri = Uri.parse("tel:"+phoneNumber);
            Intent i = new Intent(Intent.ACTION_VIEW, smsUri);
            String str = null;
            try {
                str = new String(decoderfun(key), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i.putExtra("sms_body", str +" "+decryptedText);
            i.setType("vnd.android-dir/mms-sms");
            startActivity(i);
        });
    }

    public void decode() {
        if ((TextUtils.isEmpty(encriptedSms) || (TextUtils.isEmpty(tvIV) || (TextUtils.isEmpty(key))))) {
            Toast t = Toast.makeText(this, "Fill empty fields.", Toast.LENGTH_SHORT);
            t.show();
        } else {
            try {

                byte[] encText = decoderfun(encriptedSms);
                byte[] iv = decoderfun(tvIV);
                byte[] encodedSecretKey = decoderfun(key);
                SecretKey originalSecretKey = new SecretKeySpec(encodedSecretKey, 0, encodedSecretKey.length, "AES");

                decryptedText = Decrypt.decrypt(encText,originalSecretKey,iv);
                tvDecryptedSms.setText(encriptedSms);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static byte[] decoderfun(String enval) {
        byte[] conVal = Base64.decode(enval,Base64.DEFAULT);
        return conVal;

    }
}