package com.example.jarvis.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPreviousButton;
    private TextView mQuestionTextView;
    private int idQuestion;
    private Question[] questionBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buttonsInstance();
        this.createQuestionBank();

        int question = questionBank[idQuestion].getId();
        mQuestionTextView.setText(question);
    }

    private void buttonsInstance(){
        this.mTrueButton = (Button) findViewById(R.id.true_button);
        trueButtonListiner();
        this.mFalseButton = (Button) findViewById(R.id.false_button);
        falseButtonListiner();
        this.mNextButton = (Button) findViewById(R.id.next_button);
        nextButtonListiner();
        this.mPreviousButton = (Button) findViewById(R.id.previous_button);
        previousButtonListiner();
        this.mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
    }

    private void trueButtonListiner(){
        this.mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                nextQuestion();
            }
        });
    }

    private void falseButtonListiner(){
        this.mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                nextQuestion();
            }
        });
    }

    private void nextButtonListiner(){
        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
    }

    private void previousButtonListiner(){
        this.mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousQuestion();
            }
        });
    }

    private void nextQuestion(){
        this.idQuestion = (this.idQuestion + 1) % this.questionBank.length;
        int nextQuestion = questionBank[this.idQuestion].getId();
        this.mQuestionTextView.setText(nextQuestion);
    }

    private void previousQuestion(){
        int nextQuestion = 0;
        if(this.idQuestion == 0){
            this.idQuestion = (questionBank.length - 1);
        }else{
            this.idQuestion = (this.idQuestion - 1) % this.questionBank.length;
            this.mQuestionTextView.setText(nextQuestion);
        }
        nextQuestion = questionBank[this.idQuestion].getId();
        this.mQuestionTextView.setText(nextQuestion);
    }

    private void checkAnswer(boolean userAnswer){
        boolean realAnswer = this.questionBank[idQuestion].isAnswear();
        int result = (realAnswer == userAnswer)?
                R.string.correct_answer : R.string.incorrect_answer;

        Toast.makeText(this,result,Toast.LENGTH_LONG).show();
    }

    private void createQuestionBank(){
        this.questionBank  =
                new Question[]{
                        new Question(R.string.question_africa,true),
                        new Question(R.string.question_argentina,true),
                        new Question(R.string.question_brazil,false),
                        new Question(R.string.question_canada,true),
                        new Question(R.string.question_italy,false),
                        new Question(R.string.question_recife,true),
                        new Question(R.string.question_maple,true),
                        new Question(R.string.question_russia,true),
                        new Question(R.string.question_Switzerland,true),
                        new Question(R.string.question_usa,false)
                };
        Collections.shuffle(Arrays.asList(questionBank));
    }
}
