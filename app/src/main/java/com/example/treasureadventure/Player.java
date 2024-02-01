package com.example.treasureadventure;

import java.util.ArrayList;

public class Player {

    private int maxHP = 30;
    private int hp;
    private Room currentRoom;
    private boolean fightState = false;
    private ArrayList<Item> inventory = new ArrayList<>();

    Player (Room currentRoom) {
        this.currentRoom = currentRoom;
        hp = maxHP;
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

    public int getMaxHP(){
        return maxHP;
    }

    public void addItemToInventory(){
        inventory.add(new Item());
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public void updateInventory(){
        inventory.removeIf(item -> item.getValue() == 0);
    }
}
