package com.example.treasureadventure;

import java.util.Random;

public class Goblin {

    private final Random random = new Random();
    private int maxHP;
    private int hp;

    Goblin(){
       maxHP = 0 + random.nextInt(6); // max 10
       hp = maxHP;
    }

    public int getHP(){
        return hp;
    }

    public int getMaxHP() { return maxHP; }

    public void setHP(int newHP){
        hp = newHP;
    }
}
