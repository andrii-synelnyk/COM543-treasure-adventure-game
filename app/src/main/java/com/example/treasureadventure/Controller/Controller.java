package com.example.treasureadventure.Controller;

import com.example.treasureadventure.Enums.Direction;
import com.example.treasureadventure.GameSaver.GameLoader;
import com.example.treasureadventure.Model.Model;
import com.example.treasureadventure.View.GameActivity;

public class Controller {

    public Model model;
    GameActivity gameActivity;

    public Controller(GameActivity gameActivity, boolean loaded, int numberOfRooms){
        if (!loaded) {
            model = new Model();
            model.initializeIfNotLoaded(numberOfRooms);
        }
        else model = new GameLoader(gameActivity).retreiveSave();

        setupView(gameActivity);
    }

    private void setupView(GameActivity gameActivity){
        this.gameActivity = gameActivity;
        updateButtons();
        gameActivity.updateInventoryView(model.player.getInventory());

        gameActivity.setupView(model.player.getMaxHP(), model.player.getHP(), model.getNumberOfAllRooms(), model.getNumberOfclearedRooms(), model.player.getFightState());
        if (model.player.getFightState()) startFightState();
    }

    private void updateButtons(){
        int[] availableRooms = model.checkAvailableRoomsToMoveTo();
        if(availableRooms[0] == 0) gameActivity.changeMoveButtonStatus(Direction.UP, false);
        else gameActivity.changeMoveButtonStatus(Direction.UP,true);
        if(availableRooms[1] == 0) gameActivity.changeMoveButtonStatus(Direction.DOWN, false);
        else gameActivity.changeMoveButtonStatus(Direction.DOWN, true);
        if(availableRooms[2] == 0) gameActivity.changeMoveButtonStatus(Direction.LEFT, false);
        else gameActivity.changeMoveButtonStatus(Direction.LEFT, true);
        if(availableRooms[3] == 0) gameActivity.changeMoveButtonStatus(Direction.RIGHT, false);
        else gameActivity.changeMoveButtonStatus(Direction.RIGHT, true);
    }

    public void move(Direction direction){
        model.movePlayer(direction);
        updateButtons();
        if (model.player.getFightState()) startFightState();
        updateDungeonProgressBar();
        gameActivity.updateInventoryView(model.player.getInventory());

        gameActivity.keepItemSelected();

        checkIfGameEnded();
    }

    private void startFightState(){
        gameActivity.changeFightOrUseButtonStatus(true);
        gameActivity.changeAllMoveButtonsStatus(false);

        // show monster image
        gameActivity.showMonster(true);
        gameActivity.showGoblinHPBar(true, model.currentGoblin.getHP(), model.currentGoblin.getMaxHP());
    }

    private void stopFightState(){
        gameActivity.changeFightOrUseButtonStatus(false);
        updateButtons();
        updateDungeonProgressBar();

        gameActivity.showGoblinHPBar(false, 0, 0); // int values don't matter

        // remove monster image
        gameActivity.showMonster(false);
        checkIfGameEnded();
    }

    public void fightOrUse(){
        model.fightOrUse();
        gameActivity.updateHPBar(model.player.getHP(), model.player.getMaxHP());
        gameActivity.updateGoblinHPBar(model.currentGoblin.getHP(), model.currentGoblin.getMaxHP());
        if (!model.player.getFightState()) stopFightState();

        // update view of items (change values or delete from the list)
        model.player.updateInventory();
        gameActivity.updateInventoryView(model.player.getInventory());
        itemDeselected();

        gameActivity.clearLastSelectedItem();

        checkIfGameEnded();
    }

    private void updateDungeonProgressBar(){
        int allRooms = model.getNumberOfAllRooms();
        int clearedRooms = model.getNumberOfclearedRooms();

        gameActivity.updateDungeonProgressBar(clearedRooms, allRooms);
    }

    public void itemSelected(int id){
        gameActivity.changeFightOrUseButtonText("Use");
        model.itemSelected(id);
    }

    public void itemDeselected(){
        gameActivity.changeFightOrUseButtonText("Attack");
        model.itemDeselected();
    }

    private void checkIfGameEnded(){
        if (model.gameWin){
            gameActivity.gameEnded("Win");
        } else if (model.gameOver){
            // show monster
            gameActivity.showMonster(true);
            gameActivity.gameEnded("Lose");
        }
    }
}
