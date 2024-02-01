package com.example.treasureadventure;

import android.graphics.Color;

public class Controller {

    Model model;
    View view;

    MainActivity mainActivity;

    Controller(MainActivity mainActivity){
        model = new Model();
        view = new View();

        this.mainActivity = mainActivity;
        updateButtons();
        mainActivity.updateInventoryView(model.player.getInventory());
    }

    private void updateButtons(){
        int[] availableRooms = model.checkAvailableRoomsToMoveTo();
        if(availableRooms[0] == 0) mainActivity.changeMoveButtonStatus(Direction.UP, false);
        else mainActivity.changeMoveButtonStatus(Direction.UP,true);
        if(availableRooms[1] == 0) mainActivity.changeMoveButtonStatus(Direction.DOWN, false);
        else mainActivity.changeMoveButtonStatus(Direction.DOWN, true);
        if(availableRooms[2] == 0) mainActivity.changeMoveButtonStatus(Direction.LEFT, false);
        else mainActivity.changeMoveButtonStatus(Direction.LEFT, true);
        if(availableRooms[3] == 0) mainActivity.changeMoveButtonStatus(Direction.RIGHT, false);
        else mainActivity.changeMoveButtonStatus(Direction.RIGHT, true);
    }

    public void move(Direction direction){
        model.movePlayer(direction);
        updateButtons();
        if (model.player.getFightState()) startFightState();
        updateDungeonProgressBar();
    }

    private void startFightState(){
        mainActivity.changeFightOrUseButtonStatus(true);
        mainActivity.changeAllMoveButtonsStatus(false);

        mainActivity.showGoblinHealthBar(true, model.currentGoblin.getHP(), model.currentGoblin.getMaxHP());
    }

    private void stopFightState(){
        mainActivity.changeFightOrUseButtonStatus(false);
        updateButtons();
        updateDungeonProgressBar();

        mainActivity.showGoblinHealthBar(false, 0, 0); // int values don't matter
    }

    public void fightOrUse(){
        model.fightOrUse();
        mainActivity.updateHPBar(model.player.getHP(), model.player.getMaxHP());
        mainActivity.updateGoblinHPBar(model.currentGoblin.getHP(), model.currentGoblin.getMaxHP());
        if (!model.player.getFightState()) stopFightState();

        // update view of items (change values or delete from the list)
        model.player.updateInventory();
        mainActivity.updateInventoryView(model.player.getInventory());
        itemDeselected();
    }

    private void updateDungeonProgressBar(){
        int allRooms = model.getNumberOfAllRooms();
        int clearedRooms = model.getNumberOfclearedRooms();

        mainActivity.updateDungeonProgressBar(clearedRooms, allRooms);
    }

    public void itemSelected(int id){
        // list item is highlighted inside mainActivity
        // button is changed from fight to use from controller
        // item selected passed into model

        mainActivity.changeFightOrUseButtonText("Use");
        model.itemSelected(id);
    }

    public void itemDeselected(){
        mainActivity.changeFightOrUseButtonText("Fight bare-handed");
        model.itemDeselected();
    }
}
