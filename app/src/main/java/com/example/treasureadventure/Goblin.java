package com.example.treasureadventure;

import java.util.Random;

public class Goblin {

    private final Random random = new Random();
    private int hp;

    Goblin(){
       hp = 5 + random.nextInt(6); // max 10
    }

    public int getHP(){
        return hp;
    }

    public void setHP(int newHP){
        hp = newHP;
    }
}
