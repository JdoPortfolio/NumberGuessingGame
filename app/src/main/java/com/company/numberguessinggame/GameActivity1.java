package com.company.numberguessinggame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity1 extends AppCompatActivity {

    private TextView textViewLast, textViewRight, textViewHint;
    private Button buttonConfirm;
    private EditText editTextGuess;

    boolean twoDigits, threeDigits, fourDigits;

    Random r =  new Random();
    int random;
    int remainingRight = 10;

    //Array to keep user guesses:
    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        textViewHint =  findViewById(R.id.textViewHint);
        textViewLast = findViewById(R.id.textViewLast);
        textViewRight =  findViewById(R.id.textViewRight);
        buttonConfirm =  findViewById(R.id.buttonConfirm);
        editTextGuess =  findViewById(R.id.editTextGuess);

        twoDigits = getIntent().getBooleanExtra("two", false);
        threeDigits = getIntent().getBooleanExtra("three", false);
        fourDigits =  getIntent().getBooleanExtra("four", false);

        if (twoDigits)
        {
            random = r.nextInt(90)+10;
        }
        if (threeDigits)
        {
            random = r.nextInt(900)+100;
        }
        if (fourDigits)
        {
            random = r.nextInt(9000)+1000;
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guess = editTextGuess.getText().toString();
                if (guess.equals(""))
                {
                    Toast.makeText(GameActivity1.this,"Please enter a guess!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    textViewLast.setVisibility(View.VISIBLE);
                    textViewRight.setVisibility(View.VISIBLE);
                    textViewHint.setVisibility(View.VISIBLE);

                    userAttempts++;
                    remainingRight--;

                    int userGuess = Integer.parseInt(guess);
                    guessesList.add(userGuess);

                    textViewLast.setText("Your last guess is: "+guess);
                    textViewRight.setText("Your remaining rights are: " +remainingRight);

                    if (random == userGuess)
                    {
                        AlertDialog.Builder builder =  new AlertDialog.Builder(GameActivity1.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Congratulations! My guess was "+random+"\n\n You guessed my number in " +
                                userAttempts+" attempts. \n\n Your guesses: "+
                                guessesList + "\n\n Would you like to play again?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(GameActivity1.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                               moveTaskToBack(true);
                               android.os.Process.killProcess(android.os.Process.myPid());
                               System.exit(1);
                            }
                        });

                        builder.create().show();
                    }
                    if (random < userGuess)
                    {
                        textViewHint.setText("Decrease your guess");
                    }
                    if (random > userGuess)
                    {
                        textViewHint.setText("Increase your guess");
                    }
                    if (remainingRight == 0)
                    {
                        AlertDialog.Builder builder =  new AlertDialog.Builder(GameActivity1.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry! Your right to guess is over"
                                + "\n\n My guess was " + random
                                +"\n\n Your guesses: "+ guessesList
                                + "\n\n Would you like to play again?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(GameActivity1.this, MainActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        });

                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });

                        builder.create().show();

                    }

                    editTextGuess.setText("");
                }
            }
        });

    }
}