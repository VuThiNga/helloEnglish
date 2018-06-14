package com.sugi.ngagerrard.helloenglish;

import java.io.Serializable;

/**
 * Created by Nga Gerrard on 28/05/2018.
 */

public class Word implements Serializable{
    private int id;
    private String lesson;
    private String word;
    private String pro;
    private String type;
    private String vi;
    private String exampleEn;
    private String exampleVi;
    private String mp3;
    private String mp3Example;
    private String fr;
    private String ja;
    private String ko;
    private int score;
    private boolean favourite;

    public Word(int id, String lesson, String word, String pro, String type, String vi,
                String exampleEn, String exampleVi, String mp3, String mp3Example,
                String fr, String ja, String ko, int score, boolean favourite) {
        this.id = id;
        this.lesson = lesson;
        this.word = word;
        this.pro = pro;
        this.type = type;
        this.vi = vi;
        this.exampleEn = exampleEn;
        this.exampleVi = exampleVi;
        this.mp3 = mp3;
        this.mp3Example = mp3Example;
        this.fr = fr;
        this.ja = ja;
        this.ko = ko;
        this.score = score;
        this.favourite = favourite;
    }

    public Word() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVi() {
        return vi;
    }

    public void setVi(String vi) {
        this.vi = vi;
    }

    public String getExampleEn() {
        return exampleEn;
    }

    public void setExampleEn(String exampleEn) {
        this.exampleEn = exampleEn;
    }

    public String getExampleVi() {
        return exampleVi;
    }

    public void setExampleVi(String exampleVi) {
        this.exampleVi = exampleVi;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getMp3Example() {
        return mp3Example;
    }

    public void setMp3Example(String mp3Example) {
        this.mp3Example = mp3Example;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }

    public String getJa() {
        return ja;
    }

    public void setJa(String ja) {
        this.ja = ja;
    }

    public String getKo() {
        return ko;
    }

    public void setKo(String ko) {
        this.ko = ko;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
