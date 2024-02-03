package com.example.treasureadventure.Model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import java.util.ArrayList;

@Root
public class Player {
    @Element
    private int maxHP = 30;
    @Element
    private int hp;
    @Element
    private Room currentRoom;
    @Element
    private boolean fightState = false;
    @ElementList(entry = "item", inline = true, required = false)
    private ArrayList<Item> inventory = new ArrayList<>();

    // SimpleXML requires a no-arg constructor for serialization
    public Player() {
        // This constructor is required by SimpleXML
    }

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
        inventory.add(new Item(true));
    }

    public ArrayList<Item> getInventory(){
        return inventory;
    }

    public void updateInventory(){
        inventory.removeIf(item -> item.getValue() == 0);
    }
}
