package com.example.treasureadventure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DungeonGenerator {

    private int initialArraySize;
    private int[][] mazeArray;
    private final List<int[]> listOfRooms = new ArrayList<>();
    private final Random random = new Random();

    public void generateDungeon(int roomsNeeded) {

        initialArraySize = roomsNeeded;
        mazeArray = new int[initialArraySize][initialArraySize];

        // Initialize mazeArray with zeros
        for (int[] ints : mazeArray) {
            Arrays.fill(ints, 0);
        }

        // Define start room location
        double startRoomX = (double) initialArraySize / 2;
        double startRoomY = (double) initialArraySize / 2;

        if (startRoomX == Math.ceil(startRoomX)) {
            int randomFactorX = random.nextInt(2); // Random int 0 or 1
            if (randomFactorX == 1) startRoomX--;

            int randomFactorY = random.nextInt(2); // Random int 0 or 1
            if (randomFactorY == 1) startRoomY--;
        } else {
            startRoomX = Math.floor(startRoomX);
            startRoomY = Math.floor(startRoomY);
        }

        // Place start room
        System.out.println(startRoomY); // DEBUG
        System.out.println(startRoomX); // DEBUG
        mazeArray[(int)startRoomY][(int)startRoomX] = 5;
        listOfRooms.add(new int[]{(int)startRoomY, (int)startRoomX});

        // Generating branching rooms
        for (int roomsAdded = 1; roomsAdded < roomsNeeded; roomsAdded++) {
            int roomIndexToBranchFurther = random.nextInt(listOfRooms.size());
            int[] possiblyNewRoom = createRoomFrom(listOfRooms.get(roomIndexToBranchFurther));

            if (possiblyNewRoom[0] != -1 && possiblyNewRoom[1] != -1) {
                listOfRooms.add(possiblyNewRoom);
            } else roomsAdded--; // Means room was not successfully added since to many neighbours, need to try again
        }

        showDungeon(mazeArray);
    }

    public int[] createRoomFrom(int[] parentRoom) {
        int parentRoomY = parentRoom[0];
        int parentRoomX = parentRoom[1];
        boolean checkUP = false, checkDOWN = false, checkRIGHT = false, checkLEFT = false;
        List<int[]> candidateCoordinatesForNewRoom = new ArrayList<>();

        // Check surrounding rooms
        if (parentRoomY - 1 >= 0) checkUP = true;
        if (parentRoomY + 1 < initialArraySize) checkDOWN = true;
        if (parentRoomX - 1 >= 0) checkLEFT = true;
        if (parentRoomX + 1 < initialArraySize) checkRIGHT = true;

        // Add candidate rooms
        if (checkUP && mazeArray[parentRoomY - 1][parentRoomX] == 0) {
            candidateCoordinatesForNewRoom.add(new int[]{parentRoomY - 1, parentRoomX, 1});
        }
        if (checkDOWN && mazeArray[parentRoomY + 1][parentRoomX] == 0) {
            candidateCoordinatesForNewRoom.add(new int[]{parentRoomY + 1, parentRoomX, 2});
        }
        if (checkLEFT && mazeArray[parentRoomY][parentRoomX - 1] == 0) {
            candidateCoordinatesForNewRoom.add(new int[]{parentRoomY, parentRoomX - 1, 3});
        }
        if (checkRIGHT && mazeArray[parentRoomY][parentRoomX + 1] == 0) {
            candidateCoordinatesForNewRoom.add(new int[]{parentRoomY, parentRoomX + 1, 4});
        }

        if (!candidateCoordinatesForNewRoom.isEmpty()) {
            int[] randomRoomFromCandidates = candidateCoordinatesForNewRoom.get(random.nextInt(candidateCoordinatesForNewRoom.size()));
            mazeArray[randomRoomFromCandidates[0]][randomRoomFromCandidates[1]] = randomRoomFromCandidates[2]; // Assuming 1 represents a room
            return new int[]{randomRoomFromCandidates[0], randomRoomFromCandidates[1]};
        } else {
            return new int[]{-1, -1};
        }
    }

    public void showDungeon(int[][] dungeonArray) {
        for (int[] ints : dungeonArray) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }
    }
}
