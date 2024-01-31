package com.example.treasureadventure;

public class Player {

    private int health;
    private Room currentRoom;
    private Item[] inventory;

    Player (Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public void moveTo(Room newRoom) {
        currentRoom = newRoom;
    }

    public Room getCurrentRoom(){
        return currentRoom;
    }
}
