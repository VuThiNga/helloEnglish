package com.sugi.ngagerrard.helloenglish;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nga Gerrard on 26/05/2018.
 */

public class DatabaseManager {
    private static final String TAG = DatabaseManager.class.getSimpleName();
    //private static final String DB_NAME = "class6_v2.db";
    private String DB_NAME;

    private final String pathDb;

    private SQLiteDatabase sqliteManager;
    private Context context;

    public DatabaseManager(Context context, String DB_NAME) {
        this.DB_NAME = DB_NAME;
        this.context = context;
        pathDb = Environment.getDataDirectory() + File.separator + "data" +
                File.separator + context.getPackageName()
                //duong dan external của app hiện tại
                + File.separator + "database"
                + File.separator + DB_NAME;
    }

    public void openDB() {
        copyDB();
        if (sqliteManager == null ||
                !sqliteManager.isOpen()) {
            sqliteManager = SQLiteDatabase
                    .openDatabase(pathDb, null,
                            SQLiteDatabase.OPEN_READWRITE);
        }
    }

    public void closeDB() {
        if (sqliteManager != null && sqliteManager.isOpen()) {
            sqliteManager.close();
        }
    }

    public void copyDB() {
        String path
                = Environment.getDataDirectory()
                //duong dan external cua toan bo app
                + File.separator + "data"
                + File.separator + context.getPackageName()
                //duong dan external cua app hien tai
                + File.separator + "database";
        new File(path).mkdir();
        File file = new File(pathDb);
        if (file.exists()) {
            return;
        }
        //copy
        AssetManager manager = context.getAssets();
        try {
            InputStream in = manager.open(DB_NAME);
            OutputStream out = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int le = in.read(b);
            while (le > -1) {
                out.write(b, 0, le);
                le = in.read(b);
            }
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Lesson> getAllLesson() {
        List<Lesson> listLesson = new ArrayList<Lesson>();
        openDB();
        Cursor c = sqliteManager.rawQuery("SELECT * FROM lesson_tbl",
                null);

        if (c != null) {
            int inId = c.getColumnIndex("id");
            int inLesson = c.getColumnIndex("lesson");
            int inName = c.getColumnIndex("name");
            int inImg = c.getColumnIndex("img");
            int inTotalWord = c.getColumnIndex("total_word");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(inId);
                int lesson = c.getInt(inLesson);
                String name = c.getString(inName);
                String img = c.getString(inImg);
                int totalWord = c.getInt(inTotalWord);
                Lesson les = new Lesson(id, lesson, name, img, totalWord);
                listLesson.add(les);
                c.moveToNext();
            }
            c.close();
        }
        closeDB();
        return listLesson;
    }

    //test git
    public List<Word> getListWordByLesson(String lesson) {
        List<Word> words = new ArrayList<Word>();
        openDB();
        Cursor c = sqliteManager.rawQuery("SELECT * FROM word_tbl WHERE lesson = ?", new String[]{lesson});
        if (c != null) {
            int inId = c.getColumnIndex("_id");
            //int inLesson = c.getColumnIndex("lesson");
            int inWord = c.getColumnIndex("word");
            int inPro = c.getColumnIndex("pro");
            int inType = c.getColumnIndex("type");
            int inVi = c.getColumnIndex("vi");
            int inExampleEn = c.getColumnIndex("example_en");
            int inExampleVi = c.getColumnIndex("example_vi");
            int inMp3 = c.getColumnIndex("mp3");
            int inMp3Exampe = c.getColumnIndex("mp3_example");
            int inFr = c.getColumnIndex("fr");
            int inJa = c.getColumnIndex("ja");
            int inKo = c.getColumnIndex("ko");
            int inScore = c.getColumnIndex("score");
            int inFavourite = c.getColumnIndex("favourite");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(inId);
                String word = c.getString(inWord);
                String pro = c.getString(inPro);
                String type = c.getString(inType);
                String vi = c.getString(inVi);
                String exampleEn = c.getString(inExampleEn);
                String exampleVi = c.getString(inExampleVi);
                String mp3 = c.getString(inMp3);
                String mp3Example = c.getString(inMp3Exampe);
                String Fr = c.getString(inFr);
                String Ja = c.getString(inJa);
                String Ko = c.getString(inKo);
                int score = c.getInt(inScore);
                boolean favourite = (c.getInt(inFavourite)) != 0;
                Word wo = new Word(id, lesson, word, pro, type,
                        vi, exampleEn, exampleVi, mp3, mp3Example,
                        Fr, Ja, Ko, score, favourite);
                words.add(wo);
                c.moveToNext();
            }
        }
        closeDB();
        return words;
    }

