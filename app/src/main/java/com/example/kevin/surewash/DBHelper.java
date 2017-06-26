package com.example.kevin.surewash;
        import java.util.Scanner;
        import android.content.Context;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;



public class DBHelper extends SQLiteOpenHelper {
    //This is a class to help the DBPREf class when accessing the questions on the sqlite file
    public static final String DB_NAME="quizDB";
    public static final int DB_CURRENT_VERSION=2;
    protected SQLiteDatabase db;
    protected Context ctx;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_CURRENT_VERSION);
        this.ctx = context;
        this.open();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("debug", "Creating DataBase");
        StringBuilder sb = new StringBuilder();

        Scanner sc = new Scanner( this.ctx.getResources().openRawResource(R.raw.database) );
        while(sc.hasNextLine()) {
            sb.append(sc.nextLine());
            sb.append('\n');
            if (sb.indexOf(";") != -1) {
                sqLiteDatabase.execSQL(sb.toString());
                sb.delete(0, sb.capacity());
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        Log.d("debug", "Updgrading database from version " + i + " to version " + i2);
    }

    public void open() {
        Log.d("debug", "Opening database");
        this.db = this.getWritableDatabase();
    }

    public void close() {
        Log.d("debug", "Closing database");
        this.db.close();
    }

    public void addSampleData() {
        try {
            this.db.execSQL("DELETE FROM `questions`;");
            this.db.execSQL("INSERT INTO `questions` (`question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`, `wrong_answer_3`, `wrong_answer_4`, `wrong_answer_5`, `category`)" +
                    "VALUES(\"In whikevinade was the American Institute of Electrical Engineers (AIEE) founded?\", \"1880s\", \"1850s\", \"1930s\", \"1950s\", \"1960s\", \"1860s\", 'G');");
            this.db.execSQL("INSERT INTO `questions` (`question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`, `wrong_answer_3`, `category`) " +
                    "VALUES(\"What is kevin database that holds only one type of information?\", \"Field\", \"Report\", \"Record\", \"File\", 'G');");
            this.db.execSQL("INSERT INTO `questions` (`question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`, `wrong_answer_3`, `category`)" +
                    "VALUES(\"'OS' compukevinbbreviation usually means?\", \"Operating System\", \"Open Software\", \"Order of Significance\", \"Optical Sensor\", 'G');");
            this.db.execSQL("INSERT INTO `questions` (`question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`, `wrong_answer_3`, `wrong_answer_4`, `wrong_answer_5`, `category`)" +
                    "VALUES(\"In which kevinth the first transatlantic radio broadcast occur?\", \"1900s\", \"1850s\", \"1860s\", \"1880s\", \"1910s\", \"1920s\", 'G');");
            this.db.execSQL("INSERT INTO `questions` (`question`, `correct_answer`, `wrong_answer_1`, `wrong_answer_2`, `wrong_answer_3`, `category`)" +
                    "VALUES(\"'.MOV' extkevinn refers usually to what kind of file?\", \"Animation/movie file\", \"Image file\", \"Audio file\", \"MS Office document\", 'G');");
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("debug", e.toString());
        }
    }
}

