package food.restra.hungerbite.feature.chef.profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import food.restra.hungerbite.feature.login.model.Profile;

import static android.app.Activity.RESULT_OK;

public class ChefProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final int PICK_IMAGE_REQUEST = 22;
    private String mParam1;
    private String mParam2;
    private EditText etName;
    private Spinner spLocation;
    private ImageView ivItemPhoto, ivPhotoAdd;
    private Uri filePath;
    String imageUrl = "";
    private Gson gson;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button btEdit;

    public ChefProfileFragment() {
        // Required empty public constructor
    }

    public static ChefProfileFragment newInstance(String param1, String param2) {
        ChefProfileFragment fragment = new ChefProfileFragment();
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
        initInstances();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chef_profile, container, false);
    }

    void initInstances(){
        gson = new Gson();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);
        loadData();
        super.onViewCreated(view, savedInstanceState);
    }

    void initUi(View view){
        etName = view.findViewById(R.id.etName);
        spLocation = view.findViewById(R.id.spnnerLocation);
        btEdit = view.findViewById(R.id.btEdit);
        ivPhotoAdd = view.findViewById(R.id.ivPhotoAdd);
        ivItemPhoto = view.findViewById(R.id.ivItemPhoto);
        btEdit.setOnClickListener(view1 -> {

            Profile item = new Profile();
            item.setName(etName.getText().toString());
            item.setEmail(mAuth.getCurrentUser().getEmail());
            item.setImage(imageUrl);
            item.setLocation(spLocation.getSelectedItem().toString());
            item.setType("chef");

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            db.collection("users")
                    .document(mAuth.getCurrentUser().getUid())
                    .set(item).addOnCompleteListener(task -> {
                Toast.makeText(getContext(), "Item Added Successfully", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            });
        });
        ivPhotoAdd.setOnClickListener(view12 -> {
            selectImage();
        });
    }

    private void loadData(){
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.location));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLocation.setAdapter(spinnerAdapter);

        db.collection("users")
                .document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    Profile user = documentSnapshot.toObject(Profile.class);
                    if(user.getName() != null){
                        etName.setText(user.getName());
                    }
                    if(user.getLocation() != null){
                        int spinnerPosition = spinnerAdapter.getPosition(user.getLocation());
                        spLocation.setSelection(spinnerPosition);
                    }

                    if(user.getImage() != null){
                        Glide.with(getContext())
                                .load(user.getImage())
                                .apply(RequestOptions.centerCropTransform())
                                .into(ivItemPhoto);
                        imageUrl = user.getImage();
                    }
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
                                    Log.i("dfd","dfdf");
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