    public void updateScore(int score, int id) {
        openDB();
        ContentValues cv = new ContentValues();
        cv.put("score", score);
        String where = "_id = " + id;
        sqliteManager.update("word_tbl", cv, where, null);
        closeDB();
    }

    public void updateFavourite(int id, boolean favourite) {
        openDB();
        ContentValues cv = new ContentValues();
        cv.put("favourite", favourite);
        String where = "_id = " + id;
        sqliteManager.update("word_tbl", cv, where, null);

        closeDB();
    }

    public void insertFavWord(Word word){
        openDB();
        ContentValues contentValues = new ContentValues();
        //contentValues.put("_id", word.getId());
        contentValues.put("lesson", word.getLesson());
        contentValues.put("word", word.getWord());
        contentValues.put("pro", word.getPro());
        contentValues.put("type", word.getType());
        contentValues.put("vi", word.getVi());
        contentValues.put("example_vi", word.getExampleVi());
        contentValues.put("example_en", word.getExampleEn());
        contentValues.put("mp3", word.getMp3());
        contentValues.put("mp3_example", word.getMp3Example());
        contentValues.put("fr", word.getFr());
        contentValues.put("ja", word.getJa());
        contentValues.put("ko", word.getKo());
        contentValues.put("score", word.getScore());
        contentValues.put("favourite", word.isFavourite());

        sqliteManager.insert("word_tbl",null, contentValues);
        closeDB();
    }

    public void deleteFavWord(Word word){
        openDB();
        sqliteManager.delete("word_tbl", "_id = ?", new String[]{
                word.getId() + ""
        } );
        closeDB();
    }
    public List<Word> getAllFavourite(){
        List<Word> words = new ArrayList<Word>();
        openDB();
        Cursor c = sqliteManager.rawQuery("SELECT * FROM word_tbl", null);
        if (c != null) {
            int inId = c.getColumnIndex("_id");
            int inLesson = c.getColumnIndex("lesson");
            int inWord = c.getColumnIndex("word");
            int inPro = c.getColumnIndex("pro");
            int inType = c.getColumnIndex("type");
            int inVi = c.getColumnIndex("vi");
            int inExampleEn = c.getColumnIndex("example_en");
            int inExampleVi = c.getColumnIndex("example_vi");
            int inMp3 = c.getColumnIndex("mp3");
            int inMp3Exampe = c.getColumnIndex("mp3_example");
            int inFr = c.getColumnIndex("fr");
            int inJa = c.getColumnIndex("ja");
            int inKo = c.getColumnIndex("ko");
            int inScore = c.getColumnIndex("score");
            int inFavourite = c.getColumnIndex("favourite");
            c.moveToFirst();
            while (!c.isAfterLast()) {
                int id = c.getInt(inId);
                String word = c.getString(inWord);
                String lesson = c.getString(inLesson);
                String pro = c.getString(inPro);
                String type = c.getString(inType);
                String vi = c.getString(inVi);
                String exampleEn = c.getString(inExampleEn);
                String exampleVi = c.getString(inExampleVi);
                String mp3 = c.getString(inMp3);
                String mp3Example = c.getString(inMp3Exampe);
                String Fr = c.getString(inFr);
                String Ja = c.getString(inJa);
                String Ko = c.getString(inKo);
                int score = c.getInt(inScore);
                boolean favourite = (c.getInt(inFavourite)) != 0;
                Word wo = new Word(id, lesson, word, pro, type,
                        vi, exampleEn, exampleVi, mp3, mp3Example,
                        Fr, Ja, Ko, score, favourite);
                words.add(wo);
                c.moveToNext();
            }
        }
        closeDB();
        return words;
    }
}

