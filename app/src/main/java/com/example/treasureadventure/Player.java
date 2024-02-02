package com.example.treasureadventure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    @XmlElement
    private int maxHP = 30;
    @XmlElement
    private int hp;
    @XmlElement
    private Room currentRoom;
    @XmlElement
    private boolean fightState = false;
    @XmlElement
    private ArrayList<Item> inventory = new ArrayList<>();

    // JAXB requires a no-arg constructor for serialization
    public Player() {
        // This constructor is required by JAXB
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
