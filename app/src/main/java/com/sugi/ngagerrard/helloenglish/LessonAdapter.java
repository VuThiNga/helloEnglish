package com.sugi.ngagerrard.helloenglish;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by Nga Gerrard on 29/05/2018.
 */

public class LessonAdapter extends PagerAdapter {
    private ILessonAdapter mILessonAdapter;

    public LessonAdapter(ILessonAdapter mILessonAdapter) {
        this.mILessonAdapter = mILessonAdapter;
    }


    @Override
    public int getCount() {
        return mILessonAdapter.getCount();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View itemView = inflater.inflate(R.layout.custom_item_lesson, container, false);
        TextView tvLessonNum = (TextView)itemView.findViewById(R.id.tv_lesson_num);
        ImageView imgLesson = (ImageView)itemView.findViewById(R.id.img_lesson);
        TextView tvLessonName = (TextView)itemView.findViewById(R.id.tv_lesson_name);
        TextView tvNumWord = (TextView)itemView.findViewById(R.id.tv_num_word);
        Button btnStart = (Button)itemView.findViewById(R.id.btn_start);
        LinearLayout layout = (LinearLayout)itemView.findViewById(R.id.layout_adapter);

        Lesson data = mILessonAdapter.getData(position);
        tvLessonNum.setText("Lesson " + data.getLesson());
        tvLessonName.setText(data.getName());
        tvNumWord.setText("Số lượng từ vựng: " + data.getTotalWord());
        int drawableResourceId = imgLesson.getContext().getResources().
                getIdentifier(data.getImage(), "drawable", imgLesson.getContext().getPackageName());

//        String completePath = Environment.getExternalStorageDirectory() + "/" + data.getImage();
//
//        File file = new File(completePath);
//        Uri uri = Uri.fromFile(file);

        //Glide.with().load(uri).into(imageView);
        Glide.with(imgLesson.getContext()).load(drawableResourceId)
                .placeholder(R.drawable.sport).into(imgLesson);
        btnStart.setBackgroundResource(mILessonAdapter.customButton());
        layout.setBackgroundResource(mILessonAdapter.customBackground());
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mILessonAdapter.onClickStart(position);
            }
        });
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public interface ILessonAdapter {
        int getCount();
        Lesson getData(int position);
        void onClickStart(int position);
        int customButton();
        int customBackground();
    }
}
