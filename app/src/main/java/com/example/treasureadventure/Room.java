package com.example.treasureadventure;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Room {

    private Map<Direction, Room> connections;

    private boolean isStartRoom;
    private final Random random = new Random();
    private boolean hasGoblin = false;
    private Goblin thisRoomGoblin;

    Room(boolean isStartRoom){
        this.isStartRoom = isStartRoom;

        Map<Direction, Room> placeholderConnections = new HashMap<>();
        placeholderConnections.put(Direction.UP, null);
        placeholderConnections.put(Direction.DOWN, null);
        placeholderConnections.put(Direction.RIGHT, null);
        placeholderConnections.put(Direction.LEFT, null);

        connections = placeholderConnections;

        if (!isStartRoom) randomSpawnGoblin();
    }

    private void randomSpawnGoblin(){
        int randomFactor = random.nextInt(2); // 0 or 1 // 50% chance of goblin spawned
        if (randomFactor == 1){
            thisRoomGoblin = new Goblin();
            hasGoblin = true;
        }
    }

    public void setUPConnection(Room connectedRoom){
        connections.put(Direction.UP, connectedRoom);
    }

    public void setDOWNConnection(Room connectedRoom){
        connections.put(Direction.DOWN, connectedRoom);
    }

    public void setRIGHTConnection(Room connectedRoom){
        connections.put(Direction.RIGHT, connectedRoom);
    }

    public void setLEFTConnection(Room connectedRoom){
        connections.put(Direction.LEFT, connectedRoom);
    }

    public boolean hasGoblin(){
        return hasGoblin && thisRoomGoblin.getHP() > 0;
    }

    public Goblin getGoblin(){
        return thisRoomGoblin;
    }

    public Map<Direction, Room> getConnections() {
        return connections;
    }
}
