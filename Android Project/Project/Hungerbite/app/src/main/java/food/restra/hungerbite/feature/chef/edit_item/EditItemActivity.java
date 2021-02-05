package food.restra.hungerbite.feature.chef.edit_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.UUID;

import food.restra.hungerbite.R;
import food.restra.hungerbite.common.util.Constants;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;

public class EditItemActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 22;
    private EditText etName, etPrice, etCategory;
    private RadioGroup timeRadioGroup;
    private RadioButton rbLunch, rbDinner, rbBreakfast, rbDesert;
    private ImageView ivItemPhoto, ivPhotoAdd;
    private AppCompatButton btAddItem;
    private FoodItem foodItem;
    private Gson gson;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri filePath;
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        initUi();
        initInstances();
        loadData();
    }

    void initInstances(){
        String data = getIntent().getStringExtra(Constants.EDIT_ITEM_DATA);
        gson = new Gson();
        foodItem = gson.fromJson(data, FoodItem.class);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    void initUi(){
        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCategory = findViewById(R.id.etCategory);
        timeRadioGroup = findViewById(R.id.rgFoodTime);
        rbLunch = findViewById(R.id.rbLunch);
        rbBreakfast = findViewById(R.id.rbBreakfast);
        rbDesert = findViewById(R.id.rbDesert);
        rbDinner = findViewById(R.id.rbDinner);
        ivItemPhoto = findViewById(R.id.ivItemPhoto);
        ivPhotoAdd = findViewById(R.id.ivPhotoAdd);
        btAddItem = findViewById(R.id.btEditItem);

        btAddItem.setOnClickListener(view1 -> {
            int selectedId = timeRadioGroup.getCheckedRadioButtonId();
            RadioButton rbTime = findViewById(selectedId);

            FoodItem item = new FoodItem();
            item.setTitle(etName.getText().toString());
            item.setPrice(etPrice.getText().toString());
            item.setCategory(rbTime.getText().toString().toLowerCase());
            item.setFoodCategory(etCategory.getText().toString());
            item.setImage(imageUrl);
            item.setProductId(foodItem.getProductId());
            item.setUploaderId(mAuth.getCurrentUser().getUid());

            ProgressDialog progressDialog = new ProgressDialog(EditItemActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            db.collection("posts")
                    .document(item.getProductId())
                    .set(item).addOnCompleteListener(task -> {
                Toast.makeText(getApplicationContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        });

        ivPhotoAdd.setOnClickListener(view12 -> {
            selectImage();
        });
    }

    private void loadData(){
        etName.setText(foodItem.getTitle());
        etPrice.setText(foodItem.getPrice());
        etCategory.setText(foodItem.getFoodCategory());
        switch (foodItem.getCategory()) {
            case "dinner":
                rbDinner.setChecked(true);
                break;
            case "breakfast":
                rbBreakfast.setChecked(true);
                break;
            case "lunch":
                rbLunch.setChecked(true);
                break;
            case "dessert":
                rbDesert.setChecked(true);
                break;
        }

        Glide.with(getApplicationContext())
                .load(foodItem.getImage())
                .apply(RequestOptions.centerCropTransform())
                .into(ivItemPhoto);

        imageUrl = foodItem.getImage();
    }

    private void selectImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(
                intent, "Select Image from here..."), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                ivItemPhoto.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(EditItemActivity.this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();
                                ref.getDownloadUrl().addOnCompleteListener(task -> {
                                    imageUrl = task.getResult().toString();
                                    Log.i("dfd","dfdf");
                                });
                                Toast.makeText(getApplicationContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                            })

                    .addOnFailureListener(e -> {
                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            });
        }
    }
}