package com.example.nikita.scarnesdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final int WINNING_SCORE = 100;
    public int MAX_COMP_TURN_SCORE = 20;
    public static final int MAX_DICE_VALUE = 6;
    public static final int MIN_DICE_VALUE = 1;
    public int userOverallScore = 0;
    public int userTurnScore = 0;
    public int compOverallScore = 0;
    public int compTurnScore = 0;
    public int currentDiceValue = 0;
    public boolean userTurn = true; // user always starts the game
    public boolean gameOver = false;

    //Declare Id variables to associate them with xml declared widgets
    Button rollBt,holdBt,resetBt;
    ImageView diceImg;
    TextView winVw,actionVw,scoreVw,tempscoreVw,holdVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rollBt = (Button)findViewById(R.id.rollButton);
        holdBt = (Button)findViewById(R.id.holdButton);
        resetBt = (Button)findViewById(R.id.resetButton);
        scoreVw = (TextView) findViewById(R.id.scoreView);
        winVw = (TextView) findViewById(R.id.winText);
        diceImg = (ImageView)findViewById(R.id.imageView);
        actionVw = (TextView) findViewById(R.id.action);
        tempscoreVw = (TextView) findViewById(R.id.tempScore);
        //holdVw = (TextView) findViewById(R.id.holdText);

        winVw.setVisibility(View.INVISIBLE);

        rollBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll();
            }
        });

        resetBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        holdBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold();
            }
        });
    }

    public void roll() {
        Random random = new Random();
        currentDiceValue = random.nextInt((MAX_DICE_VALUE - MIN_DICE_VALUE) + 1) + MIN_DICE_VALUE;

        if(currentDiceValue == 1)
        {
            diceImg.setImageResource(R.drawable.dice1);
        }
        if(currentDiceValue == 2)
        {
            diceImg.setImageResource(R.drawable.dice2);
        }
        if(currentDiceValue == 3)
        {
            diceImg.setImageResource(R.drawable.dice3);
        }
        if(currentDiceValue == 4)
        {
            diceImg.setImageResource(R.drawable.dice4);
        }
        if(currentDiceValue == 5)
        {
            diceImg.setImageResource(R.drawable.dice5);
        }
        if(currentDiceValue == 6)
        {
            diceImg.setImageResource(R.drawable.dice6);
        }

        if(currentDiceValue != 1)
        {
            userTurnScore+=currentDiceValue;
            updateTurnScore(userTurnScore);
        }
        else
        {
            updateTurnScore(0);
            switchTurn();
        }
    }

    public void hold() {

        if (userTurn) userOverallScore += userTurnScore;
        else compOverallScore += compTurnScore;

        updateScore();

        compTurnScore = 0;
        userTurnScore = 0;
        updateTurnScore(0);

        if ((userOverallScore > WINNING_SCORE) || (compOverallScore > WINNING_SCORE)) gameOver();

        if (!gameOver) switchTurn();
    }

    void gameOver(){
        rollBt.setEnabled(false);
        holdBt.setEnabled(false);

        gameOver = true ;
    }

    public void updateScore() {
        if (gameOver) {
            if (userOverallScore >= WINNING_SCORE) {
                scoreVw.setText("Your Score: " + userOverallScore );
                winVw.setText("You Won!!");
            } else {
                scoreVw.setText("Computer's Score: " + compOverallScore);
                winVw.setText("Computer Won!!");
            }
        } else {
            scoreVw.setText("Your Score: " + userOverallScore + "    Computer Score: " + compOverallScore);
        }
    }

    public void switchTurn() {

        if (userTurn) {
            actionVw.setText("Computer's Turn");
            userTurn = !userTurn;
        }
        else actionVw.setText("Your Turn");

        if (userTurn) {
            rollBt.setEnabled(true);
            holdBt.setEnabled(true);
        }
        else
            computerTurn();
    }

    public void updateTurnScore(int score)
    {
        String tempScore="Turn Score:"+ score;
        tempscoreVw.setText(tempScore);
    }

    public void computerTurn()
    {
        rollBt.setEnabled(false);
        holdBt.setEnabled(false);

        while((compTurnScore + compOverallScore >= WINNING_SCORE) || (compTurnScore >= MAX_COMP_TURN_SCORE))
        {
            roll();
        }

    }

    public void reset() {
        userOverallScore = 0;
        compOverallScore = 0;

        userTurnScore = 0;
        compTurnScore = 0;
        updateTurnScore(0);

        userTurn = true;
        gameOver = false;

        scoreVw.setText("Your Score: 0    Computer Score: 0");

        actionVw.setText("Your turn");

        diceImg.setImageResource(R.drawable.dice1);

        rollBt.setEnabled(true);
        holdBt.setEnabled(true);

    }
}