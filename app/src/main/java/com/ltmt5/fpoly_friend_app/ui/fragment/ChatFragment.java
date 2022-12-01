package com.ltmt5.fpoly_friend_app.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ltmt5.fpoly_friend_app.App;
import com.ltmt5.fpoly_friend_app.adapter.ChatAdapter;
import com.ltmt5.fpoly_friend_app.adapter.RecentConversionAdapter;
import com.ltmt5.fpoly_friend_app.adapter.RecentlyAdapter;
import com.ltmt5.fpoly_friend_app.databinding.FragmentChatBinding;
import com.ltmt5.fpoly_friend_app.help.utilities.Constants;
import com.ltmt5.fpoly_friend_app.help.utilities.PreferenceManager;
import com.ltmt5.fpoly_friend_app.listener.ConversionListener;
import com.ltmt5.fpoly_friend_app.model.Chat;
import com.ltmt5.fpoly_friend_app.model.ChatMessage;
import com.ltmt5.fpoly_friend_app.model.User;
import com.ltmt5.fpoly_friend_app.ui.activity.ChatActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.MainActivity;
import com.ltmt5.fpoly_friend_app.ui.activity.UsersActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//public class ChatFragment extends Fragment implements ChatAdapter.ItemClick, RecentlyAdapter.ItemClick {
public class ChatFragment extends Fragment implements RecentlyAdapter.ItemClick, ConversionListener {
    FragmentChatBinding binding;
    MainActivity mainActivity;
    Context context;
    ChatAdapter chatAdapter;
    RecentlyAdapter recentlyAdapter;

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
    private FirebaseFirestore database;
    private DocumentReference documentReference;

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onPause() {
        super.onPause();
        documentReference.update(Constants.KEY_AVAILABILITY, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        documentReference.update(Constants.KEY_AVAILABILITY, 1);
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

    private void initView() {
        context = App.context;
        mainActivity = (MainActivity) getActivity();

        preferenceManager = new PreferenceManager(mainActivity);
        database = FirebaseFirestore.getInstance();
        documentReference = database.collection(Constants.KEY_COLLECTION_USERS)
                .document(preferenceManager.getString(Constants.KEY_USER_ID));


        recentlyAdapter = new RecentlyAdapter(getList(), context, this);
        binding.recRecently.setAdapter(recentlyAdapter);
        getToken();
        init();
        listenConversation();


    }

    private void init() {
        conversions = new ArrayList<>();
        conversionAdapter = new RecentConversionAdapter(conversions, this);
        binding.recChat.setAdapter(conversionAdapter);
        database = FirebaseFirestore.getInstance();
    }

    private void listenConversation() {
        database.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .whereEqualTo(Constants.KEY_SENDER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
        database.collection(Constants.KEY_COLLECTION_CONVERSATION)
                .whereEqualTo(Constants.KEY_RECEIVER_ID, preferenceManager.getString(Constants.KEY_USER_ID))
                .addSnapshotListener(eventListener);
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(this::updateToken);
    }

    private void updateToken(String token) {
        preferenceManager.putString(Constants.KEY_FCM_TOKEN, token);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference =
                database.collection(Constants.KEY_COLLECTION_USERS).document(
                        preferenceManager.getString(Constants.KEY_USER_ID)
                );
        documentReference.update(Constants.KEY_FCM_TOKEN, token)
                .addOnFailureListener(e -> showToast("Unable to update token"));
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void setClick() {

    }

    private List<Chat> getList() {
        List<Chat> list = new ArrayList<>();
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        list.add(new Chat("img_recently", "Người dùng giấu tên", "Có hoạt động gần đây, tương tác liền", "08:43", "1"));
        return list;
    }

    @Override
    public void clickItem(Chat chat) {
//        startActivity(new Intent(getActivity(), StoryActivity.class));
        startActivity(new Intent(getActivity(), UsersActivity.class));
    }

    @Override
    public void onConversionClicked(User user) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra(Constants.KEY_USER, user);
        startActivity(intent);
    }
}