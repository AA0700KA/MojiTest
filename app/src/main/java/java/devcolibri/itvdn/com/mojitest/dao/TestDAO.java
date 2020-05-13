package java.devcolibri.itvdn.com.mojitest.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.devcolibri.itvdn.com.mojitest.pojo.Answer;
import java.devcolibri.itvdn.com.mojitest.pojo.Quiz;
import java.devcolibri.itvdn.com.mojitest.pojo.Test;
import java.util.ArrayList;
import java.util.List;

public class TestDAO {

    private static TestDAO instance;

    private DBHelper helper;

    private TestDAO(Context context) {
        helper = new DBHelper(context);
    }

    public static TestDAO getInstance(Context context) {
        if (instance == null) {
            instance = new TestDAO(context);
        }
        return instance;
    }

    public void addTest(Test test) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", test.getName());
        cv.put("verdict", test.getVerdict());
        long id = db.insert("tests", null, cv);
        test.setId((int)id);

        for (Quiz quiz : test.getQuizList()) {
            addQuiz(test, quiz);
        }

        helper.close();
    }

    private void addQuiz(Test test, Quiz quiz) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("question", quiz.getQuestion());
        cv.put("test_id", test.getId());
        long id = db.insert("quiz", null, cv);
        quiz.setId((int)id);

        for (Answer answer : quiz.getAnswers()) {
            addAnswer(quiz, answer);
        }

        helper.close();
    }

    private void addAnswer(Quiz quiz, Answer answer) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("answer", answer.getAnswer());
        cv.put("quiz_id", quiz.getId());
        cv.put("data", answer.getData());
        long id = db.insert("answer", null, cv);
        helper.close();
    }

    public void updateScore(Test test) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("result", test.getResult());
        int updated = db.update("tests", cv, "id = ?", new String[]{String.valueOf(test.getId())});
        Log.d("updated", "Updated = " + updated);
        helper.close();
    }

    public Test getTestById(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.query("tests",null, "id = ?",new String[]{String.valueOf(id)},null,null,null);

            if (cursor != null && cursor.moveToFirst()) {

                int nameColumnIndex = cursor.getColumnIndex("name");
                int resultColumnIndex = cursor.getColumnIndex("result");
                int verdictColumnIndex = cursor.getColumnIndex("verdict");

                String name = cursor.getString(nameColumnIndex);
                int result = cursor.getInt(resultColumnIndex);
                String verdict = cursor.getString(verdictColumnIndex);
                Test test = new Test(name);
                test.setId(id);
                test.setVerdict(verdict);
                test.setResult(result);
                List<Quiz> quizzes = getQuizByTest(test);
                test.setQuizList(quizzes);
                Log.d("testget", test.getName() + " = get");
                return test;

            }

        } finally {
            if (cursor != null)
                cursor.close();
            helper.close();
        }
        return null;
    }

    public List<Test> getAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Test> tests = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = db.query("tests",null, null,null,null,null,null);

            if (cursor != null && cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex("id");
                int nameColumnIndex = cursor.getColumnIndex("name");
                int resultColumnIndex = cursor.getColumnIndex("result");
                int verdictColumnIndex = cursor.getColumnIndex("verdict");

                do {
                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    int result = cursor.getInt(resultColumnIndex);
                    String verdict = cursor.getString(verdictColumnIndex);
                    Test test = new Test(name);
                    test.setId(id);
                    test.setVerdict(verdict);
                    test.setResult(result);
                    List<Quiz> quizzes = getQuizByTest(test);
                    test.setQuizList(quizzes);
                    tests.add(test);
                    Log.d("testget", test.getName() + " = get");
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            helper.close();
        }
        return tests;
    }

    private List<Quiz> getQuizByTest(Test test) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Quiz> quizzes = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = db.query("quiz", new String[]{"id", "question"}, "test_id = ?", new String[]{String.valueOf(test.getId())},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int idColumnIndex = cursor.getColumnIndex("id");
                int questionColumnIndex = cursor.getColumnIndex("question");

                do {
                    int id = cursor.getInt(idColumnIndex);
                    String question = cursor.getString(questionColumnIndex);
                    Quiz quiz = new Quiz(question);
                    quiz.setId(id);
                    List<Answer> answers = getAnswerByQuiz(quiz);
                    quiz.setAnswers(answers);
                    quizzes.add(quiz);
                    Log.d("testget", quiz.getQuestion() + " = get by " + test.getName());
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            helper.close();
        }

        return quizzes;
    }

    private List<Answer> getAnswerByQuiz(Quiz quiz) {
        SQLiteDatabase db = helper.getWritableDatabase();
        List<Answer> answers = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = db.query("answer", new String[]{"answer", "data"}, "quiz_id = ?", new String[]{String.valueOf(quiz.getId())},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int answerColumnIndex = cursor.getColumnIndex("answer");
                int dataColumnIndex = cursor.getColumnIndex("data");

                do {
                    int data = cursor.getInt(dataColumnIndex);
                    String answerName = cursor.getString(answerColumnIndex);
                    Answer answer = new Answer(answerName, data);
                    answers.add(answer);
                    Log.d("testget", answer.getAnswer() + " = get by " + quiz.getQuestion());
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null)
                cursor.close();
            helper.close();
        }
        return answers;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, "personal", null, 1);
        }

        public void onCreate(SQLiteDatabase db) {

            db.execSQL("create table tests ("
                    + "id integer primary key autoincrement,"
                    + "name text,"
                    + "result integer default 0,"
                    + "verdict text" +
                    ")");

            db.execSQL("create table quiz ("
                    + "id integer primary key autoincrement,"
                    + "question text,"
                    + "test_id integer,"
                    + "FOREIGN KEY(test_id) REFERENCES tests(id));");

            db.execSQL("create table answer ("
                    + "id integer primary key autoincrement,"
                    + "answer text,"
                    + "data integer,"
                    + "quiz_id integer,"
                    + "FOREIGN KEY(quiz_id) REFERENCES quiz(id));");

            List<Test> tests = initStartTests();
            ContentValues cv = new ContentValues();

            Log.d("testadd", tests.size() + " = size");

            for (Test test : tests) {
                cv.clear();
                cv.put("name", test.getName());
                cv.put("verdict", test.getVerdict());
                long id = db.insert("tests", null, cv);
                for (Quiz quiz : test.getQuizList()) {
                    cv.clear();
                    cv.put("question", quiz.getQuestion());
                    cv.put("test_id", (int)id);
                    long idQuiz = db.insert("quiz", null, cv);
                    Log.d("testadd", quiz.getQuestion() + " = insert");
                    for (Answer answer : quiz.getAnswers()) {
                        cv.clear();
                        cv.put("answer", answer.getAnswer());
                        cv.put("data", answer.getData());
                        cv.put("quiz_id", (int)idQuiz);
                        db.insert("answer", null, cv);
                        Log.d("testadd", answer.getAnswer() + " = insert");
                    }
                }
            }


        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        private List<Test> initStartTests() {
            List<Test> tests = new ArrayList<>();

            Test test1 = new Test("Test 1");
            Quiz quiz1 = new Quiz("Answer 1?");
            quiz1.addAnswer(new Answer("A1", 3));
            quiz1.addAnswer(new Answer("A2", 2));
            quiz1.addAnswer(new Answer("A3", 1));
            test1.addQuiz(quiz1);
            Quiz quiz2 = new Quiz("Answer 2?");
            quiz2.addAnswer(new Answer("A1", 1));
            quiz2.addAnswer(new Answer("A2", 2));
            quiz2.addAnswer(new Answer("A3", 3));
            test1.addQuiz(quiz2);
            Quiz quiz3 = new Quiz("Answer 3?");
            quiz3.addAnswer(new Answer("A5", 2));
            quiz3.addAnswer(new Answer("A6", 3));
            quiz3.addAnswer(new Answer("A7", 1));
            test1.addQuiz(quiz3);
            test1.setVerdict("If 3-4 - You bad, 5-6 - you look 7-9 you nice");

            Test test2 = new Test("Test 2");
            Quiz quiz11 = new Quiz("Answer 1?");
            quiz11.addAnswer(new Answer("This", 3));
            quiz11.addAnswer(new Answer("Ather", 2));
            quiz11.addAnswer(new Answer("Another", 1));
            test2.addQuiz(quiz11);
            Quiz quiz21 = new Quiz("Answer 22?");
            quiz21.addAnswer(new Answer("Second", 1));
            quiz21.addAnswer(new Answer("A2", 2));
            quiz21.addAnswer(new Answer("Ather", 3));
            test2.addQuiz(quiz21);
            Quiz quiz31 = new Quiz("Answer 3333?");
            quiz31.addAnswer(new Answer("Third", 2));
            quiz31.addAnswer(new Answer("A6", 3));
            quiz31.addAnswer(new Answer("Another", 1));
            test2.addQuiz(quiz31);
            test2.setVerdict("If 3-4 - You bad, 5-6 - you look 7-9 you nice");

            tests.add(test1);
            tests.add(test2);
            return tests;
        }

    }

}
