//package com.ltmt5.fpoly_friend_app.ui.activity;
//
//import android.annotation.SuppressLint;
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Canvas;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Base64;
//import android.view.MotionEvent;
//import android.view.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.ltmt5.fpoly_friend_app.App;
//import com.ltmt5.fpoly_friend_app.R;
//import com.ltmt5.fpoly_friend_app.adapter.StoryAdapter;
//import com.ltmt5.fpoly_friend_app.databinding.ActivityStoryBinding;
//import com.ltmt5.fpoly_friend_app.model.StoryItem;
//
//import java.io.ByteArrayOutputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//import jp.shts.android.storiesprogressview.StoriesProgressView;
//
//public class StoryActivity extends AppCompatActivity implements StoriesProgressView.StoriesListener {
//
//    public static List<StoryItem> list = new ArrayList<>();
//    StoryAdapter storyAdapter;
//    long pressTime = 0L;
//    long limit = 500L;
//    String name = "Aesthetic";
//    ActivityStoryBinding binding;
//    List<Bitmap> bitmapList = new ArrayList<>();
//    private int counter = 0;
//    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    pressTime = System.currentTimeMillis();
//                    binding.stories.pause();
//                    return false;
//                case MotionEvent.ACTION_UP:
//                    long now = System.currentTimeMillis();
//                    binding.stories.resume();
//                    return limit < now - pressTime;
//            }
//            return false;
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityStoryBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        initView();
//        setClick();
//    }
//
//    private void initView() {
//        Intent intent = getIntent();
//        if (intent.getStringExtra(App.STORY) != null) {
//            name = intent.getStringExtra(App.STORY);
//        }
//        new LoadTask(list).execute();
//
//    }
//    void startStory(){
//        if (bitmapList.size()!=0){
//            binding.stories.setStoriesCount(bitmapList.size()); // <- set stories
//            binding.stories.setStoryDuration(5000L); // <- set a story duration
//            binding.stories.setStoriesListener(this); // <- set listener
//            binding.stories.startStories(counter); // <- start progress
//            storyAdapter = new StoryAdapter(this, bitmapList);
//            binding.viewPager.setAdapter(storyAdapter);
//            binding.viewPager.setCurrentItem(counter);
//        }
//    }
//
//    private void setClick() {
//        binding.btnCancel.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
//
//        binding.reverse.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.stories.reverse();
//            }
//        });
//        binding.reverse.setOnTouchListener(onTouchListener);
//
//        // bind skip view
//        binding.skip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                binding.stories.skip();
//            }
//        });
//        binding.skip.setOnTouchListener(onTouchListener);
//    }
//
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(this, MainActivity.class));
//    }
//
//    @Override
//    public void onNext() {
//        if ((counter + 1) == list.size()) return;
//
//        binding.viewPager.setCurrentItem(++counter);
//
//    }
//
//    @Override
//    public void onPrev() {
//        if ((counter - 1) < 0) return;
//        binding.viewPager.setCurrentItem(--counter);
//    }
//
//    @Override
//    public void onComplete() {
//        startActivity(new Intent(this, MainActivity.class));
//    }
//
//    @Override
//    protected void onDestroy() {
//        // Very important !
//        binding.stories.destroy();
//        super.onDestroy();
//    }
//
//    public Bitmap drawableToBitmap(Drawable drawable) {
//        Bitmap bitmap;
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
//            if (bitmapDrawable.getBitmap() != null) {
//                return bitmapDrawable.getBitmap();
//            }
//        }
//
//        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
//            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
//        } else {
//            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        }
//
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }
//
//    public String convertBitmapToArray(Bitmap bm) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
//        byte[] b = baos.toByteArray();
//        return Base64.encodeToString(b, Base64.DEFAULT);
//    }
//
//    public Bitmap getBitmapFromArray(String encoded) {
//        byte[] imageAsBytes = Base64.decode(encoded.getBytes(), Base64.DEFAULT);
//        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    class LoadTask extends AsyncTask<Void, Void, Void> {
//        ProgressDialog progressDialog;
//        List<StoryItem> list;
//
//        LoadTask(List<StoryItem> list) {
//            this.list = list;
//        }
//
//        protected void onPreExecute() {
//            this.progressDialog = new ProgressDialog(StoryActivity.this);
//            this.progressDialog.setCancelable(false);
//            this.progressDialog.setMessage("Loading...");
//            this.progressDialog.show();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            bitmapList.clear();
//            for (int i = 0; i < 10; i++) {
//                bitmapList.add(drawableToBitmap(ContextCompat.getDrawable(StoryActivity.this, R.drawable.demo1)));
//            }
//            return null;
//        }
//
//        @SuppressLint({"WrongConstant"})
//        protected void onPostExecute(Void v) {
//            try {
//                this.progressDialog.dismiss();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            startStory();
//        }
//    }
//}