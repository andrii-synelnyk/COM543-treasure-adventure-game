package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button newGameButton;
    Button loadGameButton;
    GameLoader gameLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        newGameButton = findViewById(R.id.newGameButton);
        loadGameButton = findViewById(R.id.loadGameButton);

        checkIfSaveExists();

        // Set up listeners for each button
        newGameButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
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
}