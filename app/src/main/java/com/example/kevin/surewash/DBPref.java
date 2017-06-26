package com.example.kevin.surewash;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class DBPref extends DBHelper {
    //This class allows you to make different categories and change the difficultie of the quiz if you would like to do so

    public static enum Category {
        HISTORY('A'),
        GEOGRAPHY('B'),
        LITERATURE('C'),
        ART('D'),
        SPORTS('E'),
        SCIENCE('F'),
        SUREWASH('G'),
        ASTRONOMY('H'),
        OTHER('Z');

        public final char C;
        Category(char c) {
            this.C = c;
        }
    }

    public static enum Difficulty {
        EASY('e'),
        MEDIUM('m'),
        HARD('h');

        public final char D;

        Difficulty(char d) {
            this.D = d;
        }
    }

    public DBPref(Context context) {
        super(context);
    }

    public void addRecord(Question question) {
        ContentValues values = new ContentValues();
        values.put("question", question.getQuestion());
        values.put("correct_answer", question.getAnswer());

        String[] wrong_questions = question.getWrongAnswers();
        for(int i = 0; i < wrong_questions.length; i++) {
            values.put("wrong_answer_" + i, wrong_questions[i]);
        }

        db.insert("questions", null, values);
    }

    public void addRecords(Question[] questions) {
        for(int i = 0; i < questions.length; i++) {
            this.addRecord(questions[i]);
        }
    }

    public void updateRecord(Question[] id, Question question) {
        String strFilter = "_id=" + id;
        ContentValues values = new ContentValues();
        values.put("question", question.getQuestion());
        values.put("correct_answer", question.getAnswer());

        String[] wrong_questions = question.getWrongAnswers();
        for(int i = 0; i < wrong_questions.length; i++) {
            values.put("wrong_answer_" + i, wrong_questions[i]);
        }

        db.update("questions", values, strFilter, null);
    }

    public Cursor getQuestion(Category c, Difficulty d) {
        return this.db.rawQuery("SELECT `question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`," +
                        "`wrong_answer_3` FROM `questions` WHERE category = ? AND difficulty = ?" +
                        "ORDER BY RANDOM() LIMIT 1",
                new String[]{String.valueOf(c.C), String.valueOf(d.D)});
    }

    public Cursor getQuestions(Category c, Difficulty d, int limit) {
        return this.db.rawQuery("SELECT `question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`," +
                        "`wrong_answer_3` FROM `questions` WHERE category = ? AND difficulty = ?" +
                        "ORDER BY RANDOM() LIMIT ?",
                new String[]{String.valueOf(c.C), String.valueOf(d.D), String.valueOf(limit)});
    }
}

