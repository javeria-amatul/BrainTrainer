package com.example.android.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import static android.R.attr.angle;
import static android.R.attr.button;
import static android.R.id.button1;
import static android.R.id.button2;
import static android.R.id.button3;
import static com.example.android.braintrainer.R.id.result;
import static com.example.android.braintrainer.R.id.startBtn;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> answers = new ArrayList<Integer>();
    int locationOfCorrectAnswer;
    Button startBtn, button0, button1, button2, button3;
    int score = 0;
    int numberOfQuestions = 0;
    TextView result, pointTextView, sumTextView, timerTextView, playAgain;
    RelativeLayout appRunning;
    GridLayout options;

    private void disableButtons(GridLayout layout) {

        // Get all touchable views
        ArrayList<View> layoutButtons = layout.getTouchables();

        // loop through them, if they are an instance of Button, disable it.
        for(View v : layoutButtons){
            if( v instanceof Button ) {
                ((Button)v).setEnabled(false);
            }
        }
    }
    public  void playAgain(View view){
        score = 0;
        button0.setEnabled(true);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        numberOfQuestions = 0;
        timerTextView.setText("30s");
        pointTextView.setText("0/0");
        result.setText("");
        playAgain.setVisibility(View.INVISIBLE);
        generateQuestion();

        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish() {
                playAgain.setVisibility(View.VISIBLE);
                timerTextView.setText("0s");
                result.setText("Your score: " + Integer.toString(score) + "/" + Integer.toString(numberOfQuestions));
                disableButtons(options);

            }
        }.start();
    }
    public void generateQuestion(){
        Random rand = new Random();
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);
        sumTextView.setText(Integer.toString(a) + " + " + Integer.toString(b));
        locationOfCorrectAnswer = rand.nextInt(4);
        answers.clear();
        int incorrectAnswer;
        for (int i=0; i < 4; i++){

            if(i == locationOfCorrectAnswer){
                answers.add(a+b);
            }
            else{
                incorrectAnswer = rand.nextInt(61);
                while(incorrectAnswer == a + b){
                    incorrectAnswer = rand.nextInt(61);
                }
                answers.add(incorrectAnswer);
            }
        }
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }
    public void chooseAnswer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            score++;
            result.setText("Correct!");

        }
        else {
            result.setText("Wrong!");
        }
        numberOfQuestions++;
        pointTextView.setText(score+"/"+numberOfQuestions);
        generateQuestion();
    }

    public void start(View view){
        startBtn.setVisibility(view.INVISIBLE);
        appRunning.setVisibility(View.VISIBLE);
        playAgain(findViewById(R.id.playAgain));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startBtn = (Button) findViewById(R.id.startBtn);
         button0 = (Button) findViewById(R.id.first);
         button1 = (Button) findViewById(R.id.second);
         button2 = (Button) findViewById(R.id.third);
         button3 = (Button) findViewById(R.id.fourth);
        result=  (TextView) findViewById(R.id.result);
        playAgain = (Button) findViewById(R.id.playAgain);
        pointTextView = (TextView) findViewById(R.id.pointTextView);
        sumTextView = (TextView) findViewById(R.id.addNum);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        appRunning = (RelativeLayout) findViewById(R.id.appRunning);
        options = (GridLayout) findViewById(R.id.gridLayout);


    }
}
