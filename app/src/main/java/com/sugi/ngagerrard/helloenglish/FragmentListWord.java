package com.sugi.ngagerrard.helloenglish;

import android.nfc.tech.TagTechnology;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Nga Gerrard on 28/05/2018.
 */

public class FragmentListWord extends Fragment implements
        WordRecyclerViewAdapter.IWordRecyclerAdapter, View.OnClickListener, SearchView.OnQueryTextListener {
    private ImageView imgBack, imgFavorite;
    private SearchView imgSearch;
    private TextView tvLesson;
    private RecyclerView rcvListWord;
    private WordRecyclerViewAdapter adapter;
    private List<Word> words;
    private String dbPath;
    private int lesson;
    private TextToSpeech textToSpeech;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_word, container, false);
        inits(view);
        return view;
    }

    private void inits(View view) {
        words = new ArrayList<Word>();
        imgBack = view.findViewById(R.id.img_back);
        imgFavorite = view.findViewById(R.id.img_favourite);
        imgSearch = view.findViewById(R.id.img_search);
        tvLesson = view.findViewById(R.id.tv_lesson);
        imgBack.setOnClickListener(this);
        imgFavorite.setOnClickListener(this);
        imgSearch.setOnQueryTextListener(this);

        Bundle bundle = this.getArguments();
        dbPath = bundle.getString("dbWord");
        lesson = bundle.getInt("lesson");
        tvLesson.setText(bundle.getString("detailLesson"));
        if(lesson != 0){
            words = getWords();
        }
        else {
            words = getWordsFav();
        }
        rcvListWord = view.findViewById(R.id.rcv_list_word);
        rcvListWord.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new WordRecyclerViewAdapter(this);
        rcvListWord.setAdapter(adapter);
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }

    private List<Word> getWordsFav() {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        return databaseManager.getAllFavourite();
    }

    private List<Word> getWords() {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        return databaseManager.getListWordByLesson(lesson + "");
    }

    @Override
    public int getCount() {
        if(words == null){
            return 0;
        }else {
            return words.size();
        }
    }

    @Override
    public Word getData(int position) {
        return words.get(position);
    }

    @Override
    public void onClickStar(int position) {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        if(words.get(position).isFavourite()){
            databaseManager.updateFavourite(words.get(position).getId(), false);
            words.get(position).setFavourite(false);
            deleteFromFavourite(words.get(position));
        }else {
            databaseManager.updateFavourite(words.get(position).getId(), true);
            words.get(position).setFavourite(true);
            insertToFavorite(words.get(position));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickSpeaker(int position) {
//        if(textToSpeech != null){
//            textToSpeech.stop();
//            textToSpeech.shutdown();
//        }
        String wordSpeak = words.get(position).getWord();
        textToSpeech.speak(wordSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onClickLayout(int position) {
        FragmentDetailWord fragmentDetailWord = new FragmentDetailWord();
        Bundle bundle = new Bundle();
        bundle.putInt("position", position);
        bundle.putString("dbWord", dbPath);
        bundle.putInt("lesson", lesson);
        bundle.putSerializable("detailWord", words.get(position));
        fragmentDetailWord.setArguments(bundle);
        setFragment(fragmentDetailWord);
    }


    public void insertToFavorite(Word word){
        DatabaseManager databaseManager = new DatabaseManager(getContext(), "favourite.sqlite");
        databaseManager.insertFavWord(word);
    }
    public void deleteFromFavourite(Word word){
        DatabaseManager databaseManager = new DatabaseManager(getContext(), "favourite.sqlite");
        databaseManager.deleteFavWord(word);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                getActivity().onBackPressed();
                break;
            case R.id.img_favourite:
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<Word> list = new ArrayList<Word>();
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).getWord().contains(s)){
                Word wordSearch = words.get(i);
                list.add(wordSearch);
            }
        }
        words.clear();
        words.addAll(list);
        if(s.equals("")){
            getWords();
        }
        adapter.notifyDataSetChanged();
        return false;
    }

    public void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onPause() {
        if(textToSpeech != null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
