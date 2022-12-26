package com.ltmt5.fpoly_friend_app.ui.fragment;

import static com.ltmt5.fpoly_friend_app.App.TAG;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.RecentConversionAdapter;
import com.ltmt5.fpoly_friend_app.adapter.RecentlyAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentChatBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;
import com.ltmt5.fpoly_friend_app.listener.ConversionListener;
import com.ltmt5.fpoly_friend_app.model.ChatMessage;
import com.ltmt5.fpoly_friend_app.model.UserProfile;
import com.ltmt5.fpoly_friend_app.ui.activity.ChatActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.StoryActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment implements RecentlyAdapter.ItemClick, ConversionListener {
    FragmentChatBinding binding;
    MainActivity mainActivity;
    Context context;
    RecentlyAdapter recentlyAdapter;
    FirebaseUser user;
    DatabaseReference fcmToken;
    DatabaseReference availability;
    DatabaseReference userProfile;
    List<UserProfile> userProfileList = new ArrayList<>();
    ProgressDialog progressDialog;
    private PreferenceManager preferenceManager;
    private List<ChatMessage> conversions;
    private RecentConversionAdapter conversionAdapter;
    private final EventListener<QuerySnapshot> eventListener = (value, error) -> {
        if (error != null) {
            return;
        }
        if (value != null) {
            for (DocumentChange documentChange : value.getDocumentChanges()) {
                if (documentChange.getType() == DocumentChange.Type.ADDED) {
                    String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.senderId = senderId;
                    chatMessage.receiverId = receiverId;
                    if (preferenceManager.getString(Constants.KEY_USER_ID).equals(senderId)) {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_RECEIVER_IMAGE);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_RECEIVER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                    } else {
                        chatMessage.conversionImage = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        chatMessage.conversionName = documentChange.getDocument().getString(Constants.KEY_SENDER_NAME);
                        chatMessage.conversionId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                    }
                    chatMessage.message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                    chatMessage.dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                    conversions.add(chatMessage);
                } else if (documentChange.getType() == DocumentChange.Type.MODIFIED) {
                    for (int i = 0; i < conversions.size(); i++) {
                        String senderId = documentChange.getDocument().getString(Constants.KEY_SENDER_ID);
                        String receiverId = documentChange.getDocument().getString(Constants.KEY_RECEIVER_ID);
                        if (conversions.get(i).senderId.equals(senderId) && conversions.get(i).receiverId.equals(receiverId)) {
                            conversions.get(i).message = documentChange.getDocument().getString(Constants.KEY_LAST_MESSAGE);
                            conversions.get(i).dateObject = documentChange.getDocument().getDate(Constants.KEY_TIMESTAMP);
                            break;
                        }
                    }
                }
            }
            Collections.sort(conversions, (obj1, obj2) -> obj2.dateObject.compareTo(obj1.dateObject));
            conversionAdapter.notifyDataSetChanged();
            binding.recChat.smoothScrollToPosition(0);
            binding.recChat.setVisibility(View.VISIBLE);
        }
    };
    private FirebaseFirestore firestore;
    private FirebaseDatabase database;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(inflater);
        initView();
        setClick();
        return binding.getRoot();
    }

    private void getUsers() {
        progressDialog.show();
        userProfileList.clear();
        userProfileList.add(App.currentUser);
        userProfile.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressDialog.dismiss();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    if (userProfile != null) {
                        userProfileList.add(userProfile);
                    }
                }
                recentlyAdapter.setData(userProfileList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "fail to load all user");
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
//        availability.setValue(0);
    }

    @Override
    public void onResume() {
        super.onResume();
//        availability.setValue(1);
    }


    private void initView() {
        context = App.context;
        mainActivity = (MainActivity) getActivity();
        user = FirebaseAuth.getInstance().getCurrentUser();
        preferenceManager = new PreferenceManager(mainActivity);

        firestore = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");

        availability = database.getReference("user_profile/" + user.getUid() + "/availability");
        fcmToken = database.getReference("user_profile/" + user.getUid() + "/fcmToken");
        userProfile = database.getReference("user_profile_match/" + user.getUid());

        recentlyAdapter = new RecentlyAdapter(context, this);
        binding.recRecently.setAdapter(recentlyAdapter);
        getToken();
        init();
        listenConversation();
        getUsers();


    }

    private void init() {
        conversions = new ArrayList<>();
        conversionAdapter = new RecentConversionAdapter(conversions, App.context, this);
        binding.recChat.setAdapter(conversionAdapter);
    }

    private void listenConversation() {
        firestore.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        firestore.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);

//        FirebaseFirestore database = FirebaseFirestore.getInstance();
//        DocumentReference documentReference =
//                database.collection(Constants.KEY_COLLECTION_USERS).document(
//                        preferenceManager.getString(Constants.KEY_USER_ID)
//                );
//        documentReference.update(Constants.KEY_FCM_TOKEN, token)
//                .addOnFailureListener(e -> showToast("Unable to update token"));

        if (App.currentUser.getFcmToken() == null) {
            fcmToken.setValue(token, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    Log.e(TAG, "added token");
                }
            });
        }

    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void setClick() {

    }

    @Override
    public void clickItem(UserProfile userProfile) {
//        startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(Constants.KEY_USER, userProfile));
        startActivity(new Intent(getActivity(), StoryActivity.class).putExtra(Constants.KEY_USER, userProfile));
    }

    @Override
    public void onConversionClicked(UserProfile user) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}