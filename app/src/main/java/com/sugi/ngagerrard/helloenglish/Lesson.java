package com.sugi.ngagerrard.helloenglish;

/**
 * Created by Nga Gerrard on 28/05/2018.
 */

public class Lesson {
    private int id;
    private int lesson;
    private String name;
    private String image;
    private int totalWord;

    public Lesson(int lesson, String name, String image, int totalWord) {
        this.lesson = lesson;
        this.name = name;
        this.image = image;
        this.totalWord = totalWord;
    }

    public Lesson(int id, int lesson, String name, String image, int totalWord) {
        this.id = id;
        this.lesson = lesson;
        this.name = name;
        this.image = image;
        this.totalWord = totalWord;
    }

    public Lesson() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLesson() {
        return lesson;
    }

    public void setLesson(int lesson) {
        this.lesson = lesson;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getTotalWord() {
        return totalWord;
    }

    public void setTotalWord(int totalWord) {
        this.totalWord = totalWord;
    }
}
