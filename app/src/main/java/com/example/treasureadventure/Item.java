package com.example.treasureadventure;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.util.Random;

@Root
public class Item {

    @Element
    ItemType type;
    @Element
    private int value;
    private final Random random = new Random();

    // No-argument constructor for JAXB
    public Item() {
        // This constructor is required by JAXB
    }

    Item(boolean randomise){
        randomChooseItemType();
    }

    private void randomChooseItemType(){
        int randomFactor = random.nextInt(100); // Generate a number between 0 and 99

        if (randomFactor < 0) { // 60% chance
            type = ItemType.Sword;
            value = 5 + random.nextInt(6);
        } else if (randomFactor < 0) { // Additional 30% chance (60% to 89%)
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
                return "🔪 Knife | " + "damages " + value;
            case HealthPotion:
                return "💊 Stimulant | heals " + value;
            case EscapePortal:
                return "🚪 Escape | to prev room";
        }
        return "Empty Item";
    }
}
