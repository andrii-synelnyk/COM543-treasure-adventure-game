package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int roomsNeeded = 50;
        DungeonGenerator generator = new DungeonGenerator();
        generator.generateDungeon(roomsNeeded);

        int[][] dungeonMap = generator.getDungeonMap();
        DungeonConstructor constructor = new DungeonConstructor();
        constructor.constructRooms(dungeonMap);
    }

}