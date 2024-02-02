package com.example.treasureadventure;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Room {

    private Map<Direction, Integer> connections;
    private boolean isStartRoom;
    private final Random random = new Random();
    private boolean hasGoblin = false;
    private Goblin thisRoomGoblin;

    private static int nextId = 1; // Static variable to keep track of the next ID
    private int id;

    Room(boolean isStartRoom){
        this.id = nextId; // Assign the current ID to the new object
        nextId++;

        this.isStartRoom = isStartRoom;

        Map<Direction, Integer> placeholderConnections = new HashMap<>();
        placeholderConnections.put(Direction.UP, -1);
        placeholderConnections.put(Direction.DOWN, -1);
        placeholderConnections.put(Direction.RIGHT, -1);
        placeholderConnections.put(Direction.LEFT, -1);

        connections = placeholderConnections;
    }

    public void spawnGoblin(){
        thisRoomGoblin = new Goblin();
        hasGoblin = true;
    }

    public void setUPConnection(int connectedRoomId){
        connections.put(Direction.UP, connectedRoomId);
    }

    public void setDOWNConnection(int connectedRoomId){
        connections.put(Direction.DOWN, connectedRoomId);
    }

    public void setRIGHTConnection(int connectedRoomId){
        connections.put(Direction.RIGHT, connectedRoomId);
    }

    public void setLEFTConnection(int connectedRoomId){
        connections.put(Direction.LEFT, connectedRoomId);
    }

    public boolean hasGoblin(){
        return hasGoblin && thisRoomGoblin.getHP() > 0;
    }

    public Goblin getGoblin(){
        return thisRoomGoblin;
    }

    public Map<Direction, Integer> getConnections() {
        return connections;
    }

    public boolean isStartRoom(){
        return isStartRoom;
    }

    public int getId() {
        return id; // Getter method to access the unique ID
    }
}
