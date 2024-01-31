package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Button moveUpButton;
    Button moveLeftButton;
    Button moveRightButton;
    Button moveDownButton;
    Button fightOrUseButton;

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

        Controller controller = new Controller(this);
        changeFightOrUseButtonStatus(false);

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

}