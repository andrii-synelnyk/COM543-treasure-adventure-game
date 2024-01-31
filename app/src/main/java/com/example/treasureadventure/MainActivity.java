package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Button moveUpButton;
    Button moveLeftButton;
    Button moveRightButton;
    Button moveDownButton;
    Button fightOrUseButton;
    ProgressBar playerHPBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain references to the buttons
        moveUpButton = findViewById(R.id.moveUPButton);
        moveLeftButton = findViewById(R.id.moveLEFTButton);
        moveRightButton = findViewById(R.id.moveRIGHTButton);
        moveDownButton = findViewById(R.id.moveDOWNButton);
        fightOrUseButton = findViewById(R.id.fightOrUseButton);
        playerHPBar = findViewById(R.id.playerHPBar);

        Controller controller = new Controller(this);
        changeFightOrUseButtonStatus(false);
        updateHPBar(10, 10); // Make hp bar 100% at the start

        // Set up listeners for each button
        moveUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.move(Direction.UP);
            }
        });

        moveLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.move(Direction.LEFT);
            }
        });

        moveRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.move(Direction.RIGHT);
            }
        });

        moveDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.move(Direction.DOWN);
            }
        });

        fightOrUseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.fightOrUse();
            }
        });
    }

    public void changeFightOrUseButtonStatus(boolean status){
        fightOrUseButton.setEnabled(status);
    }

    public void changeMoveButtonStatus(Direction button, boolean status){
        switch (button) {
            case UP:
                moveUpButton.setEnabled(status);
                break;
            case DOWN:
                moveDownButton.setEnabled(status);
                break;
            case LEFT:
                moveLeftButton.setEnabled(status);
                break;
            case RIGHT:
                moveRightButton.setEnabled(status);
                break;
        }
    }

    public void changeAllMoveButtonsStatus(boolean status){
        moveUpButton.setEnabled(status);
        moveDownButton.setEnabled(status);
        moveLeftButton.setEnabled(status);
        moveRightButton.setEnabled(status);
    }

    public void updateHPBar(int newHP, int maxHP){
        float onePercent = (float) maxHP /100;
        int percentsToDisplay = (int) (newHP / onePercent);

        if (newHP <= 0) playerHPBar.setProgress(0);
        else playerHPBar.setProgress(percentsToDisplay);
    }

}