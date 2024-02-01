package com.example.treasureadventure;

import java.util.Random;

public class Item {

    ItemType type;
    private int value;
    private final Random random = new Random();

    Item(){
        randomChooseItemType();
    }

    private void randomChooseItemType(){
        int randomFactor = random.nextInt(3);
        switch (randomFactor){
            case 0:
                type = ItemType.Sword;
                value = 5 + random.nextInt(6);
                break;
            case 1:
                type = ItemType.HealthPotion;
                value = 3 + random.nextInt(4);
                break;
            case 2:
                type = ItemType.EscapePortal;
                value = 1;
                break;
        }
    }

    public ItemType getType(){
        return type;
    }

    public int getValue(){
        return value;
    }

    public void setValue(int newValue){
        value = newValue;
    }

    public String toString(){
        switch (type){
            case Sword:
                return "🗡️ Sword | " + "deals to goblin " + value;
            case HealthPotion:
                return "🧪 Health Potion | heals " + value;
            case EscapePortal:
                return "🚪 Escape Portal | teleports to start";
        }
        return "Empty Item";
    }
}
