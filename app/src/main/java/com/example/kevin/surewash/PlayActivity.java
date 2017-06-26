package com.example.kevin.surewash;

import java.util.LinkedList;
        import java.util.Queue;
        import android.app.Activity;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.Collection;
        import java.util.Iterator;
        import java.util.Queue;
        import java.util.LinkedList;

public class PlayActivity extends Activity implements View.OnClickListener {

    private static int QUESTIONS = 4;
    private int counter =0 ;
    private int quizNum = 1;
    private DBPref db;
    private TextView tv_question;
    private TextView questionCoutner;
    private Button option_1;
    private Button option_2;
    private Button option_3;
    private Button option_4;

    private Queue<Question> questions = new LinkedList<Question>();
    private Question active_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "PlayActivity.onCreate");
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_play);
        this.questionCoutner = (TextView) findViewById(R.id.textQuiz);
        this.tv_question = (TextView) this.findViewById(R.id.tv_question);
        this.option_1 = (Button) this.findViewById(R.id.btn_option_1);
        this.option_2 = (Button) this.findViewById(R.id.btn_option_2);
        this.option_3 = (Button) this.findViewById(R.id.btn_option_3);
        this.option_4 = (Button) this.findViewById(R.id.btn_option_4);

        this.db = new DBPref( this );
        Cursor questions = this.db.getQuestions(DBPref.Category.SUREWASH, DBPref.Difficulty.EASY, QUESTIONS);

        if (questions.moveToFirst()) {
            do {
                String question = questions.getString( questions.getColumnIndex("question") );
                String answer   = questions.getString( questions.getColumnIndex("correct_answer") );
                Queue<String> wrong  = new LinkedList<String>();

                wrong.offer( questions.getString( questions.getColumnIndex("wrong_answer_1") ) );
                wrong.offer( questions.getString( questions.getColumnIndex("wrong_answer_2") ) );
                wrong.offer( questions.getString( questions.getColumnIndex("wrong_answer_3") ) );

                for (int i = 1; i <= 7; i++) {
                    int idx = questions.getColumnIndex("wrong_answer_" + i);
                    if (idx != -1) {
                        wrong.offer( questions.getString(idx) );
                    } else {
                        break;
                    }
                }

                this.questions.offer( new Question(question, answer, (String[]) wrong.toArray(new String[wrong.size()])) );
            } while(questions.moveToNext());
        }

        this.db.close();

        this.setQuestion(this.questions.poll());

        this.option_1.setOnClickListener(this);
        this.option_2.setOnClickListener(this);
        this.option_3.setOnClickListener(this);
        this.option_4.setOnClickListener(this);
    }

    private void setQuestion(Question question) {
        this.active_question = question;

        String[] answers = question.getAnswers();
        questionCoutner.setText("Question " + quizNum);
        quizNum++;
        this.tv_question.setText( question.getQuestion() );
        this.option_1.setText( answers[0] );
        this.option_2.setText( answers[1] );
        this.option_3.setText( answers[2] );
        this.option_4.setText( answers[3] );
    }

    @Override
    public void onClick(View view) {
        Button clicked = (Button) view;
        if (clicked.getText().toString() == this.active_question.getAnswer()) {
            counter++;
            if (this.questions.size() > 0) {
                this.setQuestion( this.questions.poll() );
            } else if(this.questions.size() == 0 && counter ==4) {
                Toast t = Toast.makeText(this, "You win! \n"+ "Scored " + counter + " out of 4", Toast.LENGTH_LONG);
                t.show();
                this.finish();
            }else{
                Toast t = Toast.makeText(this, "You lose \n"+ "Scored " + counter + " out of 4", Toast.LENGTH_LONG);
                t.show();
                this.finish();
            }
        } else {
            if (this.questions.size() > 0) {
                this.setQuestion( this.questions.poll() );
            } else {
                Toast t = Toast.makeText(this, "You lose \n"+ "Scored " + counter + " out of 4", Toast.LENGTH_LONG);
                t.show();
                this.finish();
            }
        }
    }
}

