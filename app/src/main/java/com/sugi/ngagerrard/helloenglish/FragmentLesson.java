package com.sugi.ngagerrard.helloenglish;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nga Gerrard on 29/05/2018.
 */

public class FragmentLesson extends Fragment implements LessonAdapter.ILessonAdapter, View.OnClickListener {
    private ImageView imgBackClass;
    private TextView tvClass;
    private LinearLayout layoutClass;
    private ViewPager viewPagerLesson;
    private LessonAdapter adapter;
    private List<Lesson> lessons;
    private String dbPath;
    private String numberClass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        inits(view);
        return view;
    }

    private void inits(View view) {
        lessons = new ArrayList<Lesson>();
        layoutClass = view.findViewById(R.id.layout_class);
        imgBackClass = view.findViewById(R.id.img_back_class);
        tvClass = view.findViewById(R.id.tv_class);
        imgBackClass.setOnClickListener(this);
        viewPagerLesson = view.findViewById(R.id.list_lesson);
        Bundle bundle = this.getArguments();
        dbPath = bundle.getString("db");
        numberClass = bundle.getString("class");
        tvClass.setText(numberClass);
        customColor(numberClass);
        getLessons();
        adapter = new LessonAdapter(this);
        viewPagerLesson.setAdapter(adapter);
    }

    private void customColor(String numberClass) {
        switch (numberClass){
            case "Class 6":
                layoutClass.setBackgroundResource(R.color.color_class_6);
                break;
            case "Class 7":
                layoutClass.setBackgroundResource(R.color.color_class_7);
                break;
            case "Class 8":
                layoutClass.setBackgroundResource(R.color.color_class_8);
                break;
            case "Class 9":
                layoutClass.setBackgroundResource(R.color.color_class_9);
                break;
            case "Class 10":
                layoutClass.setBackgroundResource(R.color.color_class_10);
                break;
            case "Class 11":
                layoutClass.setBackgroundResource(R.color.color_class_11);
                break;
            case "Class 12":
                layoutClass.setBackgroundResource(R.color.color_class_12);
                break;
            default:
                break;
        }
    }

    private void getLessons() {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        lessons = databaseManager.getAllLesson();

    }

    @Override
    public int getCount() {
        if(lessons == null){
            return 0;
        }else {
            return lessons.size();
        }
    }

    @Override
    public Lesson getData(int position) {
        return lessons.get(position);
    }

    @Override
    public void onClickStart(int position) {
        FragmentListWord fragmentListWord = new FragmentListWord();
        Bundle bundle = new Bundle();
        bundle.putInt("lesson", lessons.get(position).getLesson());
        bundle.putString("dbWord", dbPath);
        bundle.putString("detailLesson", lessons.get(position).getName());
        fragmentListWord.setArguments(bundle);
        setFragment(fragmentListWord);
    }

    @Override
    public int customButton() {
        switch (numberClass){
            case "Class 6":
                return R.drawable.shape_button6;
            case "Class 7":
                return R.drawable.shape_button7;
            case "Class 8":
                return R.drawable.shape_button_8;
            case "Class 9":
                return R.drawable.shape_button9;
            case "Class 10":
                return R.drawable.shape_button10;
            case "Class 11":
                return R.drawable.shape_button11;
            case "Class 12":
                return R.drawable.shape_button12;
            default:
               return 0;
        }
    }

    @Override
    public int customBackground() {
        switch (numberClass){
            case "Class 6":
                return R.color.color_class_6;
            case "Class 7":
                return R.color.color_class_7;
            case "Class 8":
                return R.color.color_class_8;
            case "Class 9":
                return R.color.color_class_9;
            case "Class 10":
                return R.color.color_class_10;
            case "Class 11":
                return R.color.color_class_11;
            case "Class 12":
                return R.color.color_class_12;
            default:
                return 0;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back_class:
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
