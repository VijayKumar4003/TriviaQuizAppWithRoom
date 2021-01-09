package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    TextView txtpreviousCoins;
    TextView txtTotalQuizQues,txtCorrectQues,txtWrongQues;

    Button btStartTrivaQuiz;
    Button btPlayScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        txtCorrectQues = findViewById(R.id.txtCorrectQuesR);
        txtWrongQues = findViewById(R.id.txtWrongQuesR);
        txtpreviousCoins = findViewById(R.id.txtTotalCoinsR);
        txtTotalQuizQues = findViewById(R.id.txtTotalQuestionsR);

        btStartTrivaQuiz = findViewById(R.id.bt_play_again);
        btPlayScreen = findViewById(R.id.bt_play_screen);


        Intent intent = getIntent();

        int coins = intent.getIntExtra(Constants.COINS,0);
        int totalQues = intent.getIntExtra(Constants.TOTAL_QUES,0);
        int correctQues = intent.getIntExtra(Constants.CORRECT,0);
        int wrongQues = intent.getIntExtra(Constants.WRONG,0);


        txtpreviousCoins.setText(Constants.COINS + String.valueOf(coins));
        txtTotalQuizQues.setText(Constants.TOTAL_QUES + String.valueOf(totalQues));
        txtCorrectQues.setText(Constants.CORRECT + String.valueOf(correctQues));
        txtWrongQues.setText(Constants.WRONG + String.valueOf(wrongQues));



        btPlayScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this,PlayScreen.class);
                startActivity(intent);
            }
        });

        btStartTrivaQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Result.this,QuizActivity.class);
                startActivity(intent);
            }
        });

    }
}
