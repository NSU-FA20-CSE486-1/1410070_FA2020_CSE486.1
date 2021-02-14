package food.restra.hungerbite.feature.chef.addfood.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.IOException;
import java.util.UUID;
import food.restra.hungerbite.R;
import food.restra.hungerbite.feature.customer.feed.model.FoodItem;
import food.restra.hungerbite.feature.login.model.Profile;

import static android.app.Activity.RESULT_OK;

public class FragmentAddItem extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int PICK_IMAGE_REQUEST = 22;
    private String mParam1;
    private String mParam2;
    private EditText etName, etPrice, etCategory;
    private RadioGroup timeRadioGroup;
    private RadioButton rbLunch, rbDinner, rbBreakfast, rbDesert;
    private ImageView ivItemPhoto, ivPhotoAdd;
    private AppCompatButton btAddItem;

    private Uri filePath;

    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseFirestore db;
    FirebaseAuth mAuth;
    String imageUrl;

    public FragmentAddItem() {
        // Required empty public constructor
    }

    public static FragmentAddItem newInstance(String param1, String param2) {
        FragmentAddItem fragment = new FragmentAddItem();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initView(view);
        super.onViewCreated(view, savedInstanceState);
    }

    public void initView(View view) {
        etName = view.findViewById(R.id.etName);
        etPrice = view.findViewById(R.id.etPrice);
        etCategory = view.findViewById(R.id.etCategory);
        timeRadioGroup = view.findViewById(R.id.rgFoodTime);
        rbLunch = view.findViewById(R.id.rbLunch);
        rbBreakfast = view.findViewById(R.id.rbBreakfast);
        rbDesert = view.findViewById(R.id.rbDesert);
        rbDinner = view.findViewById(R.id.rbDinner);
        ivItemPhoto = view.findViewById(R.id.ivItemPhoto);
        ivPhotoAdd = view.findViewById(R.id.ivPhotoAdd);
        btAddItem = view.findViewById(R.id.btAddItem);


        ivPhotoAdd.setOnClickListener(view12 -> {
            selectImage();
        });

        btAddItem.setOnClickListener(view1 -> {
            int selectedId = timeRadioGroup.getCheckedRadioButtonId();
            RadioButton rbTime = view.findViewById(selectedId);

            FoodItem item = new FoodItem();
            item.setTitle(etName.getText().toString());
            item.setPrice(etPrice.getText().toString());
            item.setCategory(rbTime.getText().toString().toLowerCase());
            item.setFoodCategory(etCategory.getText().toString());
            item.setImage(imageUrl);
            item.setUploaderId(mAuth.getCurrentUser().getUid());

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        Profile user = documentSnapshot.toObject(Profile.class);
                        if(user.getName() != null && user.getLocation() != null){
                            item.setUploaderImage(user.getImage());
                            item.setUploaderName(user.getName());
                            item.setLocation(user.getLocation());
                            item.setUploaderToken(user.getToken());
                            db.collection("posts")
                                    .add(item).addOnCompleteListener(task -> {
                                        progressDialog.dismiss();
                                        Toast.makeText(getContext(), "Item added successfully", Toast.LENGTH_SHORT).show();
                                    });
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Your profile is not complete", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), filePath);
                ivItemPhoto.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(filePath)
                .addOnSuccessListener(
                    taskSnapshot -> {
                        progressDialog.dismiss();
                        ref.getDownloadUrl().addOnCompleteListener(task -> {
                            imageUrl = task.getResult().toString();
                        });
                        Toast.makeText(getContext(), "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                    })

                .addOnFailureListener(e -> {
                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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