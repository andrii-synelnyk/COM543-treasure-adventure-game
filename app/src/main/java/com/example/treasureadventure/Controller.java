package com.example.treasureadventure;

public class Controller {

    Model model;
    View view;

    MainActivity mainActivity;

    Controller(MainActivity mainActivity){
        model = new Model();
        view = new View();

        this.mainActivity = mainActivity;
        updateButtons();
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
    }

    private void startFightState(){
        mainActivity.changeFightOrUseButtonStatus(true);
        mainActivity.changeAllMoveButtonsStatus(false);
    }

    private void stopFightState(){
        mainActivity.changeFightOrUseButtonStatus(false);
        updateButtons();
    }

    public void fightOrUse(){
        model.fightOrUse();
        if (!model.player.getFightState()) stopFightState();
    }
}
