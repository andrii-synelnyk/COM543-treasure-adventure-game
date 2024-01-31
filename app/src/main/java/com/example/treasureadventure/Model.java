package com.example.treasureadventure;

import java.util.Map;

public class Model {

    Player player;

    Model() {
        startGame();
    }

    private void startGame(){
        Room startRoom = generateAndConstructDungeon();

        player = new Player(startRoom);
    }

    private Room generateAndConstructDungeon(){
        int roomsNeeded = 6;
        DungeonGenerator generator = new DungeonGenerator();
        generator.generateDungeon(roomsNeeded);

        int[][] dungeonMap = generator.getDungeonMap();
        DungeonConstructor constructor = new DungeonConstructor();
        constructor.constructRooms(dungeonMap);

        return constructor.getStartRoom();
    }

    // To know which move button to deactivate
    public int[] checkAvailableRoomsToMoveTo(){
        int[] availableDirections = {0, 0, 0, 0};

        Room currentRoom = player.getCurrentRoom();
        Map<Direction, Room> connections = currentRoom.getConnections();

        if(connections.get(Direction.UP) != null) availableDirections[0] = 1;
        if(connections.get(Direction.DOWN) != null) availableDirections[1] = 1;
        if(connections.get(Direction.LEFT) != null) availableDirections[2] = 1;
        if(connections.get(Direction.RIGHT) != null) availableDirections[3] = 1;

        return availableDirections;
    }

    public void movePlayer(Direction direction){
        Room currentRoom = player.getCurrentRoom();
        Map<Direction, Room> connections = currentRoom.getConnections();

        player.moveTo(connections.get(direction));
    }
}







