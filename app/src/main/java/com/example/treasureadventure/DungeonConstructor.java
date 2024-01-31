package com.example.treasureadventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DungeonConstructor {

    private Map<String, Room> rooms = new HashMap<>();
    private Room startRoom;

    public void constructRooms(int[][] dungeonMap){
        for (int i = 0; i < dungeonMap.length; i++) {
            for (int j = 0; j < dungeonMap[i].length; j++) {
                // If the room encountered is intended to be the start room
                if (dungeonMap[i][j] == 5){
                    String key = i + "," + j;
                    startRoom = new Room();
                    rooms.put(key, startRoom);
                }

                if (dungeonMap[i][j] != 0) analyzeAroundCell(i, j, dungeonMap);
            }
        }

        printRooms();
    }

    private void analyzeAroundCell(int coordinateY, int coordinateX, int[][] dungeonMap) {
        boolean checkUP = false, checkDOWN = false, checkRIGHT = false, checkLEFT = false;

        if (coordinateY - 1 >= 0) checkUP = true;
        if (coordinateY + 1 < dungeonMap.length) checkDOWN = true;
        if (coordinateX - 1 >= 0) checkLEFT = true;
        if (coordinateX + 1 < dungeonMap.length) checkRIGHT = true;

        Room currentRoom = checkIfAlreadyExists(coordinateY, coordinateX);

        if (checkUP && dungeonMap[coordinateY - 1][coordinateX] == 1) {
            Room connectedRoom = checkIfAlreadyExists(coordinateY - 1, coordinateX);
            currentRoom.setUPConnection(connectedRoom);
            connectedRoom.setDOWNConnection(currentRoom);
        }
        if (checkDOWN && dungeonMap[coordinateY + 1][coordinateX] == 2) {
            Room connectedRoom = checkIfAlreadyExists(coordinateY + 1, coordinateX);
            currentRoom.setDOWNConnection(connectedRoom);
            connectedRoom.setUPConnection(currentRoom);
        }
        if (checkLEFT && dungeonMap[coordinateY][coordinateX - 1] == 3) {
            Room connectedRoom = checkIfAlreadyExists(coordinateY, coordinateX - 1);
            currentRoom.setLEFTConnection(connectedRoom);
            connectedRoom.setRIGHTConnection(currentRoom);
        }
        if (checkRIGHT && dungeonMap[coordinateY][coordinateX + 1] == 4) {
            Room connectedRoom = checkIfAlreadyExists(coordinateY, coordinateX + 1);
            currentRoom.setRIGHTConnection(connectedRoom);
            connectedRoom.setLEFTConnection(currentRoom);
        }
    }

    private Room checkIfAlreadyExists(int coordinateY, int coordinateX) {
        String key = coordinateY + "," + coordinateX;
        if (!rooms.containsKey(key)) {
            rooms.put(key, new Room());
        }
        return rooms.get(key);
    }

    public Room getStartRoom(){
        return startRoom;
    }

    private void printRooms(){
        for (Room room : rooms.values()) {
            System.out.println(room.getConnections());
        }
    }
}
