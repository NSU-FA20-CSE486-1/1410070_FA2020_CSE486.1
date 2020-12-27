package task.cse486.encriptedsms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {
    KeyGenerator keyGenerator;
    SecretKey secretKey;
    byte[] secretKeyen;
    String strSecretKey;
    byte[] IV = new byte[16];
    byte[] cipherText;
    SecureRandom random;

    EditText etEncripted;
    EditText etPhoneNumber;
    EditText etSms;
    Button btEncrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etEncripted = findViewById(R.id.etEncripted);
        etPhoneNumber = findViewById(R.id.etNumber);

        etSms = findViewById(R.id.etSms);

        btEncrypt = findViewById(R.id.btEncrypt);

        btEncrypt.setOnClickListener(view -> encrypt());
    }

    private void encrypt(){
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        keyGenerator.init(256);
        secretKey = keyGenerator.generateKey();
        secretKeyen=secretKey.getEncoded();
        strSecretKey = encoderfun(secretKeyen);

        random = new SecureRandom();
        random.nextBytes(IV);
        try {
            cipherText = Encrypt.encrypt(etSms.getText().toString().trim().getBytes(), secretKey, IV);
            String encriptedSms = encoderfun(cipherText);
            String tvIV = encoderfun(IV);

            Intent intent = new Intent(getApplicationContext(), EncriptedSmsActivity.class);
            intent.putExtra("key", strSecretKey);
            intent.putExtra("sms", encriptedSms);
            intent.putExtra("iv", tvIV);
            intent.putExtra("phone", etPhoneNumber.getText().toString());
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String encoderfun(byte[] decval) {
        String conVal= Base64.encodeToString(decval,Base64.DEFAULT);
        return conVal;
    }


}