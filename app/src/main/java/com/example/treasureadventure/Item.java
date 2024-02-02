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
        int randomFactor = random.nextInt(100); // Generate a number between 0 and 99

        if (randomFactor < 60) { // 60% chance
            type = ItemType.Sword;
            value = 5 + random.nextInt(6);
        } else if (randomFactor < 90) { // Additional 30% chance (60% to 89%)
            type = ItemType.HealthPotion;
            value = 3 + random.nextInt(4);
        } else { // Remaining 10% chance (90% to 99%)
            type = ItemType.EscapePortal;
            value = 1;
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
                return "🗡️ Sword | " + "damages " + value;
            case HealthPotion:
                return "🧪 Health Potion | heals " + value;
            case EscapePortal:
                return "🚪 Escape Portal | teleports to the previous room";
        }
        return "Empty Item";
    }
}
