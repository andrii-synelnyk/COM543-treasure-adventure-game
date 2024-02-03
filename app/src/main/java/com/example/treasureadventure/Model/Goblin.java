package com.example.treasureadventure.Model;

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

    // No-argument constructor for SimpleXML
    public Goblin() {
        // This constructor is required by SimpleXML
    }

    public Goblin(boolean generateStats) {
        if (generateStats) {
            maxHP = 10 + random.nextInt(3); // max 12
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
