package com.sugi.ngagerrard.helloenglish;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Nga Gerrard on 31/05/2018.
 */

public class FragmentClassList extends Fragment implements View.OnClickListener {
    // private TranferDataFragment mTranferData;
    private FrameLayout layout6;
    private FrameLayout layout7;
    private FrameLayout layout8;
    private FrameLayout layout9;
    private FrameLayout layout10;
    private FrameLayout layout11;
    private FrameLayout layout12;
    private ImageView imgSetting;

    FragmentLesson fragmentLesson;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_class_fragment, container, false);
        inits(view);
        return view;
    }

    private void inits(View view) {
        imgSetting = view.findViewById(R.id.img_setting);
        layout6 = view.findViewById(R.id.class_6);
        layout7 = view.findViewById(R.id.class_7);
        layout8 = view.findViewById(R.id.class_8);
        layout9 = view.findViewById(R.id.class_9);
        layout10 = view.findViewById(R.id.class_10);
        layout11 = view.findViewById(R.id.class_11);
        layout12 = view.findViewById(R.id.class_12);
        layout6.setOnClickListener(this);
        layout7.setOnClickListener(this);
        layout8.setOnClickListener(this);
        layout9.setOnClickListener(this);
        layout10.setOnClickListener(this);
        layout11.setOnClickListener(this);
        layout12.setOnClickListener(this);
        imgSetting.setOnClickListener(this);
        fragmentLesson = new FragmentLesson();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_setting:
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.inflate(R.menu.menu_favourite);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.item_favourite:
                                Bundle bundle = new Bundle();
                                bundle.putString("dbWord", "favourite.sqlite");
                                bundle.putString("detailLesson", "Favourite");
                                bundle.putInt("lesson", 0);
                                FragmentListWord fragmentListWord = new FragmentListWord();
                                fragmentListWord.setArguments(bundle);
                                setFragment(fragmentListWord);
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                break;
            case R.id.class_6:
                Bundle bundle = new Bundle();
                bundle.putString("db", "class6_v2.db");
                bundle.putString("class", "Class 6");
                fragmentLesson.setArguments(bundle);
                setFragment(fragmentLesson);
                break;
            //mTranferData.tranferDataClass("class6_v2.db");
            case R.id.class_7:
                bundle = new Bundle();
                bundle.putString("db", "class7_v2.db");
                bundle.putString("class", "Class 7");
                fragmentLesson.setArguments(bundle);
                setFragment(fragmentLesson);
                break;
            //mTranferData.tranferDataClass("class7_v2.db");
            case R.id.class_8:
                bundle = new Bundle();
                bundle.putString("db", "class8_v2.db");
                bundle.putString("class", "Class 8");
                fragmentLesson.setArguments(bundle);
                setFragment(fragmentLesson);
                break;
            //mTranferData.tranferDataClass("class8_v2.db");
            case R.id.class_9:
                bundle = new Bundle();
                bundle.putString("db", "class9_v2.db");
                bundle.putString("class", "Class 9");
                fragmentLesson.setArguments(bundle);
                setFragment(fragmentLesson);
                break;
            //mTranferData.tranferDataClass("class9_v2.db");
            case R.id.class_10:
                bundle = new Bundle();
                bundle.putString("db", "class10_v2.db");
                bundle.putString("class", "Class 10");
                fragmentLesson.setArguments(bundle);
                setFragment(fragmentLesson);
                break;
            //mTranferData.tranferDataClass("class10_v2.db");
            case R.id.class_11:
                bundle = new Bundle();
                bundle.putString("db", "class11_v2.db");
                fragmentLesson.setArguments(bundle);
                bundle.putString("class", "Class 11");
                setFragment(fragmentLesson);
                break;
            //mTranferData.tranferDataClass("class11_v2.db");
            case R.id.class_12:
                bundle = new Bundle();
                bundle.putString("db", "class12_v2.db");
                bundle.putString("class", "Class 12");
                fragmentLesson.setArguments(bundle);
                //mTranferData.tranferDataClass("class12_v2.db");
                setFragment(fragmentLesson);
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

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        try{
//            mTranferData = (TranferDataFragment) context;
//        }catch (ClassCastException e){
//            throw new ClassCastException(context.toString() + " must implement TranferDataFragment");
//        }
//
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mTranferData = null;
//    }
}
