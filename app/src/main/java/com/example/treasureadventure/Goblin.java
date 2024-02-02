package com.example.treasureadventure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.Random;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Goblin {

    private final Random random = new Random();
    @XmlElement
    private int maxHP;
    @XmlElement
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
