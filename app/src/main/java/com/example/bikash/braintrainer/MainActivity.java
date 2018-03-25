package com.example.bikash.braintrainer;

import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button startButton, option1, option2, option3, option4;
    TextView timerView, questionView, scoreView, resultView;
    CountDownTimer countDownTimer;
    Random random;
    List<Button> buttonList;
    int answer, score, gameState,totalQuestions;
    String question;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVariables();
    }

    private void initializeVariables() {
        buttonList = new ArrayList();
        startButton = findViewById(R.id.startButton);
        option1 = findViewById(R.id.optionButton1);
        buttonList.add(option1);
        option2 = findViewById(R.id.optionButton2);
        buttonList.add(option2);
        option3 = findViewById(R.id.optionButton3);
        buttonList.add(option3);
        option4 = findViewById(R.id.optionButton4);
        buttonList.add(option4);
        timerView = findViewById(R.id.timerTextView);
        questionView = findViewById(R.id.questionTextView);
        questionView.setText("");
        scoreView = findViewById(R.id.scoreTextView);
        scoreView.setText("0/0");
        resultView = findViewById(R.id.resultTextView);
        resultView.setText("");
        random = new Random();
        score = 0;
        gameState = 0;
        totalQuestions = 0 ;


    }

    public void start(View view) {
        startButton.setVisibility(View.INVISIBLE);
        initializeVariables();
        startGame(view);

    }

    private void startGame(View view) {
        gameState = 1;
        startCountdown();
        generateQuestion();

    }

    private void generateQuestion() {
        int a = generateRandomNumberBetween(0, 100);
        int b = generateRandomNumberBetween(0, 100);
        String operator = generateRandomOperator();
        answer = calculateTotal(a, b, operator);
        question = String.valueOf(a) + operator + String.valueOf(b);
        questionView.setText(question);
        totalQuestions++;
        fillOptions();
    }

    private void fillOptions() {
        int randomIndex = generateRandomNumberBetween(0, 4);
        buttonList.get(randomIndex).setText(String.valueOf(answer));
        for (int i = 0; i < 4; i++) {
            if (i != randomIndex) {
                buttonList.get(i).setText(String.valueOf(generateRandomNumberBetween(-200, 200)));
            }
        }
    }


    private int calculateTotal(int a, int b, String operator) {

        switch (operator) {
            case "+":
                return a + b;
            case "-":
                return a - b;


        }
        return 0;

    }

    private void startCountdown() {
        Log.i("info", "StartCountdonw");
        countDownTimer = new CountDownTimer(30000, 1000) {


            @Override
            public void onTick(long millisecondsLeft) {
                timerView.setText(String.valueOf(millisecondsLeft / 1000) + "s");
            }

            @Override
            public void onFinish() {
                timerView.setText(String.valueOf("0s"));
                Log.i("info", "countdown completed");
                gameState = 0;
                startButton.setText("Play Again!!!");
                startButton.setVisibility(View.VISIBLE);
                resultView.setText("Your Score: "+score);
            }
        };
        countDownTimer.start();
    }

    private int generateRandomNumberBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }


    private String generateRandomOperator() {

        int op = generateRandomNumberBetween(0, 4);
        Log.i("info", String.valueOf(op));
        switch (op) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "+";
            case 3:
                return "-";

        }
        return null;
    }

    public void submitAnswer(View view) {
        if(gameState == 1){
            checkAnswer(view);
            generateQuestion();
        }

    }

    private void checkAnswer(View view) {

        switch (view.getId()) {
            case R.id.optionButton1:
                finalCheckAnswer(option1.getText().toString());
                break;

            case R.id.optionButton2:
                finalCheckAnswer(option2.getText().toString());
                break;

            case R.id.optionButton3:
                finalCheckAnswer(option3.getText().toString());
                break;

            case R.id.optionButton4:
                finalCheckAnswer(option4.getText().toString());
                break;
            default:

        }

    }

    private void finalCheckAnswer(String text) {
        try {
            if (answer == Integer.parseInt(text)) {
                showMessage(true);
                updateScore(true);
            } else {
                updateScore(false);
                showMessage(false);
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }


    }

    private void showMessage(Boolean isAnswerCorrect) {
        if (isAnswerCorrect)
            resultView.setText("Correct!!");
        else
            resultView.setText("Wrong");
    }

    private void updateScore(Boolean isAnswerCorrect) {
        if(isAnswerCorrect)
            scoreView.setText(String.valueOf(++score)+"/"+totalQuestions);
        else
            scoreView.setText(String.valueOf(score)+"/"+totalQuestions);
    }
}
