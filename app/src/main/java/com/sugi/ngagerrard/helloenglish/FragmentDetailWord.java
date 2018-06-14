package com.sugi.ngagerrard.helloenglish;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Nga Gerrard on 29/05/2018.
 */

public class FragmentDetailWord extends Fragment implements View.OnClickListener {
    private static final int CODE_SUCCEDD = 100;
    private ImageView imgClose, imgPrevious, imgNext, imgSpeakWord, imgSpeakSentence, imgSpeechToText;
    private TextView tvWord, tvPro, tvVi, tvExam, tvExamVi;
    private ImageView imgFavourite;
    private TextToSpeech textToSpeech;
    private FrameLayout layout;
    private int position;
    private List<Word> list = new ArrayList<Word>();
    String dbPath;
    int lesson;
    private TextView tvScore;
    private ImageView imgScore;
    Word word;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_word, container, false);
        inits(view);
        return view;
    }

    private void inits(View view) {
        Bundle bundle = this.getArguments();
        word = (Word) bundle.getSerializable("detailWord");
        position = bundle.getInt("position");
        lesson = bundle.getInt("lesson");
        dbPath = bundle.getString("dbWord");
        if(lesson != 0){
            list = getWords(dbPath, lesson);
        }
        else {
            list = getFavourite(dbPath);
        }

        layout = view.findViewById(R.id.layout_speech);
        imgClose = view.findViewById(R.id.img_close);
        imgPrevious = view.findViewById(R.id.img_previous);
        imgNext = view.findViewById(R.id.img_next);
        imgSpeakWord = view.findViewById(R.id.img_speak_word);
        imgSpeakSentence = view.findViewById(R.id.img_speak_sentences);
        tvWord = view.findViewById(R.id.tv_word_detail);
        tvPro = view.findViewById(R.id.tv_pro_detail);
        tvVi = view.findViewById(R.id.tv_vi_detail);
        tvExam = view.findViewById(R.id.tv_example_detail);
        tvExamVi = view.findViewById(R.id.tv_exam_vi_detail);
        imgFavourite = view.findViewById(R.id.img_add_favourite);
        imgSpeechToText = view.findViewById(R.id.img_speech_to_text);
        tvScore = view.findViewById(R.id.tv_score_detail);
        imgScore = view.findViewById(R.id.img_score_detail);

//        tvWord.setText(word.getWord());
//        tvPro.setText(word.getPro());
//        tvVi.setText(word.getVi());
//        tvExam.setText(word.getExampleEn());
//        tvExamVi.setText(word.getExampleVi());

        updateUI(position);
        imgClose.setOnClickListener(this);
        imgPrevious.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgSpeakWord.setOnClickListener(this);
        imgSpeakSentence.setOnClickListener(this);
        imgFavourite.setOnClickListener(this);
        imgSpeechToText.setOnClickListener(this);
        textToSpeech = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
    }

    private List<Word> getFavourite(String dbPath) {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        return databaseManager.getAllFavourite();
    }

    private List<Word> getWords(String dbPath, int lesson) {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        return databaseManager.getListWordByLesson(lesson + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                CloseFragment();
                break;
            case R.id.img_previous:
                OpenPreviousWord();
                break;
            case R.id.img_next:
                OpenNextWord();
                break;
            case R.id.img_speak_word:
                SpeakWord();
                break;
            case R.id.img_speak_sentences:
                SpeakSentence();
                break;
            case R.id.img_add_favourite:
                UpdateFavourite();
                break;
            case R.id.img_speech_to_text:
                SpeechToText();
                break;
            default:
                break;
        }
    }

    private void SpeechToText() {
        checkPermission();
        Animation animationScale = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_anim);
        layout.startAnimation(animationScale);
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something");
        try {
            startActivityForResult(intent, CODE_SUCCEDD);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(),
                    getString(R.string.speech_not_support),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_SUCCEDD:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String output = result.get(0);
                    int score = checkPronunciation(output, word.getWord());
                    updateScore(score);
                }
                break;
        }
    }

    private int checkPronunciation(String output, String word) {
        int charEqual = 0;
        List<Character> charWord = new ArrayList<Character>();
        List<Character> charOutPut = new ArrayList<Character>();
        for (int i = 0; i < word.length(); i++) {
            charWord.add(word.charAt(i));
        }
        for (int i = 0; i < output.length(); i++) {
            charOutPut.add(output.charAt(i));
        }

        if (word.length() >= output.length()) {
            for (int i = 0; i < output.length(); i++) {
                if (charOutPut.get(i) == charWord.get(i)) {
                    charEqual++;
                }
            }
            double scoreDevice = charEqual * 1.0 / word.length();
            if (scoreDevice < 0.2) {
                charEqual = 1;
            } else if (scoreDevice < 0.4 && scoreDevice >= 0.2) {
                charEqual = 2;
            } else if (scoreDevice < 0.6 && scoreDevice >= 0.4) {
                charEqual = 3;
            } else if (scoreDevice < 0.9 && scoreDevice >= 0.6) {
                charEqual = 4;
            } else if (scoreDevice <= 1 && scoreDevice >= 0.9) {
                charEqual = 5;
            } else {
                charEqual = 0;
            }
        }
        if (word.length() < output.length()) {
            for (int i = 0; i < word.length(); i++) {
                if (charOutPut.get(i) == charWord.get(i)) {
                    charEqual++;
                }
            }
            double scoreDevice = charEqual * 1.0 / word.length();
            if (scoreDevice < 0.2) {
                charEqual = 1;
            } else if (scoreDevice < 0.4 && scoreDevice >= 0.2) {
                charEqual = 2;
            } else if (scoreDevice < 0.6 && scoreDevice >= 0.4) {
                charEqual = 3;
            } else if (scoreDevice < 0.9 && scoreDevice >= 0.6) {
                charEqual = 4;
            } else if (scoreDevice <= 1 && scoreDevice >= 0.9) {
                charEqual = 5;
            } else {
                charEqual = 0;
            }
        }
        return charEqual;
    }

    private void updateScore(int score) {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        databaseManager.updateScore(score, word.getId());
        tvScore.setText(score +  "");
        if(score == 0){
            imgScore.setImageResource(R.drawable.ic_star_blue_grey_200_24dp);
        }else {
            imgScore.setImageResource(R.drawable.ic_star_yellow_700_24dp);
        }

    }

    private void UpdateFavourite() {
        DatabaseManager databaseManager = new DatabaseManager(getContext(), dbPath);
        list = databaseManager.getListWordByLesson(lesson + "");
        if (list.get(position).isFavourite()) {
            imgFavourite.setImageResource(R.drawable.ic_star_blue_grey_200_24dp);
            databaseManager.updateFavourite(list.get(position).getId(), false);
            deleteFromFavourite(list.get(position));
        } else {
            imgFavourite.setImageResource(R.drawable.ic_star_yellow_700_24dp);
            databaseManager.updateFavourite(list.get(position).getId(), true);
            insertToFavorite(list.get(position));
        }
    }
    public void insertToFavorite(Word word){
        DatabaseManager databaseManager = new DatabaseManager(getContext(), "favourite.sqlite");
        databaseManager.insertFavWord(word);
    }
    public void deleteFromFavourite(Word word){
        DatabaseManager databaseManager = new DatabaseManager(getContext(), "favourite.sqlite");
        databaseManager.deleteFavWord(word);
    }
    private void SpeakSentence() {
        String wordSpeak = tvExam.getText().toString();
        textToSpeech.speak(wordSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void SpeakWord() {
        Animation animationScale = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_anim);
        imgSpeakWord.startAnimation(animationScale);
        String wordSpeak = tvWord.getText().toString();
        textToSpeech.speak(wordSpeak, TextToSpeech.QUEUE_FLUSH, null);
    }

    private void OpenNextWord() {
        if (position == list.size() - 1) {
            position = 0;
        } else {
            position++;
        }
        updateUI(position);
    }

    private void OpenPreviousWord() {
        if (position == 0) {
            position = list.size() - 1;
        } else {
            position--;
        }
        updateUI(position);
    }

    private void updateUI(int position) {
        tvWord.setText(list.get(position).getWord());
        tvPro.setText(list.get(position).getPro());
        tvVi.setText(list.get(position).getVi());
        tvExam.setText(list.get(position).getExampleEn());
        tvExamVi.setText(list.get(position).getExampleVi());
        if (list.get(position).isFavourite()) {
            imgFavourite.setImageResource(R.drawable.ic_star_yellow_700_24dp);
        } else {
            imgFavourite.setImageResource(R.drawable.ic_star_blue_grey_200_24dp);
        }
        tvScore.setText(list.get(position).getScore() + "");
        if(list.get(position).getScore() == 0){
            imgScore.setImageResource(R.drawable.ic_star_blue_grey_200_24dp);
        }else {
            imgScore.setImageResource(R.drawable.ic_star_yellow_700_24dp);
        }

    }

    void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!(ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getContext().getPackageName()));
                startActivity(intent);
                getActivity().finish();
            }
        }
    }

    private void CloseFragment() {
        getActivity().onBackPressed();
    }

    @Override
    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}
