package com.infowithvijay.triviaquizappwithroom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    Button buttonA,buttonB,buttonC,buttonD;
    TextView questionText,txtTotalQuestion,timeText,txtCoin,txtCorrect,txtWrong;



    private QuestionsViewModel questionsViewModelob;

    List<Questions> list;

    Questions currentQuestion;

    int qid = 1;

    int sizeOfQuiz = 8;

    Handler handler = new Handler();
    Handler handler2 = new Handler();

    AnimationDrawable anim;

    CountDownTimer countDownTimer;
    int timeValue = 130;

    TimerDialog timerDialog;

    int correct = 0;
    int wrong = 0;
    int coins = 0;

    Animation shakeAnimation;

    Animation correctAnsAnimation;

    int MUSIC_FLAG = 0;
    PlaySound playSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionText = findViewById(R.id.txtTriviaQuestion);
        txtTotalQuestion = findViewById(R.id.txtTotalQuestions);
        txtCoin = findViewById(R.id.txtCoin);
        timeText = findViewById(R.id.txtTimer);
        txtCorrect = findViewById(R.id.txtCorrect);
        txtWrong = findViewById(R.id.txtWrong);

        buttonA = findViewById(R.id.buttonA);
        buttonB = findViewById(R.id.buttonB);
        buttonC = findViewById(R.id.buttonC);
        buttonD = findViewById(R.id.buttonD);

        timerDialog = new TimerDialog(this);

        txtTotalQuestion.setText(qid + "/" + sizeOfQuiz);

        txtCorrect.setText(String.valueOf(correct));
        txtWrong.setText(String.valueOf(wrong));
        txtCoin.setText(String.valueOf(correct));

        playSound = new PlaySound(this);

        shakeAnimation = AnimationUtils.loadAnimation(this,R.anim.incorrect_animation);
        shakeAnimation.setRepeatCount(3);

        correctAnsAnimation = AnimationUtils.loadAnimation(this,R.anim.right_ans_animation);
        correctAnsAnimation.setRepeatCount(3);

        countDownTimer = new CountDownTimer(32000,1000) {
            @Override
            public void onTick(long l) {


                timeText.setText(String.valueOf(timeValue));

                timeValue -= 1;


                if (timeValue == -1){

                   disableButtons();

                   MUSIC_FLAG = 3;
                   playSound.seAudioforAnswers(MUSIC_FLAG);
                   timerDialog.timerDialog();

                }
            }

            @Override
            public void onFinish() {

            }
        }.start();




        questionsViewModelob = ViewModelProviders.of(this).get(QuestionsViewModel.class);
        questionsViewModelob.getAllQuestions().observe(this, new Observer<List<Questions>>() {
            @Override
            public void onChanged(List<Questions> questions) {

                fetchQuestions(questions);

            }
        });

    }

    private void fetchQuestions(List<Questions> questions){

        list = questions;

        Collections.shuffle(list);

        currentQuestion = list.get(qid);

        updateQueAnsOptions();


    }

    private void updateQueAnsOptions() {

        buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.buttonBG));
        buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.buttonBG));
        buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.buttonBG));
        buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.buttonBG));

        questionText.setText(currentQuestion.getQuestion());
        buttonA.setText(currentQuestion.getOptA());
        buttonB.setText(currentQuestion.getOptB());
        buttonC.setText(currentQuestion.getOptC());
        buttonD.setText(currentQuestion.getOptD());


        countDownTimer.cancel();
        countDownTimer.start();


    }

    private void SetNewQuestion(){

        qid++;

        txtTotalQuestion.setText(qid + "/" + sizeOfQuiz);

        currentQuestion = list.get(qid);

        enableButtons();

        updateQueAnsOptions();

    }


    private void correctAns(int correct){
        txtCorrect.setText(String.valueOf(correct));
    }


    private void wrongAns(int wrong){
        txtWrong.setText(String.valueOf(wrong));
    }


    public void buttonA(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonA.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonA.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())){

                    buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    buttonA.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);

                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);

                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));

                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {

                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    buttonA.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);
                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){
                                buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else if(currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
                                buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else {
                                buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {

                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);



    }

    public void buttonB(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonB.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonB.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){

                    buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    buttonB.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);
                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {
                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    buttonB.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);
                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptA().equals(currentQuestion.getAnswer())){
                                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else if(currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
                                buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else {
                                buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {
                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);


    }

    public void buttonC(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonC.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonC.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptC().equals(currentQuestion.getAnswer())){

                    buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    buttonC.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);
                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {
                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    buttonC.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);
                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){
                                buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else if(currentQuestion.getOptA().equals(currentQuestion.getAnswer())) {
                                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else {
                                buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {
                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);



    }

    public void buttonD(View view) {

        countDownTimer.cancel();

        disableButtons();

        buttonD.setBackgroundResource(R.drawable.flash_animation);
        anim = (AnimationDrawable) buttonD.getBackground();
        anim.start();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (currentQuestion.getOptD().equals(currentQuestion.getAnswer())){

                    buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                    buttonD.startAnimation(correctAnsAnimation);
                    correct++;
                    correctAns(correct);
                    MUSIC_FLAG = 1;
                    playSound.seAudioforAnswers(MUSIC_FLAG);
                    coins = coins + 10;
                    txtCoin.setText(String.valueOf(coins));
                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){

                                SetNewQuestion();

                            }else {
                                finalQuizData();

                            }

                        }
                    },2000);

                }else {

                    buttonD.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.red));
                    buttonD.startAnimation(shakeAnimation);
                    wrong++;
                    wrongAns(wrong);

                    MUSIC_FLAG = 2;
                    playSound.seAudioforAnswers(MUSIC_FLAG);

                    Handler handler3 = new Handler();
                    handler3.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (currentQuestion.getOptB().equals(currentQuestion.getAnswer())){
                                buttonB.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else if(currentQuestion.getOptC().equals(currentQuestion.getAnswer())) {
                                buttonC.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }else {
                                buttonA.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.green));
                            }

                        }
                    },2000);


                    handler2.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (qid!= sizeOfQuiz){
                                SetNewQuestion();
                            }else {
                                finalQuizData();

                            }

                        }
                    },3000);

                }


            }
        },5000);


    }


    @Override
    protected void onResume() {
        super.onResume();

        countDownTimer.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }

        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (countDownTimer!=null){
            countDownTimer.cancel();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (countDownTimer!=null){
            countDownTimer.cancel();
            finish();
        }

    }


    private void disableButtons(){

        buttonA.setEnabled(false);
        buttonB.setEnabled(false);
        buttonC.setEnabled(false);
        buttonD.setEnabled(false);

    }

    private void enableButtons(){
        buttonA.setEnabled(true);
        buttonB.setEnabled(true);
        buttonC.setEnabled(true);
        buttonD.setEnabled(true);
    }


    private void finalQuizData(){

        Intent resultData = new Intent(QuizActivity.this,Result.class);
        resultData.putExtra(Constants.COINS,coins);
        resultData.putExtra(Constants.TOTAL_QUES,sizeOfQuiz);
        resultData.putExtra(Constants.CORRECT,correct);
        resultData.putExtra(Constants.WRONG,wrong);
        startActivity(resultData);


    }


}
