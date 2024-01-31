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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain references to the buttons
        moveUpButton = findViewById(R.id.moveUPButton);
        moveLeftButton = findViewById(R.id.moveLEFTButton);
        moveRightButton = findViewById(R.id.moveRIGHTButton);
        moveDownButton = findViewById(R.id.moveDOWNButton);

        Controller controller = new Controller(this);

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
    }

    public void deactivateButton(Direction button){
        switch (button) {
            case UP:
                moveUpButton.setEnabled(false);
                break;
            case DOWN:
                moveDownButton.setEnabled(false);
                break;
            case LEFT:
                moveLeftButton.setEnabled(false);
                break;
            case RIGHT:
                moveRightButton.setEnabled(false);
                break;
        }
    }

    public void activateButton(Direction button){
        switch (button) {
            case UP:
                moveUpButton.setEnabled(true);
                break;
            case DOWN:
                moveDownButton.setEnabled(true);
                break;
            case LEFT:
                moveLeftButton.setEnabled(true);
                break;
            case RIGHT:
                moveRightButton.setEnabled(true);
                break;
        }
    }

}