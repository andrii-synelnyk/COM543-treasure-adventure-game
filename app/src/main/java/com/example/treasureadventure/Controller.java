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
        if(availableRooms[0] == 0) mainActivity.deactivateButton(Direction.UP);
        else mainActivity.activateButton(Direction.UP);
        if(availableRooms[1] == 0) mainActivity.deactivateButton(Direction.DOWN);
        else mainActivity.activateButton(Direction.DOWN);
        if(availableRooms[2] == 0) mainActivity.deactivateButton(Direction.LEFT);
        else mainActivity.activateButton(Direction.LEFT);
        if(availableRooms[3] == 0) mainActivity.deactivateButton(Direction.RIGHT);
        else mainActivity.activateButton(Direction.RIGHT);
    }

    public void move(Direction direction){
        model.movePlayer(direction);
        updateButtons();
    }
}
