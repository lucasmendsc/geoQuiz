package com.example.jarvis.geoquiz;

import android.app.Activity;
import android.content.Intent;
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
    private Button mCheatButton;
    private boolean mIsCheater;
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private TextView mQuestionTextView;
    private int idQuestion;
    private Question[] questionBank;
    Integer score = 0;

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
        questionTextViewListiner();
        this.mCheatButton = (Button) findViewById(R.id.cheat_button);
        cheatButtonListiner();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater =
                    CheatActivity.wasAnswerShown(data);
        }
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
                mIsCheater = false;
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

    private void questionTextViewListiner(){
        this.mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                        CheatActivity.class);
                startActivity(intent);
            }
        });
    }

    private void  cheatButtonListiner(){
        this.mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = questionBank[idQuestion].isAnswear();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    private void nextQuestion(){
        this.idQuestion = (this.idQuestion + 1) % this.questionBank.length;
        mIsCheater = false;
        int nextQuestion = questionBank[this.idQuestion].getId();
        this.mQuestionTextView.setText(nextQuestion);
        if(this.idQuestion == this.questionBank.length - 1){
            Toast.makeText(this,"O seu placar final foi de : " + score.toString(),Toast.LENGTH_LONG).show();
            this.score = 0;
        }
    }

    private void previousQuestion(){
        mIsCheater = false;
        if(this.idQuestion == 0){
            return;
        }else{
            this.idQuestion = (this.idQuestion - 1) % this.questionBank.length;
        }
        int nextQuestion = questionBank[this.idQuestion].getId();
        this.mQuestionTextView.setText(nextQuestion);
    }

    private void checkAnswer(boolean userAnswer) {
        boolean answerIsTrue = questionBank[idQuestion].isAnswear();
        int messageResId = 0;
        if (mIsCheater) {
            messageResId = R.string.judgment_string;
        } else {
            if (userAnswer == answerIsTrue) {
                messageResId = R.string.correct_answer;
                score++;
            } else {
                messageResId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
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
