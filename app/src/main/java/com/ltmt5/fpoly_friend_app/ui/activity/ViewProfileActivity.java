package com.ltmt5.fpoly_friend_app.ui.activity;

import static com.ltmt5.fpoly_friend_app.App.currentUser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.databinding.ActivityViewProfileBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.model.UserProfile;

import java.util.Date;
import java.util.HashMap;

public class ViewProfileActivity extends AppCompatActivity {
    ActivityViewProfileBinding binding;
    UserProfile mUserProfile;
    String TAG = "AAA";
    String tempText = "Hi chào cậu, cậu ăn cơm chưa ?";
    String uID;
    private FirebaseFirestore firebaseFirestore;
    private String conversionId = null;
    private final OnCompleteListener<QuerySnapshot> conversionCompleteListener = task -> {
        if (task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() > 0) {
            DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
            conversionId = documentSnapshot.getId();
        }
    };
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        setClick();
    }

    private void initView() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mUserProfile = (UserProfile) getIntent().getSerializableExtra(Constants.KEY_USER);
        uID = getIntent().getStringExtra(Constants.KEY_USER_ID);
        if (uID != null) {
            binding.layoutFooter.setVisibility(View.INVISIBLE);
            for (UserProfile userProfile : App.userProfileList) {
                if (userProfile.getUserId().equals(uID)) {
                    mUserProfile = userProfile;
                }
            }
        }
        if (mUserProfile != null) {
            binding.tvName.setText(mUserProfile.getName());
            binding.tvAge.setText("" + (2022 - mUserProfile.getAge()));
            binding.tvDistance.setText(mUserProfile.getEducation());
            if (mUserProfile.getDescription() != null) {
                binding.tvDescription.setText(mUserProfile.getDescription());
            } else {
                binding.tvDescription.setText(mUserProfile.getGender());
            }
            Glide.with(this).load(mUserProfile.getImageUri()).centerCrop().into(binding.imgAvatar);
        }

    }

    private void setClick() {
        binding.btnLike.setOnClickListener(v -> {
            sendMessage();
            DatabaseReference myRef = firebaseDatabase.getReference("user_profile_match/" + currentUser.getUserId() + "/" + mUserProfile.getUserId());
            mUserProfile.setAvailability(0);
            myRef.setValue(mUserProfile, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    onBackPressed();
                }
            });

        });
        binding.btnSkip.setOnClickListener(v -> {
            DatabaseReference myRef = firebaseDatabase.getReference("user_profile_match/" + currentUser.getUserId() + "/" + mUserProfile.getUserId());
            myRef.removeValue(new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    onBackPressed();
                }
            });

        });

        binding.btnBan.setOnClickListener(v -> {
            startActivity(new Intent(this, BanActivity.class).putExtra(Constants.KEY_USER, mUserProfile));
        });
    }

    private void updateConversion(String message) {
        DocumentReference documentReference =
                firebaseFirestore.collection(Constants.KEY_COLLECTION_CONVERSATION).document(conversionId);
        documentReference.update(
                Constants.KEY_LAST_MESSAGE, message,
                Constants.KEY_TIMESTAMP, new Date()
        );
    }

    private void addConversion(HashMap<String, Object> conversion) {
        firebaseFirestore.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .add(conversion)
                .addOnSuccessListener(documentReference -> conversionId = documentReference.getId());
    }

    private void sendMessage() {
        HashMap<String, Object> message = new HashMap<>();
        message.put(Constants.KEY_SENDER_ID, App.currentUser.getUserId());
        message.put(Constants.KEY_RECEIVER_ID, mUserProfile.getUserId());
        message.put(Constants.KEY_MESSAGE, tempText);
        message.put(Constants.KEY_TIMESTAMP, new Date());
        firebaseFirestore.collection(Constants.KEY_COLLECTION_CHAT).add(message);

        HashMap<String, Object> conversion = new HashMap<>();
        conversion.put(Constants.KEY_SENDER_ID, App.currentUser.getUserId());
        conversion.put(Constants.KEY_SENDER_NAME, App.currentUser.getName());
        conversion.put(Constants.KEY_SENDER_IMAGE, App.currentUser.getImageUri());
        conversion.put(Constants.KEY_RECEIVER_ID, mUserProfile.getUserId());
        conversion.put(Constants.KEY_RECEIVER_NAME, mUserProfile.getName());
        conversion.put(Constants.KEY_RECEIVER_IMAGE, mUserProfile.getImageUri());
        conversion.put(Constants.KEY_LAST_MESSAGE, tempText);
        conversion.put(Constants.KEY_TIMESTAMP, new Date());
        addConversion(conversion);

    }
}