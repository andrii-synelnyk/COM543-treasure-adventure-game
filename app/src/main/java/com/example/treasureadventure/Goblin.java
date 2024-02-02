package com.example.treasureadventure;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.util.Random;

@Root
public class Goblin {

    private final Random random = new Random();
    @Element
    private int maxHP;
    @Element
    private int hp;

    // No-argument constructor for JAXB
    public Goblin() {
        // This constructor is required by JAXB
    }

    public Goblin(boolean generateStats) {
        if (generateStats) {
            maxHP = 5 + random.nextInt(6); // max 10
            hp = maxHP;
        }
    }

    public int getHP(){
        return hp;
    }

    public int getMaxHP() { return maxHP; }

    public void setHP(int newHP){
        hp = newHP;
    }
}
