package com.example.treasureadventure;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Room {
    @XmlElement
    private Map<Direction, Integer> connections;
    @XmlElement
    private boolean isStartRoom;
    private final Random random = new Random();
    @XmlElement
    private boolean hasGoblin = false;
    @XmlElement
    private Goblin thisRoomGoblin;
    @XmlElement
    private static int nextId = 1; // Static variable to keep track of the next ID
    @XmlElement
    private int id;

    public Room() {
        // This constructor is required by JAXB
        initializeConnections();
    }

    Room(boolean isStartRoom){
        this.id = nextId; // Assign the current ID to the new object
        nextId++;

        this.isStartRoom = isStartRoom;
        initializeConnections();
    }

    private void initializeConnections() {
        Map<Direction, Integer> placeholderConnections = new HashMap<>();
        placeholderConnections.put(Direction.UP, -1);
        placeholderConnections.put(Direction.DOWN, -1);
        placeholderConnections.put(Direction.RIGHT, -1);
        placeholderConnections.put(Direction.LEFT, -1);

        connections = placeholderConnections;
    }

    public void spawnGoblin(){
        thisRoomGoblin = new Goblin(true);
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
