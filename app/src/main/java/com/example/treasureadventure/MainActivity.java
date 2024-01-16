package com.example.treasureadventure;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    fun generateDungeon(roomsNeeded) { // 15

        // Create initial zeros array
        initialArraySize = 30;
        int[[]] mazeArray = new int[initialArraySize new int[initialArraySize]].fillZeros();

        // Define start room location
        startRoomX= initialArraySize / 2;

        if (!startRoomX.isInt()) {
            randomFactorX = Random.int(0, 1);
            if (randomFactorX == 1) startRoomX.roundUp(); else startRoomX.roundDown();

            startRoomY = 0;
            randomFactorY = Random.int(0, 1);
            if (randomFactorY == 1) startRoomY.roundUp(); else startRoomY.roundDown();
        } else {
            startRoomY = startRoomX;
        }

        // Place start room
        mazeArray[startRoomY][startRoomX] = 1;

        // Generating branching rooms

        int[][] listOfRooms = new int[][];
        listOfRooms.add([startRoomY, startRoomX])

        for(roomsAdded = 1; roomsAdded < roomsNeeded; roomsAdded++) {
            roomIndexToBranchFurther = Random.int(0, listOfRooms.length() - 1);

            int[] possiblyNewRoom = createRoomFrom(listOfRooms[roomIndexToBranchFurther]);
            if (possiblyNewRoom != [-1, -1]) {
                int[] newRoom = possiblyNewRoom;
                listOfRooms.add(newRoom);
            } else continue;
        }

        showDungeon();
    }

    fun createRoomFrom(int[] parentRoom) {
        int parentRoomY = parentRoom[0];
        int parentRoomX = parentRoom[1];
        checkUP, checkDOWN, checkRIGHT, checkLEFT = false;
        int[][] candidateCoordinatesForNewRoom = new int[][];

        if (parentRoomY - 1 != 0) checkUP = true;
        if (parentRoomY + 1 != initialArraySize) checkDOWN = true;
        if (parentRoomX - 1 != 0) checkLEFT = true;
        if (parentRoomX + 1 != initialArraySize) checkRIGHT = true;

        if (checkUP && mazeArray[parentRoomY - 1][parentRoomX] == 0) {
            candidateCoordinatesForNewRoom.add([parentRoomY - 1, parentRoomX, 1]);
        }
        if (checkDOWN && mazeArray[parentRoomY + 1][parentRoomX] == 0) {
            candidateCoordinatesForNewRoom.add([parentRoomY + 1, parentRoomX, 2])
        }
        if (checkLEFT && mazeArray[parentRoomY][parentRoomX - 1] == 0) {
            candidateCoordinatesForNewRoom.add([parentRoomY, parentRoomX - 1, 3]);
        }
        if (checkRIGHT && mazeArray[parentRoomY][parentRoomX + 1] == 0) {
            candidateCoordinatesForNewRoom.add([parentRoomY, parentRoomX + 1, 4]);
        }

        if (!candidateCoordinatesForNewRoom.empty()) {
            randomRoomFromCandidates = Random.int(0, candidateCoordinatesForNewRoom.length - 1);
        } else return [-1, -1];

        mazeArray[randomRoomFromCandidates[0]][randomRoomFromCandidates[1]] = randomRoomFromCandidates[2];
        return [randomRoomFromCandidates[0], randomRoomFromCandidates[1]];
    }

    fun showDungeon(int[][] dungeonArray) {
        for (int i = 0; i < dungeonArray.length; i++) {
            for (int j = 0; j < dungeonArray[i].length; j++) {
                System.out.print(dungeonArray[i][j] + " ");
            }
            System.out.println();
        }
    }
     */
}