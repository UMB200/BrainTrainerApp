package com.example.umix.braintrainer;

import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static com.example.umix.braintrainer.R.id.gameLayout;


public class MainActivity extends AppCompatActivity {
    /**
     * 1 call function on Go button
     * @param savedInstanceState
     */

    CountDownTimer countDownTimer;
    TextView timerText, questionText, resultText, answerText;
    int gameTime = 5000;
    Button startBtn, btn0, btn1, btn2, btn3;
    //Layout mainLayout;
    ConstraintLayout gameLayout;
    ArrayList<Integer> results = new ArrayList<Integer>();
    int correctBtn;
    int score = 0;
    int attempts = 0;

    private void generateRandom(){
        //generate random number for question quiz
        Random randNumber = new Random();
        int sumA = randNumber.nextInt(21);
        int sumB = randNumber.nextInt(21);
        //assign sum of 2 random numbers to text box
        int sumOfAandB = sumA + sumB;
        questionText.setText(Integer.toString(sumA) + " + " + Integer.toString(sumB));
        //generate random number for buttons that would contain answers
        //since there are 4 buttons we limit to 4 numbers
        int numberOfOptions = 4;
        correctBtn = randNumber.nextInt(numberOfOptions);
        results.clear();

        for(int i = 0; i < numberOfOptions; i++){
            if(i == correctBtn){
                results.add(sumOfAandB);
            }
            else{
                //check to see if random number generated the same number as correctBtn
                int incorrectOption = randNumber.nextInt(41);
                //generate new number of incorrectOption generated the same number as correctBtn
                while (incorrectOption == sumOfAandB){
                    incorrectOption = randNumber.nextInt(41);
                }
                results.add(incorrectOption);
            }
        }

    }
    private void populateButtons(){
        btn0.setText(Integer.toString(results.get(0)));
        btn1.setText(Integer.toString(results.get(1)));
        btn2.setText(Integer.toString(results.get(2)));
        btn3.setText(Integer.toString(results.get(3)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn =(Button)findViewById(R.id.startBtn);
        startBtn.setVisibility(View.VISIBLE);
        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        timerText =(TextView)findViewById(R.id.timerText);
        answerText = findViewById(R.id.answerText);
        resultText = findViewById(R.id.resultText);
        GridLayout questionGrid = (GridLayout)findViewById(R.id.questionGrid);
        gameLayout = findViewById(R.id.gameLayout);
        questionText = (TextView)findViewById(R.id.questionText);
        gameLayout.setVisibility(View.INVISIBLE);
    }
    public void startGame(View view){

        startTimer(gameTime);
        startBtn.setVisibility(View.INVISIBLE);
        generateRandom();
        populateButtons();
    }
    //check if correct button is clicked
    public void resultCheck(View view){
        //if tag of clicked button matches to correct option
        if (Integer.toString(correctBtn).equals(view.getTag().toString())){
            answerText.setText("Correct");
            //if answer is correct update scores
            score++;
        } else {
            //if not just update textview
            answerText.setText("Wrong");
        }
        //update number of attempts with how many times button was clicked
        attempts++;
        resultText.setText(Integer.toString(score) + "/"+ Integer.toString(attempts));
        generateRandom();
        populateButtons();
    }
    private void startTimer(int startGameTime){
        answerText.setText("");
        countDownTimer = new CountDownTimer(startGameTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimerText((int)millisUntilFinished/1000);
            }
            @Override
            public void onFinish() {
                finishGame();
                gameLayout.setVisibility(View.INVISIBLE);
            }
        }.start();

    }
    private void updateTimerText(int timeParameter){
        int secondsLeft = timeParameter;
        String timeFormatString = String.format(Locale.getDefault(), "%02d", secondsLeft);
        timerText.setText(timeFormatString + "s");

    }
    
    private void finishGame(){
        answerText.setText("Game over");
        startBtn.setVisibility(View.VISIBLE);
        startBtn.setText("Play again");

    }
}