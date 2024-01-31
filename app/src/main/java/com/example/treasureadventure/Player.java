package com.example.treasureadventure;

public class Player {

    private int hp = 30;
    private Room currentRoom;
    private Item[] inventory;
    private boolean fightState = false;

    Player (Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void moveTo(Room newRoom) {
        currentRoom = newRoom;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }

    public void setFightState(boolean state){
        fightState = state;
    }

    public boolean getFightState(){
        return fightState;
    }

    public void setHP(int newHP){
        hp = newHP;
    }

    public int getHP(){
        return hp;
    }
}
