package com.example.treasureadventure.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.FrameLayout;
import android.view.ViewGroup;

import com.example.treasureadventure.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button newGameButton;
    Button loadGameButton;
    Button tutorialButton;
    Button aboutButton;
    Button exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        newGameButton = findViewById(R.id.newGameButton);
        loadGameButton = findViewById(R.id.loadGameButton);
        tutorialButton = findViewById(R.id.tutorialButton);
        aboutButton = findViewById(R.id.aboutButton);
        exitButton = findViewById(R.id.exitButton);

        checkIfSaveExists();

        // Set up listeners for each button
        newGameButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRoomNumberDialog();
            }
        });

        loadGameButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("load-button", true);
                startActivity(intent);
            }
        });

        tutorialButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });

        exitButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // After returning from other activities to make load game button active if new save got created
    @Override
    protected void onResume() {
        super.onResume();

        checkIfSaveExists();
    }

    private void checkIfSaveExists(){
        File file = new File(getFilesDir(), "savedGame.xml");
        boolean exists = file.exists();

        loadGameButton.setEnabled(exists);
    }

    private void openRoomNumberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Create an EditText with FrameLayout
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        FrameLayout container = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        input.setLayoutParams(params);
        container.addView(input);

        builder.setTitle("How many rooms?");
        builder.setMessage("How many rooms you want to explore? (recommended 10, acceptable 5-1000)");
        builder.setView(container); // Set container with EditText

        builder.setPositiveButton("Start Game", null); // Set OnClickListener to null initially

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                final TextView messageView = dialog.findViewById(android.R.id.message);
                Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String userInputString = input.getText().toString();
                        int userInputNumber;
                        try {
                            userInputNumber = Integer.parseInt(userInputString);
                            if (!(userInputNumber < 5) && !(userInputNumber > 1000)) {
                                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                                intent.putExtra("number-of-rooms", userInputNumber + 1);
                                startActivity(intent);
                                dialog.dismiss(); // Close the dialog if input is valid
                            } else {
                                if (messageView != null) {
                                    messageView.setText("Number of rooms should be in bounds 5-1000");
                                }
                                input.setText(""); // Clear the input field
                            }
                        } catch (NumberFormatException e) {
                            if (messageView != null) {
                                messageView.setText("Please enter a valid number.");
                            }
                            input.setText(""); // Clear the input field if input is not a number
                        }
                    }
                });
            }
        });

        dialog.show();
    }
}