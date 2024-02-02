package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    ProgressBar goblinHPBar;
    ListView inventoryList;
    private int selectedPosition = -1; // for deselection functionality
    TextView goblinHPText;
    TextView playerHPText;
    TextView dungeonProgressText;
    TextView inventoryCountText;
    private int savedPosition = -1; // for saving selected item between rooms
    ListView listView;


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
        goblinHPBar = findViewById(R.id.goblinHPBar);
        inventoryList = findViewById(R.id.inventoryList);
        goblinHPText = findViewById(R.id.goblinHPText);
        playerHPText = findViewById(R.id.playerHPText);
        dungeonProgressText = findViewById(R.id.dungeonProgressText);
        inventoryCountText = findViewById(R.id.inventoryCountText);

        controller = new Controller(this);

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

    public void setupView(int playerMaxHP, int allDungeonRooms){
        changeFightOrUseButtonStatus(false);
        updateHPBar(playerMaxHP, playerMaxHP); // Make hp bar 100% at the start
        showGoblinHPBar(false, 0, 0); // int values don't matter
        updateDungeonProgressBar(0, allDungeonRooms);
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
        
        //Update text on the bar
        playerHPText.setText(newHP + "/" + maxHP);
    }

    public void updateDungeonProgressBar(int clearedRooms, int allRooms){
        float onePercent = (float) allRooms / 100;
        int percentsToDisplay = (int) Math.ceil(clearedRooms / onePercent);

        dungeonProgressBar.setProgress(percentsToDisplay);
        
        //Update text on the bar
        dungeonProgressText.setText(clearedRooms + "/" + allRooms);
    }

    public void showGoblinHPBar(boolean status, int newHP, int maxHP){
        if (status) {
            goblinHPBar.setVisibility(View.VISIBLE);
            goblinHPText.setVisibility(View.VISIBLE);
        }
        else {
            goblinHPBar.setVisibility(View.INVISIBLE);
            goblinHPText.setVisibility(View.INVISIBLE);
        }

        updateGoblinHPBar(newHP, maxHP); // needed for after damaging goblin, teleporting and coming back to him his health stays the same
    }

    public void updateGoblinHPBar(int newHP, int maxHP){
        float onePercent = (float) maxHP / 100;
        int percentsToDisplay = (int) Math.ceil(newHP / onePercent);

        if (newHP <= 0) goblinHPBar.setProgress(0);
        else goblinHPBar.setProgress(percentsToDisplay);
        
        //Update text on the bar
        goblinHPText.setText(newHP + "/" + maxHP);
    }

    public void changeFightOrUseButtonText(String text){
        fightOrUseButton.setText(text);
    }

    public void updateInventoryView(ArrayList<Item> inventory){
        //Update number of items in inventory text
        inventoryCountText.setText("Items in inventory: " + inventory.size());

        //Update inventory list
        ArrayList<String> inventoryString = new ArrayList<>();

        for (Item item : inventory){
            inventoryString.add(item.toString());
        }

        listView = findViewById(R.id.inventoryList);
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
                    controller.itemDeselected();

                    savedPosition = -1;
                } else {
                    // Item selected
                    listView.setItemChecked(position, true);
                    selectedPosition = position;
                    controller.itemSelected(position);

                    savedPosition = position;
                }
            }
        });

        selectedPosition = -1;
    }

    public void keepItemSelected(){
        if (savedPosition != -1) {
            listView.setItemChecked(savedPosition, true);
            controller.itemSelected(savedPosition);
        }
    }

    public void clearLastSelectedItem(){
        savedPosition = -1;
    }

}