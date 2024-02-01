package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Controller controller;
    Button moveUpButton;
    Button moveLeftButton;
    Button moveRightButton;
    Button moveDownButton;
    Button fightOrUseButton;
    ProgressBar playerHPBar;
    ProgressBar dungeonProgressBar;
    ProgressBar goblinHealthBar;
    ListView inventoryList;
    private int selectedPosition = -1; // -1 indicates no selection


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
        dungeonProgressBar = findViewById(R.id.dungeonProgressBar);
        goblinHealthBar = findViewById(R.id.goblinHealthBar);
        inventoryList = findViewById(R.id.inventoryList);

        controller = new Controller(this);
        changeFightOrUseButtonStatus(false);
        updateHPBar(10, 10); // Make hp bar 100% at the start
        showGoblinHealthBar(false, 0, 0); // int values don't matter

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
        float onePercent = (float) maxHP / 100;
        int percentsToDisplay = (int) Math.ceil(newHP / onePercent);

        if (newHP <= 0) playerHPBar.setProgress(0);
        else playerHPBar.setProgress(percentsToDisplay);
    }

    public void updateDungeonProgressBar(int clearedRooms, int allRooms){
        float onePercent = (float) allRooms / 100;
        int percentsToDisplay = (int) Math.ceil(clearedRooms / onePercent);

        dungeonProgressBar.setProgress(percentsToDisplay);
    }

    public void showGoblinHealthBar(boolean status, int newHP, int maxHP){
        if (status) goblinHealthBar.setVisibility(View.VISIBLE);
        else goblinHealthBar.setVisibility(View.INVISIBLE);

        updateGoblinHPBar(newHP, maxHP); // needed for after damaging goblin, teleporting and coming back to him his health stays the same
    }

    public void updateGoblinHPBar(int newHP, int maxHP){
        float onePercent = (float) maxHP / 100;
        int percentsToDisplay = (int) Math.ceil(newHP / onePercent);

        if (newHP <= 0) goblinHealthBar.setProgress(0);
        else goblinHealthBar.setProgress(percentsToDisplay);
    }

    public void changeFightOrUseButtonText(String text){
        fightOrUseButton.setText(text);
    }

    public void updateInventoryView(ArrayList<Item> inventory){
        ArrayList<String> inventoryString = new ArrayList<>();

        for (Item item : inventory){
            inventoryString.add(item.toString());
        }

        ListView listView = findViewById(R.id.inventoryList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_single_choice, inventoryString);

        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Setting an item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedPosition == position) {
                    // Item deselected
                    listView.setItemChecked(position, false);
                    selectedPosition = -1;
                    controller.itemDeselected(); // Implement this method to handle deselection
                } else {
                    // Item selected
                    listView.setItemChecked(position, true);
                    selectedPosition = position;
                    controller.itemSelected(position);
                }
            }
        });

        selectedPosition = -1;
    }

}