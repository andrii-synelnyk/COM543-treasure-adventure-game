package com.example.treasureadventure;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private Map<Direction, Room> connections;

    Room(){
        Map<Direction, Room> placeholderConnections = new HashMap<>();
        placeholderConnections.put(Direction.UP, null);
        placeholderConnections.put(Direction.DOWN, null);
        placeholderConnections.put(Direction.RIGHT, null);
        placeholderConnections.put(Direction.LEFT, null);

        connections = placeholderConnections;
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

    public Map<Direction, Room> getConnections() {
        return connections;
    }
}
