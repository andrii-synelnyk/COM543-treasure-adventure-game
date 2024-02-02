package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.AlertDialog;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

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
    GameSaver gameSaver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Intent intent = getIntent();
        boolean loadButtonPressed = intent.getBooleanExtra("load-button", false);
        int numberOfRooms = intent.getIntExtra("number-of-rooms", 10);

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

        if (!loadButtonPressed) controller = new Controller(this, false, numberOfRooms);
        else controller = new Controller(this, true, -1); // -1 is a placeholder, the number of rooms will be taken from save
        gameSaver = new GameSaver(this);

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

    public void setupView(int playerMaxHP, int playerHP, int allDungeonRooms, int clearedRooms){
        changeFightOrUseButtonStatus(false);
        updateHPBar(playerHP, playerMaxHP); // Make hp bar 100% at the start
        showGoblinHPBar(false, 0, 0); // int values don't matter
        updateDungeonProgressBar(clearedRooms, allDungeonRooms);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.saveButton) {
            gameSaver.save(controller.model);
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_button, menu);
        return true;
    }

    public void gameEnded(String result){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (result.equals("Win")){
            builder.setTitle("YOU WON! 🎉");
            builder.setMessage("Congratulations!");
        }else {
            builder.setTitle("YOU LOST 😢");
            builder.setTitle("Try one more time");
        }

        // Add the button
        builder.setPositiveButton("Return to menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                onBackPressed();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false); // Makes the dialog non-cancellable
        dialog.setCanceledOnTouchOutside(false); // The dialog is not cancelled when touched outside its window
        dialog.show();
    }
}